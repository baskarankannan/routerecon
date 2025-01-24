package com.techacsent.route_recon.model.polygonimageuploadresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Success {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("landmarkImage")
    @Expose
    private List<LandmarkImage> landmarkImage = null;

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

    public List<LandmarkImage> getLandmarkImage() {
        return landmarkImage;
    }

    public void setLandmarkImage(List<LandmarkImage> landmarkImage) {
        this.landmarkImage = landmarkImage;
    }

}
