package com.techacsent.route_recon.event_bus_object;

public class DistanceUnitObject {
    private boolean isImperial;

    public DistanceUnitObject(boolean isImperial) {
        this.isImperial = isImperial;
    }

    public boolean isImperial() {
        return isImperial;
    }

    public void setImperial(boolean imperial) {
        isImperial = imperial;
    }
}
