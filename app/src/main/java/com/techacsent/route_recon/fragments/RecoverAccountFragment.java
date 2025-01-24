package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecoverAccountFragment extends Fragment {
    private EditText etNewPass;
    private EditText etConfirmPass;
    private String email;
    private String code;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public RecoverAccountFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email");
            code = getArguments().getString("code");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recover_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNewPass = view.findViewById(R.id.et_new_password);
        etConfirmPass = view.findViewById(R.id.et_confirm_password);
        Button btnOkay = view.findViewById(R.id.btn_okay);
        btnOkay.setOnClickListener(v -> {
            if (etNewPass.getText().toString().trim().length()<6) {
                Toast.makeText(getActivity(), getResources().getString(R.string.text_password_warning), Toast.LENGTH_SHORT).show();

            } else if (!etNewPass.getText().toString().trim().equals(etConfirmPass.getText().toString().trim())) {
                Toast.makeText(getActivity(), Constant.PASSWORD_MISMATCH_WARNING, Toast.LENGTH_SHORT).show();
            } else {
                recoverAccount();
            }

        });
    }

    private void recoverAccount() {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.recoverAccount(email, code, etNewPass.getText().toString().trim(), new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Toast.makeText(getActivity(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                fragmentActivityCommunication.showProgressDialog(false);
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
