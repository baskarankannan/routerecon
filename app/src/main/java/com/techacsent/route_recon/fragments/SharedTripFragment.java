package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.NotificationBadgeObject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.utills.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class SharedTripFragment extends Fragment {
    private Button btnMyTrip;
    private Button btnBeingShared;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private Fragment fragment;

    private boolean isMyTrip = true;

    public SharedTripFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shared_trip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.fragmentToolbarbyPosition(2);
        initializeView(view);
        initializeListener();
        EventBus.getDefault().post(new NotificationBadgeObject(true, "share_action"));
    }

    private void initializeView(View view) {
        btnMyTrip = view.findViewById(R.id.btn_my_trip);
        btnBeingShared = view.findViewById(R.id.btn_being_trip);

        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        ImageView ivRefresh = view.findViewById(R.id.iv_refresh);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvDone = view.findViewById(R.id.tv_done);
        tvDone.setVisibility(View.GONE);
        ivRefresh.setVisibility(View.GONE);
        ivAddUpdate.setVisibility(View.GONE);
        tvTitle.setText(Constant.SHARED_TRIP);

        btnMyTrip.setClickable(false);
        fragment = new BeingSharedFragment();
        loadFragment(fragment);

    }

    private void initializeListener() {
        btnBeingShared.setOnClickListener(v -> {
            btnBeingShared.setBackgroundResource(R.drawable.bg_select_right);
            btnMyTrip.setBackgroundResource(R.drawable.bg_unselect_left);
            btnBeingShared.setClickable(false);
            btnMyTrip.setClickable(true);
            btnBeingShared.setTextColor(getResources().getColor(R.color.white));
            btnMyTrip.setTextColor(getResources().getColor(R.color.orange));
            fragment = new MyTripsFragment();
            loadFragment(fragment);
            //EventBus.getDefault().post(new NotificationBadgeObject(true, "share_action"));
            isMyTrip = false;
        });

        btnMyTrip.setOnClickListener(v -> {
            btnMyTrip.setBackgroundResource(R.drawable.bg_select_left);
            btnBeingShared.setBackgroundResource(R.drawable.bg_unselect_right);
            btnMyTrip.setClickable(false);
            btnBeingShared.setClickable(true);
            btnMyTrip.setTextColor(getResources().getColor(R.color.white));
            btnBeingShared.setTextColor(getResources().getColor(R.color.orange));
            isMyTrip = true;
            fragment = new BeingSharedFragment();
            loadFragment(fragment);
            EventBus.getDefault().post(new NotificationBadgeObject(true, "share_action"));
        });
    }

    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.shared_trip_content, fragment, fragment.getClass().getSimpleName()).commit();

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

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(isMyTrip){
                fragment = new MyTripsFragment();
                loadFragment(fragment);
            }else {
                fragment = new BeingSharedFragment();
                loadFragment(fragment);
            }

        }
    }*/
}
