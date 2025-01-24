package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.BeingSharedTripResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class BeingSharedViewModel extends AndroidViewModel {

    private MutableLiveData<BeingSharedTripResponse> attendingTripMutableLiveData;

    private MutableLiveData<BeingSharedTripResponse> pendingTripMutableLiveData;

    public BeingSharedViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BeingSharedTripResponse> getAttendingTrip(String token, int status, int limit, int offset) {
        if (attendingTripMutableLiveData == null) {
            attendingTripMutableLiveData = new MutableLiveData<>();
        }
        loadAttendingTrip(token, status, limit, offset);
        return attendingTripMutableLiveData;
    }

    public LiveData<BeingSharedTripResponse> getPendingTrip(String token, int status, int limit, int offset) {
        if (pendingTripMutableLiveData == null) {
            pendingTripMutableLiveData = new MutableLiveData<>();
        }
        loadAttendingTrip(token, status, limit, offset);
        return pendingTripMutableLiveData;
    }


    private void loadAttendingTrip(String token, int status, int limit, int offset) {
        ApiService apiService = new ApiCaller();
        apiService.getSharedTrip(token, status, limit, offset, new ResponseCallback<BeingSharedTripResponse>() {
            @Override
            public void onSuccess(BeingSharedTripResponse data) {
                if (status == 1) {
                    attendingTripMutableLiveData.setValue(data);
                } else {
                    pendingTripMutableLiveData.setValue(data);
                }
            }

            @Override
            public void onError(Throwable th) {
                if (status == 1) {
                    attendingTripMutableLiveData.setValue(null);
                } else {
                    pendingTripMutableLiveData.setValue(null);
                }
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
