package com.techacsent.route_recon.event_bus_object;

public class NotificationBadgeObject {

    private boolean isRemove;

    private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public NotificationBadgeObject( boolean isRemove, String actionName) {
        this.isRemove = isRemove;
        this.actionName = actionName;

    }
}
