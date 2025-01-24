package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class PostCrimeReportModel {

    /**
     * reportType : 1
     * lat : 1.343433
     * long : 2.453443
     * radius : 3
     * address : dhaka
     * fullAddress : dhaka,bd
     * description : beautiful bd
     */

    @SerializedName("serverTripId")
    private int serverTripId;
    @SerializedName("reportType")
    private int reportType;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double longX;
    @SerializedName("radius")
    private int radius;
    @SerializedName("address")
    private String address;
    @SerializedName("fullAddress")
    private String fullAddress;
    @SerializedName("description")
    private String description;

    public int getServerTripId() {
        return serverTripId;
    }

    public void setServerTripId(int serverTripId) {
        this.serverTripId = serverTripId;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
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
