package com.tw.awayday.citizensassist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.widget.ArrayAdapter.createFromResource;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.koushikdutta.ion.Ion.with;
import static com.tw.awayday.citizensassist.Constants.REQUEST_IMAGE_CAPTURE;
import static com.tw.awayday.citizensassist.ServerDetails.SERVER_URL;
import static com.tw.awayday.citizensassist.ServerDetails.UPLOAD;
import static com.tw.awayday.citizensassist.UserMessages.IMAGE;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_CAMERA;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;

public class RaiseIssueActivity extends AppCompatActivity {
    private ImageView imageView;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_an_issue);
        imageView = (ImageView) findViewById(R.id.imageView);
        final Spinner spinner = (Spinner) findViewById(R.id.complaint_spinner);

        int complaintCategories = R.array.complaintCategories;
        int simpleSpinnerItem = android.R.layout.simple_spinner_item;
        ArrayAdapter<CharSequence> adapter = createFromResource(RaiseIssueActivity.this, complaintCategories, simpleSpinnerItem);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.captureImageButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                makeText(getApplicationContext(), OPENING_CAMERA, LENGTH_SHORT).show();
                dispatchTakePictureIntent();
            }
        });

        findViewById(R.id.tagLocationButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                Intent myIntent = new Intent(RaiseIssueActivity.this, LocationFetcherActivity.class);
                startActivityForResult(myIntent, Constants.TAG_LOCATION);
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postImage();
            }
        });
    }

    private void postImage() {
        with(RaiseIssueActivity.this)
                .load(SERVER_URL + UPLOAD)
                .setMultipartFile(IMAGE, file)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.getResult());
                            if (jsonObject.getString("success").equals("true")) {
                                setContentView(R.layout.raise_issue_success);
                                findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent mainActivity = new Intent(RaiseIssueActivity.this, MainActivity.class);
                                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivity(mainActivity);
                                    }
                                });
                            } else {
                                makeText(getApplicationContext(), jsonObject.getString("response") + "Oops..Try again!", LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

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
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
            file = new File(getRealPathFromURI(tempUri));
            imageView.setImageBitmap(imageBitmap);
        }
        if (requestCode == Constants.TAG_LOCATION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            LatLng position = (LatLng) extras.get("Position");
            makeText(getApplicationContext(), String.valueOf(position.latitude) + " yo " +
                    String.valueOf(position.longitude), LENGTH_SHORT).show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(JPEG, 100, bytes);
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
