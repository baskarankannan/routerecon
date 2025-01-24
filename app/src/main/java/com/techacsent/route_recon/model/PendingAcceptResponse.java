package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class PendingAcceptResponse {

    /**
     * is-following : yes
     */

    @SerializedName("is-following")
    private String isfollowing;

    public String getIsfollowing() {
        return isfollowing;
    }

    public void setIsfollowing(String isfollowing) {
        this.isfollowing = isfollowing;
    }
}
