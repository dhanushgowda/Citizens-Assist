package com.tw.awayday.citizensassist;

import com.google.android.gms.maps.model.LatLng;

public class IssueObject {
    private final LatLng position;
    private final String imagePath;
    private final String userComments;

    public IssueObject(LatLng position, String imagePath, String userComments) {
        this.position = position;
        this.imagePath = imagePath;
        this.userComments = userComments;
    }
}
