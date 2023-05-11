package com.nekoid.smektuber.screen.home.job;

import android.graphics.drawable.Drawable;

public class MenuJobs {
    private String DataTextJobs;
    private Drawable ImageJobs;


    public MenuJobs() {
    }

    public MenuJobs(String dataTextJobs, Drawable imageJobs) {
        DataTextJobs = dataTextJobs;
        ImageJobs = imageJobs;
    }

    public String getDataTextJobs() {
        return DataTextJobs;
    }

    public void setDataTextJobs(String dataTextJobs) {
        this.DataTextJobs = dataTextJobs;
    }

    public Drawable getImageJobs() {

        return ImageJobs;
    }

    public void setImageJobs(Drawable imageJobs) {
        this.ImageJobs = imageJobs;
    }
}
