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

public class MainActivity extends AppCompatActivity {

    boolean loggedIn = false;

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
                if (!loggedIn) {
                    login();
                } else
                    setContentView(R.layout.raise_an_issue);
            }

            private void login() {
                LoginButton loginButton;
                CallbackManager callbackManager;
                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager = CallbackManager.Factory.create();
                setContentView(R.layout.login_page);
                loginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
                loginButton.getLoginBehavior();
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        loggedIn = true;
                        setContentView(R.layout.raise_an_issue);
                    }

                    @Override
                    public void onCancel() {
                        setContentView(R.layout.login_page);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        setContentView(R.layout.login_page);
                    }
                });
            }
        });
    }
}
