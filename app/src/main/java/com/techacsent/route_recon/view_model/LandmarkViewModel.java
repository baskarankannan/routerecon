package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import android.util.Log;
import android.widget.Toast;

import com.techacsent.route_recon.model.LandmarkCreateResponseV2;
import com.techacsent.route_recon.model.deleteHazardImage.DeleteHazardImageResponse;
import com.techacsent.route_recon.model.deletehazardpin.DeleteHazardPinResponse;
import com.techacsent.route_recon.model.edithazrdpin.EditHazardPinModel;
import com.techacsent.route_recon.model.edithazrdpin.EditHazardPinResponse;
import com.techacsent.route_recon.model.LandmarkCreateResponse;
import com.techacsent.route_recon.model.MarkerDetailResponse;
import com.techacsent.route_recon.model.polygonimageuploadresponse.PolygonImageUploadRespone;
import com.techacsent.route_recon.model.PolygonPinDetailsResponse;
import com.techacsent.route_recon.model.PolygonPinIImageUploadModel;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.CreateLandmarkModel;
import com.techacsent.route_recon.model.request_body_model.EditMarkerModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

public class LandmarkViewModel extends AndroidViewModel {

    private MutableLiveData<MarkerDetailResponse> markerDetailResponseMutableLiveData;
    private MutableLiveData<PolygonPinDetailsResponse> pinDetailResponseMutableLiveData;

    private MutableLiveData<SuccessArray> deleteResponseMutableLiveData;
    private MutableLiveData<SuccessArray> editResponseMutableLiveData;
    private MutableLiveData<LandmarkCreateResponseV2> landmarkCreateResponseMutableLiveData;
    private MutableLiveData<PolygonImageUploadRespone> polygonImageUploadResponeMutableLiveData;
    private MutableLiveData<SuccessArray> deleteImageResponseMutableLiveData;
    private MutableLiveData<DeleteHazardImageResponse> deleteHazardImageResponseMutableLiveData;
    private MutableLiveData<EditHazardPinResponse> editHazardPinResponseMutableLiveData;
    private MutableLiveData<DeleteHazardPinResponse> deleteHazardPinResponseMutableLiveData;

    public LandmarkViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MarkerDetailResponse> getMarkerDescription(String token, String markerID) {
        if (markerDetailResponseMutableLiveData == null) {
            markerDetailResponseMutableLiveData = new MutableLiveData<>();
        }

        getMarkerDetails(token, markerID);
        return markerDetailResponseMutableLiveData;
    }

    public LiveData<SuccessArray> getDeleteMarker(String token, String markerId) {
        if (deleteResponseMutableLiveData == null) {
            deleteResponseMutableLiveData = new MutableLiveData<>();
        }
        deleteMarker(token, markerId);
        return deleteResponseMutableLiveData;
    }

    public LiveData<SuccessArray> getEditMarker(String token, EditMarkerModel editMarkerModel) {
        if (editResponseMutableLiveData == null) {
            editResponseMutableLiveData = new MutableLiveData<>();
        }
        editMarker(token, editMarkerModel);
        return editResponseMutableLiveData;
    }

    public LiveData<LandmarkCreateResponseV2> callUploadLandmarkImage(String token, CreateLandmarkModel createLandmarkModel) {
        if (landmarkCreateResponseMutableLiveData == null) {
            landmarkCreateResponseMutableLiveData = new MutableLiveData<>();
        }
        uploadLandmarkImage(token, createLandmarkModel);
        return landmarkCreateResponseMutableLiveData;
    }

    public LiveData<SuccessArray> callDeleteImage(String token, int id) {
        if (deleteImageResponseMutableLiveData == null) {
            deleteImageResponseMutableLiveData = new MutableLiveData<>();
        }
        deleteLandmarkImage(token, id);
        return deleteImageResponseMutableLiveData;
    }


