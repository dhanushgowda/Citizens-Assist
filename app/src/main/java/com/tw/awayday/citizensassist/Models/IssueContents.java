package com.tw.awayday.citizensassist.Models;

import com.google.android.gms.maps.model.LatLng;

public class IssueContents {
    private LatLng issueLocation;
    private String category;
    private String imagePath;
    private String userComments;

    public void addIssueLocation(LatLng issueLocation) {
        this.issueLocation = issueLocation;
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
