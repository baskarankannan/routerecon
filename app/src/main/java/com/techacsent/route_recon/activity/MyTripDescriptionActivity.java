package com.techacsent.route_recon.activity;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.AddMoreObject;
import com.techacsent.route_recon.fragments.MyTripDescriptionFragment;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.MyTripsResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class MyTripDescriptionActivity extends BaseActivity implements NavigationOptionInterface {
    private String tripId;
    private String shareTripId;
    private boolean isEditable;
    private KProgressHUD progressDialogFragment;
    private MyTripsResponse.ListBean item;
    private boolean isArchived;
    private boolean isRemovable;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip_description);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.trip_details));
        //progressDialogFragment = new ProgressDialogFragment();
        loadProgressHud();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripId = bundle.getString("trip_id");
            shareTripId = bundle.getString("shared_trip_id");
            item = bundle.getParcelable("parcel");
            isEditable = bundle.getBoolean("is_editable");
            isArchived = bundle.getBoolean("is_archieved");
            isRemovable = bundle.getBoolean("is_removable");
        }
        loadInitialFragment();
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


    private void loadInitialFragment() {
        Fragment fragment = new MyTripDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", tripId);
        bundle.putString("shared_trip_id", shareTripId);
        bundle.putParcelable("parcel", item);
        bundle.putBoolean("is_editable", isEditable);
        bundle.putBoolean("is_archieved", isArchived);
        bundle.putBoolean("is_removable", isRemovable);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.description_content, fragment, fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void shareFragmentToolbar(boolean isShare) {

    }

    @Override
    public void tripDetailsToolbar() {

    }

    @Override
    public void navigationChooseToolbar(boolean isHide) {
        if(isHide){
            toolbar.getMenu().findItem(R.id.action_add_more).setVisible(false);
            toolbar.getMenu().findItem(R.id.action_cancel_trip).setVisible(false);
        }else {
            toolbar.getMenu().findItem(R.id.action_add_more).setVisible(true);
            toolbar.getMenu().findItem(R.id.action_cancel_trip).setVisible(true);
        }

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
    protected void onDestroy() {
        super.onDestroy();
        progressDialogFragment = null;
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu!=null){
            invalidateOptionsMenu();
            if (!isEditable) {
                menu.findItem(R.id.action_add_more).setVisible(false);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shared_trip_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_more:
                EventBus.getDefault().post(new AddMoreObject(false));
                break;

            case R.id.action_cancel_trip:
                EventBus.getDefault().post(new AddMoreObject(true));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
