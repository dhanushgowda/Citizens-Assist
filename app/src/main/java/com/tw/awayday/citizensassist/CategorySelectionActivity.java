package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.MainActivity.newIssue;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;

public class CategorySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        findViewById(R.id.potholeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Potholes");
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.clogged_drains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Clogged Drains");
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.bad_roads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Bad Roads");
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.roadside_garbage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Roadside Garbage");
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.malfunctioningTrafficSignal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Traffic Signals");
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.street_lights).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Street Lights");
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
    }
}
