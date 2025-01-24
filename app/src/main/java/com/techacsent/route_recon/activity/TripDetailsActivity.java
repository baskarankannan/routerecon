package com.techacsent.route_recon.activity;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polygon;
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
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.fragments.DirectionFragment;
import com.techacsent.route_recon.fragments.NavigationOptionFragment;
import com.techacsent.route_recon.fragments.SecurityChecklistFragment;
import com.techacsent.route_recon.fragments.ShareFragment;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.DetailsViewModel;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.techacsent.route_recon.utills.Utils.getMilesFromMeter;

public class TripDetailsActivity extends BaseActivity implements NavigationOptionInterface, FragmentActivityCommunication/*, PermissionsListener*/ {

    private MapboxMap map;
    private MapView mapView;
    private Button btnChecklist;
    private Button btnNavigation;
    private Button btnDirection;
    private TextView tvTripName;
    private TextView tvStarts;
    private TextView tvEnds;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvTime;
    private TextView tvDistance;
    private LinearLayout layoutTripInfo;

    private BasicTripDescription basicTripDescription;
    private boolean isLastTrip;
    private WaypointDescription waypointObjectList;

    private DetailsViewModel detailsViewModel;

    private Icon safeIcon;
    private Icon landmarkIcon;
    protected Icon hazardIcon;
    private Icon deleteIcon;
    private Icon startMarker;
    private Icon waypointMarker;
    private Icon endMarker;
    private Map<String, com.mapbox.mapboxsdk.annotations.Polygon> hashMap;

    private Polyline optimizedPolyline;
    private LayoutInflater layoutInflater;
    private static final int REQUEST_UPDATE_MARKER = 102;
    private KProgressHUD progressDialogFragment;
    private List<Point> pointList;
    int[] typeNetworks = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
    private Menu mMenu;

    private TrafficPlugin trafficPlugin;


    private Button sendBtn;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        hashMap = new HashMap<>();
        pointList = new ArrayList<>();
        loadProgressHud();
        setContentView(R.layout.activity_trip_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            basicTripDescription = bundle.getParcelable("parcel");
            isLastTrip = bundle.getBoolean("is_previous");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //markerObjectList = AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().fetchMarkerByTripId(basicTripDescription.getTripId());
        waypointObjectList = AppDatabase.getAppDatabase(getApplicationContext()).daoWaypoint().fetchWaypointById(basicTripDescription.getTripId());
        initializeView(savedInstanceState);
        setupMap();
        initializeListener();
    }

