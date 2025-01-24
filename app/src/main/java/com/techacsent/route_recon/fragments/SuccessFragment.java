package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.utills.Constant;


import java.util.Objects;

import timber.log.Timber;

public class SuccessFragment extends Fragment {
    private Button btnViewTrip;
    private Button btnShareTrip;
    private int tripId;
    private boolean istripUpdate;
    private NavigationOptionInterface navigationOptionInterface;

    public SuccessFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
            istripUpdate = getArguments().getBoolean("is_new_trip");
        }
        Timber.d(String.valueOf(tripId));
        Timber.d(String.valueOf(istripUpdate));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        navigationOptionInterface.hideToolbar(true);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        btnViewTrip = view.findViewById(R.id.btn_view_trip);
        btnShareTrip = view.findViewById(R.id.btn_share_trip);
        if (istripUpdate) {
            tvTitle.setText(R.string.trip_updated);
        }
    }

    private void initializeListener() {
        btnViewTrip.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getActivity().finish();
        });
        btnShareTrip.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
            Fragment fragment = new ShareFragment();
            Bundle bundle = new Bundle();
            bundle.putString("trip_id", String.valueOf(tripId));
            bundle.putBoolean(Constant.KEY_IS_SHARE, true);
            bundle.putBoolean("is_from_details",false);
            bundle.putBoolean("is_from_success",true);
            fragment.setArguments(bundle);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName()).commit();

        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }
}
