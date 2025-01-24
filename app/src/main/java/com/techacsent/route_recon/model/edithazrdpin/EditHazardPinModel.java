package com.techacsent.route_recon.model.edithazrdpin;

import com.google.gson.annotations.SerializedName;

public class EditHazardPinModel {


    @SerializedName("id")
    private int id;

    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String lng;

    @SerializedName("address")
    private String address;
    @SerializedName("name")
    private String name;

    public EditHazardPinModel(int id, String lat, String lng, String address, String name) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.name = name;
    }


}
