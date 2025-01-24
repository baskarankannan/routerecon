package com.techacsent.route_recon.room_db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserObject {
    @PrimaryKey
    private int userID;

    private String userName;
    private String email;
    private String name;
    private String phone;
    private String gender;
    private String profileImage;

    public UserObject(int userID, String userName, String email, String name, String phone, String gender, String profileImage) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
