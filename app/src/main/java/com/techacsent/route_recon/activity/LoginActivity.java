package com.techacsent.route_recon.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.LoginFragment;
import com.techacsent.route_recon.interfaces.LoginActivityInterface;

public class LoginActivity extends BaseActivity implements LoginActivityInterface {
    private KProgressHUD progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        //progressDialogFragment = new ProgressDialogFragment();
        loadProgressHud();
        addLoginFragment();
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

    private void addLoginFragment() {
        Fragment fragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment, fragment.getClass().getSimpleName()).commit();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogFragment = null;
    }
}
