package com.nekoid.smektuber.screen.home.article;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MenuArtikelDashboard {
    private String DataTextArtikel;
    private Drawable ImageArtikel;
    private Bitmap imageUrl;


    public MenuArtikelDashboard() {
    }

    public MenuArtikelDashboard(String dataTextArtikel, Drawable imageArtikel) {
        DataTextArtikel = dataTextArtikel;
        ImageArtikel = imageArtikel;
    }

    public MenuArtikelDashboard(String dataTextArtikel, Bitmap imageUrl) {
        DataTextArtikel = dataTextArtikel;
        this.imageUrl = imageUrl;
    }

    public String getDataTextArtikel() {

        return DataTextArtikel;
    }

    public Bitmap getImageUrl() {
        return imageUrl;
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
