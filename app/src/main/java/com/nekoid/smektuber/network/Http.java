package com.nekoid.smektuber.network;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Http {

    private static final String  GET = "GET";

    private static final String POST = "POST";

    private static final String PUT = "PUT";

    public static void get(String url, Async async) throws IOException {
        get(url, null, async);
    }

    public static void get(String url, @Nullable Map<String, String> headers, Async async) throws IOException {
        run(GET, url, async, headers, null, null, null, null);
    }

    public static void post(String url, Async async) throws IOException {
        post(url, null, async);
    }

    public static void post(String url, @Nullable Map<String, String> headers, Async async) throws IOException {
        post(url, headers, null, async);
    }

    public static void post(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> body, Async async) throws IOException {
        run(POST, url, async, headers, body, null, null, null);
    }

    public static void put(String url, Async async) throws IOException {
        put(url, null, async);
    }

    public static void put(String url, @Nullable Map<String, String> headers, Async async) throws IOException {
        put(url, headers, null, async);
    }

    public static void put(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> body, Async async) throws IOException {
        run(PUT, url, async, headers, body, null, null, null);
    }

    public static void multipartFile(String url, Map<String, File> file, Async async) throws IOException {
        run(POST, url, async, null, null, file, null, null);
    }

    public static void multipartFile(String url, Map<String, File> file, @Nullable Map<String, String> headers, Async async) throws IOException {
        run(POST, url, async, headers, null, file, null, null);
    }

    public static void multipartBitmap(String url, Map<String, Bitmap> bitmap, Async async) throws IOException {
        run(POST, url, async, null, null, null, bitmap, null);
    }

    public static void multipartBitmap(String url, Map<String, Bitmap> bitmap, @Nullable Map<String, String> headers, Async async) throws IOException {
        run(POST, url, async,  headers, null, null, bitmap, null);
    }

    public static void multipartUri(String url, Map<String, Uri> uri, Async async) throws IOException {
        run(POST, url, async,  null, null, null, null, uri);
    }

    public static void multipartUri(String url, Map<String, Uri> uri, @Nullable Map<String, String> headers, Async async) throws IOException {
        run(POST, url, async, headers, null, null, null, uri);
    }

    private static void run(String mMethod, String url, Async async, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, @Nullable Map<String, File> file, @Nullable Map<String, Bitmap> bitmap, @Nullable Map<String, Uri> uri) throws IOException {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String baseUrl = "https://lutfisobri.my.id/api/v1";
            Request request = null;
            try {
                request = new Request(new URL(baseUrl + url), mMethod, headers, params, file, bitmap, uri);
                request.connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Request finalRequest = request;
            handler.post(() -> {
                async.onResponse(finalRequest.getResponse());
            });
        });
    }
}
