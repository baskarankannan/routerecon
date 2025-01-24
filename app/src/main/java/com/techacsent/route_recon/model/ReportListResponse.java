package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportListResponse {

    @SerializedName("markerReportList")
    private List<MarkerReportListBean> markerReportList;

    public List<MarkerReportListBean> getMarkerReportList() {
        return markerReportList;
    }

    public void setMarkerReportList(List<MarkerReportListBean> markerReportList) {
        this.markerReportList = markerReportList;
    }

    public static class MarkerReportListBean {
        /**
         * id : 2
         * reportType : 1
         * name : bad_route
         * iconImg : http://travelplannerbackend.sol/get-reporticon-image/2
         */

        @SerializedName("id")
        private int id;
        @SerializedName("reportType")
        private int reportType;
        @SerializedName("name")
        private String name;
        @SerializedName("iconImg")
        private String iconImg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getReportType() {
            return reportType;
        }

        public void setReportType(int reportType) {
            this.reportType = reportType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIconImg() {
            return iconImg;
        }

        public void setIconImg(String iconImg) {
            this.iconImg = iconImg;
        }
    }
}
