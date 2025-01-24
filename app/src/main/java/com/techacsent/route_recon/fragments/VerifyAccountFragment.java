package com.techacsent.route_recon.fragments;


import android.content.Context;
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
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.CreateAccountObject;
import com.techacsent.route_recon.interfaces.LoginActivityInterface;
import com.techacsent.route_recon.model.CreateAccountResponse;
import com.techacsent.route_recon.model.request_body_model.VerifyAccountModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyAccountFragment extends DialogFragment {

    private String email;
    private EditText etCode;

    private LoginActivityInterface loginActivityInterface;


    public VerifyAccountFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verify_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etMail = view.findViewById(R.id.et_email);
        etCode = view.findViewById(R.id.et_code);
        Button btnCreateAccount = view.findViewById(R.id.btn_create_account);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        etMail.setText(email);
        etMail.setEnabled(false);
        btnCreateAccount.setOnClickListener(v -> {
            if (etCode.getText().toString().trim().length() > 1) {
                verifyAccount();

            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_valid_code), Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());
    }

    private void verifyAccount() {
        loginActivityInterface.showProgressDialog(true);
        VerifyAccountModel verifyAccountModel = new VerifyAccountModel();
        verifyAccountModel.setEmail(email);
        verifyAccountModel.setCode(etCode.getText().toString().trim());

        ApiService apiService = new ApiCaller();
        apiService.verifyAccount(Constant.JWT_HASH_SIGNETURE_FOR_LOGIN, verifyAccountModel, new ResponseCallback<CreateAccountResponse>() {
            @Override
            public void onSuccess(CreateAccountResponse data) {
                EventBus.getDefault().post(new CreateAccountObject(true));
                dismiss();
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                loginActivityInterface.showProgressDialog(false);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                loginActivityInterface.showProgressDialog(false);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginActivityInterface) {
            loginActivityInterface = (LoginActivityInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement LoginActivityInterface interface");
        }
    }
}
