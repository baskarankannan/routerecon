package com.techacsent.route_recon.interfaces;

public interface NavigationOptionInterface {
    void shareFragmentToolbar(boolean isShare);
    void tripDetailsToolbar();
    void navigationChooseToolbar(boolean isHide);
    void showProgressDialog(boolean isShown);
    void setNavigation(boolean isFromCurrentPos);
    void hideToolbar(boolean isHide);
    void shareLocation(int tripId);
}
