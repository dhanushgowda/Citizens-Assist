package com.tw.awayday.citizensassist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.tw.awayday.citizensassist.CaptureImageActivity.*;
import static com.tw.awayday.citizensassist.ServerDetails.*;
import static com.tw.awayday.citizensassist.TagLocationActivity.*;

public class AddCommentsActivity extends AppCompatActivity {

    boolean issuedRaisedSuccessfully = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comments);
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String userComments = ((EditText) findViewById(R.id.comments)).getText().toString();
                IssueObject issueObject = new IssueObject(position, imagePath, userComments);

                Ion.with(getApplicationContext())
                        .load(SERVER_URL + RAISE_ISSUE)
                        .setJsonPojoBody(issueObject)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null) {
                                    issuedRaisedSuccessfully = false;
                                    return;
                                }

                                if (result.get("issueStatus").getAsString().equals("Success")) {
                                    issuedRaisedSuccessfully = true;
                                    return;
                                }

                                issuedRaisedSuccessfully = false;
                            }
                        });

                if (issuedRaisedSuccessfully) {
                    startActivity(new Intent(AddCommentsActivity.this, RaiseIssueSuccessActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry there was an error. Please try again!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddCommentsActivity.this, MainActivity.class));
                }
            }
        });
    }
}
