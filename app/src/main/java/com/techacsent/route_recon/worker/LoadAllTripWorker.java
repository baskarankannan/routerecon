package com.techacsent.route_recon.worker;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.TripListResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class LoadAllTripWorker extends Worker {
    //private Data output;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
    List<LocationsBeanForLocalDb> locationsBeanList = new ArrayList<>();

    public LoadAllTripWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        loadAllTripAndSaveDB();
        return Result.success();
    }

    private void loadAllTripAndSaveDB() {
        ApiService apiService = new ApiCaller();
        apiService.getAllTripList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), 0, 30, 0, new ResponseCallback<TripListResponse>() {
            @Override
            public void onSuccess(TripListResponse data) {
                for (TripListResponse.ListBean listBean : data.getList()) {
                    try {
                        Date tripBeginTime = null;
                        if(listBean.getEndTime() != null) {
                            tripBeginTime = simpleDateFormat.parse(listBean.getEndTime());
                        }
                        BasicTripDescription basicTripDescription = new BasicTripDescription();
                        basicTripDescription.setTripId(listBean.getId());
                        basicTripDescription.setTripName(listBean.getTripName());
                        basicTripDescription.setBeginTime(listBean.getBeginTime());
                        basicTripDescription.setEndTime(listBean.getEndTime());
                        if(tripBeginTime == null){
                            basicTripDescription.setBeginDateinMills(0);

                        }else{
                            basicTripDescription.setBeginDateinMills(tripBeginTime.getTime());

                        }
                        basicTripDescription.setStartPointLat(listBean.getStartPoint().getLat());
                        basicTripDescription.setStartPointLonX(listBean.getStartPoint().getLongX());
                        basicTripDescription.setStartPointAddress(listBean.getStartPoint().getAddress());
                        basicTripDescription.setStartPointFullAddress(listBean.getStartPoint().getFullAddress());
                        basicTripDescription.setEndPointLat(listBean.getEndPoint().getLat());
                        basicTripDescription.setEndPointLonX(listBean.getEndPoint().getLongX());
                        basicTripDescription.setEndPointAddress(listBean.getEndPoint().getAddress());
                        basicTripDescription.setEndPointFullAddress(listBean.getEndPoint().getFullAddress());
                        basicTripDescription.setFriendShared(Integer.parseInt(listBean.getFriendAttend()));
                        //basicTripDescription.setTripJson(listBean.getTripJson());
                        AppDatabase.getAppDatabase(getApplicationContext()).daoTripBasic().insertTripBasic(basicTripDescription);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (listBean.getMarkers() != null && listBean.getMarkers().size() > 0) {
                        for (TripListResponse.ListBean.MarkersBean markersBean : listBean.getMarkers()) {
                            MarkerDescription markerDescription = new MarkerDescription();
                            markerDescription.setMarkType(markersBean.getMarkType());
                            markerDescription.setLat(markersBean.getLat());
                            markerDescription.setLongX(markersBean.getLongX());
                            markerDescription.setRadius(markersBean.getRadius());
                            markerDescription.setAddress(markersBean.getAddress());
                            markerDescription.setFullAddress(markersBean.getFullAddress());
                            markerDescription.setTripId(Integer.parseInt(listBean.getId()));
                            markerDescription.setMarkerId(markersBean.getId());
                            if (markersBean.getLocations() != null) {
                               locationsBeanList.clear();
                                for (TripListResponse.ListBean.MarkersBean.LocationsBean listBean1 : markersBean.getLocations()) {
                                    LocationsBeanForLocalDb locationsBean = new LocationsBeanForLocalDb();
                                    locationsBean.setLat(listBean1.getLat());
                                    locationsBean.setLongX(listBean1.getLongX());
                                    locationsBeanList.add(locationsBean);

                                }
                                markerDescription.setHazardMarkerPointList(locationsBeanList);

                            }
                            AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().insertMarker(markerDescription);
                        }
                    }
                    WaypointDescription waypointDescription = new WaypointDescription();
                    waypointDescription.setTripJson(listBean.getTripJson());
                    waypointDescription.setTripId(Integer.parseInt(listBean.getId()));
                    if (listBean.getWayPoints() != null && listBean.getWayPoints().size() > 0) {
                        List<WaypointModel.WayPointsBean> wayPointsListBeans = new ArrayList<>();
                        for (TripListResponse.ListBean.WayPointsBean wayPointsBean : listBean.getWayPoints()) {
                            WaypointModel.WayPointsBean waypoint = new WaypointModel.WayPointsBean();
                            waypoint.setLat(wayPointsBean.getLat());
                            waypoint.setLongX(wayPointsBean.getLongX());
                            waypoint.setType(wayPointsBean.getType());
                            waypoint.setAddress(wayPointsBean.getAddress());
                            waypoint.setFullAddress(wayPointsBean.getFullAddress());
                            wayPointsListBeans.add(waypoint);
                        }
                        waypointDescription.setWayPointsBeanList(wayPointsListBeans);
                    }
                    AppDatabase.getAppDatabase(getApplicationContext()).daoWaypoint().insertWaypoint(waypointDescription);

                    EventBus.getDefault().post(true);

                    //output = new Data.Builder().putBoolean("is_successful", true).build();
                }


            }

            @Override
            public void onError(Throwable th) {
                EventBus.getDefault().post(true);
            }
        });
    }

    private void loadTripMarker(){

    }
}
