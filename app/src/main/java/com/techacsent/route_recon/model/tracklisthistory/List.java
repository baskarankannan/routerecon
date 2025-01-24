package com.techacsent.route_recon.model.tracklisthistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class List {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("startLat")
    @Expose
    private String startLat;
    @SerializedName("startLong")
    @Expose
    private String startLong;
    @SerializedName("endLat")
    @Expose
    private String endLat;
    @SerializedName("endLong")
    @Expose
    private String endLong;
    @SerializedName("creationDate")
    @Expose
    private String creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "List{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", speed='" + speed + '\'' +
                ", distance='" + distance + '\'' +
                ", startLat='" + startLat + '\'' +
                ", startLong='" + startLong + '\'' +
                ", endLat='" + endLat + '\'' +
                ", endLong='" + endLong + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}