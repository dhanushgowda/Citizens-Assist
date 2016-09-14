package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.*;
import static com.tw.awayday.citizensassist.UserMessages.LOGIN_SUCCESSFUL;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;


public class LoginActivityToRaiseIssue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeText(getApplicationContext(), LOGIN_SUCCESSFUL, LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivityToRaiseIssue.this, TagLocationActivity.class));
            }
        });
    }
}
