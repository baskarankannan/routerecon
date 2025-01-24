package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class DeleteTripChecklistModel {

    /**
     * tripChecklistId : 0
     */

    @SerializedName("tripChecklistId")
    private int tripChecklistId;

    public int getTripChecklistId() {
        return tripChecklistId;
    }

    public void setTripChecklistId(int tripChecklistId) {
        this.tripChecklistId = tripChecklistId;
    }
}
