package com.techacsent.route_recon.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.techacsent.route_recon.model.SaveLocationResponse;
import com.techacsent.route_recon.model.request_body_model.SaveLocationModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import timber.log.Timber;

public class RouteService extends Service {
    private static RouteService mInstance;
    private CountDownTimer countDownTimer;
    private Handler handler = new Handler();
    private Runnable runnable;
    private LocationService myTracker;
    private SaveLocationModel saveLocationModel;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        myTracker = new LocationService(this);
        saveLocationModel = new SaveLocationModel();
        //startLocationUpdate(PreferenceManager.getInt(Constant.KEY_DURATION));
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Timber.e( "Service Running");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mInstance = null;
       // countDownTimer.cancel();
        countDownTimer = null;
        stopService();
        Timber.e( "Service Destroyed");
        handler.removeCallbacks(runnable);
    }

    public static synchronized RouteService getInstance() {
        return mInstance;
    }

    public void stopService() {
        stopSelf();
        //countDownTimer.cancel();
    }

    public void updateCurrentLocation(int locationShareId, SaveLocationModel saveLocationModel) {
        ApiService apiService = new ApiCaller();
        apiService.saveCurrentLocation(locationShareId, PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                saveLocationModel, new ResponseCallback<SaveLocationResponse>() {
                    @Override
                    public void onSuccess(SaveLocationResponse data) {
                        Timber.e( data.getLat() + ", " + data.getLon());
                    }

                    @Override
                    public void onError(Throwable th) {
                        Timber.e(th);
                        stopService();
                    }
                });

    }

    public void startLocationUpdate(int duration) {
        countDownTimer = new CountDownTimer(duration * 1000, Constant.INTERVAL_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                handler = new Handler();
                runnable = () -> {
                    saveLocationModel.setLat(myTracker.getLatitude());
                    saveLocationModel.setLon(myTracker.getLongitude());
                    updateCurrentLocation(PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID), saveLocationModel);
                };
                handler.postDelayed(runnable, Constant.INTERVAL_TIME);

            }

            @Override
            public void onFinish() {
                Timber.e("Finished Share Location");
                PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, false);
                PreferenceManager.updateValue(Constant.KEY_END_TIME, 0L);
                PreferenceManager.updateValue(Constant.KEY_START_TIME, 0L);
                PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARE_ID, -1);
                countDownTimer.cancel();
                countDownTimer = null;
                handler.removeCallbacks(runnable);
                stopSelf();
            }
        }.start();
    }


}
