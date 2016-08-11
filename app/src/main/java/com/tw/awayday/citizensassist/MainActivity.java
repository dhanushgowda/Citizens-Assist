package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static android.widget.Toast.*;
import static com.tw.awayday.citizensassist.ServerDetails.SERVER_URL;
import static com.tw.awayday.citizensassist.ServerDetails.validateUser;
import static com.tw.awayday.citizensassist.UserCredentials.*;

public class MainActivity extends AppCompatActivity {
    public static final String ERROR_TRY_AGAIN = "An error occured! Please try again";
    public static final String LOGIN_FAILED = "Login failed! Please Retry!";
    ImageView imageView;
    Button cameraButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!loggedIn) {
            setContentView(R.layout.login_page);
            Button loginButton = (Button) findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText email = (EditText) findViewById(R.id.email);
                    String userEmail = email.getText().toString();
                    EditText password = (EditText) findViewById(R.id.pword);
                    String userPassword = password.getText().toString();
                    validateUser(userEmail, userPassword);
                    if (loggedIn) {
                        setContentView(R.layout.activity_main);
                    }
                }
            });
        }

        setContentView(R.layout.activity_main);


        setContentView(R.layout.activity_main);
        Button raiseAnIssue = (Button) findViewById(R.id.raiseIssueButton);
        raiseAnIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.raise_an_issue);
                imageView = (ImageView) findViewById(R.id.imageView);
                cameraButton = (Button) findViewById(R.id.button_test);
                submitButton = (Button) findViewById(R.id.submitButton);
                cameraButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        makeText(getApplicationContext(), "Opening Camera", LENGTH_SHORT).show();
                        dispatchTakePictureIntent();
                    }
                });
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeText(getApplicationContext(), "Work under progress", LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void validateUser(final String userEmail, final String userPassword) {
        User user = new User(userEmail, userPassword);
        Ion.with(this)
                .load(SERVER_URL + validateUser)
                .setJsonPojoBody(user)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            makeText(getApplicationContext(), ERROR_TRY_AGAIN, LENGTH_LONG).show();
                            return;
                        }

                        if (result.get("message").getAsString().equals("Failed")) {
                            makeText(getApplicationContext(), LOGIN_FAILED, LENGTH_LONG).show();
                            return;
                        }

                        loggedIn = true;
                        currentUser.is(userEmail, userPassword);
                    }
                });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
