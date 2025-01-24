package com.techacsent.route_recon.model.deletehazardpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteHazardPinResponse {

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
