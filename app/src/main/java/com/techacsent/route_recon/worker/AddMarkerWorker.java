package com.techacsent.route_recon.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.utills.Constant;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;
import java.util.List;

public class AddMarkerWorker extends Worker {
    public AddMarkerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String markerResponse = getInputData().getString(Constant.KEY_MARKER_RESPONSE);
        String markerInfo = getInputData().getString(Constant.KEY_MARKER_INFO);
        String markerId = getInputData().getString(Constant.KEY_TRIP_MARKER_ID);
        CreateTripMarkerModelClass createTripMarkerModelClass = new CreateTripMarkerModelClass();
        TripMarkerCreateResponse createTripMarkerResponse = new TripMarkerCreateResponse();
        Gson gson = new Gson();
        createTripMarkerResponse = gson.fromJson(markerResponse, TripMarkerCreateResponse.class);
        createTripMarkerModelClass = gson.fromJson(markerInfo, CreateTripMarkerModelClass.class);

        saveMarker(createTripMarkerResponse,createTripMarkerModelClass, markerId);
        return Result.success();
    }

    private void saveMarker(TripMarkerCreateResponse tripMarkerCreateResponse, CreateTripMarkerModelClass createTripMarkerModelClass, String markerId) {


        MarkerDescription markerDescription = new MarkerDescription();
        markerDescription.setMarkType(createTripMarkerModelClass.getMarkType());
        markerDescription.setLat(createTripMarkerModelClass.getLat());
        markerDescription.setLongX(createTripMarkerModelClass.getLongX());
        markerDescription.setRadius(createTripMarkerModelClass.getRadius());
        markerDescription.setAddress(createTripMarkerModelClass.getAddress());
        markerDescription.setFullAddress(createTripMarkerModelClass.getFullAddress());
        markerDescription.setTripId(createTripMarkerModelClass.getTripId());
        markerDescription.setMarkerId(markerId);

        if ( ! tripMarkerCreateResponse.getSuccess().getLocations().isEmpty()) {

            Log.e("AddMarkerWorker", " : Class "+ createTripMarkerModelClass.getLocations().toString());

            List<LocationsBeanForLocalDb> locationsBeanForLocalDbList = new ArrayList<>();

            for(   TripMarkerCreateResponse.SuccessBean.Location location  : tripMarkerCreateResponse.getSuccess().getLocations( )){
                LocationsBeanForLocalDb locationsBeanForLocalDb = new LocationsBeanForLocalDb();

                locationsBeanForLocalDb.setId(location.getId());
                locationsBeanForLocalDb.setAddress(location.getAddress());
                locationsBeanForLocalDb.setName(location.getName());
                locationsBeanForLocalDb.setLat(location.getLat());
                locationsBeanForLocalDb.setLongX(location.getLong());
                locationsBeanForLocalDbList.add(locationsBeanForLocalDb);
            }

            markerDescription.setHazardMarkerPointList(locationsBeanForLocalDbList);
        }

        AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().insertMarker(markerDescription);

    }
}
