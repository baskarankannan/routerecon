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
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.BeingSharedTripResponse;
import com.techacsent.route_recon.utills.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PendingTripsAdapter extends BaseRecyclerAdapter<BeingSharedTripResponse.ListBean,
        OnRecyclerItemClickListener<BeingSharedTripResponse.ListBean>, PendingTripsAdapter.MyTripListViewHolder> {

    private boolean isShowOptionPanel;

    public PendingTripsAdapter(Context context, boolean isShowOptionPanel) {
        super(context);
        this.isShowOptionPanel = isShowOptionPanel;
    }

    @NonNull
    @Override
    public MyTripListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyTripListViewHolder(inflate(R.layout.my_trips_item_row, viewGroup));
    }

    class MyTripListViewHolder extends BaseViewHolder<BeingSharedTripResponse.ListBean, OnRecyclerItemClickListener<BeingSharedTripResponse.ListBean>> {
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

        @Override
        public void onBind(BeingSharedTripResponse.ListBean item, @Nullable OnRecyclerItemClickListener<BeingSharedTripResponse.ListBean> listener) {
            tvTripName.setText(item.getTrip().getTripName());
            tvCount.setText(item.getTrip().getFriendAttend());
            tvTripRoute.setText(item.getTrip().getStartpoint().getAddress());

            if (item.getTrip().getBeginTime().equals(Constant.DEFAULT_DATE)) {
                tvTripDate.setText(R.string.not_applicable);
                tvTripTime.setText(R.string.not_applicable);
            } else {
                try {
                    @SuppressLint("SimpleDateFormat") Date startTimeAndDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(item.getTrip().getBeginTime());
                    @SuppressLint("SimpleDateFormat") String startTime = new SimpleDateFormat(Constant.TIME_FORMAT).format(startTimeAndDate);
                    @SuppressLint("SimpleDateFormat") String startDate = new SimpleDateFormat(Constant.DATE_FORMAT).format(startTimeAndDate);
                    tvTripTime.setText(startTime);
                    tvTripDate.setText(startDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            /*tvTripDate.setText(R.string.not_applicable);
            tvTripTime.setText(R.string.not_applicable);*/
            if (isShowOptionPanel) {
                acceptCancel.setVisibility(View.VISIBLE);
            } else {
                acceptCancel.setVisibility(View.GONE);
            }
            if (listener != null) {
                layoutContent.setOnClickListener(v -> listener.onItemClicked(item, 0));
                ivCancel.setOnClickListener(v -> listener.onItemClicked(item, 1));
                ivAccept.setOnClickListener(v -> listener.onItemClicked(item, 2));

            }

        }
    }
}

