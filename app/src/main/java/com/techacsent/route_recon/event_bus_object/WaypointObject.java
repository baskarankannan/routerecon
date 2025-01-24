package com.techacsent.route_recon.event_bus_object;

public class WaypointObject {

    private double lat;
    private double longX;
    private int type;
    private String address;
    private String fullAddress;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongX() {
        return longX;
    }

    public void setLongX(double longX) {
        this.longX = longX;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public WaypointObject(double lat, double longX, int type, String address, String fullAddress) {
        this.lat = lat;
        this.longX = longX;
        this.type = type;
        this.address = address;
        this.fullAddress = fullAddress;
    }
}
