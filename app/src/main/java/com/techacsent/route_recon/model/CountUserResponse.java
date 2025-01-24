package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class CountUserResponse {

    /**
     * totalFollowing : 0
     * totalFollower : 1
     * totalPendingRequest : 0
     */

    @SerializedName("totalFollowing")
    private int totalFollowing;
    @SerializedName("totalFollower")
    private int totalFollower;
    @SerializedName("totalPendingRequest")
    private int totalPendingRequest;

    public int getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(int totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public int getTotalFollower() {
        return totalFollower;
    }

    public void setTotalFollower(int totalFollower) {
        this.totalFollower = totalFollower;
    }

    public int getTotalPendingRequest() {
        return totalPendingRequest;
    }

    public void setTotalPendingRequest(int totalPendingRequest) {
        this.totalPendingRequest = totalPendingRequest;
    }
}
