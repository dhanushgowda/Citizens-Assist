package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static android.widget.Toast.*;
import static com.tw.awayday.citizensassist.MainActivity.*;
import static com.tw.awayday.citizensassist.ServerDetails.*;

public class AddCommentsActivity extends AppCompatActivity {
    boolean issuedRaisedSuccessfully = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comments);
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String userComments = ((EditText) findViewById(R.id.comments)).getText().toString();
                newIssue.addComments(userComments);
                saveIssueInServer();
            }
        });
    }

    private Future<JsonObject> saveIssueInServer() {
        return Ion.with(getApplicationContext())
                .load(SERVER_URL + SERVER_PORT + RAISE_ISSUE)
                .setJsonPojoBody(newIssue)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        issuedRaisedSuccessfully = true;

                        if (issuedRaisedSuccessfully) {
                            startActivity(new Intent(AddCommentsActivity.this, RaiseIssueSuccessActivity.class));
                        } else {
                            makeText(getApplicationContext(), "Sorry there was an error. Please try again!", LENGTH_LONG).show();
                            startActivity(new Intent(AddCommentsActivity.this, MainActivity.class));
                        }
                    }
                });
    }
}
