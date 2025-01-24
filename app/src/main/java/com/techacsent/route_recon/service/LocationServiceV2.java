package com.techacsent.route_recon.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.techacsent.route_recon.BuildConfig;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.LocationTracking;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import static java.lang.String.format;


public class LocationServiceV2 extends Service {

    public static final String TAG = LocationService.class.getSimpleName();
    private static final long LOCATION_REQUEST_INTERVAL = 10000;
    private static final float LOCATION_REQUEST_DISPLACEMENT = 5.0f;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    int locationTrackingCount = 0;

    Boolean isLocationTrackingStarted = false;

    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;

    LocationTracking locationTracking;
    ;

    double distance = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        showNotificationAndStartForegroundService();

        Gson gson = new Gson();


        mLocationCallback = new LocationCallback() {


            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                locationTrackingCount++;

                Log.e("locationService", String.valueOf(distance));
                Log.e("locationTrackingCount", "locationTrackingCount "+locationTrackingCount);


                //locationResult.getLastLocation().getSpeed();

                //Log.e("speed", ""+locationResult.getLastLocation().getSpeed());


                if (isLocationTrackingStarted) {


                    //PreferenceManager.getString(Constant.KEY_LOCATION_TRACK_START_TIME);
                    locationTracking = gson.fromJson(PreferenceManager.getString(Constant.KEY_LOCATION_TRACK_START_TIME), LocationTracking.class);


                    double latestLong = locationResult.getLastLocation().getLongitude();
                    double latestLat = locationResult.getLastLocation().getLatitude();


                    distance = distance+ Utils.calculateDistanceDifferenceInMeter(
                            locationTracking.getLongitude(),
                            locationTracking.getLatitude(),
                            latestLong,
                            latestLat);

                    locationTracking = new LocationTracking(latestLat,
                            latestLong, "time");

                    String locationTrackingString = gson.toJson(locationTracking);

                    PreferenceManager.updateValue(Constant.KEY_LOCATION_TRACK_START_TIME, locationTrackingString);




                    Log.e("LocationServiceV2", "DistanceCoverer" + distance);


                    //distance = 10000000;
                    //locationTrackingCount = 10000000;

                    int intDistance = (int) distance;

                    String stringDistance;


                    if (intDistance < 1000) {


                        stringDistance = intDistance + " " + "m";


                    } else {

                        int quotient = intDistance / 1000;
                        int remainder = intDistance % 1000;
                        stringDistance = quotient + "." + remainder + " " + "km";


                    }

                    PreferenceManager.updateValue(Constant.KEY_TRACKED_DISTANCE, stringDistance);

                    int durationInSecond = locationTrackingCount * 10;

                    String stringDuration;

                    if (durationInSecond < 60) {

                        stringDuration = durationInSecond + " " + " s";


                        PreferenceManager.updateValue(Constant.KEY_TRACKED_TIME, stringDuration);


                    } else {

                        int durationInMinute = durationInSecond / 60;

                        durationInSecond = durationInSecond % 60;

                        if (durationInMinute < 60) {


                            stringDuration = durationInMinute + "." + durationInSecond + " " + "m";

                            PreferenceManager.updateValue(Constant.KEY_TRACKED_TIME, stringDuration);


                        } else {

                            int durationInHour = durationInMinute / 60;

                            durationInMinute = durationInMinute % 60;

                            stringDuration = durationInHour + "h" + durationInMinute + "m";

                            PreferenceManager.updateValue(Constant.KEY_TRACKED_TIME, stringDuration);


                        }

                    }


                    float averageSpeed  = locationResult.getLastLocation().getSpeed()/locationTrackingCount ;
                    Log.e("locationService", "averageSpeed "+String.valueOf(averageSpeed));



                    String stringSpeed = format("%.2f", averageSpeed) + " " + "m/s";


                    PreferenceManager.updateValue(Constant.KEY_TRACKED_SPEED, stringSpeed);


                    Intent intent = new Intent("FILTER"); //FILTER is a string to identify this intent
                    intent.putExtra("DistanceCovered", stringDistance);
                    intent.putExtra("Duration", stringDuration);
                    intent.putExtra("Speed", stringSpeed);
                    sendBroadcast(intent);


                } else {


                    locationTracking = new LocationTracking(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude(), "time");

                    String locationTrackingString = gson.toJson(locationTracking);

                    PreferenceManager.updateValue(Constant.KEY_LOCATION_TRACK_START_TIME, locationTrackingString);
                    isLocationTrackingStarted = true;


                }


            }

        };


        //here you get the continues location updated based on the interval defined in
        //location request


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        createLocationRequest();


        return START_NOT_STICKY;
    }


    /**
     * Method used for creating location request
     * After successfully connection of the GoogleClient ,
     * This method used for to request continues location
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL).setFastestInterval(LOCATION_REQUEST_INTERVAL);

        requestLocationUpdate();
    }

    /**
     * Method used for the request new location using Google FusedLocation Api
     */
    private void requestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Log.e("permission", "denied");


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

       /* mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                //get the last location of the device

                Log.e("onSuccess", "lati" + location.getLatitude());

            }
        });*/

        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.myLooper()


        );
    }

    private void removeLocationUpdate() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    /**
     * This Method shows notification for ForegroundService
     * Start Foreground Service and Show Notification to user for android all version
     */
    private void showNotificationAndStartForegroundService() {

        final String CHANNEL_ID = BuildConfig.APPLICATION_ID.concat("_notification_id");
        final String CHANNEL_NAME = BuildConfig.APPLICATION_ID.concat("_notification_name");
        final int NOTIFICATION_ID = 100;

        NotificationCompat.Builder builder;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_NONE;
            assert notificationManager != null;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (mChannel == null) {
                mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("App is running background to track your location")
                    .setContentTitle(getString(R.string.app_name));
            startForeground(NOTIFICATION_ID, builder.build());
        } else {
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("App is running background to track your location")
                    .setContentTitle(getString(R.string.app_name));
            startForeground(NOTIFICATION_ID, builder.build());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isLocationTrackingStarted = false;
        removeLocationUpdate();

    }
}
