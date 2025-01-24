package com.techacsent.route_recon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.tracklisthistory.List;
import com.techacsent.route_recon.model.tracklisthistory.TrackListHistoryResponse;


public class TrackRecordHistoryAdapter extends RecyclerView.Adapter<TrackRecordHistoryAdapter.TrackRecordViewHolder> {

    private Context context;
    private java.util.List<List> trackHistoryList;

    private String adapterType;



    public TrackRecordHistoryAdapter(Context context, TrackListHistoryResponse trackListHistoryResponse) {
        this.context = context;
        this.trackHistoryList = trackListHistoryResponse.getList();
    }

    @Override
    public TrackRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tracked_trip_list_history, parent, false);
        return new TrackRecordHistoryAdapter.TrackRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackRecordViewHolder holder, final int position){


        Log.e("TrackHistory", trackHistoryList.get(position).getName().toString());

        holder.textViewTripTitle.setText(trackHistoryList.get(position).getName());
        holder.textViewTime.setText(trackHistoryList.get(position).getTime());
        holder.textViewSpeed.setText(trackHistoryList.get(position).getSpeed());
        holder.textViewDistance.setText(trackHistoryList.get(position).getDistance());
    }



    @Override
    public int getItemCount() {

        return trackHistoryList.size();
    }

    class TrackRecordViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTripTitle, textViewTime, textViewSpeed, textViewDistance;
        private LinearLayout container;

        public TrackRecordViewHolder(View itemView) {

            super(itemView);
            textViewTripTitle = itemView.findViewById(R.id.textViewTripName);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewSpeed = itemView.findViewById(R.id.textViewSpeed);
            textViewDistance = itemView.findViewById(R.id.timeDistance);
        }
    }

}
