package com.techacsent.route_recon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnGroupClickListener;
import com.techacsent.route_recon.model.GroupListResponse;

import java.util.ArrayList;
import java.util.List;

public class FilteredGroupAdapter extends RecyclerView.Adapter<FilteredGroupAdapter.MyViewHolder> implements Filterable {

    private List<GroupListResponse.ListBean> contactList;
    private List<GroupListResponse.ListBean> contactListFiltered;
    private OnGroupClickListener listener;

    public FilteredGroupAdapter(List<GroupListResponse.ListBean> contactList, OnGroupClickListener listener) {
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.group_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final GroupListResponse.ListBean contact = contactListFiltered.get(i);

        myViewHolder.tvGroupName.setText(contact.getName());
        myViewHolder.ivDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(contact, 1);
            }
        });

        myViewHolder.ivEditGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(contact, 2);
            }
        });

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
                    List<GroupListResponse.ListBean> filteredList = new ArrayList<>();
                    for (GroupListResponse.ListBean row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<GroupListResponse.ListBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupName;
        ImageView ivEditGroup;
        ImageView ivDeleteGroup;

        MyViewHolder(View view) {
            super(view);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            ivEditGroup = itemView.findViewById(R.id.iv_edit_group);
            ivDeleteGroup = itemView.findViewById(R.id.iv_delete_group);


        }
    }
}
