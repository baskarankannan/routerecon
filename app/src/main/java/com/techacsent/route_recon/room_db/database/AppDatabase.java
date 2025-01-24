package com.techacsent.route_recon.room_db.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.techacsent.route_recon.room_db.dao.DaoEmergencyContact;
import com.techacsent.route_recon.room_db.dao.DaoMarker;
import com.techacsent.route_recon.room_db.dao.DaoTripBasic;
import com.techacsent.route_recon.room_db.dao.DaoWaypoint;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.ContactDescription;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.utills.Constant;

@Database(entities = {BasicTripDescription.class, MarkerDescription.class, WaypointDescription.class, ContactDescription.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mInstance;

    public abstract DaoTripBasic daoTripBasic();

    public abstract DaoMarker daoMarker();

    public abstract DaoWaypoint daoWaypoint();

    public abstract DaoEmergencyContact daoContact();

    public static AppDatabase getAppDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constant.ROOM_DB_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }
}
