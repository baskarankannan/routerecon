package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.LoginResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.UserTokenModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    private MutableLiveData<SuccessArray> successArrayMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<LoginResponse> getLogin(String email, String password) {
        loginResponseMutableLiveData = new MutableLiveData<>();
        login(email, password);
        return loginResponseMutableLiveData;
    }

    public LiveData<SuccessArray> updateServerToken(String token, UserTokenModel userTokenModel) {
        successArrayMutableLiveData = new MutableLiveData<>();
        updateFirebaseToken(token, userTokenModel);
        return successArrayMutableLiveData;
    }

    private void login(String email, String password) {
        ApiService apiService = new ApiCaller();
        apiService.login(email, password, new ResponseCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                loginResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                loginResponseMutableLiveData.setValue(null);
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateFirebaseToken(String token, UserTokenModel userTokenModel) {
        ApiService apiService = new ApiCaller();
        apiService.updateFirebaseToken(token, userTokenModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                successArrayMutableLiveData.postValue(data);
            }

            @Override
            public void onError(Throwable th) {
                successArrayMutableLiveData.postValue(null);
            }
        });

    }
}
