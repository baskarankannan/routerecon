package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmergencyContactListResponse {

    /**
     * success : {"title":"Emergency Call","contacts":[{"id":59,"contactName":"Alik","contactNo":"01966261868"},{"id":60,"contactName":"Aman","contactNo":"+880 1981-217831"},{"id":61,"contactName":"Aman","contactNo":"+880 1981-217831"},{"id":62,"contactName":"Aman","contactNo":"+880 1981-217831"},{"id":63,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":64,"contactName":"Ashick","contactNo":"01789945377"},{"id":65,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":66,"contactName":"Babul vanga","contactNo":"+8801926547732"},{"id":67,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":68,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":69,"contactName":"Aman","contactNo":"+880 1981-217831"}]}
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
         * title : Emergency Call
         * contacts : [{"id":59,"contactName":"Alik","contactNo":"01966261868"},{"id":60,"contactName":"Aman","contactNo":"+880 1981-217831"},{"id":61,"contactName":"Aman","contactNo":"+880 1981-217831"},{"id":62,"contactName":"Aman","contactNo":"+880 1981-217831"},{"id":63,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":64,"contactName":"Ashick","contactNo":"01789945377"},{"id":65,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":66,"contactName":"Babul vanga","contactNo":"+8801926547732"},{"id":67,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":68,"contactName":"Baba Gp","contactNo":"+8801725802890"},{"id":69,"contactName":"Aman","contactNo":"+880 1981-217831"}]
         */

        @SerializedName("title")
        private String title;
        @SerializedName("contacts")
        private List<ContactsBean> contacts;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ContactsBean> getContacts() {
            return contacts;
        }

        public void setContacts(List<ContactsBean> contacts) {
            this.contacts = contacts;
        }

        public static class ContactsBean {
            /**
             * id : 59
             * contactName : Alik
             * contactNo : 01966261868
             */

            @SerializedName("id")
            private int id;
            @SerializedName("contactName")
            private String contactName;
            @SerializedName("contactNo")
            private String contactNo;
            @SerializedName("email")
            private String email;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

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
        }
    }
}
