package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripDetailsResponse {


    /**
     * statusmsg : success
     * data : {"id":"109","tripId":"1543216535574","refStartpointId":"769","refEndpointId":"770",
     */

    @SerializedName("statusmsg")
    private String statusmsg;
    @SerializedName("data")
    private DataBean data;

    public String getStatusmsg() {
        return statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        this.statusmsg = statusmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 109
         * tripId : 1543216535574
         * refStartpointId : 769
         * refEndpointId : 770
         * tripName : Tap
         * tripDescription : null
         * beginTime : 0000-00-00 00:00:00
         * endTime : 0000-00-00 00:00:00
         * status : 0
         * creationDate : 2018-11-26 07:16:19
         * updationDate : 2018-11-26 07:16:19
         * startpoint : {"id":"769","refTripId":"109","lat":23.485466003418,"long":89.419830322266,"type":1,"address":"Magura, Bangladesh","fullAddress":"Magura, Bangladesh"}
         * endpoint : {"id":"770","refTripId":"109","lat":24.158504486084,"long":89.44807434082,"type":1,"address":"Pabna District, Bangladesh","fullAddress":"Pabna District, Bangladesh"}
         * wayPoints : [{"id":"771","refTripId":"109","lat":23.768957138062,"long":89.08464050293,"type":1,"address":"Hatia - Gopalpur Rd, Bangladesh","fullAddress":"Hatia - Gopalpur Rd, Bangladesh, Kustia Sadar Upazila, Bangladesh"}]
         */

        @SerializedName("id")
        private String id;
        @SerializedName("tripId")
        private String tripId;
        @SerializedName("refStartpointId")
        private String refStartpointId;
        @SerializedName("refEndpointId")
        private String refEndpointId;
        @SerializedName("tripName")
        private String tripName;
        @SerializedName("tripDescription")
        private Object tripDescription;
        @SerializedName("beginTime")
        private String beginTime;
        @SerializedName("endTime")
        private String endTime;
        @SerializedName("status")
        private String status;
        @SerializedName("creationDate")
        private String creationDate;
        @SerializedName("updationDate")
        private String updationDate;
        @SerializedName("startpoint")
        private StartpointBean startpoint;
        @SerializedName("endpoint")
        private EndpointBean endpoint;
        @SerializedName("tripJson")
        private String tripJson;
        @SerializedName("wayPoints")
        private List<WayPointsBean> wayPoints;
        @SerializedName("markers")
        private List<MarkersBean> markers;
        @SerializedName("points")
        private List<?> points;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTripId() {
            return tripId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public String getRefStartpointId() {
            return refStartpointId;
        }

        public void setRefStartpointId(String refStartpointId) {
            this.refStartpointId = refStartpointId;
        }

        public String getRefEndpointId() {
            return refEndpointId;
        }

        public void setRefEndpointId(String refEndpointId) {
            this.refEndpointId = refEndpointId;
        }

        public String getTripName() {
            return tripName;
        }

        public void setTripName(String tripName) {
            this.tripName = tripName;
        }

        public Object getTripDescription() {
            return tripDescription;
        }

        public void setTripDescription(Object tripDescription) {
            this.tripDescription = tripDescription;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getUpdationDate() {
            return updationDate;
        }

        public void setUpdationDate(String updationDate) {
            this.updationDate = updationDate;
        }

        public StartpointBean getStartpoint() {
            return startpoint;
        }

        public void setStartpoint(StartpointBean startpoint) {
            this.startpoint = startpoint;
        }

        public EndpointBean getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(EndpointBean endpoint) {
            this.endpoint = endpoint;
        }

        public String getTripJson() {
            return tripJson;
        }

        public void setTripJson(String tripJson) {
            this.tripJson = tripJson;
        }

        public List<WayPointsBean> getWayPoints() {
            return wayPoints;
        }

        public void setWayPoints(List<WayPointsBean> wayPoints) {
            this.wayPoints = wayPoints;
        }

        public List<MarkersBean> getMarkers() {
            return markers;
        }

        public void setMarkers(List<MarkersBean> markers) {
            this.markers = markers;
        }

        public List<?> getPoints() {
            return points;
        }

        public void setPoints(List<?> points) {
            this.points = points;
        }

        public static class StartpointBean {
            /**
             * id : 769
             * refTripId : 109
             * lat : 23.485466003418
             * long : 89.419830322266
             * type : 1
             * address : Magura, Bangladesh
             * fullAddress : Magura, Bangladesh
             */

            @SerializedName("id")
            private String id;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("type")
            private int type;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
        }

        public static class EndpointBean {
            /**
             * id : 770
             * refTripId : 109
             * lat : 24.158504486084
             * long : 89.44807434082
             * type : 1
             * address : Pabna District, Bangladesh
             * fullAddress : Pabna District, Bangladesh
             */

            @SerializedName("id")
            private String id;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("type")
            private int type;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
        }

        public static class WayPointsBean {
            /**
             * id : 771
             * refTripId : 109
             * lat : 23.768957138062
             * long : 89.08464050293
             * type : 1
             * address : Hatia - Gopalpur Rd, Bangladesh
             * fullAddress : Hatia - Gopalpur Rd, Bangladesh, Kustia Sadar Upazila, Bangladesh
             */

            @SerializedName("id")
            private String id;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("type")
            private int type;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
        }

        public static class MarkersBean {
            /**
             * id : 1014
             * userId : 6
             * refTripId : 109
             * markType : 2
             * lat : 23.880676269531
             * long : 90.463043212891
             * radius : 0
             * address : Talia Mashjid Rd, Talia, Bangladesh
             * fullAddress : Talia Mashjid Rd, Talia, Bangladesh, Talia, Bangladesh
             * description :
             * tripSpecific : 1
             * landmarkImage : []
             */

            @SerializedName("id")
            private String id;
            @SerializedName("userId")
            private String userId;
            @SerializedName("refTripId")
            private String refTripId;
            @SerializedName("markType")
            private int markType;
            @SerializedName("lat")
            private double lat;
            @SerializedName("long")
            private double longX;
            @SerializedName("radius")
            private int radius;
            @SerializedName("address")
            private String address;
            @SerializedName("fullAddress")
            private String fullAddress;
            @SerializedName("description")
            private String description;
            @SerializedName("tripSpecific")
            private int tripSpecific;
            @SerializedName("landmarkImage")
            private List<?> landmarkImage;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getRefTripId() {
                return refTripId;
            }

            public void setRefTripId(String refTripId) {
                this.refTripId = refTripId;
            }

            public int getMarkType() {
                return markType;
            }

            public void setMarkType(int markType) {
                this.markType = markType;
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

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
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

            public int getTripSpecific() {
                return tripSpecific;
            }

            public void setTripSpecific(int tripSpecific) {
                this.tripSpecific = tripSpecific;
            }

            public List<?> getLandmarkImage() {
                return landmarkImage;
            }

            public void setLandmarkImage(List<?> landmarkImage) {
                this.landmarkImage = landmarkImage;
            }
        }
    }
}
