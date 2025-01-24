package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    /**
     * packages : {"packageId":1,"packagePrice":290,"currentDateTime":"2019-06-27 13:32:25"}
     * success : {"title":"Stripe payment","message":"Stripe payment successfully"}
     */

    @SerializedName("packages")
    private PackagesBean packages;
    @SerializedName("success")
    private SuccessBean success;

    public PackagesBean getPackages() {
        return packages;
    }

    public void setPackages(PackagesBean packages) {
        this.packages = packages;
    }

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public static class PackagesBean {
        /**
         * packageId : 1
         * packagePrice : 290
         * currentDateTime : 2019-06-27 13:32:25
         */

        @SerializedName("packageId")
        private int packageId;
        @SerializedName("packagePrice")
        private double packagePrice;
        @SerializedName("currentDateTime")
        private String currentDateTime;

        public int getPackageId() {
            return packageId;
        }

        public void setPackageId(int packageId) {
            this.packageId = packageId;
        }

        public double getPackagePrice() {
            return packagePrice;
        }

        public void setPackagePrice(double packagePrice) {
            this.packagePrice = packagePrice;
        }

        public String getCurrentDateTime() {
            return currentDateTime;
        }

        public void setCurrentDateTime(String currentDateTime) {
            this.currentDateTime = currentDateTime;
        }
    }

    public static class SuccessBean {
        /**
         * title : Stripe payment
         * message : Stripe payment successfully
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
