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
import com.techacsent.route_recon.interfaces.OnEmergencyContactItemClickListener;
import com.techacsent.route_recon.room_db.entity.ContactDescription;

public class EmergencyContactAdapter extends BaseRecyclerAdapter<ContactDescription, OnEmergencyContactItemClickListener<ContactDescription>, EmergencyContactAdapter.EmergencyContactViewHolder> {

    public EmergencyContactAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public EmergencyContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EmergencyContactViewHolder(inflate(R.layout.emergency_contact_item, viewGroup));
    }

    class EmergencyContactViewHolder extends BaseViewHolder<ContactDescription, OnEmergencyContactItemClickListener<ContactDescription>> {
        LinearLayout frameContent;
        TextView tvContactName;
        TextView tvContactNo;
        ImageView ivCall;
        ImageView ivEdit;
        ImageView ivDelete;

        EmergencyContactViewHolder(View itemView) {
            super(itemView);
            frameContent = itemView.findViewById(R.id.content);
            tvContactName = itemView.findViewById(R.id.tv_name);
            tvContactNo = itemView.findViewById(R.id.tv_contact_no);
            ivCall = itemView.findViewById(R.id.iv_call);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_delete);

        }

        @Override
        public void onBind(ContactDescription item, @Nullable OnEmergencyContactItemClickListener<ContactDescription> listener) {
            tvContactName.setText(item.getContactName());
            tvContactNo.setText(item.getContactNo());
            ivCall.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item,0, getAdapterPosition());
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClicked(item,1,getAdapterPosition());
                    }
                }
            });

            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClicked(item,2,getAdapterPosition());
                    }
                }
            });

            //ivDelete.setOnClickListener(v -> listener.onItemClicked(item,1));

        }
    }
}
