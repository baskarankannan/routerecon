package com.techacsent.route_recon.event_bus_object;

public class CoordinateUnitObject {

    String unitType;

    public CoordinateUnitObject(String unit) {

        unitType = unit;

    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}