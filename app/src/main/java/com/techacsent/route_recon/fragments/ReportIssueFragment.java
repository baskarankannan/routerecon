package com.techacsent.route_recon.fragments;


import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.SendEmailModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.jetbrains.annotations.NotNull;


import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportIssueFragment extends Fragment {

    private EditText etFrom;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public ReportIssueFragment() {
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_issue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etBody = view.findViewById(R.id.et_body);
        Button btnSend = view.findViewById(R.id.btn_send);
        etFrom = view.findViewById(R.id.et_from);
        TextView tvChoose = view.findViewById(R.id.tv_from);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.text_report_issue));

        tvChoose.setOnClickListener(v -> pickContact());

        btnSend.setOnClickListener(v -> {
            if(TextUtils.isEmpty(etBody.getText().toString().trim())){
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getString(R.string.text_report_is_empty), Toast.LENGTH_SHORT).show();

            }else {
                sendFeedback(etBody.getText().toString().trim());
            }
        });
    }

    private void pickContact() {
        Intent googlePicker = AccountPicker
                .newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                        true, null, null, null, null);
        startActivityForResult(googlePicker, 100);
    }

    private void sendFeedback(String msg) {
        fragmentActivityCommunication.showProgressDialog(true);
        SendEmailModel sendEmailModel = new SendEmailModel();
        sendEmailModel.setMessage(msg);
        sendEmailModel.setEmail(getResources().getString(R.string.text_dev_mail));
        ApiService apiService = new ApiCaller();
        apiService.sendFeedback(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), sendEmailModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                fragmentActivityCommunication.showProgressDialog(false);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                getActivity().finish();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                fragmentActivityCommunication.showProgressDialog(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            etFrom.setText(accountName);
        }
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
