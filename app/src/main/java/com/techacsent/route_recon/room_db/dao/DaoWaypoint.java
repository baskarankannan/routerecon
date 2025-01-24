package com.techacsent.route_recon.room_db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.techacsent.route_recon.room_db.entity.WaypointDescription;

import java.util.List;

@Dao
public interface DaoWaypoint {
    @Insert
    void insertWaypoint(WaypointDescription waypointDescription);

    @Query("SELECT * FROM WaypointDescription WHERE `tripId` = :tripId")
    WaypointDescription fetchWaypointById(String tripId);

    @Query("SELECT * FROM WaypointDescription")
    List<WaypointDescription> getAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWaypoint(WaypointDescription waypointDescription);

    @Delete
    void deleteWaypoint(WaypointDescription waypointDescription);
}
