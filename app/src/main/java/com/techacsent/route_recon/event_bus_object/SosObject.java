package com.techacsent.route_recon.event_bus_object;

public class SosObject {

    private boolean isSend;

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public SosObject(boolean isSend) {
        this.isSend = isSend;
    }
}
