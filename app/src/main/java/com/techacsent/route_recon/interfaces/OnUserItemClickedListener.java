package com.techacsent.route_recon.interfaces;

import com.techacsent.route_recon.model.UserSearchResponse;

public interface OnUserItemClickedListener {
    void onUserItemClicked(UserSearchResponse.ListBean.UserBean listBean, int position, boolean isShowDialog, String action);
}
