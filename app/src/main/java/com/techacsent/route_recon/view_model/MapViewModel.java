package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class MapViewModel extends AndroidViewModel {

    private MutableLiveData<TripMarkerCreateResponse> markerCreateResponseMutableLiveData;

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TripMarkerCreateResponse> callCreateMarker(String token, CreateTripMarkerModelClass createTripMarkerModelClass) {
        if (markerCreateResponseMutableLiveData == null) {
            markerCreateResponseMutableLiveData = new MutableLiveData<>();
        }
        createMarker(token, createTripMarkerModelClass);
        return markerCreateResponseMutableLiveData;
    }


    private void createMarker(String token, CreateTripMarkerModelClass createTripMarkerModelClass) {
        ApiService apiService = new ApiCaller();
        apiService.createTripMarker(token, createTripMarkerModelClass, new ResponseCallback<TripMarkerCreateResponse>() {
            @Override
            public void onSuccess(TripMarkerCreateResponse data) {
                markerCreateResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                markerCreateResponseMutableLiveData.setValue(null);
                makeToast(th.getMessage());
            }
        });
    }

    private void makeToast(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
