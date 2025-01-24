package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTripsResponse {

    /**
     * list : [{"trip":{"id":"112","tripId":"1543470714024","tripSharingId":"82","tripName":"Freddie","tripDescription":null,"totalUser":"1","refStartpointId":"783","refEndpointId":"784","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"783","refTripId":"112","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"784","refTripId":"112","lat":22.940877914429,"long":91.406661987305,"type":1,"address":"Feni District, Bangladesh","fullAddress":"Feni District, Bangladesh"},"landmarkImage":[],"friendAttend":"1","friendShared":"1"}},{"trip":{"id":"104","tripId":"1542622544588","tripSharingId":"77","tripName":"Test","tripDescription":null,"totalUser":"1","refStartpointId":"752","refEndpointId":"753","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"752","refTripId":"104","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"753","refTripId":"104","lat":24.894929885864,"long":91.868705749512,"type":1,"address":"Sylhet, Bangladesh","fullAddress":"Sylhet, Bangladesh"},"landmarkImage":[],"friendAttend":"1","friendShared":"1"}},{"trip":{"id":"104","tripId":"1542622544588","tripSharingId":"71","tripName":"Test","tripDescription":null,"totalUser":"1","refStartpointId":"752","refEndpointId":"753","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"752","refTripId":"104","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"753","refTripId":"104","lat":24.894929885864,"long":91.868705749512,"type":1,"address":"Sylhet, Bangladesh","fullAddress":"Sylhet, Bangladesh"},"landmarkImage":[],"friendAttend":"2","friendShared":"1"}},{"trip":{"id":"104","tripId":"1542622544588","tripSharingId":"70","tripName":"Test","tripDescription":null,"totalUser":"1","refStartpointId":"752","refEndpointId":"753","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"752","refTripId":"104","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"753","refTripId":"104","lat":24.894929885864,"long":91.868705749512,"type":1,"address":"Sylhet, Bangladesh","fullAddress":"Sylhet, Bangladesh"},"landmarkImage":[],"friendAttend":"2","friendShared":"1"}},{"trip":{"id":"104","tripId":"1542622544588","tripSharingId":"69","tripName":"Test","tripDescription":null,"totalUser":"1","refStartpointId":"752","refEndpointId":"753","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"752","refTripId":"104","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"753","refTripId":"104","lat":24.894929885864,"long":91.868705749512,"type":1,"address":"Sylhet, Bangladesh","fullAddress":"Sylhet, Bangladesh"},"landmarkImage":[],"friendAttend":"2","friendShared":"1"}},{"trip":{"id":"104","tripId":"1542622544588","tripSharingId":"58","tripName":"Test","tripDescription":null,"totalUser":"1","refStartpointId":"752","refEndpointId":"753","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"752","refTripId":"104","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"753","refTripId":"104","lat":24.894929885864,"long":91.868705749512,"type":1,"address":"Sylhet, Bangladesh","fullAddress":"Sylhet, Bangladesh"},"landmarkImage":[],"friendAttend":"2","friendShared":"1"}},{"trip":{"id":"104","tripId":"1542622544588","tripSharingId":"57","tripName":"Test","tripDescription":null,"totalUser":"1","refStartpointId":"752","refEndpointId":"753","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"752","refTripId":"104","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"753","refTripId":"104","lat":24.894929885864,"long":91.868705749512,"type":1,"address":"Sylhet, Bangladesh","fullAddress":"Sylhet, Bangladesh"},"landmarkImage":[],"friendAttend":"2","friendShared":"1"}}]
     * more-available : no
     * total : 7
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

    public static class ListBean implements Parcelable {
        /**
         * trip : {"id":"112","tripId":"1543470714024","tripSharingId":"82","tripName":"Freddie","tripDescription":null,"totalUser":"1","refStartpointId":"783","refEndpointId":"784","status":"0","beginTime":"0000-00-00 00:00:00","endTime":"0000-00-00 00:00:00","startpoint":{"id":"783","refTripId":"112","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"},"endpoint":{"id":"784","refTripId":"112","lat":22.940877914429,"long":91.406661987305,"type":1,"address":"Feni District, Bangladesh","fullAddress":"Feni District, Bangladesh"},"landmarkImage":[],"friendAttend":"1","friendShared":"1"}
         */

        @SerializedName("trip")
        private TripBean trip;

        protected ListBean(Parcel in) {
            trip = in.readParcelable(TripBean.class.getClassLoader());
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

        public TripBean getTrip() {
            return trip;
        }

        public void setTrip(TripBean trip) {
            this.trip = trip;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(trip, flags);
        }

        public static class TripBean implements Parcelable {
            /**
             * id : 112
             * tripId : 1543470714024
             * tripSharingId : 82
             * tripName : Freddie
             * tripDescription : null
             * totalUser : 1
             * refStartpointId : 783
             * refEndpointId : 784
             * status : 0
             * beginTime : 0000-00-00 00:00:00
             * endTime : 0000-00-00 00:00:00
             * startpoint : {"id":"783","refTripId":"112","lat":23.810331344604,"long":90.412521362305,"type":1,"address":"Dhaka, Bangladesh","fullAddress":"Dhaka, Bangladesh"}
             * endpoint : {"id":"784","refTripId":"112","lat":22.940877914429,"long":91.406661987305,"type":1,"address":"Feni District, Bangladesh","fullAddress":"Feni District, Bangladesh"}
             * landmarkImage : []
             * friendAttend : 1
             * friendShared : 1
             */

            @SerializedName("id")
            private String id;
            @SerializedName("timezone")
            private String timezone;
            @SerializedName("tripId")
            private String tripId;
            @SerializedName("tripSharingId")
            private String tripSharingId;
            @SerializedName("tripSharedStatus")
            private String tripSharedStatus;
            @SerializedName("tripName")
            private String tripName;
            @SerializedName("tripDescription")
            private String tripDescription;
            @SerializedName("totalUser")
            private String totalUser;
            @SerializedName("refStartpointId")
            private String refStartpointId;
            @SerializedName("refEndpointId")
            private String refEndpointId;
            @SerializedName("status")
            private String status;
            @SerializedName("beginTime")
            private String beginTime;
            @SerializedName("endTime")
            private String endTime;
            @SerializedName("startpoint")
            private StartpointBean startpoint;
            @SerializedName("endpoint")
            private EndpointBean endpoint;
            @SerializedName("friendAttend")
            private String friendAttend;
            @SerializedName("friendShared")
            private String friendShared;
            /*@SerializedName("landmarkImage")
            private List<?> landmarkImage;*/

            protected TripBean(Parcel in) {
                id = in.readString();
                timezone = in.readString();
                tripId = in.readString();
                tripSharingId = in.readString();
                tripSharedStatus = in.readString();
                tripName = in.readString();
                tripDescription = in.readString();
                totalUser = in.readString();
                refStartpointId = in.readString();
                refEndpointId = in.readString();
                status = in.readString();
                beginTime = in.readString();
                endTime = in.readString();
                startpoint = in.readParcelable(StartpointBean.class.getClassLoader());
                endpoint = in.readParcelable(EndpointBean.class.getClassLoader());
                friendAttend = in.readString();
                friendShared = in.readString();
            }

            public static final Creator<TripBean> CREATOR = new Creator<TripBean>() {
                @Override
                public TripBean createFromParcel(Parcel in) {
                    return new TripBean(in);
                }

                @Override
                public TripBean[] newArray(int size) {
                    return new TripBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTripId() {
                return tripId;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public String getTripSharingId() {
                return tripSharingId;
            }

            public void setTripSharingId(String tripSharingId) {
                this.tripSharingId = tripSharingId;
            }

            public String getTripSharedStatus() {
                return tripSharedStatus;
            }

            public void setTripSharedStatus(String tripSharedStatus) {
                this.tripSharedStatus = tripSharedStatus;
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

            public String getTotalUser() {
                return totalUser;
            }

            public void setTotalUser(String totalUser) {
                this.totalUser = totalUser;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getFriendAttend() {
                return friendAttend;
            }

            public void setFriendAttend(String friendAttend) {
                this.friendAttend = friendAttend;
            }

            public String getFriendShared() {
                return friendShared;
            }

            public void setFriendShared(String friendShared) {
                this.friendShared = friendShared;
            }

           /* public List<?> getLandmarkImage() {
                return landmarkImage;
            }

            public void setLandmarkImage(List<?> landmarkImage) {
                this.landmarkImage = landmarkImage;
            }*/

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(timezone);
                dest.writeString(tripId);
                dest.writeString(tripSharingId);
                dest.writeString(tripSharedStatus);
                dest.writeString(tripName);
                dest.writeString(tripDescription);
                dest.writeString(totalUser);
                dest.writeString(refStartpointId);
                dest.writeString(refEndpointId);
                dest.writeString(status);
                dest.writeString(beginTime);
                dest.writeString(endTime);
                dest.writeParcelable(startpoint, flags);
                dest.writeParcelable(endpoint, flags);
                dest.writeString(friendAttend);
                dest.writeString(friendShared);
            }

            public static class StartpointBean implements Parcelable {
                /**
                 * id : 783
                 * refTripId : 112
                 * lat : 23.810331344604
                 * long : 90.412521362305
                 * type : 1
                 * address : Dhaka, Bangladesh
                 * fullAddress : Dhaka, Bangladesh
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
                 * id : 784
                 * refTripId : 112
                 * lat : 22.940877914429
                 * long : 91.406661987305
                 * type : 1
                 * address : Feni District, Bangladesh
                 * fullAddress : Feni District, Bangladesh
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
        }
    }
}
