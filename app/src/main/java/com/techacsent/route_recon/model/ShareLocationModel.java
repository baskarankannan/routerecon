package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShareLocationModel {

    /**
     * is-public :
     * duration :
     * groups : [3,5]
     * users : [5,7]
     * status :
     * all : no
     */

    @SerializedName("is-public")
    private String ispublic;
    @SerializedName("duration")
    private int duration;
    @SerializedName("status")
    private String status;
    @SerializedName("all")
    private String all;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
}
