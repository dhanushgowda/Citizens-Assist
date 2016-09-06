package com.tw.awayday.citizensassist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AddCommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comments);
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent raiseIssueSuccess = new Intent(AddCommentsActivity.this, RaiseIssueSuccessActivity.class);
                startActivity(raiseIssueSuccess);
            }
        });
    }
}
