package com.techacsent.route_recon.event_bus_object;

public class StopServiceObject {
    private boolean isStop;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public StopServiceObject(boolean isStop) {
        this.isStop = isStop;
    }
}
