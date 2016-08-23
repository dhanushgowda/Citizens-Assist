package com.tw.awayday.citizensassist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.Constants.REQUEST_IMAGE_CAPTURE;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_CAMERA;
import static com.tw.awayday.citizensassist.UserMessages.WORK_UNDER_PROGRESS;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private File file;

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
                final Spinner spinner = (Spinner) findViewById(R.id.complaint_spinner);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.complaint_categories, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(adapter);

                findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        makeText(getApplicationContext(), OPENING_CAMERA, LENGTH_SHORT).show();
                        dispatchTakePictureIntent();
                    }
                });

                findViewById(R.id.tag_location).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        makeText(getApplicationContext(), "Opening maps", LENGTH_SHORT).show();
                        Intent myIntent = new Intent(MainActivity.this, LocationFetcherActivity.class);
                        startActivity(myIntent);
                    }
                });

                findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeText(getApplicationContext(), WORK_UNDER_PROGRESS, LENGTH_SHORT).show();
                        Future uploading = Ion.with(MainActivity.this)
                                .load("http://10.4.23.49:5000/upload")
                                .setMultipartFile("image", file)
                                .asString()
                                .withResponse()
                                .setCallback(new FutureCallback<Response<String>>() {
                                    @Override
                                    public void onCompleted(Exception e, Response<String> result) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result.getResult());
                                            if (jsonObject.getString("success").equals("true")) {
                                                Toast.makeText(getApplicationContext(), jsonObject.getString("response") + " yoda", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), jsonObject.getString("response"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                });
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
//            path = getPathFromURI(data.getData());

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            file = new File(getRealPathFromURI(tempUri));
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String temporaryPath = cursor.getString(idx);
        cursor.close();
        return temporaryPath;
    }
}
