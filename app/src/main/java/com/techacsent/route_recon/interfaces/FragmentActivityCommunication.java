package com.techacsent.route_recon.interfaces;

import com.techacsent.route_recon.model.TripListResponse;

import java.util.List;

public interface FragmentActivityCommunication {
    void hideBottomNav(boolean isHide);
    void showProgressDialog(boolean isShown);
    void fragmentToolbarbyPosition(int pos);
    void setTitle(String title);
    void showDone(boolean isShow);

}
