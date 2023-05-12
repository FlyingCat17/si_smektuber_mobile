package com.nekoid.smektuber.config.volley;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PublicApi {
    private static String BASE_URL = "https://lutfisobri.my.id/api/v1";

    private static Map<String, String> params = new HashMap<>();
    private static Map<String, String> headers = new HashMap<>();

    private static String token;
    private static int expiredTime;

    // method get
    public static StringRequest get(String url, Response.Listener<java.lang.String> listener) {
        return new StringRequest(Request.Method.GET, BASE_URL + url, listener, onError())  {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                return PublicApi.params;
            }

            @Nullable
            @Override
            public Map<String, String> getHeaders() {
                return PublicApi.getHeaders();
            }
        };
    }

    public static StringRequest post(String url, Response.Listener<java.lang.String> listener) {
        return new StringRequest(Request.Method.POST, BASE_URL + url, listener, onError())  {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                return PublicApi.params;
            }

            @Nullable
            @Override
            public Map<String, String> getHeaders() {
                return PublicApi.getHeaders();
            }
        };
    }

    public static StringRequest put(String url, Response.Listener<java.lang.String> listener) {
        return new StringRequest(Request.Method.PUT, BASE_URL + url, listener, onError())  {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                return PublicApi.params;
            }

            @Nullable
            @Override
            public Map<String, String> getHeaders() {
                return PublicApi.getHeaders();
            }
        };
    }

    public static void addParams(@Nullable Map<String, String> params) {
         PublicApi.params = params;
    }

    public static void addToken(String token, int expiredTime) {
        PublicApi.token = token;
        PublicApi.expiredTime = expiredTime;
    }

    private static Response.ErrorListener onError() {
        return error -> {

        };
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        if (!token.isEmpty()) {
            headers.put("Authorization", "Bearer " + token);
        }

        return headers;
    }
}
