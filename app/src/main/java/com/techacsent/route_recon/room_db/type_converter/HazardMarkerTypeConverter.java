package com.techacsent.route_recon.room_db.type_converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class HazardMarkerTypeConverter {
    private static Gson gson = new Gson();
    @TypeConverter
    public static List<LocationsBeanForLocalDb> stringTolist(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<LocationsBeanForLocalDb>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<LocationsBeanForLocalDb> someObjects) {
        return gson.toJson(someObjects);
    }
}
