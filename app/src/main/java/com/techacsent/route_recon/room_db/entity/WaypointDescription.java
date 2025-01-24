package com.techacsent.route_recon.room_db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.room_db.type_converter.RouteTypeConverter;

import java.util.List;

@Entity
public class WaypointDescription {

    @PrimaryKey (autoGenerate = true)
    private int key;

    private int tripId;

    private String tripJson;

    @TypeConverters(RouteTypeConverter.class)
    private List<WaypointModel.WayPointsBean> wayPointsBeanList;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public List<WaypointModel.WayPointsBean> getWayPointsBeanList() {
        return wayPointsBeanList;
    }

    public void setWayPointsBeanList(List<WaypointModel.WayPointsBean> wayPointsBeanList) {
        this.wayPointsBeanList = wayPointsBeanList;
    }

    public String getTripJson() {
        return tripJson;
    }

    public void setTripJson(String tripJson) {
        this.tripJson = tripJson;
    }
}
