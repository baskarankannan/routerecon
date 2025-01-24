package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class SubscriptionModel {

    /**
     * user_id : 87
     */

    @SerializedName("user_id")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
