package com.techacsent.route_recon.event_bus_object;

public class GpsOnOffObject {
    private boolean isGpsOn;

    public GpsOnOffObject(boolean isGpsOn) {
        this.isGpsOn = isGpsOn;
    }

    public boolean isGpsOn() {
        return isGpsOn;
    }

    public void setGpsOn(boolean gpsOn) {
        isGpsOn = gpsOn;
    }
}
