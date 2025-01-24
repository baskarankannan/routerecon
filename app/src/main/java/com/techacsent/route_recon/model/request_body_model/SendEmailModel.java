package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class SendEmailModel {

    /**
     * email : kelly@usa.com
     * message : test
     */

    @SerializedName("email")
    private String email;
    @SerializedName("message")
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
