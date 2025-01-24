package com.techacsent.route_recon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.model.TripFollowerResponse;

import java.util.List;

public class SelectFollwerGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> followersBean;
    private static final int FOLLOWER_TYPE = 1;
    private static final int GROUP_TYPE = 2;


    public SelectFollwerGroupAdapter(List<Object> followersBean) {
        this.followersBean = followersBean;
    }

    @Override
    public int getItemViewType(int position) {
        if (followersBean.get(position) instanceof TripFollowerResponse.ListBean) {
            return FOLLOWER_TYPE;
        } else if (followersBean.get(position) instanceof GroupListResponse.ListBean) {
            return GROUP_TYPE;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case FOLLOWER_TYPE:
                View view = inflater.inflate(R.layout.select_user_to_share_trip, parent, false);
                viewHolder = new FollowersViewHolder(view);
                break;

            case GROUP_TYPE:
                View view1 = inflater.inflate(R.layout.select_group_to_share_trip, parent, false);
                viewHolder = new GroupViewHolder(view1);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {

            case FOLLOWER_TYPE:
                FollowersViewHolder followersViewHolder = (FollowersViewHolder) holder;
                configureFollowerViewHolder(followersViewHolder, position);
                break;

            case GROUP_TYPE:
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                configureGroupViewHolder(groupViewHolder, position);
                break;
        }

    }

    private void configureFollowerViewHolder(FollowersViewHolder followersViewHolder, int position) {
        TripFollowerResponse.ListBean bean = (TripFollowerResponse.ListBean) followersBean.get(position);
        followersViewHolder.tvUserName.setText(bean.getUser().getUsername());
        if (bean.getUser().getSubscriptionStatus().equals("notpayable")) {
            followersViewHolder.chkFollow.setText(bean.getUser().getUsername());
            followersViewHolder.chkFollow.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    bean.getUser().setSelected(true);
                } else {
                    bean.getUser().setSelected(false);
                }
            });
        } else {
            followersViewHolder.chkFollow.setText(bean.getUser().getUsername().concat(" (unsubscribed)"));
            followersViewHolder.chkFollow.setEnabled(false);
        }
    }

    private void configureGroupViewHolder(GroupViewHolder groupViewHolder, int position) {
        GroupListResponse.ListBean bean = (GroupListResponse.ListBean) followersBean.get(position);
        groupViewHolder.tvUserName.setText(bean.getName());
        groupViewHolder.chkFollow.setText(bean.getName());
        groupViewHolder.chkFollow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bean.setSelected(true);
            } else {
                bean.setSelected(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followersBean.size();
    }

    private static class FollowersViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeContent;
        TextView tvUserName;
        CheckBox chkFollow;

        FollowersViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            chkFollow = itemView.findViewById(R.id.chk_select);

        }
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeContent;
        TextView tvUserName;
        CheckBox chkFollow;

        GroupViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            chkFollow = itemView.findViewById(R.id.chk_select);

        }
    }
}
