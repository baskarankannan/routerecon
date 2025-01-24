package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.SystemSecurityModel;

public class SystemChecklistCategoryAdapter extends BaseRecyclerAdapter<SystemSecurityModel, OnRecyclerItemClickListener<SystemSecurityModel>, SystemChecklistCategoryAdapter.SystemChecklistViewHolder> {

    public SystemChecklistCategoryAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SystemChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SystemChecklistViewHolder(inflate(R.layout.item_security_checklist_category, viewGroup));
    }

    class SystemChecklistViewHolder extends BaseViewHolder<SystemSecurityModel, OnRecyclerItemClickListener<SystemSecurityModel>> {
        FrameLayout frameLayout;
        TextView tvCheckList;

        SystemChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_content);
            tvCheckList = itemView.findViewById(R.id.tv_checkList);
        }

        @Override
        public void onBind(SystemSecurityModel item, @Nullable OnRecyclerItemClickListener<SystemSecurityModel> listener) {
            tvCheckList.setText(item.getDescripion());
            frameLayout.setOnClickListener(v -> listener.onItemClicked(item,getAdapterPosition()));
        }
    }
}
