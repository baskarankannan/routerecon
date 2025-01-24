package com.techacsent.route_recon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageResponse {

    @SerializedName("allPackage")
    private List<AllPackageBean> allPackage;

    public List<AllPackageBean> getAllPackage() {
        return allPackage;
    }

    public void setAllPackage(List<AllPackageBean> allPackage) {
        this.allPackage = allPackage;
    }

    public static class AllPackageBean implements Parcelable {
        /**
         * id : 1
         * packageName : superPackage
         * price : 450
         * currency : BDT
         * packageType : monthly
         */

        @SerializedName("id")
        private int id;
        @SerializedName("packageName")
        private String packageName;
        @SerializedName("price")
        private double price;
        @SerializedName("currency")
        private String currency;
        @SerializedName("packageType")
        private String packageType;
        @SerializedName("packageUpgradeInfo")
        private String packageUpgradeInfo;

        protected AllPackageBean(Parcel in) {
            id = in.readInt();
            packageName = in.readString();
            price = in.readDouble();
            currency = in.readString();
            packageType = in.readString();
            packageUpgradeInfo = in.readString();
        }

        public static final Creator<AllPackageBean> CREATOR = new Creator<AllPackageBean>() {
            @Override
            public AllPackageBean createFromParcel(Parcel in) {
                return new AllPackageBean(in);
            }

            @Override
            public AllPackageBean[] newArray(int size) {
                return new AllPackageBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPackageType() {
            return packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public String getPackageUpgradeInfo() {
            return packageUpgradeInfo;
        }

        public void setPackageUpgradeInfo(String packageUpgradeInfo) {
            this.packageUpgradeInfo = packageUpgradeInfo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(packageName);
            dest.writeDouble(price);
            dest.writeString(currency);
            dest.writeString(packageType);
            dest.writeString(packageUpgradeInfo);
        }
    }
}
