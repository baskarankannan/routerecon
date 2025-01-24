package com.techacsent.route_recon.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.LandmarkActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.ChangeMapObject;
import com.techacsent.route_recon.event_bus_object.ChangeMapView;
import com.techacsent.route_recon.event_bus_object.StartServiceObj;
import com.techacsent.route_recon.interfaces.OnDialogViewReceivedSendRequestListener;
import com.techacsent.route_recon.model.ElevationResponse;
import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.retrofit_api.ApiOkhttpCaller;
import com.techacsent.route_recon.retrofit_api.RouteHttpCallback;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.service.LocationServiceV2;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.ShowDialog;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.SharedMapViewModel;
import com.techacsent.route_recon.view_model.SharedViewModel;
import com.techacsent.route_recon.view_model.TrackViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.os.Looper.getMainLooper;
import static com.techacsent.route_recon.R.id.container_map_style;


public class TrackFragment extends Fragment  {

    private MapboxMap map;
    private MapView mapView;
    private LinearLayout layoutMenuContent;

    private LinearLayout linearLayoutTrackInfo;

    private Style mapStyle ;

    private MapData mapData;

    private SharedViewModel sharedViewModel;

    private SharedMapViewModel sharedMapViewModel;





    private LocationService locationService;
    private LatLng southEastLatLng;
    private LatLng northWest;
    private int viewportWidth;
    private int viewportHeight;
    private LayoutInflater layoutInflater;
    private static final int REQUEST_UPDATE_MARKER = 102;

    private Map<String, com.mapbox.mapboxsdk.annotations.Polygon> dangerPolygonHashMap;
    private List<LatLng> OUTER_POINTS = new ArrayList<>();
    private List<Integer> listElevation;


    private TrafficPlugin trafficPlugin;
    private int elevation = 0;
    private boolean fromBtn = false;

    private LocationComponent locationComponent;

    private ImageView fabLocation, fabAddressSearch, fabMore, imageViewTripHistory;

    // Variables needed to add the location engine
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;


    private Fragment trackHistory;

    private TextView tvDistance, tvTime, tvSpeed;

    private Button buttonTrackStartOrStop;

    private Boolean isTrackingStarted = false;

    private FrameLayout bottomSheetView;
    private Fragment bottomFragment;

    private TrackViewModel trackViewModel;


    String distance;
    String duration;
    String speed;
    String tripName;

    BuildingPlugin buildingPlugin;


    private Icon markerIcon;

    private Marker destinationMarker;


    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public TrackFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        trackViewModel = ViewModelProviders.of(requireActivity()).get(TrackViewModel.class);
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        sharedMapViewModel = ViewModelProviders.of(requireActivity()).get(SharedMapViewModel.class);


        return inflater.inflate(R.layout.fragment_track, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dangerPolygonHashMap = new HashMap<>();
        listElevation = new ArrayList<>();


        locationService = new LocationService(RouteApplication.getInstance().getApplicationContext());

       // locationService = new LocationService(requireActivity().getApplicationContext());


        isTrackingStarted = Utils.checkTrackingStatus();

        Log.e("TrackFragment", "isTrackingStarted "+ isTrackingStarted );

        initializeView(savedInstanceState, view);
        initializeMap();
        initializeListener();
        enableLocation();

    }



