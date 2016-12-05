package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Humidity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        wv = (WebView) findViewById(R.id.webView2);

        // --------- 顯示圖表資料 ----------------------------
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);       //訪問頁面中有Java Script,必須設置支持Java Script
        //wv1.getSettings().setUseWideViewPort(true);
        //wv1.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setSupportZoom(true);             // 設置支持縮放
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setInitialScale(Variable.webScale);                      // 設置縮放比例
        wv.loadUrl(Variable.urlHum + "&api_key=" + Variable.api_key);

    }

    public void clickBack(View v) {
        Intent intent = new Intent();
        intent.setClass(Humidity.this, MainActivity.class);
        startActivity(intent);
    }
}
