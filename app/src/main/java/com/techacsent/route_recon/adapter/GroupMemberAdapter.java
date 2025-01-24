package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.FollowersResponse;

import java.util.List;

public class GroupMemberAdapter extends BaseRecyclerAdapter<FollowersResponse.ListBean,
        OnRecyclerItemClickListener<FollowersResponse.ListBean>, GroupMemberAdapter.FollowersViewHolder> {

    private List<String> userIdList;


    public GroupMemberAdapter(Context context, List<String> userIdList) {
        super(context);
        this.userIdList = userIdList;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FollowersViewHolder(inflate(R.layout.select_user_to_share_trip, viewGroup));
    }

    class FollowersViewHolder extends BaseViewHolder<FollowersResponse.ListBean, OnRecyclerItemClickListener<FollowersResponse.ListBean>> {
        RelativeLayout relativeContent;
        TextView tvUserName;
        CheckBox chkFollow;

        FollowersViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            chkFollow = itemView.findViewById(R.id.chk_select);
        }

        @Override
        public void onBind(FollowersResponse.ListBean item, @Nullable OnRecyclerItemClickListener<FollowersResponse.ListBean> listener) {

            tvUserName.setText(item.getUser().getName());

            /*Timber.d(String.valueOf(userIdList.contains(item.getUser().getId())));
            Timber.d(String.valueOf(userIdList.size()));*/

            chkFollow.setText(item.getUser().getName());

            chkFollow.setChecked(userIdList.contains(item.getUser().getId()));

            chkFollow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null) {
                        if (isChecked) {
                            listener.onItemClicked(item, 0);
                        } else {
                            listener.onItemClicked(item, 1);
                        }
                    }

                    chkFollow.setChecked(isChecked);
                }
            });
        }
    }
}
