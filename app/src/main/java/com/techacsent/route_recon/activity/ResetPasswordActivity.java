package com.techacsent.route_recon.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.ResetPasswordFragment;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;

import java.util.Objects;

public class ResetPasswordActivity extends BaseActivity implements FragmentActivityCommunication {

    private KProgressHUD progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //progressDialogFragment = new ProgressDialogFragment();
        loadProgressHud();
        Fragment fragment = new ResetPasswordFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_content, fragment, fragment.getClass().getSimpleName()).commit();
    }

    private void loadProgressHud(){
        progressDialogFragment = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setDimAmount(0.5f)
                .setLabel("Loading...");
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
