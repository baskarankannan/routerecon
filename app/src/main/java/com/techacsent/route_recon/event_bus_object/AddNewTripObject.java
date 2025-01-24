package com.techacsent.route_recon.event_bus_object;

public class AddNewTripObject {
    private boolean isAdd;
    private String id;

    public boolean isAdd() {
        return isAdd;
    }

    public AddNewTripObject(boolean isAdd, String id) {

        this.isAdd = isAdd;
        this.id = id;
    }
}
