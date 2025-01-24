package com.techacsent.route_recon.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.CreateReportModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostReportAlertFragment extends DialogFragment {
    private EditText etIssue;
    private double latitude;
    private double longitude;
    private int tripId;
    private String name;
    private int reportType;

    public PostReportAlertFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return showCustomDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
            tripId = getArguments().getInt("trip_id");
            reportType = getArguments().getInt("report_type");
            name = getArguments().getString("report_title");
        }
    }

    private AlertDialog showCustomDialog(){
        ViewGroup viewGroup = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_post_report_alert, viewGroup, false);

        etIssue = dialogView.findViewById(R.id.et_issue);
        Button btnOk = dialogView.findViewById(R.id.btn_ok);
        TextView tvtitle = dialogView.findViewById(R.id.tv_title);

        tvtitle.setText(name);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

        builder.setView(dialogView);

        final android.app.AlertDialog alertDialog = builder.create();

        btnOk.setOnClickListener(v -> {
            if (etIssue.getText().toString().trim().length() > 0) {
                EventBus.getDefault().post(new CreateReportModel(etIssue.getText().toString().trim(), reportType));
                dismiss();
            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_please_write_issue), Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.show();
        return alertDialog;
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
            tripId = getArguments().getInt("trip_id");
            reportType = getArguments().getInt("report_type");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_report_alert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etIssue = view.findViewById(R.id.et_issue);
        Button btnOk = view.findViewById(R.id.btn_ok);
        *//*btnOk.setOnClickListener(v -> postReport());*//*
        btnOk.setOnClickListener(v -> {
            if (etIssue.getText().toString().trim().length() > 0) {
                EventBus.getDefault().post(new CreateReportModel(etIssue.getText().toString().trim(), reportType));
                dismiss();
            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_please_write_issue), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postReport() {
        PostCrimeReportModel postCrimeReportModel = new PostCrimeReportModel();
        postCrimeReportModel.setReportType(reportType);
        postCrimeReportModel.setLat(latitude);
        postCrimeReportModel.setLongX(longitude);
        postCrimeReportModel.setRadius(0);
        postCrimeReportModel.setAddress("");
        postCrimeReportModel.setFullAddress("");
        postCrimeReportModel.setDescription(etIssue.getText().toString().trim());
        ApiService apiService = new ApiCaller();
        apiService.postCrimeReport(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), postCrimeReportModel, new ResponseCallback<PostCrimeReportResponse>() {
            @Override
            public void onSuccess(PostCrimeReportResponse data) {
                dismiss();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
