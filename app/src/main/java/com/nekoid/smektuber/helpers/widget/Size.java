package com.nekoid.smektuber.helpers.widget;

import android.content.Context;

public class Size {
    Context context;
    int dp;
    int pixel;

    public Size(Context context, int size) {
        this.context = context;
        this.convertToPixel(size);
        this.convertToDp(size);
    }

    private void convertToDp(int size) {
        this.dp = (int) (size * this.context.getResources().getDisplayMetrics().density);
    }

    private void convertToPixel(int size) {
        this.pixel = (int) (size / this.context.getResources().getDisplayMetrics().density);
    }

    public int getDp() {
        return this.dp;
    }

    public int getPixel() {
        return this.pixel;
    }
}
