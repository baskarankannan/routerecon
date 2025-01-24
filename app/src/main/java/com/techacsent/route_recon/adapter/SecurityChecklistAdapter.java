package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.SecurityChecklistResponse;

public class SecurityChecklistAdapter extends BaseRecyclerAdapter<SecurityChecklistResponse.ChecklistBean,
        OnRecyclerItemClickListener<SecurityChecklistResponse.ChecklistBean>, SecurityChecklistAdapter.SecurityChecklistViewHolder> {

    public SecurityChecklistAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SecurityChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SecurityChecklistViewHolder(inflate(R.layout.item_security_checklist, viewGroup));
    }

    class SecurityChecklistViewHolder extends BaseViewHolder<SecurityChecklistResponse.ChecklistBean, OnRecyclerItemClickListener<SecurityChecklistResponse.ChecklistBean>> {
        TextView tvCheckList;
        ImageView ivUpdate;
        CheckBox chSelect;

        SecurityChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCheckList = itemView.findViewById(R.id.tv_checkList);
            ivUpdate = itemView.findViewById(R.id.iv_update);
            chSelect = itemView.findViewById(R.id.chk_select);
        }

        @Override
        public void onBind(SecurityChecklistResponse.ChecklistBean item, @Nullable OnRecyclerItemClickListener<SecurityChecklistResponse.ChecklistBean> listener) {
            tvCheckList.setText(item.getChecklistText());
            ivUpdate.setVisibility(View.VISIBLE);
            chSelect.setVisibility(View.VISIBLE);

            if (item.getIsCompleted().equals("yes")) {
                chSelect.setChecked(true);
            } else {
                chSelect.setChecked(false);
            }
            ivUpdate.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item, getAdapterPosition());
                }
            });

            chSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    if (isChecked) {
                        listener.onItemClicked(item, -1);
                    } else {
                        listener.onItemClicked(item, -2);

                    }
                }
            });
        }
    }

}
