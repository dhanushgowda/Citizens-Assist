package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button cameraButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        Toast.makeText(getApplicationContext(), "Opening Camera", Toast.LENGTH_SHORT).show();
                        dispatchTakePictureIntent();
                    }
                });
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
                        APIService apiService = APIClient.getClient().create(APIService.class);
                        Call<Image> call = apiService.saveImage(new Image("Random Path"));
                        System.out.println("pando: ");
                        call.enqueue(new Callback<Image>() {
                            @Override
                            public void onResponse(Call<Image> call, Response<Image> response) {

                                String movies = response.body().getPath();
                                Toast.makeText(getApplicationContext(), "Received "+movies + " "+response.body(), Toast.LENGTH_LONG).show();
                                System.out.println("yoda");
                            }

                            @Override
                            public void onFailure(Call<Image> call, Throwable t) {
                                // Log error here since request failed
                                System.out.println(t.getMessage());
                                Toast.makeText(getApplicationContext(), "Failed " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
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
