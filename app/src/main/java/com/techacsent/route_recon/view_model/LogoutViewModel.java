package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.LogoutResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutViewModel extends ViewModel {
    public MutableLiveData<LogoutResponse> logoutResponseMutableLiveData;
    private static final String TAG = LogoutViewModel.class.getSimpleName();

    public LiveData<LogoutResponse> getLogout(String token) {
        if (logoutResponseMutableLiveData == null) {
            logoutResponseMutableLiveData = new MutableLiveData<>();
            //login(email,password);
            logout(token);
        }
        return logoutResponseMutableLiveData;
    }

    private void logout(String token) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callLogout(token).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(@NonNull Call<LogoutResponse> call, @NonNull Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body()!=null && response.body().getSuccess().getMessage().equals("Successfully logout")){
                        logoutResponseMutableLiveData.setValue(response.body());
                    }else {
                        logoutResponseMutableLiveData.setValue(null);
                    }
                }else {
                    logoutResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                logoutResponseMutableLiveData.setValue(null);
            }
        });

    }
}
