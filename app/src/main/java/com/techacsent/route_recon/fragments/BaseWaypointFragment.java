package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.UpdatedCreateTripResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.worker.AddMarkerWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static java.lang.String.format;

public abstract class BaseWaypointFragment extends Fragment {

    protected MapboxMap map;
    protected MapView mapView;
    protected DirectionsRoute currentRoute;
    protected Polyline optimizedPolyline;
    protected String encodedPath;
    protected WaypointDescription waypointDescription;

    protected LatLng originCoord;
    protected LatLng destinationCoord;
    protected Point originPosition;
    protected Point destinationPosition;
    protected List<Point> pointList;
    //protected List<WaypointModel.WayPointsBean> wayPointsListBeans;
    protected UpdatedCreateTripResponse.DataBean dataBean;
    protected boolean isUpdate;
    protected Map<String, Polygon> hashMap;
    protected Map<Double, Long> waypointHashMap;
    protected List<com.mapbox.mapboxsdk.annotations.Polygon> polygonList;

    protected Icon startMarker;
    protected Icon waypointMarker;
    protected Icon endMarker;
    protected Icon safeIcon;
    protected Icon landmarkIcon;
    protected Icon dangerIcon;
    protected Icon hazardIcon;
    protected Map<String, com.mapbox.mapboxsdk.annotations.Polygon> dangerPolygonHashMap;
    protected NavigationOptionInterface navigationOptionInterface;
    protected int type = 1;
    protected List<LatLng> OUTER_POINTS = new ArrayList<>();
    /*protected UpdatedWaypointAdapter mAdapter;*/

