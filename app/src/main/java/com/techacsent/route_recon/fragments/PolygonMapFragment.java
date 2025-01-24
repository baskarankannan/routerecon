package com.techacsent.route_recon.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
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
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.LandmarkActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.DangerMarkerObj;
import com.techacsent.route_recon.event_bus_object.SyncMarkerObject;
import com.techacsent.route_recon.event_bus_object.UiManageObject;
import com.techacsent.route_recon.interfaces.BottomNavigationVisibilityListener;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnDialogViewReceivedSendRequestListener;
import com.techacsent.route_recon.model.ElevationResponse;
import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.ReceivedShareTripRequest;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.TripMarkerGetModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static java.lang.String.format;


public class PolygonMapFragment extends Fragment implements PermissionsListener, View.OnClickListener, OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {


    private LinearLayout layoutMenuContent;
    private ImageView fabAddressSearch;
    private ImageView fabLocation;
    private ImageView fabMore;
    private ImageView imageViewBack;
    private LinearLayout selectionLayout;

    private Button btnSafe;
    private Button btnLandmark;
    private Button btnHazard;
    private Button btnDanger;
    private ImageButton menuBtn;
    private Marker mMarker;

    private ImageView ivRefresh;
    private TextView tvDone;
    private TextView tvLatlong;

    private PermissionsManager permissionsManager;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private BottomNavigationVisibilityListener bottomNavigationVisibilityListener;
    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon dangerIcon;
    private Icon hazardIcon;
    private Icon iconMyloc;
    private int type = 0;
    private LocationService locationService;

    private int viewportWidth;
    private int viewportHeight;
    private CreateTripMarkerModelClass createTripMarkerModelClass;
    private LayoutInflater layoutInflater;
    private static final int REQUEST_UPDATE_MARKER = 102;
    private boolean isMarkerAdd = true;
    private SharedViewModel sharedViewModel;
    private SharedMapViewModel sharedMapViewModel;
    private MapData mapData;
    private Handler handler;
    Runnable runnable;


    private FrameLayout bottomSheetView;
    private Fragment bottomFragment;
    private int elevation = 0;
    private boolean fromBtn = false;

    private CallDrawer callDrawer;

    String markerId = "";

    Gson gson = new Gson();

    List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList;


    //

    private GoogleMap mMap;

    List<LatLng> polygonLatLngList = new ArrayList<>();

    List<LatLng> drawPolygonLatLngList = new ArrayList<>();

    private LatLng southEastLatLng;
    private LatLng northWest;

