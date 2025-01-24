package com.techacsent.route_recon.interfaces;

public interface OnEmergencyContactItemClickListener<T> extends BaseRecyclerListener {

    void onItemClicked(T item, int itemID, int pos);
}
