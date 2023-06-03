package com.nekoid.smektuber.helpers.profile;

import android.app.Activity;

import com.github.dhaval2404.imagepicker.ImagePicker;

public abstract class CallbackListener implements DialogChangePhotoProfile.Callback {

    private Activity activity;

    public CallbackListener(Activity activity) {
        this.activity = activity;
    }

    private void start(boolean isCamera) {
        ImagePicker.Builder builder = ImagePicker.with(activity)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .cropSquare()
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .saveDir(activity.getExternalCacheDir());
        if (isCamera) {
            builder.cameraOnly();
        } else {
            builder.galleryOnly();
        }
        builder.start();
    }

    @Override
    public void onCameraClick() {
        start(true);
    }

    @Override
    public void onGalleryClick() {
        start(false);
    }

    @Override
    public void onDeleteClick() {
        onDelete();
    }

    public abstract void onDelete();
}
