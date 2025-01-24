package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

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
import com.google.gson.Gson;
import com.google.maps.android.SphericalUtil;
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
/*
import com.mapbox.mapboxsdk.constants.Style;
*/
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.LandmarkActivity;
import com.techacsent.route_recon.adapter.UpdatedWaypointAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.DangerMarkerObj;
import com.techacsent.route_recon.event_bus_object.HazardNameObject;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.ElevationResponse;
import com.techacsent.route_recon.model.WaypointResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiOkhttpCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.retrofit_api.RouteHttpCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


import okhttp3.Call;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static com.techacsent.route_recon.utills.Utils.calculateDistanceDifferenceInMeter;
import static java.lang.String.format;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaypointFragment extends BaseWaypointFragment implements OnRecyclerClickListener<WaypointModel.WayPointsBean>, View.OnClickListener {
    private Button btnNext;
    private LinearLayout confirmationLayout;

    private LinearLayout layoutSegmented;
    private Button btnSafe;
    private Button btnLandmark;
    private Button btnHazard;
    private Button btnDanger;
    private ImageButton searchBtn;

    private RecyclerView rvWaypoints;
    private UpdatedWaypointAdapter mAdapter;
    private List<WaypointModel.WayPointsBean> wayPointsListBeans;
    private Button btnYes;
    private Button btnWayPoint;
    private View waypointFooter;
    private TextView tvCreate;
    private TextView tvSuggestion;
    private Button btnAddHazzardmarker;
    private TextView tvLatLon;

    private LayoutInflater layoutInflater;
    Handler handler = new Handler();
    private boolean isWaypoint = false;
    private List<MarkerDescription> markerObjectList;
    private static final int REQUEST_UPDATE_MARKER = 102;

    private CreateTripMarkerModelClass createTripMarkerModelClass;
    private LocationService locationService;
    private List<Integer> listElevation;
    private TrafficPlugin trafficPlugin;

    private  String hazardArea = "";
    private  String hazardLenght = "";

    private LinearLayout linearLayoutHazardConfrimTripCreate;
    private ImageView imageViewHazardConfirm;
    private ImageView imageViewHazardCancel;

    //for giving hazard info
    private Fragment hazardInfoBottomFragment;
    private FrameLayout bottomSheetView;
    String hazardAreaName = "";
    private Boolean isFirstTimeBottomSheetInitialize = false;




    public WaypointFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(requireActivity(), Constant.MAPBOX_API_KEY);
        if (getArguments() != null) {
            isUpdate = getArguments().getBoolean(Constant.KEY_IS_UPDATE);
            dataBean = getArguments().getParcelable(Constant.KEY_PARCEL_CREATE_TRIP_RESPONSE);
        }
        waypointDescription = AppDatabase.getAppDatabase(getActivity().getApplicationContext()).daoWaypoint().fetchWaypointById(dataBean.getId());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_waypoint, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wayPointsListBeans = new ArrayList<>();
        listElevation = new ArrayList<>();
        locationService = new LocationService(requireActivity().getApplicationContext());
        hashMap = new HashMap<>();
        waypointHashMap = new HashMap<>();
        polygonList = new ArrayList<>();
        pointList = new ArrayList<>();
        markerObjectList = new ArrayList<>();
        initializeView(savedInstanceState, view);
        initializeListener();
        initializeMap();
        initiateSwipe();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeView(Bundle savedInstanceState, View view) {
        layoutInflater = (LayoutInflater) requireContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //mapView.setStyleUrl(Style.MAPBOX_STREETS);
        btnNext = view.findViewById(R.id.btn_next);
        confirmationLayout = view.findViewById(R.id.confirmation_layout);
        btnAddHazzardmarker = view.findViewById(R.id.btn_add_hazard);

        layoutSegmented = view.findViewById(R.id.layout_segment);
        btnSafe = view.findViewById(R.id.btn_safe);
        btnLandmark = view.findViewById(R.id.btn_landmark);
        btnHazard = view.findViewById(R.id.btn_hazard);
        btnDanger = view.findViewById(R.id.btn_danger);
        searchBtn = view.findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(this);

        btnSafe.setBackgroundResource(R.drawable.bg_safe_selected);
        btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
        btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
        btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);

        btnYes = view.findViewById(R.id.btn_yes);
        btnWayPoint = view.findViewById(R.id.btn_add_waypoints);
        rvWaypoints = view.findViewById(R.id.rv_waypoints);
        waypointFooter = view.findViewById(R.id.waypoint_footer);
        tvCreate = view.findViewById(R.id.tv_create);
        tvSuggestion = view.findViewById(R.id.tv_suggestion);
        tvLatLon = view.findViewById(R.id.tv_lat_lon);
        tvSuggestion.setVisibility(View.GONE);

        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            tvLatLon.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            tvLatLon.setTextColor(getResources().getColor(R.color.white, null));
        }

        linearLayoutHazardConfrimTripCreate = view.findViewById(R.id.linearLayoutHazardConfirmTripCreate);

        imageViewHazardConfirm = view.findViewById(R.id.imageViewHazardConfirmTripCreate);
        imageViewHazardCancel = view.findViewById(R.id.imageViewHazardCancelTripCreate);

        bottomSheetView = view.findViewById(R.id.container_way_point);



        rvWaypoints.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWaypoints.setItemAnimator(new DefaultItemAnimator());
        rvWaypoints.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        IconFactory iconFactory = IconFactory.getInstance(requireActivity());
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        hazardIcon = iconFactory.fromResource(R.drawable.hazard_60);
        dangerIcon = iconFactory.fromResource(R.drawable.delete_marker);
        startMarker = iconFactory.fromResource(R.mipmap.start_trip_marker);
        waypointMarker = iconFactory.fromResource(R.mipmap.waypoint_marker);
        endMarker = iconFactory.fromResource(R.mipmap.end_trip_marker);
        if (isUpdate) {
            if (waypointDescription != null && waypointDescription.getWayPointsBeanList() != null && waypointDescription.getWayPointsBeanList().size() > 0) {
                mAdapter = new UpdatedWaypointAdapter(getActivity());
                wayPointsListBeans.addAll(waypointDescription.getWayPointsBeanList());
                mAdapter.setItems(wayPointsListBeans);
                mAdapter.setListener(this);
                rvWaypoints.setAdapter(mAdapter);
            }
        }
        Snackbar.make(view, getResources().getString(R.string.marker_suggestion), Snackbar.LENGTH_INDEFINITE)
                .setAction("CLOSE", view1 -> {

                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
        //handler.postDelayed(() -> tvSuggestion.setVisibility(View.GONE), 3000);
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
                        if (PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                            tvLatLon.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + Collections.max(listElevation) + " meter");
                        } else {
                            tvLatLon.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + Collections.max(listElevation) / 0.3048 + " feet");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }));



    }

    private void initiateSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvWaypoints);
    }

    private void moveItem(int oldPosition, int newPosition) {
        WaypointModel.WayPointsBean item = wayPointsListBeans.get(oldPosition);
        wayPointsListBeans.remove(item);
        wayPointsListBeans.add(newPosition, item);
        //mAdapter.notifyItemMoved(oldPosition, newPosition);
        mAdapter.itemMoved(oldPosition, newPosition);
       /* mAdapter.clear();
        mAdapter.setItems(wayPointsListBeans);*/
        updateRoute();
    }

    private void initializeListener() {
        btnSafe.setOnClickListener(v -> {
            type = 1;
            btnSafe.setBackgroundResource(R.drawable.bg_safe_selected);
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);
            btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnSafe.setTextColor(getResources().getColor(R.color.white));
            btnLandmark.setTextColor(getResources().getColor(R.color.orange));
            btnDanger.setTextColor(getResources().getColor(R.color.orange));

            btnAddHazzardmarker.setVisibility(View.GONE);
        });

        btnLandmark.setOnClickListener(v -> {
            type = 2;
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_selected);
            btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);
            btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);
            btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnLandmark.setTextColor(getResources().getColor(R.color.white));
            btnSafe.setTextColor(getResources().getColor(R.color.orange));
            btnDanger.setTextColor(getResources().getColor(R.color.orange));
            btnHazard.setTextColor(getResources().getColor(R.color.orange));

            btnAddHazzardmarker.setVisibility(View.GONE);
        });

        btnHazard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 4;
                btnHazard.setBackgroundResource(R.drawable.bg_hazard_selected);
                btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
                btnDanger.setBackgroundResource(R.drawable.bg_danger_unselected);
                btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);

                btnHazard.setTextColor(getResources().getColor(R.color.orange));
                btnSafe.setTextColor(getResources().getColor(R.color.orange));
                btnLandmark.setTextColor(getResources().getColor(R.color.orange));
                btnDanger.setTextColor(getResources().getColor(R.color.orange));

                btnAddHazzardmarker.setVisibility(View.VISIBLE);

            }
        });

        btnDanger.setOnClickListener(v -> {
            type = 3;
            btnDanger.setBackgroundResource(R.drawable.bg_danger_selected);
            btnSafe.setBackgroundResource(R.drawable.bg_safe_unselected);
            btnLandmark.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnHazard.setBackgroundResource(R.drawable.bg_landmark_unselected);
            btnDanger.setTextColor(getResources().getColor(R.color.white));
            btnSafe.setTextColor(getResources().getColor(R.color.orange));
            btnHazard.setTextColor(getResources().getColor(R.color.orange));
            btnLandmark.setTextColor(getResources().getColor(R.color.orange));

            btnAddHazzardmarker.setVisibility(View.GONE);
        });
        btnNext.setOnClickListener(v -> {
            confirmationLayout.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
            layoutSegmented.setVisibility(View.GONE);
            btnAddHazzardmarker.setVisibility(View.GONE);

        });

        btnWayPoint.setOnClickListener(v -> {
            isWaypoint = true;
            confirmationLayout.setVisibility(View.GONE);
            waypointFooter.setVisibility(View.VISIBLE);
            tvSuggestion.setVisibility(View.VISIBLE);
            tvSuggestion.setText(getResources().getString(R.string.waypoint_suggestion));
            handler.postDelayed(() -> tvSuggestion.setVisibility(View.GONE), 3000);
        });
        tvCreate.setOnClickListener(v -> createUpdateWaypoint());
        btnYes.setOnClickListener(v -> {
            /*if (!isUpdate) {
                waypointDescription.setTripId(Integer.parseInt(dataBean.getId()));
                if (wayPointsListBeans != null) {
                    waypointDescription.setWayPointsBeanList(wayPointsListBeans);
                }
                waypointDescription.setTripJson(encodedPath);
                AppDatabase.getAppDatabase(getActivity()).daoWaypoint().insertWaypoint(waypointDescription);
            }*/
            createUpdateWaypoint();
            //loadSuccessFragment();
        });

        btnAddHazzardmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!OUTER_POINTS.isEmpty() && OUTER_POINTS.size() > 1) {
                    //addHazzardMarker();

                    loadPolygonBeforeSaveInServer(OUTER_POINTS);



                    linearLayoutHazardConfrimTripCreate.setVisibility(View.VISIBLE);

                    bottomSheetView.setVisibility(View.VISIBLE);

                    btnAddHazzardmarker.setVisibility(View.GONE);

                    if (hazardInfoBottomFragment != null) {

                        getActivity().getSupportFragmentManager().beginTransaction().remove(hazardInfoBottomFragment).commit();


                    }
                    initHazardInfoBottomSheet();





                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_add_at_least_two_marker), Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageViewHazardConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (hazardAreaName.isEmpty()) {

                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "Please provide zone name", Toast.LENGTH_SHORT).show();

                } else {



                    CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
                    createTripMarkerModelClass.setMarkType(4);
                    createTripMarkerModelClass.setTripId(Integer.parseInt(dataBean.getId()));
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

                    btnAddHazzardmarker.setVisibility(View.GONE);

                    bottomSheetView.setVisibility(View.GONE);
                    linearLayoutHazardConfrimTripCreate.setVisibility(View.GONE);

                    btnAddHazzardmarker.setVisibility(View.VISIBLE);

                    hazardAreaName= "";
                    hazardLenght = "";
                    hazardArea = "";


                }


            }
        });

        imageViewHazardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnAddHazzardmarker.setVisibility(View.VISIBLE);

                bottomSheetView.setVisibility(View.GONE);

                linearLayoutHazardConfrimTripCreate.setVisibility(View.GONE);

                hazardAreaName= "";
                hazardLenght = "";
                hazardArea = "";
                OUTER_POINTS.clear();
                map.clear();
                type =4;

                //loadMarker();



                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "cancel clicked", Toast.LENGTH_SHORT).show();


            }
        });

    }

    protected void insertWaypointIntoRoom(WaypointResponse data) {
        WaypointDescription waypointDescription = new WaypointDescription();
        waypointDescription.setTripId(Integer.parseInt(dataBean.getId()));
        waypointDescription.setWayPointsBeanList(wayPointsListBeans);
        waypointDescription.setTripJson(data.getTripJson());
        AppDatabase.getAppDatabase(getActivity()).daoWaypoint().insertWaypoint(waypointDescription);
    }

    protected void createUpdateWaypoint() {
        navigationOptionInterface.showProgressDialog(true);
        WaypointModel waypointModel = new WaypointModel();
        waypointModel.setTripId(dataBean.getId());
        waypointModel.setWayPoints(wayPointsListBeans);
        waypointModel.setTripJson(encodedPath);
        ApiService apiService = new ApiCaller();
        apiService.createWaypoint(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), waypointModel, new ResponseCallback<WaypointResponse>() {
            @Override
            public void onSuccess(WaypointResponse data) {
                if (isUpdate) {
                    WaypointDescription waypointDescription = AppDatabase.getAppDatabase(requireActivity().getApplicationContext()).daoWaypoint().fetchWaypointById(dataBean.getId());
                    if (waypointDescription != null) {
                        waypointDescription.setTripId(Integer.parseInt(dataBean.getId()));
                        waypointDescription.setWayPointsBeanList(wayPointsListBeans);
                        waypointDescription.setTripJson(data.getTripJson());
                        AppDatabase.getAppDatabase(getActivity().getApplicationContext()).daoWaypoint().updateWaypoint(waypointDescription);
                    } else {
                        insertWaypointIntoRoom(data);
                    }

                } else {
                    insertWaypointIntoRoom(data);
                }
                navigationOptionInterface.showProgressDialog(false);
                loadSuccessFragment();
            }

            @Override
            public void onError(Throwable th) {
                navigationOptionInterface.showProgressDialog(false);
                th.printStackTrace();
            }
        });
    }

    protected void updateRoute() {
        pointList.clear();
        for (int i = 0; i < wayPointsListBeans.size(); i++) {
            LatLng waypointLatLng = new LatLng(wayPointsListBeans.get(i).getLat(), wayPointsListBeans.get(i).getLongX());
            Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
            pointList.add(wayPoint);
        }
        addStartPointMarker(originPosition);
        addDestinationMarker(destinationPosition);

        if (pointList.size() > 0) {
            getRoute(originPosition, destinationPosition, pointList);
        } else {
            getRoute(originPosition, destinationPosition, null);
        }

    }


    private void initializeMap() {
        mapView.getMapAsync(mapboxMap -> {
            map = mapboxMap;
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setLogoEnabled(false);
            mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
                trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
                getElevation();
                loadRoute();
                /*CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(dataBean.getStartpoint().getLat(), dataBean.getStartpoint().getLongX()))
                        .zoom(16)
                        .tilt(30)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 1000);*/

                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                        .include(new LatLng(dataBean.getStartpoint().getLat(), dataBean.getStartpoint().getLongX()))
                        .include(new LatLng(dataBean.getEndpoint().getLat(), dataBean.getEndpoint().getLongX()))
                        .build();

                mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), Constant.TOTAL_TIME_INTERVAL);
                if (isUpdate) {
                    loadMarkerFromDB();
                }
                mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);
                mapboxMap.addOnMapLongClickListener(point -> {
                    if (!isWaypoint) { //&& !isUpdate
                        if (createTripMarkerModelClass == null) {
                            createTripMarkerModelClass = new CreateTripMarkerModelClass();
                        }
                        createTripMarkerModelClass.setDescription(getResources().getString(R.string.description));
                        createTripMarkerModelClass.setLat(point.getLatitude());
                        createTripMarkerModelClass.setLongX(point.getLongitude());
                        createTripMarkerModelClass.setMarkType(type);
                        createTripMarkerModelClass.setAddress(Utils.getAddress(point.getLatitude(),point.getLongitude(), false, RouteApplication.getInstance()
                                .getApplicationContext()));
                        createTripMarkerModelClass.setFullAddress(Utils.getAddress(point.getLatitude(),point.getLongitude(), true, RouteApplication.getInstance()
                                .getApplicationContext()));
                        createTripMarkerModelClass.setTripId(Integer.parseInt(dataBean.getId()));
                        createTripMarkerModelClass.setRadius(0);
                        switch (type) {
                            case 1:
                                createMarker(createTripMarkerModelClass, point.getLatitude(), point.getLongitude(), 1, 0);
                                break;
                            case 2:
                                createMarker(createTripMarkerModelClass, point.getLatitude(), point.getLongitude(), 2, 0);
                                break;
                            case 3:
                                SetRadiusFragment setRadiusFragment = new SetRadiusFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(Constant.KEY_TYPE_OF_RADIUS, 2);
                                bundle.putDouble(Constant.KEY_LATITUDE, point.getLatitude());
                                bundle.putDouble(Constant.KEY_LONGITUDE, point.getLongitude());
                                setRadiusFragment.setArguments(bundle);
                                setRadiusFragment.show(getChildFragmentManager(), null);
                                break;

                            case 4:
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(point)
                                        .title(Utils.getAddress(point.getLatitude(),point.getLongitude(), true, RouteApplication.getInstance().getApplicationContext()))
                                        .icon(hazardIcon));
                                OUTER_POINTS.add(point);
                                break;
                        }
                    } else {
                        WaypointModel.WayPointsBean wayPointsBean = new WaypointModel.WayPointsBean();
                        wayPointsBean.setLat(point.getLatitude());
                        wayPointsBean.setLongX(point.getLongitude());
                        wayPointsBean.setType(1);
                        wayPointsBean.setAddress(Utils.getAddress(point.getLatitude(),point.getLongitude(), false, getActivity()));
                        wayPointsBean.setFullAddress(Utils.getAddress(point.getLatitude(),point.getLongitude(), true, getActivity()));
                        Marker marker = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(point.getLatitude(), point.getLongitude()))
                                .title(Utils.getAddress(point.getLatitude(),point.getLongitude(), false, getActivity()))
                                .snippet("0")
                                .icon(waypointMarker));
                        waypointHashMap.put(point.getLatitude(), marker.getId());
                        wayPointsListBeans.add(wayPointsBean);
                        if (mAdapter == null) {
                            initializeAdapter();
                        } else {
                            mAdapter.add(wayPointsBean);
                        }
                        updateRoute();
                    }
                    return false;
                });
                mapboxMap.setInfoWindowAdapter(marker -> {
                    @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                    TextView tvAddress = view.findViewById(R.id.tv_address);
                    TextView tvRemove = view.findViewById(R.id.tv_delete);
                    TextView tvEdit = view.findViewById(R.id.tv_edit);
                    TextView tvFullAddress = view.findViewById(R.id.tv_full_address);
                    tvRemove.setVisibility(View.GONE);
                    tvAddress.setText(marker.getTitle());
                    tvFullAddress.setText(marker.getTitle());
                    if(marker.getSnippet() != null) {

                        if (marker.getSnippet().equals("0")) {
                            tvEdit.setVisibility(View.GONE);
                        }
                    }
                    tvEdit.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), LandmarkActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("marker_id", marker.getSnippet());
                        bundle.putLong("long_marker", marker.getId());
                        bundle.putBoolean("is_marker_from_trip", true);
                        bundle.putBoolean("is_marker_from_polygon_pin", false);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, REQUEST_UPDATE_MARKER);
                        marker.hideInfoWindow();
                    });
                    return view;
                });
            });

        });
    }


    private void initializeAdapter() {
        mAdapter = new UpdatedWaypointAdapter(getActivity());
        mAdapter.setItems(wayPointsListBeans);
        mAdapter.setListener(this);
        rvWaypoints.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(WaypointModel.WayPointsBean item) {
        mAdapter.remove(item);
        wayPointsListBeans.remove(item);
        updateRoute();
        if (waypointHashMap.get(item.getLat()) != null) {
            long markerid = waypointHashMap.get(item.getLat());
            map.removeAnnotation(markerid);
        }
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
        getActivity();
        if (requestCode == REQUEST_UPDATE_MARKER) {
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
                            com.mapbox.mapboxsdk.annotations.Polygon polygon = hashMap.get(serverMarkerID);
                            map.removePolygon(polygon);
                        }
                    }
                    if (markerType == 4) {
                        map.clear();
                        loadMarkerFromDB();
                        placeRouteMarker();
                        drawOptimizedRoute();
                        //loadRoute();
                    }
                    map.removeAnnotation(markerId);
                } else {
                    if (markerType == 3) {
                        if (hashMap.containsKey(serverMarkerID)) {
                            com.mapbox.mapboxsdk.annotations.Polygon polygon = hashMap.get(serverMarkerID);
                            map.removePolygon(polygon);
                        }
                        double radius = data.getDoubleExtra("radius", 0);
                        hashMap.remove(serverMarkerID);
                        com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(lat, lon), radius / 1000, 64));
                        polygonList.add(polygon);
                        hashMap.put(serverMarkerID, polygon);
                    }
                }
            }

        }
        else if ( requestCode == 1) {


            if (data != null) {

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
                if (!isWaypoint) { //&& !isUpdate
                    if (createTripMarkerModelClass == null) {
                        createTripMarkerModelClass = new CreateTripMarkerModelClass();
                    }
                    createTripMarkerModelClass.setDescription(getResources().getString(R.string.description));
                    createTripMarkerModelClass.setLat(gLat);
                    createTripMarkerModelClass.setLongX(gLon);
                    createTripMarkerModelClass.setMarkType(type);
                    createTripMarkerModelClass.setAddress(Utils.getAddress(gLat, gLon, false, RouteApplication.getInstance()
                            .getApplicationContext()));
                    createTripMarkerModelClass.setFullAddress(Utils.getAddress(gLat, gLon, true, RouteApplication.getInstance()
                            .getApplicationContext()));
                    createTripMarkerModelClass.setTripId(Integer.parseInt(dataBean.getId()));
                    createTripMarkerModelClass.setRadius(0);
                    switch (type) {
                        case 1:
                            createMarker(createTripMarkerModelClass, gLat, gLon, 1, 0);
                            break;
                        case 2:
                            createMarker(createTripMarkerModelClass, gLat, gLon, 2, 0);
                            break;
                        case 3:
                            SetRadiusFragment setRadiusFragment = new SetRadiusFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constant.KEY_TYPE_OF_RADIUS, 2);
                            bundle.putDouble(Constant.KEY_LATITUDE, gLat);
                            bundle.putDouble(Constant.KEY_LONGITUDE, gLon);
                            setRadiusFragment.setArguments(bundle);
                            setRadiusFragment.show(getChildFragmentManager(), null);
                            break;

                        case 4:
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(gLat, gLon))
                                    .title(Utils.getAddress(gLat, gLon, true, RouteApplication.getInstance().getApplicationContext()))
                                    .icon(hazardIcon));
                            OUTER_POINTS.add(new LatLng(gLat, gLon));
                            break;
                    }
                } else {
                    WaypointModel.WayPointsBean wayPointsBean = new WaypointModel.WayPointsBean();
                    wayPointsBean.setLat(gLat);
                    wayPointsBean.setLongX(gLon);
                    wayPointsBean.setType(1);
                    wayPointsBean.setAddress(Utils.getAddress(gLat, gLon, false, getActivity()));
                    wayPointsBean.setFullAddress(Utils.getAddress(gLat, gLon, true, getActivity()));
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(gLat, gLon))
                            .title(Utils.getAddress(gLat, gLon, false, getActivity()))
                            .snippet("0")
                            .icon(waypointMarker));
                    waypointHashMap.put(gLat, marker.getId());
                    wayPointsListBeans.add(wayPointsBean);
                    if (mAdapter == null) {
                        initializeAdapter();
                    } else {
                        mAdapter.add(wayPointsBean);
                    }
                    updateRoute();
                }

                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), gLat + " " + gLon + "", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDangerMarkerAdd(DangerMarkerObj dangerMarkerObj) {
        if (dangerMarkerObj.isAdd()) {
            createTripMarkerModelClass.setLat(dangerMarkerObj.getLat());
            createTripMarkerModelClass.setLongX(dangerMarkerObj.getLonX());
            createTripMarkerModelClass.setMarkType(type);
            createTripMarkerModelClass.setAddress(Utils.getAddress(dangerMarkerObj.getLat(), dangerMarkerObj.getLonX(), false, RouteApplication.getInstance().getApplicationContext()));
            createTripMarkerModelClass.setFullAddress(Utils.getAddress(dangerMarkerObj.getLat(), dangerMarkerObj.getLonX(), true, RouteApplication.getInstance().getApplicationContext()));
            createTripMarkerModelClass.setRadius(dangerMarkerObj.getRadius() * 1000);
            createTripMarkerModelClass.setTripId(Integer.parseInt(dataBean.getId()));
            createTripMarkerModelClass.setDescription("");
            createMarker(createTripMarkerModelClass, dangerMarkerObj.getLat(), dangerMarkerObj.getLonX(), 3, dangerMarkerObj.getRadius() * 1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn: {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
// Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(getActivity());
                startActivityForResult(intent, 1);


            }
            break;
        }
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



    private void initHazardInfoBottomSheet() {

        hazardInfoBottomFragment = new HazardInfoBottomFragment();

        Bundle args = new Bundle();
        args.putString("HazardArea", hazardArea);
        args.putString("HazardLength", hazardLenght);
        hazardInfoBottomFragment.setArguments(args);


        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();

        ft.add(R.id.container_way_point, hazardInfoBottomFragment);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHazardAreaName(HazardNameObject hazardNameObject) {

        hazardAreaName = hazardNameObject.getName();
        Log.e("Waypointragment", "eventBus " + hazardAreaName);
    }
}
