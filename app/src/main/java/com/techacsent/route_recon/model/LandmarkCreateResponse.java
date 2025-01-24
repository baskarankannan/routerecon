package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

public class LandmarkCreateResponse {

    @SerializedName("success")
    private SuccessBean success;

    public SuccessBean getSuccess() {
        return success;
    }

    public void setSuccess(SuccessBean success) {
        this.success = success;
    }

    public static class SuccessBean {

        @SerializedName("title")
        private String title;
        @SerializedName("message")
        private String message;
        @SerializedName("landmarkImage")
        private LandmarkImageBean landmarkImage;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LandmarkImageBean getLandmarkImage() {
            return landmarkImage;
        }

        public void setLandmarkImage(LandmarkImageBean landmarkImage) {
            this.landmarkImage = landmarkImage;
        }

        public static class LandmarkImageBean {


            @SerializedName("id")
            private int id;
            @SerializedName("imageUrl")
            private String imageUrl;
            @SerializedName("description")
            private String description;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
