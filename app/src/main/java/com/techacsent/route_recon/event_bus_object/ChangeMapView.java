package com.techacsent.route_recon.event_bus_object;

public class ChangeMapView {

    private int type;
    private  String view;

    public ChangeMapView(int type, String view) {
        this.type = type;
        this.view = view;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
