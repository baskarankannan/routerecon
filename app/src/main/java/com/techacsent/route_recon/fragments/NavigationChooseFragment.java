package com.techacsent.route_recon.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.model.ElevationResponse;
import com.techacsent.route_recon.retrofit_api.ApiOkhttpCaller;
import com.techacsent.route_recon.retrofit_api.RouteHttpCallback;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationChooseFragment extends Fragment implements PermissionsListener/*, LocationEngineListener */ {
    private MapboxMap map;
    private MapView mapView;
    private ImageView ivNavCar;
    private ImageView ivNavCycle;
    private ImageView ivNavWalk;
    private Button btnNavigate;
    private ImageView ivMyLocation;
    private String navigationType = "driving";
    private int tripId;
    private double latitude;
    private double longitude;
    private boolean isTripRoute;
    private LocationService locationService;

    private Button btnStreet;
    private Button btnSatellite;
    private Button btnTraffic;
    private Button btnOutdoor;
    private Button btn3dMap;
    private TextView tvLatlon;

    private TrafficPlugin trafficPlugin;
    private LocationComponent locationComponent;
    private List<Integer> listElevation;

    BuildingPlugin buildingPlugin;

    private boolean is3dSelected  =false;


    public NavigationChooseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
            navigationType = getArguments().getString("navigation_type");
            isTripRoute = getArguments().getBoolean("is_trip_route");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_choose, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationService = new LocationService(RouteApplication.getInstance().getApplicationContext());
        listElevation = new ArrayList<>();
        initializeView(view, savedInstanceState);
        enableLocation();
        initializeListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeView(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        ivNavCar = view.findViewById(R.id.iv_navigate_car);
        ivNavCycle = view.findViewById(R.id.iv_navigate_cycle);
        ivNavWalk = view.findViewById(R.id.iv_navigate_walking);
        btnNavigate = view.findViewById(R.id.btn_navigation);
        ivMyLocation = view.findViewById(R.id.fab_my_location);
        ivNavCar.setBackgroundColor(getResources().getColor(R.color.orange));
        ivNavCycle.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
        ivNavWalk.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
        navigationType = "driving";

        btnStreet = view.findViewById(R.id.btn_street);
        btnSatellite = view.findViewById(R.id.btn_satellite);
        btnTraffic = view.findViewById(R.id.btn_traffic);
        btnOutdoor = view.findViewById(R.id.btn_outdoor);
        btn3dMap = view.findViewById(R.id.btn_three_d_map);
        tvLatlon = view.findViewById(R.id.tv_lat_lon);
        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            tvLatlon.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            tvLatlon.setTextColor(getResources().getColor(R.color.white, null));
        }
        resetButton();
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
                        if(PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)){
                            tvLatlon.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + Collections.max(listElevation) + " meter");
                        }else {
                            tvLatlon.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + Collections.max(listElevation)/0.3048 + " feet");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeListener() {
        ivNavCar.setOnClickListener(v -> {
            ivNavCar.setBackgroundColor(getResources().getColor(R.color.orange));
            ivNavCycle.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
            ivNavWalk.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
            navigationType = "driving";
        });
        ivNavCycle.setOnClickListener(v -> {
            ivNavCycle.setBackgroundColor(getResources().getColor(R.color.orange));
            ivNavCar.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
            ivNavWalk.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
            navigationType = "cycling";
        });
        ivNavWalk.setOnClickListener(v -> {
            ivNavWalk.setBackgroundColor(getResources().getColor(R.color.orange));
            ivNavCycle.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
            ivNavCar.setBackgroundColor(getResources().getColor(R.color.trip_details_panel_bg));
            navigationType = "walking";
        });
        btnNavigate.setOnClickListener(v -> {
            ShareOptionFragment shareOptionFragment = new ShareOptionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("trip_id", tripId);
            bundle.putDouble("lat", latitude);
            bundle.putDouble("lonX", longitude);
            bundle.putString("navigation_type", navigationType);
            bundle.putBoolean("is_trip_route", isTripRoute);
            shareOptionFragment.setArguments(bundle);
            shareOptionFragment.show(getChildFragmentManager(), null);
        });

        ivMyLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                if (locationComponent != null && locationComponent.getLastKnownLocation() != null) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude()))
                            .zoom(Constant.MORE_ZOOM_IN_MAP)
                            .tilt(30)
                            .build();
                    map.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), 2000);
                }
            }
        });

        btnStreet.setOnClickListener(v -> {

            btnStreet.setBackgroundResource(R.drawable.next_trip_colored_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnOutdoor.setBackgroundResource(R.drawable.pending_unselected);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnStreet.setTextColor(getResources().getColor(R.color.white));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            tvLatlon.setTextColor(getResources().getColor(R.color.black, null));
            map.setStyle(Style.MAPBOX_STREETS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 0);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_mapbox_streets));
            });

        });
        btnSatellite.setOnClickListener(v -> {

            btnSatellite.setBackgroundResource(R.drawable.pending_selected);
            btnOutdoor.setBackgroundResource(R.drawable.pending_unselected);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnSatellite.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            tvLatlon.setTextColor(getResources().getColor(R.color.white, null));

            map.setStyle(Style.SATELLITE, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 1);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_satellite));
            });

        });
        btnTraffic.setOnClickListener(v -> {

            btnTraffic.setBackgroundResource(R.drawable.pending_selected);
            btnOutdoor.setBackgroundResource(R.drawable.pending_unselected);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnTraffic.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            tvLatlon.setTextColor(getResources().getColor(R.color.white, null));

            map.setStyle(Style.SATELLITE_STREETS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 2);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite_streets));
            });

        });
        btnOutdoor.setOnClickListener(v -> {

            btnOutdoor.setBackgroundResource(R.drawable.last_trip_colored_bg);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnOutdoor.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            tvLatlon.setTextColor(getResources().getColor(R.color.black, null));

            map.setStyle(Style.OUTDOORS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 3);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_outdoors));
            });

        });

        btn3dMap.setOnClickListener(v -> {

            if(!is3dSelected) {

                btn3dMap.setBackgroundResource(R.drawable.bg_select_right);
                btn3dMap.setTextColor(getResources().getColor(R.color.white));
                buildingPlugin.setVisibility(true);
                is3dSelected = true;

            }else{

                btn3dMap.setBackgroundResource(R.drawable.last_trip_bg);
                buildingPlugin.setVisibility(false);
                btn3dMap.setTextColor((getResources().getColor(R.color.orange)));
                is3dSelected = false;
            }


        });
    }

    private void resetButton() {
        switch (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID)) {
            case 0:
                btnStreet.setBackgroundResource(R.drawable.next_trip_colored_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnOutdoor.setBackgroundResource(R.drawable.pending_unselected);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnStreet.setTextColor(getResources().getColor(R.color.white));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 1:
                btnSatellite.setBackgroundResource(R.drawable.pending_selected);
                btnOutdoor.setBackgroundResource(R.drawable.pending_unselected);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnSatellite.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 2:
                btnTraffic.setBackgroundResource(R.drawable.pending_selected);
                btnOutdoor.setBackgroundResource(R.drawable.pending_unselected);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnTraffic.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 3:
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_colored_bg);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnOutdoor.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                break;

        }
    }

    private void enableLocation() {
        mapView.getMapAsync(mapboxMap -> {
            map = mapboxMap;
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setLogoEnabled(false);

            mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
                getElevation();
                trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                        .zoom(16)
                        .tilt(30)
                        .bearing(180)
                        .build();
                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 1000);
                enableLocationComponent(style);
                mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);

                buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);

            });



        });

    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(Objects.requireNonNull(getActivity()))) {
            locationComponent = map.getLocationComponent();
            locationComponent.activateLocationComponent(getActivity(), loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            /*locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);*/
        } else {
            PermissionsManager permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
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
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }*/

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation();
        } else {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }
}
