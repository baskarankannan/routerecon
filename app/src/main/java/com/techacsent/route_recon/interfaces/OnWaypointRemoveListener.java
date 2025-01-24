package com.techacsent.route_recon.interfaces;

import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;

public interface OnWaypointRemoveListener {
    void onWaypointRemove(int position, CreateTripModelClass.WayPointsBean wayPointsBean);
}
