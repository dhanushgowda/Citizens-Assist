package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.Constants.REQUEST_IMAGE_CAPTURE;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_CAMERA;
import static com.tw.awayday.citizensassist.UserMessages.WORK_UNDER_PROGRESS;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home();
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
