package com.nekoid.smektuber.screen.home;

import android.graphics.drawable.Drawable;

public class MenuExtra {
    private String TitleMenuExtra;
    private Drawable ImageExtra;


    public MenuExtra() {
    }

    public MenuExtra(String titleMenuExtra, Drawable imageExtra) {
        TitleMenuExtra = titleMenuExtra;
        ImageExtra = imageExtra;
    }

    public String getTitleMenuExtra() {
        return TitleMenuExtra;
    }

    public void setTitleMenuExtra(String titleMenuExtra) {
        this.TitleMenuExtra = titleMenuExtra;
    }

    public Drawable getImageExtra() {

        return ImageExtra;
    }

    public void setImageExtra(Drawable imageExtra) {
        this.ImageExtra = imageExtra;
    }
}
