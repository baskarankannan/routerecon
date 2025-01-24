package com.techacsent.route_recon.room_db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techacsent.route_recon.room_db.entity.ContactDescription;

import java.util.List;

@Dao
public interface DaoEmergencyContact {
    @Insert
    void insertContact(ContactDescription contactDescription);

    @Insert
    void insertListOfContact(List<ContactDescription> list);

    @Query("SELECT * FROM ContactDescription WHERE `userId` = :userId")
    List<ContactDescription> fetchContactByUserId(int userId);

    @Delete
    void deleteContact(ContactDescription contactDescription);

    @Query("DELETE FROM ContactDescription")
    public void nukeTable();

    @Update(onConflict = 1)
    void updateContact(ContactDescription contactDescription);

}
