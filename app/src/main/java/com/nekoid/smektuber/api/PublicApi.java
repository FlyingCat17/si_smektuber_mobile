package com.nekoid.smektuber.api;

import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.helpers.thread.Threads;

import java.util.HashMap;
import java.util.Map;

public class PublicApi {

    public static final String BASE_URL = "https://lutfisobri.my.id/";

    public static final String API_VERSION = "api/v1";

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
                handlerHeaders();
            } else {
                headers.put("Authorization", "Bearer " + token);
            }
        }
        return headers;
    }

    private static void handlerHeaders() {
        Threads.execute((executor, handler) -> executor.execute(() -> {
            long startTime = Utils.getCurrentTimeMillis();
            Utils.getBaseActivity().doLogin();
            long timeOut = Utils.secondToMillis(60);
            while (Utils.getCurrentTimeMillis() - startTime < timeOut) {
                if (Utils.getBaseActivity().isLogin()) break;
            }
        }));
    }
}
