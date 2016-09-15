package com.tw.awayday.citizensassist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.loadUrl("http://www.google.com");
    }
}
