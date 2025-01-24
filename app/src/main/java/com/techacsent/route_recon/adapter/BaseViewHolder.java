package com.techacsent.route_recon.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.techacsent.route_recon.interfaces.BaseRecyclerListener;

public abstract class BaseViewHolder<T,L extends BaseRecyclerListener> extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBind(T item, @Nullable L listener);
}
