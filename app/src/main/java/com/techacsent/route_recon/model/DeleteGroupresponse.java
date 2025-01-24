package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class DeleteGroupresponse {

    /**
     * success : {"title":"Group","message":"Delete successfully"}
     */

    @SerializedName("success")
    private SuccessBean success;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public static class SuccessBean {
        /**
         * title : Group
         * message : Delete successfully
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
