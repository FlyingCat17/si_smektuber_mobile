package com.nekoid.smektuber.helpers.widget;

import android.content.Context;

public class Color {
    Context context;
    int color;

    public Color(Context context, int color) {
        this.context = context;
        this.setColor(color);
    }

    private void setColor(int color) {
        this.color = this.context.getResources().getColor(color);
    }

    public int getColor() {
        return color;
    }
}
