package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.BlockResponse;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsViewModel extends AndroidViewModel {
    public MutableLiveData<BlockResponse> blockResponseMutableLiveData;
    public MutableLiveData<FollowUnfollowResponse> followUnfollowResponseMutableLiveData = new MutableLiveData<FollowUnfollowResponse>();

    public UserDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BlockResponse> getBlock(String token, String shouldBlock, String blockId) {
        if (blockResponseMutableLiveData == null) {
            blockResponseMutableLiveData = new MutableLiveData<BlockResponse>();
            //login(email,password);
            blockUser(token, shouldBlock, blockId);
        }
        return blockResponseMutableLiveData;
    }

    public LiveData<FollowUnfollowResponse> getFollowUnfollow(int path, String token, String amFollower) {
        followUnfollow(path, token, amFollower);
        return followUnfollowResponseMutableLiveData;
    }

    private void blockUser(String token, String shouldBlock, String blockId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("block", shouldBlock);
            jsonObject.put("block-id", blockId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callUserBlock(token, requestBody).enqueue(new Callback<BlockResponse>() {
                @Override
                public void onResponse(@NotNull Call<BlockResponse> call, @NotNull Response<BlockResponse> response) {
                    if (response.isSuccessful()) {
                        blockResponseMutableLiveData.postValue(response.body());
                        //blockResponseMutableLiveData.setValue(response.body());
                    } else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String key;
                            JSONObject errorObj = jObjError.getJSONObject("error");
                            //responseCallback.onError(new Exception(errorObj.get("message").toString()));
                            Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(), Toast.LENGTH_SHORT).show();
                            blockResponseMutableLiveData.postValue(null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(@NotNull Call<BlockResponse> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    blockResponseMutableLiveData.postValue(null);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void followUnfollow(int path, String token, String amFollower) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("am-follower", amFollower);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callFollowUnfollow(path, token, requestBody).enqueue(new Callback<FollowUnfollowResponse>() {
                @Override
                public void onResponse(@NotNull Call<FollowUnfollowResponse> call, @NotNull Response<FollowUnfollowResponse> response) {
                    if (response.isSuccessful()) {
                        followUnfollowResponseMutableLiveData.postValue(response.body());
                        //followUnfollowResponseMutableLiveData.setValue(response.body());
                    } else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String key;
                            JSONObject errorObj = jObjError.getJSONObject("error");
                            //responseCallback.onError(new Exception(errorObj.get("message").toString()));
                            Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(), Toast.LENGTH_SHORT).show();
                            followUnfollowResponseMutableLiveData.postValue(null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<FollowUnfollowResponse> call, @NotNull Throwable t) {
                    t.printStackTrace();
                    followUnfollowResponseMutableLiveData.postValue(null);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
