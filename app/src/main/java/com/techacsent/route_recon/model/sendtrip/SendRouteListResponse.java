package com.techacsent.route_recon.model.sendtrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendRouteListResponse {

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    @SerializedName("has-more")
    @Expose
    private Boolean hasMore;


    @SerializedName("more-available")
    @Expose
    private String moreAvailable;
    @SerializedName("total")
    @Expose
    private Integer total;

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(String moreAvailable) {
        this.moreAvailable = moreAvailable;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
