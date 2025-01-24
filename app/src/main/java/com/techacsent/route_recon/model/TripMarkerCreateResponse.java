package com.techacsent.route_recon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripMarkerCreateResponse {



    @SerializedName("success")
    @Expose
    private SuccessBean success;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public static class SuccessBean {
        @SerializedName("markerID")
        @Expose
        private Integer markerID;
        @SerializedName("locations")
        @Expose
        private List<Location> locations = null;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getMarkerID() {
            return markerID;
        }

        public void setMarkerID(Integer markerID) {
            this.markerID = markerID;
        }

        public List<Location> getLocations() {
            return locations;
        }

        public void setLocations(List<Location> locations) {
            this.locations = locations;
        }

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

        @Override
        public String toString() {
            return "SuccessBean{" +
                    "markerID=" + markerID +
                    ", locations=" + locations +
                    ", title='" + title + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }

        public static class Location{

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("lat")
            @Expose
            private Double lat;
            @SerializedName("long")
            @Expose
            private Double _long;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("address")
            @Expose
            private String address;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public Double getLong() {
                return _long;
            }

            public void setLong(Double _long) {
                this._long = _long;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            @Override
            public String toString() {
                return "Location{" +
                        "id=" + id +
                        ", lat=" + lat +
                        ", _long=" + _long +
                        ", name='" + name + '\'' +
                        ", address='" + address + '\'' +
                        '}';
            }
        }
    }

}
