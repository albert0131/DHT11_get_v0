package com.example.user.dht11_get_v0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class Temperature extends AppCompatActivity {

    WebView wv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        wv2 = (WebView) findViewById(R.id.webView2);

        wv2.setWebChromeClient(new WebChromeClient());
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.getSettings().setSupportZoom(true);
        wv2.getSettings().setBuiltInZoomControls(true);
        wv2.setInitialScale(Variable.webScale);
        wv2.loadUrl(Variable.urlTem + "&api_key=" + Variable.api_key);

    }
}
