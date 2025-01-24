package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class EmergenctContactModel {

    /**
     * contact_name : Alik
     * contact_no : 01966261868
     */

    @SerializedName("contact_name")
    private String contactName;
    @SerializedName("contact_no")
    private String contactNo;
    @SerializedName("email")
    private String email;

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
