package com.techacsent.route_recon.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.techacsent.route_recon.R;

import org.greenrobot.eventbus.EventBus;

public class SosFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage(getResources().getString(R.string.sos_mail_confirmation))
                .setTitle(getResources().getString(R.string.sos_email))
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    EventBus.getDefault().post(true);
                    dismiss();


                }).setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dismiss();

                })
                .create();

    }
}
