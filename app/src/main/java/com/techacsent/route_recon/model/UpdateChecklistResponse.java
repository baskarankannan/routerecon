package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class UpdateChecklistResponse {


    /**
     * success : {"title":"Security Checklist","message":"Update successfully"}
     * checklist : {"checklistId":40,"checklistText":"Test draker","isCompleted":"yes"}
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("checklist")
    private ChecklistBean checklist;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public ChecklistBean getChecklist() {
        return checklist;
    }

    public void setChecklist(ChecklistBean checklist) {
        this.checklist = checklist;
    }

    public static class SuccessBean {
        /**
         * title : Security Checklist
         * message : Update successfully
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

    public static class ChecklistBean {
        /**
         * checklistId : 40
         * checklistText : Test draker
         * isCompleted : yes
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
