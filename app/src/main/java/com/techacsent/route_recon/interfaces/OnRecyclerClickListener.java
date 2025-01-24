package com.techacsent.route_recon.interfaces;

public interface OnRecyclerClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item);
}
