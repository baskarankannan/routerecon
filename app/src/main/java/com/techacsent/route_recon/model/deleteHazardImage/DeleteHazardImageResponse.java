package com.techacsent.route_recon.model.deleteHazardImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteHazardImageResponse {

    @SerializedName("success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}
