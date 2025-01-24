package com.techacsent.route_recon.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.GroupListResponse;

import org.greenrobot.eventbus.EventBus;

public class AlertDeleteGroupFragment extends DialogFragment {

    private GroupListResponse.ListBean item;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            item = getArguments().getParcelable("parcel");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage("are you sure about deleting this group?")
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    EventBus.getDefault().post(item);
                    dismiss();

                }).create();

    }
}
