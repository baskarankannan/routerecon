package com.techacsent.route_recon.room_db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techacsent.route_recon.room_db.entity.MarkerDescription;

import java.util.List;

@Dao
public interface DaoMarker {

    @Insert
    void insertMarker(MarkerDescription model);

    @Insert
    void insertListOfMarker(List<MarkerDescription> markerDescriptionList);

    @Query("SELECT * FROM MarkerDescription WHERE `tripId` = :tripId")
    List<MarkerDescription> fetchMarkerByTripId(String tripId);

    @Query("SELECT * FROM MarkerDescription WHERE `markerId` = :markerId")
    MarkerDescription fetchMarkerByMarkerId(String markerId);

    @Query("SELECT * FROM MarkerDescription")
    List<MarkerDescription> getAll();

    @Update(onConflict = 1)
    void updateMarker(MarkerDescription markerDescription);

    @Delete
    void deleteMarker(MarkerDescription markerDescription);

    @Query("DELETE FROM MarkerDescription")
    public void nukeTable();

    @Delete
    void deleteMarkerList(List<MarkerDescription> markerDescriptionList);

}
