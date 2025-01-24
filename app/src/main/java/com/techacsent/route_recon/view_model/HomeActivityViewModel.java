package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.BadgeCountResponse;
import com.techacsent.route_recon.model.SaveLocationResponse;
import com.techacsent.route_recon.model.SubscriptionResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.SaveLocationModel;
import com.techacsent.route_recon.model.request_body_model.SetBadgeModel;
import com.techacsent.route_recon.model.request_body_model.SubscriptionModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class HomeActivityViewModel extends AndroidViewModel {

    private MutableLiveData<SubscriptionResponse> subscriptionResponseMutableLiveData;

    private MutableLiveData<SuccessArray> resetResponseMutableLiveData;

    private MutableLiveData<BadgeCountResponse> badgeCountResponseMutableLiveData;

    private MutableLiveData<SaveLocationResponse> saveLocationResponseMutableLiveData;

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<SubscriptionResponse> callCheckStatus(String token, SubscriptionModel subscriptionModel) {
        if (subscriptionResponseMutableLiveData == null) {
            subscriptionResponseMutableLiveData = new MutableLiveData<>();
        }
        checkUserStatus(token, subscriptionModel);
        return subscriptionResponseMutableLiveData;
    }

    public LiveData<SuccessArray> callResetBadge(String token, SetBadgeModel setBadgeModel) {
        if (resetResponseMutableLiveData == null) {
            resetResponseMutableLiveData = new MutableLiveData<>();
        }
        resetBadge(token, setBadgeModel);
        return resetResponseMutableLiveData;
    }

    public LiveData<BadgeCountResponse> callGetBadgeCount(String token) {
        if (badgeCountResponseMutableLiveData == null) {
            badgeCountResponseMutableLiveData = new MutableLiveData<>();
        }
        getBadgeCount(token);
        return badgeCountResponseMutableLiveData;
    }

    public LiveData<SaveLocationResponse> callUpdateLocation(int locationShareId, String token, SaveLocationModel saveLocationModel) {
        if (saveLocationResponseMutableLiveData == null) {
            saveLocationResponseMutableLiveData = new MutableLiveData<>();
        }

        updateLocation(locationShareId, token, saveLocationModel);

        return saveLocationResponseMutableLiveData;
    }

    private void checkUserStatus(String token, SubscriptionModel subscriptionModel) {
        ApiService apiService = new ApiCaller();
        apiService.getSubscriptionStatus(token, subscriptionModel, new ResponseCallback<SubscriptionResponse>() {
            @Override
            public void onSuccess(SubscriptionResponse data) {
                subscriptionResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                subscriptionResponseMutableLiveData.setValue(null);
                makeToast(th.getMessage());
            }
        });
    }

    private void resetBadge(String token, SetBadgeModel setBadgeModel) {
        ApiService apiService = new ApiCaller();
        apiService.setBadgeCount(token, setBadgeModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                resetResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                resetResponseMutableLiveData.setValue(null);
            }
        });
    }

    private void getBadgeCount(String token) {
        ApiService apiService = new ApiCaller();
        apiService.getBadgeCount(token, new ResponseCallback<BadgeCountResponse>() {
            @Override
            public void onSuccess(BadgeCountResponse data) {
                badgeCountResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                badgeCountResponseMutableLiveData.setValue(null);
            }
        });
    }

    private void updateLocation(int locationShareId, String token, SaveLocationModel saveLocationModel) {
        ApiService apiService = new ApiCaller();
        apiService.saveCurrentLocation(locationShareId, token, saveLocationModel, new ResponseCallback<SaveLocationResponse>() {
            @Override
            public void onSuccess(SaveLocationResponse data) {
                saveLocationResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                saveLocationResponseMutableLiveData.setValue(null);
            }
        });

    }


    private void makeToast(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
