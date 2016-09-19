package com.tw.awayday.citizensassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdvancedSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        findViewById(R.id.state_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.state_select_group).setVisibility(View.VISIBLE);
                findViewById(R.id.default_view).setVisibility(View.GONE);
                findViewById(R.id.state_select_group_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.state_select_group).setVisibility(View.GONE);
                        findViewById(R.id.default_view).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        findViewById(R.id.city_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.city_select_group).setVisibility(View.VISIBLE);
                findViewById(R.id.default_view).setVisibility(View.GONE);
                findViewById(R.id.city_select_group_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.city_select_group).setVisibility(View.GONE);
                        findViewById(R.id.default_view).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        findViewById(R.id.area_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.area_select_group).setVisibility(View.VISIBLE);
                findViewById(R.id.default_view).setVisibility(View.GONE);
                findViewById(R.id.area_select_group_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.area_select_group).setVisibility(View.GONE);
                        findViewById(R.id.default_view).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        findViewById(R.id.issue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.issue_select_group).setVisibility(View.VISIBLE);
                findViewById(R.id.default_view).setVisibility(View.GONE);
                findViewById(R.id.issue_select_group_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.issue_select_group).setVisibility(View.GONE);
                        findViewById(R.id.default_view).setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdvancedSearchActivity.this, SearchResultActivity.class));
            }
        });
    }
}
