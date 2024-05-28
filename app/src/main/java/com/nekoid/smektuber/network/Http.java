package com.nekoid.smektuber.network;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.nekoid.smektuber.helpers.thread.Threads;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class Http {

    private static final String GET = "GET";

    private static final String POST = "POST";

    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    public static void get(String url, Async async) {
        get(url, null, async);
    }

    public static void get(String url, @Nullable Map<String, String> headers, Async async) {
        run(GET, url, async, headers, null, null, null, null);
    }

    public static void post(String url, Async async) {
        post(url, null, async);
    }

    public static void post(String url, @Nullable Map<String, String> headers, Async async) {
        post(url, headers, null, async);
    }

    public static void post(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> body, Async async) {
        run(POST, url, async, headers, body, null, null, null);
    }

    public static void put(String url, Async async) {
        put(url, null, async);
    }

    public static void put(String url, @Nullable Map<String, String> headers, Async async) {
        put(url, headers, null, async);
    }

    public static void put(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> body, Async async) {
        run(PUT, url, async, headers, body, null, null, null);
    }

    public static void multipartFile(String url, Map<String, File> file, Async async) {
        run(POST, url, async, null, null, file, null, null);
    }

    public static void multipartFile(String url, Map<String, File> file, @Nullable Map<String, String> headers, Async async) {
        run(POST, url, async, headers, null, file, null, null);
    }
//    public static void deleteFile(String url, Map<String, File> file, @Nullable Map<String, String> headers, Async async) {
//        run(DELETE, url, async, headers, null, null, null, null);
//    }
    public static void delete(String url, @Nullable Map<String, String> headers, Async async) {
        run(DELETE, url, async, headers, null, null, null, null);
    }



    @Deprecated
    public static void multipartBitmap(String url, Map<String, Bitmap> bitmap, Async async) {
        run(POST, url, async, null, null, null, bitmap, null);
    }

    @Deprecated
    public static void multipartBitmap(String url, Map<String, Bitmap> bitmap, @Nullable Map<String, String> headers, Async async) {
        run(POST, url, async, headers, null, null, bitmap, null);
    }

    @Deprecated
    public static void multipartUri(String url, Map<String, Uri> uri, Async async) {
        run(POST, url, async, null, null, null, null, uri);
    }

    @Deprecated
    public static void multipartUri(String url, Map<String, Uri> uri, @Nullable Map<String, String> headers, Async async) {
        run(POST, url, async, headers, null, null, null, uri);
    }

    public static void loadImage(String url, ImageView imageView, Animation animation, LoadImage loadImage) {
        if (url != null && imageView != null) {
            onLoadImage(url, imageView, animation, loadImage);
        }
    }

    public static void loadImage(String url, ImageView imageView, Animation animation) {
        if (url != null && imageView != null) {
            onLoadImage(url, imageView, animation, null);
        }
    }

    public static void loadImage(String url, ImageView imageView, LoadImage loadImage) {
        if (imageView != null && url != null) {
            onLoadImage(url, imageView, null, loadImage);
        }
    }

    public static void loadImage(String url, ImageView imageView) {
        if (imageView != null && url != null) {
            onLoadImage(url, imageView, null, null);
        }
    }

    public static void loadImageWithoutCache(String url, ImageView imageView, Animation animation, LoadImage loadImage) {
        if (url != null && imageView != null) {
            onLoadImageWithoutCache(url, imageView, animation, loadImage);
        }
    }

    public static void loadImageWithoutCache(String url, ImageView imageView, Animation animation) {
        if (url != null && imageView != null) {
            loadImageWithoutCache(url, imageView, animation, null);
        }
    }

    public static void loadImageWithoutCache(String url, ImageView imageView, LoadImage loadImage) {
        if (imageView != null && url != null) {
            loadImageWithoutCache(url, imageView, null, loadImage);
        }
    }

    public static void loadImageWithoutCache(String url, ImageView imageView) {
        if (imageView != null && url != null) {
            loadImageWithoutCache(url, imageView, null, null);
        }
    }

    public static void loadImageToBitmap(String url, ResponseBitmap response) {
        Cache cache = Cache.getInstance();

        if (cache.get(url) != null) {
            response.onResponse((Bitmap) cache.get(url));
            return;
        }

        Threads.execute((executor, handler) -> {
            executor.execute(() -> {
                Request request;
                try {
                    request = new Request(new URL(url));
                    request.loadImage();
                } catch (IOException e) {
                    Threads.shutdown();
                    return;
                }
                Request finalRequest = request;
                handler.post(() -> {
                    response.onResponse(finalRequest.getImageBitmap());
                    cache.put(url, finalRequest.getImageBitmap());
                });
            });
        });
    }

    private static void onLoadImage(String url, ImageView imageView, Animation animation, LoadImage loadImage) {
        Cache cache = Cache.getInstance();

        if (cache.get(url) != null) {
            setImageFromBitmap(imageView, (Bitmap) cache.get(url), animation, loadImage);
            return;
        }
        executeUrlForImage(url, imageView, animation, cache, loadImage);
    }

    private static void onLoadImageWithoutCache(String url, ImageView imageView, Animation animation, LoadImage loadImage) {
        executeUrlForImage(url, imageView, animation, null, loadImage);
    }

    private static void executeUrlForImage(String url, ImageView imageView, Animation animation, Cache cache, LoadImage loadImage) {
        Threads.execute((executor, handler) -> {
            executor.execute(() -> {
                Request request;
                try {
                    request = new Request(new URL(url));
                    request.loadImage();
                } catch (IOException e) {
                    Threads.shutdown();
                    return;
                }
                Request finalRequest = request;
                handler.post(() -> {
                    setImageFromBitmap(imageView, finalRequest.getImageBitmap(), animation, loadImage);
                    if (cache != null) cache.put(url, finalRequest.getImageBitmap());
                });
            });
        });
    }

    private static void setImageFromBitmap(ImageView imageView, Bitmap bitmap, Animation animation, LoadImage loadImage) {
        if (animation != null) {
            imageView.startAnimation(animation);
        }
        imageView.setImageBitmap(bitmap);
        if (loadImage != null) {
            loadImage.onLoad();
        }
    }

    private static void run(String mMethod, String url, Async async, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, @Nullable Map<String, File> file, @Nullable Map<String, Bitmap> bitmap, @Nullable Map<String, Uri> uri) {
        Threads.execute((executor, handler) -> {
            executor.execute(() -> {
                Request request;
                try {
                    request = new Request(new URL(url), mMethod, headers, params, file, bitmap, uri);
                    request.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                    Threads.shutdown();
                    return;
                }
                Request finalRequest = request;
                handler.post(() -> {
                    async.onResponse(finalRequest.getResponse());
                });
            });
        });
    }

    public interface LoadImage {
        void onLoad();
    }
}
