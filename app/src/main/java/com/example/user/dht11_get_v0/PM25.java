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

public class PM25 extends AppCompatActivity {

    WebView wv;
    TextView tvPm25Max, tvPm25Min, tvPm25Avg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm25);
        setTitle(Variable.titlePm25);

        wv = (WebView) findViewById(R.id.webView3);
        // --------- 顯示圖表資料 ----------------------------
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setInitialScale(Variable.webScale);
        //wv.loadUrl(Variable.urlPm25 + "&api_key=" + Variable.api_key);
        wv.loadUrl(Variable.urlPm25);

        tvPm25Max = (TextView) findViewById(R.id.pm25Max);
        tvPm25Min = (TextView) findViewById(R.id.pm25Min);
        tvPm25Avg = (TextView) findViewById(R.id.pm25Avg);
    }

    // ------ 計算最大最小平均值 ----------------------------------------
    public void clickCalculate(View v) {

        RequestQueue queue = Volley.newRequestQueue(PM25.this);
        // field需用array解析, results=? 設定抓取筆數
        StringRequest request = new StringRequest(Variable.urlFeeds,// + Variable.fieldResults,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String pm25;
                        float pm25Value;
                        float pm25Sum = 0;
                        float pm25Max = 0;
                        float pm25Min = 100;
                        float pm25Avg = 0;
                        //int tempBuf = 0;

                        try {
                            for(int i=0 ; i<Variable.fieldResults ; i++) {
                                pm25 = new JSONArray(new JSONObject(response).getString("feeds")).getJSONObject(i).getString("field3");
                                pm25Value = Float.parseFloat(pm25);
                                pm25Sum += pm25Value;
                                //tempBuf = tempValue;
                                if(pm25Value > pm25Max)
                                    pm25Max = pm25Value;
                                if(pm25Value < pm25Min)
                                    pm25Min = pm25Value;
                            }
                            pm25Avg = pm25Sum/Variable.fieldResults;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        tvPm25Max.setText("最大PM2.5濃度: " + String.valueOf(pm25Max) + " μg/m3");
                        tvPm25Min.setText("最小PM2.5濃度: " + String.valueOf(pm25Min) + " μg/m3");
                        tvPm25Avg.setText("平均PM2.5濃度: " + String.valueOf(pm25Avg) + " μg/m3");
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

}
