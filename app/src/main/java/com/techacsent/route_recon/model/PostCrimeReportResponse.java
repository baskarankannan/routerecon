package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class PostCrimeReportResponse {


    /**
     * success : {"title":"","message":""}
     * data : {"serverTripId":0,"reportId":0,"reportType":1,"lat":30.2335433,"long":30.534343,"description":null,"radius":0,"address":null,"fullAddress":null,"reportImage":null,"reportGeneratedDate":""}
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("data")
    private DataBean data;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class SuccessBean {
        /**
         * title :
         * message :
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

    public static class DataBean {
        /**
         * serverTripId : 0
         * reportId : 0
         * reportType : 1
         * lat : 30.2335433
         * long : 30.534343
         * description : null
         * radius : 0
         * address : null
         * fullAddress : null
         * reportImage : null
         * reportGeneratedDate :
         */

        @SerializedName("serverTripId")
        private int serverTripId;
        @SerializedName("reportId")
        private int reportId;
        @SerializedName("reportType")
        private int reportType;
        @SerializedName("lat")
        private double lat;
        @SerializedName("long")
        private double longX;
        @SerializedName("description")
        private Object description;
        @SerializedName("radius")
        private int radius;
        @SerializedName("address")
        private Object address;
        @SerializedName("fullAddress")
        private Object fullAddress;
        @SerializedName("reportImage")
        private Object reportImage;
        @SerializedName("reportGeneratedDate")
        private String reportGeneratedDate;

        public int getServerTripId() {
            return serverTripId;
        }

        public void setServerTripId(int serverTripId) {
            this.serverTripId = serverTripId;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public int getReportType() {
            return reportType;
        }

        public void setReportType(int reportType) {
            this.reportType = reportType;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLongX() {
            return longX;
        }

        public void setLongX(double longX) {
            this.longX = longX;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(Object fullAddress) {
            this.fullAddress = fullAddress;
        }

        public Object getReportImage() {
            return reportImage;
        }

        public void setReportImage(Object reportImage) {
            this.reportImage = reportImage;
        }

        public String getReportGeneratedDate() {
            return reportGeneratedDate;
        }

        public void setReportGeneratedDate(String reportGeneratedDate) {
            this.reportGeneratedDate = reportGeneratedDate;
        }
    }
}
