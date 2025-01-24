package com.techacsent.route_recon.event_bus_object;

public class MapRedesignObject {
    private boolean isRelocate;

    public boolean isRelocate() {
        return isRelocate;
    }

    public MapRedesignObject(boolean isRelocate) {
        this.isRelocate = isRelocate;
    }

    public void setRelocate(boolean relocate) {
        isRelocate = relocate;
    }
}
