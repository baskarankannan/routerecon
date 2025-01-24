package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class SharingTripLocationResponse {

    /**
     * success : {"title":"Sharing Trip Location","message":"Trip Location share successfully"}
     * data : {"id":1720,"latitude":23.444,"longitude":90.442224}
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("data")
    private DataBean data;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class SuccessBean {
        /**
         * title : Sharing Trip Location
         * message : Trip Location share successfully
         */

        @SerializedName("title")
        private String title;
        @SerializedName("message")
        private String message;

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
    }

    public static class DataBean {
        /**
         * id : 1720
         * latitude : 23.444
         * longitude : 90.442224
         */

        @SerializedName("id")
        private int id;
        @SerializedName("latitude")
        private double latitude;
        @SerializedName("longitude")
        private double longitude;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
