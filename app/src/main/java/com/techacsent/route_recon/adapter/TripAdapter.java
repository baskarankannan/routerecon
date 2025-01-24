package com.techacsent.route_recon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripAdapter extends BaseRecyclerAdapter<BasicTripDescription,
        OnRecyclerItemClickListener<BasicTripDescription>, TripAdapter.TripListViewHolder> {

    public TripAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public TripListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TripListViewHolder(inflate(R.layout.trip_list_row, viewGroup));
    }

    class TripListViewHolder extends BaseViewHolder<BasicTripDescription, OnRecyclerItemClickListener<BasicTripDescription>> {
        TextView tvTripName;
        TextView tvSharedWith;
        TextView tvTripRoute;
        TextView tvTripDate;
        TextView tvTripTime;
        ImageView ivDelete;
        LinearLayout layoutContent;

        TripListViewHolder(View itemView) {
            super(itemView);
            tvTripName = itemView.findViewById(R.id.tv_trip_name);
            tvSharedWith = itemView.findViewById(R.id.tv_trip_share_number);
            tvTripRoute = itemView.findViewById(R.id.tv_trip_route);
            tvTripDate = itemView.findViewById(R.id.tv_trip_date);
            tvTripTime = itemView.findViewById(R.id.tv_trip_time);
            ivDelete = itemView.findViewById(R.id.iv_delete_trip);
            layoutContent = itemView.findViewById(R.id.trip_content);
        }

        @Override
        public void onBind(BasicTripDescription tripListObject, @Nullable OnRecyclerItemClickListener<BasicTripDescription> listener) {
            tvTripName.setText(tripListObject.getTripName());
            tvTripRoute.setText(tripListObject.getStartPointAddress());
            tvSharedWith.setText(tripListObject.getFriendShared()+"");
            parseDate(tripListObject);
            layoutContent.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(tripListObject, 0);
                }
            });

            ivDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(tripListObject, 1);
                }
            });

        }

        @SuppressLint("SimpleDateFormat")
        private void parseDate(BasicTripDescription tripBean) {
            String startTimeDate = tripBean.getBeginTime();
            if (tripBean.getBeginTime().equals(Constant.DEFAULT_DATE)) {
                tvTripDate.setText(R.string.not_applicable);
                tvTripTime.setText(R.string.not_applicable);
            } else {
                try {
                    Date startTimeAndDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(startTimeDate);
                    String startTime = new SimpleDateFormat(Constant.TIME_FORMAT).format(startTimeAndDate);
                    String startDate = new SimpleDateFormat(Constant.DATE_FORMAT).format(startTimeAndDate);
                    tvTripTime.setText(startTime);
                    tvTripDate.setText(startDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
