package com.techacsent.route_recon.activity;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.LandmarkImageAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.DataObject;
import com.techacsent.route_recon.fragments.ChooseImageSourceFragment;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.edithazrdpin.EditHazardPinModel;
import com.techacsent.route_recon.model.Image;
import com.techacsent.route_recon.model.MarkerDetailResponse;
import com.techacsent.route_recon.model.PolygonPinIImageUploadModel;
import com.techacsent.route_recon.model.request_body_model.CreateLandmarkModel;
import com.techacsent.route_recon.model.request_body_model.EditMarkerModel;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.LandmarkViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LandmarkActivity extends BaseActivity implements OnRecyclerClickListener<MarkerDetailResponse.LandmarkImageBean> {
    private ImageView ivUpdate;
    private EditText etName;
    private EditText etAddress;
    private EditText etDetails;
    private TextView tvAddPhoto;
    private TextView tvDetailTitle;
    private EditText etRadius;
    private RecyclerView rvLandmarkImage;
    private Button btnDelete;
    private LandmarkImageAdapter mAdapter;
    private LinearLayout layoutRadius;
    private List<MarkerDetailResponse.LandmarkImageBean> landmarkImageBeanList;

    private int markerType;
    private double latitude;
    private double longitude;
    private KProgressHUD progressDialogFragment;
    private String markerId; //id to get marker details
    private String id; //id to get marker or pin details
    private String pointerId; //id to get marker or pin details
    private long absoluteMarker;
    private boolean isTripMarker;
    private boolean isPolygonMarker;
    private CreateLandmarkModel createLandmarkModel;
    private PolygonPinIImageUploadModel polygonPinIImageUploadModel;
    private MarkerDetailResponse.LandmarkImageBean landmarkImageBean;
    private boolean isEdit = false;
    private static final int REQUEST_SELECT_IMAGE_FROM_GALLERY = 100;
    private static final int REQUEST_CAPTURE_IMAGE_FROM_CAMERA = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;
    private InputMethodManager mInputMethodManager;
    private MarkerDescription markerDescription;

    private LandmarkViewModel landmarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializView();

        landmarkViewModel = ViewModelProviders.of(this).get(LandmarkViewModel.class);
        createLandmarkModel = new CreateLandmarkModel();
        polygonPinIImageUploadModel = new PolygonPinIImageUploadModel();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("marker_id");
            absoluteMarker = bundle.getLong("long_marker");
            isTripMarker = bundle.getBoolean("is_marker_from_trip");
            isPolygonMarker = bundle.getBoolean("is_marker_from_polygon_pin");

            if (isPolygonMarker) {

                String value[] = id.split("-");

                Log.e("LandMarkActvity", "MarkerId " + value[0]);
                Log.e("LandMarkActvity", "PinId " + value[1]);
                markerId = value[0];
                pointerId = value[1];
                markerDescription = AppDatabase.getAppDatabase(this).daoMarker().fetchMarkerByMarkerId(markerId);

                getPolygonPinDetails();

            } else {
                markerId = id;
                markerDescription = AppDatabase.getAppDatabase(this).daoMarker().fetchMarkerByMarkerId(markerId);
                getMarkerDetails();

            }

        }

        etRadius.setText(String.valueOf(markerDescription.getRadius()));
        if (markerDescription.getMarkType() != 3) {
            layoutRadius.setVisibility(View.GONE);
        }

        Log.e("marker_idT", " id " + markerId);


        initializeListener();
    }

    private void initializView() {
        ImageView ivShare = findViewById(R.id.iv_share_trip);
        ivShare.setVisibility(View.GONE);
        TextView tvTitle = findViewById(R.id.tv_title);

/*
        tvTitle.setText(getResources().getString(R.string.landmark_image));
*/
        tvTitle.setText(getResources().getString(R.string.mark_details));
        ivUpdate = findViewById(R.id.iv_update_trip);
        etName = findViewById(R.id.et_marker_name);
        etAddress = findViewById(R.id.et_marker_address);
        etDetails = findViewById(R.id.et_marker_full_address);
        tvDetailTitle = findViewById(R.id.tvDetails);
        layoutRadius = findViewById(R.id.layout_radius);
        etRadius = findViewById(R.id.et_marker_radius);
        tvAddPhoto = findViewById(R.id.tv_add_photo);
        rvLandmarkImage = findViewById(R.id.rv_landmark_image);
        btnDelete = findViewById(R.id.btn_delete);
        //progressDialogFragment = new ProgressDialogFragment();
        loadProgressHud();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvLandmarkImage.invalidate();
        rvLandmarkImage.setLayoutManager(layoutManager);
        rvLandmarkImage.setItemAnimator(new DefaultItemAnimator());


    }

    private void loadProgressHud() {
        progressDialogFragment = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setDimAmount(0.5f)
                .setLabel("Loading...");
    }

    private void initializeListener() {

        tvAddPhoto.setOnClickListener(v -> {
            ChooseImageSourceFragment chooseImageSourceFragment = new ChooseImageSourceFragment();
            chooseImageSourceFragment.show(getSupportFragmentManager(), null);
        });

        ivUpdate.setOnClickListener(v -> {
            if (!isEdit) {
                etAddress.setEnabled(true);
                etDetails.setEnabled(true);
                etRadius.setEnabled(true);
                etName.setEnabled(true);
                ivUpdate.setImageResource(R.drawable.ic_done_white_24dp);
                isEdit = true;
                etName.requestFocus();
                mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.showSoftInput(etName, InputMethodManager.SHOW_FORCED);
                etName.selectAll();
            } else {
                isEdit = false;

                if (isPolygonMarker) {

                    editHazardPin();

                } else {
                    editMarker();
                }
            }

        });
        btnDelete.setOnClickListener(v -> deleteMarker(markerId));
    }

    private void scrollToBottom() {
        rvLandmarkImage.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void getMarkerDetails() {
        markerType = markerDescription.getMarkType();
        latitude = markerDescription.getLat();
        longitude = markerDescription.getLongX();
        if (markerDescription.getAddress() != null) {

            etName.setText("");

        } else {
            etName.setText(markerDescription.getAddress());
        }
        etAddress.setText(markerDescription.getFullAddress());
        etDetails.setText(markerDescription.getDescription());

        landmarkViewModel.getMarkerDescription(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                markerId).observe(this, markerDetailResponse -> {
            if (markerDetailResponse != null) {
                if (markerDetailResponse.getLandmarkImage() != null && markerDetailResponse.getLandmarkImage().size() > 0) {
                    landmarkImageBeanList = markerDetailResponse.getLandmarkImage();
                    initializeAdapter();
                }
            }
        });
    }


    //Hazard
    private void getPolygonPinDetails() {

        markerType = markerDescription.getMarkType();
        Log.e("LandMacrAct", " MarkerType " + markerType);
        latitude = markerDescription.getLat();
        longitude = markerDescription.getLongX();

        /* etName.setText(markerDescription.getAddress());
        etAddress.setText(markerDescription.getFullAddress());
        etDetails.setText(markerDescription.getDescription());*/

        landmarkViewModel.getPolygonPinDetails(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                pointerId).observe(this, pinDetailResponse -> {
            if (pinDetailResponse != null) {

                Log.e("LandMarkActT", pinDetailResponse.toString());


                latitude =pinDetailResponse.getLat();
                longitude =pinDetailResponse.getLong();

                etName.setText(pinDetailResponse.getName());
                etAddress.setText(pinDetailResponse.getAddress());
                etDetails.setVisibility(View.GONE);
                tvDetailTitle.setVisibility(View.GONE);

                if (pinDetailResponse.getImages() != null && pinDetailResponse.getImages().size() > 0) {

                    landmarkImageBeanList = new ArrayList<>();

                    for (Image image : pinDetailResponse.getImages()) {

                        landmarkImageBean = new MarkerDetailResponse.LandmarkImageBean();

                        landmarkImageBean.setId(image.getId());
                        landmarkImageBean.setImageUrl(image.getImageUrl());
                        landmarkImageBean.setDescription(image.getDescription());


                        landmarkImageBeanList.add(landmarkImageBean);


                    }
                    //landmarkImageBeanList = pinDetailResponse.getImages() ;
                    initializeAdapter();
                }
            }
        });
    }

    private void uploadLandmarkImage(String encodedLandmarkImage) {
        progressDialogFragment.show();

        if (isPolygonMarker) {


            polygonPinIImageUploadModel.setPointer_id(Integer.parseInt(pointerId));
            polygonPinIImageUploadModel.setMark_id(0);
            polygonPinIImageUploadModel.setImage(encodedLandmarkImage);
            polygonPinIImageUploadModel.setDescription("");

            landmarkViewModel.callUploadPolygonPinImage(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), polygonPinIImageUploadModel).observe(this, landmarkCreateResponse -> {

                Log.e("LandMarkActt", "data " + landmarkCreateResponse.toString());

                if (landmarkCreateResponse != null) {

                    Log.e("LandMarkActt", "Image Url " + landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getImageUrl());


                    landmarkImageBean = new MarkerDetailResponse.LandmarkImageBean();
                    landmarkImageBean.setId(landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getId());
                    landmarkImageBean.setImageUrl(landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getImageUrl());
                    landmarkImageBean.setDescription(landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getDescription());
                    progressDialogFragment.dismiss();
                    if (mAdapter != null) {
                        mAdapter.add(landmarkImageBean);
                        progressDialogFragment.dismiss();
                        scrollToBottom();
                    } else {
                        progressDialogFragment.dismiss();
                        landmarkImageBeanList = new ArrayList<>();
                        landmarkImageBeanList.add(landmarkImageBean);
                        initializeAdapter();
                        scrollToBottom();
                    }
                } else {
                    progressDialogFragment.dismiss();
                }

            });
        } else {

            createLandmarkModel.setMarkId(markerId);
            createLandmarkModel.setImage(encodedLandmarkImage);
            createLandmarkModel.setDescription("Lorem Ipsum");
            landmarkViewModel.callUploadLandmarkImage(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createLandmarkModel).observe(this, landmarkCreateResponse -> {
                if (landmarkCreateResponse != null) {
                    landmarkImageBean = new MarkerDetailResponse.LandmarkImageBean();
                    landmarkImageBean.setId(landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getId());
                    landmarkImageBean.setImageUrl(landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getImageUrl());
                    landmarkImageBean.setDescription(landmarkCreateResponse.getSuccess().getLandmarkImage().get(0).getDescription());
                    progressDialogFragment.dismiss();
                    if (mAdapter != null) {
                        mAdapter.add(landmarkImageBean);
                        progressDialogFragment.dismiss();
                        scrollToBottom();
                    } else {
                        progressDialogFragment.dismiss();
                        landmarkImageBeanList = new ArrayList<>();
                        landmarkImageBeanList.add(landmarkImageBean);
                        initializeAdapter();
                        scrollToBottom();
                    }
                } else {
                    progressDialogFragment.dismiss();
                }

            });

        }
    }

    private void deleteMarker(String markerId) {
        progressDialogFragment.show();


        if(isPolygonMarker) {



            landmarkViewModel.deleteHazardPin(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), Integer.parseInt(pointerId)).observe(this, deleteHazardPinResponse -> {

                progressDialogFragment.dismiss();


                if (deleteHazardPinResponse != null) {

                        setBackData(false);

                } else {
                    progressDialogFragment.dismiss();
                }
            });



        }else {

            landmarkViewModel.getDeleteMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), markerId).observe(this, successArray -> {
                if (successArray != null) {
                    if (isTripMarker) {
                        deleteMarkerFromLocalDB();
                        progressDialogFragment.dismiss();
                        setBackData(false);
                    } else {
                        deleteMarkerFromLocalDB();
                        setBackData(false);
                    }
                    progressDialogFragment.dismiss();
                } else {
                    progressDialogFragment.dismiss();
                }
            });
        }
    }

    private void deleteMarkerFromLocalDB() {
        MarkerDescription markerDescription = AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().fetchMarkerByMarkerId(markerId);
        AppDatabase.getAppDatabase(getApplicationContext()).daoMarker().deleteMarker(markerDescription);
    }

    private void editMarker() {
        progressDialogFragment.show();
        EditMarkerModel editMarkerModel = new EditMarkerModel();
        editMarkerModel.setMarkerId(Integer.parseInt(markerId));
        if (markerType != 3) {
            editMarkerModel.setRadius(0);
        } else {
            editMarkerModel.setRadius(Double.parseDouble(etRadius.getText().toString().trim()));
        }
        editMarkerModel.setAddress(etName.getText().toString().trim());
        editMarkerModel.setFullAddress(etAddress.getText().toString().trim());
        editMarkerModel.setDescription(etDetails.getText().toString().trim());
        landmarkViewModel.getEditMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), editMarkerModel).observe(this, successArray -> {
            if (successArray != null) {
                //EventBus.getDefault().post(new MarkerObj(etName.getText().toString().trim(), etAddress.getText().toString().trim()));
                markerDescription.setAddress(etName.getText().toString().trim());
                markerDescription.setFullAddress(etAddress.getText().toString().trim());
                markerDescription.setDescription(etDetails.getText().toString().trim());
                markerDescription.setRadius(Double.parseDouble(etRadius.getText().toString().trim()));
                AppDatabase.getAppDatabase(LandmarkActivity.this).daoMarker().updateMarker(markerDescription);
                //LandmarkActivity.this.finish();
                progressDialogFragment.dismiss();
                setBackData(true);
            } else {
                progressDialogFragment.dismiss();
            }

        });
    }


    private void editHazardPin() {
        progressDialogFragment.show();

       EditHazardPinModel editHazardPinModel = new EditHazardPinModel(
                Integer.parseInt(pointerId),
                String.valueOf(latitude),
                String.valueOf(longitude),
                etAddress.getText().toString().trim(),
                etName.getText().toString().trim());


        landmarkViewModel.editHazardPinSubmit(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), editHazardPinModel).observe(this, editHazardPinResponse -> {
            if (editHazardPinResponse != null) {
                //EventBus.getDefault().post(new MarkerObj(etName.getText().toString().trim(), etAddress.getText().toString().trim()));

                Log.e("EditHazarPin", "Success");

                markerDescription.setAddress(etName.getText().toString().trim());
                markerDescription.setFullAddress(etAddress.getText().toString().trim());
                markerDescription.setDescription(etDetails.getText().toString().trim());
                markerDescription.setRadius(Double.parseDouble(etRadius.getText().toString().trim()));

                AppDatabase.getAppDatabase(LandmarkActivity.this).daoMarker().updateMarker(markerDescription);
                //LandmarkActivity.this.finish();
                setBackData(true);

                progressDialogFragment.dismiss();


            } else {
                progressDialogFragment.dismiss();
            }

        });
    }

    private void initializeAdapter() {
        mAdapter = new LandmarkImageAdapter(LandmarkActivity.this);
        mAdapter.setItems(landmarkImageBeanList);
        mAdapter.setListener(this);
        rvLandmarkImage.setAdapter(mAdapter);
    }

    private void setBackData(boolean isEdit) {

        Intent intent = new Intent();
        intent.putExtra("is_edit", isEdit);
        intent.putExtra("long_marker", absoluteMarker);
        intent.putExtra("marker_type", markerType);
        intent.putExtra("server_marker_id", markerId);
        intent.putExtra("lat", latitude);
        intent.putExtra("lon", longitude);

        if(isPolygonMarker){

            intent.putExtra("isPolygonMarkerDelete", true);

        }else{
            intent.putExtra("isPolygonMarkerDelete", false);

        }
        intent.putExtra("address", etName.getText().toString().trim());

        if (markerType == 3) {
            intent.putExtra("radius", Double.parseDouble(etRadius.getText().toString().trim()));

        }
        setResult(RESULT_OK, intent);
        progressDialogFragment.dismiss();
        finish();
    }

    private void deleteLandmarkImage(MarkerDetailResponse.LandmarkImageBean landmarkImageBean) {

        if(isPolygonMarker){

            landmarkViewModel.callDeleteHazardPinImage(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                    landmarkImageBean.getId()).observe(this, deleteHazardImageResponse -> {
                if (deleteHazardImageResponse != null) {
                    mAdapter.remove(landmarkImageBean);
                }
            });


        }else {

            landmarkViewModel.callDeleteImage(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                    landmarkImageBean.getId()).observe(this, successArray -> {
                if (successArray != null) {
                    mAdapter.remove(landmarkImageBean);
                }
            });
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE_FROM_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String encodedImage;
        if (requestCode == REQUEST_SELECT_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream;
                    assert imageUri != null;
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream baos0 = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, Constant.COMPRESSED_IMAGE_QUALITY, baos0);
                    byte[] imageBytes0 = baos0.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);

                    uploadLandmarkImage(encodedImage);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CAPTURE_IMAGE_FROM_CAMERA && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    assert imageBitmap != null;
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, Constant.COMPRESSED_IMAGE_QUALITY, out);
                    byte[] imageBytes0 = out.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
                    uploadLandmarkImage(encodedImage);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openImageSource(DataObject dataObject) {
        if (dataObject.getPosition() == 1) {
            openCameraByCheckingPermission();

        } else {

            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_FROM_GALLERY);
        }
    }

    private void openCameraByCheckingPermission() {
        Permissions.check(this, Manifest.permission.CAMERA, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                openCamera();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), "permission Denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onBlocked(Context context, ArrayList<String> blockedList) {
                return super.onBlocked(context, blockedList);
            }

            @Override
            public void onJustBlocked(Context context, ArrayList<String> justBlockedList, ArrayList<String> deniedPermissions) {
                super.onJustBlocked(context, justBlockedList, deniedPermissions);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        try {
            mInputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(MarkerDetailResponse.LandmarkImageBean item) {
        deleteLandmarkImage(item);
    }
}
