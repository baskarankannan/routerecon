package com.techacsent.route_recon.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

@SuppressLint("Registered")
public class LocationService extends Service implements LocationListener {
    /*
        private static final int REQUEST_CODE_PERMISSION = 2;
    */
/*
    private String mPermission = "android.permission.ACCESS_FINE_LOCATION";
*/
/*
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100L;
*/
    boolean canGetLocation;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    double altitude;
    private Context mContext;
    public String cityName;
    public String address;
    public String countryCode;
    public String zip;
    public String countryName;
    public String state;
    /*public String macAddress;
    public String ipAddress;*/

    public LocationService(Context ctx) {
        this.mContext = ctx;
        this.isGPSEnabled = false;
        this.isNetworkEnabled = false;
        this.canGetLocation = false;
        this.getLocation();
        this.getAddress();
    }

    @SuppressLint({"MissingPermission", "WrongConstant"})
    public Location getLocation() {
        try {
            this.locationManager = (LocationManager) this.mContext.getSystemService("location");
            this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            if (this.isGPSEnabled || this.isNetworkEnabled) {
                this.canGetLocation = true;
                long MIN_TIME_BW_UPDATES = 60000L;
                if (this.isNetworkEnabled) {
                    this.locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 100.0F, this);
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation("network");
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                            this.altitude = this.location.getAltitude();
                        }
                    }
                }

                if (this.isGPSEnabled && this.location == null) {
                    this.locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 100.0F, this);
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation("gps");
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                            this.altitude = this.location.getAltitude();
                        }
                    }
                }
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.location;
    }

    private void getAddress() {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(this.latitude, this.longitude, 1);
            this.address = addresses.get(0).getAddressLine(0);
            this.zip = addresses.get(0).getPostalCode();
            this.cityName = addresses.get(0).getLocality();
            this.countryCode = addresses.get(0).getCountryCode();
            this.countryName = addresses.get(0).getCountryName();
            this.state = addresses.get(0).getAdminArea();
            /*this.macAddress = IPTracker.getMACAddress("wlan0");
            this.ipAddress = IPTracker.getIPAddress(true);*/
        } catch (Exception var3) {
            System.out.println("------------->Exception");
            var3.printStackTrace();
        }

    }

    public double getLatitude() {
        if (this.location != null) {
            this.latitude = this.location.getLatitude();
        }

        return this.latitude;
    }

    public double getLongitude() {
        if (this.location != null) {
            this.longitude = this.location.getLongitude();
        }

        return this.longitude;
    }

    public double getAltitude(){
        if (this.location != null) {
            this.longitude = this.location.getAltitude();
        }

        return this.longitude;
    }


    /*public boolean canGetLocation() {
        return this.canGetLocation;
    }*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
