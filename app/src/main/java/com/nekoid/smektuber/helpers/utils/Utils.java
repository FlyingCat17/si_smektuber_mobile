package com.nekoid.smektuber.helpers.utils;

import android.view.animation.AlphaAnimation;

public class Utils {

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
