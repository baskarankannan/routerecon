package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class UserDetailsResponse {

    /**
     * data : {"id":"6","username":"Alik","email":"aa@aa.com","name":"Alik Sio","phone":"","gender":"","profileImg":""}
     * success : {"title":"My Profile"}
     */

    @SerializedName("data")
    private DataBean data;
    @SerializedName("success")
    private SuccessBean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * id : 6
         * username : Alik
         * email : aa@aa.com
         * name : Alik Sio
         * phone :
         * gender :
         * profileImg :
         */

        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("name")
        private String name;
        @SerializedName("phone")
        private String phone;
        @SerializedName("gender")
        private String gender;
        @SerializedName("profileImg")
        private String profileImg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getProfileImg() {
            return profileImg;
        }

        public void setProfileImg(String profileImg) {
            this.profileImg = profileImg;
        }
    }

    public static class SuccessBean {
        /**
         * title : My Profile
         */

        @SerializedName("title")
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
