package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.MarkerEditObject;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditMarkerFragment extends DialogFragment {
    private CreateTripModelClass.MarkersBean markerBean;
    private String address;
    private String fullAddress;

    public EditMarkerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //markerBean = getArguments().getParcelable(Constant.KEY_PARCELABLE_MARKER_OBJ);
            address = getArguments().getString("address");
            fullAddress = getArguments().getString("full_address");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_marker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
    }

    private void initializeView(View view) {
        EditText etAddress = view.findViewById(R.id.et_address);
        EditText etFullAddress = view.findViewById(R.id.et_full_address);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnOkay = view.findViewById(R.id.btn_edit_marker);
        etAddress.setText(address);
        etFullAddress.setText(fullAddress);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MarkerEditObject(etAddress.getText().toString().trim(),
                        etFullAddress.getText().toString().trim()));
                dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
