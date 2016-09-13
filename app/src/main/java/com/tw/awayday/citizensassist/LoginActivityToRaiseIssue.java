package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.*;
import static com.tw.awayday.citizensassist.UserMessages.LOGIN_SUCCESSFUL;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;
import static com.tw.awayday.citizensassist.UserStatus.*;


public class LoginActivityToRaiseIssue extends AppCompatActivity {

    public static boolean redirectedFromIssuePage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< 7e90fc9ac6b6611e55593f3051d6f93b98d1074f:app/src/main/java/com/tw/awayday/citizensassist/LoginActivityToRaiseIssue.java
                makeText(getApplicationContext(), LOGIN_SUCCESSFUL, LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivityToRaiseIssue.this, TagLocationActivity.class));
                UserStatus.loggedIn = true;
                Toast.makeText(getApplicationContext(), LOGIN_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                Intent mainActivity = new Intent(LoginActivity.this, TagLocationActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainActivity);
=======
                if (redirectedFromIssuePage) {
                    redirectedFromIssuePage = false;
                    Intent tagLocationIntent = new Intent(LoginActivity.this, TagLocationActivity.class);
                    tagLocationIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(tagLocationIntent);
                } else {
                    Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(mainActivityIntent);
                }
                loggedIn = true;
                makeText(getApplicationContext(), LOGIN_SUCCESSFUL, LENGTH_SHORT).show();
>>>>>>> Swathi | Adds proper forwarding to appropriate pages based on from where login is initiated:app/src/main/java/com/tw/awayday/citizensassist/LoginActivity.java
            }
        });
    }
}
