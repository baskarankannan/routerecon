package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.DeleteGroupresponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationDeleteFragment extends DialogFragment {
    private View view;
    private TextView tvTitle;
    private Button btnCancel;
    private Button btnOk;
    private String groupId;
    private String groupName;


    public ConfirmationDeleteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupId = getArguments().getString(Constant.KEY_GROUP_ID);
        groupName = getArguments().getString(Constant.KEY_GROUP_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirmation_delete, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        initializeListener();
    }

    private void initializeView() {
        tvTitle = view.findViewById(R.id.tv_title);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnOk = view.findViewById(R.id.btn_ok);
        tvTitle.setText(groupName);
    }

    private void initializeListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup(groupId, 0);
            }
        });

    }

    private void deleteGroup(String groupId, int position) {
        ApiService apiService = new ApiCaller();
        apiService.deleteGroup(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                groupId, new ResponseCallback<DeleteGroupresponse>() {
                    @Override
                    public void onSuccess(DeleteGroupresponse data) {
                        Toast.makeText(getActivity(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onError(Throwable th) {
                        th.printStackTrace();
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
