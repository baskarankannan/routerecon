package com.techacsent.route_recon.model;

public class SystemSecurityModel {
    private String title;
    private String descripion;

    public SystemSecurityModel(String title, String descripion) {
        this.title = title;
        this.descripion = descripion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }
}
