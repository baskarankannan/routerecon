/*
package com.techacsent.route_recon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnClickListener;
import com.techacsent.route_recon.interfaces.OnWaypointRemoveListener;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;

import java.util.List;

public class WaypointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CreateTripModelClass.WayPointsBean> wayPointsBeanList;
    private OnWaypointRemoveListener onWaypointRemoveListener;

    public WaypointsAdapter(List<CreateTripModelClass.WayPointsBean> wayPointsBeanList,OnWaypointRemoveListener onWaypointRemoveListener) {
        this.wayPointsBeanList = wayPointsBeanList;
        this.onWaypointRemoveListener = onWaypointRemoveListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_attending_on_trip, parent, false);
        return new WaypointsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WaypointsViewHolder waypointsViewHolder = (WaypointsViewHolder) holder;
        CreateTripModelClass.WayPointsBean wayPointsBean = wayPointsBeanList.get(position);
        waypointsViewHolder.tvMail.setText(wayPointsBean.getFullAddress());
        waypointsViewHolder.tvName.setVisibility(View.GONE);
        waypointsViewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWaypointRemoveListener.onWaypointRemove(position,wayPointsBean);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wayPointsBeanList.size();
    }
    private static class WaypointsViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvMail;
        ImageView ivRemove;

        public WaypointsViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMail = itemView.findViewById(R.id.tv_mail);
            ivRemove = itemView.findViewById(R.id.iv_cancel);
        }
    }
}
*/
