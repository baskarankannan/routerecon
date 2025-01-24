package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.DangerMarkerObj;
import com.techacsent.route_recon.utills.Constant;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetRadiusFragment extends DialogFragment {
    private RangeBar rangeBar;
    private TextView tvRadius;
    private TextView tvOk;
    private int type;
    //private TripMarkerResponse.ListBean markerBean;
    private double lat;
    private double lonX;

    public SetRadiusFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (type == 1) {
            markerBean = getArguments().getParcelable(Constant.KEY_PARCELABLE_MARKER_OBJ);
        } else if (type == 2) {*/
        if (getArguments() != null) {
            type = getArguments().getInt(Constant.KEY_TYPE_OF_RADIUS);
            lat = getArguments().getDouble(Constant.KEY_LATITUDE);
            lonX = getArguments().getDouble(Constant.KEY_LONGITUDE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_radius, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        rangeBar = view.findViewById(R.id.rangebar);
        tvRadius = view.findViewById(R.id.tv_radius);
        tvOk = view.findViewById(R.id.tv_ok);
    }

    private void initializeListener() {
        rangeBar.setOnRangeBarChangeListener((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> tvRadius.setText(rightPinValue));
        tvOk.setOnClickListener(v -> {
            if (type == 1) {
                //editMarker();
            } else if (type == 2) {
                EventBus.getDefault().post(new DangerMarkerObj(Double.valueOf(tvRadius.getText().toString()), lat, lonX, true));
                dismiss();
            }
        });
    }

    /*private void editMarker() {
        ApiService apiService = new ApiCaller();
        EditMarkerModel editMarkerModel = new EditMarkerModel();
        editMarkerModel.setMarkerId(Integer.parseInt(markerBean.getId()));
        editMarkerModel.setRadius(Integer.parseInt(tvRadius.getText().toString().trim()));
        editMarkerModel.setAddress(markerBean.getAddress());
        editMarkerModel.setFullAddress(markerBean.getFullAddress());
        editMarkerModel.setDescription(markerBean.getDescription());
        apiService.editTripMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), editMarkerModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                dismiss();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
