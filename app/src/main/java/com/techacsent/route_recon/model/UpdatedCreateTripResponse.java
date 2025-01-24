package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdatedCreateTripResponse {
    /**
     * title : Trip
     * message : Create trip successfully
     * data : {"id":"120","tripId":"dtesstTrip","refStartpointId":"391","refEndpointId":"393","tripName":"wsssttseeklyTrip","tripDescription":"nice trip","beginTime":"2018-07-30 16:00:09","endTime":"2018-07-30 16:00:09","status":"0","creationDate":"2018-11-02 08:19:24","updationDate":"2018-11-02 08:19:24","startpoint":{"id":"391","refTripId":"120","lat":23.792085647583,"long":90.369644165039,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"},"endpoint":{"id":"393","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"},"wayPoints":[{"id":"392","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}],"markers":[{"id":"83","userId":"1","refTripId":"120","markType":1,"lat":123.79208564758,"long":190.36964416504,"radius":4,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh","description":"markers added","tripSpecific":1,"landmarkImage":[]}],"points":[]}
     */

    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable{
        /**
         * id : 120
         * tripId : dtesstTrip
         * refStartpointId : 391
         * refEndpointId : 393
         * tripName : wsssttseeklyTrip
         * tripDescription : nice trip
         * beginTime : 2018-07-30 16:00:09
         * endTime : 2018-07-30 16:00:09
         * status : 0
         * creationDate : 2018-11-02 08:19:24
         * updationDate : 2018-11-02 08:19:24
         * startpoint : {"id":"391","refTripId":"120","lat":23.792085647583,"long":90.369644165039,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}
         * endpoint : {"id":"393","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}
         * wayPoints : [{"id":"392","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}]
         * markers : [{"id":"83","userId":"1","refTripId":"120","markType":1,"lat":123.79208564758,"long":190.36964416504,"radius":4,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh","description":"markers added","tripSpecific":1,"landmarkImage":[]}]
         * points : []
         */

        @SerializedName("id")
        private String id;
        @SerializedName("tripId")
        private String tripId;
        @SerializedName("refStartpointId")
        private String refStartpointId;
        @SerializedName("refEndpointId")
        private String refEndpointId;
        @SerializedName("tripName")
        private String tripName;
        @SerializedName("tripDescription")
        private String tripDescription;
        @SerializedName("beginTime")
        private String beginTime;
        @SerializedName("endTime")
        private String endTime;
        @SerializedName("status")
        private String status;
        @SerializedName("creationDate")
        private String creationDate;
        @SerializedName("updationDate")
        private String updationDate;
        @SerializedName("startpoint")
        private StartpointBean startpoint;
        @SerializedName("endpoint")
        private EndpointBean endpoint;
        /*@SerializedName("tripJson")
        private String tripJson;*/
        @SerializedName("points")
        private List<?> points;
        @SerializedName("friendShared")
        private String friendShared;

        @SerializedName("friendAttend")
        private String friendAttend;

        protected DataBean(Parcel in) {
            id = in.readString();
            tripId = in.readString();
            refStartpointId = in.readString();
            refEndpointId = in.readString();
            tripName = in.readString();
            tripDescription = in.readString();
            beginTime = in.readString();
            endTime = in.readString();
            status = in.readString();
            creationDate = in.readString();
            updationDate = in.readString();
            startpoint = in.readParcelable(StartpointBean.class.getClassLoader());
            endpoint = in.readParcelable(EndpointBean.class.getClassLoader());
            friendShared = in.readString();
            friendAttend = in.readString();
            //tripJson = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTripId() {
            return tripId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public String getRefStartpointId() {
            return refStartpointId;
        }

        public void setRefStartpointId(String refStartpointId) {
            this.refStartpointId = refStartpointId;
        }

        public String getRefEndpointId() {
            return refEndpointId;
        }

        public void setRefEndpointId(String refEndpointId) {
            this.refEndpointId = refEndpointId;
        }

        public String getTripName() {
            return tripName;
        }

        public void setTripName(String tripName) {
            this.tripName = tripName;
        }

        public String getTripDescription() {
            return tripDescription;
        }

        public void setTripDescription(String tripDescription) {
            this.tripDescription = tripDescription;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getUpdationDate() {
            return updationDate;
        }

        public void setUpdationDate(String updationDate) {
            this.updationDate = updationDate;
        }

        public StartpointBean getStartpoint() {
            return startpoint;
        }

        public void setStartpoint(StartpointBean startpoint) {
            this.startpoint = startpoint;
        }

        public EndpointBean getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(EndpointBean endpoint) {
            this.endpoint = endpoint;
        }

        /*public String getTripJson() {
            return tripJson;
        }

        public void setTripJson(String tripJson) {
            this.tripJson = tripJson;
        }*/

        public List<?> getPoints() {
            return points;
        }

        public void setPoints(List<?> points) {
            this.points = points;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getFriendShared() {
            return friendShared;
        }

        public void setFriendShared(String friendShared) {
            this.friendShared = friendShared;
        }

        public String getFriendAttend() {
            return friendAttend;
        }

        public void setFriendAttend(String friendAttend) {
            this.friendAttend = friendAttend;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(tripId);
            dest.writeString(refStartpointId);
            dest.writeString(refEndpointId);
            dest.writeString(tripName);
            dest.writeString(tripDescription);
            dest.writeString(beginTime);
            dest.writeString(endTime);
            dest.writeString(status);
            dest.writeString(creationDate);
            dest.writeString(updationDate);
            dest.writeParcelable(startpoint, flags);
            dest.writeParcelable(endpoint, flags);
            dest.writeString(friendShared);
            dest.writeString(friendAttend);
            //dest.writeString(tripJson);
        }

        public static class StartpointBean implements Parcelable {
            /**
             * id : 391
             * refTripId : 120
             * lat : 23.792085647583
             * long : 90.369644165039
             * type : 1
             * address : Dhaka
             * fullAddress : Dhaka, Dhaka, Bangladesh
             */

            @SerializedName("id")
            private String id;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("type")
            private int type;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;

            protected StartpointBean(Parcel in) {
                id = in.readString();
                refTripId = in.readString();
                lat = in.readDouble();
                longX = in.readDouble();
                type = in.readInt();
                address = in.readString();
                fullAddress = in.readString();
            }

            public static final Creator<StartpointBean> CREATOR = new Creator<StartpointBean>() {
                @Override
                public StartpointBean createFromParcel(Parcel in) {
                    return new StartpointBean(in);
                }

                @Override
                public StartpointBean[] newArray(int size) {
                    return new StartpointBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLongX() {
                return longX;
            }

            public void setLongX(double longX) {
                this.longX = longX;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(refTripId);
                dest.writeDouble(lat);
                dest.writeDouble(longX);
                dest.writeInt(type);
                dest.writeString(address);
                dest.writeString(fullAddress);
            }
        }

        public static class EndpointBean implements Parcelable {
            /**
             * id : 393
             * refTripId : 120
             * lat : 123.79208374023
             * long : 190.36964416504
             * type : 1
             * address : Dhaka
             * fullAddress : Dhaka, Dhaka, Bangladesh
             */

            @SerializedName("id")
            private String id;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("type")
            private int type;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;

            protected EndpointBean(Parcel in) {
                id = in.readString();
                refTripId = in.readString();
                lat = in.readDouble();
                longX = in.readDouble();
                type = in.readInt();
                address = in.readString();
                fullAddress = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(refTripId);
                dest.writeDouble(lat);
                dest.writeDouble(longX);
                dest.writeInt(type);
                dest.writeString(address);
                dest.writeString(fullAddress);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<EndpointBean> CREATOR = new Creator<EndpointBean>() {
                @Override
                public EndpointBean createFromParcel(Parcel in) {
                    return new EndpointBean(in);
                }

                @Override
                public EndpointBean[] newArray(int size) {
                    return new EndpointBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLongX() {
                return longX;
            }

            public void setLongX(double longX) {
                this.longX = longX;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
            }
        }


    }
}
