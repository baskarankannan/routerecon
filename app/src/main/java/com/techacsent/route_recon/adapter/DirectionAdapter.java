package com.techacsent.route_recon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapbox.api.directions.v5.models.LegStep;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;

import java.text.DecimalFormat;

public class DirectionAdapter extends BaseRecyclerAdapter<LegStep, OnRecyclerClickListener<LegStep>, DirectionAdapter.DirectionViewHolder> {

    public DirectionAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public DirectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DirectionViewHolder(inflate(R.layout.item_direction, viewGroup));
    }

    class DirectionViewHolder extends BaseViewHolder<LegStep, OnRecyclerClickListener<LegStep>> {
        TextView tvInstruction;
        TextView tvDistance;

        DirectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstruction = itemView.findViewById(R.id.tv_instruction);
            tvDistance = itemView.findViewById(R.id.tv_distance);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBind(LegStep item, @Nullable OnRecyclerClickListener listener) {
            tvInstruction.setText(item.maneuver().instruction());
            tvDistance.setText(new DecimalFormat("##.##").format(item.distance() * 1.09361) + " yard");
        }
    }
}
