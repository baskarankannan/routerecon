package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateTripesponse {

    /**
     * title : Trip
     * message : Update trip successfully
     * data : {"id":"120","tripId":"dtesstTrip","refStartpointId":"394","refEndpointId":"395","tripName":"wsssttseeklyTrip","tripDescription":"nice trip","beginTime":"2018-07-30 16:00:09","endTime":"2018-07-30 16:00:09","status":"0","creationDate":"2018-11-02 08:19:24","updationDate":"2018-11-02 08:19:24","startpoint":{"id":"394","refTripId":"120","lat":23.792085647583,"long":90.369644165039,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"},"endpoint":{"id":"395","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"},"wayPoints":[{"id":"396","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}],"markers":[{"id":"84","userId":"1","refTripId":"120","markType":1,"lat":123.79208564758,"long":190.36964416504,"radius":4,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh","description":"markers added","tripSpecific":1,"landmarkImage":[]}],"points":[]}
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
         * id : 120
         * tripId : dtesstTrip
         * refStartpointId : 394
         * refEndpointId : 395
         * tripName : wsssttseeklyTrip
         * tripDescription : nice trip
         * beginTime : 2018-07-30 16:00:09
         * endTime : 2018-07-30 16:00:09
         * status : 0
         * creationDate : 2018-11-02 08:19:24
         * updationDate : 2018-11-02 08:19:24
         * startpoint : {"id":"394","refTripId":"120","lat":23.792085647583,"long":90.369644165039,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}
         * endpoint : {"id":"395","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}
         * wayPoints : [{"id":"396","refTripId":"120","lat":123.79208374023,"long":190.36964416504,"type":1,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh"}]
         * markers : [{"id":"84","userId":"1","refTripId":"120","markType":1,"lat":123.79208564758,"long":190.36964416504,"radius":4,"address":"Dhaka","fullAddress":"Dhaka, Dhaka, Bangladesh","description":"markers added","tripSpecific":1,"landmarkImage":[]}]
         * points : []
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
        private String tripDescription;
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

        public String getTripDescription() {
            return tripDescription;
        }

        public void setTripDescription(String tripDescription) {
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
             * id : 394
             * refTripId : 120
             * lat : 23.792085647583
             * long : 90.369644165039
             * type : 1
             * address : Dhaka
             * fullAddress : Dhaka, Dhaka, Bangladesh
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
             * id : 395
             * refTripId : 120
             * lat : 123.79208374023
             * long : 190.36964416504
             * type : 1
             * address : Dhaka
             * fullAddress : Dhaka, Dhaka, Bangladesh
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
             * id : 396
             * refTripId : 120
             * lat : 123.79208374023
             * long : 190.36964416504
             * type : 1
             * address : Dhaka
             * fullAddress : Dhaka, Dhaka, Bangladesh
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
             * id : 84
             * userId : 1
             * refTripId : 120
             * markType : 1
             * lat : 123.79208564758
             * long : 190.36964416504
             * radius : 4
             * address : Dhaka
             * fullAddress : Dhaka, Dhaka, Bangladesh
             * description : markers added
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
