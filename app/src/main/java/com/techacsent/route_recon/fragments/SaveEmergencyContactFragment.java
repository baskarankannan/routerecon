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
import android.widget.EditText;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.ContactInfoObj;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveEmergencyContactFragment extends DialogFragment {
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private boolean isEdit;
    private String email;
    private int id;
    private int pos;

    private String name;
    private String phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            phone = getArguments().getString("phone");
            isEdit = getArguments().getBoolean("is_edit");
            if (isEdit) {
                email = getArguments().getString("email");
                id = getArguments().getInt("id");
                pos = getArguments().getInt("pos");
            }
        }
    }

    public SaveEmergencyContactFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_save_emergency_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etName = view.findViewById(R.id.et_name);
        etPhone = view.findViewById(R.id.et_phone_number);
        etEmail = view.findViewById(R.id.et_email);
        Button btnSave = view.findViewById(R.id.btn_save_contact);
        /*etName.setEnabled(false);
        etPhone.setEnabled(false);*/
        if (isEdit) {
            etEmail.setText(email);
        }

        etName.setText(name);
        etPhone.setText(phone);

        btnSave.setOnClickListener(v -> {
            btnSave.setEnabled(false);
            if (!isEdit) {
                if (isValidEmail(etEmail.getText().toString().trim())) {
                    EventBus.getDefault().post(new ContactInfoObj(etName.getText().toString().trim(), etPhone.getText().toString().trim(), etEmail.getText().toString().trim(), isEdit, id, pos));
                    dismiss();
                    btnSave.setEnabled(true);
                } else if (etName.getText().toString().trim().length() < 1) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_name), Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                } else if (etPhone.getText().toString().trim().length() < 3) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_number), Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                }
            } else {
                if (isValidEmail(etEmail.getText().toString().trim())) {
                    EventBus.getDefault().post(new ContactInfoObj(etName.getText().toString().trim(), etPhone.getText().toString().trim(), etEmail.getText().toString().trim(), isEdit, id, pos));
                    dismiss();
                } else if (etName.getText().toString().trim().length() < 1) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_name), Toast.LENGTH_SHORT).show();

                } else if (etPhone.getText().toString().trim().length() < 3) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_enter_number), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                }
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
