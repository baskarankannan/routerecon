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
import com.techacsent.route_recon.model.CategoryWiseChecklistResponse;


public class CategorizedChecklistAdapter extends BaseRecyclerAdapter<CategoryWiseChecklistResponse.ListBean,
        OnRecyclerItemClickListener<CategoryWiseChecklistResponse.ListBean>, CategorizedChecklistAdapter.CategorizedViewHolder> {

    private boolean isDeleteable;


    public CategorizedChecklistAdapter(Context context,boolean isDeleteable) {
        super(context);
        this.isDeleteable = isDeleteable;
    }

    @NonNull
    @Override
    public CategorizedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategorizedViewHolder(inflate(R.layout.item_categorized_checklist, viewGroup));
    }

    class CategorizedViewHolder extends BaseViewHolder<CategoryWiseChecklistResponse.ListBean, OnRecyclerItemClickListener<CategoryWiseChecklistResponse.ListBean>> {
        TextView tvInstruction;
        RelativeLayout content;
        ImageView ivRemove;

        CategorizedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstruction = itemView.findViewById(R.id.tv_checkList);
            content = itemView.findViewById(R.id.layout_content);
            ivRemove = itemView.findViewById(R.id.iv_remove);
        }

        @Override
        public void onBind(CategoryWiseChecklistResponse.ListBean item, @Nullable OnRecyclerItemClickListener<CategoryWiseChecklistResponse.ListBean> listener) {
            tvInstruction.setText(item.getChecklistText());

            if (item.getDeleteable().equals("no")) {
                ivRemove.setVisibility(View.GONE);
            }

            if(!isDeleteable){
                ivRemove.setVisibility(View.GONE);
            }

            ivRemove.setOnClickListener(v -> listener.onItemClicked(item, 0));

            content.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item, 1);
                }
            });
        }
    }
}
