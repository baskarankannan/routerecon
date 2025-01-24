package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class UserTokenModel {

    /**
     * user_id : 0
     * token :
     * device_type : 0
     */

    @SerializedName("user_id")
    private int userId;
    @SerializedName("token")
    private String token;
    @SerializedName("device_type")
    private int deviceType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
