package com.techacsent.route_recon.model.request_body_model;

import com.google.gson.annotations.SerializedName;

public class FeedbackModel {

    /**
     * report_id : 339
     * feedback_type : 2
     */

    @SerializedName("report_id")
    private int reportId;
    @SerializedName("feedback_type")
    private int feedbackType;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(int feedbackType) {
        this.feedbackType = feedbackType;
    }
}
