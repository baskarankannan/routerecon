package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class UserTripSharingResponseModel {

    /**
     * trip_sharing_id : 0
     * status :
     */

    @SerializedName("trip_sharing_id")
    private int tripSharingId;
    @SerializedName("status")
    private String status;

    public int getTripSharingId() {
        return tripSharingId;
    }

    public void setTripSharingId(int tripSharingId) {
        this.tripSharingId = tripSharingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
