package com.example.user.dht11_get_v0;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 2016/12/5.
 */

// ------------- Solve Volley UTF8 Problem -------------------
public class MyUTF8StringRequest extends StringRequest {
    public MyUTF8StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String utf8String = null;
        try {
            utf8String = new String(response.data, "UTF-8");
            return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return super.parseNetworkResponse(response);
    }
}
