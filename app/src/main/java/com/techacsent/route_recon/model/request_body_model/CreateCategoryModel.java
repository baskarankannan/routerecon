package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class CreateCategoryModel {

    /**
     * category_name : test
     */

    @SerializedName("category_name")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
