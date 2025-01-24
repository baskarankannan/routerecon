package com.techacsent.route_recon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techacsent.route_recon.interfaces.BaseRecyclerListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, L extends BaseRecyclerListener, VH extends BaseViewHolder<T, L>> extends RecyclerView.Adapter<VH> {
    List<T> items;
    private L listener;
    private LayoutInflater layoutInflater;
    private int position;

    public BaseRecyclerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull VH vh, @SuppressLint("RecyclerView") int i) {
        T item = items.get(i);
        vh.onBind(item, listener);
        position = i;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot set `null` item to the Recycler adapter");
        }
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    public int getAdapterPosition() {
        return position;
    }

    public void updatePosition(int pos, T item) {
        items.set(pos, item);
        notifyItemChanged(pos, item);
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to the Recycler adapter");
        }
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot add `null` items to the Recycler adapter");
        }
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size() - items.size(), items.size());
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void itemMoved(int oldPosition, int newPosition) {
        T item = items.get(oldPosition);
        items.remove(item);
        items.add(newPosition, item);
        notifyItemMoved(oldPosition, newPosition);
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    public void setListener(L listener) {
        this.listener = listener;
    }

    @NonNull
    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
        return layoutInflater.inflate(layout, parent, attachToRoot);
    }

    @NonNull
    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout, parent, false);
    }
}
