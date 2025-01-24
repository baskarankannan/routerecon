/*
package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.LandmarkActivity;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.mapbox.core.constants.Constants.PRECISION_6;

*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class TripDetailsFragment extends Fragment {
    private MapboxMap map;
    private MapView mapView;
    private Button btnChecklist;
    private Button btnNavigation;
    private Button btnDirection;
    private TextView tvTripName;
    private TextView tvStarts;
    private TextView tvEnds;
    private LinearLayout tripStartTime;
    private LinearLayout tripEndTime;

    private Point originPosition;
    private Point destinationPosition;
    private LatLng originCoord;
    private LatLng destinationCoord;
    private NavigationOptionInterface navigationOptionInterface;

    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon deleteIcon;
    private Icon startMarker;
    private Icon waypointMarker;
    private Icon endMarker;
    private List<Point> pointList;

    */
/*private TripListObject tripListObject;
    private List<MarkerObject> markerObjectList;*//*

    private Polyline optimizedPolyline;
    private int tripId;
    private LayoutInflater layoutInflater;
    private static final int REQUEST_UPDATE_MARKER = 102;
    private static final String TAG = TripDetailsFragment.class.getSimpleName();

    public TripDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt(Constant.KEY_TRIP_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pointList = new ArrayList<Point>();
        navigationOptionInterface.hideToolbar(false);
        initializeView(view, savedInstanceState);

        initializeListener();
        */
/*tripListObject = RealmController.with(this).getTripDetails(String.valueOf(PreferenceManager.getInt(Constant.KEY_USER_ID)), tripId);
        if (tripListObject.getMarkerList().size() > 0) {
            for (MarkerObject markerObject : tripListObject.getMarkerList()) {
                if (markerObject.getId() == tripId) {
                    markerObjectList.add(markerObject);
                }
            }
        }*//*

        //setupMap(tripListObject);
    }

    private void initializeView(View view, Bundle savedInstanceState) {
        navigationOptionInterface.tripDetailsToolbar();
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        btnChecklist = view.findViewById(R.id.btn_security_checklist);
        btnNavigation = view.findViewById(R.id.btn_navigation);
        btnDirection = view.findViewById(R.id.btn_direction);
        tvTripName = view.findViewById(R.id.trip_name);
        tvStarts = view.findViewById(R.id.trip_starts);
        tvEnds = view.findViewById(R.id.trip_ends);
        tripStartTime = view.findViewById(R.id.layout_trip_start_time);
        tripEndTime = view.findViewById(R.id.layout_trip_end_time);
        tripStartTime.setVisibility(View.GONE);
        tripEndTime.setVisibility(View.GONE);
        IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        deleteIcon = iconFactory.fromResource(R.drawable.delete_marker);
        startMarker = iconFactory.fromResource(R.mipmap.start_trip_marker);
        waypointMarker = iconFactory.fromResource(R.mipmap.waypoint_marker);
        endMarker = iconFactory.fromResource(R.mipmap.end_trip_marker);
        //markerObjectList = new ArrayList<>();
        layoutInflater = (LayoutInflater) Objects.requireNonNull(getContext())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initializeListener() {
        btnDirection.setOnClickListener(v -> Toast.makeText(getActivity(), Constant.MSG_FEATURE_UNDER_DEVELOPMENT, Toast.LENGTH_SHORT).show());
        btnChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment securityChecklistFragment = new SecurityChecklistFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("trip_id", tripId);
                securityChecklistFragment.setArguments(bundle);
                loadFragment(securityChecklistFragment);
            }
        });
        */
/*btnNavigation.setOnClickListener(v -> {
            NavigationOptionFragment navigationOptionFragment = new NavigationOptionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("trip_id", tripId);
            bundle.putDouble("lat", tripListObject.getStartLat());
            bundle.putDouble("lonX", tripListObject.getStartLonX());
            navigationOptionFragment.setArguments(bundle);
            navigationOptionFragment.show(getChildFragmentManager(), navigationOptionFragment.getTag());
        });*//*

    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.trip_details_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    */
