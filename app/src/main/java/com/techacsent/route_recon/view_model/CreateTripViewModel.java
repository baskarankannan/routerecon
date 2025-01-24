package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.UpdatedCreateTripResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTripViewModel extends ViewModel {
    private MutableLiveData<UpdatedCreateTripResponse> createTripResponseMutableLiveData;
    private MutableLiveData<UpdatedCreateTripResponse> updateTripResponseMutableLiveData;

    public LiveData<UpdatedCreateTripResponse> getCreateTrip(String token, CreateTripModelClass dataBean) {
        if (createTripResponseMutableLiveData == null) {
            createTripResponseMutableLiveData = new MutableLiveData<>();
            createTrip(token, dataBean);
        }
        return createTripResponseMutableLiveData;
    }

    public LiveData<UpdatedCreateTripResponse> getUpdateTrip(String token, CreateTripModelClass dataBean) {
        if (updateTripResponseMutableLiveData == null) {
            updateTripResponseMutableLiveData = new MutableLiveData<>();
            updateTrip(token, dataBean);
        }
        return updateTripResponseMutableLiveData;
    }

    private void createTrip(String token, CreateTripModelClass dataBean) {
        ApiService apiService = new ApiCaller();
        apiService.createBasicTrip(token, dataBean, new ResponseCallback<UpdatedCreateTripResponse>() {
            @Override
            public void onSuccess(UpdatedCreateTripResponse data) {
                createTripResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                createTripResponseMutableLiveData.setValue(null);

            }
        });

    }

    private void updateTrip(String token, CreateTripModelClass dataBean) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUpdateTrip(token, dataBean).enqueue(new Callback<UpdatedCreateTripResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdatedCreateTripResponse> call, @NonNull Response<UpdatedCreateTripResponse> response) {
                if (response.isSuccessful()) {
                    updateTripResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdatedCreateTripResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
