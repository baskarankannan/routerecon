package com.techacsent.route_recon.event_bus_object;

public class NavigationItem {
    boolean isFromCurrentPosition;

    public boolean isFromCurrentPosition() {
        return isFromCurrentPosition;
    }

    public NavigationItem(boolean isFromCurrentPosition) {

        this.isFromCurrentPosition = isFromCurrentPosition;
    }
}
