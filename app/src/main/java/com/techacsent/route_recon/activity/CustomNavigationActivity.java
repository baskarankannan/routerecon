package com.techacsent.route_recon.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.NavigationChooseFragment;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;

public class CustomNavigationActivity extends BaseActivity implements FragmentActivityCommunication {
    private int tripId;
    private double latitude;
    private double longitude;
    private boolean isTripRoute;
    private KProgressHUD progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripId = bundle.getInt("trip_id");
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("lonX");
            isTripRoute = bundle.getBoolean("is_trip_route");
        }
        loadProgressHud();
        loadNavigationFragment();
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


    private void loadNavigationFragment() {
        Fragment fragment = new NavigationChooseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("trip_id", tripId);
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lonX", longitude);
        bundle.putBoolean("is_trip_route", isTripRoute);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_custom_nav_content, fragment, fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void hideBottomNav(boolean isHide) {

    }

    @Override
    public void showProgressDialog(boolean isShown) {
        if (isShown) {
            progressDialogFragment.show();
        } else {
            progressDialogFragment.dismiss();
        }

    }

    @Override
    public void fragmentToolbarbyPosition(int pos) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void showDone(boolean isShow) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogFragment = null;
    }
}
