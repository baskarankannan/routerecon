package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.TripAttendingAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.AddMoreObject;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.GetTripLocationModel;
import com.techacsent.route_recon.model.MyTripDescriptionModel;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.CancelUserModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.MyTripDescriptionViewModel;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTripDescriptionFragment extends Fragment implements OnRecyclerClickListener<MyTripDescriptionModel.DataBean.FriendAttendBean> {
    private MapboxMap map;
    private MapView mapView;
    private TextView tvTripName;
    private TextView tvStarts;
    private TextView tvEnds;
    private RecyclerView rvFriendsList;
    private Button btnCancelTrip;
    private Button btnAddMore;
    private String tripId;
    private String sharedTripId;
    private boolean isEditable;
    private MyTripDescriptionViewModel myTripDescriptionViewModel;
    private Point originPosition;
    private Point destinationPosition;
    private LatLng originCoord;
    private LatLng destinationCoord;
    /* private FriendsInSharedTripAdapter friendsInSharedTripAdapter;*/
    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon deleteIcon;
    private Icon startMarker;
    private Icon waypointMarker;
    private Icon endMarker;
    private Icon dot;
    private Icon hazardIcon;
    private Polyline optimizedPolyline;
    private Marker userMarker;
    private Handler handler;
    private Runnable runnable;
    private List<Point> pointList;
    private MyTripsResponse.ListBean item;
    private boolean isArchived;
    List<LatLng> latLngs = new ArrayList<>();
    private boolean isSnackbarShowed = false;
    private TripAttendingAdapter mAdapter;
    private boolean isRemovable;
    private int tripSharingId;
    private MyTripDescriptionModel model;
    private NavigationOptionInterface navigationOptionInterface;

    private TrafficPlugin trafficPlugin;

    public MyTripDescriptionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getString("trip_id");
            sharedTripId = getArguments().getString("shared_trip_id");
            item = getArguments().getParcelable("parcel");
            isEditable = getArguments().getBoolean("is_editable");
            isArchived = getArguments().getBoolean("is_archieved");
            isRemovable = getArguments().getBoolean("is_removable");
            Timber.d("%s", isRemovable);
            setHasOptionsMenu(false);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_trip_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mapbox.getInstance(Objects.requireNonNull(getActivity()), Constant.MAPBOX_API_KEY);
        myTripDescriptionViewModel = ViewModelProviders.of(this).get(MyTripDescriptionViewModel.class);
        pointList = new ArrayList<>();
        initializeView(view, savedInstanceState);
        sharedTripDetails();
    }

    private void initializeView(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        tvTripName = view.findViewById(R.id.trip_name);
        tvStarts = view.findViewById(R.id.trip_starts);
        tvEnds = view.findViewById(R.id.trip_ends);
        rvFriendsList = view.findViewById(R.id.rv_friends);
        LinearLayout actionContainer = view.findViewById(R.id.layout_action_container);
        rvFriendsList.invalidate();
        rvFriendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFriendsList.setHasFixedSize(false);
        rvFriendsList.setItemAnimator(new DefaultItemAnimator());
        rvFriendsList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        if (isArchived) {
            actionContainer.setVisibility(View.GONE);
        } else {
            actionContainer.setVisibility(View.VISIBLE);
        }
        btnCancelTrip = view.findViewById(R.id.btn_cancel_trip);
        btnAddMore = view.findViewById(R.id.btn_add_more);
        IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        deleteIcon = iconFactory.fromResource(R.drawable.delete_marker);
        dot = iconFactory.fromResource(R.drawable.ic_dot_55);
        LinearLayout layoutTripStart = view.findViewById(R.id.layout_trip_start_time);
        LinearLayout layoutTripEnd = view.findViewById(R.id.layout_trip_end_time);
        layoutTripStart.setVisibility(View.GONE);
        layoutTripEnd.setVisibility(View.GONE);
        startMarker = iconFactory.fromResource(R.mipmap.start_trip_marker);
        waypointMarker = iconFactory.fromResource(R.mipmap.waypoint_marker);
        endMarker = iconFactory.fromResource(R.mipmap.end_trip_marker);
        hazardIcon = iconFactory.fromResource(R.drawable.hazard_60);
        if (isEditable) {
            btnAddMore.setVisibility(View.VISIBLE);
            //actionContainer.setVisibility(View.VISIBLE);
        } else {
            btnAddMore.setVisibility(View.GONE);
        }

        btnAddMore.setVisibility(View.GONE);
        btnCancelTrip.setVisibility(View.GONE);
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

    private void loadAddMoreFragment(MyTripDescriptionModel myTripDescriptionModel) {
        Fragment fragment = new ShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", sharedTripId);
        bundle.putBoolean(Constant.KEY_IS_SHARE, false);
        bundle.putParcelable(Constant.KEY_PARCEL_MY_TRIP_DESCRIPTION, myTripDescriptionModel.getData());
        bundle.putBoolean("is_from_success", false);
        bundle.putString("org_trip_id", tripId);
        bundle.putString("trip_sharing_id", sharedTripId);
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.description_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    private void sharedTripDetails() {
        navigationOptionInterface.showProgressDialog(true);
        myTripDescriptionViewModel.getSharedTripDetails(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), Integer.valueOf(sharedTripId)).observe(this, myTripDescriptionModel -> {
            if (myTripDescriptionModel != null) {
                //Logger.d(myTripDescriptionModel);
                setupMap(myTripDescriptionModel);
            }else {
                navigationOptionInterface.showProgressDialog(false);
            }
        });
    }

    private void getLocation() {
        ApiService apiService = new ApiCaller();
        apiService.getTripLocation(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                Integer.parseInt(tripId), "current", new ResponseCallback<GetTripLocationModel>() {
                    @Override
                    public void onSuccess(GetTripLocationModel data) {
                        if (data != null && data.getList() != null) {
                            if (data.getList().get(0).getLocationStatus().equals("started")) {
                                isSnackbarShowed = false;
                                if (userMarker != null) {
                                    map.removeAnnotation(userMarker);
                                    userMarker = map.addMarker(new MarkerOptions()
                                            .position(new LatLng(data.getList().get(0).getLocation().getLat(), data.getList().get(0).getLocation().getLon())).icon(dot));
                                } else {
                                    userMarker = map.addMarker(new MarkerOptions()
                                            .position(new LatLng(data.getList().get(0).getLocation().getLat(), data.getList().get(0).getLocation().getLon())).icon(dot));
                                }
                            } else if (data.getList().get(0).getLocationStatus().equals("finished")) {
                                if (!isSnackbarShowed) {
                                    try {
                                        Snackbar.make(tvTripName, getResources().getString(R.string.text_alert_stop_location), Snackbar.LENGTH_SHORT)
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                .show();
                                        isSnackbarShowed = true;

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                if (userMarker != null) {
                                    map.removeAnnotation(userMarker);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable th) {
                        //Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("SimpleDateFormat")
    private void setupMap(MyTripDescriptionModel myTripDescriptionModel) {
        model = myTripDescriptionModel;
        tripSharingId = Integer.parseInt(myTripDescriptionModel.getData().getTripSharingId());
        tvTripName.setText(myTripDescriptionModel.getData().getTripName());
        try {
            mapView.getMapAsync(mapboxMap -> {
                map = mapboxMap;
                mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);
                mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
                    mapboxMap.getUiSettings().setAttributionEnabled(false);
                    mapboxMap.getUiSettings().setLogoEnabled(false);
                    trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                    trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
                    /*CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(myTripDescriptionModel.getData().getStartpoint().getLat(), myTripDescriptionModel.getData().getStartpoint().getLongX()))
                            .zoom(9)
                            .tilt(30)
                            .build();
                    mapboxMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), Constant.TOTAL_TIME_INTERVAL);*/

                    LatLngBounds latLngBounds = new LatLngBounds.Builder()
                            .include(new LatLng(myTripDescriptionModel.getData().getStartpoint().getLat(), myTripDescriptionModel.getData().getStartpoint().getLongX()))
                            .include(new LatLng(myTripDescriptionModel.getData().getEndpoint().getLat(), myTripDescriptionModel.getData().getEndpoint().getLongX()))
                            .build();

                    mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50),Constant.TOTAL_TIME_INTERVAL);

                    originCoord = new LatLng(myTripDescriptionModel.getData().getStartpoint().getLat(), myTripDescriptionModel.getData().getStartpoint().getLongX());
                    destinationCoord = new LatLng(myTripDescriptionModel.getData().getEndpoint().getLat(), myTripDescriptionModel.getData().getEndpoint().getLongX());
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                    if (myTripDescriptionModel.getData().getWayPoints().size() > 0) {
                        for (int i = 0; i < myTripDescriptionModel.getData().getWayPoints().size(); i++) {
                            LatLng waypointLatLng = new LatLng(myTripDescriptionModel.getData().getWayPoints().get(i).getLat(), myTripDescriptionModel.getData().getWayPoints().get(i).getLongX());
                            Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                            pointList.add(wayPoint);
                            addWaypointtMarker(wayPoint);
                        }
                        addStartPointMarker(originPosition);
                        addDestinationMarker(destinationPosition);
                        getRoute(originPosition, destinationPosition, pointList);
                    } else {
                        addStartPointMarker(originPosition);
                        addDestinationMarker(destinationPosition);
                        getRoute(originPosition, destinationPosition, null);
                    }

                    tvStarts.setText(myTripDescriptionModel.getData().getStartpoint().getAddress());
                    tvEnds.setText(myTripDescriptionModel.getData().getEndpoint().getAddress());
                    if (myTripDescriptionModel.getData().getMarkers() != null) {
                        for (MyTripDescriptionModel.DataBean.MarkersBean markersBean : myTripDescriptionModel.getData().getMarkers()) {
                            if (markersBean.getMarkType() == 1) {
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                        .setIcon(safeIcon);
                            } else if (markersBean.getMarkType() == 2) {
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                        .setIcon(landmarkIcon);
                            } else if (markersBean.getMarkType() == 3) {
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                        .setIcon(deleteIcon);
                                mapboxMap.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius() / 1000, 64));
                            } else if (markersBean.getMarkType() == 4) {

                                if (markersBean.getLocations() != null && markersBean.getLocations().size() > 0) {
                                    for (MyTripDescriptionModel.DataBean.MarkersBean.LocationsBean locationsBean : markersBean.getLocations()) {
                                        map.addMarker(new MarkerOptions()
                                                .position(new LatLng(locationsBean.getLat(), locationsBean.getLongX()))
                                                .icon(hazardIcon));

                                        latLngs.add(new LatLng(locationsBean.getLat(), locationsBean.getLongX()));
                                    }
                                    map.addPolygon(new PolygonOptions()
                                            .addAll(latLngs)
                                            .fillColor(Color.YELLOW)
                                            .alpha(0.2f));
                                }
                            }
                        }
                    }
                    if (myTripDescriptionModel.getData().getFriendAttend() != null && myTripDescriptionModel.getData().getFriendAttend().size() > 0) {
                        initAdapter(myTripDescriptionModel);
                    }
                });
            });

        } catch (IllegalArgumentException e) {
            ErrorFragment errorFragment = new ErrorFragment();
            Bundle bundle = new Bundle();
            bundle.putString("error_msg", e.getMessage());
            errorFragment.setArguments(bundle);
            errorFragment.setCancelable(false);
            errorFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
        }
        navigationOptionInterface.showProgressDialog(false);
    }

    private void initAdapter(MyTripDescriptionModel myTripDescriptionModel) {
        mAdapter = new TripAttendingAdapter(RouteApplication.getInstance().getApplicationContext(), isRemovable);
        mAdapter.setItems(myTripDescriptionModel.getData().getFriendAttend());
        mAdapter.setListener(this);
        rvFriendsList.setAdapter(mAdapter);
    }

    private void addStartPointMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .icon(startMarker));
    }

    private void addWaypointtMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .icon(waypointMarker));
    }

    private void addDestinationMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .icon(endMarker));
    }

    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        myTripDescriptionViewModel.getFinalRoute(origin, destination, wayPoints).observe(this, directionsRoute -> {
            if (directionsRoute != null) {
                drawOptimizedRoute(directionsRoute);
            }

        });
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mapView.onStart();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                getLocation();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        mapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @SuppressLint("SimpleDateFormat")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotoAddMoreUser(AddMoreObject addMoreObject) {
        if (addMoreObject.isCancel()) {
            ConfirmationCancelFragment confirmationCancelFragment = new ConfirmationCancelFragment();
            Bundle bundle = new Bundle();
            bundle.putString("trip_sharing_id", String.valueOf(tripSharingId));
            bundle.putParcelable("parcel", item);
            confirmationCancelFragment.setArguments(bundle);
            confirmationCancelFragment.show(getChildFragmentManager(), null);
        } else {
            if(!isEditable){
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getString(R.string.text_trip_message), Toast.LENGTH_SHORT).show();

            }else {
                if (model.getData().getTripSharedStatus().equals("new")) {
                    Date currentDate = null;
                    Date endDate = null;
                    try {
                        Calendar c = Calendar.getInstance();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
                        String strDate = sdf.format(c.getTime());
                        currentDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(strDate);
                        endDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(model.getData().getEndTime());

                        if (currentDate.compareTo(endDate) > 0) {
                            Snackbar.make(btnAddMore, getResources().getString(R.string.text_previous_trip), Snackbar.LENGTH_SHORT)
                                    .show();
                        } else {
                            loadAddMoreFragment(model);
                        }
                    } catch (ParseException | NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getString(R.string.text_trip_message), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void removeUser(MyTripDescriptionModel.DataBean.FriendAttendBean item) {
        CancelUserModel cancelUserModel = new CancelUserModel();
        List<Integer> listOfUserId = new ArrayList<>();
        listOfUserId.add(Integer.parseInt(item.getId()));
        cancelUserModel.setUsers(listOfUserId);
        cancelUserModel.setTripSharingId(tripSharingId);
        ApiService apiService = new ApiCaller();
        apiService.cancelUserOnTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), cancelUserModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                mAdapter.remove(item);
            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }

    @Override
    public void onItemClicked(MyTripDescriptionModel.DataBean.FriendAttendBean item) {
        removeUser(item);
        //Toast.makeText(getActivity(), "kkkk",Toast.LENGTH_SHORT).show();
    }
}
