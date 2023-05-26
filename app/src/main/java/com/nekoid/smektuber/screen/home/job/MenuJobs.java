package com.nekoid.smektuber.screen.home.job;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MenuJobs {
    private String DataTextJob;
    private Drawable ImageJob;
    private Bitmap imageUrl;
    public MenuJobs() {
    }

    public MenuJobs(String dataTextJob, Drawable imageJob) {
        DataTextJob = dataTextJob;
        ImageJob = imageJob;
    }

    public MenuJobs(String dataTextJob, Bitmap imageUrl) {
        DataTextJob = dataTextJob;
        this.imageUrl = imageUrl;
    }

    public String getdataTextJob() {

        return DataTextJob;
    }

    public Bitmap getImageUrl() {
        return imageUrl;
    }

    public void setTitledataTextJob(String dataTextJob) {

        this.DataTextJob = dataTextJob;
    }

    public Drawable getImageJob() {

        return ImageJob;
    }

    public void setImageJob(Drawable imageJob) {

        this.ImageJob = imageJob;
    }
}

