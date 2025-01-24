package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.techacsent.route_recon.model.ChangePasswordResponse;
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

public class ChangePasswordViewModel extends AndroidViewModel {
    public MutableLiveData<ChangePasswordResponse> changePasswordResponseMutableLiveData;
    private static final String TAG=ChangePasswordViewModel.class.getName();

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ChangePasswordResponse> getChangePassword(String token, String oldPassword, String newPassword) {
        changePasswordResponseMutableLiveData = new MutableLiveData<ChangePasswordResponse>();
        changePassword(token,oldPassword,newPassword);
        return changePasswordResponseMutableLiveData;
    }

    private void changePassword(String token, String oldPassword, String newPassword) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("old_password", oldPassword);
            jsonObject.put("password", newPassword);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callChangePassword(token, requestBody).enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull Response<ChangePasswordResponse> response) {
                    //Log.e(TAG, new Gson().toJson(response));
                    if (response.isSuccessful()) {
                        if(response.body()!=null && response.body().getSuccess().getMessage().equals("password update successfully")){
                            changePasswordResponseMutableLiveData.postValue(response.body());
                        }else {
                            changePasswordResponseMutableLiveData.postValue(null);
                        }

                    }else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String key;
                            JSONObject errorObj = jObjError.getJSONObject("error");
                            //responseCallback.onError(new Exception(errorObj.get("message").toString()));
                            Toast.makeText(getApplication().getApplicationContext(), errorObj.get("message").toString(), Toast.LENGTH_SHORT).show();
                            changePasswordResponseMutableLiveData.postValue(null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ChangePasswordResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
