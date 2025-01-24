package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class CreateAccountModel {

    /**
     * username :
     * email :
     * password :
     * fullname :
     * phone :
     * gender :
     * profileImg :
     */

    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("device_id")
    private String deviceId;
    @SerializedName("gender")
    private String gender;
    @SerializedName("profileImg")
    private String profileImg;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
