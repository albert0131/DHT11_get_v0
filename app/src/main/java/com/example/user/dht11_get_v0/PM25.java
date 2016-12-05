package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class PM25 extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm25);

        wv = (WebView) findViewById(R.id.webView3);

        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setInitialScale(Variable.webScale);
        wv.loadUrl(Variable.urlPm25 + "&api_key=" + Variable.api_key);

    }

    public void clickBack(View v) {
        Intent intent = new Intent();
        intent.setClass(PM25.this, MainActivity.class);
        startActivity(intent);
    }
}
