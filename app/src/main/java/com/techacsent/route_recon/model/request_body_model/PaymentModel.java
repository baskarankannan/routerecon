package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class PaymentModel {

    /**
     * package_id : 1
     * payable_amount : 2.9
     * card_number : 4242424242424242
     * expiration_month : 8
     * expiration_year : 2019
     * cvc : 121
     * billing_zip : 123
     * user_ip :
     * device_id : asdfasdf
     * recipient_email :
     * message :
     */

    @SerializedName("package_id")
    private int packageId;
    @SerializedName("payable_amount")
    private double payableAmount;
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("expiration_month")
    private int expirationMonth;
    @SerializedName("expiration_year")
    private int expirationYear;
    @SerializedName("cvc")
    private String cvc;
    @SerializedName("billing_zip")
    private String billingZip;
    @SerializedName("user_ip")
    private String userIp;
    @SerializedName("device_id")
    private String deviceId;
    @SerializedName("recipient_email")
    private String recipientEmail;
    @SerializedName("message")
    private String message;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
