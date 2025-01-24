package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.MarkerDetailResponse;

public class LandmarkImageAdapter extends BaseRecyclerAdapter<MarkerDetailResponse.LandmarkImageBean,
        OnRecyclerClickListener<MarkerDetailResponse.LandmarkImageBean>, LandmarkImageAdapter.LandmarkViewHolder> {
    public LandmarkImageAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public LandmarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LandmarkViewHolder(inflate(R.layout.landmark_image_item, viewGroup));
    }

    public static class LandmarkViewHolder extends BaseViewHolder<MarkerDetailResponse.LandmarkImageBean, OnRecyclerClickListener<MarkerDetailResponse.LandmarkImageBean>> {
        ImageView ivLandmarkImage;
        ImageView ivCancel;
        ProgressBar progressBar;

        LandmarkViewHolder(View itemView) {
            super(itemView);
            ivLandmarkImage = itemView.findViewById(R.id.iv_landmark_image);
            ivCancel = itemView.findViewById(R.id.iv_delete_image);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

        @Override
        public void onBind(MarkerDetailResponse.LandmarkImageBean item, @Nullable OnRecyclerClickListener<MarkerDetailResponse.LandmarkImageBean> listener) {
            item.setPosition(getAdapterPosition());
            Picasso.get().load(item.getImageUrl())
                    .resize(200, 200)
                    .centerCrop()
                    .placeholder(R.drawable.landmark_place_holer)
                    .into(ivLandmarkImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                            ivCancel.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    });
            ivCancel.setOnClickListener(v -> listener.onItemClicked(item));

        }
    }
}
