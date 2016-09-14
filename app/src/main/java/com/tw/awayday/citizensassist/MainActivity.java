package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tw.awayday.citizensassist.Models.IssueContents;

import static android.view.View.OnClickListener;
import static android.widget.Toast.*;
import static com.tw.awayday.citizensassist.Constants.*;
import static com.tw.awayday.citizensassist.UserMessages.ALREADY_LOGGED_IN;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;
import static com.tw.awayday.citizensassist.UserStatus.*;

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
<<<<<<< 8504b6b8afc4552172cfa0181fe970ed1b14c7ee
                newIssue = new IssueContents();
                startActivity(new Intent(MainActivity.this, TagLocationActivity.class));
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                if (loggedIn) {
                    startActivity(new Intent(MainActivity.this, TagLocationActivity.class));
                } else {
                    LoginActivity.redirectedFromIssuePage = true;
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
=======
>>>>>>> Swathi| Fixed login issues
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                if (LOGGED_IN)
                    makeText(getApplicationContext(), ALREADY_LOGGED_IN, LENGTH_SHORT).show();
                else
<<<<<<< 8504b6b8afc4552172cfa0181fe970ed1b14c7ee
                    startActivity(new Intent(MainActivity.this, LoginActivityFromHamburger.class));
                if (loggedIn) {
                    loggedIn = false;
                    Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_LONG).show();
                    return true;
                }
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
=======
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
>>>>>>> Swathi| Fixed login issues
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
