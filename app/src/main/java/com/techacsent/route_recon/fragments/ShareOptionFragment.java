package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.RouteNavigationActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareOptionFragment extends BottomSheetDialogFragment {
    private int tripId;
    private double latitude;
    private double longitude;
    private String navigationType;
    private boolean isTripRoute;
    private FragmentActivityCommunication fragmentActivityCommunication;
    int[] typeNetworks = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};

    public ShareOptionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
            navigationType = getArguments().getString("navigation_type");
            isTripRoute = getArguments().getBoolean("is_trip_route");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvShare = view.findViewById(R.id.tv_share_location);
        TextView tvWithoutShare = view.findViewById(R.id.tv_without_share_location);
        tvShare.setOnClickListener(v -> {
            if (isNetworkAvailable(RouteApplication.getInstance().getApplicationContext(), typeNetworks)) {
                showNavigationFragment(true);
                //startStoptTrip();
            } else {
                dismiss();
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_no_internet), Toast.LENGTH_SHORT).show();
            }
        });
        tvWithoutShare.setOnClickListener(v -> {
            if (isNetworkAvailable(RouteApplication.getInstance().getApplicationContext(), typeNetworks)) {
                showNavigationFragment(false);
            } else {
                dismiss();
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_no_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNavigationFragment(boolean isShare) {
        Intent intent = new Intent(getActivity(), RouteNavigationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_share", isShare);
        bundle.putInt("trip_id", tripId);
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lonX", longitude);
        bundle.putString("navigation_type", navigationType);
        bundle.putBoolean("is_trip_route", isTripRoute);
        intent.putExtras(bundle);
        startActivity(intent);
        dismiss();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) RouteApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isNetworkAvailable(Context context, int[] typeNetworks) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int typeNetwork : typeNetworks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(typeNetwork);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
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
}
