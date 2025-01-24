package com.techacsent.route_recon.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.techacsent.route_recon.model.MyTripsResponse;

public class SharedTripDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, MyTripsResponse.ListBean>> itemLiveDataSource = new MutableLiveData<>();
    @Override
    public DataSource<Integer, MyTripsResponse.ListBean> create() {
        //getting our data source object
        SharedTripDataSource sharedTripDataSource = new SharedTripDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(sharedTripDataSource);

        //returning the datasource
        return sharedTripDataSource;
    }
    public MutableLiveData<PageKeyedDataSource<Integer, MyTripsResponse.ListBean>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