    private Boolean isFirstTimeBottomSheetInitialize = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fromBtn = getArguments().getBoolean("fromBtn");
        }
    }

    public PolygonMapFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_polygon_map, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //dangerPolygonHashMap = new HashMap<>();
        //listElevation = new ArrayList<>();
        sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        sharedMapViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedMapViewModel.class);


        locationService = new LocationService(Objects.requireNonNull(getActivity()).getApplicationContext());
        fragmentActivityCommunication.fragmentToolbarbyPosition(1);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        initializeView(savedInstanceState, view);

        initializeListener();


        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //checkTripSendReceivingRequest();


      /*  ArrayList<ReceivedShareTripRequest>receivedShareTripRequestList = new ArrayList<>();

        ReceivedShareTripRequest receivedShareTripRequests = new ReceivedShareTripRequest("233","androidtwo","test");
        ReceivedShareTripRequest receivedShareTripRequestsTwo = new ReceivedShareTripRequest("244","androidthree","testee");

        receivedShareTripRequestList.add(receivedShareTripRequests);
        receivedShareTripRequestList.add(receivedShareTripRequestsTwo);

        String receivedShareTripRequestListString = gson.toJson(receivedShareTripRequestList);

        PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST,receivedShareTripRequestListString);
*/

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeView(Bundle savedInstanceState, View view) {


        btnSafe = view.findViewById(R.id.btn_safe);
        btnLandmark = view.findViewById(R.id.btn_landmark);
        btnHazard = view.findViewById(R.id.btn_hazard);
        menuBtn = view.findViewById(R.id.menu_btn);

        selectionLayout = view.findViewById(R.id.selection_layout);


        selectionLayout.setVisibility(View.GONE);


        btnDanger = view.findViewById(R.id.btn_danger);
        tvLatlong = view.findViewById(R.id.tv_lat_long);

        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            tvLatlong.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            tvLatlong.setTextColor(getResources().getColor(R.color.white, null));
        }

        btnHazard.setBackgroundResource(R.drawable.bg_hazard_selected);
        btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);
        btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);
        btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
        btnHazard.setTextColor(getResources().getColor(R.color.orange));
        btnSafe.setTextColor(getResources().getColor(R.color.orange));
        btnDanger.setTextColor(getResources().getColor(R.color.orange));
        btnLandmark.setTextColor(getResources().getColor(R.color.orange));
        type = Constant.POLYGON;

        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        ivRefresh = view.findViewById(R.id.iv_refresh);
        imageViewBack = view.findViewById(R.id.iv_back);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvDone = view.findViewById(R.id.tv_done);
        tvDone.setVisibility(View.VISIBLE);
        ivRefresh.setVisibility(View.GONE);
        imageViewBack.setVisibility(View.VISIBLE);
        // ivRefresh.setBackgroundResource(R.drawable.quantum_ic_arrow_back_grey600_24);
        ivAddUpdate.setVisibility(View.GONE);
        tvTitle.setText(Constant.Polygon);

        bottomSheetView = view.findViewById(R.id.container_polygon_map);
        fabAddressSearch = view.findViewById(R.id.fab_address_search);
        fabLocation = view.findViewById(R.id.fab_my_location);
        fabMore = view.findViewById(R.id.fab_option);
        layoutMenuContent = view.findViewById(R.id.layout_option);
        layoutInflater = (LayoutInflater) Objects.requireNonNull(getContext())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Snackbar.make(view, getResources().getString(R.string.marker_suggestion), Snackbar.LENGTH_LONG)
                .setAction("CLOSE", view1 -> {

                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();

    }

    private void initBottomPicker() {
        bottomFragment = new PolygonMapBottomFragment();
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_polygon_map, bottomFragment);
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
            type = 2;
            //fragmentActivityCommunication.showDone(false);
            tvDone.setVisibility(View.GONE);
        });

        btnHazard.setOnClickListener(v -> {
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

            locationsBeanList = new ArrayList<>();


            if (isFirstTimeBottomSheetInitialize) {

                if (drawPolygonLatLngList.size()> 1) {

                    CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                    createTripMarkerModelClass.setMarkType(4);
                    createTripMarkerModelClass.setTripId(0);
                    createTripMarkerModelClass.setLat(drawPolygonLatLngList.get(0).latitude);
                    createTripMarkerModelClass.setLongX(drawPolygonLatLngList.get(0).longitude);
                    createTripMarkerModelClass.setRadius(0);
                    createTripMarkerModelClass.setAddress(getAddress(drawPolygonLatLngList.get(0), false));
                    createTripMarkerModelClass.setFullAddress(getAddress(drawPolygonLatLngList.get(0), true));
                    createTripMarkerModelClass.setDescription("demo");
                    createTripMarkerModelClass.setZone_name("demo");
                    createTripMarkerModelClass.setZone_area("demo");
                    createTripMarkerModelClass.setZone_length("demo");

                    List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();

                    int count  = 1;
                    for (LatLng latLng : drawPolygonLatLngList) {
                        CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();
                        locationsBean.setLat(latLng.latitude);
                        locationsBean.setLongX(latLng.longitude);
                        locationsBean.setName("Pin "+count);
                        locationsBean.setAddress("");
                        locationsBeanList.add(locationsBean);

                        count++;
                    }
                    createTripMarkerModelClass.setLocations(locationsBeanList);

                    createMarker(createTripMarkerModelClass, 4, 0, 0, 0);

                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_add_at_least_two_marker), Toast.LENGTH_SHORT).show();
                }


            } else {

                bottomSheetView.setVisibility(View.VISIBLE);
                if (bottomFragment != null) {
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().show(bottomFragment).commit();
                } else {
                    initBottomPicker();
                }

                isFirstTimeBottomSheetInitialize = true;

            }


            if (drawPolygonLatLngList.size()> 1) {

                CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                createTripMarkerModelClass.setMarkType(4);
                createTripMarkerModelClass.setTripId(0);
                createTripMarkerModelClass.setLat(drawPolygonLatLngList.get(0).latitude);
                createTripMarkerModelClass.setLongX(drawPolygonLatLngList.get(0).longitude);
                createTripMarkerModelClass.setRadius(0);
                createTripMarkerModelClass.setAddress(getAddress(drawPolygonLatLngList.get(0), false));
                createTripMarkerModelClass.setFullAddress(getAddress(drawPolygonLatLngList.get(0), true));
                createTripMarkerModelClass.setDescription("demo");
                createTripMarkerModelClass.setZone_name("demo");
                createTripMarkerModelClass.setZone_area("demo");
                createTripMarkerModelClass.setZone_length("demo");


                int count  = 1;
                for (LatLng latLng : drawPolygonLatLngList) {
                    CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();
                    locationsBean.setLat(latLng.latitude);
                    locationsBean.setLongX(latLng.longitude);
                    locationsBean.setName("Pin "+count);
                    locationsBean.setAddress("");
                    locationsBeanList.add(locationsBean);

                    count++;
                }
                createTripMarkerModelClass.setLocations(locationsBeanList);

                createMarker(createTripMarkerModelClass, 4, 0, 0, 0);

            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_add_at_least_two_marker), Toast.LENGTH_SHORT).show();
            }
            //fabAddressSearch.show();
        });

        ivRefresh.setOnClickListener(v -> {
            SyncWithInternetFragment syncWithInternetFragment = new SyncWithInternetFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_in_trip", false);
            syncWithInternetFragment.setArguments(bundle);
            syncWithInternetFragment.show(getChildFragmentManager(), null);

        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getFragmentManager().popBackStack();


            }
        });

        /*  fabLocation.setOnClickListener(v -> {

         *//* CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                    .zoom(18)
                    .tilt(30)
                    .build();
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 1000);*//*

        });*/

      /*  fabMore.setOnClickListener(v -> {


            bottomSheetView.setVisibility(View.VISIBLE);
            if (bottomFragment != null) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().show(bottomFragment).commit();
            } else {
                initBottomPicker();
            }
            layoutMenuContent.setVisibility(View.GONE);


        });

        fabAddressSearch.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
     // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(getActivity());
            startActivityForResult(intent, 1);

            *//*try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .build(Objects.requireNonNull(getActivity()));
                startActivityForResult(intent, 1);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }*//*
        });*/


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


    private void loadInitMarker() {
        fragmentActivityCommunication.showProgressDialog(true);
        TripMarkerGetModel mTripMarkerGetModel = new TripMarkerGetModel();
        mTripMarkerGetModel.setSelat(southEastLatLng.latitude);
        mTripMarkerGetModel.setSelon(southEastLatLng.longitude);
        mTripMarkerGetModel.setNwlat(northWest.latitude);
        mTripMarkerGetModel.setNwlon(northWest.longitude);
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
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().hide(bottomFragment).commit();
        }
        layoutMenuContent.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishMarkerAdd(UiManageObject uiManageObject) {
        if (type == 4) {
            if (!polygonLatLngList.isEmpty() && polygonLatLngList.size() > 2) {
                CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                createTripMarkerModelClass.setMarkType(4);
                createTripMarkerModelClass.setTripId(0);
                createTripMarkerModelClass.setLat(polygonLatLngList.get(0).latitude);
                createTripMarkerModelClass.setLongX(polygonLatLngList.get(0).longitude);
                createTripMarkerModelClass.setRadius(0);
                createTripMarkerModelClass.setAddress(getAddress(polygonLatLngList.get(0), false));
                createTripMarkerModelClass.setFullAddress(getAddress(polygonLatLngList.get(0), true));
                createTripMarkerModelClass.setDescription("demo");
                List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();
                for (LatLng latLng : polygonLatLngList) {
                    CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();
                    locationsBean.setLat(latLng.latitude);
                    locationsBean.setLongX(latLng.longitude);
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

  /*  @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeMapStyle(ChangeMapObject floatNumber) {
        if (floatNumber.getType() == 1) {
            map.setStyle(Style.MAPBOX_STREETS, style -> {
                tvLatlong.setTextColor(getResources().getColor(R.color.black, null));
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 0);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_mapbox_streets));

                mapData = new MapData(0, getResources().getString(R.string.mapbox_style_mapbox_streets));
                sharedMapViewModel.select(mapData);
            });

        } else if (floatNumber.getType() == 2) {
            tvLatlong.setTextColor(getResources().getColor(R.color.white, null));

            *//*map.setStyle(Style.SATELLITE, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 1);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite));

                mapData = new MapData(1, getResources().getString(R.string.mapbox_style_satellite));
                sharedMapViewModel.select(mapData);
            });*//*


        } else if (floatNumber.getType() == 3) {
            tvLatlong.setTextColor(getResources().getColor(R.color.white, null));
           *//* map.setStyle(Style.SATELLITE_STREETS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 2);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite_streets));

                mapData = new MapData(2, getResources().getString(R.string.mapbox_style_satellite_streets));
                sharedMapViewModel.select(mapData);
            });*//*

        } else if (floatNumber.getType() == 4) {

            *//*map.setStyle(Style.OUTDOORS, style -> {
                tvLatlong.setTextColor(getResources().getColor(R.color.black, null));
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 3);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_outdoors));

                mapData = new MapData(3, getResources().getString(R.string.mapbox_style_outdoors));
                sharedMapViewModel.select(mapData);
            });*//*
        } else if (floatNumber.getType() == 5) {
            *//*bottomSheetView.setVisibility(View.GONE);
            layoutMenuContent.setVisibility(View.VISIBLE);*//*
            if (bottomFragment.isVisible()) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().hide(bottomFragment).commit();
            }
            layoutMenuContent.setVisibility(View.VISIBLE);


        }
    }*/


    private String getAddress(LatLng point, boolean isFullAddress) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
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
        // map.clear();
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
                      //  loadMarker();
                    } else {
                       // loadMarker();
                    }

                    fragmentActivityCommunication.showProgressDialog(false);
                } else {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }
        });
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    /*private void loadMarker(Style style) {
        List<MarkerDescription> markerDescriptionList = AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoMarker().fetchMarkerByTripId(String.valueOf(0));
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

                    // 3 = danger
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(markerDescription.getLat(), markerDescription.getLongX()))
                            .title(markerDescription.getAddress())
                            .setSnippet(markerDescription.getMarkerId())
                            .icon(dangerIcon));
                    com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter
                            (new LatLng(markerDescription.getLat(), markerDescription.getLongX()), markerDescription.getRadius() / 1000, 64));
                    dangerPolygonHashMap.put(markerDescription.getMarkerId(), polygon);
                } else if (markerDescription.getMarkType() == 4) {

                    // 4 = hazard
                    if (markerDescription.getHazardMarkerPointList() != null) {
                        List<LatLng> latLngs = new ArrayList<>();


                        for (CreateTripMarkerModelClass.LocationsBean locationsBean : markerDescription.getHazardMarkerPointList()) {
                            Marker marker = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(locationsBean.getLat(), locationsBean.getLongX()))
                                    .setTitle(Utils.getAddress(locationsBean.getLat(), locationsBean.getLongX(), true, RouteApplication.getInstance().getApplicationContext()))
                                    .setSnippet(markerDescription.getMarkerId())
                                    .icon(hazardIcon));
                            latLngs.add(new LatLng(locationsBean.getLat(), locationsBean.getLongX()));


                        }



                      *//*  com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = map.addPolygon(new PolygonOptions()
                                .addAll(latLngs)
                                .fillColor(Color.YELLOW)
                                .alpha(0.2f));*//*

                        List<List<LatLng>> OuterlatLngs = new ArrayList<>();
                        OuterlatLngs.add(latLngs);


                        FillManager fillManager = new FillManager(mapView, map, style);


                        FillOptions fillOptions = new FillOptions()
                                .withLatLngs(OuterlatLngs)
                                .withFillColor("#FFFFFF");
                        fillManager.create(fillOptions);

                        List<FillOptions> fillOptionsList = new ArrayList<>();

                        for (int i = 0; i < 20; i++) {
                            int color = Color.argb(255, new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
                            fillOptionsList.add(new FillOptions()
                                    .withLatLngs(OuterlatLngs)
                                    .withFillColor("#FFFFFF")


                            );
                        }

                        fillManager.create(fillOptionsList);


                    }
                }

            }
        }

    }*/

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

    /*private void loadMarker() {

        List<MarkerDescription> markerDescriptionList = AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoMarker().fetchMarkerByTripId(String.valueOf(0));

        if (markerDescriptionList != null && markerDescriptionList.size() > 0) {

            for (MarkerDescription markerDescription : markerDescriptionList) {

                if (markerDescription.getMarkType() == 4) {


                    polygonLatLngList.clear();


                    // 4 = hazard
                    if (markerDescription.getHazardMarkerPointList() != null) {

                        // List<LatLng> latLngs = new ArrayList<>();


                         List<String>polygonPointIdList = new ArrayList<>();
                         List<String>polygonPointNameList = new ArrayList<>();





                        for (CreateTripMarkerModelClass.LocationsBean locationsBean : markerDescription.getHazardMarkerPointList()) {


                            polygonLatLngList.add(new LatLng(locationsBean.getLat(), locationsBean.getLongX()));
                            //polygonPointIdList.add(locationsBean.getId());
                            polygonPointNameList.add(locationsBean.getName());

                           // Log.e("FromDb", "Poin Id "+ locationsBean.getId());


                        }

                        Polygon polygon1 = null;


                        PolygonOptions poly = new PolygonOptions();


                        for (LatLng latLng : polygonLatLngList) {

                            poly.add(new LatLng(latLng.latitude, latLng.longitude));

                        }

                        poly.clickable(true);

                        polygon1 = mMap.addPolygon(poly);

                        for (int i = 0; i < polygonLatLngList.size(); i++) {

                            Log.e("PolygonPoint", "id " + polygonPointIdList.get(i));


                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(polygonLatLngList.get(i).latitude, polygonLatLngList.get(i).longitude))
                                    .title(polygonPointNameList.get(i))
                                    .snippet(polygonPointIdList.get(i))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hazard_60)));

                        }


                        //for mid point marker set
                        for (int i = 0; i < polygonLatLngList.size(); i++) {

                            if (i == polygonLatLngList.size() - 1) {


                                double distance = calculateDistanceDifferenceInMeter(polygonLatLngList.get(i).longitude,
                                        polygonLatLngList.get(i).latitude,
                                        polygonLatLngList.get(0).longitude,
                                        polygonLatLngList.get(0).latitude);


                                mMap.addMarker(new MarkerOptions()
                                        .position(midPoint(polygonLatLngList.get(i).latitude, polygonLatLngList.get(i).longitude,
                                                polygonLatLngList.get(0).latitude, polygonLatLngList.get(0).longitude))
                                        .icon(BitmapDescriptorFactory.fromBitmap(
                                                getBitmapFromView(String.valueOf(format("%.2f", distance))))));


                            } else {

                                double distance = calculateDistanceDifferenceInMeter(polygonLatLngList.get(i).longitude,
                                        polygonLatLngList.get(i).latitude,
                                        polygonLatLngList.get(i + 1).longitude,
                                        polygonLatLngList.get(i + 1).latitude);

                                mMap.addMarker(new MarkerOptions()
                                        .position(midPoint(polygonLatLngList.get(i).latitude, polygonLatLngList.get(i).longitude,
                                                polygonLatLngList.get(i + 1).latitude, polygonLatLngList.get(i + 1).longitude))
                                        .snippet("d")
                                        .icon(BitmapDescriptorFactory.fromBitmap(
                                                getBitmapFromView(String.valueOf(format("%.2f", distance))))));
                            }
                        }


                        // Store a data object with the polygon, used here to indicate an arbitrary type.
                        polygon1.setTag("alpha");
                        // [END maps_poly_activity_add_polygon]
                        // Style the polygon.

                        if (polygonLatLngList.size() > 2) {

                            stylePolygon(polygon1, true);

                        } else {

                            stylePolygon(polygon1, false);

                        }


                    }

                     *//*FragmentTransaction ft = getFragmentManager().beginTransaction();
                     ft.detach(this).attach(this).commit();*//*

                   // setInfoWindowOnMarker(mMap);



                        *//*for (String distanceDiff : distanceInMeter) {

                            Log.e("disDIff", distanceDiff);
                        }*//*


                    // }
                }

            }
        }


    }*/


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

                        int count = 0;

                        if(type  == 4){

                            for(CreateTripMarkerModelClass.LocationsBean locationsBean : locationsBeanList){


                               // locationsBean.setId(data.getSuccess().getLocations().get(count).getId().toString());

                                count++;
                            }
                        }


                        Log.e("Pointer Data", data.getSuccess().getLocations().toString());


                        String tripResponse = gson.toJson(createTripMarkerModelClass);

                        Data markerData = new Data.Builder()
                                .putString(Constant.KEY_MARKER_RESPONSE, tripResponse)
                                .putString(Constant.KEY_TRIP_MARKER_ID, String.valueOf(data.getSuccess().getMarkerID()))
                                .build();

                        final OneTimeWorkRequest addMarkerWorker = new OneTimeWorkRequest.Builder(AddMarkerWorker.class)
                                .setInputData(markerData)
                                .build();

                        WorkManager.getInstance().enqueue(addMarkerWorker);

                        WorkManager.getInstance().getWorkInfoByIdLiveData(addMarkerWorker.getId()).observe(Objects.requireNonNull(getActivity()), workInfo -> {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                switch (type) {


                                    case 4:
                                        //map.clear();
                                        //loadMarker();
                                        //OUTER_POINTS.clear();
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

        if (context instanceof BottomNavigationVisibilityListener) {
            bottomNavigationVisibilityListener = (BottomNavigationVisibilityListener) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }

        /*try {
            callDrawer = (CallDrawer) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }*/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            // map.getStyle(this::enableLocationComponent);
        } else {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showTraffic() {
        //trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
    }

    @Override
    public void onStart() {
        super.onStart();
        //  loadMapData();
        EventBus.getDefault().register(this);
        handler = new Handler();

/*        Runnable runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                getElevation();
                handler.postDelayed(this, 15000);
            }
        };
        handler.postDelayed(runnable, 15000);*/
    }

    private void loadMapData() {
        sharedViewModel.getSelected().observe(this, mapData -> {
            /*if (map != null) {
                map.setStyle(mapData.getStyle(), style -> {
                    EventBus.getDefault().post(mapData);
                });
            }*/
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //  mapView.onResume();


        bottomNavigationVisibilityListener.showBottomNavigation(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        // type = 1;
        //mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        //mapView.onStop();
    }

    @Override
    public void onDestroyView() {

        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroyView();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;
        bottomFragment = null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                        " \n" + "Elevation " + elevation + " meter");
            } else {
                tvLatlong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                        " \n" + "Elevation " + elevation * 3.28084 + " feet");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_btn:

                callDrawer.openDrawer();
                break;
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLngBounds curScreen = mMap.getProjection().getVisibleRegion().latLngBounds;
        northWest = curScreen.northeast;
        southEastLatLng = curScreen.southwest;


        if (PreferenceManager.getBool(Constant.KEY_IS_MARKER_LOAD)) {
            //loadMarker();
        }
        else {
            loadInitMarker();
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(22.3795, 91.8079), 18));


        // setInfoWindowOnMarker(googleMap);

       /* googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.marker_popup, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                TextView tvDetails = v.findViewById(R.id.tv_edit);

                // Getting reference to the TextView to set latitude
                TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);
                TextView tvRemove = v.findViewById(R.id.tv_delete);
                tvAddress.setText(arg0.getTitle());
                tvDetails.setText("Details");
                tvRemove.setText(arg0.getSnippet());





                // Getting reference to the TextView to set longitude
               // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                // Setting the latitude
               // tvLat.setText("Latitude:" + latLng.latitude);

                // Setting the longitude
                //tvLng.setText("Longitude:"+ latLng.longitude);

                // Returning the view containing InfoWindow contents
                return v;

            }
        });*/


        mMap.setOnMarkerClickListener(marker -> {

            mMarker = marker;

           // marker.showInfoWindow();

            if(mMarker.getSnippet() != null) {

                setInfoWindowOnMarker(mMap);

            }else{

                mMap.clear();
                //loadMarker();

            }
            Log.e("data", "on marker click");

            return true;
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {



                Toast.makeText(getActivity(), "Coming Soon !", Toast.LENGTH_SHORT).show();



                //Log.e("PolygonMap", "marker.getSnippet() : " + marker.getSnippet());

            /*    Intent intent = new Intent(getActivity(), LandmarkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("marker_id", mMarker.getSnippet());
                bundle.putLong("long_marker", 231);
                bundle.putBoolean("is_marker_from_trip", false);
                intent.putExtras(bundle);
                mMarker.hideInfoWindow();
                startActivityForResult(intent, REQUEST_UPDATE_MARKER);*/
                // arg0.hideInfoWindow();
            }

        });


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(com.google.android.gms.maps.model.LatLng latLng) {


                drawPolygonLatLngList.add(new LatLng(latLng.latitude, latLng.longitude));

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hazard_60)));
                //.title("Your marker title")
                //.snippet("Your marker snippet"));
            }
        });

    }


    public interface CallDrawer {
        void openDrawer();

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

        //print out in degrees
    }


    // [START maps_poly_activity_on_polyline_click]
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);


    // [START maps_poly_activity_style_polygon]
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

    // [START maps_poly_activity_style_polyline]
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;


    /**
     * Styles the polygon, based on type.
     * @param polygon The polygon object that needs styling.
     */

    /**
     * Styles the polygon, based on type.
     *
     * @param polygon The polygon object that needs styling.
     */
    private void stylePolygon(Polygon polygon, Boolean isPolygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                //pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;
        }

        //polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);

        if (isPolygon) {

            showText();
        }
    }
    // [END maps_poly_activity_style_polygon]


    private void showText() {


        /*List<LatLng>latLngList1 = new ArrayList<>();


        latLngList1.add(new LatLng(22.3795,91.8079));
        latLngList1.add(new LatLng(22.3792,91.8085));
        latLngList1.add(new LatLng(22.3800,91.8084));

*/


        double area = SphericalUtil.computeArea(polygonLatLngList);

        //Log.i(TAG, "computeArea " + SphericalUtil.computeArea(latLngs));


        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (LatLng latLng : polygonLatLngList) {
            builder.include(latLng);
        }

        mMap.addGroundOverlay(new GroundOverlayOptions()
                .positionFromBounds(builder.build())
                .image(BitmapDescriptorFactory.fromBitmap(
                        getBitmapFromViewForArea(String.valueOf(format("%.2f", area)))
                        )
                )
                .zIndex(100)
        );
    }

    private Bitmap getBitmapFromView(String value) {
        //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

        View customView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text, null);

        TextView textView = customView.findViewById(R.id.textView);
        textView.setText(value + " " + "m ");
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        Log.e("PolygonMap", "onActivityResult");


        if (resultCode == RESULT_OK && requestCode == REQUEST_UPDATE_MARKER) {
            if (data != null) {
                boolean isEdit = data.getBooleanExtra("is_edit", false);
                long markerId = data.getLongExtra("long_marker", 0);
                String serverMarkerID = data.getStringExtra("server_marker_id");
                int markerType = data.getIntExtra("marker_type", 0);
                double lat = data.getDoubleExtra("lat", 0);
                double lon = data.getDoubleExtra("lon", 0);
                if (!isEdit) {

                    if (markerType == 4) {
                       // loadMarker();
                    }
                } else {

                }
            }
        }
    }

    private void setInfoWindowOnMarker(GoogleMap map) {

        Log.e("polygonMap", "Setinfo called");


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                //mMarker = arg0;

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.marker_popup, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                TextView tvDetails = v.findViewById(R.id.tv_edit);

                // Getting reference to the TextView to set latitude
                TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);
                TextView tvRemove = v.findViewById(R.id.tv_delete);
                tvAddress.setText(mMarker.getTitle());
                tvDetails.setText("Edit");
                tvRemove.setText(mMarker.getSnippet());

                Log.e("getSnippedt", "MarkerID :"+ arg0.getSnippet());






                // Getting reference to the TextView to set longitude
                // TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                // Setting the latitude
                // tvLat.setText("Latitude:" + latLng.latitude);

                // Setting the longitude
                //tvLng.setText("Longitude:"+ latLng.longitude);

                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        mMarker.showInfoWindow();


    }


}


