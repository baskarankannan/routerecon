package com.techacsent.route_recon.model.request_body_model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*import javax.annotation.Nullable;*/

public class CreateTripModelClass implements Parcelable {


    /**
     * tripId : 9A199762-4F18-43BB-896F-824947F47D5F
     * tripName : Dhaka
     * tripDescription :
     * beginTime : 2018-09-05 09:32:33
     * endTime : 2018-09-06 09:32:39
     * endPoint : {"fullAddress":"Dhaka, Dhaka, Bangladesh","long":90.38350161353162,"id":"","type":1,"lat":23.685113159452285,"address":"Dhaka"}
     * startPoint : {"fullAddress":"BRTC Bus Stand, Dhaka, Dhaka, Bangladesh","long":90.37065323152979,"id":"","type":1,"lat":23.773578798234524,"address":"BRTC Bus Stand"}
     * updationDate : 2018-09-05 09:32:44
     * markers : [{"long":90.36896632977368,"id":"","tripSpecific":1,"address":"Boishakhi","description":"","radius":0,"fullAddress":"Boishakhi, Dhaka, Dhaka, Bangladesh","lat":23.740104882828646,"markType":1},{"long":90.39985203353694,"id":"","tripSpecific":1,"address":"InterContinental Dhaka","description":"","radius":0,"fullAddress":"InterContinental Dhaka, Dhaka, Dhaka, Bangladesh","lat":23.74304325115176,"markType":2},{"long":90.37911696837682,"id":"","tripSpecific":1,"address":"New Market","description":"","radius":1000,"fullAddress":"New Market, Dhaka, Dhaka, Bangladesh","lat":23.727000541712385,"markType":3}]
     * wayPoints : [{"fullAddress":"Sky View Restaurant, Dhaka, Dhaka, Bangladesh","long":90.39100275883857,"id":"","type":1,"lat":23.758925175535396,"address":"Sky View Restaurant"},{"fullAddress":"Ramna Park, Dhaka, Dhaka, Bangladesh","long":90.40508276777769,"id":"","type":1,"lat":23.737561516487432,"address":"Ramna Park"}]
     * creationDate : 2018-09-05 09:32:44
     * points : []
     */


    @Nullable
    @SerializedName("id")
    private String id;
    @SerializedName("tripId")
    private String tripId;
    @SerializedName("tripName")
    private String tripName;
    @SerializedName("tripDescription")
    private String tripDescription;
    @SerializedName("beginTime")
    private String beginTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("endPoint")
    private EndPointBean endPoint;
    @SerializedName("startPoint")
    private StartPointBean startPoint;
    @SerializedName("updationDate")
    private String updationDate;
    @SerializedName("creationDate")
    private String creationDate;
    @SerializedName("markers")
    private List<MarkersBean> markers;
    @SerializedName("wayPoints")
    private List<WayPointsBean> wayPoints;
    @SerializedName("points")
    private List<?> points;
    @SerializedName("tripJson")
    private String tripJson;

    @SerializedName("timeZone")
    private String timeZone;

    public CreateTripModelClass() {
    }

    protected CreateTripModelClass(Parcel in) {
        tripId = in.readString();
        tripName = in.readString();
        tripDescription = in.readString();
        beginTime = in.readString();
        endTime = in.readString();
        endPoint = in.readParcelable(EndPointBean.class.getClassLoader());
        startPoint = in.readParcelable(StartPointBean.class.getClassLoader());
        updationDate = in.readString();
        creationDate = in.readString();
        markers = in.createTypedArrayList(MarkersBean.CREATOR);
        wayPoints = in.createTypedArrayList(WayPointsBean.CREATOR);
        tripJson = in.readString();
        timeZone = in.readString();
    }

    public static final Creator<CreateTripModelClass> CREATOR = new Creator<CreateTripModelClass>() {
        @Override
        public CreateTripModelClass createFromParcel(Parcel in) {
            return new CreateTripModelClass(in);
        }

        @Override
        public CreateTripModelClass[] newArray(int size) {
            return new CreateTripModelClass[size];
        }
    };

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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

