package com.techacsent.route_recon.worker;

import android.content.Context;
import androidx.annotation.NonNull;

import android.widget.Toast;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.techacsent.route_recon.model.SubscriptionResponse;
import com.techacsent.route_recon.model.request_body_model.SubscriptionModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import timber.log.Timber;

public class CheckStatusWorker extends Worker {
    private Data dataModel;

    public CheckStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        getStatus();
        return Result.success(dataModel);
    }


    private void getStatus() {
        SubscriptionModel subscriptionModel = new SubscriptionModel();
        subscriptionModel.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
        ApiService apiService = new ApiCaller();
        apiService.getSubscriptionStatus(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                subscriptionModel, new ResponseCallback<SubscriptionResponse>() {
                    @Override
                    public void onSuccess(SubscriptionResponse data) {
                        Toast.makeText(getApplicationContext(), data.getSubscriptionStatus(), Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(data);
                        dataModel = new Data.Builder().putString("data", jsonString).build();
                        Timber.d(data.getSubscriptionStatus());
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        dataModel = new Data.Builder().putString("msg", th.getMessage()).build();

                    }
                });
    }
}
