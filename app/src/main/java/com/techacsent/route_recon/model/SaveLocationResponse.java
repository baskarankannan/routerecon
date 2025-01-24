package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class SaveLocationResponse {

    /**
     * id : 80
     * liveLocationId : 14
     * lat : 90.999
     * lon : 23.9088
     */

    @SerializedName("id")
    private int id;
    @SerializedName("liveLocationId")
    private String liveLocationId;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLiveLocationId() {
        return liveLocationId;
    }

    public void setLiveLocationId(String liveLocationId) {
        this.liveLocationId = liveLocationId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
