package com.techacsent.route_recon.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.techacsent.route_recon.R;


import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorFragment extends DialogFragment {

    private String msg;


    public ErrorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString("error_msg");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    dismiss();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getActivity().finish();

                }).create();

    }

}
