package com.tw.awayday.citizensassist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.tw.awayday.citizensassist.UserCredentials.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button raiseAnIssue = (Button) findViewById(R.id.raiseIssueButton);
        Button viewHistory = (Button) findViewById(R.id.viewHistoryButton);
        Button viewTrends = (Button) findViewById(R.id.viewTrendsButton);
        Button myProfile = (Button) findViewById(R.id.myProfileButton);

        raiseAnIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RaiseIssueController raiseIssueController = new RaiseIssueController(getApplicationContext());
                if (!loggedIn) {
                    new LoginController(getApplicationContext(), raiseIssueController).login();
                } else {
                    raiseIssueController.raiseNewIssue();
                }
            }
        });
    }
}
