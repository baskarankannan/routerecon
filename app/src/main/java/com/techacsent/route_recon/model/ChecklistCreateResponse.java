package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class ChecklistCreateResponse {

    /**
     * checklist : {"checklistId":321,"checklistText":"tesdddt","deleteable":"no"}
     * success : {"title":"Security Checklist Create","message":"Security checklist created successfully"}
     */

    @SerializedName("checklist")
    private ChecklistBean checklist;
    @SerializedName("success")
    private SuccessBean success;

    public ChecklistBean getChecklist() {
        return checklist;
    }

    public void setChecklist(ChecklistBean checklist) {
        this.checklist = checklist;
    }

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public static class ChecklistBean {
        /**
         * checklistId : 321
         * checklistText : tesdddt
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

    public static class SuccessBean {
        /**
         * title : Security Checklist Create
         * message : Security checklist created successfully
         */

        @SerializedName("title")
        private String title;
        @SerializedName("message")
        private String message;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
