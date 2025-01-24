package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WaypointModel {


    /**
     * tripId : 143
     * wayPoints : [{"lat":123.792085647583,"long":190.369644165039,"type":1,"address":"","fullAddress":""},{"lat":123.792085647583,"long":190.369644165039,"type":1,"address":"","fullAddress":""}]
     */

    @SerializedName("tripId")
    private String tripId;
    @SerializedName("wayPoints")
    private List<WayPointsBean> wayPoints;
    @SerializedName("tripJson")
    private String tripJson;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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
         * lat : 123.792085647583
         * long : 190.369644165039
         * type : 1
         * address :
         * fullAddress :
         */

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
