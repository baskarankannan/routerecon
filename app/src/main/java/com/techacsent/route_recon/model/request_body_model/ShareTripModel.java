package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShareTripModel {

    /**
     * is-public : yes
     * groups : [3,3]
     * trip_sharing_id : 32
     * users : [5]
     * status :
     * all : yes
     */

    @SerializedName("is-public")
    private String ispublic;
    @SerializedName("trip_id")
    private int tripSharingId;
    @SerializedName("status")
    private String status;
    @SerializedName("all")
    private String all;

    @SerializedName("trip_send")
    private String trip_send;

    @SerializedName("groups")
    private List<Integer> groups;
    @SerializedName("users")
    private List<Integer> users;

    public String getIspublic() {
        return ispublic;
    }

    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }

    public int getTripSharingId() {
        return tripSharingId;
    }

    public void setTripSharingId(int tripSharingId) {
        this.tripSharingId = tripSharingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    public String getTrip_send() {
        return trip_send;
    }

    public void setTrip_send(String trip_send) {
        this.trip_send = trip_send;
    }
}
