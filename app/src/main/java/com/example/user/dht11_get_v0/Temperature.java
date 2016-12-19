package com.example.user.dht11_get_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Temperature extends AppCompatActivity {

    WebView wv;
//    Button btnBack;
    TextView tvTempMax, tvTempMin, tvTempAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        setTitle(Variable.titleTemp);

        wv = (WebView) findViewById(R.id.webView1);
//        btnBack = (Button) findViewById(R.id.back);

        // --------- 顯示圖表資料 ----------------------------
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setInitialScale(Variable.webScale);
        //wv.loadUrl(Variable.urlTem + "&api_key=" + Variable.api_key);
        wv.loadUrl(Variable.urlTem);

        tvTempMax = (TextView) findViewById(R.id.tempMax);
        tvTempMin = (TextView) findViewById(R.id.tempMin);
        tvTempAvg = (TextView) findViewById(R.id.tempAvg);

    }

    // ------ 計算最大最小平均值 ----------------------------------------
    public void clickCalculate(View v) {

        RequestQueue queue = Volley.newRequestQueue(Temperature.this);
        // field需用array解析, results=? 設定抓取筆數
        StringRequest request = new StringRequest(Variable.urlFeeds,// + Variable.fieldResults,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        
                        String temp;
                        float tempValue;
                        float tempSum = 0;
                        float tempMax = 0;
                        float tempMin = 100;
                        float tempAvg = 0;
                        //int tempBuf = 0;
                        
                        try {
                            for(int i=0 ; i<Variable.fieldResults ; i++) {
                                temp = new JSONArray(new JSONObject(response).getString("feeds")).getJSONObject(i).getString("field1");
                                tempValue = Float.parseFloat(temp);
                                tempSum += tempValue;
                                //tempBuf = tempValue;
                                if(tempValue > tempMax)
                                    tempMax = tempValue;
                                if(tempValue < tempMin)
                                    tempMin = tempValue;
                                
//                                 tempTime = new JSONArray(new JSONObject(response).getString("feeds")).getJSONObject(i).getString("created_at");
//                                 tempMax = new JSONArray(new JSONObject(response).getString("feeds")).getJSONObject(i).getString("field1");
//                                 Log.d("Time", tempTime);
//                                 Log.d("TEMP", tempMax);
                            }
                            tempAvg = tempSum/Variable.fieldResults;
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


/*//------------------------------------------------------------------------------------
                        // ------- GSON 寫法 (還有error) -------
                        Gson gson = new Gson();
                        ThingSpeak thingSpeak = gson.fromJson(response, ThingSpeak.class);
                        String tempMax = thingSpeak.getFeeds()[(thingSpeak.getFeeds().length)-1].getField1();
                        Log.d("TEMP", tempMax);

//                        for(Feeds data : thingSpeak.getFeeds())
//                        {
//                            Log.d("TEMP", data.getField1());
//                        }

                        for (int i=0 ; i < thingSpeak.getFeeds().length ; i++)
                        {
                            String temp = thingSpeak.getFeeds()[i].getField1();
                            Log.d("TEMP", temp);
                        }
*///------------------------------------------------------------------------------------

                            tvTempMax.setText("最大溫度值: " + String.valueOf(tempMax) + " ℃");
                            tvTempMin.setText("最小溫度值: " + String.valueOf(tempMin) + " ℃");
                            tvTempAvg.setText("平均溫度值: " + String.valueOf(tempAvg) + " ℃");
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