    protected void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        assert Mapbox.getAccessToken() != null;
        NavigationRoute.Builder builder = NavigationRoute.builder(getActivity())
                .accessToken(/*Mapbox.getAccessToken()*/Constant.MAPBOX_API_KEY)
                .origin(origin)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .destination(destination);
        if (wayPoints != null && wayPoints.size() > 0) {
            for (Point point : wayPoints)
                builder.addWaypoint(point);
        }
        builder.build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                try {
                    if (response.body() == null) {
                        return;
                    } else if (response.body().routes().size() < 1) {
                        return;
                    }
                    currentRoute = response.body().routes().get(0);
                    drawOptimizedRoute(currentRoute);
                    encodedPath = Base64.encodeToString(Objects.requireNonNull(currentRoute.geometry()).getBytes(), Base64.DEFAULT);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void drawOptimizedRoute() {
        try {
            if (optimizedPolyline != null) {
                map.removePolyline(optimizedPolyline);
            }
            String decodedPath = new String(Base64.decode(encodedPath, Base64.DEFAULT));
            Timber.d(decodedPath);
            LatLng[] pointsToDraw = convertLineStringToLatLng(decodedPath);
            optimizedPolyline = map.addPolyline(new PolylineOptions()
                    .add(pointsToDraw)
                    .color(Color.parseColor(Constant.TEAL_COLOR))
                    .width(Constant.POLYLINE_WIDTH));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private LatLng[] convertLineStringToLatLng(String geometry) {
        LineString lineString = LineString.fromPolyline(geometry, PRECISION_6);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        return points;
    }

    private void drawOptimizedRoute(DirectionsRoute route) {
        if (optimizedPolyline != null) {
            map.removePolyline(optimizedPolyline);
        }
        LatLng[] pointsToDraw = convertLineStringToLatLng(route);
        optimizedPolyline = map.addPolyline(new PolylineOptions()
                .add(pointsToDraw)
                .color(Color.parseColor(Constant.TEAL_COLOR))
                .width(Constant.POLYLINE_WIDTH));
    }

    private LatLng[] convertLineStringToLatLng(DirectionsRoute route) {
        LineString lineString = LineString.fromPolyline(Objects.requireNonNull(route.geometry()), PRECISION_6);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        return points;
    }

    protected void loadSuccessFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
        Fragment fragment = new SuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("trip_id", Integer.parseInt(dataBean.getId()));
        bundle.putBoolean("is_new_trip", isUpdate);
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    protected void showRouteWithWaypoint(boolean isFromServer) {
        for (WaypointModel.WayPointsBean wayPointsBean : waypointDescription.getWayPointsBeanList()) {
            LatLng waypointLatLng = new LatLng(wayPointsBean.getLat(), wayPointsBean.getLongX());
            Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
            addWaypointMarker(wayPoint);
            pointList.add(wayPoint);
        }
        addStartPointMarker(originPosition);
        addDestinationMarker(destinationPosition);
        if(isFromServer){
            getRoute(originPosition, destinationPosition, pointList);
        }

    }

    protected void placeRouteMarker(){
        originCoord = new LatLng(dataBean.getStartpoint().getLat(), dataBean.getStartpoint().getLongX());
        destinationCoord = new LatLng(dataBean.getEndpoint().getLat(), dataBean.getEndpoint().getLongX());
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        if (isUpdate) {
            if (waypointDescription != null && waypointDescription.getWayPointsBeanList() != null && waypointDescription.getWayPointsBeanList().size() > 0) {
                showRouteWithWaypoint(false);
            } else {
                addStartPointMarker(originPosition);
                addDestinationMarker(destinationPosition);
                //getRoute(originPosition, destinationPosition, null);
            }

        } else {
            addStartPointMarker(originPosition);
            addDestinationMarker(destinationPosition);
            //getRoute(originPosition, destinationPosition, null);
        }

    }

    protected void addStartPointMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .snippet("0")
                .title(Utils.getAddress(point.latitude(), point.longitude(), false, getActivity()))
                .icon(startMarker));
    }

    protected void addDestinationMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .snippet("0")
                .title(Utils.getAddress(point.latitude(), point.longitude(), false, getActivity()))
                .icon(endMarker));
    }

    protected void addWaypointMarker(Point point) {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .title(Utils.getAddress(point.latitude(), point.longitude(), false, getActivity()))
                .snippet("0")
                .icon(waypointMarker));
        waypointHashMap.put(point.latitude(), marker.getId());
    }


    protected void createMarker(CreateTripMarkerModelClass createTripMarkerModelClass, double lat, double lonX, int type, double radius) {
        navigationOptionInterface.showProgressDialog(true);
        /*CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
        createTripMarkerModelClass.setLat(lat);
        createTripMarkerModelClass.setLongX(lonX);
        createTripMarkerModelClass.setMarkType(type);
        createTripMarkerModelClass.setAddress(Utils.getAddress(new LatLng(lat, lonX), false, getActivity()));
        createTripMarkerModelClass.setFullAddress(Utils.getAddress(new LatLng(lat, lonX), true, getActivity()));
        createTripMarkerModelClass.setTripId(Integer.parseInt(dataBean.getId()));
        createTripMarkerModelClass.setRadius(radius);*/
        ApiService apiService = new ApiCaller();
        apiService.createTripMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                createTripMarkerModelClass, new ResponseCallback<TripMarkerCreateResponse>() {
                    @Override
                    public void onSuccess(TripMarkerCreateResponse data) {
                        LatLng pointLatLong = new LatLng(lat, lonX);
                        Gson gson = new Gson();

                       /* String tripResponse = gson.toJson(createTripMarkerModelClass);

                        Data markerData = new Data.Builder()
                                .putString(Constant.KEY_MARKER_RESPONSE, tripResponse)
                                .putString(Constant.KEY_TRIP_MARKER_ID, String.valueOf(data.getSuccess().getMarkerID()))
                                .build();*/


                        String tripResponse = gson.toJson(data);
                        String markerInfo = gson.toJson(createTripMarkerModelClass);

                        Data markerData = new Data.Builder()
                                .putString(Constant.KEY_MARKER_RESPONSE, tripResponse)
                                .putString(Constant.KEY_MARKER_INFO, markerInfo)
                                .putString(Constant.KEY_TRIP_MARKER_ID, String.valueOf(data.getSuccess().getMarkerID()))
                                .build();

                        final OneTimeWorkRequest addMarkerWorker = new OneTimeWorkRequest.Builder(AddMarkerWorker.class)
                                .setInputData(markerData)
                                .build();
                        WorkManager.getInstance().enqueue(addMarkerWorker);
                        WorkManager.getInstance().getWorkInfoByIdLiveData(addMarkerWorker.getId()).observe(requireActivity(), workInfo -> {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                switch (createTripMarkerModelClass.getMarkType()) {
                                    case 1:
                                        map.addMarker(new MarkerOptions()
                                                .position(pointLatLong)
                                                .setTitle(Utils.getAddress(pointLatLong.getLatitude(),pointLatLong.getLongitude(), true, requireActivity().getApplicationContext()))
                                                .setSnippet(String.valueOf(data.getSuccess().getMarkerID()))
                                                .icon(safeIcon));
                                        navigationOptionInterface.showProgressDialog(false);
                                        break;
                                    case 2:
                                        map.addMarker(new MarkerOptions()
                                                .position(pointLatLong)
                                                .setTitle(Utils.getAddress(pointLatLong.getLatitude(),pointLatLong.getLongitude(), true, requireActivity().getApplicationContext()))
                                                .setSnippet(String.valueOf(data.getSuccess().getMarkerID()))
                                                .icon(landmarkIcon));
                                        navigationOptionInterface.showProgressDialog(false);
                                        break;
                                    case 3:
                                        map.addMarker(new MarkerOptions()
                                                .position(pointLatLong)
                                                .setTitle(Utils.getAddress(pointLatLong.getLatitude(),pointLatLong.getLongitude(), true, requireActivity().getApplicationContext()))
                                                .setSnippet(String.valueOf(data.getSuccess().getMarkerID()))
                                                .icon(dangerIcon));
                                        com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(lat, lonX), radius / 1000, 64));
                                        polygonList.add(polygon);
                                        hashMap.put(String.valueOf(data.getSuccess().getMarkerID()), polygon);
                                        Timber.d("%s", polygonList.size());
                                        navigationOptionInterface.showProgressDialog(false);
                                        break;

                                    case 4:
                                        map.clear();
                                        //loadRoute();
                                        placeRouteMarker();
                                        drawOptimizedRoute();
                                        loadMarkerFromDB();
                                        OUTER_POINTS.clear();
                                        navigationOptionInterface.showProgressDialog(false);

                                }
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        navigationOptionInterface.showProgressDialog(false);
                    }
                });

    }

    protected void loadRoute() {
        originCoord = new LatLng(dataBean.getStartpoint().getLat(), dataBean.getStartpoint().getLongX());
        destinationCoord = new LatLng(dataBean.getEndpoint().getLat(), dataBean.getEndpoint().getLongX());
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        if (isUpdate) {
            if (waypointDescription != null && waypointDescription.getWayPointsBeanList() != null && waypointDescription.getWayPointsBeanList().size() > 0) {
                showRouteWithWaypoint(true);
            } else {
                addStartPointMarker(originPosition);
                addDestinationMarker(destinationPosition);
                getRoute(originPosition, destinationPosition, null);
            }

        } else {
            addStartPointMarker(originPosition);
            addDestinationMarker(destinationPosition);
            getRoute(originPosition, destinationPosition, null);
        }
    }

    protected void loadMarkerFromDB() {
        List<MarkerDescription> markerObjectList = AppDatabase.getAppDatabase(requireActivity().getApplicationContext()).daoMarker().fetchMarkerByTripId(dataBean.getId());
        if (markerObjectList != null && markerObjectList.size() > 0) {
            for (MarkerDescription markersBean : markerObjectList)
                if (markersBean.getMarkType() == 1) {
                    map.addMarker(new MarkerOptions()
                            .title(markersBean.getAddress())
                            .snippet(markersBean.getMarkerId())
                            .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                            .setIcon(safeIcon);
                } else if (markersBean.getMarkType() == 2) {
                    map.addMarker(new MarkerOptions()
                            .title(markersBean.getAddress())
                            .snippet(markersBean.getMarkerId())
                            .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                            .setIcon(landmarkIcon);
                } else if (markersBean.getMarkType() == 3) {
                    map.addMarker(new MarkerOptions()
                            .title(markersBean.getAddress())
                            .snippet(markersBean.getMarkerId())
                            .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                            .setIcon(dangerIcon);
                    com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius() / 1000, 64));
                    polygonList.add(polygon);
                    hashMap.put(markersBean.getMarkerId(), polygon);
                    //Timber.d("%s", polygonList.size());
                } else if (markersBean.getMarkType() == 4) {
                   
                    if (markersBean.getHazardMarkerPointList() != null) {



                        List<LatLng> latLngs = new ArrayList<>();

                        int k = 1;


                        for (LocationsBeanForLocalDb locationsBean : markersBean.getHazardMarkerPointList()) {


                            latLngs.add(new LatLng(locationsBean.getLat(), locationsBean.getLongX()));


                        }

                        for (LatLng latLng : latLngs) {

                            IconFactory iconFactory = IconFactory.getInstance(getActivity());
                            Icon icon = iconFactory.fromResource(R.drawable.hazard_60);

                            //   Log.e("PoinID","value"+ markerDescription.getHazardMarkerPointList().get(k-1).getId() );


                            //for marker
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(latLng.getLatitude(), latLng.getLongitude()))
                                    .icon(icon)
                                    .setSnippet(markersBean.getMarkerId() + "-" + String.valueOf(markersBean.getHazardMarkerPointList().get(k - 1).getId()))
                                    .title(markersBean.getHazardMarkerPointList().get(k - 1).getName()));


                            k++;

                        }

                        if (latLngs.size() > 2) {


                            // add midpoint as a marker
                            for (int i = 0; i < latLngs.size(); i++) {

                                if (i == latLngs.size() - 1) {


                                    double distance = calculateDistanceDifferenceInMeter(latLngs.get(i).getLongitude(),
                                            latLngs.get(i).getLatitude(),
                                            latLngs.get(0).getLongitude(),
                                            latLngs.get(0).getLatitude());


                                    IconFactory iconFactory = IconFactory.getInstance(getActivity());
                                    Icon icon = iconFactory.fromBitmap(getBitmapFromView(String.valueOf(format("%.2f", distance))));


                                    map.addMarker(new MarkerOptions()
                                            .position(midPoint(latLngs.get(i).getLatitude(), latLngs.get(i).getLongitude(),
                                                    latLngs.get(0).getLatitude(), latLngs.get(0).getLongitude()))
                                            .icon(icon));


                                } else {


                                    double distance = calculateDistanceDifferenceInMeter(latLngs.get(i).getLongitude(),
                                            latLngs.get(i).getLatitude(),
                                            latLngs.get(i + 1).getLongitude(),
                                            latLngs.get(i + 1).getLatitude());

                                    IconFactory iconFactory = IconFactory.getInstance(getActivity());
                                    Icon icon = iconFactory.fromBitmap(getBitmapFromView(String.valueOf(format("%.2f", distance))));


                                    map.addMarker(new MarkerOptions()
                                            .position(midPoint(latLngs.get(i).getLatitude(), latLngs.get(i).getLongitude(),
                                                    latLngs.get(i + 1).getLatitude(), latLngs.get(i + 1).getLongitude()))
                                            .icon(icon));
                                }
                            }


                            com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = map.addPolygon(new PolygonOptions()
                                    .addAll(latLngs)
                                    .fillColor(Color.YELLOW)
                                    .alpha(0.2f));


                            //convert latlong type to google maps typw
                            List<com.google.android.gms.maps.model.LatLng> latLngList = new ArrayList<>();

                            for (LatLng latLng : latLngs) {


                                latLngList.add(new com.google.android.gms.maps.model.LatLng(latLng.getLatitude(), latLng.getLongitude()));
                            }



                            double area = SphericalUtil.computeArea(latLngList);


                            // for showing area

                            IconFactory iconFactory = IconFactory.getInstance(getActivity());
                            Icon icon = iconFactory.fromBitmap(getBitmapFromViewForArea(String.valueOf(format("%.2f", area))));

                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(centroid(latLngs)[0], centroid(latLngs)[1]))
                                    .icon(icon));
                        } else if (latLngs.size() == 2){

                            //for line draw


                            double distance = calculateDistanceDifferenceInMeter(latLngs.get(0).getLongitude(),
                                    latLngs.get(0).getLatitude(),
                                    latLngs.get(1).getLongitude(),
                                    latLngs.get(1).getLatitude());


                            IconFactory iconFactory = IconFactory.getInstance(getActivity());
                            Icon icon = iconFactory.fromBitmap(getBitmapFromView(String.valueOf(format("%.2f", distance))));


                            map.addMarker(new MarkerOptions()
                                    .position(midPoint(latLngs.get(0).getLatitude(), latLngs.get(0).getLongitude(),
                                            latLngs.get(1).getLatitude(), latLngs.get(1).getLongitude()))
                                    .icon(icon));


                            // create new PolylineOptions from all points
                            PolylineOptions polylineOptions = new PolylineOptions()
                                    .addAll(latLngs)
                                    .color(Color.YELLOW)
                                    .width(3f);

                            // add polyline to MapboxMap object
                            map.addPolyline(polylineOptions);


                        }
                        
                        
                        

                    }
                }
        }
    }

    protected void loadMarker() {
        List<MarkerDescription> markerDescriptionList = AppDatabase.getAppDatabase(requireActivity().getApplicationContext()).daoMarker().fetchMarkerByTripId(dataBean.getId());
        if (markerDescriptionList != null && markerDescriptionList.size() > 0) {
            for (MarkerDescription markerDescription : markerDescriptionList) {
                if (markerDescription.getMarkType() == 1) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(safeIcon));
                } else if (markerDescription.getMarkType() == 2) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(landmarkIcon));

                } else if (markerDescription.getMarkType() == 3) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(dangerIcon));
                    com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter
                            (new LatLng(markerDescription.getLat(), markerDescription.getLongX()), markerDescription.getRadius() / 1000, 64));
                    dangerPolygonHashMap.put(markerDescription.getMarkerId(), polygon);
                } else if (markerDescription.getMarkType() == 4) {


                    if (markerDescription.getHazardMarkerPointList() != null) {
                        List<LatLng> latLngs = new ArrayList<>();
                        for (LocationsBeanForLocalDb locationsBean : markerDescription.getHazardMarkerPointList()) {
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(locationsBean.getLat(), locationsBean.getLongX()))
                                    .title(Utils.getAddress(locationsBean.getLat(), locationsBean.getLongX(), true, RouteApplication.getInstance().getApplicationContext()))
                                    .setSnippet(markerDescription.getMarkerId())
                                    .icon(hazardIcon));
                            latLngs.add(new LatLng(locationsBean.getLat(), locationsBean.getLongX()));
                        }
                        com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = map.addPolygon(new PolygonOptions()
                                .addAll(latLngs)
                                .fillColor(Color.YELLOW)
                                .alpha(0.3f));

                    }
                }


            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }


    protected Bitmap getBitmapFromView(String value) {
        //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

        View customView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text, null);

        TextView textView = customView.findViewById(R.id.textView);
        textView.setText(value + " " + "m ");

        //    textView.setTextColor(Color.parseColor("#FFFFFF"));


        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            textView.setTextColor(Color.parseColor("#000000"));
        } else {

            //textView.setTextColor(getResources().getColor(R.color.white, null));
            textView.setTextColor(Color.parseColor("#FFFFFF"));

        }

        customView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customView.layout(0, 0, customView.getMeasuredWidth(), customView.getMeasuredHeight());
        customView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customView.getMeasuredWidth(), customView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customView.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        customView.draw(canvas);
        return returnedBitmap;
    }


    protected Bitmap getBitmapFromViewForArea(String value) {
        //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

        View customView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text_area, null);

        TextView textView = customView.findViewById(R.id.textView);

        textView.setTextColor(Color.parseColor("#FFFFFF"));

        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            textView.setTextColor(Color.parseColor("#000000"));
        } else {

            //textView.setTextColor(getResources().getColor(R.color.white, null));
            textView.setTextColor(Color.parseColor("#FFFFFF"));

        }

        textView.setText(value + " " + "m²");
        customView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customView.layout(0, 0, customView.getMeasuredWidth(), customView.getMeasuredHeight());
        customView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customView.getMeasuredWidth(), customView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customView.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        customView.draw(canvas);
        return returnedBitmap;
    }


    public static double[] centroid(List<LatLng> points) {
        double[] centroid = {0.0, 0.0};

        for (int i = 0; i < points.size(); i++) {
            centroid[0] += points.get(i).getLatitude();
            centroid[1] += points.get(i).getLongitude();
        }

        int totalPoints = points.size();
        centroid[0] = centroid[0] / totalPoints;
        centroid[1] = centroid[1] / totalPoints;

        return centroid;
    }

    public double calculateDistanceDifferenceInMeter(double startLng, double startLat,
                                                     double endLng, double endLat) {

        Location startPoint = new Location("locationStart");
        startPoint.setLatitude(startLat);
        startPoint.setLongitude(startLng);

        Location endPoint = new Location("locationEnd");
        endPoint.setLatitude(endLat);
        endPoint.setLongitude(endLng);

        double distance = startPoint.distanceTo(endPoint);


        return distance;


    }

    public LatLng midPoint(double lat1, double lon1, double lat2, double lon2) {

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));

    }

}
