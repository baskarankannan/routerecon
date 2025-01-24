package com.techacsent.route_recon.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.ShareLiveLocationFragment;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;

import java.util.Objects;

public class TrackerActivity extends BaseActivity implements FragmentActivityCommunication {
    private KProgressHUD progressDialogFragment;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            isEdit = b.getBoolean("is_edit");
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (!isEdit) {
            getSupportActionBar().setTitle(getResources().getString(R.string.text_share_location));
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.text_edit_share_location));
        }
        loadProgressHud();
        Fragment fragment = new ShareLiveLocationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_edit", isEdit);
        fragment.setArguments(bundle);
        loadloadFraFragment(fragment);

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


    private void loadloadFraFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().add(R.id.tracker_content, fragment, fragment.getClass().getSimpleName())
                .commit();
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

        getSupportActionBar().setTitle(title);

    }

    @Override
    public void showDone(boolean isShow) {

    }
}
