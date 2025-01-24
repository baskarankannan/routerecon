package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.TripAcceptResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class SharedMapViewModel extends ViewModel {
    public MutableLiveData<MapData> selectedData = new MutableLiveData<>();

    public MutableLiveData<TripAcceptResponse> response = new MutableLiveData<>();


    public void select(MapData mapData){
        selectedData.setValue(mapData);
    }

    public LiveData<MapData> getSelected(){
        return selectedData;
    }

    public LiveData<TripAcceptResponse> liveDataTripRequest(String token,String trip_id) {
        response = new MutableLiveData<>();
        acceptTripRequest(token, trip_id);
        return response;
    }


    private void acceptTripRequest(String token, String trip_id) {
        ApiService apiService = new ApiCaller();
        apiService.sendTripAcceptRequest(token,trip_id,new ResponseCallback<TripAcceptResponse>() {
            @Override
            public void onSuccess(TripAcceptResponse data) {
                response.postValue(data);
            }

            @Override
            public void onError(Throwable th) {
                response.postValue(null);
            }
        });

    }
}
