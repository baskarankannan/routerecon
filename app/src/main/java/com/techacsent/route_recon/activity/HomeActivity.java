package com.techacsent.route_recon.activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Tovuti;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import io.github.rupinderjeet.kprogresshud.KProgressHUD;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.LoadBadge;
import com.techacsent.route_recon.event_bus_object.NotificationBadgeObject;
import com.techacsent.route_recon.event_bus_object.StartServiceObj;
import com.techacsent.route_recon.event_bus_object.StopServiceObject;
import com.techacsent.route_recon.fragments.MapFragment;
import com.techacsent.route_recon.fragments.PolygonMapFragment;
import com.techacsent.route_recon.fragments.SettingsFragment;
import com.techacsent.route_recon.fragments.SharedTripFragment;
import com.techacsent.route_recon.fragments.TrackFragment;
import com.techacsent.route_recon.fragments.TrackerFragment;
import com.techacsent.route_recon.fragments.TripsFragment;
import com.techacsent.route_recon.interfaces.BottomNavigationVisibilityListener;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.BadgeCountResponse;
import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.SaveLocationResponse;
import com.techacsent.route_recon.model.request_body_model.SaveLocationModel;
import com.techacsent.route_recon.model.request_body_model.SetBadgeModel;
import com.techacsent.route_recon.model.request_body_model.SubscriptionModel;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.service.LocationServiceV2;
import com.techacsent.route_recon.service.RouteService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.HomeActivityViewModel;
import com.techacsent.route_recon.view_model.SharedMapViewModel;
import com.techacsent.route_recon.view_model.SharedViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class HomeActivity extends BaseActivity implements FragmentActivityCommunication, BottomNavigationView.OnNavigationItemSelectedListener, MapFragment.CallDrawer, BottomNavigationVisibilityListener, MapFragment.CallPolygonMap {
    private BottomNavigationView bottomNavigationView;
    private Fragment active;
    private KProgressHUD progressDialogFragment;

    private static GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    private Handler handler;
    private Runnable runnable;
    private LocationService locationService;
    private Fragment trip, map, shared, tracker, settings,track, currentFragment;
    private Snackbar snackbar;
    private HomeActivityViewModel homeActivityViewModel;
    private MapFragment mapFragment;
    private static final String TAG = "HomeActivity";

    private LocationComponent locationComponent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        locationService = new LocationService(this);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        deleteToken();
        initGoogleAPIClient();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        checkLocationPermission();
        checkStatus();



    }

    private void checkLocationPermission() {
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                initializeView();
                checkPermissions();
                if (!PreferenceManager.getBool(Constant.KEY_IS_BADGE_LOADED)) {
                    //getBadgeCount();
                } else {
                    updateBadgeLocally();
                }

                if (snackbar != null) {
                    snackbar.dismiss();
                }
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                snackbar = Snackbar.make(bottomNavigationView, getResources().getString(R.string.text_location_alert), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings", view -> {

                            openPermissionDialog();


                        }).setActionTextColor(getResources().getColor(android.R.color.holo_red_light));
                snackbar.show();

            }

            @Override
            public boolean onBlocked(Context context, ArrayList<String> blockedList) {
                return super.onBlocked(context, blockedList);
            }

            @Override
            public void onJustBlocked(Context context, ArrayList<String> justBlockedList, ArrayList<String> deniedPermissions) {
                super.onJustBlocked(context, justBlockedList, deniedPermissions);

            }
        });

    }

    private void openPermissionDialog() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void checkStatus() {
        SubscriptionModel subscriptionModel = new SubscriptionModel();
        subscriptionModel.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
        homeActivityViewModel.callCheckStatus(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                subscriptionModel).observe(this, data -> {
            if (data != null) {
                PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_STATUS, data.getSubscriptionStatus());
                Gson gson = new Gson();
                String jsonString = gson.toJson(data);
                Timber.d(data.getSubscriptionStatus());
                if (data.getSubscriptionStatus().equals("payable")) {
                    gotoPaymentActivity();
                }
            }
        });
    }

    private void gotoPaymentActivity() {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
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

    private void checkInternet() {
        Tovuti.from(RouteApplication.getInstance().getApplicationContext()).monitor((connectionType, isConnected, isFast) -> {
            if (!isConnected) {
                snackbar = Snackbar.make(bottomNavigationView, getResources().getString(R.string.text_no_network), Snackbar.LENGTH_INDEFINITE)
                        .setAction("CLOSE", view -> {

                        })
                        .setActionTextColor(getResources().getColor(android.R.color.white));
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(HomeActivity.this, android.R.color.holo_red_light));
                snackbar.show();
            } else {
                if (snackbar != null) {
                    snackbar.dismiss();
                }

            }
        });
    }

    private void initializeView() {
        initFragment();
        loadProgressHud();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void deleteToken() {
        new Thread(() -> {
            FirebaseMessaging.getInstance().deleteToken();
        }).start();
    }

    private void initFragment() {

        bottomNavigationView.getMenu().getItem(0).setCheckable(true);



        map = new MapFragment();
        trip = new TripsFragment();
        shared = new SharedTripFragment();
        settings = new SettingsFragment();
        tracker = new TrackerFragment();
        track =  new TrackFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, map, map.getClass()
                .getSimpleName())
                .commit();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, trip, trip.getClass()
                .getSimpleName()).hide(trip)
                .commit();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, shared, shared.getClass()
                .getSimpleName()).hide(shared)
                .commit();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, tracker, tracker.getClass()
                .getSimpleName()).hide(tracker)
                .commit();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, settings, settings.getClass()
                .getSimpleName()).hide(settings)
                .commit();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, track, track.getClass()
                .getSimpleName()).hide(track)
                .commit();



        active = map;


    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(HomeActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        });
    }

    private void showBadge(BottomNavigationView bottomNavigationView, @IdRes int itemId, String value) {

        Log.e("HomeActi", "showBadge "+value );
        @SuppressLint("RestrictedApi") BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);
        View badge = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_notification_badge, bottomNavigationView, false);

        TextView text = badge.findViewById(R.id.badge_text_view);
        text.setText(value);
        itemView.addView(badge);
    }

    private void removeBadge(BottomNavigationView bottomNavigationView, @IdRes int itemId) {
        @SuppressLint("RestrictedApi") BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);

        Timber.d("%s", itemView.getChildCount());
        if (itemView.getChildCount() == 3) {
            itemView.removeViewAt(2);
        }
    }

    /*private void resetBadge(String singlebadgetype, List<String> listBadgeType, String prefKey, int count) {
        SetBadgeModel setBadgeModel = new SetBadgeModel();
        List<SetBadgeModel.BadgesBean> badgesBeanList = new ArrayList<>();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                SetBadgeModel.BadgesBean badgesBean = new SetBadgeModel.BadgesBean();
                badgesBean.setBadgeCount(0);
                badgesBean.setBadgeType(listBadgeType.get(i));
                badgesBeanList.add(badgesBean);
            }
        } else {
            SetBadgeModel.BadgesBean badgesBean = new SetBadgeModel.BadgesBean();
            badgesBean.setBadgeCount(0);
            badgesBean.setBadgeType(singlebadgetype);
            badgesBeanList.add(badgesBean);
        }

        setBadgeModel.setBadges(badgesBeanList);
        ApiService apiService = new ApiCaller();
        apiService.setBadgeCount(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), setBadgeModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                PreferenceManager.updateValue(prefKey, 0);
            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }*/

    private void resetBadge(String singlebadgetype, List<String> listBadgeType, String prefKey, int count) {
        SetBadgeModel setBadgeModel = new SetBadgeModel();
        List<SetBadgeModel.BadgesBean> badgesBeanList = new ArrayList<>();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                SetBadgeModel.BadgesBean badgesBean = new SetBadgeModel.BadgesBean();
                badgesBean.setBadgeCount(0);
                badgesBean.setBadgeType(listBadgeType.get(i));
                badgesBeanList.add(badgesBean);
            }
        } else {
            SetBadgeModel.BadgesBean badgesBean = new SetBadgeModel.BadgesBean();
            badgesBean.setBadgeCount(0);
            badgesBean.setBadgeType(singlebadgetype);
            badgesBeanList.add(badgesBean);
        }
        setBadgeModel.setBadges(badgesBeanList);
        homeActivityViewModel.callResetBadge(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), setBadgeModel).observe(this, successArray -> {
            if (successArray != null) {
                PreferenceManager.updateValue(prefKey, 0);
            }
        });
    }

    @Override
    public void hideBottomNav(boolean isHide) {
        /*if (isHide) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }*/
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
    public void fragmentToolbarbyPosition(int pos) {

    }

    /*private void getBadgeCount() {
        homeActivityViewModel.callGetBadgeCount(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID)).observe(this, new Observer<BadgeCountResponse>() {
            @Override
            public void onChanged(@Nullable BadgeCountResponse data) {
                if (data != null) {
                    PreferenceManager.updateValue(Constant.KEY_IS_BADGE_LOADED, true);
                    if (data.getBadgecounts().getLocationshare() > 0) {
                        PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARING_BADGE_COUNT, data.getBadgecounts().getLocationshare());
                        showBadge(bottomNavigationView, R.id.action_tracker, String.valueOf(data.getBadgecounts().getLocationshare()));
                    }
                    if (data.getBadgecounts().getFriendreqsend() + data.getBadgecounts().getFriendreqaccept() > 0) {
                        PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT, data.getBadgecounts().getFriendreqsend() + data.getBadgecounts().getFriendreqaccept());
                        showBadge(bottomNavigationView, R.id.action_settings, String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
                    }
                    if (data.getBadgecounts().getTripshare() + data.getBadgecounts().getTripaccept() > 0) {
                        PreferenceManager.updateValue(Constant.KEY_TRIP_SHARING_BADGE_COUNT, data.getBadgecounts().getTripshare() + data.getBadgecounts().getTripaccept());
                        showBadge(bottomNavigationView, R.id.action_shared, String.valueOf(PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT)));
                    }
                }
            }
        });
    }*/

    private void updateBadgeLocally() {
        if (PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT) > 0) {
           // showBadge(bottomNavigationView, R.id.action_settings, String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
        }
        if (PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT) > 0) {
            showBadge(bottomNavigationView, R.id.action_shared, String.valueOf(PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT)));
        }
        if (PreferenceManager.getInt(Constant.KEY_LOCATION_SHARING_BADGE_COUNT) > 0) {
            showBadge(bottomNavigationView, R.id.action_tracker, String.valueOf(PreferenceManager.getInt(Constant.KEY_LOCATION_SHARING_BADGE_COUNT)));
        }
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public void showDone(boolean isShow) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case RESULT_OK:
                    break;

                case RESULT_CANCELED:
                    break;
            }
        } else if (resultCode == RESULT_OK && requestCode == 121) {
            SharedViewModel sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
            MapData mapData = new MapData(PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID), PreferenceManager.getString(Constant.KEY_MAPBOX_STYLE_VALUE));
            sharedViewModel.select(mapData);
            SharedMapViewModel sharedMapViewModel = ViewModelProviders.of(this).get(SharedMapViewModel.class);
            sharedMapViewModel.select(mapData);
        } else if (resultCode == RESULT_OK && requestCode == 122) {
           // removeBadge(bottomNavigationView, R.id.action_settings);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkInternet();
        Timber.d("%s", PreferenceManager.getLong(Constant.KEY_END_TIME));
        Timber.d("%s", PreferenceManager.getBool(Constant.KEY_IS_LOCATION_SHARING));
        if (PreferenceManager.getLong(Constant.KEY_END_TIME) > 0L) {
            long remainingtime = PreferenceManager.getLong(Constant.KEY_END_TIME) - System.currentTimeMillis();
            if (remainingtime > 0L) {
                PreferenceManager.updateValue(Constant.KEY_DURATION, (int) remainingtime);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SaveLocationModel saveLocationModel = new SaveLocationModel();

                            Location location = locationService.getLocation();
                           /* saveLocationModel.setLat(locationService.getLatitude());
                            saveLocationModel.setLon(locationService.getLongitude());*/
                            saveLocationModel.setLat(location.getLatitude());
                            saveLocationModel.setLon(location.getLongitude());

                            updateCurrentLocation(PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID), saveLocationModel);

                            long time = PreferenceManager.getLong(Constant.KEY_END_TIME) - System.currentTimeMillis();
                            if (time <= 0L) {
                                handler.removeCallbacks(runnable);
                            }
                            handler.postDelayed(this, 5000);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(runnable, 5000);

            } else {
                PreferenceManager.updateValue(Constant.KEY_END_TIME, 0L);
                PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, false);
                if (RouteService.getInstance() != null) {
                    RouteService.getInstance().stopService();
                }
                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }
            }
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void r(LoadBadge loadBadge) {
        if (PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT) > 0) {
            showBadge(bottomNavigationView, R.id.action_shared, String.valueOf(PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT)));
        }
        if (PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT) > 0) {
            //PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT, 0);
           // showBadge(bottomNavigationView, R.id.action_settings, String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
        }
    }

    /*public void updateCurrentLocation(int locationShareId, SaveLocationModel saveLocationModel) {
        ApiService apiService = new ApiCaller();
        apiService.saveCurrentLocation(locationShareId, PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                saveLocationModel, new ResponseCallback<SaveLocationResponse>() {
                    @Override
                    public void onSuccess(SaveLocationResponse data) {
                        Timber.e(data.getLat() + ", " + data.getLon());
                    }

                    @Override
                    public void onError(Throwable th) {
                        Timber.e(th);
                    }
                });

    }*/

    public void updateCurrentLocation(int locationShareId, SaveLocationModel saveLocationModel) {

        homeActivityViewModel.callUpdateLocation(locationShareId, PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                saveLocationModel).observe(this, new Observer<SaveLocationResponse>() {
            @Override
            public void onChanged(@Nullable SaveLocationResponse saveLocationResponse) {
                if (saveLocationResponse != null) {
                    Timber.e(saveLocationResponse.getLat() + ", " + saveLocationResponse.getLon());
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Tovuti.from(this).stop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stopService(StopServiceObject stopServiceObject) {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartService(StartServiceObj startServiceObj) {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "hi, i am working", Toast.LENGTH_SHORT).show();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                SaveLocationModel saveLocationModel = new SaveLocationModel();

                Location location = locationService.getLocation();

                saveLocationModel.setLat(location.getLatitude());
                saveLocationModel.setLon(location.getLongitude());

               /* saveLocationModel.setLat(locationService.getLatitude());
                saveLocationModel.setLon(locationService.getLongitude());*/

                updateCurrentLocation(PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID), saveLocationModel);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            try {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == ACCESS_FINE_LOCATION_INTENT_ID) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                if (mGoogleApiClient == null) {
                    initGoogleAPIClient();
                    showSettingDialog();
                } else
                    showSettingDialog();


            } else {

                /*Toast.makeText(HomeActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                Toast.makeText(HomeActivity.this, "AAA", Toast.LENGTH_SHORT).show();
                checkPermissions();*/
            }
        }
    }

    private Runnable sendUpdatesToUI = this::showSettingDialog;
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Toast.makeText(HomeActivity.this, "BROADCAST_ACTION", Toast.LENGTH_SHORT).show();
                    locationService = new LocationService(HomeActivity.this);
                } else {
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
        progressDialogFragment = null;
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removeNotificationBadge(NotificationBadgeObject notificationBadgeObject) {
        removeNotification(notificationBadgeObject.getActionName());
    }

    private void removeNotification(String actionName) {
        switch (actionName) {
            case "share_action":
                if (PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT) > 0) {
                    List<String> badgeTypeList = new ArrayList<>();
                    badgeTypeList.add("trip-share");
                    badgeTypeList.add("trip-accept");
                    resetBadge(null, badgeTypeList, Constant.KEY_TRIP_SHARING_BADGE_COUNT, 2);
                    removeBadge(bottomNavigationView, R.id.action_shared);
                }
                break;
            case "friend_action":
                if (PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT) > 0) {
                    List<String> badgeTypeList = new ArrayList<>();
                    badgeTypeList.add("friend-req-send");
                    badgeTypeList.add("friend-req-accept");
                    resetBadge(null, badgeTypeList, Constant.KEY_FRIEND_REQ_BADGE_COUNT, 2);
                    //removeBadge(bottomNavigationView, R.id.action_settings);
                }

                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {


            case R.id.action_trips:


                //FragmentManager fm = getSupportFragmentManager();
                //Fragment fragment_byID = fm.findFragmentById(R.id.frame_content);

                //bottomNavigationView.getMenu().getItem(0).setCheckable(true);

               // currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_content);



               // Log.d(TAG, "onNavigationItemSelected: ".concat(fragment_byID.getClass().getSimpleName()));

                /*if (fragment_byID.getClass().getSimpleName().equalsIgnoreCase("MapFragment")) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }*/

                /*if (currentFragment instanceof SettingsFragment) {
                    Log.e(TAG, " SettingsFragment "+ active);

                    active = settings;
                }else{

                    Log.e(TAG, " OtherFragment "+ active);

                }*/

                getSupportFragmentManager().beginTransaction().hide(active).show(trip).commit();
                active = trip;
                removeBadge(bottomNavigationView, R.id.action_trips);
                break;

            case R.id.action_maps:

                /*if (currentFragment instanceof SettingsFragment) {
                    Log.e(TAG, " SettingsFragment "+ active);
                    active = settings;
                }else{

                    Log.e(TAG, " OtherFragment "+ active);

                }*/

                getSupportFragmentManager().beginTransaction().hide(active).show(map).commit();
                active = map;
               // removeBadge(bottomNavigationView, R.id.action_maps);
                break;

            case R.id.action_shared:
                getSupportFragmentManager().beginTransaction().hide(active).show(shared).commit();
                removeNotification("share_action");
                active = shared;
                break;

            case R.id.action_tracker:
                getSupportFragmentManager().beginTransaction().hide(active).show(tracker).commit();
                active = tracker;
                if (PreferenceManager.getInt(Constant.KEY_LOCATION_SHARING_BADGE_COUNT) > 0) {
                    resetBadge("location-share", null, Constant.KEY_LOCATION_SHARING_BADGE_COUNT, 1);
                    removeBadge(bottomNavigationView, R.id.action_tracker);
                }
                break;

            case R.id.action_track_bottom:

                getSupportFragmentManager().beginTransaction().hide(active).show(track).commit();


                active = track;

               /* if (!active.isAdded()) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.frame_content, track, track.getClass().getSimpleName()).show(track)
                            .commit();

                }else {
                    getSupportFragmentManager().beginTransaction().hide(active).show(track).commit();

                }*/

                break;

/*            case R.id.action_settings:
                getSupportFragmentManager().beginTransaction().hide(active).show(settings).commit();
                removeBadge(bottomNavigationView, R.id.action_settings);
                active = settings;
                break;*/
 /*
            case R.id.action_tracker_bottom:
                Toast.makeText(HomeActivity.this,getString(R.string.on_progress) ,Toast.LENGTH_LONG).show();

                break;*/

           /* case R.id.action_tools:

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new PolygonMapFragment(), PolygonMapFragment.class.getClass()
                        .getSimpleName()).addToBackStack(PolygonMapFragment.class.getClass()
                        .getSimpleName())
                        .commit();


               // Toast.makeText(HomeActivity.this,getString(R.string.on_progress) ,Toast.LENGTH_LONG).show();

                break;*/

            default:
                return true;
        }
        return true;
    }

    @Override
    public void openDrawer() {


        Log.e("FragmentName", SettingsFragment.class.getClass().toString());

        getSupportFragmentManager().beginTransaction().hide(active).show(settings).commit();
        active = settings;

      /*  getSupportFragmentManager().beginTransaction().add(R.id.frame_content, new SettingsFragment(), SettingsFragment.class.getClass()
                .getSimpleName()).addToBackStack(SettingsFragment.class.getClass()
                .getSimpleName())
                .commit();*/

    }

    @Override
    public void showBottomNavigation(boolean status) {
        if (status) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    @Override
    public void openPolygonGoogleMap() {

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new PolygonMapFragment(), PolygonMapFragment.class.getClass()
                .getSimpleName()).addToBackStack(PolygonMapFragment.class.getClass()
                .getSimpleName())
                .commit();

    }

    @Override
    public void onBackPressed() {

         if (active instanceof SettingsFragment) {
                    Log.e(TAG, " SettingsFragment "+ active);



             getSupportFragmentManager().beginTransaction().hide(active).show(map).commit();
             active = map;


         }else{

             super.onBackPressed();

                    Log.e(TAG, " OtherFragment "+ active);

                }
    }
}