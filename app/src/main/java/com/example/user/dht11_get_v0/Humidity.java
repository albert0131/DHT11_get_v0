package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Humidity extends AppCompatActivity {

    WebView wv1;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        wv1 = (WebView) findViewById(R.id.webView1);
        btnBack = (Button) findViewById(R.id.back);

        // --------- 顯示圖表資料 ----------------------------
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.getSettings().setJavaScriptEnabled(true);       //訪問頁面中有Java Script,必須設置支持Java Script
        //wv1.getSettings().setUseWideViewPort(true);
        //wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setSupportZoom(true);             // 設置支持縮放
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.setInitialScale(Variable.webScale);                      // 設置縮放比例
        wv1.loadUrl(Variable.urlHum + "&api_key=" + Variable.api_key);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Humidity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
