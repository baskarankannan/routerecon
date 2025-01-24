package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.PackageResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class PackageViewModel extends AndroidViewModel {

    private MutableLiveData<PackageResponse> packageResponseMutableLiveData;

    public PackageViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PackageResponse> callGetPackage(String token, String status) {
        if (packageResponseMutableLiveData == null) {
            packageResponseMutableLiveData = new MutableLiveData<>();
        }
        getPackage(token, status);
        return packageResponseMutableLiveData;
    }

    private void getPackage(String token, String status) {
        ApiService apiService = new ApiCaller();
        apiService.getPackage(token, status, new ResponseCallback<PackageResponse>() {
            @Override
            public void onSuccess(PackageResponse data) {
                packageResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                packageResponseMutableLiveData.setValue(null);
                makeText(th.getMessage());
            }
        });
    }

    private void makeText(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
