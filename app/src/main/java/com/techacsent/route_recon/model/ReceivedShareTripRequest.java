package com.techacsent.route_recon.model;

public class ReceivedShareTripRequest {

    private String TripId;
    private String SenderName;
    private String TripName;


    public ReceivedShareTripRequest(String tripId, String senderName, String tripName) {
        TripId = tripId;
        SenderName = senderName;
        TripName = tripName;
    }

    public String getTripId() {
        return TripId;
    }

    public void setTripId(String tripId) {
        TripId = tripId;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }
}
