package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.techacsent.route_recon.model.EditProfileResponse;
import com.techacsent.route_recon.model.UserDetailsResponse;
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

public class EditProfileViewModel extends AndroidViewModel {
    private MutableLiveData<EditProfileResponse> editProfileResponseMutableLiveData;
    private MutableLiveData<UserDetailsResponse> userDetailsResponseMutableLiveData;
    private static final String TAG = EditProfileViewModel.class.getSimpleName();

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<EditProfileResponse> getEditProfile(String auth, String email, String username, String fullname, String phone, String profileImg) {
        if(editProfileResponseMutableLiveData==null){
            editProfileResponseMutableLiveData = new MutableLiveData<>();
        }
        editProfile(auth, email, username, fullname, phone, profileImg);
        return editProfileResponseMutableLiveData;
    }

    public LiveData<UserDetailsResponse> getUserDetails(String auth, int userId) {
        userDetailsResponseMutableLiveData = new MutableLiveData<UserDetailsResponse>();
        userDetails(auth, userId);
        return userDetailsResponseMutableLiveData;
    }

    private void editProfile(String auth, String email, String username, String fullname, String phone, String profileImg) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("username", username);
            jsonObject.put("fullname", fullname);
            jsonObject.put("phone", phone);
            jsonObject.put("profileImg", profileImg);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callEditProfile(auth, requestBody).enqueue(new Callback<EditProfileResponse>() {
                @Override
                public void onResponse(@NonNull Call<EditProfileResponse> call, @NonNull Response<EditProfileResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getSuccess().getMessage().equals("Update successfully.")) {
                            editProfileResponseMutableLiveData.setValue(response.body());
                        } else {
                            Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                            editProfileResponseMutableLiveData.setValue(null);
                        }
                    } else {
                        editProfileResponseMutableLiveData.setValue(null);
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

                    }
                }

                @Override
                public void onFailure(@NonNull Call<EditProfileResponse> call, @NonNull Throwable t) {
                    editProfileResponseMutableLiveData.setValue(null);
                    Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void userDetails(String auth, int userId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callUserDetails(auth, userId).enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailsResponse> call, @NonNull Response<UserDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        userDetailsResponseMutableLiveData.postValue(response.body());
                    } else {
                        userDetailsResponseMutableLiveData.postValue(null);
                        Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    userDetailsResponseMutableLiveData.postValue(null);
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                userDetailsResponseMutableLiveData.postValue(null);
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
