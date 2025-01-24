package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.model.LocationShareResponse;
import com.techacsent.route_recon.model.ShareLocationModel;
import com.techacsent.route_recon.model.TripFollowerResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ShareLiveLocationViewModel extends AndroidViewModel {

    private MutableLiveData<TripFollowerResponse> tripFollowerResponseMutableLiveData;

    private MutableLiveData<GroupListResponse> groupListResponseMutableLiveData;

    private MutableLiveData<LocationShareResponse> locationShareResponseMutableLiveData;


    public ShareLiveLocationViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<TripFollowerResponse> callTripFollower(String token, String isFollower, int tripid, int limit, int offset) {
        if (tripFollowerResponseMutableLiveData == null) {
            tripFollowerResponseMutableLiveData = new MutableLiveData<>();
            //getFollowings(token, isFollower, limit, offset);
            getTripFollowings(token, isFollower, tripid, limit, offset);
        }
        return tripFollowerResponseMutableLiveData;
    }

    public LiveData<GroupListResponse> callGroupList(String token, int limit, int offset) {
        if (groupListResponseMutableLiveData == null) {
            groupListResponseMutableLiveData = new MutableLiveData<>();
            getGroupList(token, limit, offset);
        }
        return groupListResponseMutableLiveData;
    }

    public LiveData<LocationShareResponse> callShareLiveLocation(String token, ShareLocationModel shareLocationModel){
        if (locationShareResponseMutableLiveData == null) {
            locationShareResponseMutableLiveData = new MutableLiveData<>();
            shareLiveLocation(token,shareLocationModel);
        }

        return locationShareResponseMutableLiveData;
    }

    private void shareLiveLocation(String token,ShareLocationModel shareLocationModel ){
        ApiService apiService = new ApiCaller();
        apiService.shareLiveLocation(token, shareLocationModel, new ResponseCallback<LocationShareResponse>() {
            @Override
            public void onSuccess(LocationShareResponse data) {
                locationShareResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                locationShareResponseMutableLiveData.setValue(null);
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(),Toast.LENGTH_SHORT).show();
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
                        assert response.errorBody() != null;
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        tripFollowerResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException | JSONException e) {
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
                        assert response.errorBody() != null;
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(),Toast.LENGTH_SHORT).show();
                        groupListResponseMutableLiveData.setValue(null);
                        //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                    } catch (IOException | JSONException e) {
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
}
