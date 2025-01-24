package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techacsent.route_recon.model.ReportListResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class ReportFragmentViewModel extends ViewModel {

    private MutableLiveData<ReportListResponse> reportListResponseMutableLiveData;

    public LiveData<ReportListResponse> getReportMarkList(String token, String status) {
        if (reportListResponseMutableLiveData == null) {
            reportListResponseMutableLiveData = new MutableLiveData<>();
            getReporMarkertListFromServer(token, status);
        }
        return reportListResponseMutableLiveData;
    }


    private void getReporMarkertListFromServer(String token, String status) {
        ApiService apiService = new ApiCaller();
        apiService.getReportMarkerType(token, status, new ResponseCallback<ReportListResponse>() {
            @Override
            public void onSuccess(ReportListResponse data) {
                reportListResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                reportListResponseMutableLiveData.setValue(null);
            }
        });
    }

}
