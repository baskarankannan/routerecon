package com.techacsent.route_recon.room_db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techacsent.route_recon.room_db.entity.ContactDescription;
import com.techacsent.route_recon.room_db.entity.UserObject;

@Dao
public interface DaoUserObject {
    @Insert
    void insertContact(UserObject userObject);

    @Query("SELECT * FROM UserObject WHERE `userID` = :userID")
    UserObject fetchDataByID(int userID);

    @Delete
    void deleteContact(ContactDescription userObject);

    @Query("DELETE FROM UserObject")
    public void nukeTable();

    @Update(onConflict = 1)
    void updateContact(UserObject userObject);
}
