package com.techacsent.route_recon.interfaces;


import com.techacsent.route_recon.model.CreateGroupResponse;

public interface UpdateGroupNameCallBack {
    void getCallBack(CreateGroupResponse createGroupResponse);
    void getError(String error);
}
