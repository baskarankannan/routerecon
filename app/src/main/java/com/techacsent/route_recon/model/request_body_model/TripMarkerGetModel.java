package com.techacsent.route_recon.model.request_body_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TripMarkerGetModel implements Parcelable {

    /**
     * selat : 23.82965360478005
     * selon : 90.33371252318648
     * nwlat : 23.63971526935684
     * nwlon : 90.4620623951048
     * limit : 10
     * offset : 0
     * tripSpecific : 0
     */

    @SerializedName("selat")
    private double selat;
    @SerializedName("selon")
    private double selon;
    @SerializedName("nwlat")
    private double nwlat;
    @SerializedName("nwlon")
    private double nwlon;
    @SerializedName("limit")
    private String limit;
    @SerializedName("offset")
    private String offset;
    @SerializedName("tripSpecific")
    private String tripSpecific;

    public TripMarkerGetModel() {
    }

    protected TripMarkerGetModel(Parcel in) {
        selat = in.readDouble();
        selon = in.readDouble();
        nwlat = in.readDouble();
        nwlon = in.readDouble();
        limit = in.readString();
        offset = in.readString();
        tripSpecific = in.readString();
    }

    public static final Creator<TripMarkerGetModel> CREATOR = new Creator<TripMarkerGetModel>() {
        @Override
        public TripMarkerGetModel createFromParcel(Parcel in) {
            return new TripMarkerGetModel(in);
        }

        @Override
        public TripMarkerGetModel[] newArray(int size) {
            return new TripMarkerGetModel[size];
        }
    };

    public double getSelat() {
        return selat;
    }

    public void setSelat(double selat) {
        this.selat = selat;
    }

    public double getSelon() {
        return selon;
    }

    public void setSelon(double selon) {
        this.selon = selon;
    }

    public double getNwlat() {
        return nwlat;
    }

    public void setNwlat(double nwlat) {
        this.nwlat = nwlat;
    }

    public double getNwlon() {
        return nwlon;
    }

    public void setNwlon(double nwlon) {
        this.nwlon = nwlon;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTripSpecific() {
        return tripSpecific;
    }

    public void setTripSpecific(String tripSpecific) {
        this.tripSpecific = tripSpecific;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(selat);
        dest.writeDouble(selon);
        dest.writeDouble(nwlat);
        dest.writeDouble(nwlon);
        dest.writeString(limit);
        dest.writeString(offset);
        dest.writeString(tripSpecific);
    }
}
