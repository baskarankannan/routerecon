package com.techacsent.route_recon.event_bus_object;

public class RemoveListObject {
    private boolean isClear;

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public RemoveListObject(boolean isClear) {
        this.isClear = isClear;
    }
}