    public EndPointBean getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPointBean endPoint) {
        this.endPoint = endPoint;
    }

    public StartPointBean getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(StartPointBean startPoint) {
        this.startPoint = startPoint;
    }

    public String getUpdationDate() {
        return updationDate;
    }

    public void setUpdationDate(String updationDate) {
        this.updationDate = updationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<MarkersBean> getMarkers() {
        return markers;
    }

    public void setMarkers(List<MarkersBean> markers) {
        this.markers = markers;
    }

    public List<WayPointsBean> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPointsBean> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public List<?> getPoints() {
        return points;
    }

    public void setPoints(List<?> points) {
        this.points = points;
    }

    public String getTripJson() {
        return tripJson;
    }

    public void setTripJson(String tripJson) {
        this.tripJson = tripJson;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripId);
        dest.writeString(tripName);
        dest.writeString(tripDescription);
        dest.writeString(beginTime);
        dest.writeString(endTime);
        dest.writeParcelable(endPoint, flags);
        dest.writeParcelable(startPoint, flags);
        dest.writeString(updationDate);
        dest.writeString(creationDate);
        dest.writeTypedList(markers);
        dest.writeTypedList(wayPoints);
        dest.writeString(tripJson);
        dest.writeString(timeZone);
    }

    public static class EndPointBean implements Parcelable {
        /**
         * fullAddress : Dhaka, Dhaka, Bangladesh
         * long : 90.38350161353162
         * id :
         * type : 1
         * lat : 23.685113159452285
         * address : Dhaka
         */

        @SerializedName("fullAddress")
        private String fullAddress;
        @SerializedName("long")
        private double longX;
        @SerializedName("id")
        private String id;
        @SerializedName("type")
        private int type;
        @SerializedName("lat")
        private double lat;
        @SerializedName("address")
        private String address;

        public EndPointBean() {
        }

        protected EndPointBean(Parcel in) {
            fullAddress = in.readString();
            longX = in.readDouble();
            id = in.readString();
            type = in.readInt();
            lat = in.readDouble();
            address = in.readString();
        }

        public static final Creator<EndPointBean> CREATOR = new Creator<EndPointBean>() {
            @Override
            public EndPointBean createFromParcel(Parcel in) {
                return new EndPointBean(in);
            }

            @Override
            public EndPointBean[] newArray(int size) {
                return new EndPointBean[size];
            }
        };

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(fullAddress);
            dest.writeDouble(longX);
            dest.writeString(id);
            dest.writeInt(type);
            dest.writeDouble(lat);
            dest.writeString(address);
        }
    }

    public static class StartPointBean implements Parcelable {
        /**
         * fullAddress : BRTC Bus Stand, Dhaka, Dhaka, Bangladesh
         * long : 90.37065323152979
         * id :
         * type : 1
         * lat : 23.773578798234524
         * address : BRTC Bus Stand
         */

        @SerializedName("fullAddress")
        private String fullAddress;
        @SerializedName("long")
        private double longX;
        @SerializedName("id")
        private String id;
        @SerializedName("type")
        private int type;
        @SerializedName("lat")
        private double lat;
        @SerializedName("address")
        private String address;

        public StartPointBean() {
        }

        protected StartPointBean(Parcel in) {
            fullAddress = in.readString();
            longX = in.readDouble();
            id = in.readString();
            type = in.readInt();
            lat = in.readDouble();
            address = in.readString();
        }

        public static final Creator<StartPointBean> CREATOR = new Creator<StartPointBean>() {
            @Override
            public StartPointBean createFromParcel(Parcel in) {
                return new StartPointBean(in);
            }

            @Override
            public StartPointBean[] newArray(int size) {
                return new StartPointBean[size];
            }
        };

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(fullAddress);
            dest.writeDouble(longX);
            dest.writeString(id);
            dest.writeInt(type);
            dest.writeDouble(lat);
            dest.writeString(address);
        }
    }

    public static class MarkersBean implements Parcelable {
        /**
         * long : 90.36896632977368
         * id :
         * tripSpecific : 1
         * address : Boishakhi
         * description :
         * radius : 0
         * fullAddress : Boishakhi, Dhaka, Dhaka, Bangladesh
         * lat : 23.740104882828646
         * markType : 1
         */

        @SerializedName("long")
        private double longX;
        @SerializedName("id")
        private String id;
        @SerializedName("tripSpecific")
        private int tripSpecific;
        @SerializedName("address")
        private String address;
        @SerializedName("description")
        private String description;
        @SerializedName("radius")
        private double radius;
        @SerializedName("fullAddress")
        private String fullAddress;
        @SerializedName("lat")
        private double lat;
        @SerializedName("markType")
        private int markType;

        public MarkersBean() {
        }

        protected MarkersBean(Parcel in) {
            longX = in.readDouble();
            id = in.readString();
            tripSpecific = in.readInt();
            address = in.readString();
            description = in.readString();
            radius = in.readDouble();
            fullAddress = in.readString();
            lat = in.readDouble();
            markType = in.readInt();
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

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTripSpecific() {
            return tripSpecific;
        }

        public void setTripSpecific(int tripSpecific) {
            this.tripSpecific = tripSpecific;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getRadius() {
            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public int getMarkType() {
            return markType;
        }

        public void setMarkType(int markType) {
            this.markType = markType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(longX);
            dest.writeString(id);
            dest.writeInt(tripSpecific);
            dest.writeString(address);
            dest.writeString(description);
            dest.writeDouble(radius);
            dest.writeString(fullAddress);
            dest.writeDouble(lat);
            dest.writeInt(markType);
        }
    }

    public static class WayPointsBean implements Parcelable {
        /**
         * fullAddress : Sky View Restaurant, Dhaka, Dhaka, Bangladesh
         * long : 90.39100275883857
         * id :
         * type : 1
         * lat : 23.758925175535396
         * address : Sky View Restaurant
         */

        @SerializedName("fullAddress")
        private String fullAddress;
        @SerializedName("long")
        private double longX;
        @SerializedName("id")
        private String id;
        @SerializedName("type")
        private int type;
        @SerializedName("lat")
        private double lat;
        @SerializedName("address")
        private String address;

        private long markerId;

        public WayPointsBean() {
        }

        protected WayPointsBean(Parcel in) {
            fullAddress = in.readString();
            longX = in.readDouble();
            id = in.readString();
            type = in.readInt();
            lat = in.readDouble();
            address = in.readString();
            markerId = in.readLong();
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

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getMarkerId() {
            return markerId;
        }

        public void setMarkerId(long markerId) {
            this.markerId = markerId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(fullAddress);
            dest.writeDouble(longX);
            dest.writeString(id);
            dest.writeInt(type);
            dest.writeDouble(lat);
            dest.writeString(address);
            dest.writeLong(markerId);
        }
    }
}
