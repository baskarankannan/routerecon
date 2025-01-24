package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomChecklistResponse {

    /**
     * title : Custom Security Checklist
     * customChecklist : [{"checklistId":147,"title":"AA","description":"test"}]
     */

    @SerializedName("title")
    private String title;
    @SerializedName("customChecklist")
    private List<CustomChecklistBean> customChecklist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CustomChecklistBean> getCustomChecklist() {
        return customChecklist;
    }

    public void setCustomChecklist(List<CustomChecklistBean> customChecklist) {
        this.customChecklist = customChecklist;
    }

    public static class CustomChecklistBean {
        /**
         * checklistId : 147
         * title : AA
         * description : test
         */

        @SerializedName("checklistId")
        private int checklistId;
        @SerializedName("title")
        private String title;
        @SerializedName("description")
        private String description;

        public int getChecklistId() {
            return checklistId;
        }

        public void setChecklistId(int checklistId) {
            this.checklistId = checklistId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
