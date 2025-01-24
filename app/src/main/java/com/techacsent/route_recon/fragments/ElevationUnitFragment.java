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

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.ElevationUnitObject;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElevationUnitFragment extends DialogFragment implements View.OnClickListener {


    public ElevationUnitFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_elevation_unit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvfeet = view.findViewById(R.id.tv_feet);
        TextView tvMeter = view.findViewById(R.id.tv_meter);
        tvfeet.setOnClickListener(this);
        tvMeter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feet:
                EventBus.getDefault().post(new ElevationUnitObject(false));
                dismiss();
                break;
            case R.id.tv_meter:
                EventBus.getDefault().post(new ElevationUnitObject(true));
                dismiss();
                break;
        }

    }
}
