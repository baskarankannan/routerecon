package com.techacsent.route_recon.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends AndroidViewModel {
    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<DirectionsRoute> directionsRouteMutableLiveData;

    public LiveData<DirectionsRoute> getFinalRoute(Point origin, Point destination, List<Point> wayPoints) {
        if (directionsRouteMutableLiveData == null) {
            directionsRouteMutableLiveData = new MutableLiveData<>();
        }
        getRoute(origin, destination, wayPoints);
        return directionsRouteMutableLiveData;
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
                //assert response.body() != null;
                if (response.body() != null) {
                    directionsRouteMutableLiveData.setValue(response.body().routes().get(0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
