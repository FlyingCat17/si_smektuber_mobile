package com.nekoid.smektuber.config.volley;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.LocalStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class PublicApi {
    private static BaseActivity baseActivity;
    private static String BASE_URL = "https://lutfisobri.my.id/api/v1";

    private static Map<String, String> params = new HashMap<>();
    private static Map<String, String> headers = new HashMap<>();

    private static String token;
    private static int expiredTime;
    private static Timer timer;
    private static LocalStorage.Api api;

    public static void setBaseActivity(BaseActivity baseActivity) {
        PublicApi.baseActivity = baseActivity;
    }

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
        api = new LocalStorage.Api(token, expiredTime);
        LocalStorage.setApi(api);
    }

    private static Response.ErrorListener onError() {
        return error -> {

        };
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        System.out.println(token);

        if (token != null) {
            if (api.isExpired() && baseActivity != null) {
                    baseActivity.doLogin(
                            baseActivity.getAuthPreferences().getString("_username", ""),
                            baseActivity.getAuthPreferences().getString("_credentials", "")
                    );
                return getHeaders();
            }
            headers.put("Authorization", "Bearer " + token);
        }

        return headers;
    }
}
