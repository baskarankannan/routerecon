package com.techacsent.route_recon.event_bus_object;

public class SyncMarkerObject {

    private boolean isSync;

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public SyncMarkerObject(boolean isSync) {
        this.isSync = isSync;
    }
}
