package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tw.awayday.citizensassist.Models.IssueAddress;

public class TagLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_location);
        findViewById(R.id.tagLocationButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent(TagLocationActivity.this, LocationFetcherActivity.class), Constants.TAG_LOCATION);
            }
        });
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TagLocationActivity.this, CaptureImageActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TAG_LOCATION && resultCode == RESULT_OK) {
            IssueAddress issueAddress = data.getParcelableExtra("IssueAddress");
            TextView textView = (TextView) findViewById(R.id.addressView);
            textView.setText(issueAddress.toString());
        }
    }
}
