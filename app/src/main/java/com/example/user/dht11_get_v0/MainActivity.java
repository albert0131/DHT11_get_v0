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

class Variable {
     static int webScale = 150;                       // set webview scale
     static String api_key = "TC1HMH4R7SSVE93A";      // ThingSpeak API key
    // 圖表資料url
     static String urlLast = "https://api.thingspeak.com/channels/176126/feed/last.json?";
     static String urlTem = "https://thingspeak.com/channels/176126/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BA%AB%E5%BA%A6&type=line";
     static String urlHum = "https://thingspeak.com/channels/176126/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E6%BF%95%E5%BA%A6&type=line";
     static String urlPm25 = "https://thingspeak.com/channels/176126/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=%E7%94%B2%E8%84%98&type=line";

}


public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3;

    Button btnHum;
    Button btnTem;
    Button btnPm25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);

        btnTem = (Button) findViewById(R.id.buttonTem);
        btnHum = (Button) findViewById(R.id.buttonHum);
        btnPm25 = (Button) findViewById(R.id.buttonPm25);

        btnTem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Temperature.class);
                startActivity(intent);
                //finish();
            }
        });

        btnHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Humidity.class);
                startActivity(intent);
                //finish();
            }
        });

        btnPm25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PM25.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    public void click(View v) {
// ----------- 抓取ThinkSpeak last data --------------------
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // feed需利用Object解析
        StringRequest request = new StringRequest(Variable.urlLast + "&api_key=" + Variable.api_key,

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

