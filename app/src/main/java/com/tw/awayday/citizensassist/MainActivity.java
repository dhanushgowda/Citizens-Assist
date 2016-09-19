package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tw.awayday.citizensassist.Models.IssueContents;

import static android.view.View.OnClickListener;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.Constants.LOGGED_IN;
import static com.tw.awayday.citizensassist.Constants.REDIRECTED_FROM_ISSUE_PAGE;
import static com.tw.awayday.citizensassist.UserMessages.ALREADY_LOGGED_IN;

public class MainActivity extends AppCompatActivity {
    public static IssueContents newIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.raiseIssueButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                newIssue = new IssueContents();
                if (LOGGED_IN)
                    startActivity(new Intent(MainActivity.this, TagLocationActivity.class));
                else {
                    REDIRECTED_FROM_ISSUE_PAGE = true;
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });

        findViewById(R.id.pothole).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewComplaintActivity.class));
            }
        });

        findViewById(R.id.statistics).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_acitivity_hamburger, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                if (LOGGED_IN)
                    makeText(getApplicationContext(), ALREADY_LOGGED_IN, LENGTH_SHORT).show();
                else
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;

            case R.id.AboutUs:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
