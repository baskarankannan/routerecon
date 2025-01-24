package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class CreateChecklistModel {

    /**
     * trip_id : 0
     * title :
     * description :
     */

    @SerializedName("trip_id")
    private int tripId;


    @SerializedName("categoryId")
    private int categoryId;

    @SerializedName("checklistId")
    private int checklistId;



    /*@SerializedName("is_custom")
    private int isCustom;

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;*/

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


    /*public int getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(int isCustom) {
        this.isCustom = isCustom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/
}
