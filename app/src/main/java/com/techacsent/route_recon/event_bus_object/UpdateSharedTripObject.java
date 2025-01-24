package com.techacsent.route_recon.event_bus_object;

public class UpdateSharedTripObject {

    private boolean isUpdate;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public UpdateSharedTripObject(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }
}
