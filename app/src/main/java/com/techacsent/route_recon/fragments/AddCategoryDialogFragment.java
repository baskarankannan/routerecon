package com.techacsent.route_recon.fragments;


import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.request_body_model.CreateCategoryModel;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.CreateCategoryViewModel;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */

public class AddCategoryDialogFragment extends DialogFragment {

    private EditText etCategory;
    private CreateCategoryViewModel createCategoryViewModel;
    private NavigationOptionInterface navigationOptionInterface;

    public AddCategoryDialogFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createCategoryViewModel = ViewModelProviders.of(this).get(CreateCategoryViewModel.class);
        etCategory = view.findViewById(R.id.et_category_name);
        Button btnSave = view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(etCategory.getText().toString().trim())) {
                createCategory();
            } else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_category_name), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createCategory() {

        navigationOptionInterface.showProgressDialog(true);
        CreateCategoryModel createCategoryModel = new CreateCategoryModel();
        createCategoryModel.setCategoryName(etCategory.getText().toString().trim());
        createCategoryViewModel.callCreateCategory(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                createCategoryModel).observe(this, categoryCreateResponse -> {
            if (categoryCreateResponse != null) {
                EventBus.getDefault().post(categoryCreateResponse);
                navigationOptionInterface.showProgressDialog(false);
                dismiss();
            } else {
                navigationOptionInterface.showProgressDialog(false);
            }

        });
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }
}
