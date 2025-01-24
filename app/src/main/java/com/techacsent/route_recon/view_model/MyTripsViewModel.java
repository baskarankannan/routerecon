package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class MyTripsViewModel extends AndroidViewModel {

    private MutableLiveData<MyTripsResponse> myTripsResponseMutableLiveData;

    public MyTripsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MyTripsResponse> getMyTripFromServer(String token, int limit, int offset){
        if(myTripsResponseMutableLiveData == null){
            myTripsResponseMutableLiveData = new MutableLiveData<>();
        }
        getMyTrip(token, limit, offset);
        return myTripsResponseMutableLiveData;
    }

    private void getMyTrip(String token, int limit, int offset) {
        ApiService apiService = new ApiCaller();
        apiService.getMyTrip(token, limit, offset, new ResponseCallback<MyTripsResponse>() {
            @Override
            public void onSuccess(MyTripsResponse data) {
                myTripsResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                myTripsResponseMutableLiveData.setValue(null);
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
