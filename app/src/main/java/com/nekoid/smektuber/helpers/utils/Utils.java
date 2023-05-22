package com.nekoid.smektuber.helpers.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.animation.AlphaAnimation;

public class Utils {

    private static BaseActivity baseActivity;

    public static void setBaseActivity(BaseActivity baseActivity) {
        Utils.baseActivity = baseActivity;
    }

    public static BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public static int intToDp(int size) {
        return (int) (size * baseActivity.getResources().getDisplayMetrics().density);
    }

    public static RecycleItemDecoration setRecyclerPaddingRight(int right) {
        return new RecycleItemDecoration(intToDp(right));
    }

    /**
     * <p>Set Animation for load image</p>
     * */
    public static android.view.animation.Animation animation() {
        return animation(500);
    }

    /**
     * <p>Set Animation for load image</p>
     * */
    public static android.view.animation.Animation animation(long duration) {
        android.view.animation.Animation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(duration); // Set the duration of the animation

        // Set animation listener
        fadeInAnimation.setAnimationListener(new Animation());
        return fadeInAnimation;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) baseActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    public static Uri toUri(String s) {
        return Uri.parse(s);
    }

    public static String toString(Object x) {
        return String.valueOf(x);
    }
}
