package com.techacsent.route_recon.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import io.github.rupinderjeet.kprogresshud.KProgressHUD;
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
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.feedback.FeedbackItem;
import com.mapbox.services.android.navigation.ui.v5.listeners.FeedbackListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.ui.v5.listeners.RouteListener;
import com.mapbox.services.android.navigation.ui.v5.map.NavigationMapboxMap;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigationOptions;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.CreateReportModel;
import com.techacsent.route_recon.event_bus_object.StartServiceObj;
import com.techacsent.route_recon.fragments.PostReportAlertFragment;
import com.techacsent.route_recon.fragments.PostReportFragment;
import com.techacsent.route_recon.fragments.SosFragment;
import com.techacsent.route_recon.interfaces.LoginActivityInterface;
import com.techacsent.route_recon.interfaces.OnDialogViewItemClickListener;
import com.techacsent.route_recon.interfaces.OnDialogViewReceivedSendRequestListener;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.PostCrimeReportModel;
import com.techacsent.route_recon.model.PostCrimeReportResponse;
import com.techacsent.route_recon.model.ReportInRangeResponse;
import com.techacsent.route_recon.model.SharingTripLocationResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.TripStartResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.FeedbackModel;
import com.techacsent.route_recon.model.request_body_model.ReportInRangeModel;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.service.LocationServiceV2;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.ShowDialog;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.TrackViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;

