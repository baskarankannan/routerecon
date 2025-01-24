package com.techacsent.route_recon.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.HomeActivity;
import com.techacsent.route_recon.activity.ResetPasswordActivity;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.LoginActivityInterface;
import com.techacsent.route_recon.model.request_body_model.UserTokenModel;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.LoginViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import timber.log.Timber;


public class LoginFragment extends Fragment {
    private FrameLayout frameContent;
    private EditText etMail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPass;
    private TextView tvCreateNew;
    private LoginViewModel loginViewModel;
    private UserTokenModel userTokenModel;
    private LoginActivityInterface loginActivityInterface;
    private String token;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        getServerToken();
        initializeView(view);
        spannableSignup(tvCreateNew, Constant.MSG_CREATE_NEW, true);
        spannableSignup(tvForgotPass, Constant.MSG_FORGOT_PASSWORD, false);
        initializeListener();
    }

    private void initializeView(View view) {
        frameContent = view.findViewById(R.id.login_content);
        etMail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvForgotPass = view.findViewById(R.id.tv_forgot_password);
        tvCreateNew = view.findViewById(R.id.tv_create_new);
        TextView tvVersion = view.findViewById(R.id.tv_version);
        userTokenModel = new UserTokenModel();
       // tvVersion.setText(BuildConfig.BUILD_NO);
        checkLocationpermission();
    }

    private void initializeListener() {
        btnLogin.setOnClickListener(v -> {
            if (checkLocationpermission()) {
                if (etMail.getText().toString().trim().length() < 1) {
                    Snackbar.make(v, Constant.EMPTY_WARNING, Snackbar.LENGTH_LONG).show();
                } else if (etPassword.getText().toString().trim().length() < 6) {
                    Snackbar.make(v, Constant.PASSWORD_WARNING, Snackbar.LENGTH_LONG).show();
                } else if (!Utils.isValidEmail(etMail.getText().toString().trim())) {
                    Snackbar.make(v, getResources().getString(R.string.email_not_valid), Snackbar.LENGTH_LONG).show();
                } else {
                    login(etMail.getText().toString().trim(), etPassword.getText().toString().trim());
                }
            } else {
                Snackbar.make(v, getResources().getString(R.string.text_location_alert), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings", view -> openPermissionDialog())
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
        frameContent.setOnClickListener(this::hideKeyboardFrom);
    }

    public void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean checkLocationpermission() {
        if (ContextCompat.checkSelfPermission(RouteApplication.getInstance().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.text_location_alert_header))
                        .setMessage(getResources().getString(R.string.text_location_alert))
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION))
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(RouteApplication.getInstance().getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                }

            } else {
                Snackbar.make(btnLogin, getResources().getString(R.string.text_location_alert), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings", view -> openPermissionDialog())
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();

            }
        }
    }


    private void login(String email, String password) {
        loginActivityInterface.showProgressDialog(true);

        loginViewModel.getLogin(email, password).observe(this, loginResponse -> {
            if (loginResponse != null) {
                PreferenceManager.updateValue(Constant.KEY_USER_ID, loginResponse.getUserId());
                PreferenceManager.updateValue(Constant.KEY_TOKEN_ID, loginResponse.getTokenId());
                PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_DATE, loginResponse.getUserData().getLastSubscriptionDate());
                PreferenceManager.updateValue(Constant.KEY_IS_LOGGED_IN, true);
                PreferenceManager.updateValue(Constant.KEY_IS_LOADED_TRIPS, false);
                PreferenceManager.updateValue(Constant.KEY_MAP_STYLE_ID, 2);
                PreferenceManager.updateValue(Constant.KEY_USER_EMAIL, loginResponse.getUserData().getEmail());
                PreferenceManager.updateValue(Constant.KEY_MAPBOX_STYLE_VALUE, getResources().getString(R.string.mapbox_style_satellite_streets));
                PreferenceManager.updateValue(Constant.KEY_IS_ELEVATION_IN_METER, true);
                PreferenceManager.updateValue(Constant.KEY_UNIT_COORDINATE, "decimal");
                Utils.getHashSignatureFromTokenAndUserId();
                updateFirebaseToken();
            } else {
                loginActivityInterface.showProgressDialog(false);
                //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFirebaseToken() {

        userTokenModel.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
        userTokenModel.setToken(token);/*getFirebaseToken()*/
        userTokenModel.setDeviceType(2);

        loginViewModel.updateServerToken(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), userTokenModel).observe(this, successArray -> {
            if (successArray != null) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                loginActivityInterface.showProgressDialog(false);
                requireActivity().finish();
            } else {
                loginActivityInterface.showProgressDialog(false);
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spannableSignup(TextView textView, final String signup, boolean isSignUp) {
        SpannableStringBuilder spanText = new SpannableStringBuilder();
        if (isSignUp) {
            spanText.append("No Account? ");
            spanText.append(signup);
            spanText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Fragment fragment = new CreateAccountFragment();
                    addFragment(fragment);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint textPaint) {
                    textPaint.setColor(getResources().getColor(R.color.orange));
                    textPaint.setUnderlineText(true);
                }
            }, spanText.length() - signup.length(), spanText.length(), 0);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            spanText.append(signup);
            spanText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint textPaint) {
                    textPaint.setColor(Color.WHITE);
                    textPaint.setUnderlineText(true);
                }
            }, spanText.length() - signup.length(), spanText.length(), 0);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spanText, TextView.BufferType.SPANNABLE);
        }
    }

    private void addFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
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

    private void getServerToken() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            token = instanceIdResult.getToken();
            Log.e("firbaseToken : ","token : "+token);
            Timber.d(token);
            //PreferenceManager.updateValue(Constant.KEY_FIREBASE_TOKEN_ID, token);

        });
    }

    private void openPermissionDialog() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", Objects.requireNonNull(getActivity()).getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


}
