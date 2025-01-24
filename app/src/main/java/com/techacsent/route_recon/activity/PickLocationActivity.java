package com.techacsent.route_recon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

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
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.MarkerEditObject;
import com.techacsent.route_recon.fragments.EditMarkerFragment;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PickLocationActivity extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback/*, LocationEngineListener*/ {
    private MapboxMap map;
    private MapView mapView;
    private EditText etPlace;
    //private PlaceAutocompleteFragment placeAutocompleteFragment;
    private PermissionsManager permissionsManager;

    private Marker destinationMarker;
    private String address;
    private String fullAddress;
    private double latitude;
    private double longitute;
    private LocationService locationService;
    private LayoutInflater layoutInflater;
    private Icon iconMyloc;
    private TextView tvPlaceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        locationService = new LocationService(RouteApplication.getInstance().getApplicationContext());
        layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setSupportActionBar(toolbar);
        IconFactory iconFactory = IconFactory.getInstance(this);
        iconMyloc = iconFactory.fromResource(R.drawable.my_pos_marker_icon);
        mapView = findViewById(R.id.mapView);
        ImageView ivOk = findViewById(R.id.iv_ok);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        ivOk.setOnClickListener(v -> returnBackWithUpdate(address, latitude, longitute));

        ImageView ivSearch = findViewById(R.id.iv_search);
        tvPlaceName = findViewById(R.id.tv_place_name);

        ivSearch.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(
                    Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(RouteApplication.getInstance().getApplicationContext());
            startActivityForResult(intent, 101);
        });
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, getResources().getString(R.string.text_permission_explanation), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            map.getStyle(this::enableLocationComponent);
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        map = mapboxMap;
        mapboxMap.getUiSettings().setLogoEnabled(false);
        mapboxMap.getUiSettings().setAttributionEnabled(false);

        mapboxMap.setStyle(PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE), style -> {
            enableLocationComponent(style);
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                    .zoom(9)
                    .tilt(30)
                    .bearing(180)
                    .build();

            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 3000);
            mapboxMap.addOnMapLongClickListener(point -> {
                if (destinationMarker != null) {
                    mapboxMap.removeMarker(destinationMarker);
                }
                latitude = point.getLongitude();
                longitute = point.getLatitude();
                address = Utils.getAddress(point.getLatitude(),point.getLongitude(), false, PickLocationActivity.this);
                fullAddress = Utils.getAddress(point.getLatitude(),point.getLongitude(), true, PickLocationActivity.this);
                Toast.makeText(this, fullAddress + " " + latitude + " " + longitute, Toast.LENGTH_SHORT).show();

                CameraPosition cPosition = new CameraPosition.Builder()
                        .target(point)
                        .zoom(16)
                        .tilt(30)
                        .bearing(180)
                        .build();

                map.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cPosition), 3000);
                destinationMarker = map.addMarker(new MarkerOptions()
                        .position(point)
                        .title(String.valueOf(Utils.getAddress(point.getLatitude(),point.getLongitude(), false, RouteApplication.getInstance().getApplicationContext())))
                        .snippet(String.valueOf(Utils.getAddress(point.getLatitude(),point.getLongitude(), true, RouteApplication.getInstance().getApplicationContext()))));
                tvPlaceName.setText(Utils.getAddress(point.getLatitude(),point.getLongitude(), false, RouteApplication.getInstance().getApplicationContext()));
                return false;
            });

            mapboxMap.setInfoWindowAdapter(marker -> {
                View view = layoutInflater.inflate(R.layout.marker_popup, null);
                TextView tvEdit = view.findViewById(R.id.tv_edit);
                TextView tvAddress = view.findViewById(R.id.tv_address);
                TextView tvFullAddress = view.findViewById(R.id.tv_full_address);
                tvAddress.setText(address);
                tvFullAddress.setText(fullAddress);
                tvEdit.setText(R.string.text_edit);
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditMarkerFragment editMarkerFragment = new EditMarkerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("address", tvAddress.getText().toString().trim());
                        bundle.putString("full_address", tvFullAddress.getText().toString().trim());
                        editMarkerFragment.setArguments(bundle);
                        editMarkerFragment.show(getSupportFragmentManager(), null);
                        marker.hideInfoWindow();
                    }
                });
                return view;
            });

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            if (data != null) {
                Place places = Autocomplete.getPlaceFromIntent(data);
                String gPlace = places.getName();
                double gLat = Objects.requireNonNull(places.getLatLng()).latitude;
                double gLon = places.getLatLng().longitude;
                longitute = gLat;
                latitude = gLon;
                fullAddress = String.valueOf(gPlace);
                address = String.valueOf(gPlace);
                Toast.makeText(this, fullAddress+" "+gLat+" "+gLon, Toast.LENGTH_SHORT).show();

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
                tvPlaceName.setText(fullAddress);
            }

        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = map.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            /*locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);*/
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void returnBackWithUpdate(String place, double latitude, double longitude) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("place", place);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("full_address", fullAddress);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateInfo(MarkerEditObject markerEditObject) {
        address = markerEditObject.getAddress();
        fullAddress = markerEditObject.getFullAddress();

    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
