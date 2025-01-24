package com.techacsent.route_recon.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationTracking {

    double latitude;
    double longitude;
    String time;

    public LocationTracking(double latitude, double longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LocationTracking{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", time='" + time + '\'' +
                '}';
    }
}
