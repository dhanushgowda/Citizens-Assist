package com.tw.awayday.citizensassist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class RaiseIssueController extends AppCompatActivity {
    private Context applicationContext;

    public RaiseIssueController(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void raiseNewIssue() {
        setContentView(R.layout.raise_an_issue);
    }
}
