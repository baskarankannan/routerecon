package com.techacsent.route_recon.interfaces;

import com.techacsent.route_recon.model.FollowersResponse;

public interface OnFriendSearchListener {
    void onClick(FollowersResponse.ListBean item, int flag);
}
