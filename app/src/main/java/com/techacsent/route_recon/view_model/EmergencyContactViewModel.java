package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.techacsent.route_recon.model.CreateContactSuccessResponse;
import com.techacsent.route_recon.model.EditContactResponse;
import com.techacsent.route_recon.model.EmergencyContactListResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.EditContactModel;
import com.techacsent.route_recon.model.request_body_model.EmergenctContactModel;
import com.techacsent.route_recon.model.request_body_model.SendSosModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

public class EmergencyContactViewModel extends AndroidViewModel {
    private MutableLiveData<EmergencyContactListResponse> emergencyContactListResponseMutableLiveData;

    private MutableLiveData<CreateContactSuccessResponse> createContactSuccessResponseMutableLiveData;

    private MutableLiveData<SuccessArray> successArrayMutableLiveData;

    private MutableLiveData<SuccessArray> sosMutableLiveData;

    private MutableLiveData<EditContactResponse> editContactResponseMutableLiveData;

    public EmergencyContactViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<EmergencyContactListResponse> getEmergencyContactList(String token) {
        emergencyContactListResponseMutableLiveData = new MutableLiveData<>();
        getContactListFromServer(token);
        return emergencyContactListResponseMutableLiveData;
    }

    public LiveData<CreateContactSuccessResponse> createEmergencyContact(EmergenctContactModel emergenctContactModel) {
        createContactSuccessResponseMutableLiveData = new MutableLiveData<>();
        createContact(emergenctContactModel);
        return createContactSuccessResponseMutableLiveData;
    }

    public LiveData<SuccessArray> deleteContact(String token, int id) {
        successArrayMutableLiveData = new MutableLiveData<>();
        deleteContactFromServer(token, id);
        return successArrayMutableLiveData;
    }

    public LiveData<SuccessArray> sendSoS(SendSosModel sendSosModel) {
        sosMutableLiveData = new MutableLiveData<>();
        //deleteContactFromServer(token, id);
        sendSos(sendSosModel);
        return sosMutableLiveData;
    }

    public LiveData<EditContactResponse> updateEmergencyContact(EditContactModel editContactModel) {
        editContactResponseMutableLiveData = new MutableLiveData<>();
        updateContact(editContactModel);
        return editContactResponseMutableLiveData;
    }

    private void sendSos(SendSosModel sendSosModel) {
        ApiService apiService = new ApiCaller();
        apiService.sendSosEmail(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), sendSosModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                sosMutableLiveData.setValue(data);
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable th) {
                sosMutableLiveData.setValue(null);
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getContactListFromServer(String token) {
        ApiService apiService = new ApiCaller();
        apiService.getContactList(token, new ResponseCallback<EmergencyContactListResponse>() {
            @Override
            public void onSuccess(EmergencyContactListResponse data) {
                emergencyContactListResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                emergencyContactListResponseMutableLiveData.setValue(null);
            }
        });

    }

    private void createContact(EmergenctContactModel emergenctContactModel) {
        ApiService apiService = new ApiCaller();
        apiService.createEmergencyContact(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), emergenctContactModel, new ResponseCallback<CreateContactSuccessResponse>() {
            @Override
            public void onSuccess(CreateContactSuccessResponse data) {
                createContactSuccessResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                createContactSuccessResponseMutableLiveData.setValue(null);
                Toast.makeText(getApplication(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteContactFromServer(String token, int id) {
        ApiService apiService = new ApiCaller();
        apiService.deleteContect(token, id, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                successArrayMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                successArrayMutableLiveData.setValue(null);

            }
        });

    }

    private void updateContact(EditContactModel editContactModel) {
        ApiService apiService = new ApiCaller();
        apiService.editContact(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), editContactModel,
                new ResponseCallback<EditContactResponse>() {
                    @Override
                    public void onSuccess(EditContactResponse data) {
                        editContactResponseMutableLiveData.setValue(data);
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getApplication(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        editContactResponseMutableLiveData.setValue(null);

                    }
                });

    }

}
