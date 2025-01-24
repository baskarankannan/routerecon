package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.PickLocationActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.UpdatedCreateTripResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.service.LocationService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.worker.SaveTripWorker;
import com.techacsent.route_recon.worker.UpdateTripWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUpdateFragment extends Fragment {
    private EditText etTripName;
    private TextView tvStartingPoint;
    private TextView tvDestination;
    private TextView tvTripBeginsTime;
    private TextView tvTripEndsTime;

    private double startLatitude;
    private double startLongitude;
    private String startFullAddress;
    private String endFullAddress;
    private double endLatitude;
    private double endLongitude;
    private NavigationOptionInterface navigationOptionInterface;
    private LocationService mService;

    private boolean isUpdate;
    private BasicTripDescription basicTripDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUpdate = getArguments().getBoolean(Constant.KEY_IS_UPDATE);
            basicTripDescription = getArguments().getParcelable("parcel");
        }
        mService = new LocationService(RouteApplication.getInstance().getApplicationContext());
    }

    public CreateUpdateFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_update, container, false);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTripName = view.findViewById(R.id.et_trip_name);
        tvStartingPoint = view.findViewById(R.id.et_starting_point);
        tvDestination = view.findViewById(R.id.et_destination);
        tvTripBeginsTime = view.findViewById(R.id.et_trip_begins_time);
        tvTripEndsTime = view.findViewById(R.id.et_trip_ends_time);
        LinearLayout layoutStart = view.findViewById(R.id.layout_start_point);
        LinearLayout layoutEnd = view.findViewById(R.id.layout_end_point);
        LinearLayout layoutStartTime = view.findViewById(R.id.layout_begin_time);
        LinearLayout layoutEndTime = view.findViewById(R.id.layout_end_time);
        etTripName.requestFocus();
        etTripName.setSelectAllOnFocus(true);
        etTripName.selectAll();

        Button btnNext = view.findViewById(R.id.btn_next);
        if (isUpdate) {
            displayTripData();
        }else {
            tvStartingPoint.setText(Utils.getAddress(mService.getLatitude(), mService.getLongitude(), true, RouteApplication.getInstance().getApplicationContext()));
            startFullAddress = tvStartingPoint.getText().toString();
            startLatitude = mService.getLatitude();
            startLongitude = mService.getLongitude();
        }
        layoutStart.setOnClickListener(v -> {
            etTripName.clearFocus();
            Intent intent = new Intent(getActivity(), PickLocationActivity.class);
            startActivityForResult(intent, 1);
        });
        layoutEnd.setOnClickListener(v -> {
            etTripName.clearFocus();
            Intent intent = new Intent(getActivity(), PickLocationActivity.class);
            startActivityForResult(intent, 2);
        });
        layoutStartTime.setOnClickListener(v -> {
            hideKeyboardFrom(v);
            pickDate(tvTripBeginsTime);
        });
        layoutEndTime.setOnClickListener(v -> {
            hideKeyboardFrom(v);
            pickDate(tvTripEndsTime);
        });
        btnNext.setOnClickListener(v -> {
            hideKeyboardFrom(v);
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(tvTripBeginsTime.getText().toString().trim());
                endDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(tvTripEndsTime.getText().toString().trim());
            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }

            if (etTripName.getText().toString().trim().length() < 1) {
                Toast.makeText(getActivity(), Constant.MSG_TRIP_NAME_EMPTY, Toast.LENGTH_SHORT).show();
            } else if (tvStartingPoint.getText().toString().trim().length() < 1) {
                Toast.makeText(getActivity(), Constant.MSG_START_POINT_NAME_EMPTY, Toast.LENGTH_SHORT).show();

            } else if (tvDestination.getText().toString().trim().length() < 1) {
                Toast.makeText(getActivity(), Constant.MSG_DEST_POINT_NAME_EMPTY, Toast.LENGTH_SHORT).show();

            }
            else if (tvTripBeginsTime.getText().toString().trim().length() < 1) {
                Toast.makeText(getActivity(), Constant.MSG_TRIP_BEGAN_TIME_EMPTY, Toast.LENGTH_SHORT).show();

            } else if (tvTripEndsTime.getText().toString().trim().length() < 1) {
                Toast.makeText(getActivity(), Constant.MSG_TRIP_END_TIME_EMPTY, Toast.LENGTH_SHORT).show();
            }

            else {
                assert startDate != null;

                if (startDate.compareTo(endDate) > 0) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_start_end_time_warning), Toast.LENGTH_SHORT).show();
                } else {
                    if (!isUpdate) {
                        createTrip();
                    } else {
                        updateTrip();
                    }

                }
            }
        });
    }

    public void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void displayTripData() {
        etTripName.setText(basicTripDescription.getTripName());
        tvStartingPoint.setText(basicTripDescription.getStartPointAddress());
        tvDestination.setText(basicTripDescription.getEndPointAddress());
        tvTripBeginsTime.setText(basicTripDescription.getBeginTime());
        tvTripEndsTime.setText(basicTripDescription.getEndTime());
        startLatitude = basicTripDescription.getStartPointLat();
        startLongitude = basicTripDescription.getStartPointLonX();
        endLatitude = basicTripDescription.getEndPointLat();
        endLongitude = basicTripDescription.getEndPointLonX();
    }

    private void createTrip() {
        navigationOptionInterface.showProgressDialog(true);
        CreateTripModelClass createTripModelClass = getDataForCreateUpdateTrip(false);
        ApiService apiService = new ApiCaller();
        apiService.createBasicTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                createTripModelClass, new ResponseCallback<UpdatedCreateTripResponse>() {
                    @Override
                    public void onSuccess(UpdatedCreateTripResponse data) {
                        Gson gson = new Gson();
                        String tripResponse = gson.toJson(data);
                        Data inputData = new Data.Builder()
                                .putString(Constant.KEY_CREATE_TRIP_RESPONSE, tripResponse) //3882-8622
                                .build();
                        final OneTimeWorkRequest saveTripDta = new OneTimeWorkRequest.Builder(SaveTripWorker.class)
                                .setInputData(inputData)
                                .build();
                        WorkManager.getInstance().enqueue(saveTripDta);
                        WorkManager.getInstance().getWorkInfoByIdLiveData(saveTripDta.getId()).observe(Objects.requireNonNull(getActivity()), workInfo -> {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                                loadWaypointFragment(data);
                                navigationOptionInterface.showProgressDialog(false);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable th) {
                        navigationOptionInterface.showProgressDialog(false);
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updateTrip() {
        navigationOptionInterface.showProgressDialog(true);
        CreateTripModelClass createModelClass = getDataForCreateUpdateTrip(true);
        ApiService apiService = new ApiCaller();
        apiService.updateTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                createModelClass, new ResponseCallback<UpdatedCreateTripResponse>() {
                    @Override
                    public void onSuccess(UpdatedCreateTripResponse data) {
                        Gson gson = new Gson();
                        String tripResponse = gson.toJson(data);
                        Data inputData = new Data.Builder()
                                .putString(Constant.KEY_CREATE_TRIP_RESPONSE, tripResponse) //3882-8622
                                .build();
                        final OneTimeWorkRequest updateTripData = new OneTimeWorkRequest.Builder(UpdateTripWorker.class)
                                .setInputData(inputData)
                                .build();
                        WorkManager.getInstance().enqueue(updateTripData);
                        WorkManager.getInstance().getWorkInfoByIdLiveData(updateTripData.getId()).observe(Objects.requireNonNull(getActivity()), workInfo -> {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                                loadWaypointFragment(data);
                                navigationOptionInterface.showProgressDialog(false);
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        navigationOptionInterface.showProgressDialog(false);
                    }
                });

    }

    private void loadWaypointFragment(UpdatedCreateTripResponse updateTripesponse) {
        Fragment fragment = new WaypointFragment();
        Bundle bundle = new Bundle();
        if (isUpdate) {
            bundle.putBoolean(Constant.KEY_IS_UPDATE, true);
        } else {
            bundle.putBoolean(Constant.KEY_IS_UPDATE, false);
        }

        bundle.putParcelable(Constant.KEY_PARCEL_CREATE_TRIP_RESPONSE, updateTripesponse.getData());
        fragment.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    private CreateTripModelClass getDataForCreateUpdateTrip(boolean isUpdate) {
        CreateTripModelClass createModelClass = new CreateTripModelClass();
        createModelClass.setTimeZone(TimeZone.getDefault().getID());
        if (isUpdate) {
            createModelClass.setId(basicTripDescription.getTripId());
        }
        CreateTripModelClass.StartPointBean startpointBean = new CreateTripModelClass.StartPointBean();
        CreateTripModelClass.EndPointBean endpointBean = new CreateTripModelClass.EndPointBean();
        createModelClass.setTripName(etTripName.getText().toString().trim());
        createModelClass.setBeginTime(tvTripBeginsTime.getText().toString().trim());
        createModelClass.setEndTime(tvTripEndsTime.getText().toString().trim());
        startpointBean.setAddress(tvStartingPoint.getText().toString().trim());
        startpointBean.setFullAddress(startFullAddress);
        startpointBean.setLat(startLatitude);
        startpointBean.setLongX(startLongitude);
        startpointBean.setType(1);
        endpointBean.setLat(endLatitude);
        endpointBean.setLongX(endLongitude);
        endpointBean.setAddress(tvDestination.getText().toString().trim());
        endpointBean.setFullAddress(endFullAddress);
        endpointBean.setType(1);
        createModelClass.setTripId(String.valueOf(System.currentTimeMillis()));
        createModelClass.setStartPoint(startpointBean);
        createModelClass.setEndPoint(endpointBean);
        return createModelClass;
    }

    private void pickDate(TextView textView) {
        new SingleDateAndTimePickerDialog.Builder(getActivity())
                .title(getResources().getString(R.string.text_calender_title))
                .bottomSheet()
                .curved()
                .displayHours(true)
                .displayMinutes(true)
                .mainColor(getResources().getColor(R.color.colorPrimary))
                .mustBeOnFuture()
                .minutesStep(5)
                .displayAmPm(true)
                .listener(date -> {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT); //dd-MM-yyyy hh:mm aa
                    String format = spf.format(date);
                    textView.setText(format);
                }).display();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            assert bundle != null;
            if (bundle.getString("place") == null) {
                Toast.makeText(getActivity(), Constant.MSG_EMPTY_PLACE_SELECTED, Toast.LENGTH_SHORT).show();

            } else {
                String startAddress = bundle.getString("place");
                startFullAddress = bundle.getString("full_address");
                /*startLatitude = bundle.getDouble("latitude");
                startLongitude = bundle.getDouble("longitude");*/

                startLatitude = bundle.getDouble("longitude");//longitude
                startLongitude = bundle.getDouble("latitude");//latitude
                tvStartingPoint.setText(startAddress);
            }

        } else {
            getActivity();
            if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                if (bundle.getString("place") == null) {
                    Toast.makeText(getActivity(), Constant.MSG_EMPTY_PLACE_SELECTED, Toast.LENGTH_SHORT).show();

                } else {
                    String endAddress = bundle.getString("place");
                    endFullAddress = bundle.getString("full_address");
                    //String address = bundle.getString("place");
                    /*endLatitude = bundle.getDouble("latitude");
                    endLongitude = bundle.getDouble("longitude");*/
                    endLatitude = bundle.getDouble("longitude");
                    endLongitude = bundle.getDouble("latitude");
                    tvDestination.setText(endAddress);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mService = null;
    }
}
