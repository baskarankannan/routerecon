package com.techacsent.route_recon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.utills.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTripAdapter extends BaseRecyclerAdapter<MyTripsResponse.ListBean,
        OnRecyclerClickListener<MyTripsResponse.ListBean>, MyTripAdapter.MyTripListViewHolder> {
    private boolean isShowOptionPanel;

    public MyTripAdapter(Context context, boolean isShowOptionPanel) {
        super(context);
        this.isShowOptionPanel = isShowOptionPanel;
    }

    @NonNull
    @Override
    public MyTripListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyTripListViewHolder(inflate(R.layout.my_trips_item_row, viewGroup));
    }

    class MyTripListViewHolder extends BaseViewHolder<MyTripsResponse.ListBean, OnRecyclerClickListener<MyTripsResponse.ListBean>> {
        TextView tvTripName;
        TextView tvCount;
        TextView tvTripRoute;
        TextView tvTripDate;
        TextView tvTripTime;
        LinearLayout layoutContent;
        RelativeLayout acceptCancel;
        ImageView ivCancel;
        ImageView ivAccept;

        MyTripListViewHolder(View itemView) {
            super(itemView);
            tvTripName = itemView.findViewById(R.id.tv_trip_name);
            tvCount = itemView.findViewById(R.id.tv_member_count);
            tvTripRoute = itemView.findViewById(R.id.tv_trip_route);
            tvTripDate = itemView.findViewById(R.id.tv_trip_date);
            tvTripTime = itemView.findViewById(R.id.tv_trip_time);
            layoutContent = itemView.findViewById(R.id.trip_content);
            acceptCancel = itemView.findViewById(R.id.accept_cancel);
            ivCancel = itemView.findViewById(R.id.iv_cancel);
            ivAccept = itemView.findViewById(R.id.iv_accept);
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        public void onBind(MyTripsResponse.ListBean tripListResponse, @Nullable OnRecyclerClickListener<MyTripsResponse.ListBean> listener) {
            tvTripName.setText(tripListResponse.getTrip().getTripName());
            tvCount.setText(tripListResponse.getTrip().getFriendAttend());
            tvTripRoute.setText(tripListResponse.getTrip().getStartpoint().getAddress());
            if (tripListResponse.getTrip().getBeginTime().equals(Constant.DEFAULT_DATE)) {
                tvTripDate.setText(R.string.not_applicable);
                tvTripTime.setText(R.string.not_applicable);
            } else {
                Date startTimeAndDate = null;
                try {
                    startTimeAndDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(tripListResponse.getTrip().getBeginTime());
                    String startTime = new SimpleDateFormat(Constant.TIME_FORMAT).format(startTimeAndDate);
                    String startDate = new SimpleDateFormat(Constant.DATE_FORMAT).format(startTimeAndDate);
                    tvTripDate.setText(startDate);
                    tvTripTime.setText(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (isShowOptionPanel) {
                acceptCancel.setVisibility(View.VISIBLE);
            } else {
                acceptCancel.setVisibility(View.GONE);
            }
            layoutContent.setOnClickListener(v -> listener.onItemClicked(tripListResponse));

        }
    }
}