public class RouteNavigationActivity extends AppCompatActivity implements OnNavigationReadyCallback,
        NavigationListener, RouteListener, ProgressChangeListener, FeedbackListener, LoginActivityInterface{

    private NavigationView navigationView;
    private MapboxMap map;
    private TextView tvSuccess,tvSpeed;
    private int tripId;
    private boolean isShare;
    private boolean isTripRoute;
    private double latitude;
    private double longitude;
    private String navigationtype;
    private View layoutAction;

    private WaypointDescription waypointObjectList;
    private List<MarkerDescription> markerDescriptionList;
    private Point originPosition;
    private Point destinationPosition;
    private DirectionsRoute currentRoute;
    private List<Marker> markerList;

    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon deleteIcon;
    private Icon hazardIcon;
    private Icon traffic;
    private Icon police;
    private Icon accident;
    private Icon hzard;
    private Icon construction;
    private Icon closure;
    private Icon checkpoint;
    private Icon socialDisturbness;
    private Icon weather;
    private Icon fuel;
    private Icon mapIssue;

    private Handler handler;
    private Handler handler_speed;
    private Handler disHandler;
    private Handler reportHandler;
    private Handler messagePanelHandler = new Handler();
    Runnable disRunnable = null;
    Runnable runnable = null;
    Runnable speedRunnable = null;
    Runnable rangeRunnable = null;
    private Location currentLocation;

    private boolean isInsideAlertDone = false;
    private boolean isOutsideAlertDone = false;
    private boolean isAnyDangerZoneTouched = false;

    private List<MarkerDescription> wayPointsBeansList;
    private TextToSpeech textToSpeech;
    private List<ReportInRangeResponse.ListBean> beanList;
    private Set<ReportInRangeResponse.ListBean> listBeanSet;
    private LayoutInflater layoutInflater;
    private KProgressHUD progressDialogFragment;


    //for waypoint
    protected Polyline optimizedPolyline;
    protected String encodedPath;

    protected List<Point> pointList;
    protected Icon waypointMarker;


    private TrackViewModel trackViewModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_notification);
        beanList = new ArrayList<>();
        listBeanSet = new HashSet<>();
        markerList = new  ArrayList<>();
        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //updateNightMode();

        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isShare = bundle.getBoolean("is_share");
            tripId = bundle.getInt("trip_id");
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("lonX");
            isTripRoute = bundle.getBoolean("is_trip_route");
            navigationtype = bundle.getString("navigation_type");
        }
        try {
            textToSpeech = new TextToSpeech(this, status -> {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        tvSuccess = findViewById(R.id.tv_success);
        tvSpeed = findViewById(R.id.tv_speed);
        IconFactory iconFactory = IconFactory.getInstance(this);
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        deleteIcon = iconFactory.fromResource(R.drawable.delete_marker);
        hazardIcon = iconFactory.fromResource(R.drawable.hazard_60);
        traffic = iconFactory.fromResource(R.drawable.traffic_icon);
        police = iconFactory.fromResource(R.drawable.police_new);
        accident = iconFactory.fromResource(R.drawable.accident);
        hzard = iconFactory.fromResource(R.drawable.hazard);
        construction = iconFactory.fromResource(R.drawable.construction);
        closure = iconFactory.fromResource(R.drawable.closure);
        checkpoint = iconFactory.fromResource(R.drawable.checkpoint);
        socialDisturbness = iconFactory.fromResource(R.drawable.social_disturbance);
        weather = iconFactory.fromResource(R.drawable.weather);
        fuel = iconFactory.fromResource(R.drawable.map_fuel);
        mapIssue = iconFactory.fromResource(R.drawable.map_issue);

        waypointMarker = iconFactory.fromResource(R.mipmap.waypoint_marker);

        layoutAction = findViewById(R.id.layout_action);
        navigationView = findViewById(R.id.navigationView);
        FloatingActionButton btnSendSos = findViewById(R.id.fab_send_mail);
        FloatingActionButton btnPost = findViewById(R.id.fab_post);
        FloatingActionButton btnAddressSearch = findViewById(R.id.fab_address_search_in_navigation);
        navigationView.onCreate(savedInstanceState);
        navigationView.initialize(this);
        //updateNightMode();
        BasicTripDescription basicTripDescription = AppDatabase.getAppDatabase(this.getApplicationContext()).daoTripBasic().fetchSingleTripById(String.valueOf(tripId));
        markerDescriptionList = AppDatabase.getAppDatabase(this.getApplicationContext()).daoMarker().fetchMarkerByTripId(String.valueOf(tripId));
        LatLng originCoord = new LatLng(latitude, longitude);
        LatLng destinationCoord = new LatLng(basicTripDescription.getEndPointLat(), basicTripDescription.getEndPointLonX());
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        //originPosition = Point.fromLngLat(-117.61296898, 47.64287731);
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        //destinationPosition = Point.fromLngLat(-116.243890, 43.618259);
        if (markerDescriptionList != null) {
            Timber.d(String.valueOf(markerDescriptionList.size()));
        }
        if (isTripRoute) {
            waypointObjectList = AppDatabase.getAppDatabase(this.getApplicationContext()).daoWaypoint().fetchWaypointById(String.valueOf(tripId));
        }
        loadProgressHud();

        btnSendSos.setOnClickListener(v -> {
            SosFragment sosFragment = new SosFragment();
            sosFragment.show(getSupportFragmentManager(), null);
        });

        btnPost.setOnClickListener(v -> {
            PostReportFragment postReportFragment = new PostReportFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putDouble("lat", currentLocation.getLatitude());
            bundle1.putDouble("lonX", currentLocation.getLongitude());
            bundle1.putInt("trip_id", tripId);
            postReportFragment.setArguments(bundle1);
            getSupportFragmentManager().beginTransaction().add(R.id.notification_content, postReportFragment, postReportFragment.getClass().getSimpleName())
                    .addToBackStack(postReportFragment.getClass().getSimpleName()).commit();
        });

        btnAddressSearch.setOnClickListener(v -> {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);
            startActivityForResult(intent, 1);


        });

        wayPointsBeansList = new ArrayList<>();
        if (markerDescriptionList != null && markerDescriptionList.size() > 0) {
            for (MarkerDescription wayPointsBean : markerDescriptionList) {
                if (wayPointsBean.getMarkType() == 3) {
                    wayPointsBeansList.add(wayPointsBean);
                }
            }
        }

        if (Utils.checkTrackingStatus()) {


            Toast.makeText(RouteNavigationActivity.this, "Tracker is already running", Toast.LENGTH_SHORT).show();


        } else {

            ShowDialog.showUpdateAppDialog(

                    RouteNavigationActivity.this,
                    "data",
                    new OnDialogViewItemClickListener() {
                        @Override
                        public void onClickYes() {





                            ShowDialog.showSaveTrackedInfoDialog(RouteNavigationActivity.this,
                                    new OnDialogViewReceivedSendRequestListener() {


                                        @Override
                                        public void onAccept(String tripId) {


                                            PreferenceManager.updateValue(Constant.KEY_IS_TRIP_NAME, tripId);


                                            PreferenceManager.updateValue(Constant.KEY_IS_TRACKING_STARTED, true);


                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                                Objects.requireNonNull(startForegroundService(new Intent(RouteNavigationActivity.this, LocationServiceV2.class)));

                                            } else
                                                Objects.requireNonNull(startService(new Intent(RouteNavigationActivity.this, LocationServiceV2.class)));



                                        }

                                        @Override
                                        public void onDenied() {

                                        }
                                    }

                            );

                        }

                        @Override
                        public void onClickNo() {

                        }
                    }
            );
        }
    }

    private void loadProgressHud() {
        progressDialogFragment = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setDimAmount(0.5f)
                .setLabel("Loading...");
    }

    private MarkerDescription getClosestWaypoint() {
        if (currentLocation != null) {
            /*double distance = Utils.getDistance(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    wayPointsBeansList.get(0).getLat(), wayPointsBeansList.get(0).getLongX());*/
            double distance = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
                    .distanceTo(new LatLng(wayPointsBeansList.get(0).getLat(), wayPointsBeansList.get(0).getLongX()));
            int pos = 0;
            for (int i = 0; i < wayPointsBeansList.size(); i++) {
                /*double cDistance = Utils.getDistance(currentLocation.getLatitude(), currentLocation.getLongitude(),
                        wayPointsBeansList.get(i).getLat(), wayPointsBeansList.get(i).getLongX());*/
                double cDistance = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
                        .distanceTo(new LatLng(wayPointsBeansList.get(i).getLat(), wayPointsBeansList.get(i).getLongX()));
                if (cDistance < distance) {
                    pos = i;
                    distance = cDistance;
                }
            }
            return wayPointsBeansList.get(pos);
        }
        return null;
    }

    @Override
    public void onNavigationReady(boolean isRunning) {

        pointList = new ArrayList<>();
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
        map = Objects.requireNonNull(navigationView.retrieveNavigationMapboxMap()).retrieveMap();

        //getReportIssueFromServer();

        loadCustomMarker();
        NavigationMapboxMap map1 = navigationView.retrieveNavigationMapboxMap();
        map1.updateIncidentsVisibility(true);
        map1.updateTrafficVisibility(true);

        map1.updateLocationLayerRenderMode(RenderMode.COMPASS);
        /* map.getUiSettings().setCompassEnabled(true);*/

        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), map1.isTrafficVisible()+"",Toast.LENGTH_SHORT).show();

        map.setInfoWindowAdapter(marker -> {
            @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.layout_issue_feedback, null);
            TextView tvAddress = view.findViewById(R.id.tv_address);
            LinearLayout feedbackLayout = view.findViewById(R.id.layout_feedback);
            FloatingActionButton btnLike = view.findViewById(R.id.fab_like);
            FloatingActionButton btnUnlike = view.findViewById(R.id.fab_unlike);
            tvAddress.setText(marker.getTitle());

            if (marker.getSnippet().substring(marker.getSnippet().length() - 1).equals("i")) {
                feedbackLayout.setVisibility(View.VISIBLE);
                btnLike.setOnClickListener(v -> {
                    String[] parts = marker.getSnippet().split("-");
                    String reportId = parts[0];
                    sendFeedback(Integer.parseInt(reportId), 1);
                    //map.removeMarker(marker);
                    marker.hideInfoWindow();
                });

                btnUnlike.setOnClickListener(v -> {
                    String[] parts = marker.getSnippet().split("-");
                    String reportId = parts[0];
                    sendFeedback(Integer.parseInt(reportId), 2);
                    //map.removeMarker(marker);
                    marker.hideInfoWindow();
                });
            }

            return view;
        });

    }

    private void sendFeedback(int reportID, int status) {

        FeedbackModel feedbackModel = new FeedbackModel();
        feedbackModel.setReportId(reportID);
        feedbackModel.setFeedbackType(status);
        ApiService apiService = new ApiCaller();
        apiService.sendfeedbackOverIssue(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), feedbackModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_input_sent_successfully), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable th) {

            }
        });
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
                        if (th.getMessage().equals("Adjust Route Time")) {
                            Snackbar.make(layoutAction, getString(R.string.text_adjust_route_time), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Close", view -> {

                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        } else {
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        }

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
                    //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    th.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void updateLocationAndShareTrip() {
        startStoptTrip("start");
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentLocation != null) {
                    saveUserLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
                    handler.postDelayed(this, 5000);
                }

            }
        };
        handler.postDelayed(runnable, 5000);
    }

    private void updateSpeed() {
        handler_speed = new Handler();

        speedRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentLocation != null) {

                    handler_speed.postDelayed(this, 3000);

                    int speed =  (int) (currentLocation.getSpeed() * 2.2369);

                    tvSpeed.setText(speed +" "+"mph");
                }

            }
        };
        handler_speed.postDelayed(speedRunnable, 3000);
    }


    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {

        assert Mapbox.getAccessToken() != null;

        NavigationRoute.Builder builder = null;

        if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("") ||
                PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("metric")) {

            builder = NavigationRoute.builder(this)
                    .accessToken(Mapbox.getAccessToken())
                    .origin(origin)
                    .profile(navigationtype)
                    .voiceUnits(DirectionsCriteria.METRIC)
                    .destination(destination);
            if (wayPoints != null) {
                for (Point point : wayPoints)
                    builder.addWaypoint(point);
            }
        }else if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")){

             builder = NavigationRoute.builder(this)
                    .accessToken(Mapbox.getAccessToken())
                    .origin(origin)
                    .profile(navigationtype)
                    .voiceUnits(DirectionsCriteria.IMPERIAL)
                    .destination(destination);
            if (wayPoints != null) {
                for (Point point : wayPoints)
                    builder.addWaypoint(point);
            }

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

              //  currentRoute = response.body().routes().get(0);
                drawOptimizedRoute(currentRoute);


                startNavigation();
                //getReportIssueFromServer();
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable t) {
                Timber.e("Error: %s", t.getMessage());
            }
        });




    }

     

    private void startNavigation() {

        MapboxNavigationOptions mapboxNavigationOptions = MapboxNavigationOptions.builder()
                .isFromNavigationUi(true)
                .enableFasterRouteDetection(true)
                .build();

        //MapboxMapMatching mapboxMapMatching = MapboxMapMatching.builder().

        try {
            if (currentRoute == null) {
                return;
            }
            NavigationViewOptions options = NavigationViewOptions.builder()
                    .directionsRoute(currentRoute)
                    .routeListener(this)
                    .shouldSimulateRoute(false)
                    .navigationListener(this)
                    .progressChangeListener(this)
                    .feedbackListener(this)
                    .waynameChipEnabled(true)
                    .navigationOptions(mapboxNavigationOptions)
                    .build();
            navigationView.startNavigation(options);
            navigationView.startCamera(currentRoute);
            navigationView.retrieveFeedbackButton().hide();
            layoutAction.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCustomMarker() {
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
                    map.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()),
                            markersBean.getRadius() / 1000, 64));
                } else if (markersBean.getMarkType() == 4) {
                    if (markersBean.getHazardMarkerPointList() != null) {
                        List<LatLng> latLngs = new ArrayList<>();
                        for (LocationsBeanForLocalDb locationsBean : markersBean.getHazardMarkerPointList()) {
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(locationsBean.getLat(), locationsBean.getLongX()))
                                    .title(Utils.getAddress(locationsBean.getLat(), locationsBean.getLongX(), true, RouteApplication.getInstance().getApplicationContext()))
                                    .setSnippet(markersBean.getMarkerId())
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
    public void onFeedbackOpened() {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "onFeedbackOpened", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFeedbackCancelled() {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "onFeedbackCancelled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFeedbackSent(FeedbackItem feedbackItem) {
        //reportType = reportType(feedbackItem.getFeedbackType());
        PostReportAlertFragment postReportAlertFragment = new PostReportAlertFragment();
        postReportAlertFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onCancelNavigation() {
        if (isShare) {
            startStoptTrip("stop");
        }

        trackViewModel.liveDataTripRequest(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                PreferenceManager.getString(Constant.KEY_IS_TRIP_NAME),
                PreferenceManager.getString(Constant.KEY_TRACKED_TIME),
                PreferenceManager.getString(Constant.KEY_TRACKED_SPEED),
                PreferenceManager.getString(Constant.KEY_TRACKED_DISTANCE),
                "0.0",
                "0.0",
                "0.0",
                "0.0").observe(this, data -> {



            Log.e("RouteNavAct", "Stop service");


        });

        PreferenceManager.updateValue(Constant.KEY_IS_TRACKING_STARTED, false);


        this.stopService(new Intent(this, LocationServiceV2.class));

        navigationView.stopNavigation();
        updateWasNavigationStopped(true);
        updateWasInTunnel(false);
        RouteNavigationActivity.this.finish();
    }

    @Override
    public void onNavigationFinished() {


        trackViewModel.liveDataTripRequest(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                PreferenceManager.getString(Constant.KEY_IS_TRIP_NAME),
                PreferenceManager.getString(Constant.KEY_TRACKED_TIME),
                PreferenceManager.getString(Constant.KEY_TRACKED_SPEED),
                PreferenceManager.getString(Constant.KEY_TRACKED_DISTANCE),
                "0.0",
                "0.0",
                "0.0",
                "0.0").observe(this, data -> {



            Log.e("RouteNavAct", "Stop service");


                });

        PreferenceManager.updateValue(Constant.KEY_IS_TRACKING_STARTED, false);


        Objects.requireNonNull(this.stopService(new Intent(this, LocationServiceV2.class)));


        if (isShare) {
            startStoptTrip("stop");
        }
        navigationView.stopNavigation();
        updateWasNavigationStopped(true);
        updateWasInTunnel(false);
        RouteNavigationActivity.this.finish();


    }

    @Override
    public void onNavigationRunning() {


    }

    @Override
    public boolean allowRerouteFrom(Point offRoutePoint) {
        return false;
    }

    @Override
    public void onOffRoute(Point offRoutePoint) {

    }

    @Override
    public void onRerouteAlong(DirectionsRoute directionsRoute) {
        //navigationView.startCamera(directionsRoute);
    }

    @Override
    public void onFailedReroute(String errorMessage) {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArrival() {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.arrival_text), Toast.LENGTH_SHORT).show();
        navigationView.stopNavigation();
        updateWasNavigationStopped(true);
        updateWasInTunnel(false);
        RouteNavigationActivity.this.finish();
    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {
        currentLocation = location;
        Timber.d(String.valueOf(currentLocation.getLatitude()));
        boolean isInTunnel = routeProgress.inTunnel();
        boolean wasIntunnel = wasInTunnel();
        if (isInTunnel) {
            if (!wasIntunnel) {
                updateWasInTunnel(true);
                updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        } else {
            if (wasIntunnel) {
                updateWasInTunnel(false);
                updateCurrentNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.onStart();
        EventBus.getDefault().register(this);
        updateSpeed();
        if (isShare) {
            updateLocationAndShareTrip();
        }
        getIssueAndDisplayonMap();
        if (wayPointsBeansList != null && wayPointsBeansList.size() > 0) {
            disHandler = new Handler();
            disRunnable = new Runnable() {
                @SuppressLint("TimberArgCount")
                @Override
                public void run() {
                    if (wayPointsBeansList != null && wayPointsBeansList.size() > 0) {
                        MarkerDescription markerDescription = getClosestWaypoint();
                        if (markerDescription != null) {
                            double dis = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
                                    .distanceTo(new LatLng(markerDescription.getLat(), markerDescription.getLongX()));
                            Timber.e(String.valueOf(dis));
                            Timber.e(String.valueOf(markerDescription.getRadius()));


                            if (dis < markerDescription.getRadius()) {
                                if (!isInsideAlertDone) {
                                    textToSpeech.speak(getString(R.string.text_announcement_inside_danger_zone), TextToSpeech.QUEUE_FLUSH, null);
                                    isInsideAlertDone = true;
                                    isOutsideAlertDone = false;
                                    isAnyDangerZoneTouched = true;
                                    /*Toast.makeText(RouteApplication.getInstance().getApplicationContext(),
                                            "Danger zone", Toast.LENGTH_SHORT).show();*/
                                }


                                //wayPointsBeansList.remove(markerDescription);
                            }
                            if (dis > markerDescription.getRadius()) {
                                if (!isOutsideAlertDone && isAnyDangerZoneTouched) {
                                    textToSpeech.speak(getString(R.string.text_announcement_outside_danger_zone), TextToSpeech.QUEUE_FLUSH, null);
                                    isOutsideAlertDone = true;
                                    isInsideAlertDone = false;
                                    wayPointsBeansList.remove(markerDescription);
                                }
                            }
                            disHandler.postDelayed(this, 5000);
                        }
                    }
                }
            };
            disHandler.postDelayed(disRunnable, 5000);
        }

    }

    private void getIssueAndDisplayonMap() {
        reportHandler = new Handler();
        rangeRunnable = new Runnable() {
            @Override
            public void run() {
                getReportIssueFromServer();
                reportHandler.postDelayed(this, 10000);
            }
        };
        reportHandler.postDelayed(rangeRunnable, 10000);
    }

    private void getReportIssueFromServer() {
        try {
            ReportInRangeModel reportInRangeModel = new ReportInRangeModel();
            reportInRangeModel.setLat(currentLocation.getLatitude());
            reportInRangeModel.setLongX(currentLocation.getLongitude());
            reportInRangeModel.setRadius("1");
            ApiService apiService = new ApiCaller();
            apiService.getReportInRange(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), reportInRangeModel, new ResponseCallback<ReportInRangeResponse>() {
                @Override
                public void onSuccess(ReportInRangeResponse data) {
                    beanList.addAll(data.getList());
                    listBeanSet.clear();
                    listBeanSet.addAll(beanList);
                    for (ReportInRangeResponse.ListBean listBean : listBeanSet) {
                        /*if (markerList != null && markerList.size() > 0) {
                            for (Marker marker : markerList) {
                                map.removeMarker(marker);
                                markerList.remove(marker);
                            }
                        }*/
                        switch (listBean.getReportType()) {
                            case 1:
                                loadSingleIssueMarker(listBean, traffic);
                                break;
                            case 2:
                                loadSingleIssueMarker(listBean, police);
                                break;
                            case 3:
                                loadSingleIssueMarker(listBean, accident);
                                break;
                            case 4:
                                loadSingleIssueMarker(listBean, hzard);
                                break;
                            case 5:
                                loadSingleIssueMarker(listBean, construction);
                                break;
                            case 6:
                                loadSingleIssueMarker(listBean, closure);
                                break;
                            case 7:
                                loadSingleIssueMarker(listBean, checkpoint);
                                break;
                            case 8:
                                loadSingleIssueMarker(listBean, socialDisturbness);
                                break;
                            case 9:
                                loadSingleIssueMarker(listBean, weather);
                                break;
                            case 10:
                                loadSingleIssueMarker(listBean, fuel);
                                break;
                            case 11:
                                loadSingleIssueMarker(listBean, mapIssue);
                                break;
                        }
                        /*map.addMarker(new MarkerOptions()
                                .position(new LatLng(listBean.getLat(), listBean.getLongX()))
                                .title(listBean.getDescription())
                                .setSnippet(listBean.getReportId() + "-i")
                                .icon(routeIssue));*/
                    }
                }

                @Override
                public void onError(Throwable th) {
                    th.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSingleIssueMarker(ReportInRangeResponse.ListBean listBean, Icon icon) {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(listBean.getLat(), listBean.getLongX()))
                .title(listBean.getDescription())
                .setSnippet(listBean.getReportId() + "-i")
                .icon(icon));
        markerList.add(marker);
    }

    @Override
    public void onBackPressed() {
        if (navigationView != null) {
            navigationView.onBackPressed();
        }

        super.onBackPressed();
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
    public void onPause() {
        super.onPause();
        navigationView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        navigationView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigationView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigationView.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        if (disHandler != null) {
            disHandler.removeCallbacks(disRunnable);
        }
        if (reportHandler != null) {
            reportHandler.removeCallbacks(rangeRunnable);
        }
        if (handler_speed != null) {
            handler_speed.removeCallbacks(speedRunnable);
        }
        currentLocation = null;

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        navigationView.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendSos(Boolean bool) {
        sendSoS();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postRouteIssueOnServer(CreateReportModel createReportModel) {
        postReport(createReportModel.getIssue(), createReportModel.getType());
    }

    private void postReport(String issue, int type) {
        PostCrimeReportModel postCrimeReportModel = new PostCrimeReportModel();
        postCrimeReportModel.setServerTripId(tripId);
        postCrimeReportModel.setReportType(type);
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
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                tvSuccess.setVisibility(View.VISIBLE);
                tvSuccess.setText(data.getSuccess().getMessage().toUpperCase());
                messagePanelHandler.postDelayed(() -> tvSuccess.setVisibility(View.GONE), 3000);
            }

            @Override
            public void onError(Throwable th) {
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                tvSuccess.setBackgroundColor(getResources().getColor(R.color.error_panel_color));
                tvSuccess.setVisibility(View.VISIBLE);
                tvSuccess.setText(R.string.text_can_not_send_sos);
                messagePanelHandler.postDelayed(() -> tvSuccess.setVisibility(View.GONE), 3000);

            }
        });
    }

    private boolean wasInTunnel() {
       /* Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(context.getString(R.string.was_in_tunnel), false);*/
        return PreferenceManager.getBool("was_in_tunnel");
    }

    private void updateWasInTunnel(boolean wasInTunnel) {
        PreferenceManager.updateValue("was_in_tunnel", wasInTunnel);
    }

    private void updateCurrentNightMode(int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        recreate();
    }

    private boolean wasNavigationStopped() {
        return PreferenceManager.getBool("was_navigation_stopped");
    }

    public void updateWasNavigationStopped(boolean wasNavigationStopped) {
        PreferenceManager.updateValue("was_navigation_stopped", wasNavigationStopped);
    }

    private void updateNightMode() {
        if (wasNavigationStopped()) {
            updateWasNavigationStopped(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME);
            recreate();
        }
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        if (isShown) {
            progressDialogFragment.show();
        } else {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            /*Place googlePlace = PlaceAutocomplete.getPlace(RouteApplication.getInstance().getApplicationContext(), data);
            double gLat = googlePlace.getLatLng().latitude;
            double gLon = googlePlace.getLatLng().longitude;
            CharSequence gPlace = googlePlace.getAddress();
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(gLat, gLon))
                    .zoom(16)
                    .build();

            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 2000);
            if (destinationMarker != null) {
                map.removeMarker(destinationMarker);
            }
            destinationMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(gLat, gLon))
                    .title(String.valueOf(gPlace))
                    .snippet("a")
                    .icon(iconMyloc));*/

            Place place = Autocomplete.getPlaceFromIntent(data);

            Log.e("Place", place.toString());

            LatLng waypointLatLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());

            pointList.add(wayPoint);

            getRoute(originPosition, destinationPosition, pointList);


            double gLat = Objects.requireNonNull(place.getLatLng()).latitude;
            double gLon = place.getLatLng().longitude;


            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(gLat, gLon))
                    .title(Utils.getAddress(gLat, gLon, false, this))
                    .snippet("0")
                    .icon(waypointMarker));


            // waypointHashMap.put(gLat, marker.getId());

            // wayPointsListBeans.add(wayPointsBean);



          /*  double gLat = Objects.requireNonNull(place.getLatLng()).latitude;
            double gLon = place.getLatLng().longitude;
            CharSequence gPlace = place.getAddress();
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(gLat, gLon))
                    .zoom(16)
                    .build();

            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 2000);
            if (destinationMarker != null) {
                map.removeMarker(destinationMarker);
            }
            destinationMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(gLat, gLon))
                    .title(String.valueOf(gPlace))
                    .snippet("a")
                    .icon(markerIcon));

            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), gLat + " " + gLon + "", Toast.LENGTH_SHORT).show();
*/

        }

    }

    /*protected void updateRoute() {

        pointList.clear();

        for (int i = 0; i < wayPointsListBeans.size(); i++) {
            LatLng waypointLatLng = new LatLng(wayPointsListBeans.get(i).getLat(), wayPointsListBeans.get(i).getLongX());
            Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
            pointList.add(wayPoint);
        }
       // addStartPointMarker(originPosition);
        //addDestinationMarker(destinationPosition);

        if (pointList.size() > 0) {
            getRoute(originPosition, destinationPosition, pointList);
        } else {
            getRoute(originPosition, destinationPosition, null);
        }

    }*/





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



   /* private LatLng[] convertLineStringToLatLng(String geometry) {
        LineString lineString = LineString.fromPolyline(geometry, PRECISION_6);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        return points;
    }*/
}
