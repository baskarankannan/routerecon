package com.techacsent.route_recon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.FollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class SelectMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<FollowersResponse.ListBean> followersBean;
    private List<FollowersResponse.ListBean> followerBeanFiltered;

    public SelectMemberAdapter(List<FollowersResponse.ListBean> followersBean) {
        this.followersBean = followersBean;
        this.followerBeanFiltered = followersBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_user_to_share_trip, parent, false);
        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FollowersViewHolder followersViewHolder = (FollowersViewHolder) holder;
        FollowersResponse.ListBean bean = followerBeanFiltered.get(position);
        followersViewHolder.tvUserName.setText(bean.getUser().getName());
        followersViewHolder.chkFollow.setText(bean.getUser().getName());
        followersViewHolder.chkFollow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bean.getUser().setSelected(true);
                } else {
                    bean.getUser().setSelected(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return followerBeanFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    followerBeanFiltered = followersBean;
                } else {
                    List<FollowersResponse.ListBean> filteredList = new ArrayList<>();
                    for (FollowersResponse.ListBean row : followersBean) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getUser().getUsername().toLowerCase().contains(charString.toLowerCase()) || row.getUser().getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    followerBeanFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = followerBeanFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                followerBeanFiltered = (ArrayList<FollowersResponse.ListBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private static class FollowersViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeContent;
        TextView tvUserName;
        CheckBox chkFollow;

        public FollowersViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            chkFollow = itemView.findViewById(R.id.chk_select);

        }
    }
}
