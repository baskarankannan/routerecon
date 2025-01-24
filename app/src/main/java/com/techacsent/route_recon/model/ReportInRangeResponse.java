package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportInRangeResponse {


    /**
     * success : {"title":"Report feedback","message":"Display report list with feedback"}
     * list : [{"reportId":339,"lat":20,"long":20,"address":"Bogura","fullAddress":"Bogura, BD","description":"test","likeCount":4,"unlikeCount":3}]
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("list")
    private List<ListBean> list;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class SuccessBean {
        /**
         * title : Report feedback
         * message : Display report list with feedback
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

    public static class ListBean {
        /**
         * reportId : 339
         * lat : 20
         * long : 20
         * address : Bogura
         * fullAddress : Bogura, BD
         * description : test
         * likeCount : 4
         * unlikeCount : 3
         */

        @SerializedName("reportId")
        private int reportId;
        @SerializedName("reportType")
        private int reportType;
        @SerializedName("lat")
        private double lat;
        @SerializedName("long")
        private double longX;
        @SerializedName("address")
        private String address;
        @SerializedName("fullAddress")
        private String fullAddress;
        @SerializedName("description")
        private String description;
        @SerializedName("likeCount")
        private int likeCount;
        @SerializedName("unlikeCount")
        private int unlikeCount;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getUnlikeCount() {
            return unlikeCount;
        }

        public void setUnlikeCount(int unlikeCount) {
            this.unlikeCount = unlikeCount;
        }
    }
}
