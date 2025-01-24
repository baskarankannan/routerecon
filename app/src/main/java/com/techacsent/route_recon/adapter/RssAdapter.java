package com.techacsent.route_recon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;

import java.text.SimpleDateFormat;

public class RssAdapter extends BaseRecyclerAdapter<Article, OnRecyclerClickListener<Article>, RssAdapter.RssVH> {
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy");

    public RssAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RssVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RssVH(inflate(R.layout.item_rss_list, viewGroup));
    }

    public static class RssVH extends BaseViewHolder<Article, OnRecyclerClickListener<Article>> {

        private TextView tvTitle;
        private TextView tvDate;
        private LinearLayout content;

        RssVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            content = itemView.findViewById(R.id.content);

        }

        @Override
        public void onBind(Article item, @Nullable OnRecyclerClickListener<Article> listener) {
            tvTitle.setText(item.getTitle());
            tvDate.setText(spf.format(item.getPubDate()));

            content.setOnClickListener(v -> {
                if(listener!=null){
                    listener.onItemClicked(item);
                }
            });

        }
    }
}
