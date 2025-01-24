package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.paging.SharedTripDataSource;
import com.techacsent.route_recon.paging.SharedTripDataSourceFactory;

public class PagedSharedTripViewModel extends ViewModel {
    public LiveData<PagedList<MyTripsResponse.ListBean>> pagedListLiveData;
    public LiveData<PageKeyedDataSource<Integer,MyTripsResponse.ListBean>> sourceLiveData;

    public PagedSharedTripViewModel(){
        SharedTripDataSourceFactory itemDataSourceFactory = new SharedTripDataSourceFactory();
        sourceLiveData = itemDataSourceFactory.getItemLiveDataSource();
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(SharedTripDataSource.PAGE_SIZE).build();

        pagedListLiveData = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();


    }
}
