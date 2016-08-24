package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.raiseIssueButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent raiseIssue = new Intent(MainActivity.this, RaiseIssueActivity.class);
                startActivity(raiseIssue);
            }
        });

        findViewById(R.id.viewTrendsButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewTrends = new Intent(MainActivity.this, ViewTrendsActivity.class);
                startActivity(viewTrends);
            }
        });
    }
}
