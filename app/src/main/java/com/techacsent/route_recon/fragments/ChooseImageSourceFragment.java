package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.DataObject;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseImageSourceFragment extends BottomSheetDialogFragment {

    public ChooseImageSourceFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_choose_image_source, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvCamera = view.findViewById(R.id.tv_camera);
        TextView tvGallery = view.findViewById(R.id.tv_gallery);
        tvCamera.setOnClickListener(v -> {
            EventBus.getDefault().post(new DataObject(1, true));
            dismiss();
        });

        tvGallery.setOnClickListener(v -> {
            EventBus.getDefault().post(new DataObject(2, true));
            dismiss();
        });
    }
}
