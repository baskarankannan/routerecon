package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    /**
     * userId : 6
     * tokenId : MTU0Mjg2MDg1NF83NzYzNzU=
     * success : {"title":"Login Success","message":"Login successfully"}
     * userData : {"isPrivate":"Yes","id":"6","username":"Alik","email":"aa@aa.com","name":"Alik Sio","phone":"","gender":"","profileImg":""}
     */

    @SerializedName("userId")
    private int userId;
    @SerializedName("tokenId")
    private String tokenId;
    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("userData")
    private UserDataBean userData;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public UserDataBean getUserData() {
        return userData;
    }

    public void setUserData(UserDataBean userData) {
        this.userData = userData;
    }

    public static class SuccessBean {
        /**
         * title : Login Success
         * message : Login successfully
         */

        @SerializedName("title")
        private String title;
        @SerializedName("message")
        private String message;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class UserDataBean {
        /**
         * isPrivate : Yes
         * id : 6
         * username : Alik
         * email : aa@aa.com
         * name : Alik Sio
         * phone :
         * gender :
         * profileImg :
         */

        @SerializedName("isPrivate")
        private String isPrivate;
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
        @SerializedName("imei")
        private String imei;
        @SerializedName("gender")
        private String gender;
        @SerializedName("profileImg")
        private String profileImg;
        @SerializedName("lastSubscriptionDate")
        private String lastSubscriptionDate;

        public String getLastSubscriptionDate() {
            return lastSubscriptionDate;
        }

        public void setLastSubscriptionDate(String lastSubscriptionDate) {
            this.lastSubscriptionDate = lastSubscriptionDate;
        }

        public String getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(String isPrivate) {
            this.isPrivate = isPrivate;
        }

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

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
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
}
