package com.techacsent.route_recon.event_bus_object;

public class CreateReportModel {

    private String issue;
    private int type;

    public CreateReportModel(String issue, int type) {
        this.issue = issue;
        this.type = type;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
