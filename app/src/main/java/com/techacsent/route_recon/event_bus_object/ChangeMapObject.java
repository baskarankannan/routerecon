package com.techacsent.route_recon.event_bus_object;

public class ChangeMapObject {

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChangeMapObject(int type) {
        this.type = type;
    }
}
