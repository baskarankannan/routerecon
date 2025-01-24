package com.techacsent.route_recon.room_db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.room_db.type_converter.HazardMarkerTypeConverter;

import java.util.List;

@Entity
public class MarkerDescription {
    @PrimaryKey(autoGenerate = true)
    private int key;

    private String markerId;
    private int markType;
    private int tripId;
    private double lat;
    private double longX;
    private double radius;
    private String address;
    private String fullAddress;
    private String description;


    @TypeConverters(HazardMarkerTypeConverter.class)
    private List<LocationsBeanForLocalDb> hazardMarkerPointList;

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
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

    public List<LocationsBeanForLocalDb> getHazardMarkerPointList() {
        return hazardMarkerPointList;
    }

    public void setHazardMarkerPointList(List<LocationsBeanForLocalDb> hazardMarkerPointList) {
        this.hazardMarkerPointList = hazardMarkerPointList;
    }
}
