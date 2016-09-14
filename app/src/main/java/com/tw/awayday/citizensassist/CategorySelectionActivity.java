package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static android.widget.ArrayAdapter.createFromResource;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.tw.awayday.citizensassist.MainActivity.newIssue;
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
                String category = spinner.getSelectedItem().toString();
                newIssue.addCategory(category);
                makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();
                startActivity(new Intent(CategorySelectionActivity.this, CaptureImageActivity.class));
            }
        });
    }
}
