package com.nekoid.smektuber.screen.home.jurusan;

import android.graphics.drawable.Drawable;

public class MenuJurus {
    private String TitleMenuJurusan;
    private Drawable ImageJurus;


    public MenuJurus() {
    }

    public MenuJurus(String titleMenuJurusan, Drawable imageJurus) {
        TitleMenuJurusan = titleMenuJurusan;
        ImageJurus = imageJurus;
    }

    public String getTitleMenuJurus() {

        return TitleMenuJurusan;
    }

    public void setTitleMenuJurusan(String titleMenuExtra) {

        this.TitleMenuJurusan = titleMenuExtra;
    }

    public Drawable getImageJurus() {

        return ImageJurus;
    }

    public void setImageJurus(Drawable imageJurus) {

        this.ImageJurus = imageJurus;
    }
}
