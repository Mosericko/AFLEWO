package com.mosericko.aflewo.customer.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.mosericko.aflewo.R;

public class FAQs extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        webView = findViewById(R.id.faqsWeb);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.aflewo.com/Faq.html");
    }
}