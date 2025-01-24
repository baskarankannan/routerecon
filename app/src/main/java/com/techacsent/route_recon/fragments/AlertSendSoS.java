package com.techacsent.route_recon.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.techacsent.route_recon.R;

import org.greenrobot.eventbus.EventBus;

public class AlertSendSoS extends DialogFragment {
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            email = getArguments().getString("email");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage("Are you sure about sending sos email to this contact?")
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    EventBus.getDefault().post(email);
                    dismiss();

                }).create();

    }
}
