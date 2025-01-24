package com.techacsent.route_recon.event_bus_object;

public class DataObject {
    private int position;
    private boolean isBlock;

    public boolean isBlock() {
        return isBlock;
    }

    public DataObject(int position, boolean isBlock) {
        this.position = position;
        this.isBlock = isBlock;
    }

    public int getPosition() {
        return position;
    }

}
