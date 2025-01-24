package com.techacsent.route_recon.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.custom_view.RoundedCustomImageView;
import com.techacsent.route_recon.event_bus_object.CreateAccountObject;
import com.techacsent.route_recon.event_bus_object.DataObject;
import com.techacsent.route_recon.interfaces.LoginActivityInterface;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.CreateAccountModel;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class CreateAccountFragment extends Fragment {
    private FrameLayout frameCreateAccount;
    private RoundedCustomImageView ivImage;
    private FloatingActionButton fbAdd;
    private EditText etFullName;
    private EditText etMail;
    private EditText etUserName;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnCreateAccount;
    private Fragment fragment;
    private static final int REQUEST_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int REQUEST_CAPTURE_IMAGE_FROM_CAMERA = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;
    private String encodedImage;
    private LoginActivityInterface loginActivityInterface;
    private CreateAccountModel createAccountModel;

    public CreateAccountFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*if (Build.VERSION.SDK_INT >= 21) {
            AdjustResizeHelper.assistActivity(getActivity());
        }*/
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        frameCreateAccount = view.findViewById(R.id.frame_create_content);
        ivImage = view.findViewById(R.id.iv_image);
        fbAdd = view.findViewById(R.id.btn_add_image);
        etFullName = view.findViewById(R.id.et_full_name);
        etMail = view.findViewById(R.id.et_email);
        etUserName = view.findViewById(R.id.et_user_name);
        etPhone = view.findViewById(R.id.et_phone_number);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnCreateAccount = view.findViewById(R.id.btn_create_account);
        TextView tvSignin = view.findViewById(R.id.tv_sign_in);
        TextView tvTerms = view.findViewById(R.id.tv_terms_privacy);
        spannableTermsAndPolicy(tvTerms);
        spannableSignin(tvSignin);
        createAccountModel = new CreateAccountModel();
    }

    private void initializeListener() {
        btnCreateAccount.setOnClickListener(v -> {
            if (etFullName.getText().toString().trim().length() < 1 || etMail.getText().toString().trim().length() < 1
                    || etUserName.getText().toString().trim().length() < 1 || etPassword.getText().toString().trim().length() < 1
                    || etConfirmPassword.getText().toString().trim().length() < 1) {
                Snackbar.make(v, Constant.EMPTY_FIELD_WARNING, Snackbar.LENGTH_LONG).show();
            } else if (!etConfirmPassword.getText().toString().trim().equals(etPassword.getText().toString().trim())) {
                Snackbar.make(v, Constant.PASSWORD_UNMATCHED_WARNING, Snackbar.LENGTH_LONG).show();
            } else if (etPassword.getText().toString().trim().length() < 6) {
                Snackbar.make(v, Constant.PASSWORD_WARNING, Snackbar.LENGTH_LONG).show();
            } else if (!Utils.isValidEmail(etMail.getText().toString().trim())) {
                Snackbar.make(v, getResources().getString(R.string.email_not_valid), Snackbar.LENGTH_LONG).show();
            } else if (etPhone.getText().toString().trim().length() < 1) {
                Snackbar.make(v, getResources().getString(R.string.text_phone_warning), Snackbar.LENGTH_LONG).show();
            } else {
                createAccount(etUserName.getText().toString().trim(), etMail.getText().toString().trim(),
                        etPassword.getText().toString().trim(), etFullName.getText().toString().trim(), etPhone.getText().toString().trim());
            }
        });

        fbAdd.setOnClickListener(v -> {
            ChooseImageSourceFragment chooseImageSourceFragment = new ChooseImageSourceFragment();
            chooseImageSourceFragment.show(getChildFragmentManager(), null);
        });
        frameCreateAccount.setOnClickListener(this::hideKeyboardFrom);
    }

    public void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openImageSource(DataObject dataObject) {
        if (dataObject.getPosition() == 1) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                openCamera();
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_FROM_GALLERY);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void completeSignup(CreateAccountObject createAccountObject) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(getActivity(), "permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE_FROM_CAMERA);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream;
                    assert imageUri != null;
                    imageStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream baos0 = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos0);
                    ivImage.setImageBitmap(selectedImage);
                    byte[] imageBytes0 = baos0.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CAPTURE_IMAGE_FROM_CAMERA && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ivImage.setImageBitmap(imageBitmap);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    assert imageBitmap != null;
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    byte[] imageBytes0 = out.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
                }
            }
        }

    }

    private void createAccount(String username, String email, String password, String fullname, String phone) {
        loginActivityInterface.showProgressDialog(true);
        createAccountModel.setEmail(email);
        createAccountModel.setPassword(password);
        createAccountModel.setFullname(fullname);
        createAccountModel.setUsername(username);
        createAccountModel.setDeviceId(Utils.getDeviceId());
        createAccountModel.setPhone(phone);

        createAccountModel.setProfileImg(encodedImage);
        ApiService apiService = new ApiCaller();
        apiService.createAccount(createAccountModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {

                VerifyAccountFragment verifyAccountFragment = new VerifyAccountFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", etMail.getText().toString().trim());
                verifyAccountFragment.setArguments(bundle);
                verifyAccountFragment.setCancelable(false);
                verifyAccountFragment.show(getChildFragmentManager(), null);
                loginActivityInterface.showProgressDialog(false);
            }

            @Override
            public void onError(Throwable th) {
                loginActivityInterface.showProgressDialog(false);
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spannableTermsAndPolicy(@NonNull TextView textView) {
        SpannableStringBuilder spanText = new SpannableStringBuilder();
        spanText.append(Constant.MSG_BY_SIGNING_UP);
        spanText.append(Constant.MSG_TERMS_AND_CONDITION);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                fragment = new WebFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean("is_privacy_policy", false);
                fragment.setArguments(bundle1);
                loadFragment(fragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(Color.WHITE);
                textPaint.setUnderlineText(true);
            }
        }, spanText.length() - Constant.MSG_TERMS_AND_CONDITION.length(), spanText.length(), 0);

        spanText.append(" and ");
        spanText.append(Constant.MSG_PRIVACY_POLICY);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                fragment = new WebFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("is_privacy_policy", true);
                fragment.setArguments(bundle);
                loadFragment(fragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(Color.WHITE);
                textPaint.setUnderlineText(true);
            }
        }, spanText.length() - Constant.MSG_PRIVACY_POLICY.length(), spanText.length(), 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanText, TextView.BufferType.SPANNABLE);

    }

    private void spannableSignin(@NonNull TextView textView) {
        SpannableStringBuilder spanText = new SpannableStringBuilder();
        spanText.append(Constant.MSG_ALREADY_ACCOUNT);
        spanText.append("Sign In!");
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();

            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.orange));
                textPaint.setUnderlineText(true);
            }
        }, spanText.length() - "Sign In!".length(), spanText.length(), 0);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanText, TextView.BufferType.SPANNABLE);

    }

    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment, fragment.getClass().getSimpleName())
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
