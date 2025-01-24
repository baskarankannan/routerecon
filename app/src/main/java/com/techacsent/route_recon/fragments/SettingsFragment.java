package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.PaymentActivity;
import com.techacsent.route_recon.activity.RouteSettingsActivity;
import com.techacsent.route_recon.adapter.ItemAdapter;
import com.techacsent.route_recon.event_bus_object.DistanceUnitObject;
import com.techacsent.route_recon.event_bus_object.ElevationUnitObject;
import com.techacsent.route_recon.event_bus_object.TrafficObject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.ItemData;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsFragment extends Fragment implements OnRecyclerItemClickListener {
    private FragmentActivityCommunication fragmentActivityCommunication;
    private RecyclerView rvList;
    private ItemAdapter mAdapter;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        fragmentActivityCommunication.fragmentToolbarbyPosition(4);
        fragmentActivityCommunication.hideBottomNav(false);
    }

    private void initializeView(View view) {
        rvList = view.findViewById(R.id.rv_settings_list);
        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        ImageView ivRefresh = view.findViewById(R.id.iv_refresh);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvDone = view.findViewById(R.id.tv_done);
        tvDone.setVisibility(View.GONE);
        ivRefresh.setVisibility(View.GONE);
        ivAddUpdate.setVisibility(View.GONE);
        tvTitle.setText(getResources().getString(R.string.settings));
        rvList.invalidate();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setItemAnimator(new DefaultItemAnimator());
/*
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
*/
        List<ItemData> itemDataList = new ArrayList<>();
      /*  itemDataList.add(new ItemData("Resource", 0, false, false,true));
        itemDataList.add(new ItemData("Security Checklist", R.drawable.ic_security_checklist, false, false,false));
        itemDataList.add(new ItemData("Travel Advisories", R.drawable.ic_rss_feed, false, false,false));
        itemDataList.add(new ItemData("Track Records", R.drawable.ic_track_record_v2, false, false,false));
        itemDataList.add(new ItemData("Emergency SOS", 0, false, false,true));
        itemDataList.add(new ItemData("Emergency SOS", R.drawable.ic_sos, false, false,false));
        itemDataList.add(new ItemData("Manage Friends", 0, false, false,true));
        itemDataList.add(new ItemData("Manage Friends", R.drawable.ic_manage_friends, false, false,false));
        itemDataList.add(new ItemData("Map Settings",0, false, false,true));

        itemDataList.add(new ItemData("Elevation Unit is in : ".concat(PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER) ? "Meter" : "Feet".concat(" (click to change)")),
                R.drawable.ic_map_black_24dp, false, true,false));

        itemDataList.add(new ItemData("Coordinates",
                0, false, false,false));

        itemDataList.add(new ItemData("My Account", 0, false, false,true));
        itemDataList.add(new ItemData("Join Now", R.drawable.ic_payment_black_24dp, false, false,false));
        itemDataList.add(new ItemData("Edit Profile", R.drawable.ic_edit_profile, false, false,false));
        itemDataList.add(new ItemData("Change Password", R.drawable.ic_change_password, false, false,false));
        itemDataList.add(new ItemData("Archived Routes", R.drawable.ic_archive_trip, false, false,false));
        itemDataList.add(new ItemData("Help & Feedback", 0, false, false,true));
        itemDataList.add(new ItemData("Report Issue", R.drawable.ic_report_a_problem, false, false,false));
        itemDataList.add(new ItemData("Terms & Services", 0, false, false,true));
        itemDataList.add(new ItemData("Terms & Condition", R.drawable.ic_terms_and_conditions, false, false,false));
        itemDataList.add(new ItemData("Privacy policy", R.drawable.ic_privacy_policy, false, false,false));
        itemDataList.add(new ItemData("Log out", R.drawable.ic_logout_, false, false,false));
        itemDataList.add(new ItemData("Blocked Users", R.drawable.ic_blocked_user, false, false,false));
        itemDataList.add(new ItemData("", R.drawable.ic_show_traffic, true, false,false));
*/

        itemDataList.add(new ItemData("Resource", 0, false, false,true,false));
        itemDataList.add(new ItemData("Security Checklist", R.drawable.ic_security_checklist, false, false,false,false));
        itemDataList.add(new ItemData("Travel Advisories", R.drawable.ic_rss_feed, false, false,false,false));
        itemDataList.add(new ItemData("Track Records", R.drawable.ic_track_record_v2, false, false,false,false));
        itemDataList.add(new ItemData("Emergency SOS", 0, false, false,true,false));
        itemDataList.add(new ItemData("Emergency SOS", R.drawable.ic_sos, false, false,false,false));
        itemDataList.add(new ItemData("Manage Friends", 0, false, false,true,false));
        itemDataList.add(new ItemData("Manage Friends", R.drawable.ic_manage_friends, false, false,false,false));
        itemDataList.add(new ItemData("Map Settings",0, false, false,true,false));
        itemDataList.add(new ItemData("Units",0, false, false,false,true));

      /*  itemDataList.add(new ItemData("Elevation Unit is in : ".concat(PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER) ? "Meter" : "Feet".concat(" (click to change)")),
                R.drawable.ic_map_black_24dp, false, true,false,false));
*/
        itemDataList.add(new ItemData("Coordinates",
                0, false, false,false,false));

        itemDataList.add(new ItemData("My Account", 0, false, false,true,false));
        itemDataList.add(new ItemData("Join Now", R.drawable.ic_payment_black_24dp, false, false,false,false));
        itemDataList.add(new ItemData("Edit Profile", R.drawable.ic_edit_profile, false, false,false,false));
        itemDataList.add(new ItemData("Change Password", R.drawable.ic_change_password, false, false,false,false));
        itemDataList.add(new ItemData("Archived Routes", R.drawable.ic_archive_trip, false, false,false,false));
        itemDataList.add(new ItemData("Help & Feedback", 0, false, false,true,false));
        itemDataList.add(new ItemData("Report Issue", R.drawable.ic_report_a_problem, false, false,false,false));
        itemDataList.add(new ItemData("Terms & Services", 0, false, false,true,false));
        itemDataList.add(new ItemData("Terms & Condition", R.drawable.ic_terms_and_conditions, false, false,false,false));
        itemDataList.add(new ItemData("Privacy policy", R.drawable.ic_privacy_policy, false, false,false,false));
        itemDataList.add(new ItemData("Log out", R.drawable.ic_logout_, false, false,false,false));
        itemDataList.add(new ItemData("Blocked Users", R.drawable.ic_blocked_user, false, false,false,false));
        itemDataList.add(new ItemData("", R.drawable.ic_show_traffic, true, false,false,false));



        initAdapter(itemDataList);
    }

    private void initAdapter(List<ItemData> itemDataList) {
        mAdapter = new ItemAdapter(requireActivity().getApplicationContext(), true);
        mAdapter.setItems(itemDataList);
        mAdapter.setListener(this);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }

    @Override
    public void onItemClicked(Object item, int itemID) {

        Log.e("ItemId", "id "+ itemID);

        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), itemID + "", Toast.LENGTH_SHORT).show();
        ///when new item added in the side bar,need to check Item Id
        if (itemID == 21) {
            LogoutFragment logoutFragment = new LogoutFragment();
            logoutFragment.show(getChildFragmentManager(), null);

        } else if (itemID == -1) {

            // for show traffic in map
            EventBus.getDefault().post(new TrafficObject(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED)));
        }
        //when new item added in the side bar,need to check Item Id
        else if (itemID == 12){
            // level = join now in menu item
            gotoPaymentActivity();

        }
        /*else if (itemID == 50) {

            ///when new item added in the side bar,need to check pos value
            mAdapter.updatePosition(10, new ItemData(getString(R.string.text_in_meter), R.drawable.ic_map_black_24dp, false, true,false,false));
            PreferenceManager.updateValue(Constant.KEY_IS_ELEVATION_IN_METER, true);



        } else if (itemID == 51) {
            ///when new item added in the side bar,need to check pos value
            mAdapter.updatePosition(10, new ItemData(getString(R.string.text_in_feet), R.drawable.ic_map_black_24dp, false, true,false,false));
            PreferenceManager.updateValue(Constant.KEY_IS_ELEVATION_IN_METER, false);

        }*/

        else {
            Log.e("ItemId", "id "+ itemID);
            Intent intent = new Intent(getActivity(), RouteSettingsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("pos", itemID);
            intent.putExtras(bundle);
            requireActivity().startActivityForResult(intent, 122);
        }
    }

    private void gotoPaymentActivity() {
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void updateElevationUnit(ElevationUnitObject object) {
        PreferenceManager.updateValue(Constant.KEY_IS_ELEVATION_IN_METER, object.isMeter());
        String txt = "Elevation Unit is in : ".concat(object.isMeter() ? "Meter" : "Feet".concat(" (click to change)"));
        mAdapter.updatePosition(8, new ItemData(txt, R.drawable.ic_map_black_24dp, false, true,false,false));
        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), object.isMeter()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;

    }


}
