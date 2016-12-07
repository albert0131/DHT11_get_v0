package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Humidity extends AppCompatActivity {

    WebView wv;
    TextView tvHumMax, tvHumMin, tvHumAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        setTitle(Variable.titleHum);

        wv = (WebView) findViewById(R.id.webView2);

        // --------- 顯示圖表資料 ----------------------------
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);       //訪問頁面中有Java Script,必須設置支持Java Script
        //wv1.getSettings().setUseWideViewPort(true);
        //wv1.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setSupportZoom(true);             // 設置wv支持縮放
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setInitialScale(Variable.webScale);                      // 設置wv縮放比例
        //wv.loadUrl(Variable.urlHum + "&api_key=" + Variable.api_key);
        wv.loadUrl(Variable.urlHum);

        tvHumMax = (TextView) findViewById(R.id.humMax);
        tvHumMin = (TextView) findViewById(R.id.humMin);
        tvHumAvg = (TextView) findViewById(R.id.humAvg);
    }

    // ------ 計算最大最小平均值 ----------------------------------------
    public void clickCalculate(View v) {

        RequestQueue queue = Volley.newRequestQueue(Humidity.this);
        // field需用array解析, results=? 設定抓取筆數
        StringRequest request = new StringRequest(Variable.urlFeeds,// + Variable.fieldResults,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String hum;
                        float humValue;
                        float humSum = 0;
                        float humMax = 0;
                        float humMin = 100;
                        float humAvg = 0;
                        //int tempBuf = 0;

                        try {
                            for(int i=0 ; i<Variable.fieldResults ; i++) {
                                hum = new JSONArray(new JSONObject(response).getString("feeds")).getJSONObject(i).getString("field2");
                                humValue = Float.parseFloat(hum);
                                humSum += humValue;
                                //tempBuf = tempValue;
                                if(humValue > humMax)
                                    humMax = humValue;
                                if(humValue < humMin)
                                    humMin = humValue;
                            }
                            humAvg = humSum/Variable.fieldResults;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        tvHumMax.setText("最大濕度值: " + String.valueOf(humMax) + " %RH");
                        tvHumMin.setText("最小濕度值: " + String.valueOf(humMin) + " %RH");
                        tvHumAvg.setText("平均濕度值: " + String.valueOf(humAvg) + " %RH");
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
    // -------------------------------------------------------------------------------


    public void clickBack(View v) {
        Intent intent = new Intent();
        intent.setClass(Humidity.this, MainActivity.class);
        startActivity(intent);
    }
}
