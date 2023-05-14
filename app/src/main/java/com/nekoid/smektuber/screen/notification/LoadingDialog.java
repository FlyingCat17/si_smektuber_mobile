package com.nekoid.smektuber.screen.notification;


import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.nekoid.smektuber.R;

public class LoadingDialog {

    private AlertDialog isdialog;
    private Activity mActivity;

    public LoadingDialog(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void startLoading() {
        // set View
        LayoutInflater inflater = mActivity.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setView(inflater.inflate( R.layout.custom_loader, null));
        builder.setCancelable(false);
        isdialog = builder.create();
        isdialog.show();
    }

    public void isDismiss() {
        if (isdialog != null) {
            isdialog.dismiss();
        }
    }
}