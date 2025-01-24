package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class VerifyAccountModel {

    /**
     * email : kalamtester@gmail.com
     * code : 936799
     */

    @SerializedName("email")
    private String email;
    @SerializedName("code")
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
