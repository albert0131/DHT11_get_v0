package com.example.user.dht11_get_v0;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    //WebView wv, wv2;
    String api_key = "TC1HMH4R7SSVE93A";
    String urlLast = "https://api.thingspeak.com/channels/176126/feed/last.json?";
    //String urlHum = "https://thingspeak.com/channels/176126/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BF%95%E5%BA%A6&type=line";
    //String urlTem = "https://thingspeak.com/channels/176126/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BA%AB%E5%BA%A6&type=line&api_key=TC1HMH4R7SSVE93A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        //wv = (WebView) findViewById(R.id.webView);
        //wv2 = (WebView) findViewById(R.id.webView2);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
// ----------- 抓取ThinkSpeak last data --------------------
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

                            Log.d("NET", humiLast);
                            Log.d("NET", tempLast);
                            Log.d("NET", pm25Last);
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

            }}
        );
/*
// --------- 顯示圖表資料 ----------------------------
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(urlHum+api_key);

        wv2.setWebChromeClient(new WebChromeClient());
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.loadUrl(urlTem+api_key);
// --------------------------------------------------
*/
        queue.add(request);
        queue.start();

    }
}