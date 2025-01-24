package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class CreateLandmarkModel {

    /**
     * mark_id :
     * image :
     * description :
     */

    @SerializedName("mark_id")
    private String markId;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
