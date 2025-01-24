package com.techacsent.route_recon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnClickListener;
import com.techacsent.route_recon.model.MyTripDescriptionModel;

import java.util.List;

public class FriendsInSharedTripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyTripDescriptionModel.DataBean.FriendAttendBean> friendAttendBeanList;
    private OnClickListener onClickListener;

    public FriendsInSharedTripAdapter(List<MyTripDescriptionModel.DataBean.FriendAttendBean> friendAttendBeanList,OnClickListener onClickListener) {
        this.friendAttendBeanList = friendAttendBeanList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_attending_on_trip, parent, false);
        return new FriendsInSharedTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendsInSharedTripViewHolder friendsInSharedTripViewHolder = (FriendsInSharedTripViewHolder) holder;
        friendsInSharedTripViewHolder.bindViewHolder(friendAttendBeanList.get(position),onClickListener);
    }

    @Override
    public int getItemCount() {
        return friendAttendBeanList.size();
    }

    private static class FriendsInSharedTripViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvMail;
        ImageView ivRemove;

        FriendsInSharedTripViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMail = itemView.findViewById(R.id.tv_mail);
            ivRemove = itemView.findViewById(R.id.iv_remove);
        }
        void bindViewHolder(MyTripDescriptionModel.DataBean.FriendAttendBean dataBean, OnClickListener onClickListener){
            if(dataBean!=null){
                tvName.setText(dataBean.getUsername());
                tvMail.setText(dataBean.getEmail());
                ivRemove.setVisibility(View.GONE);
                ivRemove.setOnClickListener(v -> onClickListener.onItemClickedListener(1));
            }

        }
    }
}
