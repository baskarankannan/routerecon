package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class ReportInRangeModel {

    /**
     * lat : 20
     * long : 20
     * radius : 23
     */

    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double longX;
    @SerializedName("radius")
    private String radius;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongX() {
        return longX;
    }

    public void setLongX(double longX) {
        this.longX = longX;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
}
