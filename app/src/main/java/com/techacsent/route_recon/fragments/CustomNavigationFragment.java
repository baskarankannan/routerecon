package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.feedback.FeedbackItem;
import com.mapbox.services.android.navigation.ui.v5.listeners.FeedbackListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.RouteListener;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.model.PostCrimeReportModel;
import com.techacsent.route_recon.model.PostCrimeReportResponse;
import com.techacsent.route_recon.model.SharingTripLocationResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.TripStartResponse;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomNavigationFragment extends Fragment implements OnNavigationReadyCallback,
        NavigationListener, RouteListener, ProgressChangeListener, FeedbackListener {
    private NavigationView navigationView;
    private int tripId;
    private boolean isShare;
    private boolean isTripRoute;
    private View layoutAction;

    private DirectionsRoute currentRoute;
    private double latitude;
    private double longitude;
    //private String navigationType;
    /*private LocationService locationService;*/
    private Handler handler;
    private Location currentLocation;

    private WaypointDescription waypointObjectList;
    private BasicTripDescription basicTripDescription;
    private List<MarkerDescription> markerDescriptionList;

    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon deleteIcon;

    private Point originPosition;
    private Point destinationPosition;
    private int reportType;

    public CustomNavigationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isShare = getArguments().getBoolean("is_share");
            tripId = getArguments().getInt("trip_id");
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
            isTripRoute = getArguments().getBoolean("is_trip_route");
            //navigationType = getArguments().getString("navigation_type");
        }
        basicTripDescription = AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoTripBasic().fetchSingleTripById(String.valueOf(tripId));
        markerDescriptionList = AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoMarker().fetchMarkerByTripId(String.valueOf(tripId));
        if (markerDescriptionList != null) {
            Timber.d(String.valueOf(markerDescriptionList.size()));
        }
        if (isTripRoute) {
            waypointObjectList = AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoWaypoint().fetchWaypointById(String.valueOf(tripId));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateNightMode();
        //locationService = new LocationService(Objects.requireNonNull(getActivity()).getApplicationContext());
        ImageView btnPost = view.findViewById(R.id.fab_post);
        ImageView btnSendSos = view.findViewById(R.id.fab_send_mail);
        IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        deleteIcon = iconFactory.fromResource(R.drawable.delete_marker);
        layoutAction = view.findViewById(R.id.layout_action);
        btnPost.setOnClickListener(v -> {
            PostReportFragment postReportFragment = new PostReportFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("lat", currentLocation.getLatitude());
            bundle.putDouble("lonX", currentLocation.getLongitude());
            bundle.putInt("trip_id", tripId);
            postReportFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_custom_nav_content, postReportFragment, postReportFragment.getClass().getSimpleName())
                    .addToBackStack(postReportFragment.getClass().getSimpleName()).commit();

        });

        btnSendSos.setOnClickListener(v -> {
            SosFragment sosFragment = new SosFragment();
            sosFragment.show(getChildFragmentManager(), null);

        });
        LatLng originCoord = new LatLng(latitude, longitude);
        LatLng destinationCoord = new LatLng(basicTripDescription.getEndPointLat(), basicTripDescription.getEndPointLonX());
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        navigationView = view.findViewById(R.id.navigationView);
        navigationView.onCreate(savedInstanceState);
        navigationView.initialize(this);
    }

    @Override
    public void onNavigationReady(boolean isRunning) {
        List<Point> pointList = new ArrayList<>();
        if (waypointObjectList != null && waypointObjectList.getWayPointsBeanList() != null) {
            for (int i = 0; i < waypointObjectList.getWayPointsBeanList().size(); i++) {
                LatLng waypointLatLng = new LatLng(waypointObjectList.getWayPointsBeanList().get(i).getLat(), waypointObjectList.getWayPointsBeanList().get(i).getLongX());
                Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                pointList.add(wayPoint);
            }
            getRoute(originPosition, destinationPosition, pointList);
        } else {
            getRoute(originPosition, destinationPosition, null);
        }
        MapboxMap map = Objects.requireNonNull(navigationView.retrieveNavigationMapboxMap()).retrieveMap();
        /*map.setStyle(Style.SATELLITE_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                loadCustomMarker(map);
            }
        });*/
        loadCustomMarker(map);

    }

    private void loadCustomMarker(MapboxMap map) {
        if (markerDescriptionList != null && markerDescriptionList.size() > 0) {
            for (MarkerDescription markersBean : markerDescriptionList) {
                if (markersBean.getMarkType() == 1) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                            .title(markersBean.getAddress())
                            .setSnippet(markersBean.getMarkerId())
                            .icon(safeIcon));
                } else if (markersBean.getMarkType() == 2) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                            .title(markersBean.getAddress())
                            .setSnippet(markersBean.getMarkerId())
                            .icon(landmarkIcon));
                } else if (markersBean.getMarkType() == 3) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                            .title(markersBean.getAddress())
                            .setSnippet(markersBean.getMarkerId())
                            .icon(deleteIcon));
                    map.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius() / 1000, 64));
                }
            }
        }
    }

    private void updateLocationAndShareTrip() {
        startStoptTrip("start");
        handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                saveUserLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    private void startStoptTrip(String status) {
        ApiService apiService = new ApiCaller();
        apiService.tripStart(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                tripId, status, new ResponseCallback<TripStartResponse>() {
                    @Override
                    public void onSuccess(TripStartResponse data) {
                        Timber.e(data.getSuccess().getMessage());
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserLocation(double lat, double lon) {
        ApiService apiService = new ApiCaller();
        apiService.shareTripLocation(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), tripId, lat, lon, new ResponseCallback<SharingTripLocationResponse>() {
            @Override
            public void onSuccess(SharingTripLocationResponse data) {
                Timber.e("%s", data.getData().getLatitude());
                Timber.e("%s", data.getData().getLongitude());
            }

            @Override
            public void onError(Throwable th) {
                try {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        assert Mapbox.getAccessToken() != null;
        NavigationRoute.Builder builder = NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .destination(destination);
        if (wayPoints != null) {
            for (Point point : wayPoints)
                builder.addWaypoint(point);
        }
        builder.build().getRoute(new Callback<DirectionsResponse>() {
            @SuppressLint("TimberArgCount")
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                Timber.e("Response code: %s", response.code(), response);
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }
                currentRoute = response.body().routes().get(0);
                startNavigation();

            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable t) {
                Timber.e("Error: %s", t.getMessage());

            }
        });
    }

    private void sendSoS() {
        SendSosModel sendSosModel = new SendSosModel();
        sendSosModel.setLat(currentLocation.getLatitude());
        sendSosModel.setLongX(currentLocation.getLongitude());
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = spf.format(currentTime);
        sendSosModel.setSosTime(dateString);
        ApiService apiService = new ApiCaller();
        apiService.sendSosEmail(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), sendSosModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendSos(Boolean bool) {
        sendSoS();
    }

    private void startNavigation() {
        try {
            if (currentRoute == null) {
                return;
            }
            NavigationViewOptions options = NavigationViewOptions.builder()
                    .directionsRoute(currentRoute)
                    .routeListener(this)
                    .shouldSimulateRoute(true)
                    .navigationListener(this)
                    .progressChangeListener(this)
                    .feedbackListener(this)
                    .build();
            navigationView.startNavigation(options);
            navigationView.startCamera(currentRoute);
            layoutAction.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCancelNavigation() {
        if (isShare) {
            startStoptTrip("stop");
        }
        updateWasNavigationStopped(true);
        updateWasInTunnel(false);
        Timber.d("onCancelNavigation");
        navigationView.stopNavigation();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onNavigationFinished() {
        if (isShare) {
            startStoptTrip("stop");
        }
        Timber.d("onNavigationFinished");
        navigationView.stopNavigation();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onNavigationRunning() {
        //Toast.makeText(getActivity(), "onNavigationRunning", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        navigationView.onStart();
        EventBus.getDefault().register(this);
        if (isShare) {
            updateLocationAndShareTrip();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        navigationView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            navigationView.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        navigationView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        navigationView.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigationView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navigationView.onDestroy();
    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {
        currentLocation = location;
        boolean isInTunnel = routeProgress.inTunnel();
        boolean wasInTunnel = wasInTunnel();
        if (isInTunnel) {
            if (!wasInTunnel) {
                updateWasInTunnel(true);
                updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        } else {
            if (wasInTunnel) {
                updateWasInTunnel(false);
                updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            }
        }
        List<WaypointModel.WayPointsBean> wayPointsBeansList = new ArrayList<>();
        if(waypointObjectList!=null && waypointObjectList.getWayPointsBeanList()!=null && waypointObjectList.getWayPointsBeanList().size()>0){
            for(WaypointModel.WayPointsBean wayPointsBean : waypointObjectList.getWayPointsBeanList()){
                if(wayPointsBean.getType()==3){
                    wayPointsBeansList.add(wayPointsBean);
                }
            }



        }
    }

    @Override
    public boolean allowRerouteFrom(Point offRoutePoint) {
        return true;
    }

    @Override
    public void onOffRoute(Point offRoutePoint) {

    }

    @Override
    public void onRerouteAlong(DirectionsRoute directionsRoute) {
        navigationView.startCamera(directionsRoute);
    }

    @Override
    public void onFailedReroute(String errorMessage) {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onArrival() {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.arrival_text), Toast.LENGTH_SHORT).show();
        navigationView.stopNavigation();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onFeedbackOpened() {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "onFeedbackOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFeedbackCancelled() {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "onFeedbackCancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFeedbackSent(FeedbackItem feedbackItem) {
        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), feedbackItem.getFeedbackType() + feedbackItem.getDescription(), Toast.LENGTH_SHORT).show();
        reportType = reportType(feedbackItem.getFeedbackType());
        PostReportAlertFragment postReportAlertFragment = new PostReportAlertFragment();
        postReportAlertFragment.show(getChildFragmentManager(), null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postRouteIssueOnServer(String issue) {
        postReport(issue);
    }

    private void postReport(String issue) {
        PostCrimeReportModel postCrimeReportModel = new PostCrimeReportModel();
        postCrimeReportModel.setServerTripId(tripId);
        postCrimeReportModel.setReportType(reportType);
        postCrimeReportModel.setLat(currentLocation.getLatitude());
        postCrimeReportModel.setLongX(currentLocation.getLongitude());
        postCrimeReportModel.setRadius(0);
        postCrimeReportModel.setAddress(Utils.getAddress(currentLocation.getLatitude(), currentLocation.getLongitude(), false, RouteApplication.getInstance().getApplicationContext()));
        postCrimeReportModel.setFullAddress(Utils.getAddress(currentLocation.getLatitude(), currentLocation.getLongitude(), true, RouteApplication.getInstance().getApplicationContext()));
        postCrimeReportModel.setDescription(issue);
        ApiService apiService = new ApiCaller();
        apiService.postCrimeReport(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), postCrimeReportModel, new ResponseCallback<PostCrimeReportResponse>() {
            @Override
            public void onSuccess(PostCrimeReportResponse data) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean wasInTunnel() {
        return PreferenceManager.getBool("was_in_tunnel", false);
    }

    private void updateWasInTunnel(boolean wasInTunnel) {
        PreferenceManager.updateValue("was_in_tunnel", wasInTunnel);
    }

    private void updateCurrentNightMode(int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        getActivity().recreate();
    }

    private boolean wasNavigationStopped() {
        return PreferenceManager.getBool("was_nav_stopped");
    }

    public void updateWasNavigationStopped(boolean wasNavigationStopped) {
        PreferenceManager.updateValue("was_nav_stopped", wasNavigationStopped);
    }


    private void updateNightMode() {
        if (wasNavigationStopped()) {
            updateWasNavigationStopped(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            getActivity().recreate();
        }
    }


    private int reportType(String feedbackType) {
        switch (feedbackType) {
            case "not_allowed":
                return 0;
            case "road_closed":
                return 1;
            case "report_traffic":
                return 2;
            case "confusing_instruction":
                return 3;
            case "other_map_issue":
                return 4;
            case "routing_error":
                return 5;
            default:
                return 0;
        }
    }

}
