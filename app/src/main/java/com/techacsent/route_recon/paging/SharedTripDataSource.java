package com.techacsent.route_recon.paging;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
SharedTripDataSource extends PageKeyedDataSource<Integer, MyTripsResponse.ListBean> {
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MyTripsResponse.ListBean> callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callMyTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), PAGE_SIZE, FIRST_PAGE).enqueue(new Callback<MyTripsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MyTripsResponse> call, @NonNull Response<MyTripsResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        callback.onResult(response.body().getList(),null,FIRST_PAGE+1);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<MyTripsResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MyTripsResponse.ListBean> callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callMyTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),PAGE_SIZE, FIRST_PAGE).enqueue(new Callback<MyTripsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MyTripsResponse> call, @NonNull Response<MyTripsResponse> response) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body().getList(), adjacentKey);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyTripsResponse> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MyTripsResponse.ListBean> callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callMyTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), PAGE_SIZE, FIRST_PAGE).enqueue(new Callback<MyTripsResponse>() {
            @Override
            public void onResponse(Call<MyTripsResponse> call, Response<MyTripsResponse> response) {
                Integer adjacentKey = (params.key > 1) ? params.key + 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body().getList(), adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<MyTripsResponse> call, Throwable t) {

            }
        });

    }
}
