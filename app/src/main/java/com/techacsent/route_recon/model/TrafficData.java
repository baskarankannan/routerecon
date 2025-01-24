package com.techacsent.route_recon.model;

public class TrafficData {
    private boolean isShow;

    public TrafficData(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
