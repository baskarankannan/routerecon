package com.techacsent.route_recon.model;

public class ItemData {
    private String title;
    private int drawable;
    private boolean isTraffic;
    private boolean isElevationUnitInMeter;
    private boolean isLabel;
    private boolean isDistanceUnit;

   /* public ItemData(String title, int drawable, boolean isTraffic, boolean isElevationUnit,boolean isLabel) {
        this.title = title;
        this.drawable = drawable;
        this.isTraffic = isTraffic;
        this.isElevationUnitInMeter = isElevationUnit;
        this.isLabel=isLabel;
    }*/

    public ItemData(String title, int drawable, boolean isTraffic, boolean isElevationUnitInMeter, boolean isLabel, boolean isDistanceUnit) {
        this.title = title;
        this.drawable = drawable;
        this.isTraffic = isTraffic;
        this.isElevationUnitInMeter = isElevationUnitInMeter;
        this.isLabel = isLabel;
        this.isDistanceUnit = isDistanceUnit;
    }

    public boolean isLabel() {
        return isLabel;
    }

    public void setLabel(boolean label) {
        isLabel = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public boolean isTraffic() {
        return isTraffic;
    }

    public void setTraffic(boolean traffic) {
        isTraffic = traffic;
    }

    public boolean isElevationUnitInMeter() {
        return isElevationUnitInMeter;
    }

    public void setElevationUnitInMeter(boolean elevationUnitInMeter) {
        isElevationUnitInMeter = elevationUnitInMeter;
    }

    public boolean isDistanceUnit() {
        return isDistanceUnit;
    }

    public void setDistanceUnit(boolean distanceUnit) {
        isDistanceUnit = distanceUnit;
    }
}
