package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static android.view.View.OnClickListener;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.raiseIssueButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), OPENING_MAPS, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, TagLocationActivity.class));
            }
        });
    }
}