/*private void setupMap(TripListObject tripListObject) {
        mapView.getMapAsync(mapboxMap -> {
            map = mapboxMap;
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(tripListObject.getStartLat(), tripListObject.getStartLonX()))
                    .zoom(9)
                    .tilt(30)
                    .bearing(180)
                    .build();

            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), Constant.TOTAL_TIME_INTERVAL);

            originCoord = new LatLng(tripListObject.getStartLat(), tripListObject.getStartLonX());
            destinationCoord = new LatLng(tripListObject.getEndLat(), tripListObject.getEndLonX());
            originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
            destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
            if (tripListObject.getWaypointList().size() > 0) {
                for (int i = 0; i < tripListObject.getWaypointList().size(); i++) {
                    LatLng waypointLatLng = new LatLng(tripListObject.getWaypointList().get(i).getLat(), tripListObject.getWaypointList().get(i).getLongX());
                    Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                    pointList.add(wayPoint);
                    addWaypointtMarker(tripListObject.getWaypointList().get(i));

                }
                addStartPointMarker(tripListObject);
                addDestinationMarker(tripListObject);
                drawOptimizedRoute();
            } else {
                pointList.add(originPosition);
                pointList.add(destinationPosition);
                addStartPointMarker(tripListObject);
                addDestinationMarker(tripListObject);
                drawOptimizedRoute();
            }

            tvTripName.setText(tripListObject.getTripName());
            tvStarts.setText(tripListObject.getStartAddress());
            tvEnds.setText(tripListObject.getEndAddress());
            if (markerObjectList.size() > 0) {
                for (MarkerObject markersBean : markerObjectList) {
                    if (markersBean.getMarkerType() == 1) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                                .title(markersBean.getAddress())
                                .setSnippet(markersBean.getMarkerID())
                                .icon(safeIcon));
                    } else if (markersBean.getMarkerType() == 2) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                                .title(markersBean.getAddress())
                                .setSnippet(markersBean.getMarkerID())
                                .icon(landmarkIcon));
                    } else if (markersBean.getMarkerType() == 3) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                                .title(markersBean.getAddress())
                                .setSnippet(markersBean.getMarkerID())
                                .icon(deleteIcon));
                        mapboxMap.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius(), 64));
                    }
                }
            }
            mapboxMap.setInfoWindowAdapter(marker -> {
                @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                TextView tvAddress = view.findViewById(R.id.tv_address);
                TextView tvRemove = view.findViewById(R.id.tv_delete);
                TextView tvEdit = view.findViewById(R.id.tv_edit);
                tvAddress.setText(marker.getTitle());
                tvRemove.setVisibility(View.GONE);
                if (marker.getSnippet().equals("0")) {
                    tvEdit.setVisibility(View.GONE);
                }
                tvEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), LandmarkActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("marker_id", marker.getSnippet());
                    bundle.putLong("long_marker", marker.getId());
                    bundle.putBoolean("is_marker_from_trip", true);
                    bundle.putInt(Constant.KEY_TRIP_ID, tripId);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_UPDATE_MARKER);
                    marker.hideInfoWindow();
                });
                return view;
            });
        });
    }*//*


   */
/* private void addStartPointMarker(TripListObject tripListObject) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(tripListObject.getStartLat(), tripListObject.getStartLonX()))
                .title(tripListObject.getStartAddress())
                .setSnippet("0")
                .icon(startMarker));
    }

    private void addWaypointtMarker(WaypointObject waypointObject) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(waypointObject.getLat(), waypointObject.getLongX()))
                .setSnippet("0")
                .title(waypointObject.getAddress())
                .icon(waypointMarker));
    }

    private void addDestinationMarker(TripListObject tripListObject) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(tripListObject.getEndLat(), tripListObject.getEndLonX()))
                .title(tripListObject.getEndAddress())
                .setSnippet("0")
                .icon(endMarker));
    }*//*


    */
/*private void drawOptimizedRoute() {
        if (optimizedPolyline != null) {
            map.removePolyline(optimizedPolyline);
        }
        String decodedPath = new String(Base64.decode(tripListObject.getRouteGeometry(), Base64.DEFAULT));
        LatLng[] pointsToDraw = convertLineStringToLatLng(decodedPath);
        optimizedPolyline = map.addPolyline(new PolylineOptions()
                .add(pointsToDraw)
                .color(Color.parseColor(Constant.TEAL_COLOR))
                .width(Constant.POLYLINE_WIDTH));
    }*//*


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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_UPDATE_MARKER) {
            if (data != null) {
                long markerId = data.getLongExtra("long_marker", 0);
                Toast.makeText(getActivity(), markerId + "", Toast.LENGTH_SHORT).show();
                map.removeAnnotation(markerId);
            }
        }
    }
}
*/
