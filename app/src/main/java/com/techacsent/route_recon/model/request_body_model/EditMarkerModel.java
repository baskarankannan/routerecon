package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class EditMarkerModel {

    /**
     * markerId : 0
     * radius : 0
     * address : aa
     * fullAddress : aaa
     * description : aaaaa
     */

    @SerializedName("markerId")
    private int markerId;
    @SerializedName("radius")
    private double radius;
    @SerializedName("address")
    private String address;
    @SerializedName("fullAddress")
    private String fullAddress;
    @SerializedName("description")
    private String description;

    public int getMarkerId() {
        return markerId;
    }

    public void setMarkerId(int markerId) {
        this.markerId = markerId;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
