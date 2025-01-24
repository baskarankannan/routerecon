package com.techacsent.route_recon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.SendTripsAcceptListener;
import com.techacsent.route_recon.model.ReceivedShareTripRequest;
import com.techacsent.route_recon.model.sendtrip.List;
import com.techacsent.route_recon.model.sendtrip.SendRouteListResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class SendTripsAdapter extends RecyclerView.Adapter<SendTripsAdapter.SendTripViewHolder> {

    private Context context;
    private final ArrayList<List> receivedShareTripRequestList;


    private String adapterType;

   private final SendTripsAcceptListener sendTripsAcceptListener;

    public SendTripsAdapter(Context context, ArrayList<List> receivedShareTripRequestList, SendTripsAcceptListener sendTripsAcceptListener) {
        this.context = context;
        this.receivedShareTripRequestList = receivedShareTripRequestList;
        this.sendTripsAcceptListener = sendTripsAcceptListener;

       // Log.e("SendTripsAdapter", "const " +receivedShareTripRequestList.size());

    }

    @Override
    public @NotNull SendTripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_send_trips, parent, false);
        return new SendTripsAdapter.SendTripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SendTripViewHolder holder, final int position){

      //  Log.e("SendTripsAdapter", "size " +receivedShareTripRequestList.size());

        //Log.e("SendTripsAdapter", "name " +receivedShareTripRequestList.get(position).getSenderName());

          Log.e("SendTripsAdapter", receivedShareTripRequestList.get(position).getTrip().getStartpoint().getAddress());


        holder.tvTripTitle.setText(receivedShareTripRequestList.get(position).getTrip().getSenderName()
        +" is sending you route " +receivedShareTripRequestList.get(position).getTrip().getTripName());
        holder.tvTripRoute.setText(receivedShareTripRequestList.get(position).getTrip().getStartpoint().getAddress());
        holder.tvTripDate.setText(receivedShareTripRequestList.get(position).getTrip().getBeginTime().substring(0,10));
        holder.tvTripTime.setText(receivedShareTripRequestList.get(position).getTrip().getBeginTime().substring(10));

         holder.ivAccept.setOnClickListener(view -> sendTripsAcceptListener.onSendTripAccept(true,
                receivedShareTripRequestList.get(position).getTrip().getId()));


        holder.ivCancel.setOnClickListener(view -> sendTripsAcceptListener.onSendTripAccept(false,
                receivedShareTripRequestList.get(position).getTrip().getId()));



    }




    @Override
    public int getItemCount() {

        Log.e("SendTripsAdapter", "getItemCount " +receivedShareTripRequestList.size());


        return receivedShareTripRequestList.size();



    }

    class SendTripViewHolder extends RecyclerView.ViewHolder {

        TextView tvTripName;
        TextView tvTripTitle;
        TextView tvTripRoute;
        TextView tvTripDate;
        TextView tvTripTime;
        LinearLayout layoutContent;
        RelativeLayout acceptCancel;
        ImageView ivCancel;
        ImageView ivAccept;

        public SendTripViewHolder(View itemView) {

            super(itemView);
            //tvTripName = itemView.findViewById(R.id.tv_trip_name);
            tvTripTitle = itemView.findViewById(R.id.tv_trip_title);
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
