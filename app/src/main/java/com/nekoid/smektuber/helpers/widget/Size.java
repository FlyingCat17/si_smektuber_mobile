package com.nekoid.smektuber.helpers.widget;

import android.app.Activity;

public class Size {
    private Activity activity;
    int dp;
    int px;
    public Size(Activity activity, int size) {
        this.activity = activity;
        this.build(size);
    }

    private void build(int size) {
        this.dp = (int) (size * this.activity.getResources().getDisplayMetrics().density);
        this.px = (int) (size / this.activity.getResources().getDisplayMetrics().density);
    }
}
