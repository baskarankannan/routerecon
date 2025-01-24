package com.techacsent.route_recon.fragments;

import android.Manifest;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.custom_view.RoundedCustomImageView;
import com.techacsent.route_recon.event_bus_object.DataObject;
import com.techacsent.route_recon.image_processing.CircleTransform;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.EditProfileViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    private RoundedCustomImageView ivImage;
    private ProgressBar progressBar;
    private FloatingActionButton fbAdd;
    private EditText etMail;
    private EditText etUserName;
    private EditText etFullname;
    private EditText etPhone;
    private Button btnOkay;
    private EditProfileViewModel editProfileViewModel;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private String encodedImage;
    private static final int REQUEST_SELECT_IMAGE = 100;

    private static final int REQUEST_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int REQUEST_CAPTURE_IMAGE_FROM_CAMERA = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;

    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.edit_profile_text));
        initializeView(view);
        userDetails(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), PreferenceManager.getInt(Constant.KEY_USER_ID));
        initializeListener();
    }

    private void initializeView(View view) {
        ivImage = view.findViewById(R.id.iv_image);
        progressBar = view.findViewById(R.id.progress_bar);
        fbAdd = view.findViewById(R.id.btn_add_image);
        etMail = view.findViewById(R.id.et_email);
        etUserName = view.findViewById(R.id.et_username);
        etFullname = view.findViewById(R.id.et_fullname);
        etPhone = view.findViewById(R.id.et_phone);
        btnOkay = view.findViewById(R.id.btn_ok);
        editProfileViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);
    }

    private void initializeListener() {
        btnOkay.setOnClickListener(v -> {
            if (etMail.getText().toString().trim().length() < 0 || !Utils.isValidEmail(etMail.getText().toString().trim())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();

            } else if (etUserName.getText().toString().trim().length() < 0) {
                Toast.makeText(getActivity(), getResources().getString(R.string.text_enter_user_name), Toast.LENGTH_SHORT).show();

            } else if (etFullname.getText().toString().trim().length() < 0) {
                Toast.makeText(getActivity(), getResources().getString(R.string.text_enter_full_name), Toast.LENGTH_SHORT).show();

            } else {
                editPfofile(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                        etMail.getText().toString().trim(), etUserName.getText().toString().trim(),
                        etFullname.getText().toString().trim(), etPhone.getText().toString().trim());
            }
        });
        fbAdd.setOnClickListener(v -> {
           /* Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_SELECT_IMAGE);*/
            ChooseImageSourceFragment chooseImageSourceFragment = new ChooseImageSourceFragment();
            chooseImageSourceFragment.show(getChildFragmentManager(), null);
        });
    }

    private void editPfofile(String auth, String email, String username, String fullname, String phone) {
        fragmentActivityCommunication.showProgressDialog(true);
        editProfileViewModel.getEditProfile(auth, email, username, fullname, phone, encodedImage).observe(this, editProfileResponse -> {
            if (editProfileResponse != null) {
                //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                getActivity().finish();
                fragmentActivityCommunication.showProgressDialog(false);
            } else {
                fragmentActivityCommunication.showProgressDialog(false);
            }
        });
    }

    private void userDetails(String auth, int userId) {
        fragmentActivityCommunication.showProgressDialog(true);
        editProfileViewModel.getUserDetails(auth, userId).observe(this, userDetailsResponse -> {
            if (userDetailsResponse != null) {
                etMail.setText(userDetailsResponse.getData().getEmail());
                etUserName.setText(userDetailsResponse.getData().getUsername());
                etFullname.setText(userDetailsResponse.getData().getName());
                etPhone.setText(userDetailsResponse.getData().getPhone());
                if (userDetailsResponse.getData().getProfileImg().length() > 1) {
                    Picasso.get()
                            .load(userDetailsResponse.getData().getProfileImg())
                            .placeholder(R.drawable.placeholder_image)
                            .transform(new CircleTransform())
                            .resize(200, 200)
                            .centerCrop()
                            .into(ivImage);
                } else {
                    ivImage.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_image));
                    progressBar.setVisibility(View.GONE);
                }
                fragmentActivityCommunication.showProgressDialog(false);
            } else {
                progressBar.setVisibility(View.GONE);
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
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, Constant.COMPRESSED_IMAGE_QUALITY, baos0);
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
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, Constant.COMPRESSED_IMAGE_QUALITY, out);
                    byte[] imageBytes0 = out.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
                }
            }
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

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE_FROM_CAMERA);
        }
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

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
