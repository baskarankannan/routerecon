package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.model.ChecklistCreateResponse;
import com.techacsent.route_recon.model.request_body_model.CreateCatagorizedChkListModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateItemFragment extends DialogFragment {
    private EditText etChecklist;
    private int id;


    public CreateItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            id = getArguments().getInt("category_id");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etChecklist = view.findViewById(R.id.et_category_name);
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(etChecklist.getText().toString().trim())){
                addItem();
            }else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_category_name), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void addItem() {
        CreateCatagorizedChkListModel createCatagorizedChkListModel = new CreateCatagorizedChkListModel();
        createCatagorizedChkListModel.setChecklistText(etChecklist.getText().toString().trim());
        createCatagorizedChkListModel.setCategoryId(id);
        ApiService apiService = new ApiCaller();
        apiService.callCreateCategorizedChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createCatagorizedChkListModel, new ResponseCallback<ChecklistCreateResponse>() {
            @Override
            public void onSuccess(ChecklistCreateResponse data) {
                EventBus.getDefault().post(data);
                dismiss();
            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }
}
