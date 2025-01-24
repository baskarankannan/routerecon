package com.techacsent.route_recon.event_bus_object;

public class UiManageObject {

    private boolean isHide;

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public UiManageObject(boolean isHide) {
        this.isHide = isHide;
    }
}