    private void getMarkerDetails(String token, String markerID) {
        ApiService apiService = new ApiCaller();
        apiService.getMarkerDetails(token, markerID, new ResponseCallback<MarkerDetailResponse>() {
            @Override
            public void onSuccess(MarkerDetailResponse data) {
                markerDetailResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                markerDetailResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    private void deleteMarker(String token, String markerId) {
        ApiService apiService = new ApiCaller();
        apiService.deleteTripMarker(token, markerId, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                deleteResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                deleteResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    private void editMarker(String token, EditMarkerModel editMarkerModel) {
        ApiService apiService = new ApiCaller();
        apiService.editTripMarker(token, editMarkerModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                editResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                editResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    private void uploadLandmarkImage(String token, CreateLandmarkModel createLandmarkModel) {
        ApiService apiService = new ApiCaller();
        apiService.createLandmarkImage(token, createLandmarkModel, new ResponseCallback<LandmarkCreateResponseV2>() {
            @Override
            public void onSuccess(LandmarkCreateResponseV2 data) {
                landmarkCreateResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                landmarkCreateResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    private void deleteLandmarkImage(String token, int id) {
        ApiService apiService = new ApiCaller();
        apiService.deleteLandmarkImage(token, id, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                deleteImageResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                deleteImageResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });

    }


    public LiveData<PolygonPinDetailsResponse> getPolygonPinDetails(String token, String pinId) {
        if (pinDetailResponseMutableLiveData == null) {
            pinDetailResponseMutableLiveData = new MutableLiveData<>();
        }

        getPinDetails(token, pinId);
        return pinDetailResponseMutableLiveData;
    }

    private void getPinDetails(String token, String pinId) {
        ApiService apiService = new ApiCaller();
        apiService.getPinDetails(token, pinId, new ResponseCallback<PolygonPinDetailsResponse>() {
            @Override
            public void onSuccess(PolygonPinDetailsResponse data) {
                pinDetailResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                pinDetailResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    public LiveData<PolygonImageUploadRespone> callUploadPolygonPinImage(String token, PolygonPinIImageUploadModel polygonPinIImageUploadModel) {

        if (polygonImageUploadResponeMutableLiveData == null) {
            polygonImageUploadResponeMutableLiveData = new MutableLiveData<>();
        }
        uploadPolygonPinImage(token, polygonPinIImageUploadModel);
        Log.e("data","tt " +polygonImageUploadResponeMutableLiveData.toString() );
        return polygonImageUploadResponeMutableLiveData;
    }

    private void uploadPolygonPinImage(String token, PolygonPinIImageUploadModel polygonPinIImageUploadModel) {
        ApiService apiService = new ApiCaller();
        apiService.createPolygonPinImage(token, polygonPinIImageUploadModel, new ResponseCallback<PolygonImageUploadRespone>() {
            @Override
            public void onSuccess(PolygonImageUploadRespone data) {
                polygonImageUploadResponeMutableLiveData.setValue(data);

                Log.e("data","ttt " +polygonImageUploadResponeMutableLiveData.toString() );


            }

            @Override
            public void onError(Throwable th) {
                polygonImageUploadResponeMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    public LiveData<EditHazardPinResponse> editHazardPinSubmit(String token, EditHazardPinModel editHazardPinModel) {
        if (editHazardPinResponseMutableLiveData == null) {
            editHazardPinResponseMutableLiveData = new MutableLiveData<>();
        }
        editHazardPin(token, editHazardPinModel);
        return editHazardPinResponseMutableLiveData;
    }


    private void editHazardPin(String token, EditHazardPinModel editMarkerModel) {
        ApiService apiService = new ApiCaller();
        apiService.editHazardPinMarker(token, editMarkerModel, new ResponseCallback<EditHazardPinResponse>() {
            @Override
            public void onSuccess(EditHazardPinResponse data) {
                editHazardPinResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                editHazardPinResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    public LiveData<DeleteHazardPinResponse> deleteHazardPin(String token, int pointer_id) {
        if (deleteHazardPinResponseMutableLiveData == null) {
            deleteHazardPinResponseMutableLiveData = new MutableLiveData<>();
        }
        deleteHazardPinCall(token, pointer_id);
        return deleteHazardPinResponseMutableLiveData;
    }

    private void deleteHazardPinCall(String token, int pointer_id) {
        ApiService apiService = new ApiCaller();
        apiService.deleteHazardPin(token, pointer_id, new ResponseCallback<DeleteHazardPinResponse>() {
            @Override
            public void onSuccess(DeleteHazardPinResponse data) {
                deleteHazardPinResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                deleteHazardPinResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });
    }

    public LiveData<DeleteHazardImageResponse> callDeleteHazardPinImage(String token, int id) {
        if (deleteHazardImageResponseMutableLiveData == null) {
            deleteHazardImageResponseMutableLiveData = new MutableLiveData<>();
        }
        deleteHazardPinImage(token, id);
        return deleteHazardImageResponseMutableLiveData;
    }

    private void deleteHazardPinImage(String token, int id) {
        ApiService apiService = new ApiCaller();
        apiService.deleteHazardPinImage(token, id, new ResponseCallback<DeleteHazardImageResponse>() {
            @Override
            public void onSuccess(DeleteHazardImageResponse data) {
                deleteHazardImageResponseMutableLiveData.setValue(data);
            }

            @Override
            public void onError(Throwable th) {
                deleteHazardImageResponseMutableLiveData.setValue(null);
                showToast(th.getMessage());
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(getApplication().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
