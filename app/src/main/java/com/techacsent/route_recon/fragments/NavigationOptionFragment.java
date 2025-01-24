package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.CustomNavigationActivity;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.service.LocationService;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationOptionFragment extends BottomSheetDialogFragment {
    private TextView tvShareLocation;
    private TextView tvNavCurPos;
    private TextView tvNavStartPos;
    private NavigationOptionInterface navigationOptionInterface;
    private int tripId;
    private double latitude;
    private double longitude;
    private LocationService tracker;

    public NavigationOptionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tracker = new LocationService(getActivity());
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        tvShareLocation = view.findViewById(R.id.tv_share_location);
        tvNavCurPos = view.findViewById(R.id.tv_nav_cur_pos);
        tvNavStartPos = view.findViewById(R.id.tv_nav_start_pos);
    }

    private void initializeListener() {
        tvShareLocation.setOnClickListener(v ->

                navigationOptionInterface.shareLocation(tripId));
        tvNavCurPos.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CustomNavigationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("trip_id", tripId);
            bundle.putDouble("lat",tracker.getLatitude());
            bundle.putDouble("lonX",tracker.getLongitude());
            bundle.putBoolean("is_trip_route",false);
            intent.putExtras(bundle);
            startActivity(intent);
            dismiss();

        });
        tvNavStartPos.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CustomNavigationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("trip_id", tripId);
            bundle.putDouble("lat",latitude);
            bundle.putDouble("lonX",longitude);
            bundle.putBoolean("is_trip_route",true);
            intent.putExtras(bundle);
            startActivity(intent);
            dismiss();
        });
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
}
