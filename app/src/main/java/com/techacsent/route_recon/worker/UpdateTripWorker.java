package com.techacsent.route_recon.worker;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.techacsent.route_recon.model.UpdatedCreateTripResponse;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpdateTripWorker extends Worker {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);

    public UpdateTripWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String tripResponse = getInputData().getString(Constant.KEY_CREATE_TRIP_RESPONSE);
        UpdatedCreateTripResponse updateTripResponse = new UpdatedCreateTripResponse();
        Gson gson = new Gson();
        updateTripResponse = gson.fromJson(tripResponse, UpdatedCreateTripResponse.class);
        updateTrip(updateTripResponse);
        return Result.success();
    }

    private void updateTrip(UpdatedCreateTripResponse updateTripesponse) {
        try {
            BasicTripDescription basicTripDescription = AppDatabase.getAppDatabase(getApplicationContext()).daoTripBasic().fetchSingleTripById(updateTripesponse.getData().getId());
            Date tripBeginTime;
            tripBeginTime = simpleDateFormat.parse(updateTripesponse.getData().getEndTime());
            basicTripDescription.setTripId(updateTripesponse.getData().getId());
            basicTripDescription.setTripName(updateTripesponse.getData().getTripName());
            basicTripDescription.setBeginTime(updateTripesponse.getData().getBeginTime());
            basicTripDescription.setEndTime(updateTripesponse.getData().getEndTime());
            basicTripDescription.setBeginDateinMills(tripBeginTime.getTime());
            basicTripDescription.setStartPointLat(updateTripesponse.getData().getStartpoint().getLat());
            basicTripDescription.setStartPointLonX(updateTripesponse.getData().getStartpoint().getLongX());
            basicTripDescription.setStartPointAddress(updateTripesponse.getData().getStartpoint().getAddress());
            basicTripDescription.setStartPointFullAddress(updateTripesponse.getData().getStartpoint().getFullAddress());
            basicTripDescription.setEndPointLat(updateTripesponse.getData().getEndpoint().getLat());
            basicTripDescription.setEndPointLonX(updateTripesponse.getData().getEndpoint().getLongX());
            basicTripDescription.setEndPointAddress(updateTripesponse.getData().getEndpoint().getAddress());
            basicTripDescription.setEndPointFullAddress(updateTripesponse.getData().getEndpoint().getFullAddress());
            basicTripDescription.setFriendShared(Integer.parseInt(updateTripesponse.getData().getFriendAttend()));
            //basicTripDescription.setTripJson(updateTripesponse.getData().getTripJson());
            AppDatabase.getAppDatabase(getApplicationContext()).daoTripBasic().updateBasicTrip(basicTripDescription);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
