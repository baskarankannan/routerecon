package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.ReportListResponse;

public class PostReportAdapter extends BaseRecyclerAdapter<ReportListResponse.MarkerReportListBean, OnRecyclerClickListener<ReportListResponse.MarkerReportListBean>, PostReportAdapter.PostReportViewHolder> {

    public PostReportAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public PostReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostReportViewHolder(inflate(R.layout.item_post_report, viewGroup));
    }

    class PostReportViewHolder extends BaseViewHolder<ReportListResponse.MarkerReportListBean, OnRecyclerClickListener<ReportListResponse.MarkerReportListBean>> {
        ImageView ivImage;
        TextView tvTitle;

        PostReportViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onBind(ReportListResponse.MarkerReportListBean item, @Nullable OnRecyclerClickListener<ReportListResponse.MarkerReportListBean> listener) {
            tvTitle.setText(item.getName());

            Picasso.get().load(item.getIconImg()).placeholder(R.drawable.placeholder_image).fit().into(ivImage);

            ivImage.setOnClickListener(v -> {
                if(listener!=null){
                    listener.onItemClicked(item);
                }

            });

            /*ivImage.setImageResource(item.getImage());
            tvTitle.setText(item.getTitle());
            ivImage.setOnClickListener(v -> {
                if(listener!=null){
                    listener.onItemClicked(item);
                }

            });*/
        }
    }
}
