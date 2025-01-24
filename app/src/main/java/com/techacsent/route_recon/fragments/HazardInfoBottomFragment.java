package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.TestActivity;
import com.techacsent.route_recon.event_bus_object.ChangeMapObject;
import com.techacsent.route_recon.event_bus_object.HazardNameObject;
import com.techacsent.route_recon.model.MapData;
import com.techacsent.route_recon.model.TripMarkerCreateResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class HazardInfoBottomFragment extends Fragment {

    private TextView tvHazardArea;
    private TextView tvHazardLength;

    private EditText editTextHazardName;


    private TextView tvAreaTitle;

    private  String  hazardName;

    private Button btnStreet;
    private Button btnSatellite;
    private Button btnTraffic;
    private Button btnOutdoor;
    private View  view;

    public HazardInfoBottomFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_polygon_map_bottom, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHazardArea = view.findViewById(R.id.textViewTotalArea);
        tvHazardLength = view.findViewById(R.id.textViewTotalLength);

        tvAreaTitle = view.findViewById(R.id.tvAreaTitle);


        String hazardArea = getArguments().getString("HazardArea");
        String hazardLength = getArguments().getString("HazardLength");


        if(hazardArea.isEmpty()){

            tvAreaTitle.setVisibility(View.GONE);
            tvHazardArea.setVisibility(View.GONE);

        }


        Log.e("HazbT", hazardArea);


        tvHazardArea.setText(hazardArea+ " "+ "m²");
        tvHazardLength.setText(hazardLength+ " "+ "m");

        editTextHazardName = view.findViewById(R.id.edit_text_hazard_name);

        editTextHazardName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                hazardName = editTextHazardName.getText().toString();

                EventBus.getDefault().post(new HazardNameObject(editTextHazardName.getText().toString()));



            }
        });

       /* TextView tvAddMarker = view.findViewById(R.id.tv_add_marker);
        TextView tvSyncMap = view.findViewById(R.id.tv_sync_map_data);
        ImageView ivCross = view.findViewById(R.id.iv_cross);
        tvSwipeDirection = view.findViewById(R.id.tv_swipe_direction);


        btnStreet = view.findViewById(R.id.btn_street);
        btnSatellite = view.findViewById(R.id.btn_satellite);
        btnTraffic = view.findViewById(R.id.btn_traffic);
        btnOutdoor = view.findViewById(R.id.btn_outdoor);

        resetButton();

        btnStreet.setOnClickListener(v -> {

            btnStreet.setBackgroundResource(R.drawable.next_trip_colored_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnStreet.setTextColor(getResources().getColor(R.color.white));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            EventBus.getDefault().post(new ChangeMapObject(1));

        });
        btnSatellite.setOnClickListener(v -> {

            btnSatellite.setBackgroundResource(R.drawable.pending_selected);
            btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnSatellite.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));

            EventBus.getDefault().post(new ChangeMapObject(2));


        });
        btnTraffic.setOnClickListener(v -> {

            btnTraffic.setBackgroundResource(R.drawable.pending_selected);
            btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnTraffic.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));

            EventBus.getDefault().post(new ChangeMapObject(3));

        });
        btnOutdoor.setOnClickListener(v -> {

            btnOutdoor.setBackgroundResource(R.drawable.last_trip_colored_bg);
            btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
            btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
            btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
            btnOutdoor.setTextColor(getResources().getColor(R.color.white));
            btnStreet.setTextColor(getResources().getColor(R.color.orange));
            btnSatellite.setTextColor(getResources().getColor(R.color.orange));
            btnTraffic.setTextColor(getResources().getColor(R.color.orange));

            EventBus.getDefault().post(new ChangeMapObject(4));

        });

        tvAddMarker.setOnClickListener(v -> {

        });

        tvSyncMap.setOnClickListener(v -> {
            SyncWithInternetFragment syncWithInternetFragment = new SyncWithInternetFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_in_trip", false);
            syncWithInternetFragment.setArguments(bundle);
            syncWithInternetFragment.show(getChildFragmentManager(), null);
        });

        //tvSyncMap.setOnClickListener(v -> EventBus.getDefault().post(new SyncMarkerObject(true)));

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChangeMapObject(5));
            }
        });*/

    }

    /*private void resetButton(){
        switch (PreferenceManager.getInt(Constant.KEY_MAP_STYLE_ID)){
            case 0: btnStreet.setBackgroundResource(R.drawable.next_trip_colored_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnStreet.setTextColor(getResources().getColor(R.color.white));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 1:
                btnSatellite.setBackgroundResource(R.drawable.pending_selected);
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnSatellite.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 2:
                btnTraffic.setBackgroundResource(R.drawable.pending_selected);
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_bg);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnTraffic.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnOutdoor.setTextColor(getResources().getColor(R.color.orange));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                break;

            case 3:
                btnOutdoor.setBackgroundResource(R.drawable.last_trip_colored_bg);
                btnStreet.setBackgroundResource(R.drawable.next_trip_bg);
                btnSatellite.setBackgroundResource(R.drawable.pending_unselected);
                btnTraffic.setBackgroundResource(R.drawable.pending_unselected);
                btnOutdoor.setTextColor(getResources().getColor(R.color.white));
                btnStreet.setTextColor(getResources().getColor(R.color.orange));
                btnSatellite.setTextColor(getResources().getColor(R.color.orange));
                btnTraffic.setTextColor(getResources().getColor(R.color.orange));
                break;

        }
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeButtonState(MapData data){
        //resetButton();

    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void setStateOpen(String stateOpen) {
        if (stateOpen.equals("up")) {
            tvSwipeDirection.setText(getResources().getString(R.string.text_swipe_down));
        } else {
            tvSwipeDirection.setText(getResources().getString(R.string.text_swipe_up));
        }
    }*/

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
    public void onHiddenChanged(boolean hidden) {

      /*  super.onHiddenChanged(hidden);
        if(!isHidden()){
            btnStreet = view.findViewById(R.id.btn_street);
            btnSatellite = view.findViewById(R.id.btn_satellite);
            btnTraffic = view.findViewById(R.id.btn_traffic);
            btnOutdoor = view.findViewById(R.id.btn_outdoor);

            resetButton();
        }*/
    }
}
