package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class CreateChecklistResponse {

    /**
     * checklistId : 18
     * success : {"title":"Security Checklist Create","message":"Security checklist created successfully"}
     */

    @SerializedName("checklistId")
    private int checklistId;
    @SerializedName("success")
    private SuccessBean success;

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
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
