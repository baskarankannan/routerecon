package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnFriendSearchListener;
import com.techacsent.route_recon.model.FollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class FilteredFriendAdapter extends RecyclerView.Adapter<FilteredFriendAdapter.MyViewHolder> implements Filterable {

    private int type;

    private Context context;
    private List<FollowersResponse.ListBean> contactList;
    private List<FollowersResponse.ListBean> contactListFiltered;
    private OnFriendSearchListener listener;

    public FilteredFriendAdapter(int type, Context context, List<FollowersResponse.ListBean> contactList, OnFriendSearchListener listener) {
        this.type = type;
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
        this.contactListFiltered = contactList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final FollowersResponse.ListBean contact = contactListFiltered.get(i);
        switch (type) {
            case 0:
                switch (contact.getUser().getAmfollower()) {
                    case "no":
                        myViewHolder.btnFollowUnfollow.setText(R.string.follow);
                        break;
                    case "pending":
                        myViewHolder.btnFollowUnfollow.setText(R.string.pending);
                        break;
                    case "yes":
                        myViewHolder.btnFollowUnfollow.setText(R.string.unfollow);
                        break;
                }
                break;
            case 1:
                myViewHolder.btnFollowUnfollow.setText(R.string.accept);
                break;
            case 2:
                myViewHolder.btnFollowUnfollow.setText(R.string.unfollow);
                break;

        }

        if(contact.getUser().getSubscriptionStatus().equals("notpayable")){
            myViewHolder.tvUserName.setText(contact.getUser().getUsername());
            myViewHolder.btnFollowUnfollow.setOnClickListener(v -> listener.onClick(contact,1));
        }else {
            myViewHolder.tvUserName.setText(contact.getUser().getUsername().concat(" (unsubscribed)"));
            myViewHolder.tvUserName.setTextColor(context.getResources().getColor(R.color.gray));
            myViewHolder.btnFollowUnfollow.setEnabled(false);
            myViewHolder.tvUserName.setEnabled(false);
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
        LinearLayout relativeContent;
        TextView tvUserName;
        Button btnFollowUnfollow;

        MyViewHolder(View view) {
            super(view);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            btnFollowUnfollow = itemView.findViewById(R.id.btn_follow_unfollow);


        }
    }
}
