package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        String aboutUs = "Citizens-Assist\n\n" +
                "Citizens Assist is an online portal for raising issues which allows citizens to " +
                "report various problems in their city as well as allowing them to view the status " +
                "of the problem. Any community issues can be raised with a few taps on your " +
                "smartphone screen. You have the option to use GPS to mark the position of the issue" +
                " or search for a location. Other functionalities include viewing issues that you " +
                "raised, viewing all issues which can be sorted by type, area, severity, etc. " +
                "Additionally, one can also look at trends based on the issues raised and resolved.\n"
                + "\n\nTech Stack\n\nAndroid - Java\n\n" + "Node with MongoDB and Express\n\n";

        TextView textView = (TextView) findViewById(R.id.about_us);
        textView.setText(aboutUs);

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsActivity.this, MainActivity.class));
            }
        });
    }
}
