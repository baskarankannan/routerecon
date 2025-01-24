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
import com.techacsent.route_recon.model.MyTripDescriptionModel;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

public class TripAttendingAdapter extends BaseRecyclerAdapter<MyTripDescriptionModel.DataBean.FriendAttendBean, OnRecyclerClickListener<MyTripDescriptionModel.DataBean.FriendAttendBean>,
        TripAttendingAdapter.FriendsInSharedTripViewHolder> {

    private boolean isRemovable;

    public TripAttendingAdapter(Context context, boolean isRemovable) {
        super(context);
        this.isRemovable = isRemovable;
    }

    @NonNull
    @Override
    public FriendsInSharedTripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FriendsInSharedTripViewHolder(inflate(R.layout.friends_attending_on_trip, viewGroup));
    }

    public class FriendsInSharedTripViewHolder extends BaseViewHolder<MyTripDescriptionModel.DataBean.FriendAttendBean, OnRecyclerClickListener<MyTripDescriptionModel.DataBean.FriendAttendBean>> {
        TextView tvName;
        TextView tvMail;
        ImageView ivRemove;

        FriendsInSharedTripViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMail = itemView.findViewById(R.id.tv_mail);
            ivRemove = itemView.findViewById(R.id.iv_remove);
        }

        @Override
        public void onBind(MyTripDescriptionModel.DataBean.FriendAttendBean item, @Nullable OnRecyclerClickListener<MyTripDescriptionModel.DataBean.FriendAttendBean> listener) {
            if (item != null) {
                tvName.setText(item.getUsername());
                tvMail.setText(item.getEmail());
                if (isRemovable) {
                    if (Integer.parseInt(item.getId()) == PreferenceManager.getInt(Constant.KEY_USER_ID)) {
                        ivRemove.setVisibility(View.GONE);
                    } else {
                        ivRemove.setVisibility(View.VISIBLE);
                    }
                }

                ivRemove.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onItemClicked(item);
                    }
                });
            }

        }
    }
}
