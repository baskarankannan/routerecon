package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class DeleteCategoryModel {


    /**
     * categoryId : 23
     */

    @SerializedName("categoryId")
    private int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
