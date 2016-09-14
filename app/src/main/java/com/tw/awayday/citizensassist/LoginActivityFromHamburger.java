package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.UserMessages.LOGIN_SUCCESSFUL;

public class LoginActivityFromHamburger extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeText(getApplicationContext(), LOGIN_SUCCESSFUL, LENGTH_SHORT).show();
                Intent mainActivity = new Intent(LoginActivityFromHamburger.this, MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainActivity);
            }
        });
    }
}
