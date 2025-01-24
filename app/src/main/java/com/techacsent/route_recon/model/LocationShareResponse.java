package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class LocationShareResponse {

    /**
     * id : 15
     * success : location create successful
     */

    @SerializedName("id")
    private int id;
    @SerializedName("success")
    private String success;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
