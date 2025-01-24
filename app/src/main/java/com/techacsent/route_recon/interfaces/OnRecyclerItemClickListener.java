package com.techacsent.route_recon.interfaces;

public interface OnRecyclerItemClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item, int itemID);
}
