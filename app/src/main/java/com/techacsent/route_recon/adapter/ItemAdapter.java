package com.techacsent.route_recon.adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.event_bus_object.CoordinateUnitObject;
import com.techacsent.route_recon.event_bus_object.DistanceUnitObject;
import com.techacsent.route_recon.event_bus_object.TrafficObject;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.ItemData;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;


import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class ItemAdapter extends BaseRecyclerAdapter<ItemData,
        OnRecyclerItemClickListener<ItemData>, BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>>> {
    private static boolean isShowBadge;

    private static final int SETTING_TRAFFIC = 1;
    private static final int SETTING_OTHER = 2;
    private static final int SETTINGS_UNIT = 3;
    private static final int LABEL_TEXT = 4;
    private static final int SETTING_UNIT_DISTANCE = 5;

    public ItemAdapter(Context context, boolean isShowBadge) {
        super(context);
        ItemAdapter.isShowBadge = isShowBadge;
    }

    @Override
    public int getItemViewType(int position) {

        if (items.get(position).isLabel()) {
            return LABEL_TEXT;
        } else if (items.get(position).isTraffic()) {
            return SETTING_TRAFFIC;
        } else if (items.get(position).isDistanceUnit()) {

            return SETTING_UNIT_DISTANCE;
        } else {
            if (items.get(position).isElevationUnitInMeter()) {
                return SETTINGS_UNIT;
            } else {
                return SETTING_OTHER;
            }
        }
    }

    @NonNull
    @Override
    public BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == LABEL_TEXT) {
            return new LabelViewHolder(inflate(R.layout.label_item, viewGroup));

        } else if (i == SETTING_OTHER) {
            return new ItemViewHolder(inflate(R.layout.settings_item, viewGroup));
        } else if (i == SETTING_TRAFFIC) {
            return new TrafficSelectorViewHolder(inflate(R.layout.layout_traffic_selector, viewGroup));
        }
        /*else if (i == SETTING_UNIT_DISTANCE) {
            return new UnitDistanceSelectorViewHolder(inflate(R.layout.item_distance_unit_selector, viewGroup));
        }*/

        else{
            return new UnitDistanceSelectorViewHolder(inflate(R.layout.item_distance_unit_selector, viewGroup));
        }

        /* else {
            return new UnitSelectorViewHolder(inflate(R.layout.item_unit_selector, viewGroup));
        }*/
    }

    public static class ItemViewHolder extends BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>> {
        TextView tvName;
        TextView tvBadge;
        FrameLayout content;
        ;
        Button buttonDecimal;
        Button buttonUTM;
        Button buttonDMS;

        ItemViewHolder(View itemView) {
            super(itemView);
            buttonDecimal = itemView.findViewById(R.id.buttonDecimal);
            buttonUTM = itemView.findViewById(R.id.buttonUTM);
            buttonDMS = itemView.findViewById(R.id.buttonDMS);
            tvName = itemView.findViewById(R.id.tv_item);
            tvBadge = itemView.findViewById(R.id.badge_text_view);
            content = itemView.findViewById(R.id.content);
        }

        @Override
        public void onBind(ItemData item, @Nullable OnRecyclerItemClickListener<ItemData> listener) {

            if (getAdapterPosition() == 13) {
                // make join now level  color = red
                tvName.setTextColor(Color.RED);
            }


            if (getAdapterPosition() == 10) {
                // for coordinates showing Unit button

                buttonDecimal.setVisibility(View.VISIBLE);
                buttonUTM.setVisibility(View.VISIBLE);
                buttonDMS.setVisibility(View.VISIBLE);

                if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals(
                        "decimal")) {


                    buttonDecimal.setTextColor(Color.parseColor("#ff5252"));
                    buttonDMS.setTextColor(Color.parseColor("#000000"));
                    buttonUTM.setTextColor(Color.parseColor("#000000"));

                } else if (PreferenceManager.getString(Constant.KEY_UNIT_COORDINATE).equals(
                        "dms")) {


                    buttonDMS.setTextColor(Color.parseColor("#ff5252"));
                    buttonDecimal.setTextColor(Color.parseColor("#000000"));
                    buttonUTM.setTextColor(Color.parseColor("#000000"));

                } else {

                    buttonUTM.setTextColor(Color.parseColor("#ff5252"));
                    buttonDMS.setTextColor(Color.parseColor("#000000"));
                    buttonDecimal.setTextColor(Color.parseColor("#000000"));

                }

            }

            tvName.setText(item.getTitle());
            tvName.setCompoundDrawablesWithIntrinsicBounds(item.getDrawable(), 0, 0, 0);
            if (isShowBadge) {
                if (getAdapterPosition() == 0) {
                    if (PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT) >= 1) {
                        Timber.d(String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
                        tvBadge.setText(String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
                        tvBadge.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (getAdapterPosition() == 1) {
                    if (PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT) >= 1) {
                        tvBadge.setText(String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
                        tvBadge.setVisibility(View.VISIBLE);
                    }
                }
            }

            content.setOnClickListener(v -> {
                if (listener != null) {
                    if (getAdapterPosition() == 10) {

                        //Log.e("menu item", "coordinates clicked");


                    } else {
                        listener.onItemClicked(item, getAdapterPosition());
                    }
                }
            });

            buttonDecimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PreferenceManager.updateValue(Constant.KEY_UNIT_COORDINATE, "decimal");

                    EventBus.getDefault().post(new CoordinateUnitObject("decimal"));

                    buttonDecimal.setTextColor(Color.parseColor("#ff5252"));
                    buttonDMS.setTextColor(Color.parseColor("#000000"));
                    buttonUTM.setTextColor(Color.parseColor("#000000"));


                }
            });

            buttonUTM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PreferenceManager.updateValue(Constant.KEY_UNIT_COORDINATE, "utm");


                    EventBus.getDefault().post(new CoordinateUnitObject("utm"));
                    buttonDMS.setTextColor(Color.parseColor("#000000"));
                    buttonDecimal.setTextColor(Color.parseColor("#000000"));
                    buttonUTM.setTextColor(Color.parseColor("#ff5252"));


                    Log.e("button", "UTM clicked");
                }
            });

            buttonDMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PreferenceManager.updateValue(Constant.KEY_UNIT_COORDINATE, "dms");


                    EventBus.getDefault().post(new CoordinateUnitObject("dms"));

                    buttonDMS.setTextColor(Color.parseColor("#ff5252"));
                    buttonDecimal.setTextColor(Color.parseColor("#000000"));
                    buttonUTM.setTextColor(Color.parseColor("#000000"));


                    Log.e("button", "DMS clicked");
                }
            });
        }
    }

    public static class TrafficSelectorViewHolder extends BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>> {

        CheckBox chSelect;



        TrafficSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            chSelect = itemView.findViewById(R.id.chk_select);
        }

        @Override
        public void onBind(ItemData item, @Nullable OnRecyclerItemClickListener<ItemData> listener) {

            chSelect.setChecked(PreferenceManager.getBool(Constant.KEY_IS_TRAFFIC_SELECTED));
            chSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onItemClicked(item, -1);
                    PreferenceManager.updateValue(Constant.KEY_IS_TRAFFIC_SELECTED, isChecked);
                }
            });

        }
    }

   /* public static class UnitSelectorViewHolder extends BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>> {

        private Switch swUnit;

        LinearLayout linearLayout;

        UnitSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.contentElevationMeter);
            swUnit = itemView.findViewById(R.id.switch_status);
        }

        @Override
        public void onBind(ItemData item, @Nullable OnRecyclerItemClickListener<ItemData> listener) {


          //  linearLayout.setVisibility(View.GONE);
            if (PreferenceManager.getBool(Constant.KEY_IS_ELEVATION_IN_METER)) {
                swUnit.setText(R.string.text_in_meter);
                swUnit.setChecked(true);

            } else {
                swUnit.setText(R.string.text_in_feet);
                swUnit.setChecked(false);

            }

          //  swUnit.setVisibility(View.GONE);


            if (listener != null) {
                //swUnit.setOnClickListener(v -> listener.onItemClicked(item, getAdapterPosition()));
                swUnit.setOnClickListener(v -> {
                    if (swUnit.isChecked()) {


                        listener.onItemClicked(item, 50);
                    } else {
                        listener.onItemClicked(item, 51);
                    }

                });
            }

        }
    }*/

    public static class LabelViewHolder extends BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>> {

        private TextView lableTextView;

        LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            lableTextView = itemView.findViewById(R.id.setting_label);
        }

        @Override
        public void onBind(ItemData item, @Nullable OnRecyclerItemClickListener<ItemData> listener) {
            lableTextView.setText(item.getTitle());

        }
    }


    public static class UnitDistanceSelectorViewHolder extends BaseViewHolder<ItemData, OnRecyclerItemClickListener<ItemData>> {

        Button buttonImperial;
        Button buttonMetric;

        UnitDistanceSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonImperial = itemView.findViewById(R.id.buttonImperial);
            buttonMetric = itemView.findViewById(R.id.buttonMetric);
        }

        @Override
        public void onBind(ItemData item, @Nullable OnRecyclerItemClickListener<ItemData> listener) {


            if (PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("")
                    || PreferenceManager.getString(Constant.KEY_DISTANCE_UNIT).equals("metric")
            ) {

                buttonMetric.setTextColor(Color.parseColor("#ff5252"));
                buttonImperial.setTextColor(Color.parseColor("#000000"));

            } else {

                buttonImperial.setTextColor(Color.parseColor("#ff5252"));
                buttonMetric.setTextColor(Color.parseColor("#000000"));

            }

            buttonImperial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    PreferenceManager.updateValue(Constant.KEY_DISTANCE_UNIT, "imperial");


                    EventBus.getDefault().post(new DistanceUnitObject(true));

                    buttonImperial.setTextColor(Color.parseColor("#ff5252"));
                    buttonMetric.setTextColor(Color.parseColor("#000000"));

                    PreferenceManager.updateValue(Constant.KEY_IS_ELEVATION_IN_METER, false);



                }
            });

            buttonMetric.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PreferenceManager.updateValue(Constant.KEY_DISTANCE_UNIT, "metric");
                    PreferenceManager.updateValue(Constant.KEY_IS_ELEVATION_IN_METER, true);


                    EventBus.getDefault().post(new DistanceUnitObject(false));

                    buttonMetric.setTextColor(Color.parseColor("#ff5252"));
                    buttonImperial.setTextColor(Color.parseColor("#000000"));

                }
            });

        }
    }
}
