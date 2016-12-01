package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class PM25 extends AppCompatActivity {

    WebView wv3;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm25);

        wv3 = (WebView) findViewById(R.id.webView3);
        btnBack = (Button) findViewById(R.id.back);

        wv3.setWebChromeClient(new WebChromeClient());
        wv3.getSettings().setJavaScriptEnabled(true);
        wv3.getSettings().setSupportZoom(true);
        wv3.getSettings().setBuiltInZoomControls(true);
        wv3.setInitialScale(Variable.webScale);
        wv3.loadUrl(Variable.urlPm25 + "&api_key=" + Variable.api_key);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PM25.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
