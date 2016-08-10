package com.tw.awayday.citizensassist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.tw.awayday.citizensassist.UserCredentials.loggedIn;

public class LoginController extends AppCompatActivity {
    private Context applicationContext;
    private RaiseIssueController raiseIssueController;

    public LoginController(Context applicationContext, RaiseIssueController raiseIssueController) {
        this.applicationContext = applicationContext;
        this.raiseIssueController = raiseIssueController;
    }

    public void login() {
        LoginButton loginButton;
        CallbackManager callbackManager;
        FacebookSdk.sdkInitialize(applicationContext);
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
                raiseIssueController.raiseNewIssue();
            }
        });

    }
}
