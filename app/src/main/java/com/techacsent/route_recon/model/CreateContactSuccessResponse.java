package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class CreateContactSuccessResponse {

    /**
     * success : {"title":"Emergency Contact","message":"Create contact successfully","data":{"id":82,"contactName":"aa","contactNo":"01999","email":"aa@aa.com"}}
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
         * title : Emergency Contact
         * message : Create contact successfully
         * data : {"id":82,"contactName":"aa","contactNo":"01999","email":"aa@aa.com"}
         */

        @SerializedName("title")
        private String title;
        @SerializedName("message")
        private String message;
        @SerializedName("data")
        private DataBean data;

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

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 82
             * contactName : aa
             * contactNo : 01999
             * email : aa@aa.com
             */

            @SerializedName("id")
            private int id;
            @SerializedName("contactName")
            private String contactName;
            @SerializedName("contactNo")
            private String contactNo;
            @SerializedName("email")
            private String email;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }
}