    @SuppressLint({"SetTextI18n", "MissingPermission"})
    private void enableLocation() {

        mapView.getMapAsync(mapboxMap -> {



            map = mapboxMap;
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setLogoEnabled(false);
            mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
                trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));

                enableLocationComponent(style);



                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                        .zoom(16)
                        .tilt(30)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 2000);
                mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);
                //tvLatLong.setText(locationComponent.getLastKnownLocation().getLatitude() + locationComponent.getLastKnownLocation().getLongitude() + "");

                buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);

                if (PreferenceManager.getBool(Constant.KEY_IS_3D_MAP_SELECTED)) {

                    buildingPlugin.setVisibility(true);

                } else {


                    buildingPlugin.setVisibility(false);


                }



            });




        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(requireActivity())) {
            locationComponent = map.getLocationComponent();

          //  locationComponent.activateLocationComponent(getActivity(), loadedMapStyle);

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);




            locationComponent.setLocationComponentEnabled(true);
          // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);


           // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();



        } else {
            // permissionsManager = new PermissionsManager(this);
            //permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeView(Bundle savedInstanceState, View view) {
        mapView = view.findViewById(R.id.mapViewTrack);
        mapView.onCreate(savedInstanceState);
        @SuppressLint("UseRequireInsteadOfGet") IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
        ImageView ivRefresh = view.findViewById(R.id.iv_refresh);
        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvDistance = view.findViewById(R.id.tv_distance);
        tvTime = view.findViewById(R.id.tv_time);
        tvSpeed = view.findViewById(R.id.tv_speed);
        buttonTrackStartOrStop = view.findViewById(R.id.button_track_start_or_stop);

        linearLayoutTrackInfo = view.findViewById(R.id.linearLayout_record_data);

        layoutMenuContent = view.findViewById(R.id.layout_option);

        markerIcon = iconFactory.fromResource(R.drawable.my_pos_marker_icon);


        fabAddressSearch = view.findViewById(R.id.fab_address_search);
        fabLocation = view.findViewById(R.id.fab_my_location);
        fabMore = view.findViewById(R.id.fab_option);
        imageViewTripHistory = view.findViewById(R.id.imageViewTripHistory);

      //  imageViewTripHistory.setVisibility(View.VISIBLE);

        bottomSheetView = view.findViewById(container_map_style);


        ivRefresh.setVisibility(View.GONE);
        ivAddUpdate.setVisibility(View.GONE);
        tvTitle.setText("TRACK");

    }


    private void initializeListener() {


        buttonTrackStartOrStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTrackingStarted) {


                    tripName = PreferenceManager.getString(Constant.KEY_IS_TRIP_NAME);

                    Log.e("tripName ", " " + tripName);


                    if(!tripName.isEmpty()) {

                        trackViewModel.liveDataTripRequest(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                                tripName,
                                duration,
                                speed,
                                distance,
                                "22.2",
                                "90.03",
                                "22.34",
                                "34.455").observe(requireActivity(), data -> {



                                    Log.e("Trackfragment", "Stop service");



                                });

                        //stop tracking service
                        buttonTrackStartOrStop.setText(R.string.Start);
                        buttonTrackStartOrStop.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.olive_color));

                        tvDistance.setText("0");
                        tvTime.setText("0");
                        tvSpeed.setText("0");

                        PreferenceManager.updateValue(Constant.KEY_IS_TRACKING_STARTED, false);
                        PreferenceManager.updateValue(Constant.KEY_IS_TRIP_NAME, "");

                        requireActivity().stopService(new Intent(getActivity(), LocationServiceV2.class));

                        isTrackingStarted = false;

                    }else{

                        Log.e("tripName ", " " + "empty");


                    }





                } else {

                    //start tracking service

                    ShowDialog.showSaveTrackedInfoDialog(

                            (AppCompatActivity) getActivity(),
                            new OnDialogViewReceivedSendRequestListener() {


                                @Override
                                public void onAccept(String tripId) {

                                    tripName = tripId;

                                    PreferenceManager.updateValue(Constant.KEY_IS_TRIP_NAME, tripName);

                                    buttonTrackStartOrStop.setText(R.string.Stop);
                                    buttonTrackStartOrStop.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));

                                    isTrackingStarted = true;

                                    PreferenceManager.updateValue(Constant.KEY_IS_TRACKING_STARTED, true);

                                    tvDistance.setText("0");
                                    tvSpeed.setText("0");
                                    tvTime.setText("0");


                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                        requireActivity().startForegroundService(new Intent(getActivity(), LocationServiceV2.class));

                                    } else
                                        requireActivity().startService(new Intent(getActivity(), LocationServiceV2.class));



                                }

                                @Override
                                public void onDenied() {

                                }
                            }

                    );




                }
            }
        });

        imageViewTripHistory.setOnClickListener(v -> {


            trackHistory = new TrackedTripHistoryFragment();

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, trackHistory, trackHistory.getClass()
                    .getSimpleName()).addToBackStack(PolygonMapFragment.class.getClass()
                    .getSimpleName())
                    .commit();



          /*  getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_content, trackHistory, trackHistory.getClass().getSimpleName()).show(trackHistory)
                    .commit();*/


        });

        fabLocation.setOnClickListener(v -> {


            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                if (locationComponent != null && locationComponent.getLastKnownLocation() != null) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude()))
                            .zoom(18)
                            .tilt(30)
                            .build();
                    map.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), 2000);
                }
            }


        });

        fabAddressSearch.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
// Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(getActivity());
            startActivityForResult(intent, 1);

            /*try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .build(Objects.requireNonNull(getActivity()));
                startActivityForResult(intent, 1);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }*/
        });

        fabMore.setOnClickListener(v -> {


           // linearLayoutTrackInfo.setVisibility(View.GONE);

            bottomSheetView.setVisibility(View.VISIBLE);
            if (bottomFragment != null) {
                PreferenceManager.updateValue(Constant.KEY_IS_FROM_MAP_UI, false);
                requireActivity().getSupportFragmentManager().beginTransaction().show(bottomFragment).commit();
            } else {
                initBottomPicker();
            }
             layoutMenuContent.setVisibility(View.GONE);


            Point originPosition = Point.fromLngLat(-117.61296898, 47.64287731);
            Point destinationPosition = Point.fromLngLat(-116.243890, 43.618259);


        });

    }

    private void initializeMap() {

        final TrackFragment context = this;

        mapView.getMapAsync(mapboxMap -> {
            mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
                map = mapboxMap;
                mapboxMap.getUiSettings().setAttributionEnabled(false);
                mapboxMap.getUiSettings().setLogoEnabled(false);


                /*if (fromBtn) {
                    ScaleBarPlugin scaleBarPlugin = new ScaleBarPlugin(mapView, mapboxMap);
                    ScaleBarOptions scaleBarOptions = new ScaleBarOptions(getActivity()).
                            setTextColor(R.color.white)
                            .setTextSize(45f)
                            .setBarHeight(15f)
                            .setBorderWidth(5f)
                            .setMetricUnit(true)
                            .setRefreshInterval(15)
                            .setMarginTop(1500f)
                            .setMarginLeft(30f)
                            .setTextBarMargin(15f);

                    scaleBarPlugin.create(scaleBarOptions);
                }*/


                getElevation();


                trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                        .zoom(16)
                        .tilt(30)
                        .build();

                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 1000);
                mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);
                viewportWidth = (int) mapboxMap.getWidth();
                viewportHeight = (int) mapboxMap.getHeight();
                southEastLatLng = mapboxMap.getProjection().fromScreenLocation(new PointF(viewportWidth, viewportHeight));
                northWest = mapboxMap.getProjection().fromScreenLocation(new PointF(0, 0));

                //loadInitMarker();
               /* mapboxMap.setInfoWindowAdapter(marker -> {
                    @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                    TextView tvAddress = view.findViewById(R.id.tv_address);
                    TextView tvRemove = view.findViewById(R.id.tv_delete);
                    tvRemove.setVisibility(View.GONE);
                    TextView tvEdit = view.findViewById(R.id.tv_edit);
                    TextView tvFullAddress = view.findViewById(R.id.tv_full_address);
                    tvFullAddress.setText(marker.getTitle());
                    tvAddress.setText(marker.getTitle());
                    if (marker.getSnippet() == null) {
                        tvEdit.setVisibility(View.GONE);
    *//*
                        Toast.makeText(getActivity(), "Save The Marker First!", Toast.LENGTH_LONG).show();
    *//*
                    } else {

                        if (marker.getSnippet().equals("a")) {
                            tvEdit.setVisibility(View.GONE);
                        }
                    }


                    tvEdit.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), LandmarkActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("marker_id", marker.getSnippet());
                        bundle.putLong("long_marker", marker.getId());
                        bundle.putBoolean("is_marker_from_trip", false);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, REQUEST_UPDATE_MARKER);
                        marker.hideInfoWindow();
                    });

                    return view;
                });*/
                mapboxMap.addOnMapLongClickListener(point -> {
                    return false;

                });




                //buildingPlugin.setVisibility(true);



               /* mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        mapStyle = style;


                       *//* ScaleBarPlugin scaleBarPlugin = new ScaleBarPlugin(mapView, mapboxMap);

                        // Create a ScaleBarOptions object to use the Plugin's default styling
                        scaleBarPlugin.create(new ScaleBarOptions(getActivity()));*//*
                    }
                });*/


            });
        });


    }

    private void getElevation() {
        String url = "https://api.mapbox.com/v4/mapbox.mapbox-terrain-v2/" +
                "tilequery/" + locationService.getLongitude() + "," + locationService.getLatitude()
                + ".json?&access_token=" + Constant.MAPBOX_API_KEY;

        ApiOkhttpCaller.okHttpGet(url, getActivity(), new RouteHttpCallback(getActivity(), new RouteHttpCallback.RouteResponse() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call call, String networkResponse, int responseCode, String responseString, String body) throws IOException {
                Gson gson = new Gson();
                ElevationResponse elevationResponse = new ElevationResponse();
                elevationResponse = gson.fromJson(body, ElevationResponse.class);
                if (listElevation != null) {
                    listElevation.clear();
                }
                if (elevationResponse != null) {
                    for (ElevationResponse.FeaturesBean featuresBean : elevationResponse.getFeatures()) {
                        listElevation.add(featuresBean.getProperties().getEle());
                    }
                }
                if (locationService != null) {
                    try {
                        elevation = Collections.max(listElevation);
                        if (PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                        } else {

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onResume() {
        super.onResume();

        mapView.onResume();


        getActivity().registerReceiver(receiver, new IntentFilter("FILTER"));

        isTrackingStarted = Utils.checkTrackingStatus();


        if (isTrackingStarted) {

            tvDistance.setText(PreferenceManager.getString(Constant.KEY_TRACKED_DISTANCE));
            tvTime.setText(PreferenceManager.getString(Constant.KEY_TRACKED_TIME));
            tvSpeed.setText(PreferenceManager.getString(Constant.KEY_TRACKED_SPEED));

            buttonTrackStartOrStop.setText(getString(R.string.Stop));
            buttonTrackStartOrStop.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
        }




        /*mapView.getMapAsync(map -> map.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                buildingPlugin = new BuildingPlugin(mapView, map, style);



            }
        }));*/


    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);

        mapView.onPause();



    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    // receive data from LocationServiceV2 class
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {



            distance = intent.getStringExtra("DistanceCovered");
            duration = intent.getStringExtra("Duration");
            speed = intent.getStringExtra("Speed");


            tvDistance.setText(distance);

            tvSpeed.setText(speed);

            tvTime.setText(duration);


        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

        EventBus.getDefault().unregister(this);

    }

    private void initBottomPicker() {

        PreferenceManager.updateValue(Constant.KEY_IS_FROM_MAP_UI, false);

        bottomFragment = new MapBottomFragment();
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.add(container_map_style, bottomFragment);
        ft.commitNow();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    // bottomFragment.setStateOpen(false);
                    EventBus.getDefault().post("down");
                } else {
                    EventBus.getDefault().post("up");
                    //bottomFragment.setStateOpen(true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Timber.d(String.valueOf(slideOffset));

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeMapStyle(ChangeMapObject floatNumber) {
        if (floatNumber.getType() == 1) {

            map.setStyle(Style.MAPBOX_STREETS, style -> {

                // tvLatlong.setTextColor(getResources().getColor(R.color.black, null));


                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 0);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_mapbox_streets));

                mapData = new MapData(0, getResources().getString(R.string.mapbox_style_mapbox_streets));
                sharedMapViewModel.select(mapData);
            });

        } else if (floatNumber.getType() == 2) {

            // tvLatlong.setTextColor(getResources().getColor(R.color.white, null));

            map.setStyle(Style.SATELLITE, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 1);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite));

                mapData = new MapData(1, getResources().getString(R.string.mapbox_style_satellite));
                sharedMapViewModel.select(mapData);
            });


        } else if (floatNumber.getType() == 3) {
            //tvLatlong.setTextColor(getResources().getColor(R.color.white, null));
            map.setStyle(Style.SATELLITE_STREETS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 2);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite_streets));

                mapData = new MapData(2, getResources().getString(R.string.mapbox_style_satellite_streets));
                sharedMapViewModel.select(mapData);
            });

        } else if (floatNumber.getType() == 4) {
            map.setStyle(Style.OUTDOORS, style -> {
                // tvLatlong.setTextColor(getResources().getColor(R.color.black, null));
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 3);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_outdoors));

                mapData = new MapData(3, getResources().getString(R.string.mapbox_style_outdoors));
                sharedMapViewModel.select(mapData);
            });
        } else if (floatNumber.getType() == 5) {
            /*bottomSheetView.setVisibility(View.GONE);
            layoutMenuContent.setVisibility(View.VISIBLE);*/
            if (bottomFragment.isVisible()) {
                requireActivity().getSupportFragmentManager().beginTransaction().hide(bottomFragment).commit();
            }
            layoutMenuContent.setVisibility(View.VISIBLE);


        } else if (floatNumber.getType() == 6) {


            /*map.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    buildingPlugin = new BuildingPlugin(mapView, map, style);


                    if (PreferenceManager.getBool(Constant.KEY_IS_3D_MAP_SELECTED)) {


                        buildingPlugin.setVisibility(true);

                    } else {


                        buildingPlugin.setVisibility(false);


                    }


                }
            });*/


           /* map.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {

                *//*if(buildingPlugin != null) {
                    buildingPlugin = new BuildingPlugin(mapView, map, style);

                }
                if (PreferenceManager.getBool(Constant.KEY_IS_3D_MAP_SELECTED)) {


                        buildingPlugin.setVisibility(true);

                    } else {


                        buildingPlugin.setVisibility(false);


                    }*//*



            });*/






        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setChangeMapView(ChangeMapView changeMapView) {

        if  (changeMapView.getView().equals("track")) {

            if (PreferenceManager.getBool(Constant.KEY_IS_3D_MAP_SELECTED)) {


                map.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {


                    if (buildingPlugin == null) {

                        buildingPlugin = new BuildingPlugin(mapView, map, style);

                    }
                    buildingPlugin.setVisibility(true);

                });



            } else {
                map.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {

                    if (buildingPlugin == null) {
                        buildingPlugin = new BuildingPlugin(mapView, map, style);
                    }
                    buildingPlugin.setVisibility(false);


                });

            }

        } 


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null) {
            if (resultCode == RESULT_OK && requestCode == 1) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                double gLat = Objects.requireNonNull(place.getLatLng()).latitude;
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
            }

        }

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
    }


    @SuppressLint("MissingPermission")
    private void initLocationEngine() {

        locationEngine = LocationEngineProvider.getBestLocationEngine(requireContext());

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    private class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<TrackFragment> activityWeakReference;

        LocationChangeListeningActivityLocationCallback(TrackFragment activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            TrackFragment activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

// Create a Toast which displays the new location's coordinates
              /*  Toast.makeText(activity, String.format(activity.getString(R.string.share),
                        String.valueOf(result.getLastLocation().getLatitude()),
                        String.valueOf(result.getLastLocation().getLongitude())),
                        Toast.LENGTH_SHORT).show();*/

                Log.e("Tac","found "+ result.getLastLocation().getLatitude() );

              // Pass the new location to the Maps SDK's LocationComponent
                if (activity.map != null && result.getLastLocation() != null) {
                    activity.map.getLocationComponent().forceLocationUpdate(result.getLastLocation());

                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    } else {
                        if (locationComponent != null && locationComponent.getLastKnownLocation() != null) {
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new LatLng(result.getLastLocation().getLatitude(), result.getLastLocation().getLongitude()))
                                    .zoom(18)
                                    .tilt(30)
                                    .build();
                            map.animateCamera(CameraUpdateFactory
                                    .newCameraPosition(position), 2000);
                        }
                    }
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {

         /*  LocationChangeListeningActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }*/
        }
    }


}


