package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.TrafficData;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<MapData> selectedData = new MutableLiveData<>();

    public MutableLiveData<TrafficData> trafficDataMutableLiveData = new MutableLiveData<>();


    public void select(MapData mapData){
        selectedData.setValue(mapData);
    }

    public LiveData<MapData> getSelected(){
        return selectedData;
    }


    public void setTraffic(TrafficData data){
        trafficDataMutableLiveData.setValue(data);
    }

    public LiveData<TrafficData> getTrafficSelected(){
        return trafficDataMutableLiveData;
    }
}
