package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.tw.awayday.citizensassist.Constants.LOGGED_IN;
import static com.tw.awayday.citizensassist.Constants.REDIRECTED_FROM_ISSUE_PAGE;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOGGED_IN = true;
                if (REDIRECTED_FROM_ISSUE_PAGE) {
                    startActivity(new Intent(LoginActivity.this, TagLocationActivity.class));
                    REDIRECTED_FROM_ISSUE_PAGE = false;

                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });
    }
}
