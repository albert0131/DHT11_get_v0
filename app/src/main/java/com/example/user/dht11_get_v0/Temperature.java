package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Temperature extends AppCompatActivity {

    WebView wv2;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        wv2 = (WebView) findViewById(R.id.webView2);
        btnBack = (Button) findViewById(R.id.back);

        wv2.setWebChromeClient(new WebChromeClient());
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.getSettings().setSupportZoom(true);
        wv2.getSettings().setBuiltInZoomControls(true);
        wv2.setInitialScale(Variable.webScale);
        wv2.loadUrl(Variable.urlTem + "&api_key=" + Variable.api_key);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Temperature.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
