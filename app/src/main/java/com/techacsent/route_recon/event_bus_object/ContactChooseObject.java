package com.techacsent.route_recon.event_bus_object;

public class ContactChooseObject {

    private boolean isAdd;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public ContactChooseObject(boolean isAdd) {
        this.isAdd = isAdd;
    }
}
