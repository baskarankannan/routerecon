package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class EditContactModel {

    /**
     * id : 220
     * contact_name : demo_name
     * contact_no : 0111
     * email : sam@uk.com
     */

    @SerializedName("id")
    private int id;
    @SerializedName("contact_name")
    private String contactName;
    @SerializedName("contact_no")
    private String contactNo;
    @SerializedName("email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
