package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;


public class MyTripViewModel extends ViewModel {
    public MutableLiveData<MyTripsResponse> myTripsResponseMutableLiveData;

    public LiveData<MyTripsResponse> getMyTrip(String token, int limit, int offset) {
        if (myTripsResponseMutableLiveData == null) {
            myTripsResponseMutableLiveData = new MutableLiveData<>();
            getMyTripList(token, limit, offset);
        }
        return myTripsResponseMutableLiveData;
    }

    private void getMyTripList(String token, int limit, int offset) {
        ApiService apiService = new ApiCaller();
        apiService.getMyTrip(token, limit, offset, new ResponseCallback<MyTripsResponse>() {
            @Override
            public void onSuccess(MyTripsResponse data) {
                myTripsResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                myTripsResponseMutableLiveData.setValue(null);
            }
        });
    }

}
