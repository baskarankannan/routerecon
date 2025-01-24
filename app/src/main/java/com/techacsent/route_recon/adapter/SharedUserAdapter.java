package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.SharedUserResponse;

public class SharedUserAdapter extends BaseRecyclerAdapter<SharedUserResponse.ListBean, OnRecyclerClickListener<SharedUserResponse.ListBean>, SharedUserAdapter.SharedUserViewHolder> {


    public SharedUserAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SharedUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SharedUserViewHolder(inflate(R.layout.item_shared_user_info, viewGroup));
    }

    class SharedUserViewHolder extends BaseViewHolder<SharedUserResponse.ListBean, OnRecyclerClickListener<SharedUserResponse.ListBean>> {
        LinearLayout shareUserContent;
        TextView tvUserName;
        ImageView ivUserImage;

        SharedUserViewHolder(View itemView) {
            super(itemView);
            shareUserContent = itemView.findViewById(R.id.share_user_content);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            ivUserImage = itemView.findViewById(R.id.iv_user);

        }

        @Override
        public void onBind(SharedUserResponse.ListBean item, @Nullable OnRecyclerClickListener<SharedUserResponse.ListBean> listener) {
            tvUserName.setText(item.getUser().getName());
            shareUserContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(item);
                }
            });

        }
    }
}
