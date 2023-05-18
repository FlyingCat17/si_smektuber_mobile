package com.nekoid.smektuber.config.volley;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class Api extends Request<NetworkResponse> {
    public static interface Method {
        static int POST = Request.Method.POST;
    }
    public Api(String url, Response.ErrorListener errorListener) {
        super(url, errorListener);
    }

    public Api(int method, String url, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {

    }
}
