package com.techacsent.route_recon.event_bus_object;

public class AddMoreObject {

    private boolean isCancel;

    public AddMoreObject(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }
}
