package com.techacsent.route_recon.fragments;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;

/*import com.mapbox.pluginscalebar.ScaleBarOptions;
import com.mapbox.pluginscalebar.ScaleBarPlugin;*/
import com.mapbox.pluginscalebar.ScaleBarOptions;
import com.mapbox.pluginscalebar.ScaleBarPlugin;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.LandmarkActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.ChangeMapObject;
import com.techacsent.route_recon.event_bus_object.CoordinateUnitObject;
import com.techacsent.route_recon.event_bus_object.DangerMarkerObj;
import com.techacsent.route_recon.event_bus_object.DistanceUnitObject;
import com.techacsent.route_recon.event_bus_object.HazardNameObject;
import com.techacsent.route_recon.event_bus_object.SyncMarkerObject;
import com.techacsent.route_recon.event_bus_object.TrafficObject;
import com.techacsent.route_recon.event_bus_object.UiManageObject;
import com.techacsent.route_recon.interfaces.BottomNavigationVisibilityListener;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnDialogViewReceivedSendRequestListener;
import com.techacsent.route_recon.model.ElevationResponse;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.ReceivedShareTripRequest;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.request_body_model.TripMarkerGetModel;
import com.techacsent.route_recon.model.unitconversion.Deg2UTM;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiOkhttpCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.retrofit_api.RouteHttpCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.service.RouteReconIntentService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.ShowDialog;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.SharedMapViewModel;
import com.techacsent.route_recon.view_model.SharedViewModel;
import com.techacsent.route_recon.worker.AddMarkerWorker;
import com.mapbox.mapboxsdk.maps.*;
import com.techacsent.route_recon.worker.LoadAllTripWorker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import okhttp3.Call;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static java.lang.String.format;


public class MapFragment extends Fragment implements PermissionsListener, View.OnClickListener/*, LocationEngineListener*/ {

    private MapboxMap map;
    private MapView mapView;
    private LinearLayout layoutMenuContent;
    private ImageView fabAddressSearch;
    private ImageView fabLocation;
    private ImageView fabMore;
    private ImageView imageViewHazardConfirm;
    private ImageView imageViewHazardCancel;
    private LinearLayout selectionLayout;
    private LinearLayout linearLayoutHazardConfrim;

    private Button btnSafe;
    private Button btnLandmark;
    private Button btnHazard;
    private Button btnDanger;
    private ImageButton menuBtn;

    private ImageView ivRefresh;
    private TextView tvDone;
    private TextView tvLatlong;

    private PermissionsManager permissionsManager;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon dangerIcon;
    private Icon hazardIcon;
    private Icon iconMyloc;
    private int type = 1;
    private LocationService locationService;
    private LatLng southEastLatLng;
    private LatLng northWest;
    private int viewportWidth;
    private int viewportHeight;
    private CreateTripMarkerModelClass createTripMarkerModelClass;
    private LayoutInflater layoutInflater;
    private static final int REQUEST_UPDATE_MARKER = 102;
    private boolean isMarkerAdd = true;
    private SharedViewModel sharedViewModel;

    private SharedMapViewModel sharedMapViewModel;

    private String hazardArea = "";
    private String hazardLenght = "";

    private MapData mapData;
    private Handler handler;
    Runnable runnable;

    private Map<String, com.mapbox.mapboxsdk.annotations.Polygon> dangerPolygonHashMap;
    private List<LatLng> OUTER_POINTS = new ArrayList<>();
    private List<Integer> listElevation;

    private FrameLayout bottomSheetView;
    private Fragment bottomFragment;
    private Fragment hazardInfoBottomFragment;
    private TrafficPlugin trafficPlugin;
    private Marker destinationMarker;
    private int elevation = 0;
    private boolean fromBtn = false;

    private CallDrawer callDrawer;
    private CallPolygonMap callPolygonMap;
    private BottomNavigationVisibilityListener bottomNavigationVisibilityListener;

    String markerId = "";

    Gson gson = new Gson();

    double longitude = 0.0;
    double latitude = 0.0;

    private Boolean isFirstTimeBottomSheetInitialize = false;

    String hazardAreaName = "";

    BuildingPlugin buildingPlugin;

