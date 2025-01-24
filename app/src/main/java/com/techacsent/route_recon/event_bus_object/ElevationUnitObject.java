package com.techacsent.route_recon.event_bus_object;

public class ElevationUnitObject {
    private boolean isMeter;

    public ElevationUnitObject(boolean isMeter) {
        this.isMeter = isMeter;
    }

    public boolean isMeter() {
        return isMeter;
    }

    public void setMeter(boolean meter) {
        isMeter = meter;
    }
}
