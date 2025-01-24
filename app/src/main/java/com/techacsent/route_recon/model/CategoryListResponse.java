package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryListResponse {


    /**
     * success : Category list
     * list : [{"categoryId":1,"categoryName":"Routes","editable":"no"},{"categoryId":17,"categoryName":"test","editable":"yes"},{"categoryId":22,"categoryName":"test Category","editable":"yes"}]
     */

    @SerializedName("success")
    private String success;
    @SerializedName("list")
    private List<ListBean> list;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * categoryId : 1
         * categoryName : Routes
         * editable : no
         */

        @SerializedName("categoryId")
        private int categoryId;
        @SerializedName("categoryName")
        private String categoryName;
        @SerializedName("deleteable")
        private String editable;

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getEditable() {
            return editable;
        }

        public void setEditable(String editable) {
            this.editable = editable;
        }
    }
}
