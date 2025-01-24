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
import com.techacsent.route_recon.model.SystemSecurityModel;

public class AddChecklistAdapter extends BaseRecyclerAdapter<SystemSecurityModel, OnRecyclerClickListener<SystemSecurityModel>, AddChecklistAdapter.SystemChecklistViewHolder> {

    public AddChecklistAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SystemChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SystemChecklistViewHolder(inflate(R.layout.item_security_checklist, viewGroup));
    }

    class SystemChecklistViewHolder extends BaseViewHolder<SystemSecurityModel, OnRecyclerClickListener<SystemSecurityModel>> {
        LinearLayout frameLayout;
        TextView tvCheckList;

        SystemChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_content);
            tvCheckList = itemView.findViewById(R.id.tv_checkList);
        }

        @Override
        public void onBind(SystemSecurityModel item, @Nullable OnRecyclerClickListener<SystemSecurityModel> listener) {
            tvCheckList.setText(item.getDescripion());
            frameLayout.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item);
                }
            });
        }
    }
}
