package com.techacsent.route_recon.event_bus_object;

public class TrafficObject {
    private boolean isShow;

    public TrafficObject(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
