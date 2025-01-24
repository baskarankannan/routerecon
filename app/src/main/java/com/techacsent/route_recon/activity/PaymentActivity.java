package com.techacsent.route_recon.activity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.PackageFragment;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;

public class PaymentActivity extends BaseActivity implements FragmentActivityCommunication {

    private KProgressHUD progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadProgressHud();

        PackageFragment packageFragment = new PackageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_parent, packageFragment).commit();

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

    @Override
    public void hideBottomNav(boolean isHide) {


    }

    @Override
    public void showProgressDialog(boolean isShown) {
        if(isShown){
            progressDialogFragment.show();
        }else {
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
}
