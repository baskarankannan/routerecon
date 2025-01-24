package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.FollowersResponse;

import java.util.List;


public class FriendsItemAdapter extends BaseRecyclerAdapter<FollowersResponse.ListBean, OnRecyclerClickListener<FollowersResponse.ListBean>, FriendsItemAdapter.FollowersViewHolder> {
    private int type;
    private List<FollowersResponse.ListBean> filtereditem;

    public FriendsItemAdapter(Context context, int type) {
        super(context);
        this.type = type;
        this.filtereditem = items;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FollowersViewHolder(inflate(R.layout.user_item, viewGroup));
    }



    public class FollowersViewHolder extends BaseViewHolder<FollowersResponse.ListBean, OnRecyclerClickListener<FollowersResponse.ListBean>> {
        LinearLayout relativeContent;
        TextView tvUserName;
        Button btnFollowUnfollow;

        public FollowersViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            btnFollowUnfollow = itemView.findViewById(R.id.btn_follow_unfollow);

        }

        @Override
        public void onBind(FollowersResponse.ListBean item, @Nullable OnRecyclerClickListener<FollowersResponse.ListBean> listener) {
            tvUserName.setText(item.getUser().getUsername());
            switch (type) {
                case 0:
                    switch (item.getUser().getAmfollower()) {
                        case "no":
                            btnFollowUnfollow.setText(R.string.follow);
                            break;
                        case "pending":
                            btnFollowUnfollow.setText(R.string.pending);
                            break;
                        case "yes":
                            btnFollowUnfollow.setText(R.string.unfollow);
                            break;
                    }
                    break;
                case 1:
                    btnFollowUnfollow.setText(R.string.accept);
                    break;
                case 2:
                    btnFollowUnfollow.setText(R.string.unfollow);
                    break;

            }
            btnFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(item);
                }
            });

        }
    }
}
