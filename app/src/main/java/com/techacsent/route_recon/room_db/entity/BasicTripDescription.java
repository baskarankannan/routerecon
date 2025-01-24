package com.techacsent.route_recon.room_db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class BasicTripDescription implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int key;
    private String tripId;

    private String tripName;
    private String beginTime;
    private String endTime;
    private long beginDateinMills;

    private double startPointLat;
    private double startPointLonX;
    private String startPointAddress;
    private String startPointFullAddress;

    private double endPointLat;
    private double endPointLonX;
    private String endPointAddress;
    private String endPointFullAddress;
    private int friendShared;

    //private String tripJson;

    public BasicTripDescription() {
    }

    protected BasicTripDescription(Parcel in) {
        key = in.readInt();
        tripId = in.readString();
        tripName = in.readString();
        beginTime = in.readString();
        endTime = in.readString();
        beginDateinMills = in.readLong();
        startPointLat = in.readDouble();
        startPointLonX = in.readDouble();
        startPointAddress = in.readString();
        startPointFullAddress = in.readString();
        endPointLat = in.readDouble();
        endPointLonX = in.readDouble();
        endPointAddress = in.readString();
        endPointFullAddress = in.readString();
        friendShared = in.readInt();
        //tripJson = in.readString();
    }

    public static final Creator<BasicTripDescription> CREATOR = new Creator<BasicTripDescription>() {
        @Override
        public BasicTripDescription createFromParcel(Parcel in) {
            return new BasicTripDescription(in);
        }

        @Override
        public BasicTripDescription[] newArray(int size) {
            return new BasicTripDescription[size];
        }
    };

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
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

    public long getBeginDateinMills() {
        return beginDateinMills;
    }

    public void setBeginDateinMills(long beginDateinMills) {
        this.beginDateinMills = beginDateinMills;
    }

    public double getStartPointLat() {
        return startPointLat;
    }

    public void setStartPointLat(double startPointLat) {
        this.startPointLat = startPointLat;
    }

    public double getStartPointLonX() {
        return startPointLonX;
    }

    public void setStartPointLonX(double startPointLonX) {
        this.startPointLonX = startPointLonX;
    }

    public String getStartPointAddress() {
        return startPointAddress;
    }

    public void setStartPointAddress(String startPointAddress) {
        this.startPointAddress = startPointAddress;
    }

    public String getStartPointFullAddress() {
        return startPointFullAddress;
    }

    public void setStartPointFullAddress(String startPointFullAddress) {
        this.startPointFullAddress = startPointFullAddress;
    }

    public double getEndPointLat() {
        return endPointLat;
    }

    public void setEndPointLat(double endPointLat) {
        this.endPointLat = endPointLat;
    }

    public double getEndPointLonX() {
        return endPointLonX;
    }

    public void setEndPointLonX(double endPointLonX) {
        this.endPointLonX = endPointLonX;
    }

    public String getEndPointAddress() {
        return endPointAddress;
    }

    public void setEndPointAddress(String endPointAddress) {
        this.endPointAddress = endPointAddress;
    }

    public String getEndPointFullAddress() {
        return endPointFullAddress;
    }

    public void setEndPointFullAddress(String endPointFullAddress) {
        this.endPointFullAddress = endPointFullAddress;
    }

   /* public String getTripJson() {
        return tripJson;
    }

    public void setTripJson(String tripJson) {
        this.tripJson = tripJson;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    public int getFriendShared() {
        return friendShared;
    }

    public void setFriendShared(int friendShared) {
        this.friendShared = friendShared;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeString(tripId);
        dest.writeString(tripName);
        dest.writeString(beginTime);
        dest.writeString(endTime);
        dest.writeLong(beginDateinMills);
        dest.writeDouble(startPointLat);
        dest.writeDouble(startPointLonX);
        dest.writeString(startPointAddress);
        dest.writeString(startPointFullAddress);
        dest.writeDouble(endPointLat);
        dest.writeDouble(endPointLonX);
        dest.writeString(endPointAddress);
        dest.writeString(endPointFullAddress);
        dest.writeInt(friendShared);
        //dest.writeString(tripJson);
    }
}
