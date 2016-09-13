package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import static android.widget.ArrayAdapter.createFromResource;
<<<<<<< 9f0ec8e8b9c66ea74b35d14ffa409196e193297f
import static com.tw.awayday.citizensassist.UserMessages.OPENING_CAMERA;
=======
import static android.widget.Toast.*;
import static com.tw.awayday.citizensassist.MainActivity.*;
>>>>>>> Swathi | Creates Issue Object and sends to server
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;

public class CategorySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        final Spinner spinner = (Spinner) findViewById(R.id.complaint_spinner);
        int complaintCategories = R.array.complaintCategories;
        int simpleSpinnerItem = android.R.layout.simple_spinner_item;
        ArrayAdapter<CharSequence> adapter = createFromResource(CategorySelectionActivity.this, complaintCategories, simpleSpinnerItem);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< 9f0ec8e8b9c66ea74b35d14ffa409196e193297f
=======
                String category = spinner.getSelectedItem().toString();
                newIssue.addCategory(category);
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
>>>>>>> Swathi | Creates Issue Object and sends to server
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
    }
}
