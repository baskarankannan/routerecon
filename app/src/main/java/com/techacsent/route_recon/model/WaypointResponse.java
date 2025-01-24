package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WaypointResponse {

    /**
     * title : Waypoint
     * message : Waypoint added successfully
     * wayPoints : [{"id":"725","refTripId":"143","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"","fullAddress":""},{"id":"726","refTripId":"143","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"","fullAddress":""}]
     */

    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;
    @SerializedName("wayPoints")
    private List<WayPointsBean> wayPoints;
    @SerializedName("tripJson")
    private String tripJson;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<WayPointsBean> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPointsBean> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public String getTripJson() {
        return tripJson;
    }

    public void setTripJson(String tripJson) {
        this.tripJson = tripJson;
    }

    public static class WayPointsBean {
        /**
         * id : 725
         * refTripId : 143
         * lat : 123.79208374023
         * long : 190.36964416504
         * type : 1
         * address :
         * fullAddress :
         */

        @SerializedName("id")
        private String id;
        @SerializedName("refTripId")
        private String refTripId;
        @SerializedName("lat")
        private double lat;
        @SerializedName("long")
        private double longX;
        @SerializedName("type")
        private int type;
        @SerializedName("address")
        private String address;
        @SerializedName("fullAddress")
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
    }
}
