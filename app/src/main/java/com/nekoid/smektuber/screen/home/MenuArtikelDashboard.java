package com.nekoid.smektuber.screen.home;

import android.graphics.drawable.Drawable;

public class MenuArtikelDashboard {
    private String DataTextArtikel;
    private Drawable ImageArtikel;


    public MenuArtikelDashboard() {
    }

    public MenuArtikelDashboard(String dataTextArtikel, Drawable imageArtikel) {
        DataTextArtikel = dataTextArtikel;
        ImageArtikel = imageArtikel;
    }

    public String getDataTextArtikel() {

        return DataTextArtikel;
    }

    public void setTitleDataTextArtikel(String dataTextArtikel) {

        this.DataTextArtikel = dataTextArtikel;
    }

    public Drawable getImageArtikel() {

        return ImageArtikel;
    }

    public void setImageArtikel(Drawable imageArtikel) {

        this.ImageArtikel = imageArtikel;
    }
}
