package com.techacsent.route_recon.room_db.type_converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class RouteTypeConverter {

    private static Gson gson = new Gson();
    @TypeConverter
    public static List<WaypointModel.WayPointsBean> stringTolist(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<WaypointModel.WayPointsBean>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<WaypointModel.WayPointsBean> someObjects) {
        return gson.toJson(someObjects);
    }
}
