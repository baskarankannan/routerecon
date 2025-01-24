package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.SubscriptionResponse;
import com.techacsent.route_recon.model.request_body_model.SubscriptionModel;

public class HomeViewModel extends AndroidViewModel {
    public MutableLiveData<SubscriptionResponse> subscriptionResponseMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<SubscriptionResponse> getSubscriptionStatus(String token, SubscriptionModel subscriptionModel) {
        if (subscriptionResponseMutableLiveData == null) {
            subscriptionResponseMutableLiveData = new MutableLiveData<>();
            //getSubscriptionStatusFromApi(token, subscriptionModel);
        }
        return subscriptionResponseMutableLiveData;
    }


    /*private void getSubscriptionStatusFromApi(String token, SubscriptionModel subscriptionModel) {
        ApiService apiService = new ApiCaller();
        apiService.getSubscriptionStatus(token, subscriptionModel, new ResponseCallback<SubscriptionResponse>() {
            @Override
            public void onSuccess(Data data) {
                subscriptionResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                subscriptionResponseMutableLiveData.setValue(null);

            }
        });
    }*/
}
