package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.CategoryCreateResponse;
import com.techacsent.route_recon.model.request_body_model.CreateCategoryModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class CreateCategoryViewModel extends AndroidViewModel {

    private MutableLiveData<CategoryCreateResponse> categoryCreateResponseMutableLiveData;

    public CreateCategoryViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<CategoryCreateResponse> callCreateCategory(String toke, CreateCategoryModel createCategoryModel) {
        if (categoryCreateResponseMutableLiveData == null) {
            categoryCreateResponseMutableLiveData = new MutableLiveData<>();
            createCategory(toke, createCategoryModel);
        }
        return categoryCreateResponseMutableLiveData;
    }


    private void createCategory(String toke, CreateCategoryModel createCategoryModel) {
        ApiService apiService = new ApiCaller();
        apiService.callCreateCategory(toke, createCategoryModel, new ResponseCallback<CategoryCreateResponse>() {
            @Override
            public void onSuccess(CategoryCreateResponse data) {
                categoryCreateResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                categoryCreateResponseMutableLiveData.setValue(null);

            }
        });
    }


}
