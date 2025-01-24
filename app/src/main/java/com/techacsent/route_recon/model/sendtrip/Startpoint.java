package com.techacsent.route_recon.model.sendtrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Startpoint {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("refTripId")
    @Expose
    private String refTripId;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("long")
    @Expose
    private Double _long;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("fullAddress")
    @Expose
    private String fullAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefTripId() {
        return refTripId;
    }

    public void setRefTripId(String refTripId) {
        this.refTripId = refTripId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String  getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
