package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.CancelSharedTripResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationCancelViewModel extends ViewModel {
    private MutableLiveData<CancelSharedTripResponse> cancelSharedTripResponseMutableLiveData;

    public LiveData<CancelSharedTripResponse> getCancelSharedTrip(String token, String tripSharingId) {
        if (cancelSharedTripResponseMutableLiveData == null) {
            cancelSharedTripResponseMutableLiveData = new MutableLiveData<>();
        }
        cancelSharedTrip(token, tripSharingId);
        return cancelSharedTripResponseMutableLiveData;
    }

    private void cancelSharedTrip(String token, String tripSharingId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("trip_sharing_id", tripSharingId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callCancelSharedTrip(token, requestBody).enqueue(new Callback<CancelSharedTripResponse>() {
                @Override
                public void onResponse(@NotNull Call<CancelSharedTripResponse> call, @NotNull Response<CancelSharedTripResponse> response) {
                    if (response.isSuccessful()) {
                        cancelSharedTripResponseMutableLiveData.postValue(response.body());
                    } else {
                        cancelSharedTripResponseMutableLiveData.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CancelSharedTripResponse> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    cancelSharedTripResponseMutableLiveData.postValue(null);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            cancelSharedTripResponseMutableLiveData.postValue(null);
        }

    }
}
