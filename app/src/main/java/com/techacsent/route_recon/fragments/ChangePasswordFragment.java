package com.techacsent.route_recon.fragments;


import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.ChangePasswordViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {
    private EditText etOldPass;
    private EditText etNewPass;
    private EditText etConfirmPass;
    private Button btnOkay;
    ChangePasswordViewModel changePasswordViewModel;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public ChangePasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        fragmentActivityCommunication.setTitle("Change Password");
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        etOldPass = view.findViewById(R.id.et_old_password);
        etNewPass = view.findViewById(R.id.et_new_password);
        etConfirmPass = view.findViewById(R.id.et_confirm_password);
        btnOkay = view.findViewById(R.id.btn_okay);
    }

    private void initializeListener() {
        btnOkay.setOnClickListener(v -> {

            if (etOldPass.getText().toString().trim().length() < 6 || etNewPass.getText().toString().trim().length() < 6) {
                Toast.makeText(getActivity(), Constant.PASSWORD_WARNING, Toast.LENGTH_SHORT).show();
            } else if (!etNewPass.getText().toString().trim().equals(etConfirmPass.getText().toString().trim())) {
                Toast.makeText(getActivity(), Constant.PASSWORD_MISMATCH_WARNING, Toast.LENGTH_SHORT).show();
            } else {
                changePassword();
            }
        });
    }

    private void changePassword() {
        fragmentActivityCommunication.showProgressDialog(true);

        changePasswordViewModel.getChangePassword(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID)
                , etOldPass.getText().toString().trim(), etNewPass.getText().toString().trim()).observe(this, changePasswordResponse -> {
                    if (changePasswordResponse!=null) {
                        //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                        getActivity().finish();
                        fragmentActivityCommunication.showProgressDialog(false);
                    } else {
                        fragmentActivityCommunication.showProgressDialog(false);
                        //Toast.makeText(getActivity(), Constant.MSG_SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }

}
