package com.nekoid.smektuber.helpers.widget;

import android.content.Context;
import android.graphics.Typeface;

public class Font {
    Context context;
    Typeface font;

    public Font(Context context, String font) {
        this.context = context;
        this.setFont(font);
    }

    private void setFont(String font) {
        this.font = Typeface.createFromAsset(this.context.getAssets(), font);
    }

    public Typeface getFont() {
        return font;
    }
}
