package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class ErrorBody {

    /**
     * readyState : 4
     * responseText : {"error":{"title":"tripId is not set.","message":"Invalid request parameter"}}
     * responseJSON : {"error":{"title":"tripId is not set.","message":"Invalid request parameter"}}
     * status : 401
     * statusText : Unauthorized
     */

    @SerializedName("readyState")
    private int readyState;
    @SerializedName("responseText")
    private String responseText;
    @SerializedName("responseJSON")
    private ResponseJSONBean responseJSON;
    @SerializedName("status")
    private int status;
    @SerializedName("statusText")
    private String statusText;

    public int getReadyState() {
        return readyState;
    }

    public void setReadyState(int readyState) {
        this.readyState = readyState;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public ResponseJSONBean getResponseJSON() {
        return responseJSON;
    }

    public void setResponseJSON(ResponseJSONBean responseJSON) {
        this.responseJSON = responseJSON;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public static class ResponseJSONBean {
        /**
         * error : {"title":"tripId is not set.","message":"Invalid request parameter"}
         */

        @SerializedName("error")
        private ErrorBean error;

        public ErrorBean getError() {
            return error;
        }

        public void setError(ErrorBean error) {
            this.error = error;
        }

        public static class ErrorBean {
            /**
             * title : tripId is not set.
             * message : Invalid request parameter
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
}
