package com.techacsent.route_recon.fragments;


import android.app.NotificationManager;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.LoginActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.StopServiceObject;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.service.LocationServiceV2;
import com.techacsent.route_recon.service.RouteService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.LogoutViewModel;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Objects;

public class LogoutFragment extends DialogFragment {
    private View view;
    private Button btnCancel;
    private Button btnOk;

    public LogoutFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_logout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        initializeListener();
    }

    private void initializeView() {
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnOk = view.findViewById(R.id.btn_ok);
    }

    private void initializeListener() {
        btnCancel.setOnClickListener(v -> dismiss());
        btnOk.setOnClickListener(v -> logout(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID)));

        /*btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PaymentFragment();
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).addToBackStack(fragment.getTag()).commit();
            }
        });*/

    }

    private void logout(String token) {
        LogoutViewModel logoutViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        logoutViewModel.getLogout(token).observe(this, logoutResponse -> {
            deleteToken();
            PreferenceManager.updateValue(Constant.KEY_USER_ID, 0);
            PreferenceManager.updateValue(Constant.KEY_TOKEN_ID, "");
            PreferenceManager.updateValue(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID, "");
            PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_DATE, "");
            PreferenceManager.updateValue(Constant.KEY_IS_LOGGED_IN, false);
            PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, false);
            PreferenceManager.updateValue(Constant.KEY_IS_LOADED_TRIPS, false);
            PreferenceManager.updateValue(Constant.KEY_END_TIME, 0L);
            PreferenceManager.updateValue(Constant.KEY_DURATION, 0);
            PreferenceManager.updateValue(Constant.KEY_END_TIME, 0L);
            PreferenceManager.updateValue(Constant.KEY_IS_MARKER_LOADED, false);
            PreferenceManager.updateValue(Constant.KEY_WAS_IN_TUNNEL, false);
            PreferenceManager.updateValue(Constant.KEY_FIREBASE_TOKEN_ID, "");
            PreferenceManager.updateValue(Constant.KEY_IS_BADGE_LOADED, false);
            PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT, 0);
            PreferenceManager.updateValue(Constant.KEY_TRIP_SHARING_BADGE_COUNT, 0);
            PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARING_BADGE_COUNT, 0);
            PreferenceManager.updateValue(Constant.KEY_IS_TRAFFIC_SELECTED, false);
            PreferenceManager.updateValue(Constant.KEY_IS_MARKER_LOAD, false);
            PreferenceManager.updateValue(Constant.KEY_IS_3D_MAP_SELECTED, false);
            PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST, "");
            AppDatabase.getAppDatabase(getActivity()).clearAllTables();

            PreferenceManager.updateValue(Constant.KEY_DISTANCE_UNIT, "");



           if(PreferenceManager.getBool(Constant.KEY_IS_TRACKING_STARTED)) {


               requireActivity().stopService(new Intent(getActivity(), LocationServiceV2.class));

               PreferenceManager.updateValue(Constant.KEY_IS_TRACKING_STARTED, false);

           }

            NotificationManager notificationManager = (NotificationManager) RouteApplication.getInstance().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();

            EventBus.getDefault().post(new StopServiceObject(true));
            //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (RouteService.getInstance() != null) {
                RouteService.getInstance().stopService();
            }
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private void deleteToken() {
        new Thread(() -> {
            FirebaseMessaging.getInstance().deleteToken();
        }).start();
    }

}
