package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTripLocationModel {

    /**
     * tripStatus : started
     * list : [{"locationStatus":"started","location":{"time":"2018-12-11 04:17:38","lat":23.751489639282,"lon":90.386344909668},"user":{"id":"6","username":"asuss","email":"aa@aa.com","name":"asus","phone":"","gender":"","profileImg":""}}]
     */

    @SerializedName("tripStatus")
    private String tripStatus;
    @SerializedName("list")
    private List<ListBean> list;

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * locationStatus : started
         * location : {"time":"2018-12-11 04:17:38","lat":23.751489639282,"lon":90.386344909668}
         * user : {"id":"6","username":"asuss","email":"aa@aa.com","name":"asus","phone":"","gender":"","profileImg":""}
         */

        @SerializedName("locationStatus")
        private String locationStatus;
        @SerializedName("location")
        private LocationBean location;
        @SerializedName("user")
        private UserBean user;

        public String getLocationStatus() {
            return locationStatus;
        }

        public void setLocationStatus(String locationStatus) {
            this.locationStatus = locationStatus;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class LocationBean {
            /**
             * time : 2018-12-11 04:17:38
             * lat : 23.751489639282
             * lon : 90.386344909668
             */

            @SerializedName("time")
            private String time;
            @SerializedName("lat")
            private double lat;
            @SerializedName("lon")
            private double lon;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }

        public static class UserBean {
            /**
             * id : 6
             * username : asuss
             * email : aa@aa.com
             * name : asus
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
    }
}
