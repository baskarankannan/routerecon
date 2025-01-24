package com.techacsent.route_recon.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.model.LocationsBeanForLocalDb;
import com.techacsent.route_recon.model.PaymentResponse;
import com.techacsent.route_recon.model.TripListResponse;
import com.techacsent.route_recon.model.UserSearchResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.model.request_body_model.PaymentModel;
import com.techacsent.route_recon.model.request_body_model.TripMarkerGetModel;
import com.techacsent.route_recon.model.TripMarkerResponse;
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
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class RouteReconIntentService extends IntentService {
    public static final String ACTION_GET_TRIP_MARKER = "ACTION_GET_TRIP_MARKER";
    public static final String ACTION_LOAD_USER_SEARCH = "ACTION_LOAD_USER_SEARCH";
    public static final String ACTION_SAVE_TRIP_DATA = "ACTION_SAVE_TRIP_DATA";
    public static final String ACTION_MAKE_PAYMENT = "ACTION_MAKE_PAYMENT";


    public static final String ACTION_GET_TRIP_MARKER_RESULT = "ACTION_GET_TRIP_MARKER_RESULT";
    public static final String ACTION_GET_USER_BY_NAME = "ACTION_GET_USER_BY_NAME";
    public static final String ACTION_GET_TRIP_DATA_BY_USER = "ACTION_GET_TRIP_DATA_BY_USER";
    public static final String ACTION_GET_PAYMENT_RESULT = "ACTION_GET_PAYMENT_RESULT";


    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);

    public RouteReconIntentService() {
        super("RouteReconIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_TRIP_MARKER.equals(action)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    ResultReceiver resultReceiver = intent.getParcelableExtra(ACTION_GET_TRIP_MARKER_RESULT);
                    TripMarkerGetModel tripMarkerGetModel = bundle.getParcelable(Constant.KEY_PARCEL_MARKER_OBJ);
                    getTripMarker(tripMarkerGetModel, resultReceiver);
                }

            } else if (ACTION_LOAD_USER_SEARCH.equals(action)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    ResultReceiver resultReceiver = intent.getParcelableExtra(ACTION_GET_USER_BY_NAME);
                    String username = bundle.getString(Constant.KEY_USER_NAME_SEARCH);
                    getAlluserByNAme(username, resultReceiver);
                }
            } else if (ACTION_SAVE_TRIP_DATA.equals(action)) {
                ResultReceiver resultReceiver = intent.getParcelableExtra(ACTION_GET_TRIP_DATA_BY_USER);
                loadAllTripAndSaveDB(resultReceiver);
            }else if(ACTION_MAKE_PAYMENT.equals(action)){
                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    ResultReceiver resultReceiver = intent.getParcelableExtra(ACTION_GET_PAYMENT_RESULT);
                    String cardNumber = bundle.getString("card_number");
                    String cvc = bundle.getString("cvc");
                    int productId = bundle.getInt("product_id");
                    String zip = bundle.getString("zip");
                    int year = bundle.getInt("year");
                    int month = bundle.getInt("month");
                    double balance = bundle.getDouble("balance");
                    setPayment(productId, cardNumber, cvc, zip, year, month, balance, resultReceiver);

                }
            }
        }

    }

    private void setPayment(int productId, String cardNumber, String cvc, String zip, int year, int month, double balance,ResultReceiver resultReceiver){
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setPackageId(productId);
        paymentModel.setPayableAmount(balance);
        paymentModel.setCardNumber(cardNumber);
        paymentModel.setExpirationMonth(month);
        paymentModel.setExpirationYear(year);

        paymentModel.setCvc(cvc);
        paymentModel.setBillingZip(zip);
        paymentModel.setDeviceId(Utils.getDeviceId());
        paymentModel.setRecipientEmail(PreferenceManager.getString(Constant.KEY_USER_EMAIL));

        ApiService apiService = new ApiCaller();
        apiService.callPayment(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), paymentModel, new ResponseCallback<PaymentResponse>() {
            @Override
            public void onSuccess(PaymentResponse data) {
                PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_STATUS, "notpayable");
                PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_DATE, data.getPackages().getCurrentDateTime());
                Toast.makeText(getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putBoolean("is_finished", true);
                resultReceiver.send(0,bundle);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putBoolean("is_finished", false);
                resultReceiver.send(0,bundle);
            }
        });
    }


    private void getTripMarker(TripMarkerGetModel tripMarkerGetModel, ResultReceiver resultReceiver) {
        ApiService apiService = new ApiCaller();
        apiService.getTripMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), tripMarkerGetModel, new ResponseCallback<TripMarkerResponse>() {
            @Override
            public void onSuccess(TripMarkerResponse data) {
                if (data != null && data.getList() != null) {
                    List<MarkerDescription> markerList =
                            AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().fetchMarkerByTripId(String.valueOf(0));

                    AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().deleteMarkerList(markerList);

                    //AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().nukeTable();
                    List<MarkerDescription> markerDescriptionList = new ArrayList<>();
                    for (TripMarkerResponse.ListBean listBean : data.getList()) {
                        MarkerDescription markerDescription = new MarkerDescription();
                        markerDescription.setMarkType(listBean.getMarkType());
                        markerDescription.setLat(listBean.getLat());
                        markerDescription.setLongX(listBean.getLongX());
                        markerDescription.setRadius(listBean.getRadius());
                        markerDescription.setAddress(listBean.getAddress());
                        markerDescription.setFullAddress(listBean.getFullAddress());
                        markerDescription.setTripId(0);
                        markerDescription.setMarkerId(listBean.getId());
                        markerDescriptionList.add(markerDescription);
                        if (listBean.getLocations() != null) {
                            List<LocationsBeanForLocalDb> locationsBeanList = new ArrayList<>();
                            for (TripMarkerResponse.ListBean.LocationsBean listBean1 : listBean.getLocations()) {

                                Log.e("PointerId", "ValueT "+listBean1.getId());
                                LocationsBeanForLocalDb locationsBean = new LocationsBeanForLocalDb();
                                locationsBean.setLat(listBean1.getLat());
                                locationsBean.setLongX(listBean1.getLongX());
                                locationsBean.setId(listBean1.getId());
                                locationsBean.setName(listBean1.getName());
                                locationsBeanList.add(locationsBean);

                            }
                            markerDescription.setHazardMarkerPointList(locationsBeanList);

                        }
                    }
                    AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().insertListOfMarker(markerDescriptionList);
                }
                PreferenceManager.updateValue(Constant.KEY_IS_MARKER_LOADED, true);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.KEY_IS_FINISHED, true);
                resultReceiver.send(0, bundle);
            }

            @Override
            public void onError(Throwable th) {
                Bundle bundle = new Bundle();
                Timber.e(th);
                bundle.putBoolean(Constant.KEY_IS_FINISHED, false);
                PreferenceManager.updateValue(Constant.KEY_IS_MARKER_LOAD, true);
                resultReceiver.send(0, bundle);
                Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAlluserByNAme(String username, ResultReceiver resultReceiver) {
        ApiService apiService = new ApiCaller();
        apiService.searchUserByName(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), username, 30, 0, new ResponseCallback<UserSearchResponse>() {
            @Override
            public void onSuccess(UserSearchResponse data) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.KEY_USER_LIST_BY_NAME, data);
                resultReceiver.send(0, bundle);
            }

            @Override
            public void onError(Throwable th) {
                Timber.e(th);
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadAllTripAndSaveDB(ResultReceiver resultReceiver) {
        ApiService apiService = new ApiCaller();
        apiService.getAllTripList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), 0, 50, 0, new ResponseCallback<TripListResponse>() {
            @Override
            public void onSuccess(TripListResponse data) {
                if (data != null) {
                    for (TripListResponse.ListBean listBean : data.getList()) {
                        try {
                            Date tripBeginTime;
                            tripBeginTime = simpleDateFormat.parse(listBean.getEndTime());
                            BasicTripDescription basicTripDescription = new BasicTripDescription();
                            basicTripDescription.setTripId(listBean.getId());
                            basicTripDescription.setTripName(listBean.getTripName());
                            basicTripDescription.setBeginTime(listBean.getBeginTime());
                            basicTripDescription.setEndTime(listBean.getEndTime());
                            basicTripDescription.setBeginDateinMills(tripBeginTime.getTime());
                            basicTripDescription.setStartPointLat(listBean.getStartPoint().getLat());
                            basicTripDescription.setStartPointLonX(listBean.getStartPoint().getLongX());
                            basicTripDescription.setStartPointAddress(listBean.getStartPoint().getAddress());
                            basicTripDescription.setStartPointFullAddress(listBean.getStartPoint().getFullAddress());
                            basicTripDescription.setEndPointLat(listBean.getEndPoint().getLat());
                            basicTripDescription.setEndPointLonX(listBean.getEndPoint().getLongX());
                            basicTripDescription.setEndPointAddress(listBean.getEndPoint().getAddress());
                            basicTripDescription.setEndPointFullAddress(listBean.getEndPoint().getFullAddress());
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
                        //EventBus.getDefault().post(true);
                        PreferenceManager.updateValue(Constant.KEY_IS_LOADED_TRIPS, true);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constant.KEY_USER_LIST_BY_NAME, data);
                        resultReceiver.send(0, bundle);


                        //output = new Data.Builder().putBoolean("is_successful", true).build();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_trip_text, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(true);
                    //output = new Data.Builder().putBoolean("is_successful", false).build();
                }

            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }


}
