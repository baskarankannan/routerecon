package com.techacsent.route_recon.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.ArchivedFragment;
import com.techacsent.route_recon.fragments.BlockListFragment;
import com.techacsent.route_recon.fragments.CategoryListFragment;
import com.techacsent.route_recon.fragments.ChangePasswordFragment;
import com.techacsent.route_recon.fragments.EditProfileFragment;
import com.techacsent.route_recon.fragments.EmergencyFragment;
import com.techacsent.route_recon.fragments.ManageFragment;
import com.techacsent.route_recon.fragments.PolygonMapFragment;
import com.techacsent.route_recon.fragments.RSSFeedFragment;
import com.techacsent.route_recon.fragments.ReportIssueFragment;
import com.techacsent.route_recon.fragments.TermsPrivacyFragment;
import com.techacsent.route_recon.fragments.TrackedTripHistoryFragment;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.NavigationOptionTrackHistoryToolbar;
import com.techacsent.route_recon.utills.Constant;

import java.util.Objects;

public class RouteSettingsActivity extends BaseActivity implements FragmentActivityCommunication, NavigationOptionInterface, NavigationOptionTrackHistoryToolbar {
    private KProgressHUD progressDialogFragment;

    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("pos");
        }
        //progressDialogFragment = new ProgressDialogFragment();
        loadProgressHud();
        loadFragmentByPos(pos);
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

    private void loadFragmentByPos(int itemID) {
        Fragment fragment ;

        Log.e("ItemId", "id "+ itemID);
        switch (itemID) {

            case 1:
                //security checklist UI
                fragment = new CategoryListFragment();
                Bundle b = new Bundle();
                b.putBoolean("is_add_category", true);
                fragment.setArguments(b);
                loadFragment(fragment);
                break;

            case 2:
                fragment = new RSSFeedFragment();
                loadFragment(fragment);
                break;

            case 3:

                fragment = new TrackedTripHistoryFragment();

               /* getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, trackHistory, trackHistory.getClass()
                        .getSimpleName()).addToBackStack(PolygonMapFragment.class.getClass()
                        .getSimpleName())
                        .commit();*/

                loadFragment(fragment);

                break;

            case 5:
                fragment = new EmergencyFragment();
                loadFragment(fragment);
                break;

            case 7:
                fragment = new ManageFragment();
                loadFragment(fragment);
                break;

            //when new item added in the side bar, case value will be increased from here
            case 13:
                fragment = new EditProfileFragment();
                loadFragment(fragment);
                break;

            case 14:
                fragment = new ChangePasswordFragment();
                loadFragment(fragment);
                break;

            case 15:
                fragment = new ArchivedFragment();
                loadFragment(fragment);
                break;

            case 17:
                fragment = new ReportIssueFragment();
                loadFragment(fragment);
                break;

            case 19:
                fragment = new TermsPrivacyFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("is_privacy_policy", false);
                fragment.setArguments(bundle);
                loadFragment(fragment);
                break;

            case 20:
                fragment = new TermsPrivacyFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean("is_privacy_policy", true);
                fragment.setArguments(bundle1);
                loadFragment(fragment);
                break;

            case 22:
                fragment = new BlockListFragment();
                loadFragment(fragment);
                break;



            /*case 11:
                LogoutFragment logoutFragment = new LogoutFragment();
                logoutFragment.show(getSupportFragmentManager(), null);
                break;*/


        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void hideBottomNav(boolean isHide) {

    }

    @Override
    public void shareFragmentToolbar(boolean isShare) {

    }

    @Override
    public void tripDetailsToolbar() {
        getSupportActionBar().setTitle("Security Checklist");

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

    }

    @Override
    public void shareLocation(int tripId) {

    }

    @Override
    public void fragmentToolbarbyPosition(int pos) {

    }

    @Override
    public void setTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

    }

    @Override
    public void showDone(boolean isShow) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                if (Constant.IS_FRIEND_CHECKED) {
                    setBackData();
                } else {
                    this.finish();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (Constant.IS_FRIEND_CHECKED) {
                setBackData();
            } else {
                this.finish();
            }
        }
    }

    private void setBackData() {
        Intent intent = new Intent();
        intent.putExtra("flag", 2);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialogFragment = null;
    }

    @Override
    public void onToolbar(String value) {
        getSupportActionBar().setTitle(value);

    }
}
