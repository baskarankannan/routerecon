package com.techacsent.route_recon.event_bus_object;

public class UpdateObject {
    private boolean isUpdate;
    private int tripId;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public UpdateObject(boolean isUpdate, int tripId) {

        this.isUpdate = isUpdate;
        this.tripId = tripId;
    }
}
