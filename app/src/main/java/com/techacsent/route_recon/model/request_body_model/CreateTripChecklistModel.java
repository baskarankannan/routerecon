package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class CreateTripChecklistModel {

    /**
     * trip_id : 513
     * categoryId : 3
     * checklistId : 44
     */

    @SerializedName("trip_id")
    private int tripId;
    @SerializedName("categoryId")
    private int categoryId;
    @SerializedName("checklistId")
    private int checklistId;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }
}
