package com.techacsent.route_recon.event_bus_object;

public class ContactInfoObj {

    private String name;
    private String number;
    private String email;
    private boolean isEdit;
    private int id;
    private int pos;



    public ContactInfoObj(String name, String number, String email, boolean isEdit, int id, int pos) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.isEdit = isEdit;
        this.id = id;
        this.pos = pos;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String phone) {
        this.email = phone;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
