package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.PendingAcceptResponse;
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

public class PendingViewModel extends AndroidViewModel {
    private MutableLiveData<FollowersResponse> followersResponseMutableLiveData;
    private MutableLiveData<PendingAcceptResponse> pendingAcceptResponseMutableLiveData = new MutableLiveData<PendingAcceptResponse>();
    private static final String TAG = FollowersViewModel.class.getName();

    public PendingViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PendingAcceptResponse> getPendingActionResponse(int path, String token, String isFollowing) {
        pendingRequest(path, token, isFollowing);
        return pendingAcceptResponseMutableLiveData;
    }

    public LiveData<FollowersResponse> getPendingUser(String token, String isFollower, int limit, int offset) {
        if (followersResponseMutableLiveData == null) {
            followersResponseMutableLiveData = new MutableLiveData<FollowersResponse>();
            getPending(token, isFollower, limit, offset);
        }
        return followersResponseMutableLiveData;
    }

    private void getPending(String token, String isFollower, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowers(token, isFollower, limit, offset).enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(@NonNull Call<FollowersResponse> call, @NonNull Response<FollowersResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body()!=null && response.body().getList()!=null){
                        followersResponseMutableLiveData.postValue(response.body());
                    }else {
                        followersResponseMutableLiveData.postValue(null);
                    }

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

    private void pendingRequest(int path, String token, String isFollowing) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("is-following", isFollowing);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callPendingAction(path, token, requestBody).enqueue(new Callback<PendingAcceptResponse>() {
                @Override
                public void onResponse(@NonNull Call<PendingAcceptResponse> call, @NonNull Response<PendingAcceptResponse> response) {
                    if (response.isSuccessful()) {
                        pendingAcceptResponseMutableLiveData.postValue(response.body());
                    }else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String key;
                            JSONObject errorObj = jObjError.getJSONObject("error");
                            Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                            pendingAcceptResponseMutableLiveData.postValue(null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PendingAcceptResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplication().getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();
                    pendingAcceptResponseMutableLiveData.postValue(null);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
