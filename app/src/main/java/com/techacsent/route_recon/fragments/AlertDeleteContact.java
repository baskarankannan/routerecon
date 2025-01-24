package com.techacsent.route_recon.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.room_db.entity.ContactDescription;
import org.greenrobot.eventbus.EventBus;

public class AlertDeleteContact extends DialogFragment {

    private ContactDescription contactDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            contactDescription = getArguments().getParcelable("parcel");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage("Are you sure about Deleting this contact?")
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    EventBus.getDefault().post(contactDescription);
                    dismiss();

                }).create();

    }
}
