package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowersResponse implements Parcelable{
    /**
     * list : [{"user":{"id":"4","name":"Akramul","phone":null,"email":"a@a.com","gender":null,"username":"a@a","am-follower":"pending","is-following":"yes","follower":{"total":0},"following":{"total":1},"is-private":"Yes"}},{"user":{"id":"5","name":"Mamun","phone":null,"email":"m@m.com","gender":null,"username":"m@m","am-follower":"pending","is-following":"yes","follower":{"total":0},"following":{"total":1},"is-private":"Yes"}}]
     * more-available : no
     * total : 2
     */

    @SerializedName("more-available")
    private String moreavailable;
    @SerializedName("total")
    private int total;
    @SerializedName("list")
    private List<ListBean> list;

    protected FollowersResponse(Parcel in) {
        moreavailable = in.readString();
        total = in.readInt();
        list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<FollowersResponse> CREATOR = new Creator<FollowersResponse>() {
        @Override
        public FollowersResponse createFromParcel(Parcel in) {
            return new FollowersResponse(in);
        }

        @Override
        public FollowersResponse[] newArray(int size) {
            return new FollowersResponse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moreavailable);
        dest.writeInt(total);
        dest.writeTypedList(list);
    }

    public static class ListBean implements Parcelable {
        /**
         * user : {"id":"4","name":"Akramul","phone":null,"email":"a@a.com","gender":null,"username":"a@a","am-follower":"pending","is-following":"yes","follower":{"total":0},"following":{"total":1},"is-private":"Yes"}
         */

        @SerializedName("user")
        private UserBean user;

        protected ListBean(Parcel in) {
            user = in.readParcelable(UserBean.class.getClassLoader());
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(user, flags);
        }

        public static class UserBean implements Parcelable {
            /**
             * id : 4
             * name : Akramul
             * phone : null
             * email : a@a.com
             * gender : null
             * username : a@a
             * am-follower : pending
             * is-following : yes
             * follower : {"total":0}
             * following : {"total":1}
             * is-private : Yes
             */

            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("phone")
            private Object phone;
            @SerializedName("email")
            private String email;
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
            @SerializedName("subscriptionStatus")
            private String subscriptionStatus;


            private boolean isSelected;

            protected UserBean(Parcel in) {
                id = in.readString();
                name = in.readString();
                email = in.readString();
                username = in.readString();
                amfollower = in.readString();
                isfollowing = in.readString();
                isprivate = in.readString();
                subscriptionStatus = in.readString();
                isSelected = in.readByte() != 0;
            }

            public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
                @Override
                public UserBean createFromParcel(Parcel in) {
                    return new UserBean(in);
                }

                @Override
                public UserBean[] newArray(int size) {
                    return new UserBean[size];
                }
            };

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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
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

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public String getSubscriptionStatus() {
                return subscriptionStatus;
            }

            public void setSubscriptionStatus(String subscriptionStatus) {
                this.subscriptionStatus = subscriptionStatus;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(name);
                dest.writeString(email);
                dest.writeString(username);
                dest.writeString(amfollower);
                dest.writeString(isfollowing);
                dest.writeString(isprivate);
                dest.writeString(subscriptionStatus);
                dest.writeByte((byte) (isSelected ? 1 : 0));
            }

            public static class FollowerBean implements Parcelable {
                /**
                 * total : 0
                 */

                @SerializedName("total")
                private int total;

                protected FollowerBean(Parcel in) {
                    total = in.readInt();
                }

                public static final Creator<FollowerBean> CREATOR = new Creator<FollowerBean>() {
                    @Override
                    public FollowerBean createFromParcel(Parcel in) {
                        return new FollowerBean(in);
                    }

                    @Override
                    public FollowerBean[] newArray(int size) {
                        return new FollowerBean[size];
                    }
                };

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(total);
                }
            }

            public static class FollowingBean implements Parcelable {
                /**
                 * total : 1
                 */

                @SerializedName("total")
                private int total;

                protected FollowingBean(Parcel in) {
                    total = in.readInt();
                }

                public static final Creator<FollowingBean> CREATOR = new Creator<FollowingBean>() {
                    @Override
                    public FollowingBean createFromParcel(Parcel in) {
                        return new FollowingBean(in);
                    }

                    @Override
                    public FollowingBean[] newArray(int size) {
                        return new FollowingBean[size];
                    }
                };

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(total);
                }
            }
        }
    }
}