    private void initializeView(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        btnChecklist = findViewById(R.id.btn_security_checklist);
        sendBtn = findViewById(R.id.btn_send);
        saveBtn = findViewById(R.id.btn_save);
        btnNavigation = findViewById(R.id.btn_navigation);
        btnDirection = findViewById(R.id.btn_direction);
        tvTripName = findViewById(R.id.trip_name);
        tvStarts = findViewById(R.id.trip_starts);
        tvEnds = findViewById(R.id.trip_ends);
        tvStartTime = findViewById(R.id.trip_time_begins);
        tvEndTime = findViewById(R.id.trip_time_ends);
        tvDistance = findViewById(R.id.tv_distance);
        tvTime = findViewById(R.id.tv_expected_time);
        layoutTripInfo = findViewById(R.id.layout_trip_info);
        layoutTripInfo.setVisibility(View.VISIBLE);
        IconFactory iconFactory = IconFactory.getInstance(this);
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        hazardIcon = iconFactory.fromResource(R.drawable.hazard_60);
        deleteIcon = iconFactory.fromResource(R.drawable.delete_marker);
        startMarker = iconFactory.fromResource(R.mipmap.start_trip_marker);
        waypointMarker = iconFactory.fromResource(R.mipmap.waypoint_marker);
        endMarker = iconFactory.fromResource(R.mipmap.end_trip_marker);
        layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*if(PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_STATUS).equals("notpayable")){
            btnDirection.setEnabled(false);
            btnNavigation.setEnabled(false);
        }*/

    }

    @SuppressLint("SetTextI18n")
    private void getDistanceAndETA(Point origin, Point destination, List<Point> wayPoints) {
        detailsViewModel.getFinalRoute(origin, destination, wayPoints).observe(this, currentRoute -> {
            try {
                if (currentRoute != null) {
                    if (currentRoute.duration() != null) {
                        double hours = currentRoute.duration() / 3600;
                        double minutes = (currentRoute.duration() % 3600) / 60;
                        if (hours < 1) {
                            tvTime.setText("ETA: " + new DecimalFormat("##.##").format(minutes) + " minute");
                        } else {
                            tvTime.setText("ETA: " + new DecimalFormat("##.##").format(hours) + " hour, " + new DecimalFormat("##.##").format(minutes) + " minute");
                        }

                    }
                    if (currentRoute.distance() != null) {

                        if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("")
                        || PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("metric")
                        ) {
                            tvDistance.setText("Distance: " + new DecimalFormat("##.##").format(currentRoute.distance() / 1000) + " km");

                        }else if (PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("")
                                || PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
                        ){


                            // equation   to convert from meter to mile  = m*0.00062137119
                            tvDistance.setText("Distance: " + new DecimalFormat("##.##").format(getMilesFromMeter(currentRoute.distance())) + " mi");

                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private void initializeListener() {

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TripDetailsActivity.this, "On progress", Toast.LENGTH_LONG).show();
            }
        });


        btnDirection.setOnClickListener(v -> {
            if (PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_STATUS).equals("payable")) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_not_eligible), Toast.LENGTH_SHORT).show();
            } else {
                if (isNetworkAvailable(RouteApplication.getInstance().getApplicationContext(), typeNetworks)) {
                    Fragment securityChecklistFragment = new DirectionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("parcel", basicTripDescription);
                    securityChecklistFragment.setArguments(bundle);
                    loadFragment(securityChecklistFragment);
                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_no_internet_direction), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChecklist.setOnClickListener(v -> {
            Fragment securityChecklistFragment = new SecurityChecklistFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("trip_id", Integer.parseInt(basicTripDescription.getTripId()));
            securityChecklistFragment.setArguments(bundle);
            loadFragment(securityChecklistFragment);
            //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_feature_is_updating), Toast.LENGTH_SHORT).show();
        });
        btnNavigation.setOnClickListener(v -> {
            if (PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_STATUS).equals("payable")) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_not_eligible), Toast.LENGTH_SHORT).show();
            } else {
                if (isLastTrip) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_prevoius_trip_nav_warning), Toast.LENGTH_SHORT).show();
                } else {
                    NavigationOptionFragment navigationOptionFragment = new NavigationOptionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("trip_id", Integer.parseInt(basicTripDescription.getTripId()));
                    bundle.putDouble("lat", basicTripDescription.getStartPointLat());
                    bundle.putDouble("lonX", basicTripDescription.getStartPointLonX());
                    navigationOptionFragment.setArguments(bundle);
                    navigationOptionFragment.show(getSupportFragmentManager(), navigationOptionFragment.getTag());
                }
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callShareTripFragment(true);

            }
        });


    }

    private void setupMap() {
        try {
            Objects.requireNonNull(getSupportActionBar()).setTitle(basicTripDescription.getTripName());
            tvTripName.setText(basicTripDescription.getTripName());
            tvStarts.setText(basicTripDescription.getStartPointAddress());
            tvEnds.setText(basicTripDescription.getEndPointAddress());
            tvStartTime.setText(basicTripDescription.getBeginTime());
            tvEndTime.setText(basicTripDescription.getEndTime());
            mapView.getMapAsync(mapboxMap -> {
                map = mapboxMap;
                mapboxMap.getUiSettings().setAttributionEnabled(false);
                mapboxMap.getUiSettings().setLogoEnabled(false);
                mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
                    trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                    trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));

                /*CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(basicTripDescription.getStartPointLat(), basicTripDescription.getStartPointLonX()))
                        .zoom(9)
                        .tilt(30)
                        .bearing(180)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), Constant.TOTAL_TIME_INTERVAL);*/

                    try {
                        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                                .include(new LatLng(basicTripDescription.getStartPointLat(), basicTripDescription.getStartPointLonX()))
                                .include(new LatLng(basicTripDescription.getEndPointLat(), basicTripDescription.getEndPointLonX()))
                                .build();

                        mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), Constant.TOTAL_TIME_INTERVAL);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadRoute();
                    loadMarkerFromDB();
                    mapboxMap.setInfoWindowAdapter(marker -> {
                        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                        TextView tvAddress = view.findViewById(R.id.tv_address);
                        TextView tvRemove = view.findViewById(R.id.tv_delete);
                        TextView tvEdit = view.findViewById(R.id.tv_edit);
                        TextView tvFullAddress = view.findViewById(R.id.tv_full_address);
                        tvAddress.setText(marker.getTitle());
                        tvFullAddress.setText(marker.getTitle());
                        tvRemove.setVisibility(View.GONE);
                        if (marker.getSnippet().equals("0")) {
                            tvEdit.setVisibility(View.GONE);
                        }
                        tvEdit.setOnClickListener(v -> {
                            Intent intent = new Intent(TripDetailsActivity.this, LandmarkActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("marker_id", marker.getSnippet());
                            bundle.putLong("long_marker", marker.getId());
                            bundle.putBoolean("is_marker_from_trip", true);
                            bundle.putBoolean("is_marker_from_polygon_pin", false);
                            bundle.putInt(Constant.KEY_TRIP_ID, Integer.parseInt(basicTripDescription.getTripId()));
                            intent.putExtras(bundle);
                            startActivityForResult(intent, REQUEST_UPDATE_MARKER);
                            marker.hideInfoWindow();
                        });
                        return view;
                    });
                });
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void loadRoute() {
        addStartPointMarker();

        LatLng originCoord = new LatLng(basicTripDescription.getStartPointLat(), basicTripDescription.getStartPointLonX());
        LatLng destinationCoord = new LatLng(basicTripDescription.getEndPointLat(), basicTripDescription.getEndPointLonX());
        Point originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        Point destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        addDestinationMarker();
        if (waypointObjectList != null && waypointObjectList.getWayPointsBeanList() != null && waypointObjectList.getWayPointsBeanList().size() > 0) {
            for (WaypointModel.WayPointsBean wayPointsBean : waypointObjectList.getWayPointsBeanList()) {
                addWaypointtMarker(wayPointsBean);
            }
        }
        if (waypointObjectList != null && waypointObjectList.getTripJson() != null && waypointObjectList.getTripJson().length() > 1) {
            drawOptimizedRoute();
        } else {
            getRoute(originPosition, destinationPosition, pointList);
        }
        getDistanceAndETA(originPosition, destinationPosition, pointList);
    }

    private void loadMarkerFromDB() {
        //List<MarkerDescription> markerObjectList = AppDatabase.getAppDatabase(this).getApplicationContext()).daoMarker().fetchMarkerByTripId(dataBean.getId());
        List<MarkerDescription> markerObjectList = AppDatabase.getAppDatabase(this).daoMarker().fetchMarkerByTripId(basicTripDescription.getTripId());
        if (markerObjectList != null && markerObjectList.size() > 0) {
            for (MarkerDescription markersBean : markerObjectList) {
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
                    com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(
                            Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()),
                                    markersBean.getRadius() / 1000, 64));
                    hashMap.put(markersBean.getMarkerId(), polygon);
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

                        if(latLngs.size() > 2) {
                            com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = map.addPolygon(new PolygonOptions()
                                    .addAll(latLngs)
                                    .fillColor(Color.YELLOW)
                                    .alpha(0.3f));
                        }else if (latLngs.size() == 2){

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

    }

    public static boolean isNetworkAvailable(Context context, int[] typeNetworks) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int typeNetwork : typeNetworks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(typeNetwork);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    private void drawOptimizedRoute() {
        try {
            if (optimizedPolyline != null) {
                map.removePolyline(optimizedPolyline);
            }
            String decodedPath = new String(Base64.decode(waypointObjectList.getTripJson(), Base64.DEFAULT));
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

    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        detailsViewModel.getFinalRoute(origin, destination, wayPoints).observe(this, currentRoute -> {
            if (currentRoute != null) {
                try {
                    Timber.d(currentRoute.geometry());
                    String encodedPath = Base64.encodeToString(Objects.requireNonNull(currentRoute.geometry()).getBytes(), Base64.DEFAULT);
                    waypointObjectList.setTripJson(encodedPath);
                    AppDatabase.getAppDatabase(this).daoWaypoint().updateWaypoint(waypointObjectList);
                    drawOptimizedRoute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    private void addStartPointMarker() {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(basicTripDescription.getStartPointLat(), basicTripDescription.getStartPointLonX()))
                .title(basicTripDescription.getStartPointAddress())
                .setSnippet("0")
                .icon(startMarker));
    }

    private void addDestinationMarker() {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(basicTripDescription.getEndPointLat(), basicTripDescription.getEndPointLonX()))
                .title(basicTripDescription.getEndPointAddress())
                .setSnippet("0")
                .icon(endMarker));
    }

    private void addWaypointtMarker(WaypointModel.WayPointsBean wayPointsBean) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(wayPointsBean.getLat(), wayPointsBean.getLongX()))
                .setSnippet("0")
                .title(wayPointsBean.getAddress())
                .icon(waypointMarker));
    }

    private void sendData() {
        Intent intent = new Intent(this, CreateTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.KEY_IS_UPDATE, true);
        bundle.putParcelable("parcel", basicTripDescription);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.trip_details_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void shareFragmentToolbar( boolean isShare) {
        if(isShare) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.share_text));
        }else{

            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.text_send));

        }
    }

    @Override
    public void tripDetailsToolbar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(basicTripDescription.getTripName());
    }

    @Override
    public void navigationChooseToolbar(boolean isHide) {
        MenuItem shareTripItem = mMenu.findItem(R.id.action_share_trip);
        MenuItem editTripitem = mMenu.findItem(R.id.action_update_trip);
        if (isHide) {
            shareTripItem.setVisible(false);
            editTripitem.setVisible(false);
        } else {
            shareTripItem.setVisible(true);
            editTripitem.setVisible(true);
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


    @Override
    public void hideBottomNav(boolean isHide) {

    }

    @Override
    public void showProgressDialog(boolean isShown) {
        if (isShown) {
            //progressDialogFragment = new ProgressDialogFragment();

            progressDialogFragment.show();
        } else {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    public void fragmentToolbarbyPosition(int pos) {

    }

    @Override
    public void setTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

    }

    @Override
    public void showDone(boolean isShow) {

    }

    @Override
    public void setNavigation(boolean isFromCurrentPos) {
    }

    @Override
    public void hideToolbar(boolean isHide) {
        if (isHide) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } else {
            Objects.requireNonNull(getSupportActionBar()).show();
        }
    }

    @Override
    public void shareLocation(int tripId) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_UPDATE_MARKER) {
            if (data != null) {
                boolean isEdit = data.getBooleanExtra("is_edit", false);
                long markerId = data.getLongExtra("long_marker", 0);
                String serverMarkerID = data.getStringExtra("server_marker_id");
                int markerType = data.getIntExtra("marker_type", 0);
                double lat = data.getDoubleExtra("lat", 0);
                double lon = data.getDoubleExtra("lon", 0);
                if (!isEdit) {
                    if (markerType == 3) {
                        if (hashMap.containsKey(serverMarkerID)) {
                            Polygon polygon = hashMap.get(serverMarkerID);
                            if (polygon != null) {
                                map.removePolygon(polygon);
                            }
                        }
                    }
                    if (markerType == 4) {
                        map.clear();
                        loadMarkerFromDB();
                        loadRoute();
                    }
                    map.removeAnnotation(markerId);
                } else {
                    if (markerType == 3) {
                        if (hashMap.containsKey(serverMarkerID)) {
                            Polygon polygon = hashMap.get(serverMarkerID);
                            if (polygon != null) {
                                map.removePolygon(polygon);
                            }
                        }
                        double radius = data.getDoubleExtra("radius", 0);
                        hashMap.remove(serverMarkerID);
                        Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(lat, lon), radius / 1000, 64));
                        hashMap.put(serverMarkerID, polygon);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_trip_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share_trip:
                callShareTripFragment(false);

                return true;
            case R.id.action_update_trip:
                sendData();
                return true;
            /*case android.R.id.home:
                setBackData();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void callShareTripFragment( boolean isTripSend) {

        if (isLastTrip) {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_prevoius_trip_warning), Toast.LENGTH_SHORT).show();
        } else {
            Fragment fragment = new ShareFragment();
            Bundle bundle = new Bundle();
            bundle.putString("trip_id", basicTripDescription.getTripId());
            bundle.putBoolean(Constant.KEY_IS_SHARE, true);
            bundle.putBoolean("is_from_details", true);
            bundle.putBoolean("is_from_success", false);
            bundle.putBoolean("is_trip_send", isTripSend);
            fragment.setArguments(bundle);
            loadFragment(fragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        progressDialogFragment = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            //this.finish();
            setBackData();
        }
    }

    private void setBackData() {
        Intent intent = new Intent();
        intent.putExtra("flag", 1);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
