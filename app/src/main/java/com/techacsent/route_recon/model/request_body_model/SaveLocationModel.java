package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class SaveLocationModel {

    /**
     * lat :
     * lon :
     */

    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
