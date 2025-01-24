package com.techacsent.route_recon.model;

public class MapData {
    private int Id;
    private String style;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public MapData(int id, String style) {
        Id = id;
        this.style = style;
    }
}
