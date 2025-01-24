package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.SharedUserResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

public class TrackerViewModel extends AndroidViewModel {

    public MutableLiveData<SharedUserResponse> sharedUserResponseMutableLiveData;

    private MutableLiveData<SuccessArray> mSuccessResponse;

    public TrackerViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<SharedUserResponse> callGetSharedUser(String token, String state, int limit, int offset) {
        if (sharedUserResponseMutableLiveData == null) {
            sharedUserResponseMutableLiveData = new MutableLiveData<>();
        }
        getSharedUserList(token, state, limit, offset);

        return sharedUserResponseMutableLiveData;
    }

    public LiveData<SuccessArray> getSuccessArray(SendSosModel sendSosModel){
        if (mSuccessResponse == null) {
            mSuccessResponse = new MutableLiveData<>();
        }
        sendSOS(sendSosModel);
        return mSuccessResponse;
    }

    private void getSharedUserList(String token, String state, int limit, int offset) {
        ApiService apiService = new ApiCaller();
        apiService.getSharedLocationUser(token, state, limit, offset, new ResponseCallback<SharedUserResponse>() {
            @Override
            public void onSuccess(SharedUserResponse data) {
                sharedUserResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                sharedUserResponseMutableLiveData.setValue(null);
            }
        });
    }

    private void sendSOS(SendSosModel sendSosModel) {
        ApiService apiService = new ApiCaller();
        apiService.sendSosEmail(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                sendSosModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                //sosMutableLiveData.setValue(data);
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                mSuccessResponse.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                mSuccessResponse.setValue(null);
                //sosMutableLiveData.setValue(null);
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
