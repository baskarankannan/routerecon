package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.techacsent.route_recon.model.CreateGroupResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupViewModel extends AndroidViewModel {
    private MutableLiveData<FollowersResponse> followersResponseMutableLiveData;
    private MutableLiveData<CreateGroupResponse> createGroupResponseMutableLiveData;
    private MutableLiveData<GroupListResponse> groupListResponseMutableLiveData;
    private static final String TAG = CreateGroupViewModel.class.getSimpleName();

    public CreateGroupViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<FollowersResponse> callUserList(String token, String isFollower, int limit, int offset) {
        if (followersResponseMutableLiveData == null) {
            followersResponseMutableLiveData = new MutableLiveData<FollowersResponse>();
            getFollowings(token, isFollower, limit, offset);
        }
        return followersResponseMutableLiveData;
    }

    public LiveData<CreateGroupResponse> callCreateGroup(String token, String name, JSONArray jsonArray) {
        if (createGroupResponseMutableLiveData == null) {
            createGroupResponseMutableLiveData = new MutableLiveData<CreateGroupResponse>();
            //getFollowings(token, isFollower, limit, offset);
            createGroup(token, name, jsonArray);
        }
        return createGroupResponseMutableLiveData;
    }

    public LiveData<GroupListResponse> callGroupList(String token, int limit, int offset) {
        groupListResponseMutableLiveData = new MutableLiveData<GroupListResponse>();
        getGroupList(token, limit, offset);

        return groupListResponseMutableLiveData;
    }

    private void createGroup(String token, String name, JSONArray jsonArray) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("users", jsonArray);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callCreateGroup(token, requestBody).enqueue(new Callback<CreateGroupResponse>() {
                @Override
                public void onResponse(@NonNull Call<CreateGroupResponse> call, @NonNull Response<CreateGroupResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getSuccess().getMessage().equals("Create group successfully")) {
                            createGroupResponseMutableLiveData.postValue(response.body());
                        } else {
                            Toast.makeText(getApplication().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                            createGroupResponseMutableLiveData.postValue(null);
                        }
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
                        createGroupResponseMutableLiveData.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreateGroupResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    createGroupResponseMutableLiveData.postValue(null);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getFollowings(String token, String isFollower, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowers(token, isFollower, limit, offset).enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(@NonNull Call<FollowersResponse> call, @NonNull Response<FollowersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getList() != null && response.body().getList().size() > 0) {
                        followersResponseMutableLiveData.postValue(response.body());
                    } else {
                        Toast.makeText(getApplication().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                        followersResponseMutableLiveData.postValue(null);
                    }
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
                    followersResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FollowersResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                followersResponseMutableLiveData.postValue(null);

            }
        });
    }

    private void getGroupList(String token, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGroupList(token, limit, offset).enqueue(new Callback<GroupListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GroupListResponse> call, @NonNull Response<GroupListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getList() != null && response.body().getList().size() > 0) {
                        groupListResponseMutableLiveData.postValue(response.body());
                    } else {
                        groupListResponseMutableLiveData.postValue(null);
                    }
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
                    groupListResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                groupListResponseMutableLiveData.postValue(null);

            }
        });
    }
}
