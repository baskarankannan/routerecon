package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.TripFollowerResponse;
import com.techacsent.route_recon.model.request_body_model.AddMoreUserModel;
import com.techacsent.route_recon.model.AddMoreUserResponse;
import com.techacsent.route_recon.model.request_body_model.ShareTripModel;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.model.TripShareResponse;
import com.techacsent.route_recon.model.sendtrip.SendTripResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TripShareViewModel extends AndroidViewModel {
   /* private MutableLiveData<CountUserResponse> countUserResponseMutableLiveData;*/
    private MutableLiveData<FollowersResponse> followersResponseMutableLiveData;
    private MutableLiveData<TripShareResponse> tripShareResponseMutableLiveData;
    private MutableLiveData<SendTripResponse> sendTripResponseMutableLiveData;
    private MutableLiveData<GroupListResponse> groupListResponseMutableLiveData;
    private MutableLiveData<AddMoreUserResponse> addMoreUserResponseMutableLiveData;
    private MutableLiveData<TripFollowerResponse> tripFollowerResponseMutableLiveData;

    public TripShareViewModel(@NonNull Application application) {
        super(application);
    }

    /*public LiveData<CountUserResponse> callFollowerCount(String token, String amFollower, String isFollowing) {
        if (countUserResponseMutableLiveData == null) {
            countUserResponseMutableLiveData = new MutableLiveData<>();
            followerCount(token, amFollower, isFollowing);
        }
        return countUserResponseMutableLiveData;
    }*/

    public LiveData<FollowersResponse> callUserList(String token, String isFollower, int limit, int offset) {
        if (followersResponseMutableLiveData == null) {
            followersResponseMutableLiveData = new MutableLiveData<>();
            getFollowings(token, isFollower, limit, offset);
        }
        return followersResponseMutableLiveData;
    }

    public LiveData<TripFollowerResponse> callTripFollower(String token, String isFollower, int tripid, int limit, int offset) {
        if (tripFollowerResponseMutableLiveData == null) {
            tripFollowerResponseMutableLiveData = new MutableLiveData<>();
            //getFollowings(token, isFollower, limit, offset);
            getTripFollowings(token, isFollower, tripid, limit, offset);
        }
        return tripFollowerResponseMutableLiveData;
    }

    public LiveData<TripShareResponse> callShareTrip(String token, ShareTripModel shareTripModel) {
        /*if (tripShareResponseMutableLiveData == null) {

        }*/
        tripShareResponseMutableLiveData = new MutableLiveData<>();
        shareTrip(token, shareTripModel);
        return tripShareResponseMutableLiveData;
    }

    public LiveData<GroupListResponse> callGroupList(String token, int limit, int offset) {
        if (groupListResponseMutableLiveData == null) {
            groupListResponseMutableLiveData = new MutableLiveData<>();
            getGroupList(token, limit, offset);
        }
        return groupListResponseMutableLiveData;
    }

    public LiveData<AddMoreUserResponse> addMoreUser(String token, AddMoreUserModel addMoreUserModel) {
        /*if (addMoreUserResponseMutableLiveData == null) {

        }*/
        addMoreUserResponseMutableLiveData = new MutableLiveData<>();
        addMoreUserOnTrip(token, addMoreUserModel);
        return addMoreUserResponseMutableLiveData;
    }


    /*private void followerCount(String token, String amFollower, String isFollowing) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowerCount(token, amFollower, isFollowing).enqueue(new Callback<CountUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountUserResponse> call, @NonNull Response<CountUserResponse> response) {
                //Timber.d(new Gson().toJson(response));
                if (response.isSuccessful()) {
                    countUserResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountUserResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }*/

    private void getFollowings(String token, String isFollower, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowers(token, isFollower, limit, offset).enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(@NonNull Call<FollowersResponse> call, @NonNull Response<FollowersResponse> response) {
               // Timber.d(new Gson().toJson(response));
                if (response.isSuccessful()) {
                    if(response.body()!=null && response.body().getList()!=null && response.body().getList().size()>0){
                        followersResponseMutableLiveData.postValue(response.body());
                    } else {
                        followersResponseMutableLiveData.setValue(null);
                    }
                }else {
                    followersResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FollowersResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Timber.e(t);
                followersResponseMutableLiveData.setValue(null);

            }
        });
    }

    private void getTripFollowings(String token, String isFollower, int tripid, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callTripFollower(token, isFollower, tripid, limit, offset).enqueue(new Callback<TripFollowerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripFollowerResponse> call, @NonNull Response<TripFollowerResponse> response) {
                // Timber.d(new Gson().toJson(response));
                if (response.isSuccessful()) {
                    if(response.body()!=null && response.body().getList()!=null && response.body().getList().size()>0){
                        tripFollowerResponseMutableLiveData.postValue(response.body());
                    } else {
                        Toast.makeText(getApplication().getApplicationContext(), "You currently have no follower",Toast.LENGTH_SHORT).show();
                        tripFollowerResponseMutableLiveData.setValue(null);
                    }
                }else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        tripFollowerResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TripFollowerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Timber.e(t);
                tripFollowerResponseMutableLiveData.setValue(null);

            }
        });
    }

    private void shareTrip(String token, ShareTripModel shareTripModel) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callShareTrip(token, shareTripModel).enqueue(new Callback<TripShareResponse>() {
            @Override
            public void onResponse(@NonNull Call<TripShareResponse> call, @NonNull Response<TripShareResponse> response) {
                //Timber.d(new Gson().toJson(response));
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        tripShareResponseMutableLiveData.postValue(response.body());
                    }else {
                        tripShareResponseMutableLiveData.postValue(null);
                    }
                }else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        tripFollowerResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //tripShareResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TripShareResponse> call, @NonNull Throwable t) {
                tripShareResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });

    }

    private void getGroupList(String token, int limit, int offset) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callGroupList(token, limit, offset).enqueue(new Callback<GroupListResponse>() {
            @Override
            public void onResponse(@NonNull Call<GroupListResponse> call, @NonNull Response<GroupListResponse> response) {
                if (response.isSuccessful()) {
                    groupListResponseMutableLiveData.postValue(response.body());
                }else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        groupListResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });
    }

    public LiveData<SendTripResponse> callSendTrip(String token, ShareTripModel shareTripModel) {
        /*if (tripShareResponseMutableLiveData == null) {

        }*/
        sendTripResponseMutableLiveData = new MutableLiveData<>();
        sendTrip(token, shareTripModel);
        return sendTripResponseMutableLiveData;
    }


    private void sendTrip(String token, ShareTripModel shareTripModel) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        apiInterface.callSendTrip(token, shareTripModel).enqueue(new Callback<SendTripResponse>() {
            @Override
            public void onResponse(@NonNull Call<SendTripResponse> call, @NonNull Response<SendTripResponse> response) {
                //Timber.d(new Gson().toJson(response));
                if (response.isSuccessful()) {
                    if(response.body()!=null){
                        sendTripResponseMutableLiveData.postValue(response.body());
                    }else {
                        sendTripResponseMutableLiveData.postValue(null);
                    }
                }else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        tripFollowerResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //tripShareResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendTripResponse> call, @NonNull Throwable t) {
                sendTripResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });

    }

    private void addMoreUserOnTrip(String token, AddMoreUserModel addMoreUserModel) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callAddMoreUser(token, addMoreUserModel).enqueue(new Callback<AddMoreUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddMoreUserResponse> call, @NonNull Response<AddMoreUserResponse> response) {
                if (response.isSuccessful()) {
                    addMoreUserResponseMutableLiveData.setValue(response.body());
                }else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        addMoreUserResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddMoreUserResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });
    }
}
