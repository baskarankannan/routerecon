package com.techacsent.route_recon.event_bus_object;

public class EditGroupObject {
    private boolean isEdited;
    private String groupName;
    private int position;

    public EditGroupObject(boolean isEdited, String groupName, int position) {
        this.isEdited = isEdited;
        this.groupName = groupName;
        this.position = position;
    }

    public boolean isEdited() {

        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
