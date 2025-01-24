package com.techacsent.route_recon.event_bus_object;

public class ChecklistObject {
    private boolean isChecklist;
    private int id;
    private String checklist;
    private int position;

    public ChecklistObject(boolean isChecklist, int id, String checklist, int position) {
        this.isChecklist = isChecklist;
        this.id = id;
        this.checklist = checklist;
        this.position = position;
    }

    public boolean isChecklist() {
        return isChecklist;
    }

    public void setChecklist(boolean checklist) {
        isChecklist = checklist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
