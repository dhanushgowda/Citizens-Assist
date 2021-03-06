package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.MainActivity.newIssue;
import static com.tw.awayday.citizensassist.ServerDetails.RAISE_ISSUE;
import static com.tw.awayday.citizensassist.ServerDetails.SERVER_URL;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hamburger, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AboutUs:
                startActivity(new Intent(AddCommentsActivity.this, AboutUsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Future<JsonObject> saveIssueInServer() {
        return Ion.with(getApplicationContext())
                .load(SERVER_URL + RAISE_ISSUE)
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
