package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.techacsent.route_recon.model.tracklisthistory.TrackListHistoryResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class TrackHistoryListViewModel extends ViewModel {


    public MutableLiveData<TrackListHistoryResponse> response = new MutableLiveData<>();




    public LiveData<TrackListHistoryResponse> getTrackTripHistory(String token,int limit, int offset   ) {
        response = new MutableLiveData<>();
        acceptTripRequest( token,limit, offset);
        return response;
    }


    private void acceptTripRequest(String token, int limit, int offset) {

        ApiService apiService = new ApiCaller();

        apiService.getTrackHistoryList(token,limit,offset,new ResponseCallback<TrackListHistoryResponse>() {
            @Override
            public void onSuccess(TrackListHistoryResponse data) {
                response.postValue(data);
            }

            @Override
            public void onError(Throwable th) {
                response.postValue(null);
            }
        });

    }
}
