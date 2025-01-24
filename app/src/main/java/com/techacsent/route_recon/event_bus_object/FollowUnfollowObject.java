package com.techacsent.route_recon.event_bus_object;

public class FollowUnfollowObject {
    private String amFollower;
    private int position;
    private boolean isChangeState;

    public FollowUnfollowObject(String amFollower, int position, boolean isChangeState) {
        this.amFollower = amFollower;
        this.position = position;
        this.isChangeState = isChangeState;
    }

    public String getAmFollower() {
        return amFollower;
    }

    public int getPosition() {
        return position;
    }

    public boolean isChangeState() {
        return isChangeState;
    }
}
