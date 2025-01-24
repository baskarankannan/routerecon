package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SetBadgeModel {

    @SerializedName("badges")
    private List<BadgesBean> badges;

    public List<BadgesBean> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgesBean> badges) {
        this.badges = badges;
    }

    public static class BadgesBean {
        /**
         * badge_type : location-share
         * badge_count : 11
         */

        @SerializedName("badge_type")
        private String badgeType;
        @SerializedName("badge_count")
        private int badgeCount;

        public String getBadgeType() {
            return badgeType;
        }

        public void setBadgeType(String badgeType) {
            this.badgeType = badgeType;
        }

        public int getBadgeCount() {
            return badgeCount;
        }

        public void setBadgeCount(int badgeCount) {
            this.badgeCount = badgeCount;
        }
    }
}
