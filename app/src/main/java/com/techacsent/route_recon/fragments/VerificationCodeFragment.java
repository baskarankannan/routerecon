package com.techacsent.route_recon.fragments;


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
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificationCodeFragment extends Fragment {
    private EditText etVerificationCode;
    private String email;
    private FragmentActivityCommunication fragmentActivityCommunication;


    public VerificationCodeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verification_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etVerificationCode = view.findViewById(R.id.et_verification_code);
        Button btnVerify = view.findViewById(R.id.btn_send);
        btnVerify.setOnClickListener(v -> {
            if (etVerificationCode.getText().toString().trim().length()>0) {
                verifyCode();
            } else {
                Toast.makeText(getActivity(), Constant.EMPTY_FIELD_WARNING, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void verifyCode() {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.validateCode(email, etVerificationCode.getText().toString().trim(), new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                Fragment fragment = new RecoverAccountFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email",email);
                bundle.putString("code",etVerificationCode.getText().toString().trim());
                fragment.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_content, fragment, fragment.getClass().getSimpleName()).commit();
                fragmentActivityCommunication.showProgressDialog(false);
            }

            @Override
            public void onError(Throwable th) {
                fragmentActivityCommunication.showProgressDialog(false);
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
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
