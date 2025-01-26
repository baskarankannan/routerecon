package com.techacsent.route_recon.application;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.WorkManager;

import com.crashlytics.android.Crashlytics;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mapbox.mapboxsdk.Mapbox;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.techacsent.route_recon.BuildConfig;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import io.fabric.sdk.android.Fabric;
import java.util.Objects;

import timber.log.Timber;

public class RouteApplication extends Application implements Configuration.Provider{
    private static RouteApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        WorkManager.initialize(this, getWorkManagerConfiguration());
        Mapbox.getInstance(this, Constant.MAPBOX_API_KEY);
       /* final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);*/
        initFabric();
        instance = this;
        /*if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }*/
        Timber.plant(new Timber.DebugTree());
        Logger.addLogAdapter(new AndroidLogAdapter());


        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        Utils.getHashSignatureForLogin();
        PreferenceManager.setInstance(this);

        Places.initialize(this, getString(R.string.google_api_key_new));
        PlacesClient placesClient = Places.createClient(this);

        /*FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Timber.d(newToken);
            PreferenceManager.updateValue(Constant.KEY_FIREBASE_TOKEN_ID, newToken);
        });*/
    }

    private void initFabric() {
        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true);
    }

    public static synchronized RouteApplication getInstance() {
        if (instance == null) {
            instance = new RouteApplication();
        }
        return instance;
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();
    }
}
