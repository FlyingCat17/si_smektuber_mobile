package com.nekoid.smektuber.helpers.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.nekoid.smektuber.R;

public class DialogChangePhotoProfile {
    public static final int IMAGE_RESULT = 33;

    private String title = "Pilih Gambar";

    public DialogChangePhotoProfile title(String title) {
        this.title = title;
        return this;
    }

    @SuppressLint("InflateParams")
    public void show(Activity activity, Callback callback) {
        View view = LayoutInflater.from(activity).inflate(R.layout.fragment_chose_image, null);

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(view)
                .create();
        dialog.show();

        view.findViewById(R.id.lytCameraPick).setOnClickListener(v -> {
            dialog.dismiss();
            callback.onCameraClick();
        });

        view.findViewById(R.id.lytGalleryPick).setOnClickListener(v -> {
            dialog.dismiss();
            callback.onGalleryClick();
        });

        view.findViewById(R.id.lytImageDelete).setOnClickListener(v -> {
            dialog.dismiss();
            callback.onDeleteClick();
        });
    }

    public interface Callback {
        void onCameraClick();
        void onGalleryClick();
        void onDeleteClick();
    }
}
