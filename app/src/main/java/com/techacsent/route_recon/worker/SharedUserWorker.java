package com.techacsent.route_recon.worker;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.techacsent.route_recon.model.SharedUserResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SharedUserWorker extends Worker {
    private Data convertedData;

    public SharedUserWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (convertedData != null) {
            getSharedUser();
            return Result.success(convertedData);
        } else return Result.failure();
    }


    private void getSharedUser() {
        ApiService apiService = new ApiCaller();
        apiService.getSharedLocationUser(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "yes", 10, 0, new ResponseCallback<SharedUserResponse>() {
            @Override
            public void onSuccess(SharedUserResponse data) {
                if (data != null) {
                    if (data.getList() != null) {
                        Gson gson = new Gson();
                        String tripResponse = gson.toJson(data);
                        convertedData = new Data.Builder().putString(Constant.KEY_SHARED_USER_RESPONSE, tripResponse).build();
                    }
                }
            }

            @Override
            public void onError(Throwable th) {
                th.getMessage();
            }
        });
    }
}
