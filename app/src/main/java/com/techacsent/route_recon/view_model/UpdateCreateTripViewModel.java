package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.model.WaypointResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

public class UpdateCreateTripViewModel extends ViewModel {
    public MutableLiveData<TripMarkerCreateResponse> tripMarkerCreateResponseMutableLiveData;
    public MutableLiveData<WaypointResponse> waypointResponseMutableLiveData;

    public LiveData<TripMarkerCreateResponse> getCreateMarker(CreateTripMarkerModelClass createTripMarkerModelClass) {
        createMarker(createTripMarkerModelClass);
        return tripMarkerCreateResponseMutableLiveData;
    }

    public LiveData<WaypointResponse> getAddWaypoint(WaypointModel waypointModel) {
        createWaypoint(waypointModel);
        return waypointResponseMutableLiveData;
    }

    private void createMarker(CreateTripMarkerModelClass createTripMarkerModelClass) {
        ApiService apiService = new ApiCaller();
        apiService.createTripMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                createTripMarkerModelClass, new ResponseCallback<TripMarkerCreateResponse>() {
                    @Override
                    public void onSuccess(TripMarkerCreateResponse data) {
                        if (data != null) {
                            if (data.getSuccess().getMessage().equals("Create marks successfully")) {
                                tripMarkerCreateResponseMutableLiveData.setValue(data);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable th) {
                        th.printStackTrace();
                    }
                });

    }

    private void createWaypoint(WaypointModel waypointModel) {
        ApiService apiService = new ApiCaller();
        apiService.createWaypoint(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), waypointModel, new ResponseCallback<WaypointResponse>() {
            @Override
            public void onSuccess(WaypointResponse data) {
                waypointResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });

    }


}
