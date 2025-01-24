package com.techacsent.route_recon.event_bus_object;

public class MarkerEditObject {
    private String address;
    private String fullAddress;

    public MarkerEditObject(String address, String fullAddress) {
        this.address = address;
        this.fullAddress = fullAddress;
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
}
