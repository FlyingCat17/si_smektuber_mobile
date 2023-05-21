package com.nekoid.smektuber.api;

import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class PublicApi {

    private static String token;

    private static State.Api api;

    public static void addToken(String token, int expiredTime) {
        PublicApi.token = token;
        api = new State.Api(token, expiredTime);
        State.setApi(api);
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            if (api.isExpired() && Utils.getBaseActivity() != null) {
                    Utils.getBaseActivity().doLogin(
                            Utils.getBaseActivity().getAuthPreferences().getString("_username", ""),
                            Utils.getBaseActivity().getAuthPreferences().getString("_credentials", "")
                    );
                    while (Utils.getBaseActivity().isLogin()) {
                        return getHeaders();
                    }
            }
            headers.put("Authorization", "Bearer " + token);
        }

        return headers;
    }
}
