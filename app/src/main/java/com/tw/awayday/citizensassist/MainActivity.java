package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.koushikdutta.ion.Ion.with;
import static com.tw.awayday.citizensassist.Constants.REQUEST_IMAGE_CAPTURE;
import static com.tw.awayday.citizensassist.ServerDetails.SERVER_URL;
import static com.tw.awayday.citizensassist.ServerDetails.validateUser;
import static com.tw.awayday.citizensassist.UserCredentials.currentUser;
import static com.tw.awayday.citizensassist.UserCredentials.loggedIn;
import static com.tw.awayday.citizensassist.UserMessages.ERROR_TRY_AGAIN;
import static com.tw.awayday.citizensassist.UserMessages.LOGIN_FAILED;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_CAMERA;
import static com.tw.awayday.citizensassist.UserMessages.WORK_UNDER_PROGRESS;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!loggedIn) {
            login();
        } else {
            home();
        }
    }

    private void login() {
        setContentView(R.layout.login_page);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText) findViewById(R.id.email);
                EditText password = (EditText) findViewById(R.id.pword);
                validateUser(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void home() {
        setContentView(R.layout.activity_main);

        findViewById(R.id.raiseIssueButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.raise_an_issue);
                imageView = (ImageView) findViewById(R.id.imageView);

                findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        makeText(getApplicationContext(), OPENING_CAMERA, LENGTH_SHORT).show();
                        dispatchTakePictureIntent();
                    }
                });

                findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeText(getApplicationContext(), WORK_UNDER_PROGRESS, LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void validateUser(final String userEmail, final String userPassword) {
        with(this)
                .load(SERVER_URL + validateUser)
                .setJsonPojoBody(new User(userEmail, userPassword))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            makeText(getApplicationContext(), ERROR_TRY_AGAIN, LENGTH_LONG).show();
                            login();
                        }

                        if (result.get("message").getAsString().equals("Failed")) {
                            makeText(getApplicationContext(), LOGIN_FAILED, LENGTH_LONG).show();
                            login();
                        }

                        loggedIn = true;
                        currentUser.is(userEmail, userPassword);
                        home();
                    }
                });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
