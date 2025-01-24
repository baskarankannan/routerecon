package com.techacsent.route_recon.event_bus_object;

public class LoadBadge {
    private boolean isLoad;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public LoadBadge(boolean isLoad) {
        this.isLoad = isLoad;
    }
}
