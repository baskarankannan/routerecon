package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.FollowersResponse;

public class PendingUserAdapter /*extends RecyclerView.Adapter<RecyclerView.ViewHolder>*/
        extends BaseRecyclerAdapter<FollowersResponse.ListBean, OnRecyclerItemClickListener<FollowersResponse.ListBean>, PendingUserAdapter.PendingViewHolder> {
    public PendingUserAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public PendingUserAdapter.PendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PendingViewHolder(inflate(R.layout.pending_row, viewGroup));
    }

    public static class PendingViewHolder extends BaseViewHolder<FollowersResponse.ListBean, OnRecyclerItemClickListener<FollowersResponse.ListBean>> {
        RelativeLayout relativeContent;
        TextView tvUserName;
        ImageView ivAccept;
        ImageView ivCancel;

        public PendingViewHolder(View itemView) {
            super(itemView);
            relativeContent = itemView.findViewById(R.id.content);
            tvUserName = itemView.findViewById(R.id.tv_item);
            ivAccept = itemView.findViewById(R.id.iv_accept);
            ivCancel = itemView.findViewById(R.id.iv_cancel);

        }

        @Override
        public void onBind(FollowersResponse.ListBean item, @Nullable OnRecyclerItemClickListener<FollowersResponse.ListBean> listener) {
            tvUserName.setText(item.getUser().getUsername());
            ivAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(item,0);
                }
            });

            ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClicked(item,1);
                    }

                }
            });

        }
    }
}
