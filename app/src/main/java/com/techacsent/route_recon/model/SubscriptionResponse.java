package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class SubscriptionResponse {

    /**
     * userId : 87
     * lastDate : 2019-06-20 00:00:00
     * subscriptionStatus : notpayable
     */

    @SerializedName("userId")
    private int userId;
    @SerializedName("lastDate")
    private String lastDate;
    @SerializedName("subscriptionStatus")
    private String subscriptionStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}
