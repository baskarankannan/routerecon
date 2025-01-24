package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.CustomChecklistResponse;

public class CustomChecklistAdapter extends BaseRecyclerAdapter<CustomChecklistResponse.CustomChecklistBean,
        OnRecyclerClickListener<CustomChecklistResponse.CustomChecklistBean>, CustomChecklistAdapter.CustomChecklisViewHolder>{


    public CustomChecklistAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public CustomChecklisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomChecklisViewHolder(inflate(R.layout.item_categorized_checklist, viewGroup));
    }

    class CustomChecklisViewHolder extends BaseViewHolder<CustomChecklistResponse.CustomChecklistBean, OnRecyclerClickListener<CustomChecklistResponse.CustomChecklistBean>> {
        LinearLayout layoutContent;
        TextView tvInstruction;

        CustomChecklisViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutContent = itemView.findViewById(R.id.layout_content);
            tvInstruction = itemView.findViewById(R.id.tv_checkList);
        }

        @Override
        public void onBind(CustomChecklistResponse.CustomChecklistBean item, @Nullable OnRecyclerClickListener<CustomChecklistResponse.CustomChecklistBean> listener) {
            tvInstruction.setText(item.getDescription());

            layoutContent.setOnClickListener(v -> {
                if(listener!=null){
                    listener.onItemClicked(item);
                }

            });
        }
    }
}
