package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersViewModel extends AndroidViewModel {
    public MutableLiveData<FollowersResponse> followersResponseMutableLiveData;
    private static final String TAG = FollowersViewModel.class.getName();

    public FollowersViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<FollowersResponse> getFollowerList(String token, String isFollower, int limit, int offset) {
        if (followersResponseMutableLiveData == null) {
            followersResponseMutableLiveData = new MutableLiveData<FollowersResponse>();
            getFollower(token, isFollower, limit, offset);
        }
        return followersResponseMutableLiveData;
    }

    private void getFollower(String token, String isFollower, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowers(token, isFollower, limit, offset).enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(@NonNull Call<FollowersResponse> call, @NonNull Response<FollowersResponse> response) {
                if (response.isSuccessful()) {
                    followersResponseMutableLiveData.postValue(response.body());
                }else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        followersResponseMutableLiveData.postValue(null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FollowersResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();
                followersResponseMutableLiveData.postValue(null);

            }
        });
    }

}
