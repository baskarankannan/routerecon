package com.techacsent.route_recon.event_bus_object;

public class CreateAccountObject {

    private boolean isComplete;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public CreateAccountObject(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
