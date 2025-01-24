package com.techacsent.route_recon.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.iid.FirebaseInstanceId;
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

public class RouteApplication extends Application {
    private static RouteApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        instance = this;
        /*if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }*/
        Timber.plant(new Timber.DebugTree());
        Logger.addLogAdapter(new AndroidLogAdapter());

        Mapbox.getInstance(Objects.requireNonNull(getApplicationContext()), Constant.MAPBOX_API_KEY);
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

    public static synchronized RouteApplication getInstance() {
        if (instance == null) {
            instance = new RouteApplication();
        }
        return instance;
    }
}
