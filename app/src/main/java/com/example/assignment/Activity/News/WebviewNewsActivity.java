package com.example.assignment.Activity.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.assignment.R;

public class WebviewNewsActivity extends AppCompatActivity {
    WebView webView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_news);

        webView = findViewById(R.id.webview_News);

        intent = getIntent();
        String link = intent.getStringExtra("linkURL");
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}