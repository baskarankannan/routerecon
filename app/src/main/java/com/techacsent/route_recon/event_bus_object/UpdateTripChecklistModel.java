package com.techacsent.route_recon.event_bus_object;

public class UpdateTripChecklistModel {

    private String checklist;
    private int id;
    private String state;

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UpdateTripChecklistModel(String checklist, int id, String state) {
        this.checklist = checklist;
        this.id = id;
        this.state = state;
    }
}
