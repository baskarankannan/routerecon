package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class UpdateChecklistModel {

    /**
     * checklistId : 0
     * trip_id : 0
     * title :
     * description :
     */

    @SerializedName("tripChecklistId")
    private int tripChecklistId;
    @SerializedName("is_completed")
    private String isCompleted;
    @SerializedName("checklist_text")
    private String checklistText;

    public int getTripChecklistId() {
        return tripChecklistId;
    }

    public void setTripChecklistId(int tripChecklistId) {
        this.tripChecklistId = tripChecklistId;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getChecklistText() {
        return checklistText;
    }

    public void setChecklistText(String checklistText) {
        this.checklistText = checklistText;
    }
}
