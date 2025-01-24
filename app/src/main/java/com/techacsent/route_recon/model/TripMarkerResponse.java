package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripMarkerResponse implements Parcelable{


    /**
     * list : [{"markType":4,"lat":23.78683785,"long":90.15639824,"radius":0,"address":"","fullAddress":"","description":"","id":"1689","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[{"lat":23.78683785,"long":90.15639824},{"lat":23.86030828,"long":90.23297158},{"lat":23.77836953,"long":90.23617563}]},{"markType":2,"lat":23.97396389,"long":90.14409731,"radius":0,"address":"Unnamed Road, Bangladesh","fullAddress":"Unnamed Road, Bangladesh,Dhamrai Upazila,Bangladesh","description":"Description","id":"1652","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[]},{"markType":2,"lat":23.86230167,"long":90.4028856,"radius":0,"address":"22 Road-09, Dhaka 1230, Bangladesh","fullAddress":"22 Road-09, Dhaka 1230, Bangladesh,Dhaka,Bangladesh","description":"Description","id":"1650","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[]},{"markType":4,"lat":23.92388801,"long":90.55310484,"radius":0,"address":"","fullAddress":"","description":"","id":"1648","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[{"lat":23.92388801,"long":90.55310484},{"lat":23.90366368,"long":90.54175978}]},{"markType":4,"lat":23.7948102,"long":90.46925657,"radius":0,"address":"","fullAddress":"","description":"","id":"1646","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[{"lat":23.7948102,"long":90.46925657},{"lat":23.83626993,"long":90.50839536},{"lat":23.76276407,"long":90.50221555},{"lat":23.81365718,"long":90.54410093},{"lat":23.75836497,"long":90.47612302}]},{"markType":3,"lat":23.84119708,"long":90.37219478,"radius":6,"address":"Unnamed Road, Dhaka, Bangladesh","fullAddress":"Unnamed Road, Dhaka, Bangladesh,Dhaka,Bangladesh","description":"","id":"1596","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[]},{"markType":1,"lat":23.76079174,"long":90.393746,"radius":0,"address":"Tejgaon - Bijoy Shoroni Flyover Bridge","fullAddress":"Tejgaon - Bijoy Shoroni Flyover Bridge, Dhaka, Dhaka, Bangladesh","description":"","id":"1590","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[]},{"markType":1,"lat":23.74566265,"long":90.3910381,"radius":0,"address":"373/15, Free School Street, Hatirpul, 373/15 Free School St, Dhaka 1205, Bangladesh","fullAddress":"373/15, Free School Street, Hatirpul, 373/15 Free School St, Dhaka 1205, Bangladesh,Dhaka,Bangladesh","description":"Description","id":"1494","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[]},{"markType":2,"lat":37.42312241,"long":-122.08879089,"radius":0,"address":"Google Building 2000, 2000 Charleston Rd, Mountain View, CA 94043, USA","fullAddress":"Google Building 2000, 2000 Charleston Rd, Mountain View, CA 94043, USA,Mountain View,United States","description":null,"id":"1040","tripSpecific":0,"reportType":"","landmarkImage":[],"locations":[]}]
     * has-more : false
     * more-available : no
     * total : 9
     */

    @SerializedName("has-more")
    private boolean hasmore;
    @SerializedName("more-available")
    private String moreavailable;
    @SerializedName("total")
    private int total;
    @SerializedName("list")
    private List<ListBean> list;

    protected TripMarkerResponse(Parcel in) {
        hasmore = in.readByte() != 0;
        moreavailable = in.readString();
        total = in.readInt();
        list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<TripMarkerResponse> CREATOR = new Creator<TripMarkerResponse>() {
        @Override
        public TripMarkerResponse createFromParcel(Parcel in) {
            return new TripMarkerResponse(in);
        }

        @Override
        public TripMarkerResponse[] newArray(int size) {
            return new TripMarkerResponse[size];
        }
    };

    public boolean isHasmore() {
        return hasmore;
    }

    public void setHasmore(boolean hasmore) {
        this.hasmore = hasmore;
    }

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
        dest.writeByte((byte) (hasmore ? 1 : 0));
        dest.writeString(moreavailable);
        dest.writeInt(total);
        dest.writeTypedList(list);
    }

    public static class ListBean implements Parcelable {
        /**
         * markType : 4
         * lat : 23.78683785
         * long : 90.15639824
         * radius : 0
         * address :
         * fullAddress :
         * description :
         * id : 1689
         * tripSpecific : 0
         * reportType :
         * landmarkImage : []
         * locations : [{"lat":23.78683785,"long":90.15639824},{"lat":23.86030828,"long":90.23297158},{"lat":23.77836953,"long":90.23617563}]
         */

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
        @SerializedName("id")
        private String id;
        @SerializedName("tripSpecific")
        private int tripSpecific;
        @SerializedName("reportType")
        private String reportType;
        @SerializedName("landmarkImage")
        private List<?> landmarkImage;
        @SerializedName("locations")
        private List<LocationsBean> locations;

        protected ListBean(Parcel in) {
            markType = in.readInt();
            lat = in.readDouble();
            longX = in.readDouble();
            radius = in.readInt();
            address = in.readString();
            fullAddress = in.readString();
            description = in.readString();
            id = in.readString();
            tripSpecific = in.readInt();
            reportType = in.readString();
            locations = in.createTypedArrayList(LocationsBean.CREATOR);
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

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
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
            dest.writeInt(markType);
            dest.writeDouble(lat);
            dest.writeDouble(longX);
            dest.writeInt(radius);
            dest.writeString(address);
            dest.writeString(fullAddress);
            dest.writeString(description);
            dest.writeString(id);
            dest.writeInt(tripSpecific);
            dest.writeString(reportType);
            dest.writeTypedList(locations);
        }

        public static class LocationsBean implements Parcelable {
            /**
             * lat : 23.78683785
             * long : 90.15639824
             */

            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;

            @SerializedName("name")
            private String name;
            @SerializedName("address")
            private String address;

            @SerializedName("id")
            private int id;

            protected LocationsBean(Parcel in) {
                lat = in.readDouble();
                longX = in.readDouble();
                id = in.readInt();
                address = in.readString();
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }



            public static final Creator<LocationsBean> CREATOR = new Creator<LocationsBean>() {
                @Override
                public LocationsBean createFromParcel(Parcel in) {
                    return new LocationsBean(in);
                }

                @Override
                public LocationsBean[] newArray(int size) {
                    return new LocationsBean[size];
                }
            };

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeDouble(lat);
                dest.writeDouble(longX);
                dest.writeInt(id);
                dest.writeString(address);
            }
        }
    }
}
