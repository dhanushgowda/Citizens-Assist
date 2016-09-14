package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.widget.ArrayAdapter.createFromResource;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.MainActivity.newIssue;
import static com.tw.awayday.citizensassist.UserMessages.*;

public class CategorySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        findViewById(R.id.potholeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Potholes");
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.clogged_drains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Clogged Drains");
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.bad_roads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Bad Roads");
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.roadside_garbage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Roadside Garbage");
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.malfunctioningTrafficSignal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue.addCategory("Traffic Signals");
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
        findViewById(R.id.street_lights).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = spinner.getSelectedItem().toString();
                newIssue.addCategory(category);
                newIssue.addCategory("Street Lights");
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
    }
}
