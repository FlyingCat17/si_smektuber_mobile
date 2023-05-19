package com.nekoid.smektuber.api;

import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.State;

import java.util.HashMap;
import java.util.Map;

public class PublicApi {
    private static BaseActivity baseActivity;

    private static String token;

    private static State.Api api;

    public static void setBaseActivity(BaseActivity baseActivity) {
        PublicApi.baseActivity = baseActivity;
    }

    public static void addToken(String token, int expiredTime) {
        PublicApi.token = token;
        api = new State.Api(token, expiredTime);
        State.setApi(api);
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

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
