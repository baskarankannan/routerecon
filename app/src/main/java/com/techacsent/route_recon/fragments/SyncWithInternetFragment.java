package com.techacsent.route_recon.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.SyncInternetObject;
import com.techacsent.route_recon.event_bus_object.SyncMarkerObject;
import com.techacsent.route_recon.utills.Constant;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class SyncWithInternetFragment extends DialogFragment {

    private boolean isInTrip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isInTrip = getArguments().getBoolean("is_in_trip");
        }
        Timber.d(String.valueOf(isInTrip));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage(Constant.MSG_SYNC_WITH_INTERNET)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    if (isInTrip) {
                        EventBus.getDefault().post(new SyncInternetObject(true));
                        dialog.dismiss();
                    } else {
                        EventBus.getDefault().post(new SyncMarkerObject(true));
                        dialog.dismiss();
                    }
                    /*EventBus.getDefault().post(new SyncInternetObject(isInTrip));
                    dialog.dismiss();*/

                }).create();

    }
}
