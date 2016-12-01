package com.example.user.dht11_get_v0;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3;
    WebView wv1, wv2, wv3;

    private Button btnHum;

    int webScale = 100;             // set webview scale
    String api_key = "TC1HMH4R7SSVE93A";
    String urlLast = "https://api.thingspeak.com/channels/176126/feed/last.json?";
    // 圖表資料url
    String urlHum = "https://thingspeak.com/channels/176126/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BF%95%E5%BA%A6&type=line";
    String urlTem = "https://thingspeak.com/channels/176126/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BA%AB%E5%BA%A6&type=line";
    String urlPm25 = "https://thingspeak.com/channels/176126/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E7%94%B2%E8%84%98&type=line";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);

//        wv1 = (WebView) findViewById(R.id.webView1);
//        wv2 = (WebView) findViewById(R.id.webView2);
//        wv3 = (WebView) findViewById(R.id.webView3);

        btnHum = (Button) findViewById(R.id.buttonHum);

        btnHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Humidity.class);
                startActivity(intent);
                //finish();

            }
        });
    }

    public void click(View v) {
// ----------- 抓取ThinkSpeak last data --------------------
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // feed需利用Object解析
        StringRequest request = new StringRequest(urlLast + "&api_key=" + api_key,

                // results=? 設定抓取筆數 (field需用array解析)
                //StringRequest request = new StringRequest("https://api.thingspeak.com/channels/176126/field/1.json?results=1&api_key=TC1HMH4R7SSVE93A",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // ------- JSON 寫法 -------
                        try {
                            JSONObject objLast = new JSONObject(response);
                            String humiLast = objLast.getString("field1");
                            String tempLast = objLast.getString("field2");
                            String pm25Last = objLast.getString("field3");

                            tv1.setText("目前濕度值: " + humiLast);
                            tv2.setText("目前溫度值: " + tempLast);
                            tv3.setText("目前PM2.5值: " + pm25Last);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // --------------------------

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        queue.add(request);
        queue.start();
    }
}
// -------------------------------------------------




/*
// --------- 顯示圖表資料 ----------------------------
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.getSettings().setJavaScriptEnabled(true);       //訪問頁面中有Java Script,必須設置支持Java Script
        //wv1.getSettings().setUseWideViewPort(true);
        //wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setSupportZoom(true);             // 設置支持縮放
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.setInitialScale(webScale);                      // 設置縮放比例
        wv1.loadUrl(urlHum + "&api_key=" + api_key);

        wv2.setWebChromeClient(new WebChromeClient());
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.getSettings().setSupportZoom(true);
        wv2.getSettings().setBuiltInZoomControls(true);
        wv2.setInitialScale(webScale);
        wv2.loadUrl(urlTem + "&api_key=" + api_key);

        wv3.setWebChromeClient(new WebChromeClient());
        wv3.getSettings().setJavaScriptEnabled(true);
        wv3.getSettings().setSupportZoom(true);
        wv3.getSettings().setBuiltInZoomControls(true);
        wv3.setInitialScale(webScale);
        wv3.loadUrl(urlPm25 + "&api_key=" + api_key);
// --------------------------------------------------
*/