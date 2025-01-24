package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarkerDetailResponse {

    /**
     * id : 2
     * userId : 1
     * refTripId : 2
     * markType : 3
     * lat : 37.784580230713
     * long : -122.43952941895
     * radius : 1700
     * address : Divisadero Street
     * fullAddress : 1600 Divisadero Street, San Francisco, California 94115, United States
     * description :
     * tripSpecific : 1
     * landmarkImage : [{"id":9,"imageUrl":"https://devapi.routerecon.com/get-landmark-image/9","description":"test image"}]
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
    private List<LandmarkImageBean> landmarkImage;

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

    public List<LandmarkImageBean> getLandmarkImage() {
        return landmarkImage;
    }

    public void setLandmarkImage(List<LandmarkImageBean> landmarkImage) {
        this.landmarkImage = landmarkImage;
    }

    public static class LandmarkImageBean implements Parcelable {
        /**
         * id : 9
         * imageUrl : https://devapi.routerecon.com/get-landmark-image/9
         * description : test image
         */

        @SerializedName("id")
        private int id;
        @SerializedName("imageUrl")
        private String imageUrl;
        @SerializedName("description")
        private String description;

        private int position;

        public LandmarkImageBean() {
        }

        protected LandmarkImageBean(Parcel in) {
            id = in.readInt();
            imageUrl = in.readString();
            description = in.readString();
            position = in.readInt();
        }

        public static final Creator<LandmarkImageBean> CREATOR = new Creator<LandmarkImageBean>() {
            @Override
            public LandmarkImageBean createFromParcel(Parcel in) {
                return new LandmarkImageBean(in);
            }

            @Override
            public LandmarkImageBean[] newArray(int size) {
                return new LandmarkImageBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(imageUrl);
            dest.writeString(description);
            dest.writeInt(position);
        }
    }
}
