package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.techacsent.route_recon.model.MyTripDescriptionModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTripDescriptionViewModel extends AndroidViewModel {
    private MutableLiveData<MyTripDescriptionModel> myTripDescriptionModelMutableLiveData;
    private MutableLiveData<DirectionsRoute> directionsRouteMutableLiveData;
    private static final String TAG = MyTripDescriptionViewModel.class.getSimpleName();

    public MyTripDescriptionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MyTripDescriptionModel> getSharedTripDetails(String token, int tripSharingId) {
        if (myTripDescriptionModelMutableLiveData == null) {
            myTripDescriptionModelMutableLiveData = new MutableLiveData<>();
            sharedtripDetails(token, tripSharingId);
        }
        return myTripDescriptionModelMutableLiveData;
    }

    public LiveData<DirectionsRoute> getFinalRoute(Point origin, Point destination, List<Point> wayPoints) {
        if (directionsRouteMutableLiveData == null) {
            directionsRouteMutableLiveData = new MutableLiveData<>();
            getRoute(origin, destination, wayPoints);
        }
        return directionsRouteMutableLiveData;
    }

    private void sharedtripDetails(String token, int tripSharingId) {
        ApiService apiService = new ApiCaller();
        apiService.getSharedTripDetails(token, tripSharingId, new ResponseCallback<MyTripDescriptionModel>() {
            @Override
            public void onSuccess(MyTripDescriptionModel data) {
                myTripDescriptionModelMutableLiveData.postValue(data);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getApplication().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                myTripDescriptionModelMutableLiveData.postValue(null);

            }
        });
    }


    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        assert Mapbox.getAccessToken() != null;
        NavigationRoute.Builder builder = NavigationRoute.builder(getApplication())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .destination(destination);
        if (wayPoints != null && wayPoints.size() > 0) {
            for (Point point : wayPoints)
                builder.addWaypoint(point);
        }

        builder.build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                if(response.body()!=null && response.body().routes()!=null){
                    directionsRouteMutableLiveData.setValue(response.body().routes().get(0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable t) {

            }
        });
    }


}
