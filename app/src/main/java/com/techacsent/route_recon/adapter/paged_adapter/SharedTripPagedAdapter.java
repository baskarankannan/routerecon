package com.techacsent.route_recon.adapter.paged_adapter;

import android.annotation.SuppressLint;
import androidx.paging.PagedListAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnSharedItemClickListener;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.utills.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedTripPagedAdapter extends PagedListAdapter<MyTripsResponse.ListBean, SharedTripPagedAdapter.MyTripListViewHolder> {
    private static DiffUtil.ItemCallback<MyTripsResponse.ListBean> DIFF_CALLBACK;
    private OnSharedItemClickListener onSharedItemClickListener;
    private boolean isShowOptionPanel;

    public SharedTripPagedAdapter(boolean isShowOptionPanel, OnSharedItemClickListener onSharedItemClickListener) {
        super(DIFF_CALLBACK);
        this.isShowOptionPanel = isShowOptionPanel;
        this.onSharedItemClickListener = onSharedItemClickListener;
    }

    @NonNull
    @Override
    public MyTripListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_trips_item_row, viewGroup, false);
        return new MyTripListViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull MyTripListViewHolder myTripListViewHolder, int position) {
        MyTripsResponse.ListBean listBean = getItem(position);
        if (listBean != null) {
            myTripListViewHolder.tvTripName.setText(listBean.getTrip().getTripName());
            myTripListViewHolder.tvCount.setText(listBean.getTrip().getFriendAttend());
            myTripListViewHolder.tvTripRoute.setText(listBean.getTrip().getStartpoint().getFullAddress());
            if (listBean.getTrip().getBeginTime().equals(Constant.DEFAULT_DATE)) {
                myTripListViewHolder.tvTripDate.setText(R.string.not_applicable);
                myTripListViewHolder.tvTripTime.setText(R.string.not_applicable);
            } else {
                Date startTimeAndDate;
                try {
                    startTimeAndDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(listBean.getTrip().getBeginTime());
                    @SuppressLint("SimpleDateFormat") String startTime = new SimpleDateFormat(Constant.TIME_FORMAT).format(startTimeAndDate);
                    String startDate = new SimpleDateFormat(Constant.DATE_FORMAT).format(startTimeAndDate);
                    myTripListViewHolder.tvTripDate.setText(startDate);
                    myTripListViewHolder.tvTripTime.setText(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (isShowOptionPanel) {
                myTripListViewHolder.acceptCancel.setVisibility(View.VISIBLE);
            } else {
                myTripListViewHolder.acceptCancel.setVisibility(View.GONE);
            }
            DIFF_CALLBACK = new DiffUtil.ItemCallback<MyTripsResponse.ListBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull MyTripsResponse.ListBean oldItem, @NonNull MyTripsResponse.ListBean newItem) {
                    return oldItem.getTrip().getTripSharingId().equals(newItem.getTrip().getTripSharingId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull MyTripsResponse.ListBean oldItem, @NonNull MyTripsResponse.ListBean newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }

    }

    static class MyTripListViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripName;
        TextView tvCount;
        TextView tvTripRoute;
        TextView tvTripDate;
        TextView tvTripTime;
        LinearLayout layoutContent;
        RelativeLayout acceptCancel;
        ImageView ivCancel;
        ImageView ivAccept;

        public MyTripListViewHolder(@NonNull View itemView) {
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
    }
}
