package com.techacsent.route_recon.event_bus_object;

public class TripDetailsObject {
    private int tripId;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public TripDetailsObject(int tripId) {

        this.tripId = tripId;
    }
}
