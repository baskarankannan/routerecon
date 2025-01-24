package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlockedUserResponse {

    /**
     * list : [{"user":{"id":"8","name":"ak","phone":null,"email":null,"gender":null,"username":"akk","am-follower":"blocked","is-following":"no","follower":{"total":0},"following":{"total":0},"is-private":"Yes"}}]
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
         * user : {"id":"8","name":"ak","phone":null,"email":null,"gender":null,"username":"akk","am-follower":"blocked","is-following":"no","follower":{"total":0},"following":{"total":0},"is-private":"Yes"}
         */

        @SerializedName("user")
        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 8
             * name : ak
             * phone : null
             * email : null
             * gender : null
             * username : akk
             * am-follower : blocked
             * is-following : no
             * follower : {"total":0}
             * following : {"total":0}
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

            private int position;

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

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
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
        }
    }
}
