package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancelUserModel {


    /**
     * users : [1,2]
     * trip_sharing_id : 1
     */

    @SerializedName("trip_sharing_id")
    private int tripSharingId;
    @SerializedName("users")
    private List<Integer> users;

    public int getTripSharingId() {
        return tripSharingId;
    }

    public void setTripSharingId(int tripSharingId) {
        this.tripSharingId = tripSharingId;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }
}
