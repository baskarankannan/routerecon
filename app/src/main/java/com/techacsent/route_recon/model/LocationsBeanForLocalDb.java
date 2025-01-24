package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public  class LocationsBeanForLocalDb{


        @SerializedName("lat")
        private double lat;
        @SerializedName("long")
        private double longX;

        @SerializedName("name")
        private String name;
        @SerializedName("address")
        private String address;

        @SerializedName("id")
        private int id;
        
        public LocationsBeanForLocalDb() {
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }