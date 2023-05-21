package com.nekoid.smektuber.helpers.utils;

import android.app.Activity;
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
}
