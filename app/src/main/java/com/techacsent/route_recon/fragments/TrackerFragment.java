package com.techacsent.route_recon.fragments;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.TrackerActivity;
import com.techacsent.route_recon.adapter.SharedUserAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.SosObject;
import com.techacsent.route_recon.event_bus_object.StartServiceObj;
import com.techacsent.route_recon.event_bus_object.TrafficObject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.ElevationResponse;
import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.SharedUserResponse;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.model.unitconversion.Deg2UTM;
import com.techacsent.route_recon.retrofit_api.ApiOkhttpCaller;
import com.techacsent.route_recon.retrofit_api.RouteHttpCallback;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.SharedMapViewModel;
import com.techacsent.route_recon.view_model.SharedViewModel;
import com.techacsent.route_recon.view_model.TrackerViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;


public class TrackerFragment extends Fragment implements PermissionsListener, /*LocationEngineListener,*/
        OnRecyclerClickListener<SharedUserResponse.ListBean> {
    private MapboxMap map;
    private MapView mapView;
    private ImageView ivShareLiveLocation;
    private TextView tvLatLong;
    private ImageView fabLocation;
    private PermissionsManager permissionsManager;
    private View viewUserList;
    private ImageView ivOpenClose;
    private TextView tvSharedText;
    private RecyclerView rvUserList;
    private LinearLayout layoutOpenClose;
    private SharedUserAdapter mAdapter;
    private Icon markerIcon;
    private Handler handler;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private LocationService locationService;
    Runnable runnable;

    private Button btnStreet;
    private Button btnSatellite;
    private Button btnTraffic;
    private Button btnOutdoor;
    private Button btn3dMap;
    private ImageView ivSOS;
    private ImageView fabAddressSearch;

    private TrafficPlugin trafficPlugin;
    private LocationComponent locationComponent;
    private SharedViewModel sharedViewModel;
    private SharedMapViewModel sharedMapViewModel;
    private MapData mapData;
    private List<Integer> listElevation;
    private TrackerViewModel trackerViewModel;
    private int elevation = 0;
    private Marker destinationMarker;

    public TrackerFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trackerViewModel = ViewModelProviders.of(this).get(TrackerViewModel.class);
        fragmentActivityCommunication.hideBottomNav(false);
        fragmentActivityCommunication.fragmentToolbarbyPosition(3);
        listElevation = new ArrayList<>();

        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        sharedMapViewModel = ViewModelProviders.of(requireActivity()).get(SharedMapViewModel.class);

        locationService = new LocationService(RouteApplication.getInstance().getApplicationContext());
        initializeView(savedInstanceState, view);
        enableLocation();
        getElevation();
        initializeListener();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeView(Bundle savedInstanceState, View view) {

        fabAddressSearch = view.findViewById(R.id.fab_address_search);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        btnStreet = view.findViewById(R.id.btn_street);
        btnSatellite = view.findViewById(R.id.btn_satellite);
        btnTraffic = view.findViewById(R.id.btn_traffic);
        btnOutdoor = view.findViewById(R.id.btn_outdoor);
        btn3dMap = view.findViewById(R.id.btn_three_d_map);

        btn3dMap.setVisibility(View.GONE);

        fabLocation = view.findViewById(R.id.fab_my_location);
        ivSOS = view.findViewById(R.id.fab_sos);
        resetButton();
        ivShareLiveLocation = view.findViewById(R.id.iv_share_trip);
        tvLatLong = view.findViewById(R.id.tv_lat_long);

        if (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 0 || PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID) == 3) {
            tvLatLong.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            tvLatLong.setTextColor(getResources().getColor(R.color.white, null));
        }

        rvUserList = view.findViewById(R.id.recycle_user_views);
        tvSharedText = view.findViewById(R.id.tv_shared_text);
        viewUserList = view.findViewById(R.id.rl_user_list);
        ivOpenClose = view.findViewById(R.id.iv_open_close_arrow);
        layoutOpenClose = view.findViewById(R.id.layout_drawer);


        IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
        markerIcon = iconFactory.fromResource(R.drawable.my_pos_marker_icon);
        rvUserList.invalidate();
        rvUserList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        ImageView ivRefresh = view.findViewById(R.id.iv_refresh);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvDone = view.findViewById(R.id.tv_done);
        tvDone.setVisibility(View.GONE);
        ivRefresh.setVisibility(View.GONE);
        ivAddUpdate.setVisibility(View.GONE);
        tvTitle.setText(getResources().getString(R.string.tracker));
        //PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeListener() {
        ivShareLiveLocation.setOnClickListener(v -> {
            if (!PreferenceManager.getBool(Constant.KEY_IS_LOCATION_SHARING)) {
                Intent intent = new Intent(getActivity(), TrackerActivity.class);
                //intent.putExtra("is_edit", false);
                Bundle bundle = new Bundle();
                bundle.putBoolean("is_edit", false);
                intent.putExtras(bundle);
                startActivity(intent);
                //startActivityForResult(intent, 102);
            } else {
                Fragment fragment = new EditDeleteLocationShareFragment();
                loadFragment(fragment);
            }
        });

        layoutOpenClose.setOnClickListener(v -> {
            if (tvSharedText.getVisibility() == View.VISIBLE) {
                tvSharedText.setVisibility(View.GONE);
                rvUserList.setVisibility(View.VISIBLE);
                ivOpenClose.setImageResource(R.drawable.ic_arrow_downward_black_24dp);

            } else {
                tvSharedText.setVisibility(View.VISIBLE);
                rvUserList.setVisibility(View.GONE);
                ivOpenClose.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            }
        });

        ivSOS.setOnClickListener(v -> {
            SosAlertFragment sosFragment = new SosAlertFragment();
            sosFragment.show(getChildFragmentManager(), null);
        });

        btnStreet.setOnClickListener(v -> {

            btnStreet.setBackgroundResource(R.drawable.next_trip_colored_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnStreet.setTextColor(getResources().getColor(R.color.white));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            tvLatLong.setTextColor(getResources().getColor(R.color.black, null));
            map.setStyle(Style.MAPBOX_STREETS, style -> {

                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 0);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_mapbox_streets));

                mapData = new MapData(0, getResources().getString(R.string.mapbox_style_mapbox_streets));
                sharedViewModel.select(mapData);
            });

        });
        btnSatellite.setOnClickListener(v -> {

            btnSatellite.setBackgroundResource(R.drawable.pending_selected);
            btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnSatellite.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            tvLatLong.setTextColor(getResources().getColor(R.color.white, null));

            map.setStyle(Style.SATELLITE, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 1);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite));

                mapData = new MapData(1, getResources().getString(R.string.mapbox_style_satellite));
                sharedViewModel.select(mapData);
            });

        });
        btnTraffic.setOnClickListener(v -> {

            btnTraffic.setBackgroundResource(R.drawable.pending_selected);
            btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnTraffic.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            tvLatLong.setTextColor(getResources().getColor(R.color.white, null));

            map.setStyle(Style.SATELLITE_STREETS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 2);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite_streets));

                mapData = new MapData(2, getResources().getString(R.string.mapbox_style_satellite_streets));
                sharedViewModel.select(mapData);
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
            tvLatLong.setTextColor(getResources().getColor(R.color.black, null));

            map.setStyle(Style.OUTDOORS, style -> {
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 3);
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_outdoors));

                mapData = new MapData(3, getResources().getString(R.string.mapbox_style_outdoors));
                sharedViewModel.select(mapData);
            });

        });

        fabLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION)
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


    }

    private void resetButton() {
        switch (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID)) {
            case 0:

                btnStreet.setBackgroundResource(R.drawable.next_trip_colored_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnStreet.setTextColor(getResources().getColor(R.color.white));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));

                break;

            case 1:
                btnSatellite.setBackgroundResource(R.drawable.pending_selected);
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnSatellite.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 2:
                btnTraffic.setBackgroundResource(R.drawable.pending_selected);
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
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

    private void loadFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().
                beginTransaction().add(R.id.frame_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
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
                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 2000);
                mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);
                //tvLatLong.setText(locationComponent.getLastKnownLocation().getLatitude() + locationComponent.getLastKnownLocation().getLongitude() + "");

            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 102) {
            if (data != null) {
                boolean isShared = data.getBooleanExtra("is_shared", false);
                int sharedID = data.getIntExtra("shared_id", 0);
                int duration = data.getIntExtra("duration", 0);
                if (isShared) {
                    EventBus.getDefault().post(new StartServiceObj(true, sharedID, duration));
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "dfvsfvsfv", Toast.LENGTH_SHORT).show();
                }
            }

        }

        else if (resultCode == RESULT_OK && requestCode == 1) {
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
                    .icon(markerIcon));

            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), gLat + " " + gLon + "", Toast.LENGTH_SHORT).show();
        }



    }

    private void getSharedUserList() {
        trackerViewModel.callGetSharedUser(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                "yes", 10, 0).observe(this, new Observer<SharedUserResponse>() {
            @Override
            public void onChanged(@Nullable SharedUserResponse data) {
                if (data != null) {
                    try {
                        if (data.getList() != null && data.getList().size() > 0) {
                            tvSharedText.setText(String.format(getString(R.string.person_sharing_their_location), data.getList().size()));
                            viewUserList.setVisibility(View.VISIBLE);
                            showSharedUserOnMap(data.getList());
                            if (mAdapter == null) {
                                initAdapter(data);
                                if (data.getList() != null) {
                                    Timber.e(String.valueOf(data.getList().size()));
                                }
                            } else {
                                mAdapter.clear();
                                mAdapter.setItems(data.getList());
                            }
                            rvUserList.setAdapter(mAdapter);

                        } else {
                            viewUserList.setVisibility(View.GONE);
                             map.clear();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

            }
        });
    }

    private void initAdapter(SharedUserResponse data) {
        mAdapter = new SharedUserAdapter(getActivity());
        mAdapter.setItems(data.getList());
        mAdapter.setListener(this);
        rvUserList.setAdapter(mAdapter);
    }

    private void showSharedUserOnMap(List<SharedUserResponse.ListBean> listBeans) {
        mapView.getMapAsync(mapboxMap -> {
            mapboxMap.clear();
            for (SharedUserResponse.ListBean bean : listBeans) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(bean.getLivelocation().getLat(), bean.getLivelocation().getLon()))
                        .title(bean.getUser().getName())
                        .icon(markerIcon));
            }

            mapboxMap.setInfoWindowAdapter(marker -> {
                @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).
                        inflate(R.layout.layout_user_info_window_on_map, null);
                TextView tvName = view.findViewById(R.id.tv_name);
                tvName.setText(marker.getTitle());
                return view;
            });

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation();
        } else {
            //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(Objects.requireNonNull(getActivity()))) {
            locationComponent = map.getLocationComponent();
            locationComponent.activateLocationComponent(getActivity(), loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mapView.onStart();
        loadMapData();
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                Log.e("Trackerfrag", "onStart() getSharedUserList()");
                //getElevation();
                getElevation();
                getSharedUserList();
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable, 10000);
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


                        if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") &&  PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                            tvLatLong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                                    " \n" + "Elevation " + elevation + " meter");
                        } else  if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") &&  PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            tvLatLong.setText( getLocationFromDecimalToDMSFormat(
                                    locationService.getLatitude(), locationService.getLongitude())+ " \n" +"Elevation " + elevation + " meter");

                        }else  if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") &&   ! PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            tvLatLong.setText( getLocationFromDecimalToDMSFormat(
                                    locationService.getLatitude(), locationService.getLongitude())+ " \n" + "Elevation " + elevation * 3.28084 + " feet");

                        }else  if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") &&   ! PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            tvLatLong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude())+ " \n" + "Elevation " + elevation * 3.28084 + " feet");

                        }else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {


                            Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                            String utm  =  deg2UTM.getZone()+ " "+ deg2UTM.getLetter()+ " "+deg2UTM.getNorthing()
                                    +" "+ deg2UTM.getEasting();

                            tvLatLong.setText(utm +
                                    " \n" + "Elevation " + elevation + " meter");

                        } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("utm") && !PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                            Deg2UTM deg2UTM = new Deg2UTM(locationService.getLatitude(), locationService.getLongitude());

                            String utm  =  deg2UTM.getZone()+ " "+ deg2UTM.getLetter()+ " "+deg2UTM.getNorthing()
                                    +" "+ deg2UTM.getEasting();

                            tvLatLong.setText(utm +
                                    " \n" + "Elevation " + elevation * 3.28084 + " feet");

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }));

    }

    private void loadMapData() {
        sharedMapViewModel.getSelected().observe(this, mapData -> {
            if (map != null) {
                if (mapData != null) {
                    map.setStyle(mapData.getStyle(), style -> {
                        //resetButton();
                        //resetButton();
                    });
                }
            }
        });

    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(StartServiceObj startServiceObj){
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "osdsjsb jsb jsb sfj b", Toast.LENGTH_SHORT).show();
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendSos(SosObject object) {
        if (object.isSend()) {
            sensSOS();
        }
    }

    private void sensSOS() {
        SendSosModel sendSosModel = new SendSosModel();
        sendSosModel.setLat(locationService.getLatitude());
        sendSosModel.setLongX(locationService.getLongitude());
        trackerViewModel.getSuccessArray(sendSosModel).observe(this, successArray -> {
            if (successArray != null) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), successArray.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
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
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }

    }

    @Override
    public void onItemClicked(SharedUserResponse.ListBean item) {
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(item.getLivelocation().getLat(), item.getLivelocation().getLon()))
                .zoom(Constant.MORE_ZOOM_IN_MAP)
                .tilt(30)
                .build();
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 2000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeTrafficData(TrafficObject trafficObject) {
        if (trafficPlugin != null) {
            trafficPlugin.setVisibility(trafficObject.isShow());
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") &&  PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                tvLatLong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude()) +
                        " \n" + "Elevation " + elevation + " meter");
            } else  if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") &&  PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatLong.setText( getLocationFromDecimalToDMSFormat(
                        locationService.getLatitude(), locationService.getLongitude())+ " \n" +"Elevation " + elevation + " meter");

            }else  if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("dms") &&   ! PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatLong.setText( getLocationFromDecimalToDMSFormat(
                        locationService.getLatitude(), locationService.getLongitude())+ " \n" + "Elevation " + elevation * 3.28084 + " feet");

            }else  if ( PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals("decimal") &&   ! PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {

                tvLatLong.setText(new DecimalFormat("###.#####").format(locationService.getLatitude()) + " " + new DecimalFormat("###.#####").format(locationService.getLongitude())+ " \n" + "Elevation " + elevation * 3.28084 + " feet");

            }
            resetButton();
            if (trafficPlugin != null) {
                trafficPlugin.setVisibility(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
            }
        }
    }

    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        if(handler!=null){
            handler.removeCallbacks(runnable);
        }
    }*/


    private  String getLocationFromDecimalToDMSFormat( double latitude, double longitude){


        Log.e("latitude", String.valueOf( latitude));
        Log.e("longitude", String.valueOf( longitude));



        int degreeLat  =(int)latitude;
        Log.e("degreeLat", String.valueOf( degreeLat));

        double differenceLat = (latitude - degreeLat);

        double minuteLat  = differenceLat*60;

        Log.e("minuteLat", String.valueOf((int)minuteLat));


        int minuteLatitude  = (int)minuteLat;

        Log.e("minuteLatitude", "tauis "+ String.valueOf( minuteLatitude));


        double divQuitonent = (double)minuteLat/60;

        DecimalFormat df = new DecimalFormat("#.#");

        //  double divRemainder = minuteLatitude%60;


        Log.e("div", "div "+ String.valueOf(df.format(divQuitonent)));



        double secondLat  = (differenceLat- Double.parseDouble(df.format(divQuitonent)))*3600;

        DecimalFormat df2 = new DecimalFormat("##.##");


        String LatitudeFinal = degreeLat+ "°"+minuteLatitude+"'"+df2.format(secondLat)+"\"";

        Log.e("LatitudeFinal", "t"+LatitudeFinal);


        int degreeLong  =(int)longitude;
        Log.e("degreeLng", String.valueOf( degreeLong));

        double differenceLng = (longitude - degreeLong);

        double minuteLng  = differenceLng*60;

        Log.e("minuteLng", String.valueOf((int)minuteLng));


        int minuteLongitude  = (int)minuteLng;

        Log.e("minuteLongitude", "tauis "+ String.valueOf( minuteLongitude));


        double divQuitonentLong = (double)minuteLongitude/60;

        //  double divRemainder = minuteLatitude%60;


        Log.e("div", "div "+ String.valueOf(df.format(divQuitonentLong)));



        double secondLng  = (differenceLng- Double.parseDouble(df.format(divQuitonentLong)))*3600;



        String LongitudeFinal = degreeLong+ "°"+minuteLongitude+"'"+df2.format(secondLng)+"\"";

        Log.e("LongitudeFinal", "t"+LongitudeFinal);




        return LatitudeFinal+" "+ LongitudeFinal;


    }
}
