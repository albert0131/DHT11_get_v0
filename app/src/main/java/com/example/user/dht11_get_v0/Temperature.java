package com.example.user.dht11_get_v0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Temperature extends AppCompatActivity {

    WebView wv1, wv2, wv3;

    private Button btnTem;

    int webScale = 150;             // set webview scale
    String api_key = "TC1HMH4R7SSVE93A";
    // 圖表資料url
    String urlHum = "https://thingspeak.com/channels/176126/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BF%95%E5%BA%A6&type=line";
    String urlTem = "https://thingspeak.com/channels/176126/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BA%AB%E5%BA%A6&type=line";
    String urlPm25 = "https://thingspeak.com/channels/176126/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E7%94%B2%E8%84%98&type=line";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        wv2 = (WebView) findViewById(R.id.webView2);

        wv2.setWebChromeClient(new WebChromeClient());
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.getSettings().setSupportZoom(true);
        wv2.getSettings().setBuiltInZoomControls(true);
        wv2.setInitialScale(webScale);
        wv2.loadUrl(urlTem + "&api_key=" + api_key);

    }
}
