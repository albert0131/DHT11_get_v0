package com.example.user.dht11_get_v0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Humidity extends AppCompatActivity {

    WebView wv1, wv2, wv3;

    private Button btnHum;

    int webScale = 150;             // set webview scale
    String api_key = "TC1HMH4R7SSVE93A";
    // 圖表資料url
    String urlHum = "https://thingspeak.com/channels/176126/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BF%95%E5%BA%A6&type=line";
    String urlTem = "https://thingspeak.com/channels/176126/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BA%AB%E5%BA%A6&type=line";
    String urlPm25 = "https://thingspeak.com/channels/176126/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E7%94%B2%E8%84%98&type=line";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        wv1 = (WebView) findViewById(R.id.webView1);
        //wv2 = (WebView) findViewById(R.id.webView2);
        //wv3 = (WebView) findViewById(R.id.webView3);

        // --------- 顯示圖表資料 ----------------------------
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.getSettings().setJavaScriptEnabled(true);       //訪問頁面中有Java Script,必須設置支持Java Script
        //wv1.getSettings().setUseWideViewPort(true);
        //wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setSupportZoom(true);             // 設置支持縮放
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.setInitialScale(webScale);                      // 設置縮放比例
        wv1.loadUrl(urlHum + "&api_key=" + api_key);

    }
}
