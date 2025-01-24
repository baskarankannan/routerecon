package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class CreateTripChecklistResponse {

    /**
     * success : {"title":"Security Checklist","message":"Checklist added with trip"}
     * checklist : {"tripChecklistId":20,"checklistId":44,"isCompleted":"no"}
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
         * message : Checklist added with trip
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
         * tripChecklistId : 20
         * checklistId : 44
         * isCompleted : no
         */

        @SerializedName("tripChecklistId")
        private int tripChecklistId;
        @SerializedName("checklistId")
        private int checklistId;
        @SerializedName("isCompleted")
        private String isCompleted;

        public int getTripChecklistId() {
            return tripChecklistId;
        }

        public void setTripChecklistId(int tripChecklistId) {
            this.tripChecklistId = tripChecklistId;
        }

        public int getChecklistId() {
            return checklistId;
        }

        public void setChecklistId(int checklistId) {
            this.checklistId = checklistId;
        }

        public String getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(String isCompleted) {
            this.isCompleted = isCompleted;
        }
    }
}
