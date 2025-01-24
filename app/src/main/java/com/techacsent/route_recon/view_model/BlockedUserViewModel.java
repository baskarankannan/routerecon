package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.BlockResponse;
import com.techacsent.route_recon.model.BlockedUserResponse;
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

public class BlockedUserViewModel extends AndroidViewModel {
    public MutableLiveData<BlockResponse> blockResponseMutableLiveData;
    public MutableLiveData<BlockedUserResponse> blockedUserResponseMutableLiveData;

    public BlockedUserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BlockedUserResponse> getBlockedList(String token, String isBlocked, int limit, int offset) {
        if (blockedUserResponseMutableLiveData == null) {
            blockedUserResponseMutableLiveData = new MutableLiveData<BlockedUserResponse>();
            getList(token, isBlocked, limit, offset);
        }
        return blockedUserResponseMutableLiveData;
    }

    public LiveData<BlockResponse> getUnblock(String token, String shouldBlock, String blockId) {
        if (blockResponseMutableLiveData == null) {
            blockResponseMutableLiveData = new MutableLiveData<BlockResponse>();
            blockUser(token, shouldBlock, blockId);
        }
        return blockResponseMutableLiveData;
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
                public void onResponse(@NonNull Call<BlockResponse> call, @NonNull Response<BlockResponse> response) {
                    if (response.isSuccessful()) {
                        blockResponseMutableLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BlockResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getList(String token, String isBlocked, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callBlockList(token, isBlocked, limit, offset).enqueue(new Callback<BlockedUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlockedUserResponse> call, @NonNull Response<BlockedUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null && response.body().getList() != null && response.body().getList().size() > 0) {
                        blockedUserResponseMutableLiveData.setValue(response.body());
                    } else {
                        blockedUserResponseMutableLiveData.setValue(null);
                    }
                    //blockedUserResponseMutableLiveData.setValue(response.body());
                } else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(), Toast.LENGTH_SHORT).show();
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    blockedUserResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BlockedUserResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                blockedUserResponseMutableLiveData.setValue(null);
            }
        });
    }

}
