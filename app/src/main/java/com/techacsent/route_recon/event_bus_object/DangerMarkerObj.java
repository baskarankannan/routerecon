package com.techacsent.route_recon.event_bus_object;

public class DangerMarkerObj {
    private double radius;
    private double lat;
    private double lonX;
    private boolean isAdd;

    public DangerMarkerObj(double radius, double lat, double lonX, boolean isAdd) {
        this.radius = radius;
        this.lat = lat;
        this.lonX = lonX;
        this.isAdd = isAdd;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLonX() {
        return lonX;
    }

    public void setLonX(double lonX) {
        this.lonX = lonX;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
