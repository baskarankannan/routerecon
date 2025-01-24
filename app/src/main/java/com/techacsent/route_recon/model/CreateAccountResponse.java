package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class CreateAccountResponse {

    /**
     * userId : 20
     * tokenId : MTU0Mjg2MzI4N183NzU0MzE=
     * success : {"title":"Create Account","message":"User registered successfully."}
     * userDetails : {"id":"20","username":"bb","email":"bb@bb.com","name":"bb","phone":"","gender":"","profileImg":""}
     */

    @SerializedName("userId")
    private int userId;
    @SerializedName("tokenId")
    private String tokenId;
    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("userDetails")
    private UserDetailsBean userDetails;

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

    public UserDetailsBean getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsBean userDetails) {
        this.userDetails = userDetails;
    }

    public static class SuccessBean {
        /**
         * title : Create Account
         * message : User registered successfully.
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

    public static class UserDetailsBean {
        /**
         * id : 20
         * username : bb
         * email : bb@bb.com
         * name : bb
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
        @SerializedName("imei")
        private String imei;
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
