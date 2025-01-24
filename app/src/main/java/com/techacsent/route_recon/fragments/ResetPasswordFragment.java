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

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    private EditText etMail;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public ResetPasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etMail = view.findViewById(R.id.et_email);
        Button btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(v -> {
            if(etMail.getText().toString().trim().length()<1 || !isValidEmail(etMail.getText().toString().trim())){
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.email_not_valid,Toast.LENGTH_SHORT).show();
            } else {
                resetPassword();
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void resetPassword() {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.resetPassword(etMail.getText().toString().trim(), new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                Fragment fragment = new VerificationCodeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", etMail.getText().toString().trim());
                fragment.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_content, fragment, fragment.getClass().getSimpleName()).commit();
                fragmentActivityCommunication.showProgressDialog(false);
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
