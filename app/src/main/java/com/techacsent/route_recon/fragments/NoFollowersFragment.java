package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techacsent.route_recon.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoFollowersFragment extends DialogFragment {
    private boolean isFromSuccess;

    public NoFollowersFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            isFromSuccess = getArguments().getBoolean("is_from_success");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewAndListener(view);
    }

    private void initializeViewAndListener(View view) {
        Button btnok = view.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(v -> {
            if(isFromSuccess){
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                dismiss();
                getActivity().finish();

            }else {
                dismiss();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
            }

        });
    }

}
