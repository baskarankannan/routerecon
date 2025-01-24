package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.CategoryListResponse;

public class CategoryAdapter extends BaseRecyclerAdapter<CategoryListResponse.ListBean, OnRecyclerItemClickListener<CategoryListResponse.ListBean>, CategoryAdapter.CategoryVH> {

    private boolean isShowIcon;

    public CategoryAdapter(Context context, boolean isShowIcon) {
        super(context);
        this.isShowIcon = isShowIcon;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoryVH(inflate(R.layout.item_security_checklist_category, viewGroup));
    }

    class CategoryVH extends BaseViewHolder<CategoryListResponse.ListBean, OnRecyclerItemClickListener<CategoryListResponse.ListBean>> {
        FrameLayout frameLayout;
        TextView tvCheckList;
        ImageView ivRemove;

        CategoryVH(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_content);
            tvCheckList = itemView.findViewById(R.id.tv_checkList);
            ivRemove = itemView.findViewById(R.id.iv_remove);
        }

        @Override
        public void onBind(CategoryListResponse.ListBean item, @Nullable OnRecyclerItemClickListener<CategoryListResponse.ListBean> listener) {
            tvCheckList.setText(item.getCategoryName());
            if (!isShowIcon) {
                ivRemove.setVisibility(View.GONE);
            }
            if (item.getEditable().equals("no")) {
                ivRemove.setVisibility(View.GONE);
            }

            ivRemove.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item, 0);
                }
            });

            frameLayout.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item, 1);
                }
            });
        }
    }
}
