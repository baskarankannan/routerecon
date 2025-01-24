package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.UserSearchResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchViewModel extends ViewModel {
    private MutableLiveData<UserSearchResponse> userSearchResponseMutableLiveData;

    public LiveData<UserSearchResponse> getUserSearch(String token, String username, int limit, int offset) {
        if (userSearchResponseMutableLiveData == null) {
            userSearchResponseMutableLiveData = new MutableLiveData<>();
            loadUserSearch(token, username, limit, offset);
        }
        return userSearchResponseMutableLiveData;
    }

    private void loadUserSearch(String token, String username, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUserSearch(token, username, limit, offset).enqueue(new Callback<UserSearchResponse>() {
            @Override
            public void onResponse(Call<UserSearchResponse> call, Response<UserSearchResponse> response) {
                if (response.isSuccessful()) {
                    userSearchResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserSearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
