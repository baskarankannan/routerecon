package com.techacsent.route_recon.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.UserSearchResponse;

public class UserSearchAdapter extends BaseRecyclerAdapter<UserSearchResponse.ListBean, OnRecyclerClickListener<UserSearchResponse.ListBean>, UserSearchAdapter.UserSearchViewHolder> {
    public UserSearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public UserSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserSearchViewHolder(inflate(R.layout.user_item, viewGroup));
    }

    public static class UserSearchViewHolder extends BaseViewHolder<UserSearchResponse.ListBean, OnRecyclerClickListener<UserSearchResponse.ListBean>> {
        LinearLayout relativeContent;
        TextView tvUserName;
        Button btnFollowUnfollow;

        UserSearchViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            btnFollowUnfollow = itemView.findViewById(R.id.btn_follow_unfollow);

        }

        @Override
        public void onBind(UserSearchResponse.ListBean item, @Nullable OnRecyclerClickListener<UserSearchResponse.ListBean> listener) {
            switch (item.getUser().getAmfollower()) {
                case "pending":
                    btnFollowUnfollow.setText(R.string.pending);
                    break;
                case "yes":
                    btnFollowUnfollow.setText(R.string.following);
                    break;
                case "no":
                    btnFollowUnfollow.setText(R.string.follow);
                    break;
            }

            if (item.getUser().getSubscriptionStatus().equals("notpayable")) {

                Log.e("comeSub", "yes");
                tvUserName.setText(item.getUser().getUsername());

                tvUserName.setTextColor(Color.parseColor("#000000"));

                tvUserName.setEnabled(true);
                relativeContent.setEnabled(true);
                btnFollowUnfollow.setEnabled(true);
                btnFollowUnfollow.setBackgroundResource(R.drawable.follow_unfollow_btn_bg);


                relativeContent.setOnClickListener(v -> {
                    item.getUser().setContent(true);
                    listener.onItemClicked(item);

                });
                btnFollowUnfollow.setOnClickListener(v -> {
                    item.getUser().setContent(false);
                    listener.onItemClicked(item);

                });
            } else {
                Log.e("comeSub", "no");
                tvUserName.setText(item.getUser().getUsername().concat(" (unsubscribed)"));
                tvUserName.setTextColor(Color.parseColor("#D6D1D1"));
                tvUserName.setEnabled(false);
                relativeContent.setEnabled(false);
                btnFollowUnfollow.setEnabled(false);
                btnFollowUnfollow.setBackgroundResource(R.drawable.bg_disabled_button);
            }
        }
    }
}
