package com.techacsent.route_recon.interfaces;

import com.techacsent.route_recon.model.GroupListResponse;

public interface OnGroupClickListener {

    void onClick(GroupListResponse.ListBean listBean, int flag);
}
