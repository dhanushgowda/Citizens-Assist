package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static com.tw.awayday.citizensassist.UserMessages.LOGIN_SUCCESSFUL;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), LOGIN_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainActivity);
            }
        });
    }
}
