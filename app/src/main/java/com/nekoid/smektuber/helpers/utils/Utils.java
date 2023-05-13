package com.nekoid.smektuber.helpers.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.IOException;
import java.net.URL;

public class Utils {
    public static Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL uri = new URL(url);
                bitmap = BitmapFactory.decodeStream(uri.openConnection().getInputStream());
            }
        } catch (IOException e) {
            bitmap = null;
        }
        return bitmap;
    }
}
