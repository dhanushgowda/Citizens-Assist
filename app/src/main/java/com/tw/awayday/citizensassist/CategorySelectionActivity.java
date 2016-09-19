package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import static com.tw.awayday.citizensassist.MainActivity.newIssue;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hamburger, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AboutUs:
                startActivity(new Intent(CategorySelectionActivity.this, AboutUsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
