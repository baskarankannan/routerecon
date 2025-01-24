package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class BadgeCountResponse {

    /**
     * success : {"title":"Get Badge","message":"Get badge count"}
     * badge-counts : {"friend-req-accept":0,"friend-req-send":0,"location-share":3,"trip-share":1,"trip-accept":0,"trip-coming-up":0,"trip-start":0,"trip-finished":0,"unknown":0}
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("badge-counts")
    private BadgecountsBean badgecounts;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public BadgecountsBean getBadgecounts() {
        return badgecounts;
    }

    public void setBadgecounts(BadgecountsBean badgecounts) {
        this.badgecounts = badgecounts;
    }

    public static class SuccessBean {
        /**
         * title : Get Badge
         * message : Get badge count
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

    public static class BadgecountsBean {
        /**
         * friend-req-accept : 0
         * friend-req-send : 0
         * location-share : 3
         * trip-share : 1
         * trip-accept : 0
         * trip-coming-up : 0
         * trip-start : 0
         * trip-finished : 0
         * unknown : 0
         */

        @SerializedName("friend-req-accept")
        private int friendreqaccept;
        @SerializedName("friend-req-send")
        private int friendreqsend;
        @SerializedName("location-share")
        private int locationshare;
        @SerializedName("trip-share")
        private int tripshare;
        @SerializedName("trip-accept")
        private int tripaccept;
        @SerializedName("trip-coming-up")
        private int tripcomingup;
        @SerializedName("trip-start")
        private int tripstart;
        @SerializedName("trip-finished")
        private int tripfinished;
        @SerializedName("unknown")
        private int unknown;

        public int getFriendreqaccept() {
            return friendreqaccept;
        }

        public void setFriendreqaccept(int friendreqaccept) {
            this.friendreqaccept = friendreqaccept;
        }

        public int getFriendreqsend() {
            return friendreqsend;
        }

        public void setFriendreqsend(int friendreqsend) {
            this.friendreqsend = friendreqsend;
        }

        public int getLocationshare() {
            return locationshare;
        }

        public void setLocationshare(int locationshare) {
            this.locationshare = locationshare;
        }

        public int getTripshare() {
            return tripshare;
        }

        public void setTripshare(int tripshare) {
            this.tripshare = tripshare;
        }

        public int getTripaccept() {
            return tripaccept;
        }

        public void setTripaccept(int tripaccept) {
            this.tripaccept = tripaccept;
        }

        public int getTripcomingup() {
            return tripcomingup;
        }

        public void setTripcomingup(int tripcomingup) {
            this.tripcomingup = tripcomingup;
        }

        public int getTripstart() {
            return tripstart;
        }

        public void setTripstart(int tripstart) {
            this.tripstart = tripstart;
        }

        public int getTripfinished() {
            return tripfinished;
        }

        public void setTripfinished(int tripfinished) {
            this.tripfinished = tripfinished;
        }

        public int getUnknown() {
            return unknown;
        }

        public void setUnknown(int unknown) {
            this.unknown = unknown;
        }
    }
}
