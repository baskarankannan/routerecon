package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecurityChecklistResponse {


    /**
     * title : Tripwise Security Checklist
     * checklist : [{"checklistId":107,"checklistText":"2 full-size spare tire","isCompleted":"no"},{"checklistId":108,"checklistText":"4 cans of a Fix-A-Flat","isCompleted":"no"}]
     */

    @SerializedName("title")
    private String title;
    @SerializedName("checklist")
    private List<ChecklistBean> checklist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChecklistBean> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<ChecklistBean> checklist) {
        this.checklist = checklist;
    }

    public static class ChecklistBean {
        /**
         * checklistId : 107
         * checklistText : 2 full-size spare tire
         * isCompleted : no
         */

        @SerializedName("checklistId")
        private int checklistId;
        @SerializedName("checklistText")
        private String checklistText;
        @SerializedName("isCompleted")
        private String isCompleted;

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

        public String getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(String isCompleted) {
            this.isCompleted = isCompleted;
        }
    }
}
