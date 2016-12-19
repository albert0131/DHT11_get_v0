package com.example.user.dht11_get_v0;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
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

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.data;
import static com.android.volley.toolbox.Volley.newRequestQueue;

//-------------------- 設定變數 --------------------------------------------------
class Variable {

    static String titleMain = "環境監測網";
    static String titleTemp = titleMain + " -- 溫度資訊";
    static String titleHum = titleMain + " -- 濕度資訊";
    static String titlePm25 = titleMain + " -- PM2.5資訊";

    static int webScale = 150;                       // set webview scale
    static int fieldResults = 100 ;                    // results=? 設定抓取筆數
    static String api_key = "TC1HMH4R7SSVE93A";      // ThingSpeak API key
    // last資料url
    static String urlLast = "https://api.thingspeak.com/channels/189185/feed/last.json?";
     //static String urlLast = "https://api.thingspeak.com/channels/176126/feed/last.json?";   // url for testing
     // 圖表資料url
    static String urlTem = "https://api.thingspeak.com/channels/189185/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&type=line&timezone=Asia/Taipei&results=" + fieldResults;
    static String urlHum = "https://api.thingspeak.com/channels/189185/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&type=line&timezone=Asia/Taipei&results=" + fieldResults;
    static String urlPm25 = "https://api.thingspeak.com/channels/189185/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&type=line&timezone=Asia/Taipei&results=" + fieldResults;
     // all資料url
    static String urlFeeds = "https://api.thingspeak.com/channels/189185/feeds.json?&results=" + fieldResults;
    //static final int msgKey1 = 111;

}
//------------------------------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4;
    private static final int msgKey1 = 111;
    private static Boolean isExit = false;
    private static Boolean hasTask = false;

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
        tv4 = (TextView) findViewById(R.id.textView4);      // pm2.5 品質

        getNowData();
        new updateThread().start();
    }

    // ---------- auto update data (Thread & Handler) --------------------------------------
    public class updateThread extends Thread {
        @Override
        public void run () {
            do{
                try {
                    Thread.sleep(30000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while(true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            //Log.d("NEW",String.valueOf(msg.what));
            if (msg.what == msgKey1)
            {
                getNowData();
                Log.d("NEW","Main");
            }
        }
    };
    // -------------------------------------------------------------------

    // ******* HTTP GET 抓取目前環境資訊(ThinkSpeak last data) ****************************
    public void getNowData() {

        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
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
                            float pm25LastValue = Float.parseFloat(pm25Last);

                            tv1.setText("目前溫度值:     " + tempLast + " \u2103");  //℃ Unicode
                            tv2.setText("目前濕度值:     " + humiLast + " %RH");
                            tv3.setText("目前PM2.5濃度:  " + pm25Last + " μg/m3");

                            // ----- 空氣品質判斷 -----------------------------------
                            if(0.0 <=pm25LastValue && pm25LastValue<= 15.4) {
                                tv4.setText("目前空氣品質 : 良好Good");
                                tv4.setTextColor(0xff000000);   //black
                                tv4.setBackgroundColor(0xff32cd32); //limegreen
                                }
                            else if(15.5 <=pm25LastValue && pm25LastValue<= 35.4) {
                                tv4.setText("目前空氣品質 : 普通Moderate");
                                tv4.setTextColor(0xff000000);
                                tv4.setBackgroundColor(0xffcdcd00);   //yellow3
                                }
                            else if(35.5 <=pm25LastValue && pm25LastValue<= 54.4) {
                                tv4.setText("目前空氣品質 : 對敏感族群不健康!");
                                tv4.setTextColor(0xff000000);
                                tv4.setBackgroundColor(0xffee9a00); //orange2
                                }
                            else if(54.5 <=pm25LastValue && pm25LastValue<= 150.4) {
                                tv4.setText("目前空氣品質 : 對所有族群不健康!!");
                                //tv4.setTextColor(android.graphics.Color.RED);
                                tv4.setTextColor(0xff000000);   //
                                tv4.setBackgroundColor(0xffee0000); //red2
                                }
                            else if(150.5 <=pm25LastValue && pm25LastValue<= 250.4) {
                                tv4.setText("目前空氣品質 : 非常不健康!!!");
                                tv4.setTextColor(0xffffffff);   //white
                                tv4.setBackgroundColor(0xff8b008b); //Magenta4
                                }
                            else if(250.5 <=pm25LastValue) {
                                tv4.setText("目前空氣品質 : 危害!!!!!");
                                tv4.setTextColor(0xffffffff);   //white
                                tv4.setBackgroundColor(0xff8b1a1a); //firebrick4
                                }

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

        mQueue.add(request);
        mQueue.start();

    }
    //********************************************************************************


    // -------- call getNowData() -------------------------------
    public void clickNow(View v) {
        getNowData();
    }

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

    // ------ 按兩下Back離開程式 -----------------------------------
    Timer timerExit = new Timer();
    TimerTask task = new TimerTask() {

             @Override
             public void run() {
                     isExit = false;
                     hasTask = true;
                 }
        };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判斷是否按下Back
              if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否要退出
                     if(!isExit) {
                         isExit = true; //記錄下一次要退出
                         Toast.makeText(this, "再按一次Back退出APP"
                         , Toast.LENGTH_SHORT).show();

                         // 如果超過兩秒則恢復預設值
                         if(!hasTask) {
                         timerExit.schedule(task, 2000);
                         }
                     } else {
                         finish(); // 離開程式
                         System.exit(0);
                     }
              }
              return false;
    }
    // -----------------------------------------------------------

    // Bluetooth connection
//    public void clickBT(View v)
//    {
//        Intent it = new Intent(MainActivity.this, DevicesActivity.class);
//        startActivity(it);
//    }
}

/*
// try thread
class NowData extends Thread
{
    static TextView tv1, tv2, tv3;

    public RequestQueue mQueue = newRequestQueue(MainActivity);
    // feed需利用Object解析
    StringRequest request = new StringRequest(Variable.urlLast,   // + "&api_key=" + Variable.api_key,

            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


//                    public static void run(String response)
//    {
        // ------- JSON 寫法 -------
        try
        {
            Thread.sleep(10000);
            JSONObject objLast = new JSONObject(response);
            String tempLast = objLast.getString("field1");
            String humiLast = objLast.getString("field2");
            String pm25Last = objLast.getString("field3");

            tv1.setText("目前溫度值:     " + tempLast + " \u2103");  //℃ Unicode
            tv2.setText("目前濕度值:     " + humiLast + " %RH");
            tv3.setText("目前PM2.5濃度:  " + pm25Last + " μg/m3");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --------------------------
//    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
    );

    mQueue.add(request);
    mQueue.start();


}
*/

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


