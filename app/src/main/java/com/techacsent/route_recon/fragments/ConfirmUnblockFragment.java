package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techacsent.route_recon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmUnblockFragment extends DialogFragment {


    public ConfirmUnblockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_unblock, container, false);
    }

}
