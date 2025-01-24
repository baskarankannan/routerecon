package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class TripStartResponse {

    /**
     * success : {"title":"Trip start","message":"Trip started successfully"}
     * tripStatus : started
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("tripStatus")
    private String tripStatus;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public static class SuccessBean {
        /**
         * title : Trip start
         * message : Trip started successfully
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
}
