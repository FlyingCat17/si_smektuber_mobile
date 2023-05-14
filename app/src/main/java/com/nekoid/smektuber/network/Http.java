package com.nekoid.smektuber.network;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class Http {

    private static final String  GET = "GET";

    private static final String POST = "POST";

    private static final String PUT = "PUT";

    public static Response get(String url) throws IOException {
        return get(url, null);
    }

    public static Response get(String url, @Nullable Map<String, String> headers) throws IOException {
        return run(GET, url, headers, null, null, null, null);
    }

    public static Response post(String url) throws IOException {
        return post(url, null);
    }

    public static Response post(String url, @Nullable Map<String, String> headers) throws IOException {
        return post(url, headers, null);
    }

    public static Response post(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> body) throws IOException {
        return run(POST, url, headers, body, null, null, null);
    }

    public static Response put(String url) throws IOException {
        return put(url, null);
    }

    public static Response put(String url, @Nullable Map<String, String> headers) throws IOException {
        return put(url, headers, null);
    }

    public static Response put(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> body) throws IOException {
        return run(PUT, url, headers, body, null, null, null);
    }

    public static Response multipartFile(String url, Map<String, File> file) throws IOException {
        return run(POST, url, null, null, file, null, null);
    }

    public static Response multipartFile(String url, Map<String, File> file, @Nullable Map<String, String> headers) throws IOException {
        return run(POST, url, headers, null, file, null, null);
    }

    public static Response multipartBitmap(String url, Map<String, Bitmap> bitmap) throws IOException {
        return run(POST, url, null, null, null, bitmap, null);
    }

    public static Response multipartBitmap(String url, Map<String, Bitmap> bitmap, @Nullable Map<String, String> headers) throws IOException {
        return run(POST, url, headers, null, null, bitmap, null);
    }

    public static Response multipartUri(String url, Map<String, Uri> uri) throws IOException {
        return run(POST, url, null, null, null, null, uri);
    }

    public static Response multipartUri(String url, Map<String, Uri> uri, @Nullable Map<String, String> headers) throws IOException {
        return run(POST, url, headers, null, null, null, uri);
    }

    private static Response run(String mMethod, String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, @Nullable Map<String, File> file, @Nullable Map<String, Bitmap> bitmap, @Nullable Map<String, Uri> uri) throws IOException {
        String baseUrl = "https://lutfisobri.my.id/api/v1";
        Request request = new Request(new URL(baseUrl + url), mMethod, headers, params, file, bitmap, uri);
        request.start();

        while (request.isAlive()) {
            if (request.getResponse() != null) {
                return request.getResponse();
            }
        }
        return null;
    }
}
