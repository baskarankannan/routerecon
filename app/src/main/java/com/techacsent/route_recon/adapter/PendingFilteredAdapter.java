package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnFriendSearchListener;
import com.techacsent.route_recon.model.FollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class PendingFilteredAdapter extends RecyclerView.Adapter<PendingFilteredAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<FollowersResponse.ListBean> contactList;
    private List<FollowersResponse.ListBean> contactListFiltered;
    private OnFriendSearchListener listener;

    public PendingFilteredAdapter(Context context, List<FollowersResponse.ListBean> contactList, OnFriendSearchListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
        this.contactListFiltered = contactList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pending_row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final FollowersResponse.ListBean contact = contactListFiltered.get(i);

        if (contact.getUser().getSubscriptionStatus().equals("notpayable")) {
            myViewHolder.tvUserName.setText(contact.getUser().getUsername());
            myViewHolder.ivAccept.setOnClickListener(v -> listener.onClick(contact, 0));
            myViewHolder.ivCancel.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onClick(contact, 1);
                }

            });
        } else {
            myViewHolder.tvUserName.setText(contact.getUser().getUsername().concat(" (unsubscribed)"));
            myViewHolder.tvUserName.setTextColor(context.getResources().getColor(R.color.grey));
            myViewHolder.ivAccept.setEnabled(false);
            myViewHolder.ivCancel.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<FollowersResponse.ListBean> filteredList = new ArrayList<>();
                    for (FollowersResponse.ListBean row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getUser().getUsername().toLowerCase().contains(charString.toLowerCase()) || row.getUser().getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<FollowersResponse.ListBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeContent;
        TextView tvUserName;
        ImageView ivAccept;
        ImageView ivCancel;

        MyViewHolder(View view) {
            super(view);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            ivAccept = itemView.findViewById(R.id.iv_accept);
            ivCancel = itemView.findViewById(R.id.iv_cancel);


        }
    }
}
