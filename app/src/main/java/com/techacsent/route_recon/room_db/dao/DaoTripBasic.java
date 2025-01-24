package com.techacsent.route_recon.room_db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.techacsent.route_recon.room_db.entity.BasicTripDescription;

import java.util.List;

@Dao
public interface DaoTripBasic {
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)*/
    @Insert
    void insertTripBasic(BasicTripDescription model);

    @Query("SELECT * FROM BasicTripDescription WHERE `tripId` = :tripId")
    List<BasicTripDescription> fetchTripById(String tripId);

    @Query("SELECT * FROM BasicTripDescription WHERE `tripId` = :tripId")
    BasicTripDescription fetchSingleTripById(String tripId);

    @Query("SELECT * FROM BasicTripDescription")
    List<BasicTripDescription> getAll();

    @Query("SELECT * FROM BasicTripDescription WHERE `beginDateinMills` >= :currentTimeInMills")
    List<BasicTripDescription> getNextTrip(long currentTimeInMills);

    @Query("SELECT * FROM BasicTripDescription WHERE `beginDateinMills` < :currentTimeInMills")
    List<BasicTripDescription> getLastTrip(long currentTimeInMills);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBasicTrip(BasicTripDescription basicTripDescription);

    @Delete
    void deleteTripBasic(BasicTripDescription basicTripDescription);

    @Query("DELETE FROM BasicTripDescription")
    void nukeTable();

}
