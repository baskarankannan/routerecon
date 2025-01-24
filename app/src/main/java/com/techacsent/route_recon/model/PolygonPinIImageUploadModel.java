package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class PolygonPinIImageUploadModel {


    @SerializedName("mark_id")
    private int mark_id;

    @SerializedName("pointer_id")
    private int pointer_id;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;

    public int getPointer_id() {
        return pointer_id;
    }

    public void setPointer_id(int pointer_id) {
        this.pointer_id = pointer_id;
    }

    public int getMark_id() {
        return mark_id;
    }

    public void setMark_id(int mark_id) {
        this.mark_id = mark_id;
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
