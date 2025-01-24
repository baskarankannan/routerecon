package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.GroupListResponse;

public class GroupListAdapter extends BaseRecyclerAdapter<GroupListResponse.ListBean,
        OnRecyclerItemClickListener<GroupListResponse.ListBean>, GroupListAdapter.GroupListViewHolder> {


    public GroupListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public GroupListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GroupListViewHolder(inflate(R.layout.group_list_item, viewGroup));
    }

    class GroupListViewHolder extends BaseViewHolder<GroupListResponse.ListBean, OnRecyclerItemClickListener<GroupListResponse.ListBean>> {
        TextView tvGroupName;
        ImageView ivEditGroup;
        ImageView ivDeleteGroup;

        GroupListViewHolder(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            ivEditGroup = itemView.findViewById(R.id.iv_edit_group);
            ivDeleteGroup = itemView.findViewById(R.id.iv_delete_group);
        }

        @Override
        public void onBind(GroupListResponse.ListBean item, @Nullable OnRecyclerItemClickListener<GroupListResponse.ListBean> listener) {
            item.setPosition(getAdapterPosition());
            tvGroupName.setText(item.getName());
            ivDeleteGroup.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item, 0);
                }

            });

            ivEditGroup.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item, 1);
                }
            });
        }
    }
}
