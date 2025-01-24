package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends AndroidViewModel {
    private MutableLiveData<FollowersResponse> followersResponseMutableLiveData;
    private MutableLiveData<FollowUnfollowResponse> followUnfollowResponseMutableLiveData = new MutableLiveData<FollowUnfollowResponse>();
    private static final String TAG = FollowersViewModel.class.getName();

    public FollowingViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<FollowersResponse> getFollowinfList(String token, String isFollower, int limit, int offset) {
        if (followersResponseMutableLiveData == null) {
            followersResponseMutableLiveData = new MutableLiveData<FollowersResponse>();
            getFollowings(token, isFollower, limit, offset);
        }
        return followersResponseMutableLiveData;
    }

    public LiveData<FollowUnfollowResponse> getUnfollow(int path, String token, String amFollower) {
        unfollowUser(path, token, amFollower);
        return followUnfollowResponseMutableLiveData;
    }


    private void getFollowings(String token, String isFollower, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowings(token, isFollower, limit, offset).enqueue(new Callback<FollowersResponse>() {
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

    private void unfollowUser(int path, String token, String amFollower) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("am-follower", amFollower);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callFollowUnfollow(path, token, requestBody).enqueue(new Callback<FollowUnfollowResponse>() {
                @Override
                public void onResponse(@NonNull Call<FollowUnfollowResponse> call, @NonNull Response<FollowUnfollowResponse> response) {
                    if (response.isSuccessful()) {
                        followUnfollowResponseMutableLiveData.postValue(response.body());
                        //followUnfollowResponseMutableLiveData.setValue(response.body());
                    }else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String key;
                            JSONObject errorObj = jObjError.getJSONObject("error");
                            Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                            followUnfollowResponseMutableLiveData.postValue(null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FollowUnfollowResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    followUnfollowResponseMutableLiveData.postValue(null);
                    Toast.makeText(getApplication().getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


