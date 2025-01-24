package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;

import java.util.Objects;

public class UpdatedWaypointAdapter extends BaseRecyclerAdapter<WaypointModel.WayPointsBean,
        OnRecyclerClickListener<WaypointModel.WayPointsBean>, UpdatedWaypointAdapter.WaypointsViewHolder> {

    public UpdatedWaypointAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public WaypointsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WaypointsViewHolder(inflate(R.layout.item_waypoint, viewGroup));
    }

    public static class WaypointsViewHolder extends BaseViewHolder<WaypointModel.WayPointsBean, OnRecyclerClickListener<WaypointModel.WayPointsBean>> {
        ImageView ivReposition;
        TextView tvPlace;
        ImageView ivRemove;

        WaypointsViewHolder(View itemView) {
            super(itemView);
            ivReposition = itemView.findViewById(R.id.iv_reposition);
            tvPlace = itemView.findViewById(R.id.tv_place);
            ivRemove = itemView.findViewById(R.id.iv_cancel);
        }

        @Override
        public void onBind(WaypointModel.WayPointsBean item, @Nullable OnRecyclerClickListener<WaypointModel.WayPointsBean> listener) {
            tvPlace.setText(item.getFullAddress());
            ivRemove.setOnClickListener(v -> Objects.requireNonNull(listener).onItemClicked(item));

        }
    }
}
