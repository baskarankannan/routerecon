package com.techacsent.route_recon.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.CreateTripActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.RemoveListObject;
import com.techacsent.route_recon.event_bus_object.SyncInternetObject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.TripListResponse;
import com.techacsent.route_recon.model.request_body_model.CreateTripMarkerModelClass;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.worker.LoadAllTripWorker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class TripsFragment extends Fragment implements View.OnClickListener {

    private ImageView ivAddUpdate;
    private ImageView ivRefresh;

    private Button btnNexttrip;
    private Button btnLastTrip;
    private ImageButton mapBtn;
    private Fragment fragment;
    private FragmentActivityCommunication fragmentActivityCommunication;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
    List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();

    public TripsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.hideBottomNav(false);
        fragmentActivityCommunication.fragmentToolbarbyPosition(0);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        initializeView(view);
        initializeListener();
        if (!PreferenceManager.getBool(Constant.KEY_IS_LOADED_TRIPS)) {
            loadTripList(false);
        } else {
            fragment = new NextTripFragment();
            loadFragment(fragment);
        }
    }

    private void initializeView(View view) {
        btnNexttrip = view.findViewById(R.id.btn_next_trip);
        btnLastTrip = view.findViewById(R.id.btn_last_trip);
        ivAddUpdate = view.findViewById(R.id.iv_add_update);
        mapBtn = view.findViewById(R.id.map_btn);
        ivRefresh = view.findViewById(R.id.iv_refresh);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.trips));
    }

    private void initializeListener() {

        mapBtn.setOnClickListener(this);

        btnLastTrip.setOnClickListener(v -> {
            btnLastTrip.setBackgroundResource(R.drawable.bg_select_right);
            btnNexttrip.setBackgroundResource(R.drawable.bg_unselect_left);
            btnLastTrip.setClickable(false);
            btnNexttrip.setClickable(true);
            btnLastTrip.setTextColor(getResources().getColor(R.color.white));
            btnNexttrip.setTextColor(getResources().getColor(R.color.orange));
            fragment = new LastTripFragment();
            loadFragment(fragment);

        });
        btnNexttrip.setOnClickListener(v -> {
            btnNexttrip.setBackgroundResource(R.drawable.bg_select_left);
            btnLastTrip.setBackgroundResource(R.drawable.bg_unselect_right);
            btnNexttrip.setClickable(false);
            btnLastTrip.setClickable(true);
            btnLastTrip.setTextColor(getResources().getColor(R.color.orange));
            btnNexttrip.setTextColor(getResources().getColor(R.color.white));
            fragment = new NextTripFragment();
            loadFragment(fragment);
        });

        ivRefresh.setOnClickListener(v -> {

            SyncWithInternetFragment syncWithInternetFragment = new SyncWithInternetFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_in_trip", true);
            syncWithInternetFragment.setArguments(bundle);
            syncWithInternetFragment.show(getChildFragmentManager(), null);


        });

        ivAddUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateTripActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.KEY_IS_UPDATE, false);
            intent.putExtras(bundle);
            startActivity(intent);
        });


    }

    private void loadTripList(boolean isSync) {
        //fragmentActivityCommunication.showProgressDialog(true);
        final OneTimeWorkRequest workRequest =
                new OneTimeWorkRequest.Builder(LoadAllTripWorker.class)
                        .build();

        WorkManager.getInstance().enqueue(workRequest);
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null && workInfo.getState().isFinished()) {
                PreferenceManager.updateValue(Constant.KEY_IS_LOADED_TRIPS, true);
                if (!isSync) {
                    fragment = new NextTripFragment();
                    loadFragment(fragment);
                } else {
                    EventBus.getDefault().post(true);
                }
            }
        });
    }


    private void syncTrip() {
        EventBus.getDefault().post(new RemoveListObject(true));

  //      EventBus.getDefault().post(new LoadSendTripObject(true));



        fragmentActivityCommunication.showProgressDialog(true);

        ApiService apiService = new ApiCaller();
        apiService.getAllTripList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), 0, 30, 0, new ResponseCallback<TripListResponse>() {
            @Override
            public void onSuccess(TripListResponse data) {
                AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoTripBasic().nukeTable();
                for (TripListResponse.ListBean listBean : data.getList()) {
                    try {
                        Date tripBeginTime = null;

                        if(listBean.getEndTime() == null){

                            tripBeginTime = simpleDateFormat.parse("2019-05-17 12:55:00");

                        }else {
                            tripBeginTime = simpleDateFormat.parse(listBean.getEndTime());
                        }

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
                        basicTripDescription.setFriendShared(Integer.parseInt(listBean.getFriendAttend()));
                        //basicTripDescription.setTripJson(listBean.getTripJson());
                        AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoTripBasic().insertTripBasic(basicTripDescription);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    fragmentActivityCommunication.showProgressDialog(false);
                    EventBus.getDefault().post(true);
                }
            }

            @Override
            public void onError(Throwable th) {
                fragmentActivityCommunication.showProgressDialog(false);
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.trip_content,
                fragment, fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncData(SyncInternetObject syncInternetObject) {
        if (syncInternetObject.isSync()) {
            syncTrip();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dismissLoading(Boolean bool) {
        //fragmentActivityCommunication.showProgressDialog(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_btn: {

                Fragment f = new MapFragment();
                Bundle b = new Bundle();
                b.putBoolean("fromBtn", true);

                f.setArguments(b);

                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_content, f, f.getClass()
                        .getSimpleName()).addToBackStack(f.getClass()
                        .getSimpleName())
                        .commit();


            }
            break;
        }
    }
}
