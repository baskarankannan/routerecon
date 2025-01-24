package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HazardMarkerModel {

    /**
     * markType : 4
     * tripId : 247
     * lat : 23.749574947559946
     * long : 90.45475449545
     * radius : 0
     * address :
     * fullAddress :
     * description :
     * locations : [{"lat":23.7343085647583,"long":90.339644165039},{"lat":23.7343085647583,"long":90.339644165039},{"lat":23.7343085647583,"long":90.339644165039}]
     */

    @SerializedName("markType")
    private int markType;
    @SerializedName("tripId")
    private int tripId;
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
    @SerializedName("locations")
    private List<LocationsBean> locations;

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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

    public List<LocationsBean> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationsBean> locations) {
        this.locations = locations;
    }

    public static class LocationsBean {
        /**
         * lat : 23.7343085647583
         * long : 90.339644165039
         */

        @SerializedName("lat")
        private double lat;
        @SerializedName("long")
        private double longX;

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
    }
}
