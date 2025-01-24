package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class  CreateTripMarkerModelClass {


    /**
     * markType : 4
     * tripId : 247
     * lat : 23.749574947559946
     * long : 90.45475449545
     * radius : 0
     * address :
     * fullAddress :
     * description :
     * locations : [{"lat":23.7343085647583,"long":90.339644165039},{"lat":23.7343085647583,"long":90.339644165039},{"lat":23.7343085647583,"long":90.339644165039}]
     */

    @SerializedName("markType")
    private int markType;
    @SerializedName("tripId")
    private int tripId;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double longX;
    @SerializedName("radius")
    private double radius;
    @SerializedName("address")
    private String address;
    @SerializedName("fullAddress")
    private String fullAddress;
    @SerializedName("description")
    private String description;

    @SerializedName("zone_name")
    private String zone_name;
    @SerializedName("zone_length")
    private String zone_length;
    @SerializedName("zone_area")
    private String zone_area;

    @SerializedName("locations")
    private List<LocationsBean> locations;

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
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


    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public String getZone_length() {
        return zone_length;
    }

    public void setZone_length(String zone_length) {
        this.zone_length = zone_length;
    }

    public String getZone_area() {
        return zone_area;
    }

    public void setZone_area(String zone_area) {
        this.zone_area = zone_area;
    }

    public List<LocationsBean> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationsBean> locations) {
        this.locations = locations;
    }

    public static class LocationsBean {


        @SerializedName("lat")
        private double lat;
        @SerializedName("long")
        private double longX;

        @SerializedName("name")
        private String name;
        @SerializedName("address")
        private String address;

       /* @SerializedName("id")
        private int id;
*/
        public LocationsBean() {
        }

        public LocationsBean(double lat, double longX, String name, String address) {
            this.lat = lat;
            this.longX = longX;
            this.name = name;
            this.address = address;
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

      /*  public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
*/

    }


}
