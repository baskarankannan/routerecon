package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class EditGroupModel {

    /**
     * name : aa
     * id : 0
     */

    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
