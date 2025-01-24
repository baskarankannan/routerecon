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
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.SecurityChecklistResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChecklistDialogFragment extends DialogFragment {
    private EditText etDescription;
    private int tripId;
    private boolean isUpdate;
    private SecurityChecklistResponse.ChecklistBean checklistBean;
    private int position;
    private NavigationOptionInterface navigationOptionInterface;

    public ChecklistDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
            isUpdate = getArguments().getBoolean("is_update");
            if (isUpdate) {
                checklistBean = getArguments().getParcelable("check_list");
                position = getArguments().getInt("position");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checklist_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescription = view.findViewById(R.id.et_description);
        Button btnOkay = view.findViewById(R.id.btn_ok);
        if (isUpdate) {
            //etDescription.setText(checklistBean.getDescription());
        }
        btnOkay.setOnClickListener(v -> {
            if (!isUpdate) {
                if (etDescription.getText().toString().trim().length() < 1) {
                    Toast.makeText(getActivity(), R.string.please_add_description, Toast.LENGTH_SHORT).show();
                } else {
                    dismiss();
                    //addSecurityChecklist();
                }
            } else {
                if (etDescription.getText().toString().trim().length() < 1) {
                    Toast.makeText(getActivity(), R.string.please_add_description, Toast.LENGTH_SHORT).show();
                } else {
                    dismiss();
                    //updateSecurityChecklist();
                }
            }

        });
    }

    /*private void addSecurityChecklist() {
        navigationOptionInterface.showProgressDialog(true);
        CreateChecklistModel createChecklistModel = new CreateChecklistModel();
        createChecklistModel.setTripId(tripId);
        createChecklistModel.setIsCustom(1);
        createChecklistModel.setTitle(etDescription.getText().toString().trim());
        createChecklistModel.setDescription(etDescription.getText().toString().trim());
        ApiService apiService = new ApiCaller();
        apiService.createChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createChecklistModel, new ResponseCallback<CreateChecklistResponse>() {
            @Override
            public void onSuccess(CreateChecklistResponse data) {
                if (data.getSuccess().getMessage().equals("Security checklist created successfully")) {
                    EventBus.getDefault().post(new ChecklistObject(true, data.getChecklistId(), etDescription.getText().toString().trim(), 0));
                    navigationOptionInterface.showProgressDialog(false);
                    //dismiss();
                } else {
                    //dismiss();
                    navigationOptionInterface.showProgressDialog(false);
                }
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                navigationOptionInterface.showProgressDialog(false);
                dismiss();
            }
        });
    }
*/
    /*private void updateSecurityChecklist() {
        navigationOptionInterface.showProgressDialog(true);
        UpdateChecklistModel updateChecklistModel = new UpdateChecklistModel();
        *//*updateChecklistModel.setChecklistId(checklistBean.getChecklistId());
        updateChecklistModel.setTripId(tripId);
        updateChecklistModel.setDescription(etDescription.getText().toString().trim());*//*
        ApiService apiService = new ApiCaller();
        apiService.updateChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), updateChecklistModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                EventBus.getDefault().post(new ChecklistObject(false, checklistBean.getChecklistId(), etDescription.getText().toString().trim(), position));
                //dismiss();
                navigationOptionInterface.showProgressDialog(false);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                dismiss();
                navigationOptionInterface.showProgressDialog(false);
            }
        });
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }
}
