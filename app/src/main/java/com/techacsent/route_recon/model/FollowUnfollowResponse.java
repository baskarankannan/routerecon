package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class FollowUnfollowResponse {

    /**
     * am-follower : Pending
     */

    @SerializedName("am-follower")
    private String amfollower;

    public String getAmfollower() {
        return amfollower;
    }

    public void setAmfollower(String amfollower) {
        this.amfollower = amfollower;
    }
}
