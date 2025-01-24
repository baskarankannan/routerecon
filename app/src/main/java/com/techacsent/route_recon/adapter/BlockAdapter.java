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
import com.techacsent.route_recon.model.BlockedUserResponse;

public class BlockAdapter extends BaseRecyclerAdapter<BlockedUserResponse.ListBean,
        OnRecyclerClickListener<BlockedUserResponse.ListBean>, BlockAdapter.BlockedUserViewHolder> {

    public BlockAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BlockedUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BlockedUserViewHolder(inflate(R.layout.blocked_row, viewGroup));
    }

    class BlockedUserViewHolder extends BaseViewHolder<BlockedUserResponse.ListBean, OnRecyclerClickListener<BlockedUserResponse.ListBean>> {
        TextView tvUserName;
        ImageView ivCancel;

        BlockedUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_item);
            ivCancel = itemView.findViewById(R.id.iv_close);
        }

        @Override
        public void onBind(BlockedUserResponse.ListBean item, @Nullable OnRecyclerClickListener listener) {
            item.getUser().setPosition(getAdapterPosition());
            tvUserName.setText(item.getUser().getUsername());
            ivCancel.setOnClickListener(v -> {
                if(listener!=null){
                    listener.onItemClicked(item);
                }
            });
        }
    }
}
