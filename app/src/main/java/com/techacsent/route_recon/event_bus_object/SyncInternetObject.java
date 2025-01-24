package com.techacsent.route_recon.event_bus_object;

public class SyncInternetObject {
    private boolean isSync;

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public SyncInternetObject(boolean isSync) {

        this.isSync = isSync;
    }
}
