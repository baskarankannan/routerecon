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
import java.util.Objects;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SaveTripWorker extends Worker {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);

    public SaveTripWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String tripResponse = getInputData().getString(Constant.KEY_CREATE_TRIP_RESPONSE);
        UpdatedCreateTripResponse createTripResponse = new UpdatedCreateTripResponse();
        Gson gson = new Gson();
        createTripResponse = gson.fromJson(tripResponse, UpdatedCreateTripResponse.class);
        saveTrip(createTripResponse);
        return Result.success();
    }

    private void saveTrip(UpdatedCreateTripResponse createTripResponse) {
        try {
            Date tripBeginTime;
            tripBeginTime = simpleDateFormat.parse(createTripResponse.getData().getEndTime());
            BasicTripDescription basicTripDescription = new BasicTripDescription();
            basicTripDescription.setTripId(createTripResponse.getData().getId());
            basicTripDescription.setTripName(createTripResponse.getData().getTripName());
            basicTripDescription.setBeginTime(createTripResponse.getData().getBeginTime());
            basicTripDescription.setEndTime(createTripResponse.getData().getEndTime());
            basicTripDescription.setBeginDateinMills(tripBeginTime.getTime());
            basicTripDescription.setStartPointLat(createTripResponse.getData().getStartpoint().getLat());
            basicTripDescription.setStartPointLonX(createTripResponse.getData().getStartpoint().getLongX());
            basicTripDescription.setStartPointAddress(createTripResponse.getData().getStartpoint().getAddress());
            basicTripDescription.setStartPointFullAddress(createTripResponse.getData().getStartpoint().getFullAddress());
            basicTripDescription.setEndPointLat(createTripResponse.getData().getEndpoint().getLat());
            basicTripDescription.setEndPointLonX(createTripResponse.getData().getEndpoint().getLongX());
            basicTripDescription.setEndPointAddress(createTripResponse.getData().getEndpoint().getAddress());
            basicTripDescription.setEndPointFullAddress(createTripResponse.getData().getEndpoint().getFullAddress());
            basicTripDescription.setFriendShared(Integer.parseInt(createTripResponse.getData().getFriendAttend())/*createTripResponse.getData().getFriendShared()*/);
            AppDatabase.getAppDatabase(Objects.requireNonNull(getApplicationContext()).getApplicationContext()).daoTripBasic().insertTripBasic(basicTripDescription);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
