package com.techacsent.route_recon.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.CreateUpdateFragment;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;

import java.util.Objects;

public class CreateTripActivity extends AppCompatActivity implements NavigationOptionInterface {
    private KProgressHUD progressDialogFragment;
    private boolean isUpdate;
    private BasicTripDescription basicTripDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        loadProgressHud();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUpdate = bundle.getBoolean(Constant.KEY_IS_UPDATE);
            basicTripDescription = bundle.getParcelable("parcel");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isUpdate) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.update_trip));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.create_trip));
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadFragment();
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

    private void loadFragment() {
        Fragment fragment = new CreateUpdateFragment(); //CreateTripFragment
        Bundle bundle = new Bundle();
        if (isUpdate) {
            bundle.putParcelable("parcel", basicTripDescription);
        }
        bundle.putBoolean(Constant.KEY_IS_UPDATE, isUpdate);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void shareFragmentToolbar(boolean isShare) {

    }

    @Override
    public void tripDetailsToolbar() {

    }

    @Override
    public void navigationChooseToolbar(boolean isHide) {

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
    public void setNavigation(boolean isFromCurrentPos) {

    }

    @Override
    public void hideToolbar(boolean isHide) {
        if (isHide) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } else {
            Objects.requireNonNull(getSupportActionBar()).show();
        }
    }

    @Override
    public void shareLocation(int tripId) {
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0 || getSupportFragmentManager().getBackStackEntryCount() == 1) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0 || getSupportFragmentManager().getBackStackEntryCount() == 1) {
                this.finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogFragment = null;
    }
}
