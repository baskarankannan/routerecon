package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.ShortTripResponse;
import com.techacsent.route_recon.model.TripAcceptResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class TrackViewModel extends ViewModel {


    public MutableLiveData<ShortTripResponse> response = new MutableLiveData<>();




    public LiveData<ShortTripResponse> liveDataTripRequest(String token,String name,String time, String speed,
                                                           String distance, String start_lat, String start_long, String end_lat, String end_long
    ) {
        response = new MutableLiveData<>();
        acceptTripRequest( token,name, time, speed, distance, start_lat, start_long,end_lat,end_long);
        return response;
    }


    private void acceptTripRequest(String token,String name,String time, String speed, String distance,
                                   String start_lat, String start_long, String end_lat, String end_long) {
        ApiService apiService = new ApiCaller();
        apiService.addShortTrip(token,name,time, speed, distance,start_lat, start_long,end_lat,end_long,new ResponseCallback<ShortTripResponse>() {
            @Override
            public void onSuccess(ShortTripResponse data) {
                response.postValue(data);
            }

            @Override
            public void onError(Throwable th) {
                response.postValue(null);
            }
        });

    }
}