    private LocationComponent locationComponent;


    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(requireContext(), Constant.MAPBOX_API_KEY);
        if (getArguments() != null) {
            fromBtn = getArguments().getBoolean("fromBtn");
        }
    }

    public MapFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dangerPolygonHashMap = new HashMap<>();
        listElevation = new ArrayList<>();
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        sharedMapViewModel = ViewModelProviders.of(requireActivity()).get(SharedMapViewModel.class);


        locationService = new LocationService(requireActivity().getApplicationContext());
        fragmentActivityCommunication.fragmentToolbarbyPosition(1);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        drawerLayout = view.findViewById(R.id.drawer_layout);

        initializeView(savedInstanceState, view);
        initializeMap();
        initializeListener();

        latitude = locationService.getLatitude();
        longitude = locationService.getLongitude();

        getLocationFromDecimalToDMSFormat(latitude, longitude);



      /*  ArrayList<ReceivedShareTripRequest>receivedShareTripRequestList = new ArrayList<>();

        ReceivedShareTripRequest receivedShareTripRequests = new ReceivedShareTripRequest("233","androidtwo","test");
        ReceivedShareTripRequest receivedShareTripRequestsTwo = new ReceivedShareTripRequest("244","androidthree","testee");

        receivedShareTripRequestList.add(receivedShareTripRequests);
        receivedShareTripRequestList.add(receivedShareTripRequestsTwo);

        String receivedShareTripRequestListString = gson.toJson(receivedShareTripRequestList);

        PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST,receivedShareTripRequestListString);
*/
    }


    private void initializeView(Bundle savedInstanceState, View view) {

        mapView = view.findViewById(R.id.mapView);
       // mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.onCreate(savedInstanceState);
        IconFactory iconFactory = IconFactory.getInstance(requireActivity());
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        dangerIcon = iconFactory.fromResource(R.drawable.delete_marker);
        hazardIcon = iconFactory.fromResource(R.drawable.hazard_60);
        selectionLayout = view.findViewById(R.id.selection_layout);
        linearLayoutHazardConfrim = view.findViewById(R.id.linearLayoutHazardConfirm);

        btnSafe = view.findViewById(R.id.btn_safe);
        btnLandmark = view.findViewById(R.id.btn_landmark);
        btnHazard = view.findViewById(R.id.btn_hazard);
        menuBtn = view.findViewById(R.id.menu_btn);

        btnDanger = view.findViewById(R.id.btn_danger);
        iconMyloc = iconFactory.fromResource(R.drawable.my_pos_marker_icon);
        tvLatlong = view.findViewById(R.id.tv_lat_long);

        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            tvLatlong.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            tvLatlong.setTextColor(getResources().getColor(R.color.white, null));
        }

        btnSafe.setBackgroundResource(R.drawable.bg_safe_selected);
        btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
        btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
        btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);


        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        ivRefresh = view.findViewById(R.id.iv_refresh);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvDone = view.findViewById(R.id.tv_done);
        tvDone.setVisibility(View.GONE);
        ivRefresh.setVisibility(View.GONE);
        ivAddUpdate.setVisibility(View.GONE);
        tvTitle.setText(Constant.MAPS);

        bottomSheetView = view.findViewById(R.id.container);
        fabAddressSearch = view.findViewById(R.id.fab_address_search);
        fabLocation = view.findViewById(R.id.fab_my_location);
        fabMore = view.findViewById(R.id.fab_option);

        imageViewHazardConfirm = view.findViewById(R.id.imageViewHazardConfirm);
        imageViewHazardCancel = view.findViewById(R.id.imageViewHazardCancel);


        layoutMenuContent = view.findViewById(R.id.layout_option);

        layoutInflater = (LayoutInflater) requireContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Snackbar.make(view, getResources().getString(R.string.marker_suggestion), Snackbar.LENGTH_INDEFINITE)
                .setAction("CLOSE", view1 -> {

                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();

    }

    private void initBottomPicker() {

        PreferenceManager.updateValue(Constant.KEY_IS_FROM_MAP_UI, true);

        bottomFragment = new MapBottomFragment();
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, bottomFragment);
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

    private void initHazardInfoBottomSheet() {

        hazardInfoBottomFragment = new HazardInfoBottomFragment();

        Bundle args = new Bundle();
        args.putString("HazardArea", hazardArea);
        args.putString("HazardLength", hazardLenght);
        hazardInfoBottomFragment.setArguments(args);


        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();

        ft.add(R.id.container, hazardInfoBottomFragment);
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

    private void initializeListener() {

        menuBtn.setOnClickListener(this);

        btnSafe.setOnClickListener(v -> {
            btnSafe.setBackgroundResource(R.drawable.bg_safe_selected);
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);

            btnSafe.setTextColor(getResources().getColor(R.color.white));
            btnLandmark.setTextColor(getResources().getColor(R.color.orange));
            btnDanger.setTextColor(getResources().getColor(R.color.orange));
            btnHazard.setTextColor(getResources().getColor(R.color.orange));

            if (type == 4) {

                if(map!=null)map.clear();
                loadMarker();
            }
            type = 1;
            //fragmentActivityCommunication.showDone(false);
            tvDone.setVisibility(View.GONE);
        });

        btnLandmark.setOnClickListener(v -> {
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_selected);
            btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);
            btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);
            btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnLandmark.setTextColor(getResources().getColor(R.color.white));
            btnSafe.setTextColor(getResources().getColor(R.color.orange));
            btnDanger.setTextColor(getResources().getColor(R.color.orange));
            btnHazard.setTextColor(getResources().getColor(R.color.orange));

            if (type == 4) {

                if(map!=null)map.clear();
                loadMarker();
            }

            type = 2;
            //fragmentActivityCommunication.showDone(false);
            tvDone.setVisibility(View.GONE);
        });

        btnHazard.setOnClickListener(v -> {


            //  callPolygonMap.openPolygonGoogleMap();

            btnHazard.setBackgroundResource(R.drawable.bg_hazard_selected);
            btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);
            btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnHazard.setTextColor(getResources().getColor(R.color.orange));
            btnSafe.setTextColor(getResources().getColor(R.color.orange));
            btnDanger.setTextColor(getResources().getColor(R.color.orange));
            btnLandmark.setTextColor(getResources().getColor(R.color.orange));
            type = 4;
            //fragmentActivityCommunication.showDone(true);
            tvDone.setVisibility(View.VISIBLE);

        });

        btnDanger.setOnClickListener(v -> {

            if (type == 4) {

                if(map!=null)map.clear();
                loadMarker();
            }

            btnDanger.setBackgroundResource(R.drawable.bg_danger_selected);
            btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnDanger.setTextColor(getResources().getColor(R.color.white));
            btnSafe.setTextColor(getResources().getColor(R.color.orange));
            btnLandmark.setTextColor(getResources().getColor(R.color.orange));
            btnHazard.setTextColor(getResources().getColor(R.color.orange));
            type = 3;
            //fragmentActivityCommunication.showDone(false);
            tvDone.setVisibility(View.GONE);
        });

        tvDone.setOnClickListener(v -> {
            //start


            if (!OUTER_POINTS.isEmpty() && OUTER_POINTS.size() > 1) {


                loadPolygonBeforeSaveInServer(OUTER_POINTS);

                linearLayoutHazardConfrim.setVisibility(View.VISIBLE);

                bottomSheetView.setVisibility(View.VISIBLE);

                tvDone.setVisibility(View.GONE);

                if (hazardInfoBottomFragment != null) {

                    getActivity().getSupportFragmentManager().beginTransaction().remove(hazardInfoBottomFragment).commit();


                  /*  Bundle args = new Bundle();
                    args.putString("HazardArea", hazardArea);
                    args.putString("HazardLength", hazardLenght);
                    hazardInfoBottomFragment.setArguments(args);

                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().show(hazardInfoBottomFragment).commit();
*/
                }
                initHazardInfoBottomSheet();

            } else {


                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_add_at_least_two_marker), Toast.LENGTH_SHORT).show();


            }
            //end
            
            
            
           /* if (!OUTER_POINTS.isEmpty() && OUTER_POINTS.size() > 1) {

                CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                createTripMarkerModelClass.setMarkType(4);
                createTripMarkerModelClass.setTripId(0);
                createTripMarkerModelClass.setLat(OUTER_POINTS.get(0).getLatitude());
                createTripMarkerModelClass.setLongX(OUTER_POINTS.get(0).getLongitude());
                createTripMarkerModelClass.setRadius(0);
                createTripMarkerModelClass.setAddress(getAddress(OUTER_POINTS.get(0), false));
                createTripMarkerModelClass.setFullAddress(getAddress(OUTER_POINTS.get(0), true));
                createTripMarkerModelClass.setZone_name("test");
                createTripMarkerModelClass.setDescription("demo");

                List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();

                int k =1 ;
                for (LatLng latLng : OUTER_POINTS) {

                                       CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();


                   *//* CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean(
                            latLng.getLatitude(),
                            latLng.getLongitude(),
                            getAddress(latLng, true),
                            "Pin "+ k);*//*

                    locationsBean.setLat(latLng.getLatitude());
                    locationsBean.setName("Pin "+ k);
                    locationsBean.setAddress(getAddress(latLng, true));
                    locationsBean.setLongX(latLng.getLongitude());
                    //locationsBean.setId(k);
                    locationsBeanList.add(locationsBean);
                    k++;
                }
                createTripMarkerModelClass.setLocations(locationsBeanList);
                createMarker(createTripMarkerModelClass, 4, 0, 0, 0);

            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_add_at_least_two_marker), Toast.LENGTH_SHORT).show();
            }*/

            //fabAddressSearch.show();
        });

        ivRefresh.setOnClickListener(v -> {
            SyncWithInternetFragment syncWithInternetFragment = new SyncWithInternetFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_in_trip", false);
            syncWithInternetFragment.setArguments(bundle);
            syncWithInternetFragment.show(getChildFragmentManager(), null);

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


           /* CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                    .zoom(18)
                    .tilt(30)
                    .build();
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 1000);*/

        });


        fabMore.setOnClickListener(v -> {


            bottomSheetView.setVisibility(View.VISIBLE);
            if (bottomFragment != null) {

                PreferenceManager.updateValue(Constant.KEY_IS_FROM_MAP_UI, true);
                requireActivity().getSupportFragmentManager().beginTransaction().show(bottomFragment).commit();

            } else {

                initBottomPicker();
            }


            layoutMenuContent.setVisibility(View.GONE);


            Point originPosition = Point.fromLngLat(-117.61296898, 47.64287731);
            Point destinationPosition = Point.fromLngLat(-116.243890, 43.618259);


            /*assert Mapbox.getAccessToken() != null;
            MapboxDirections client = MapboxDirections.builder()
                    .origin(originPosition)
                    .destination(destinationPosition)
                    .overview(DirectionsCriteria.OVERVIEW_FULL)
                    .profile(DirectionsCriteria.PROFILE_WALKING)
                    .accessToken(Mapbox.getAccessToken())
                    .build();




            client.enqueueCall(new Callback<DirectionsResponse>() {


                @Override
                public void onResponse(retrofit2.@NotNull Call<DirectionsResponse> call, @NotNull Response<DirectionsResponse> response) {


                 ///   Log.e("MapFragment", "onResponse"+ called+" "+duration);

                    if (response.body() == null) {
                        Timber.d( "No routes found, make sure you set the right user and access token.");
                        return;
                    } else if (response.body().routes().size() < 1) {

                       // response.body().getRoutes().get(0).getSteps().get(0).getDistance();

                        double distance = response.body().routes().get(0).distance();
                        double duration = response.body().routes().get(0).duration();

                        Log.e("MapFragment", "Map"+ distance+" "+duration);

                        return;
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<DirectionsResponse> call, Throwable t) {

                       Log.e("MapFragment", "onResponse"+ "failed");


                    *//*Timber.d("Error: %s", throwable.getMessage());
                    if (!throwable.getMessage().equals("Coordinate is invalid: 0,0")) {

                     *//**//*   Toast.makeText(DashedLineDirectionsPickerActivity.this,
                                "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                      *//**//*
                    }*//*

                }

            });*/


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


        imageViewHazardConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (hazardAreaName.isEmpty()) {

                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "Please provide zone name", Toast.LENGTH_SHORT).show();

                } else {

                    CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                    createTripMarkerModelClass.setMarkType(4);
                    createTripMarkerModelClass.setTripId(0);
                    createTripMarkerModelClass.setLat(OUTER_POINTS.get(0).getLatitude());
                    createTripMarkerModelClass.setLongX(OUTER_POINTS.get(0).getLongitude());
                    createTripMarkerModelClass.setRadius(0);
                    createTripMarkerModelClass.setAddress(getAddress(OUTER_POINTS.get(0), false));
                    createTripMarkerModelClass.setFullAddress(getAddress(OUTER_POINTS.get(0), true));
                    createTripMarkerModelClass.setZone_name(hazardAreaName);
                    createTripMarkerModelClass.setZone_area(hazardArea);
                    createTripMarkerModelClass.setZone_length(hazardLenght);
                    createTripMarkerModelClass.setDescription("demo");

                    List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();

                    int k = 1;
                    for (LatLng latLng : OUTER_POINTS) {

                        CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();


                   /* CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean(
                            latLng.getLatitude(),
                            latLng.getLongitude(),
                            getAddress(latLng, true),
                            "Pin "+ k);*/

                        locationsBean.setLat(latLng.getLatitude());
                        locationsBean.setName("Pin " + k);
                        locationsBean.setAddress(getAddress(latLng, true));
                        locationsBean.setLongX(latLng.getLongitude());
                        //locationsBean.setId(k);
                        locationsBeanList.add(locationsBean);
                        k++;
                    }
                    createTripMarkerModelClass.setLocations(locationsBeanList);
                    createMarker(createTripMarkerModelClass, 4, 0, 0, 0);

                    isFirstTimeBottomSheetInitialize = false;

                    linearLayoutHazardConfrim.setVisibility(View.GONE);

                    bottomSheetView.setVisibility(View.GONE);
                    linearLayoutHazardConfrim.setVisibility(View.GONE);

                    tvDone.setVisibility(View.VISIBLE);

                    hazardAreaName = "";
                    hazardLenght = "";
                    hazardArea = "";


                }


            }
        });

        imageViewHazardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvDone.setVisibility(View.VISIBLE);

                bottomSheetView.setVisibility(View.GONE);

                linearLayoutHazardConfrim.setVisibility(View.GONE);

                hazardAreaName = "";
                hazardLenght = "";
                hazardArea = "";
                OUTER_POINTS.clear();
                if(map!=null)map.clear();
                type = 4;
                loadMarker();


                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "cancel clicked", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void initializeMap() {

        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {

            map = mapboxMap;
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setLogoEnabled(false);


            if (fromBtn) {
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
            }


            getElevation();
            trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
            trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
            enableLocationComponent(style);
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

            if (PreferenceManager.getBool(Constant.KEY_IS_MARKER_LOAD)) {
                loadMarker();
            } else {
                loadInitMarker();
            }

            if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                        " \n" + "Elevation " + elevation + " meter");
            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                        locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation + " meter");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                        locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


                Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                        + " " + deg2UTM.getEasting();

                tvLatlong.setText(utm +
                        " \n" + "Elevation " + elevation + " meter");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                        + " " + deg2UTM.getEasting();

                tvLatlong.setText(utm +
                        " \n" + "Elevation " + elevation * 3.28084 + " feet");

            }


            //loadInitMarker();

            mapboxMap.setInfoWindowAdapter(marker -> {
                @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                TextView tvAddress = view.findViewById(R.id.tv_address);
                TextView tvRemove = view.findViewById(R.id.tv_delete);
                LinearLayout linearLayout = view.findViewById(R.id.linearLayout_marker_pop_up);
                tvRemove.setVisibility(View.GONE);
                TextView tvEdit = view.findViewById(R.id.tv_edit);
                TextView tvFullAddress = view.findViewById(R.id.tv_full_address);
                tvFullAddress.setText(marker.getSnippet());
                tvAddress.setText(marker.getTitle());


                if (marker.getSnippet() == null) {
                    tvEdit.setVisibility(View.GONE);


                    linearLayout.setVisibility(View.GONE);
                    // marker.hideInfoWindow();

/*
                    Toast.makeText(getActivity(), "Save The Marker First!", Toast.LENGTH_LONG).show();
*/
                } else {

                    linearLayout.setVisibility(View.VISIBLE);

                    if (marker.getSnippet().equals("a")) {
                        tvEdit.setVisibility(View.GONE);
                    }
                }


                // "edit" text/buttons in  infoWindow
                tvEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), LandmarkActivity.class);
                    Bundle bundle = new Bundle();

                    Log.e("MapFragment", "getSnippet " + marker.getSnippet());

                    if (marker.getSnippet().contains("-")) {
                        //for hazard

                        Log.e("MapFragment", "TVEditButton " + marker.getSnippet());
                        bundle.putString("marker_id", marker.getSnippet());
                        bundle.putBoolean("is_marker_from_polygon_pin", true);

                    } else {
                        bundle.putString("marker_id", marker.getSnippet());
                        bundle.putBoolean("is_marker_from_polygon_pin", false);

                    }
                    bundle.putLong("long_marker", marker.getId());
                    bundle.putBoolean("is_marker_from_trip", false);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_UPDATE_MARKER);
                    marker.hideInfoWindow();
                });


                return view;
            });


            mapboxMap.addOnMapLongClickListener(point -> {

                if (isMarkerAdd) {
                    if (createTripMarkerModelClass == null) {
                        createTripMarkerModelClass = new CreateTripMarkerModelClass();
                    }
                    createTripMarkerModelClass.setDescription(getResources().getString(R.string.description));
                    createTripMarkerModelClass.setLat(point.getLatitude());
                    createTripMarkerModelClass.setLongX(point.getLongitude());
                    createTripMarkerModelClass.setMarkType(type);
                    createTripMarkerModelClass.setAddress(getAddress(point, false));
                    createTripMarkerModelClass.setFullAddress(getAddress(point, true));
                    createTripMarkerModelClass.setTripId(0);
                    createTripMarkerModelClass.setRadius(0);
                    if (type == 1) {
                        createTripMarkerModelClass.setRadius(0);
                        createMarker(createTripMarkerModelClass, 1, point.getLatitude(), point.getLongitude(), 0);
                    } else if (type == 2) {
                        createTripMarkerModelClass.setRadius(0);
                        createMarker(createTripMarkerModelClass, 2, point.getLatitude(), point.getLongitude(), 0);
                    } else if (type == 3) {
                        SetRadiusFragment setRadiusFragment = new SetRadiusFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.KEY_TYPE_OF_RADIUS, 2);
                        bundle.putDouble(Constant.KEY_LATITUDE, point.getLatitude());
                        bundle.putDouble(Constant.KEY_LONGITUDE, point.getLongitude());
                        setRadiusFragment.setArguments(bundle);
                        setRadiusFragment.show(getChildFragmentManager(), null);
                    } else {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(point)
                                .title(Utils.getAddress(point.getLatitude(), point.getLongitude(), true, RouteApplication.getInstance().getApplicationContext()))
                                .icon(hazardIcon));
                        OUTER_POINTS.add(point);
                    }
                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_select_marker_warning), Toast.LENGTH_SHORT).show();
                }
                return false;

            });


            buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);

            if (PreferenceManager.getBool(Constant.KEY_IS_3D_MAP_SELECTED)) {

                buildingPlugin.setVisibility(true);

            } else {


                buildingPlugin.setVisibility(false);


            }


           /* mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    ScaleBarPlugin scaleBarPlugin = new ScaleBarPlugin(mapView, mapboxMap);

                    // Create a ScaleBarOptions object to use the Plugin's default styling
                    scaleBarPlugin.create(new ScaleBarOptions(getActivity()));
                }
            });*/


        }));
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

                        Log.e("MapFragment", "Elevation from mapbox " + elevation);


                        if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                            tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + elevation + " meter");
                        } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                                    locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation + " meter");

                        } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                                    locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

                        } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

                        } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


                            Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                            String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                                    + " " + deg2UTM.getEasting();

                            tvLatlong.setText(utm +
                                    " \n" + "Elevation " + elevation + " meter");

                        } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                            String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                                    + " " + deg2UTM.getEasting();

                            tvLatlong.setText(utm +
                                    " \n" + "Elevation " + elevation * 3.28084 + " feet");

                        }

                       /* else {
                            tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + elevation * 3.28084 + " feet");
                        }*/


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddDangerMarker(DangerMarkerObj obj) {
        if (obj.isAdd()) {
            createTripMarkerModelClass.setLat(obj.getLat());
            createTripMarkerModelClass.setLongX(obj.getLonX());
            createTripMarkerModelClass.setMarkType(type);
            createTripMarkerModelClass.setAddress(getAddress(new LatLng(obj.getLat(), obj.getLonX()), false));
            createTripMarkerModelClass.setFullAddress(getAddress(new LatLng(obj.getLat(), obj.getLonX()), true));
            createTripMarkerModelClass.setRadius(obj.getRadius() * 1000);
            createTripMarkerModelClass.setTripId(0);
            createTripMarkerModelClass.setDescription("");
            createMarker(createTripMarkerModelClass, 3, obj.getLat(), obj.getLonX(), obj.getRadius() * 1000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMarkerSync(SyncMarkerObject obj) {
        fragmentActivityCommunication.showProgressDialog(true);
        TripMarkerGetModel mTripMarkerGetModel = new TripMarkerGetModel();
        mTripMarkerGetModel.setSelat(southEastLatLng.getLatitude());
        mTripMarkerGetModel.setSelon(southEastLatLng.getLongitude());
        mTripMarkerGetModel.setNwlat(northWest.getLatitude());
        mTripMarkerGetModel.setNwlon(northWest.getLongitude());
        mTripMarkerGetModel.setLimit(String.valueOf(250));
        mTripMarkerGetModel.setTripSpecific(String.valueOf(0));
        mTripMarkerGetModel.setOffset(String.valueOf(0));
        getTripMarkerList(mTripMarkerGetModel, false);
        if (bottomFragment.isVisible()) {
            requireActivity().getSupportFragmentManager().beginTransaction().hide(bottomFragment).commit();
        }
        layoutMenuContent.setVisibility(View.VISIBLE);

    }

    private void loadInitMarker() {
        fragmentActivityCommunication.showProgressDialog(true);
        TripMarkerGetModel mTripMarkerGetModel = new TripMarkerGetModel();
        mTripMarkerGetModel.setSelat(southEastLatLng.getLatitude());
        mTripMarkerGetModel.setSelon(southEastLatLng.getLongitude());
        mTripMarkerGetModel.setNwlat(northWest.getLatitude());
        mTripMarkerGetModel.setNwlon(northWest.getLongitude());
        mTripMarkerGetModel.setLimit(String.valueOf(250));
        mTripMarkerGetModel.setTripSpecific(String.valueOf(0));
        mTripMarkerGetModel.setOffset(String.valueOf(0));
        getTripMarkerList(mTripMarkerGetModel, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(Integer integer) {
        isMarkerAdd = true;
        selectionLayout.setVisibility(View.VISIBLE);
        if (bottomFragment != null && bottomFragment.isVisible()) {
            requireActivity().getSupportFragmentManager().beginTransaction().hide(bottomFragment).commit();
        }
        layoutMenuContent.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishMarkerAdd(UiManageObject uiManageObject) {
        if (type == 4) {
            if (!OUTER_POINTS.isEmpty() && OUTER_POINTS.size() > 2) {
                CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                createTripMarkerModelClass.setMarkType(4);
                createTripMarkerModelClass.setTripId(0);
                createTripMarkerModelClass.setLat(OUTER_POINTS.get(0).getLatitude());
                createTripMarkerModelClass.setLongX(OUTER_POINTS.get(0).getLongitude());
                createTripMarkerModelClass.setRadius(0);
                createTripMarkerModelClass.setAddress(getAddress(OUTER_POINTS.get(0), false));
                createTripMarkerModelClass.setFullAddress(getAddress(OUTER_POINTS.get(0), true));
                createTripMarkerModelClass.setDescription("demo");
                List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();
                for (LatLng latLng : OUTER_POINTS) {
                    CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();
                    locationsBean.setLat(latLng.getLatitude());
                    locationsBean.setLongX(latLng.getLongitude());
                    locationsBeanList.add(locationsBean);
                }
                createTripMarkerModelClass.setLocations(locationsBeanList);
                createMarker(createTripMarkerModelClass, 4, 0, 0, 0);

            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_add_at_least_three_marker), Toast.LENGTH_SHORT).show();
            }
        }
        //fabAddressSearch.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeMapStyle(ChangeMapObject floatNumber) {
        if (floatNumber.getType() == 1) {
            map.setStyle(com.mapbox.mapboxsdk.maps.Style.MAPBOX_STREETS, style -> {
                tvLatlong.setTextColor(getResources().getColor(R.color.black, null));


                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 0);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_mapbox_streets));

                mapData = new MapData(0, getResources().getString(R.string.mapbox_style_mapbox_streets));
                sharedMapViewModel.select(mapData);
            });

        } else if (floatNumber.getType() == 2) {
            tvLatlong.setTextColor(getResources().getColor(R.color.white, null));

            map.setStyle(com.mapbox.mapboxsdk.maps.Style.SATELLITE, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 1);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite));

                mapData = new MapData(1, getResources().getString(R.string.mapbox_style_satellite));
                sharedMapViewModel.select(mapData);
            });


        } else if (floatNumber.getType() == 3) {
            tvLatlong.setTextColor(getResources().getColor(R.color.white, null));
            map.setStyle(com.mapbox.mapboxsdk.maps.Style.SATELLITE_STREETS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 2);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite_streets));

                mapData = new MapData(2, getResources().getString(R.string.mapbox_style_satellite_streets));
                sharedMapViewModel.select(mapData);
            });

        } else if (floatNumber.getType() == 4) {
            map.setStyle(com.mapbox.mapboxsdk.maps.Style.OUTDOORS, style -> {
                tvLatlong.setTextColor(getResources().getColor(R.color.black, null));
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 3);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_outdoors));

                mapData = new MapData(3, getResources().getString(R.string.mapbox_style_outdoors));
                sharedMapViewModel.select(mapData);
            });
        } else if (floatNumber.getType() == 5) {
            /*bottomSheetView.setVisibility(View.GONE);
            layoutMenuContent.setVisibility(View.VISIBLE);*/
            if (bottomFragment != null) {
                if (bottomFragment.isVisible()) {
                    requireActivity().getSupportFragmentManager().beginTransaction().hide(bottomFragment).commit();
                }
            }
            layoutMenuContent.setVisibility(View.VISIBLE);


        } else if (floatNumber.getType() == 6) {


            if (PreferenceManager.getBool(Constant.KEY_IS_3D_MAP_SELECTED)) {

                buildingPlugin.setVisibility(true);

            } else {

                buildingPlugin.setVisibility(false);


            }


        }
    }



    private String getAddress(LatLng point, boolean isFullAddress) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(point.getLatitude(), point.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0); //0 to obtain first possible address
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            if (isFullAddress) {
                return address.concat("," + city).concat("," + country);
            } else {
                return address;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void getTripMarkerList(TripMarkerGetModel tripMarkerGetModel, boolean isInitialLoad) {
        if(map!=null)map.clear();
        Intent intent = new Intent(getActivity(), RouteReconIntentService.class);
        intent.setAction(RouteReconIntentService.ACTION_GET_TRIP_MARKER);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.KEY_PARCEL_MARKER_OBJ, tripMarkerGetModel);
        intent.putExtras(bundle);
        intent.putExtra(RouteReconIntentService.ACTION_GET_TRIP_MARKER_RESULT, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                boolean isFinished = resultData.getBoolean(Constant.KEY_IS_FINISHED);
                if (isFinished) {
                    if (isInitialLoad) {
                        PreferenceManager.updateValue(Constant.KEY_IS_MARKER_LOAD, true);
                        loadMarker();
                    } else {
                        loadMarker();
                    }

                    fragmentActivityCommunication.showProgressDialog(false);
                } else {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }
        });
        requireActivity().startService(intent);
    }


    private Bitmap getBitmapFromView() {
        //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

        View customView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text, null);

        TextView textView = customView.findViewById(R.id.textView);
        textView.setText("2220m");
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

    private void loadMarker() {
        List<MarkerDescription> markerDescriptionList = AppDatabase.getAppDatabase(requireActivity().getApplicationContext()).daoMarker().fetchMarkerByTripId(String.valueOf(0));
        if (markerDescriptionList != null && markerDescriptionList.size() > 0) {
            for (MarkerDescription markerDescription : markerDescriptionList) {
                if (markerDescription.getMarkType() == 1) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(safeIcon));
                    markerId = markerDescription.getMarkerId();
                } else if (markerDescription.getMarkType() == 2) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(landmarkIcon));

                    markerId = markerDescription.getMarkerId();


                } else if (markerDescription.getMarkType() == 3) {

                    // 3 = danger
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(dangerIcon));
                    com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter
                            (new LatLng(markerDescription.getLat(), markerDescription.getLongX()), markerDescription.getRadius() / 1000, 64));

                    dangerPolygonHashMap.put(markerDescription.getMarkerId(), polygon);

                    markerId = markerDescription.getMarkerId();

                } else if (markerDescription.getMarkType() == 4) {

                    // 4 = hazard
                    if (markerDescription.getHazardMarkerPointList() != null) {


                        List<LatLng> latLngs = new ArrayList<>();

                        int k = 1;


                        for (LocationsBeanForLocalDb locationsBean : markerDescription.getHazardMarkerPointList()) {


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
                                    .setSnippet(markerDescription.getMarkerId() + "-" + String.valueOf(markerDescription.getHazardMarkerPointList().get(k - 1).getId()))
                                    .title(markerDescription.getHazardMarkerPointList().get(k - 1).getName()));


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


                                    if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
                                    ){

                                        distance = Utils.getFeetFromMeter(distance);

                                    }


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


                                    if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
                                    ){
                                        distance = Utils.getFeetFromMeter(distance);

                                    }

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
                            ;


                            double area = SphericalUtil.computeArea(latLngList);


                            area = Utils.getFeetFromMeter(area);
                            // for showing area

                            IconFactory iconFactory = IconFactory.getInstance(getActivity());
                            Icon icon = iconFactory.fromBitmap(getBitmapFromViewForArea(String.valueOf(format("%.2f", area))));

                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(centroid(latLngs)[0], centroid(latLngs)[1]))
                                    .icon(icon));
                        } else if (latLngs.size() == 2) {

                            //for line draw


                            double distance = calculateDistanceDifferenceInMeter(latLngs.get(0).getLongitude(),
                                    latLngs.get(0).getLatitude(),
                                    latLngs.get(1).getLongitude(),
                                    latLngs.get(1).getLatitude());



                            if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
                            ){


                                distance = Utils.getFeetFromMeter(distance);

                            }

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

    }


    private void createMarker(CreateTripMarkerModelClass createTripMarkerModelClass, int type,
                              double lat, double lonX, double radius) {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();

        apiService.createTripMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                createTripMarkerModelClass, new ResponseCallback<TripMarkerCreateResponse>() {
                    @Override
                    public void onSuccess(TripMarkerCreateResponse data) {
                        LatLng pointLatLong = new LatLng(lat, lonX);
                        Gson gson = new Gson();

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
                                switch (type) {
                                    case 1:
                                        map.addMarker(new MarkerOptions()
                                                .position(pointLatLong)
                                                .setTitle(Utils.getAddress(pointLatLong.getLatitude(), pointLatLong.getLongitude(), true, requireActivity().getApplicationContext()))
                                                .setSnippet(String.valueOf(data.getSuccess().getMarkerID()))
                                                .icon(safeIcon));
                                        fragmentActivityCommunication.showProgressDialog(false);
                                        break;
                                    case 2:
                                        map.addMarker(new MarkerOptions()
                                                .position(pointLatLong)
                                                .setTitle(Utils.getAddress(pointLatLong.getLatitude(), pointLatLong.getLongitude(), true, requireActivity().getApplicationContext()))
                                                .setSnippet(String.valueOf(data.getSuccess().getMarkerID()))
                                                .icon(landmarkIcon));
                                        fragmentActivityCommunication.showProgressDialog(false);
                                        break;
                                    case 3:
                                        map.addMarker(new MarkerOptions()
                                                .position(pointLatLong)
                                                .setTitle(Utils.getAddress(pointLatLong.getLatitude(), pointLatLong.getLongitude(), true, requireActivity().getApplicationContext()))
                                                .setSnippet(String.valueOf(data.getSuccess().getMarkerID()))
                                                .icon(dangerIcon));
                                        com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(lat, lonX), radius / 1000, 64));
                                        dangerPolygonHashMap.put(String.valueOf(data.getSuccess().getMarkerID()), polygon);
                                        fragmentActivityCommunication.showProgressDialog(false);
                                        break;

                                    case 4:
                                        //hazard
                                        if(map!=null)map.clear();
                                        loadMarker();
                                        OUTER_POINTS.clear();
                                        fragmentActivityCommunication.showProgressDialog(false);
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        th.printStackTrace();
                        fragmentActivityCommunication.showProgressDialog(false);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }

        try {
            callDrawer = (CallDrawer) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }

        try {
            callPolygonMap = (CallPolygonMap) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }

        try {
            bottomNavigationVisibilityListener = (BottomNavigationVisibilityListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(RouteApplication.getInstance().getApplicationContext())) {
            locationComponent = map.getLocationComponent();
            locationComponent.activateLocationComponent(RouteApplication.getInstance().getApplicationContext(), loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            /*locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);*/
        } else {
            permissionsManager = new PermissionsManager(this);
            try {
                permissionsManager.requestLocationPermissions(getActivity());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            map.getStyle(this::enableLocationComponent);
        } else {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showTraffic() {
        trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        loadMapData();
        EventBus.getDefault().register(this);
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                getElevation();
                handler.postDelayed(this, 15000);
            }
        };
        handler.postDelayed(runnable, 15000);
    }

    private void loadMapData() {
        sharedViewModel.getSelected().observe(this, mapData -> {
            if (map != null) {
               /* map.setStyle(mapData.getStyle(), style -> {
                    EventBus.getDefault().post(mapData);
                });*/
                map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        EventBus.getDefault().post(mapData);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        bottomNavigationVisibilityListener.showBottomNavigation(true);
       //checkTripSendReceivingRequest();





    }

    @Override
    public void onPause() {
        super.onPause();
        type = 1;
        mapView.onPause();
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
        EventBus.getDefault().unregister(this);
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_UPDATE_MARKER) {
            if (data != null) {
                boolean isEdit = data.getBooleanExtra("is_edit", false);
                long markerId = data.getLongExtra("long_marker", 0);
                String serverMarkerID = data.getStringExtra("server_marker_id");
                int markerType = data.getIntExtra("marker_type", 0);
                double lat = data.getDoubleExtra("lat", 0);
                double lon = data.getDoubleExtra("lon", 0);
                boolean isDeletedHazardPin = data.getBooleanExtra("isPolygonMarkerDelete", false);

                if (!isEdit) {
                    if (markerType == 3) {
                        if (dangerPolygonHashMap.containsKey(serverMarkerID)) {
                            com.mapbox.mapboxsdk.annotations.Polygon polygon = dangerPolygonHashMap.get(serverMarkerID);
                            if (polygon != null) {
                                map.removePolygon(polygon);
                            }
                        }
                    }


                    if (markerType == 4) {

                        if (isDeletedHazardPin) {

                            loadInitMarker();


                        } else {

                            if(map!=null)map.clear();
                            loadMarker();

                        }

                    }

                   /* if (markerType == 4) {
                        map.clear();
                        loadMarker();
                    }*/
                    map.removeAnnotation(markerId);
                } else {

                    if (markerType == 3) {
                        if (dangerPolygonHashMap.containsKey(serverMarkerID)) {
                            com.mapbox.mapboxsdk.annotations.Polygon polygon = dangerPolygonHashMap.get(serverMarkerID);
                            if (polygon != null) {
                                map.removePolygon(polygon);
                            }
                        }
                        double radius = data.getDoubleExtra("radius", 0);
                        dangerPolygonHashMap.remove(serverMarkerID);
                        com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(lat, lon), radius / 1000, 64));
                        dangerPolygonHashMap.put(serverMarkerID, polygon);

                        loadInitMarker();

                    } else {


                        loadInitMarker();


                    }
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == 1) {
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
                    .icon(iconMyloc));

            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), gLat + " " + gLon + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeTrafficData(TrafficObject trafficObject) {
        if (trafficPlugin != null) {
            trafficPlugin.setVisibility(!trafficObject.isShow());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeUnitType(CoordinateUnitObject coordinateUnitObject) {


        if (coordinateUnitObject.getUnitType().equals("dms")
                && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


            tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                    locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation + " meter");

        } else if (coordinateUnitObject.getUnitType().equals("dms")

                && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


            tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                    locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

        } else if (coordinateUnitObject.getUnitType().equals("decimal") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

            tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                    " \n" + "Elevation " + elevation + " meter");

        } else if (coordinateUnitObject.getUnitType().equals("decimal") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

            tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                    " \n" + "Elevation " + elevation * 3.28084 + " feet");

        } else if (coordinateUnitObject.getUnitType().equals("utm") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


            Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

            String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                    + " " + deg2UTM.getEasting();

            tvLatlong.setText(utm +
                    " \n" + "Elevation " + elevation + " meter");

        } else if (coordinateUnitObject.getUnitType().equals("utm") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

            Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

            String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                    + " " + deg2UTM.getEasting();

            tvLatlong.setText(utm +
                    " \n" + "Elevation " + elevation * 3.28084 + " feet");

        }


        Log.e("FromAdapterclass", "Unit " + coordinateUnitObject.getUnitType());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;
        bottomFragment = null;
        hazardInfoBottomFragment = null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                        " \n" + "Elevation " + elevation + " meter");
            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                        locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation + " meter");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatlong.setText(getLocationFromDecimalToDMSFormat(
                        locationService.getLatitude(), locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) + " \n" + "Elevation " + elevation * 3.28084 + " feet");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


                Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                        + " " + deg2UTM.getEasting();

                tvLatlong.setText(utm +
                        " \n" + "Elevation " + elevation + " meter");

            } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                String utm = deg2UTM.getZone() + " " + deg2UTM.getLetter() + " " + deg2UTM.getNorthing()
                        + " " + deg2UTM.getEasting();

                tvLatlong.setText(utm +
                        " \n" + "Elevation " + elevation * 3.28084 + " feet");

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_btn:

                drawerLayout.openDrawer(Gravity.LEFT);
             //   callDrawer.openDrawer();
                break;
        }
    }


    public interface CallDrawer {
        void openDrawer();

    }

    public interface CallPolygonMap {
        void openPolygonGoogleMap();

    }


    private void checkTripSendReceivingRequest() {


        String tripSendReceived = Utils.getSendTripReceiveList();

        if (!tripSendReceived.isEmpty()) {

            Type sendListType = new TypeToken<ArrayList<ReceivedShareTripRequest>>() {
            }.getType();

            ArrayList<ReceivedShareTripRequest> receivedTripSendList = gson.fromJson(tripSendReceived, sendListType);

            for (ReceivedShareTripRequest sender : receivedTripSendList) {
                System.out.println(sender);

                ShowDialog.showSendRequestReceivedDialog((AppCompatActivity) getActivity(),
                        sender.getSenderName(),
                        sender.getTripId(),
                        sender.getTripName(),
                        new OnDialogViewReceivedSendRequestListener() {


                            @Override
                            public void onAccept(String tripId) {


                                sharedMapViewModel.liveDataTripRequest(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), tripId).observe(getActivity(), successArray -> {

                                    Log.e("TripAccept", "Successful");


                                    //fragmentActivityCommunication.showProgressDialog(true);
                                    final OneTimeWorkRequest workRequest =
                                            new OneTimeWorkRequest.Builder(LoadAllTripWorker.class)
                                                    .build();

                                    WorkManager.getInstance().enqueue(workRequest);
                                    WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId()).observe(getActivity(), workInfo -> {

                                        Log.e("MapFrag ", " Trip Reload Successful");

                                    });


                                });


                            }

                            @Override
                            public void onDenied() {


                            }
                        });
            }


        }

        Utils.removeTripRequestFromLocalStorage();


    }

    private String getLocationFromDecimalToDMSFormat(double latitude, double longitude) {


        Log.e("latitude", String.valueOf(latitude));
        Log.e("longitude", String.valueOf(longitude));


        int degreeLat = (int) latitude;
        Log.e("degreeLat", String.valueOf(degreeLat));

        double differenceLat = (latitude - degreeLat);

        double minuteLat = differenceLat * 60;

        Log.e("minuteLat", String.valueOf((int) minuteLat));


        int minuteLatitude = (int) minuteLat;

        Log.e("minuteLatitude", "tauis " + String.valueOf(minuteLatitude));


        double divQuitonent = (double) minuteLat / 60;

        DecimalFormat df = new DecimalFormat("#.#");

        //  double divRemainder = minuteLatitude%60;


        Log.e("div", "div " + String.valueOf(df.format(divQuitonent)));


        double secondLat = (differenceLat - Double.parseDouble(df.format(divQuitonent))) * 3600;

        DecimalFormat df2 = new DecimalFormat("##.##");


        String LatitudeFinal = degreeLat + "°" + minuteLatitude + "'" + df2.format(secondLat) + "\"";

        Log.e("LatitudeFinal", "t" + LatitudeFinal);


        int degreeLong = (int) longitude;
        Log.e("degreeLng", String.valueOf(degreeLong));

        double differenceLng = (longitude - degreeLong);

        double minuteLng = differenceLng * 60;

        Log.e("minuteLng", String.valueOf((int) minuteLng));


        int minuteLongitude = (int) minuteLng;

        Log.e("minuteLongitude", "tauis " + String.valueOf(minuteLongitude));


        double divQuitonentLong = (double) minuteLongitude / 60;

        //  double divRemainder = minuteLatitude%60;


        Log.e("div", "div " + String.valueOf(df.format(divQuitonentLong)));


        double secondLng = (differenceLng - Double.parseDouble(df.format(divQuitonentLong))) * 3600;


        String LongitudeFinal = degreeLong + "°" + minuteLongitude + "'" + df2.format(secondLng) + "\"";

        Log.e("LongitudeFinal", "t" + LongitudeFinal);


        return LatitudeFinal + " " + LongitudeFinal;


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


    private Bitmap getBitmapFromView(String value) {
        //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

        View customView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text, null);

        // customView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        TextView textView = customView.findViewById(R.id.textView);

        if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
        ){


            textView.setText(value + " " + "ft ");

        }else {
            textView.setText(value + " " + "m ");
        }
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


    private Bitmap getBitmapFromViewForArea(String value) {
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
        if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
        ){


            textView.setText(value + " " + "ft²");

        }else {
            textView.setText(value + " " + "m² ");
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHazardAreaName(HazardNameObject hazardNameObject) {

        hazardAreaName = hazardNameObject.getName();

        //Log.e("MapFragment", "eventBus " + hazardAreaName);
    }

    private void loadPolygonBeforeSaveInServer(List<LatLng> latLngs) {


        int k = 1;


        for (LatLng latLng : latLngs) {

            IconFactory iconFactory = IconFactory.getInstance(getActivity());
            Icon icon = iconFactory.fromResource(R.drawable.hazard_60);

            //   Log.e("PoinID","value"+ markerDescription.getHazardMarkerPointList().get(k-1).getId() );


            map.addMarker(new MarkerOptions()
                    .position(new LatLng(latLng.getLatitude(), latLng.getLongitude()))
                    .icon(icon)
                    .title("Pin " + k));
            k++;

        }

        double totalLenght = 0.0;

        if (latLngs.size() > 2) {


            // add midpoint as a marker
            for (int i = 0; i < latLngs.size(); i++) {

                if (i == latLngs.size() - 1) {


                    double distance = calculateDistanceDifferenceInMeter(latLngs.get(i).getLongitude(),
                            latLngs.get(i).getLatitude(),
                            latLngs.get(0).getLongitude(),
                            latLngs.get(0).getLatitude());


                    totalLenght += distance;


                    if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
                    ){


                        distance = Utils.getFeetFromMeter(distance);

                    }


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

                    totalLenght += distance;


                    if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
                    ){


                        distance = Utils.getFeetFromMeter(distance);

                    }


                    IconFactory iconFactory = IconFactory.getInstance(getActivity());
                    Icon icon = iconFactory.fromBitmap(getBitmapFromView(String.valueOf(format("%.2f", distance))));


                    map.addMarker(new MarkerOptions()
                            .position(midPoint(latLngs.get(i).getLatitude(), latLngs.get(i).getLongitude(),
                                    latLngs.get(i + 1).getLatitude(), latLngs.get(i + 1).getLongitude()))
                            .icon(icon));
                }


            }


            hazardLenght = String.valueOf(format("%.2f", totalLenght));


            com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = map.addPolygon(new PolygonOptions()
                    .addAll(latLngs)
                    .fillColor(Color.YELLOW)
                    .alpha(0.2f));


            //convert latlong type to google maps typw
            List<com.google.android.gms.maps.model.LatLng> latLngList = new ArrayList<>();

            for (LatLng latLng : latLngs) {


                latLngList.add(new com.google.android.gms.maps.model.LatLng(latLng.getLatitude(), latLng.getLongitude()));
            }
            ;


            double area = SphericalUtil.computeArea(latLngList);
            area = Utils.getFeetFromMeter(area);


            hazardArea = String.valueOf(format("%.2f", area));


            // for showing area

            IconFactory iconFactory = IconFactory.getInstance(getActivity());
            Icon icon = iconFactory.fromBitmap(getBitmapFromViewForArea(String.valueOf(format("%.2f", area))));

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(centroid(latLngs)[0], centroid(latLngs)[1]))
                    .icon(icon));
        } else {

            //for line draw


            double distance = calculateDistanceDifferenceInMeter(latLngs.get(0).getLongitude(),
                    latLngs.get(0).getLatitude(),
                    latLngs.get(1).getLongitude(),
                    latLngs.get(1).getLatitude());

            hazardLenght = String.valueOf(format("%.2f", distance));


            if(PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("imperial")
            ){


                distance = Utils.getFeetFromMeter(distance);

            }


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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeDistanceUnitType(DistanceUnitObject distanceUnitObject) {

        if(map!=null)map.clear();



        if (PreferenceManager.getBool(Constant.KEY_IS_MARKER_LOAD)) {
            loadMarker();
        } else {
            loadInitMarker();
        }

    }


}


