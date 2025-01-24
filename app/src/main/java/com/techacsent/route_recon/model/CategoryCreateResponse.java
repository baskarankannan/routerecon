package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class CategoryCreateResponse {

    /**
     * success : {"title":"Checklist category","customChecklist":"Checklist category added successfully"}
     * customCategory : {"categoryId":16,"categoryName":"test"}
     */

    @SerializedName("success")
    private SuccessBean success;
    @SerializedName("customCategory")
    private CustomCategoryBean customCategory;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public CustomCategoryBean getCustomCategory() {
        return customCategory;
    }

    public void setCustomCategory(CustomCategoryBean customCategory) {
        this.customCategory = customCategory;
    }

    public static class SuccessBean {
        /**
         * title : Checklist category
         * customChecklist : Checklist category added successfully
         */

        @SerializedName("title")
        private String title;
        @SerializedName("customChecklist")
        private String customChecklist;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCustomChecklist() {
            return customChecklist;
        }

        public void setCustomChecklist(String customChecklist) {
            this.customChecklist = customChecklist;
        }
    }

    public static class CustomCategoryBean {
        /**
         * categoryId : 16
         * categoryName : test
         */

        @SerializedName("categoryId")
        private int categoryId;
        @SerializedName("categoryName")
        private String categoryName;

        @SerializedName("deleteable")
        private String deleteable;

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

        public String getDeleteable() {
            return deleteable;
        }

        public void setDeleteable(String deleteable) {
            this.deleteable = deleteable;
        }
    }
}
