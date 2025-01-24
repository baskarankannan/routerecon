package com.techacsent.route_recon.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.MyTripDescriptionFragment;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;

import java.util.Objects;

public class ArchivedTripActivity extends BaseActivity implements NavigationOptionInterface {

    private String tripId;
    private String shareTripId;
    private boolean isArchived;
    private boolean isRemovable;
    private boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.trip_details));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripId = bundle.getString("trip_id");
            shareTripId = bundle.getString("shared_trip_id");
            //item = bundle.getParcelable("parcel");
            isEditable = bundle.getBoolean("is_editable");
            isArchived = bundle.getBoolean("is_archieved");
            isRemovable = bundle.getBoolean("is_removable");
        }
        loadInitialFragment();
    }


    private void loadInitialFragment() {
        Fragment fragment = new MyTripDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", tripId);
        bundle.putString("shared_trip_id", shareTripId);
        /*bundle.putParcelable("parcel", item);*/
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

    }

    @Override
    public void showProgressDialog(boolean isShown) {

    }

    @Override
    public void setNavigation(boolean isFromCurrentPos) {

    }

    @Override
    public void hideToolbar(boolean isHide) {

    }

    @Override
    public void shareLocation(int tripId) {

    }
}
