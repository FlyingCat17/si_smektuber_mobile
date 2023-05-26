package com.nekoid.smektuber.helpers.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Html;
import android.view.animation.AlphaAnimation;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.animation.Animation;

import java.util.Calendar;

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
     * <p>
     * Set Animation for load image
     * </p>
     */
    public static android.view.animation.Animation animation() {
        return animation(500);
    }

    /**
     * <p>
     * Set Animation for load image
     * </p>
     */
    public static android.view.animation.Animation animation(long duration) {
        android.view.animation.Animation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(duration); // Set the duration of the animation

        // Set animation listener
        fadeInAnimation.setAnimationListener(new Animation());
        return fadeInAnimation;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) baseActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    public static void replaceFragment(Activity activity, @IdRes int containerViewId, @NonNull Fragment fragment) {
        activity.getFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

    public static Uri toUri(String s) {
        return Uri.parse(s);
    }

    public static String toString(Object x) {
        return String.valueOf(x);
    }

    public static String strFormat(String format, Object... args) {
        return String.format(format, args);
    }


    public static PackageInfo getPackageInfo() {
        try {
            return baseActivity.getPackageManager().getPackageInfo(baseActivity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static boolean setTimeOut(long time) {
        return System.currentTimeMillis() - time > 1000;
    }

    public static long secondToMillis(long time) {
        return time * 1000;
    }

    public static String fromHtml(String html){
        return Html.fromHtml( html ).toString();
    }
}
