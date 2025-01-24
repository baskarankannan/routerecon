package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTripDescriptionModel {

    /**
     * statusmsg : success
     * data : {"id":"432","tripSharingId":"557","tripId":"1558031413682","refStartpointId":"3157","refEndpointId":"3158","tripName":"Tapper","tripDescription":null,"beginTime":"2019-05-17 02:30:00","endTime":"2019-05-19 02:30:00","status":"0","creationDate":"2019-05-16 07:31:31","updationDate":"2019-05-16 07:31:31","startpoint":{"id":"3157","refTripId":"432","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":null},"endpoint":{"id":"3158","refTripId":"432","lat":22.356851577759,"long":91.783180236816,"type":1,"address":"Chittagong, Bangladesh","fullAddress":null},"wayPoints":[{"id":"3154","refTripId":"432","lat":23.810352325439,"long":90.411346435547,"type":1,"address":"517/6 Lane - 10, Dhaka, Bangladesh","fullAddress":"517/6 Lane - 10, Dhaka, Bangladesh,Dhaka,Bangladesh"},{"id":"3155","refTripId":"432","lat":23.811159133911,"long":90.410766601562,"type":1,"address":"DOHS Bypass, Dhaka, Bangladesh","fullAddress":"DOHS Bypass, Dhaka, Bangladesh,Dhaka,Bangladesh"},{"id":"3156","refTripId":"432","lat":23.809539794922,"long":90.405296325684,"type":1,"address":"Unnamed Road, Dhaka, Bangladesh","fullAddress":"Unnamed Road, Dhaka, Bangladesh,Dhaka,Bangladesh"}],"markers":[{"id":"2396","userId":"6","refTripId":"432","markType":4,"lat":23.81165063,"long":90.41206766,"radius":0,"address":"449/3 Lane - 8, Dhaka, Bangladesh","fullAddress":"449/3 Lane - 8, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"demo","tripSpecific":1,"landmarkImage":[],"locations":[{"lat":23.81165063,"long":90.41206766},{"lat":23.81139338,"long":90.41412589},{"lat":23.80819485,"long":90.41309781}]},{"id":"2397","userId":"6","refTripId":"432","markType":1,"lat":23.8147834,"long":90.40721174,"radius":0,"address":"DOHS Bypass, Dhaka, Bangladesh","fullAddress":"DOHS Bypass, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"Description","tripSpecific":1,"landmarkImage":[],"locations":[]},{"id":"2398","userId":"6","refTripId":"432","markType":2,"lat":23.81159841,"long":90.40786344,"radius":0,"address":"Unnamed Road, Dhaka, Bangladesh","fullAddress":"Unnamed Road, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"Description","tripSpecific":1,"landmarkImage":[],"locations":[]},{"id":"2400","userId":"6","refTripId":"432","markType":4,"lat":23.81143392,"long":90.41230518,"radius":0,"address":"492/2 Lane - 9, Dhaka, Bangladesh","fullAddress":"492/2 Lane - 9, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"demo","tripSpecific":1,"landmarkImage":[],"locations":[{"lat":23.81143392,"long":90.41230518},{"lat":23.80890906,"long":90.41190012},{"lat":23.81039059,"long":90.41408826}]}],"reports":[],"points":[],"friendShared":"1","friendAttend":[{"id":"16","username":"ww","name":"ww","imei":null,"email":"ww@ww.com","isPrivate":"Yes"},{"id":"6","username":"Alex Loe","imei":null,"name":"Alex","email":"alex@dao.com","isPrivate":"Yes"}]}
     */

    @SerializedName("statusmsg")
    private String statusmsg;
    @SerializedName("data")
    private DataBean data;

    public String getStatusmsg() {
        return statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        this.statusmsg = statusmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 432
         * tripSharingId : 557
         * tripId : 1558031413682
         * refStartpointId : 3157
         * refEndpointId : 3158
         * tripName : Tapper
         * tripDescription : null
         * beginTime : 2019-05-17 02:30:00
         * endTime : 2019-05-19 02:30:00
         * status : 0
         * creationDate : 2019-05-16 07:31:31
         * updationDate : 2019-05-16 07:31:31
         * startpoint : {"id":"3157","refTripId":"432","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":null}
         * endpoint : {"id":"3158","refTripId":"432","lat":22.356851577759,"long":91.783180236816,"type":1,"address":"Chittagong, Bangladesh","fullAddress":null}
         * wayPoints : [{"id":"3154","refTripId":"432","lat":23.810352325439,"long":90.411346435547,"type":1,"address":"517/6 Lane - 10, Dhaka, Bangladesh","fullAddress":"517/6 Lane - 10, Dhaka, Bangladesh,Dhaka,Bangladesh"},{"id":"3155","refTripId":"432","lat":23.811159133911,"long":90.410766601562,"type":1,"address":"DOHS Bypass, Dhaka, Bangladesh","fullAddress":"DOHS Bypass, Dhaka, Bangladesh,Dhaka,Bangladesh"},{"id":"3156","refTripId":"432","lat":23.809539794922,"long":90.405296325684,"type":1,"address":"Unnamed Road, Dhaka, Bangladesh","fullAddress":"Unnamed Road, Dhaka, Bangladesh,Dhaka,Bangladesh"}]
         * markers : [{"id":"2396","userId":"6","refTripId":"432","markType":4,"lat":23.81165063,"long":90.41206766,"radius":0,"address":"449/3 Lane - 8, Dhaka, Bangladesh","fullAddress":"449/3 Lane - 8, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"demo","tripSpecific":1,"landmarkImage":[],"locations":[{"lat":23.81165063,"long":90.41206766},{"lat":23.81139338,"long":90.41412589},{"lat":23.80819485,"long":90.41309781}]},{"id":"2397","userId":"6","refTripId":"432","markType":1,"lat":23.8147834,"long":90.40721174,"radius":0,"address":"DOHS Bypass, Dhaka, Bangladesh","fullAddress":"DOHS Bypass, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"Description","tripSpecific":1,"landmarkImage":[],"locations":[]},{"id":"2398","userId":"6","refTripId":"432","markType":2,"lat":23.81159841,"long":90.40786344,"radius":0,"address":"Unnamed Road, Dhaka, Bangladesh","fullAddress":"Unnamed Road, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"Description","tripSpecific":1,"landmarkImage":[],"locations":[]},{"id":"2400","userId":"6","refTripId":"432","markType":4,"lat":23.81143392,"long":90.41230518,"radius":0,"address":"492/2 Lane - 9, Dhaka, Bangladesh","fullAddress":"492/2 Lane - 9, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"demo","tripSpecific":1,"landmarkImage":[],"locations":[{"lat":23.81143392,"long":90.41230518},{"lat":23.80890906,"long":90.41190012},{"lat":23.81039059,"long":90.41408826}]}]
         * reports : []
         * points : []
         * friendShared : 1
         * friendAttend : [{"id":"16","username":"ww","name":"ww","imei":null,"email":"ww@ww.com","isPrivate":"Yes"},{"id":"6","username":"Alex Loe","imei":null,"name":"Alex","email":"alex@dao.com","isPrivate":"Yes"}]
         */

        @SerializedName("id")
        private String id;
        @SerializedName("tripSharingId")
        private String tripSharingId;
        @SerializedName("tripId")
        private String tripId;
        @SerializedName("tripSharedStatus")
        private String tripSharedStatus;
        @SerializedName("refStartpointId")
        private String refStartpointId;
        @SerializedName("refEndpointId")
        private String refEndpointId;
        @SerializedName("tripName")
        private String tripName;
        @SerializedName("tripDescription")
        private Object tripDescription;
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
        @SerializedName("friendShared")
        private String friendShared;
        @SerializedName("wayPoints")
        private List<WayPointsBean> wayPoints;
        @SerializedName("markers")
        private List<MarkersBean> markers;
        @SerializedName("reports")
        private List<?> reports;
        @SerializedName("points")
        private List<?> points;
        @SerializedName("friendAttend")
        private List<FriendAttendBean> friendAttend;

        protected DataBean(Parcel in) {
            id = in.readString();
            tripSharingId = in.readString();
            tripId = in.readString();
            tripSharedStatus = in.readString();
            refStartpointId = in.readString();
            refEndpointId = in.readString();
            tripName = in.readString();
            beginTime = in.readString();
            endTime = in.readString();
            status = in.readString();
            creationDate = in.readString();
            updationDate = in.readString();
            startpoint = in.readParcelable(StartpointBean.class.getClassLoader());
            endpoint = in.readParcelable(EndpointBean.class.getClassLoader());
            friendShared = in.readString();
            wayPoints = in.createTypedArrayList(WayPointsBean.CREATOR);
            markers = in.createTypedArrayList(MarkersBean.CREATOR);
            friendAttend = in.createTypedArrayList(FriendAttendBean.CREATOR);
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

        public String getTripSharingId() {
            return tripSharingId;
        }

        public void setTripSharingId(String tripSharingId) {
            this.tripSharingId = tripSharingId;
        }

        public String getTripId() {
            return tripId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public String getTripSharedStatus() {
            return tripSharedStatus;
        }

        public void setTripSharedStatus(String tripSharedStatus) {
            this.tripSharedStatus = tripSharedStatus;
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

        public Object getTripDescription() {
            return tripDescription;
        }

        public void setTripDescription(Object tripDescription) {
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

        public String getFriendShared() {
            return friendShared;
        }

        public void setFriendShared(String friendShared) {
            this.friendShared = friendShared;
        }

        public List<WayPointsBean> getWayPoints() {
            return wayPoints;
        }

        public void setWayPoints(List<WayPointsBean> wayPoints) {
            this.wayPoints = wayPoints;
        }

        public List<MarkersBean> getMarkers() {
            return markers;
        }

        public void setMarkers(List<MarkersBean> markers) {
            this.markers = markers;
        }

        public List<?> getReports() {
            return reports;
        }

        public void setReports(List<?> reports) {
            this.reports = reports;
        }

        public List<?> getPoints() {
            return points;
        }

        public void setPoints(List<?> points) {
            this.points = points;
        }

        public List<FriendAttendBean> getFriendAttend() {
            return friendAttend;
        }

        public void setFriendAttend(List<FriendAttendBean> friendAttend) {
            this.friendAttend = friendAttend;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(tripSharingId);
            dest.writeString(tripId);
            dest.writeString(tripSharedStatus);
            dest.writeString(refStartpointId);
            dest.writeString(refEndpointId);
            dest.writeString(tripName);
            dest.writeString(beginTime);
            dest.writeString(endTime);
            dest.writeString(status);
            dest.writeString(creationDate);
            dest.writeString(updationDate);
            dest.writeParcelable(startpoint, flags);
            dest.writeParcelable(endpoint, flags);
            dest.writeString(friendShared);
            dest.writeTypedList(wayPoints);
            dest.writeTypedList(markers);
            dest.writeTypedList(friendAttend);
        }

        public static class StartpointBean implements Parcelable {
            /**
             * id : 3157
             * refTripId : 432
             * lat : 23.810331344604
             * long : 90.412521362305
             * type : 1
             * address : Dhaka, Bangladesh
             * fullAddress : null
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
            private Object fullAddress;

            protected StartpointBean(Parcel in) {
                id = in.readString();
                refTripId = in.readString();
                lat = in.readDouble();
                longX = in.readDouble();
                type = in.readInt();
                address = in.readString();
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

            public Object getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(Object fullAddress) {
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
            }
        }

        public static class EndpointBean implements Parcelable{
            /**
             * id : 3158
             * refTripId : 432
             * lat : 22.356851577759
             * long : 91.783180236816
             * type : 1
             * address : Chittagong, Bangladesh
             * fullAddress : null
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
            private Object fullAddress;

            protected EndpointBean(Parcel in) {
                id = in.readString();
                refTripId = in.readString();
                lat = in.readDouble();
                longX = in.readDouble();
                type = in.readInt();
                address = in.readString();
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

            public Object getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(Object fullAddress) {
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
            }
        }

        public static class WayPointsBean implements Parcelable {
            /**
             * id : 3154
             * refTripId : 432
             * lat : 23.810352325439
             * long : 90.411346435547
             * type : 1
             * address : 517/6 Lane - 10, Dhaka, Bangladesh
             * fullAddress : 517/6 Lane - 10, Dhaka, Bangladesh,Dhaka,Bangladesh
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

            protected WayPointsBean(Parcel in) {
                id = in.readString();
                refTripId = in.readString();
                lat = in.readDouble();
                longX = in.readDouble();
                type = in.readInt();
                address = in.readString();
                fullAddress = in.readString();
            }

            public static final Creator<WayPointsBean> CREATOR = new Creator<WayPointsBean>() {
                @Override
                public WayPointsBean createFromParcel(Parcel in) {
                    return new WayPointsBean(in);
                }

                @Override
                public WayPointsBean[] newArray(int size) {
                    return new WayPointsBean[size];
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

        public static class MarkersBean implements Parcelable {
            /**
             * id : 2396
             * userId : 6
             * refTripId : 432
             * markType : 4
             * lat : 23.81165063
             * long : 90.41206766
             * radius : 0
             * address : 449/3 Lane - 8, Dhaka, Bangladesh
             * fullAddress : 449/3 Lane - 8, Dhaka, Bangladesh,Dhaka,Bangladesh
             * description : demo
             * tripSpecific : 1
             * landmarkImage : []
             * locations : [{"lat":23.81165063,"long":90.41206766},{"lat":23.81139338,"long":90.41412589},{"lat":23.80819485,"long":90.41309781}]
             */

            @SerializedName("id")
            private String id;
            @SerializedName("userId")
            private String userId;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("markType")
            private int markType;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("radius")
            private int radius;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;
            @SerializedName("description")
            private String description;
            @SerializedName("tripSpecific")
            private int tripSpecific;
            @SerializedName("landmarkImage")
            private List<?> landmarkImage;
            @SerializedName("locations")
            private List<LocationsBean> locations;

            protected MarkersBean(Parcel in) {
                id = in.readString();
                userId = in.readString();
                refTripId = in.readString();
                markType = in.readInt();
                lat = in.readDouble();
                longX = in.readDouble();
                radius = in.readInt();
                address = in.readString();
                fullAddress = in.readString();
                description = in.readString();
                tripSpecific = in.readInt();
            }

            public static final Creator<MarkersBean> CREATOR = new Creator<MarkersBean>() {
                @Override
                public MarkersBean createFromParcel(Parcel in) {
                    return new MarkersBean(in);
                }

                @Override
                public MarkersBean[] newArray(int size) {
                    return new MarkersBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
            }

            public int getMarkType() {
                return markType;
            }

            public void setMarkType(int markType) {
                this.markType = markType;
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

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getTripSpecific() {
                return tripSpecific;
            }

            public void setTripSpecific(int tripSpecific) {
                this.tripSpecific = tripSpecific;
            }

            public List<?> getLandmarkImage() {
                return landmarkImage;
            }

            public void setLandmarkImage(List<?> landmarkImage) {
                this.landmarkImage = landmarkImage;
            }

            public List<LocationsBean> getLocations() {
                return locations;
            }

            public void setLocations(List<LocationsBean> locations) {
                this.locations = locations;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(userId);
                dest.writeString(refTripId);
                dest.writeInt(markType);
                dest.writeDouble(lat);
                dest.writeDouble(longX);
                dest.writeInt(radius);
                dest.writeString(address);
                dest.writeString(fullAddress);
                dest.writeString(description);
                dest.writeInt(tripSpecific);
            }

            public static class LocationsBean {
                /**
                 * lat : 23.81165063
                 * long : 90.41206766
                 */

                @SerializedName("lat")
                private double lat;
                @SerializedName("long")
                private double longX;

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
            }
        }

        public static class FriendAttendBean implements Parcelable {
            /**
             * id : 16
             * username : ww
             * name : ww
             * imei : null
             * email : ww@ww.com
             * isPrivate : Yes
             */

            @SerializedName("id")
            private String id;
            @SerializedName("username")
            private String username;
            @SerializedName("name")
            private String name;
            @SerializedName("imei")
            private Object imei;
            @SerializedName("email")
            private String email;
            @SerializedName("isPrivate")
            private String isPrivate;

            protected FriendAttendBean(Parcel in) {
                id = in.readString();
                username = in.readString();
                name = in.readString();
                email = in.readString();
                isPrivate = in.readString();
            }

            public static final Creator<FriendAttendBean> CREATOR = new Creator<FriendAttendBean>() {
                @Override
                public FriendAttendBean createFromParcel(Parcel in) {
                    return new FriendAttendBean(in);
                }

                @Override
                public FriendAttendBean[] newArray(int size) {
                    return new FriendAttendBean[size];
                }
            };

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getImei() {
                return imei;
            }

            public void setImei(Object imei) {
                this.imei = imei;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getIsPrivate() {
                return isPrivate;
            }

            public void setIsPrivate(String isPrivate) {
                this.isPrivate = isPrivate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(username);
                dest.writeString(name);
                dest.writeString(email);
                dest.writeString(isPrivate);
            }
        }
    }
}
