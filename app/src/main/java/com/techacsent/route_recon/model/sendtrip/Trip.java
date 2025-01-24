package com.techacsent.route_recon.model.sendtrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tripId")
    @Expose
    private String tripId;
    @SerializedName("userId")
    @Expose
    private String userId;


    @SerializedName("refStartpointId")
    @Expose
    private String refStartpointId;
    @SerializedName("refEndpointId")
    @Expose
    private String refEndpointId;
    @SerializedName("tripName")
    @Expose
    private String tripName;




    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("tripJson")
    @Expose
    private String tripJson;
    @SerializedName("beginTime")
    @Expose
    private String beginTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("tripIsUpdated")
    @Expose
    private String tripIsUpdated;
    @SerializedName("creationDate")
    @Expose
    private String creationDate;
    @SerializedName("updationDate")
    @Expose
    private String updationDate;

    @SerializedName("senderName")
    @Expose
    private String senderName;


    @SerializedName("startpoint")
    @Expose
    private Startpoint startpoint;
    @SerializedName("endpoint")
    @Expose
    private Endpoint endpoint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getRefStartpointId() {
        return refStartpointId;
    }

    public void setRefStartpointId(String refStartpointId) {
        this.refStartpointId = refStartpointId;
    }

    public String getRefEndpointId() {
        return refEndpointId;
    }

    public void setRefEndpointId(String refEndpointId) {
        this.refEndpointId = refEndpointId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTripJson() {
        return tripJson;
    }

    public void setTripJson(String tripJson) {
        this.tripJson = tripJson;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTripIsUpdated() {
        return tripIsUpdated;
    }

    public void setTripIsUpdated(String tripIsUpdated) {
        this.tripIsUpdated = tripIsUpdated;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdationDate() {
        return updationDate;
    }

    public void setUpdationDate(String updationDate) {
        this.updationDate = updationDate;
    }


    public Startpoint getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(Startpoint startpoint) {
        this.startpoint = startpoint;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
