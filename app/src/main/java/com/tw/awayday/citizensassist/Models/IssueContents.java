package com.tw.awayday.citizensassist.Models;

import com.google.android.gms.maps.model.LatLng;

public class IssueContents {
    private String category;
    private String imagePath;
    private String userComments;
    private String issueLatitude;
    private String issueLongitude;

    public void addIssueLocation(LatLng issueLocation) {
        this.issueLatitude = String.valueOf(issueLocation.latitude);
        this.issueLongitude = String.valueOf(issueLocation.longitude);
    }

    public void addCategory(String category) {
        this.category = category;
    }

    public void addImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void addComments(String userComments) {
        this.userComments = userComments;
    }
}
