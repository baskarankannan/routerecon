package com.techacsent.route_recon.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.ConfirmationCancelViewModel;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ConfirmationCancelFragment extends DialogFragment {
    private Button btnCancel;
    private Button btnOkay;
    private String tripId;
    private ConfirmationCancelViewModel confirmationCancelViewModel;
    private MyTripsResponse.ListBean item;

    public ConfirmationCancelFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getString("trip_sharing_id");
            item = getArguments().getParcelable("parcel");
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmation_cancel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnOkay = view.findViewById(R.id.btn_ok);
        confirmationCancelViewModel = ViewModelProviders.of(this).get(ConfirmationCancelViewModel.class);
        initializeListener();
    }

    private void initializeListener() {
        btnCancel.setOnClickListener(v -> dismiss());
        btnOkay.setOnClickListener(v -> canceltrip());
    }

    private void canceltrip() {
        confirmationCancelViewModel.getCancelSharedTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                tripId).observe(this, cancelSharedTripResponse -> {
            if (cancelSharedTripResponse != null) {
                if (cancelSharedTripResponse.getSuccess() != null && cancelSharedTripResponse.getSuccess().getMessage().equalsIgnoreCase("Route Sharing cancelled successfully")) {
                    setBackData();
                    /*EventBus.getDefault().post(new UpdateSharedTripObject(true));
                    dismiss();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().finish();*/
                }
            }

        });
    }

    private void setBackData() {
        Intent intent = new Intent();
        intent.putExtra("parcel", item);
        Objects.requireNonNull(getActivity()).setResult(RESULT_OK, intent);
        dismiss();
        //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getActivity().finish();

    }
}
