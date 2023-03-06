package com.example.animeggs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animeggs.R;

public class VerEpisodio extends AppCompatActivity {
    private String enlace_episodio;
    private WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_episodio);
        Intent intent = getIntent();
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        enlace_episodio = intent.getStringExtra("enlace_episodio");
        webView.loadUrl(enlace_episodio);




    }
}
