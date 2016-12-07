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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.data;

//-------------------- 設定變數 --------------------------------------------------
class Variable {

    static String titleMain = "環境監測網";
    static String titleTemp = titleMain + " -- 溫度資訊";
    static String titleHum = titleMain + " -- 濕度資訊";
    static String titlePm25 = titleMain + " -- PM2.5資訊";

    static int webScale = 150;                       // set webview scale
    static int fieldResults = 100;                    // results=? 設定抓取筆數
    static String api_key = "TC1HMH4R7SSVE93A";      // ThingSpeak API key
    // last資料url
    static String urlLast = "https://api.thingspeak.com/channels/189185/feed/last.json?";
     //static String urlLast = "https://api.thingspeak.com/channels/176126/feed/last.json?";   // for test url
     // 圖表資料url
    static String urlTem = "https://thingspeak.com/channels/189185/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&title=%E6%BA%AB%E5%BA%A6&type=line&results=" + fieldResults;
    static String urlHum = "https://thingspeak.com/channels/189185/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&title=%E6%BF%95%E5%BA%A6&type=line&results=" + fieldResults;
    static String urlPm25 = "https://thingspeak.com/channels/189185/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&title=%E7%94%B2%E8%84%98&type=line&results=" + fieldResults;
     // all資料url
    static String urlFeeds = "https://api.thingspeak.com/channels/189185/feeds.json?&results=" + fieldResults;

}
//------------------------------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3;

//    Button btnTem;
//    Button btnHum;
//    Button btnPm25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(Variable.titleMain);

        tv1 = (TextView) findViewById(R.id.textView1);      // temp
        tv2 = (TextView) findViewById(R.id.textView2);      // hum
        tv3 = (TextView) findViewById(R.id.textView3);      // pm2.5

    }
    // ----------- 抓取目前環境資訊(ThinkSpeak last data) -------------------------------
    public void clickNow(View v) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // feed需利用Object解析
        StringRequest request = new StringRequest(Variable.urlLast,   // + "&api_key=" + Variable.api_key,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // ------- JSON 寫法 -------
                        try {
                            JSONObject objLast = new JSONObject(response);
                            String tempLast = objLast.getString("field1");
                            String humiLast = objLast.getString("field2");
                            String pm25Last = objLast.getString("field3");

                            tv1.setText("目前溫度值:     " + tempLast + " 度C");
                            tv2.setText("目前濕度值:     " + humiLast + " %RH");
                            tv3.setText("目前PM2.5濃度:  " + pm25Last + " μg/m3");

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
    //-------------------------------------------------------------------------------------

    // ----- Button for getting chart data ----------------------
    public void clickTemp(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Temperature.class);
        startActivity(intent);
    }

    public void clickHum(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Humidity.class);
        startActivity(intent);
    }

    public void clickPm25(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, PM25.class);
        startActivity(intent);
    }
    // -----------------------------------------------------------

}


// -------- setOnClickListener method -------------------------------------
// Button setOnClickListener
//        btnTem = (Button) findViewById(R.id.buttonTem);
//        btnHum = (Button) findViewById(R.id.buttonHum);
//        btnPm25 = (Button) findViewById(R.id.buttonPm25);

//        btnTem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, Temperature.class);
//                startActivity(intent);
//                //finish();
//            }
//        });
//
//        btnHum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, Humidity.class);
//                startActivity(intent);
//                //finish();
//            }
//        });
//
//        btnPm25.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, PM25.class);
//                startActivity(intent);
//                //finish();
//            }
//        });


