package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryWiseChecklistResponse {

    @SerializedName("list")
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * checklistId : 1
         * checklistText : Do you have at least three different routes so you can vary your route to and from work and all other primary areas?
         * deleteable : no
         */

        @SerializedName("checklistId")
        private int checklistId;
        @SerializedName("checklistText")
        private String checklistText;
        @SerializedName("deleteable")
        private String deleteable;

        public int getChecklistId() {
            return checklistId;
        }

        public void setChecklistId(int checklistId) {
            this.checklistId = checklistId;
        }

        public String getChecklistText() {
            return checklistText;
        }

        public void setChecklistText(String checklistText) {
            this.checklistText = checklistText;
        }

        public String getDeleteable() {
            return deleteable;
        }

        public void setDeleteable(String deleteable) {
            this.deleteable = deleteable;
        }
    }
}
