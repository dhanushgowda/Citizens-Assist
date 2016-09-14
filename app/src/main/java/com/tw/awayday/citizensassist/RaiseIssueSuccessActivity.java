package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RaiseIssueSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_issue_success);
        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mainActivity = new Intent(RaiseIssueSuccessActivity.this, MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainActivity);
            }
        });
    }
}