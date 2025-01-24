package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SharedUserResponse {

    /**
     * list : [{"live-location":{"lat":23.75153923,"lon":90.38639832,"status":"Location shared"},"user":{"id":"1","name":"Hasan","phone":null,"email":null,"gender":null,"username":"h@h","am-follower":"yes","is-following":"yes","follower":{"total":0},"following":{"total":4},"is-private":"Yes"}}]
     * more-available : no
     * total : 1
     */

    @SerializedName("more-available")
    private String moreavailable;
    @SerializedName("total")
    private int total;
    @SerializedName("list")
    private List<ListBean> list;

    public String getMoreavailable() {
        return moreavailable;
    }

    public void setMoreavailable(String moreavailable) {
        this.moreavailable = moreavailable;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * live-location : {"lat":23.75153923,"lon":90.38639832,"status":"Location shared"}
         * user : {"id":"1","name":"Hasan","phone":null,"email":null,"gender":null,"username":"h@h","am-follower":"yes","is-following":"yes","follower":{"total":0},"following":{"total":4},"is-private":"Yes"}
         */

        @SerializedName("live-location")
        private LivelocationBean livelocation;
        @SerializedName("user")
        private UserBean user;

        public LivelocationBean getLivelocation() {
            return livelocation;
        }

        public void setLivelocation(LivelocationBean livelocation) {
            this.livelocation = livelocation;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class LivelocationBean {
            /**
             * lat : 23.75153923
             * lon : 90.38639832
             * status : Location shared
             */

            @SerializedName("lat")
            private double lat;
            @SerializedName("lon")
            private double lon;
            @SerializedName("status")
            private String status;

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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class UserBean {
            /**
             * id : 1
             * name : Hasan
             * phone : null
             * email : null
             * gender : null
             * username : h@h
             * am-follower : yes
             * is-following : yes
             * follower : {"total":0}
             * following : {"total":4}
             * is-private : Yes
             */

            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("phone")
            private Object phone;
            @SerializedName("email")
            private Object email;
            @SerializedName("gender")
            private Object gender;
            @SerializedName("username")
            private String username;
            @SerializedName("am-follower")
            private String amfollower;
            @SerializedName("is-following")
            private String isfollowing;
            @SerializedName("follower")
            private FollowerBean follower;
            @SerializedName("following")
            private FollowingBean following;
            @SerializedName("is-private")
            private String isprivate;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAmfollower() {
                return amfollower;
            }

            public void setAmfollower(String amfollower) {
                this.amfollower = amfollower;
            }

            public String getIsfollowing() {
                return isfollowing;
            }

            public void setIsfollowing(String isfollowing) {
                this.isfollowing = isfollowing;
            }

            public FollowerBean getFollower() {
                return follower;
            }

            public void setFollower(FollowerBean follower) {
                this.follower = follower;
            }

            public FollowingBean getFollowing() {
                return following;
            }

            public void setFollowing(FollowingBean following) {
                this.following = following;
            }

            public String getIsprivate() {
                return isprivate;
            }

            public void setIsprivate(String isprivate) {
                this.isprivate = isprivate;
            }

            public static class FollowerBean {
                /**
                 * total : 0
                 */

                @SerializedName("total")
                private int total;

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }
            }

            public static class FollowingBean {
                /**
                 * total : 4
                 */

                @SerializedName("total")
                private int total;

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }
            }
        }
    }
}
