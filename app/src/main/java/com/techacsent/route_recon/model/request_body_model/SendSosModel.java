package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class SendSosModel {

    /**
     * lat : 0
     * long : 0
     * sos_time :
     */

    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double longX;
    @SerializedName("sos_time")
    private String sosTime;
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getSosTime() {
        return sosTime;
    }

    public void setSosTime(String sosTime) {
        this.sosTime = sosTime;
    }
}
