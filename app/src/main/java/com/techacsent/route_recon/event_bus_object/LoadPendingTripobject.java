package com.techacsent.route_recon.event_bus_object;

public class LoadPendingTripobject {
    private boolean isReload;

    public LoadPendingTripobject(boolean isReload) {
        this.isReload = isReload;
    }

    public boolean isReload() {
        return isReload;
    }

    public void setReload(boolean reload) {
        isReload = reload;
    }
}
