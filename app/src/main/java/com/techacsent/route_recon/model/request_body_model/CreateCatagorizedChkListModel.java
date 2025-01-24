package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class CreateCatagorizedChkListModel {

    /**
     * categoryId : 1
     * checklist_text : tesdddt
     */

    @SerializedName("categoryId")
    private int categoryId;
    @SerializedName("checklist_text")
    private String checklistText;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getChecklistText() {
        return checklistText;
    }

    public void setChecklistText(String checklistText) {
        this.checklistText = checklistText;
    }
}
