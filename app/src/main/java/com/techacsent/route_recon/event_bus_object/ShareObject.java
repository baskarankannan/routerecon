package com.techacsent.route_recon.event_bus_object;

public class ShareObject {
    private String tripId;
    private boolean shouldShare;

    public ShareObject(String tripId, boolean shouldShare) {
        this.tripId = tripId;
        this.shouldShare = shouldShare;
    }

    public String getTripId() {
        return tripId;
    }

    public boolean isShouldShare() {
        return shouldShare;
    }
}
