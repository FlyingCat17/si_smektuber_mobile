package com.nekoid.smektuber.helpers.navigation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.activity.OnBackPressedDispatcher;

public class Of {
    private Activity activity;

    public Of(Activity activity) {
        this.activity = activity;
    }

    private Animation animation() {
        return new Animation(activity);
    }

    private Intent intent(Class route) {
        return new Intent(activity, route);
    }

    private void startIntent(Intent intent) {
        startIntent(intent, false);
    }

    private void startIntent(Intent intent, Object args) {
        startIntent(intent, false, false, args);
    }

    private void startIntent(Intent intent, boolean isReplacement) {
        startIntent(intent, isReplacement, false);
    }

    private void startIntent(Intent intent, boolean isReplacement, Object args) {
        startIntent(intent, isReplacement, false, args);
    }

    private void startIntent(Intent intent, boolean isReplacement, boolean isUntil) {
        startIntent(intent, isReplacement, isUntil, null);
    }

    private void startIntent(Intent intent, boolean isReplacement, boolean isUntil, Object args) {
        if (args != null) setArgs(intent, args);
        activity.startActivity(intent);
        if (isUntil) activity.finishAffinity();
        if (isReplacement) activity.finish();
    }

    public void start(String action, Uri uri) {
        Intent intent = new Intent(action);
        if (uri != null) {
            intent.setData(uri);
        }
        activity.startActivity(intent);
    }

    public Animation push(Class route) {
        startIntent(intent(route));
        return animation();
    }

    public Animation push(Class route, Object args) {
        startIntent(intent(route), args);
        return animation();
    }

    public Animation pushUntil(Class route) {
        startIntent(intent(route), true);
        return animation();
    }

    public Animation pushUntil(Class route, Object args) {
        startIntent(intent(route), true, true, args);
        return animation();
    }

    public Animation pushReplacement(Class route) {
        startIntent(intent(route), true);
        return animation();
    }

    public Animation pushReplacement(Class route, Object args) {
        startIntent(intent(route), true, args);
        return animation();
    }

    public Animation pushReplacementUntil(Class route) {
        startIntent(intent(route), true, true);
        return animation();
    }

    public Animation pushReplacementUntil(Class route, Object args) {
        startIntent(intent(route), true, true, args);
        return animation();
    }

    public Animation pop() {
        this.back();
        return new Animation(this.activity);
    }

    public Animation pop(Object args) {
        Intent intent = this.activity.getIntent();
        if (intent != null) {
            this.setArgs(intent, args);
        }
        this.back();
        return new Animation(this.activity);
    }

    public Animation popUntil() {
        this.back();
        return new Animation(this.activity);
    }

    public Animation popUntil(Object args) {
        Intent intent = this.activity.getIntent();
        if (intent != null) {
            this.setArgs(intent, args);
        }
        this.back();
        return new Animation(this.activity);
    }

    private void back() {
        try {
            this.onBack(this.activity).onBackPressed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public class Animation {
        private Activity activity;

        public Animation(Activity activity) {
            this.activity = activity;
        }

        public void animation(int start, int end) {
            this.activity.overridePendingTransition(start, end);
        }

        public void animation(int start, int end, int bgColor) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.activity.overridePendingTransition(start, end, bgColor);
            }
        }
    }

    private void setArgs(Intent intent, Object args) {
        Arguments arguments = new Arguments(args);
        intent.putExtra("_args", arguments);
    }

    private OnBackPressedDispatcher onBack(Activity activity) {
        return new OnBackPressedDispatcher(new Runnable() {
            @Override
            public void run() {
                activity.onBackPressed();
            }
        });
    }
}
