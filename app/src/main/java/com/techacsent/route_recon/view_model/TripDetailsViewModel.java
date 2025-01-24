package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.TripDetailsResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailsViewModel extends ViewModel {
    public MutableLiveData<TripDetailsResponse> tripDetailsResponseMutableLiveData;

    public LiveData<TripDetailsResponse> getTripDetails(String token, String tripId) {
        if (tripDetailsResponseMutableLiveData == null) {
            tripDetailsResponseMutableLiveData = new MutableLiveData<TripDetailsResponse>();
            loadTripDetails(token, tripId);
        }
        return tripDetailsResponseMutableLiveData;
    }

    private void loadTripDetails(String token, String tripId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", tripId);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callTripDetails(token, requestBody).enqueue(new Callback<TripDetailsResponse>() {
                @Override
                public void onResponse(Call<TripDetailsResponse> call, Response<TripDetailsResponse> response) {
                    tripDetailsResponseMutableLiveData.setValue(response.body());
                }

                @Override
                public void onFailure(Call<TripDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
