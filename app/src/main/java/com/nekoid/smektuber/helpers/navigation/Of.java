package com.nekoid.smektuber.helpers.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;

public class Of {
    private Activity activity;

    public Of(Activity activity) {
        this.activity = activity;
    }

    public Animation push(Class route) {
        Intent intent = new Intent(this.activity, route);
        this.activity.startActivity(intent);
        return new Animation(this.activity);
    }

    public Animation push(Class route, Object args) {
        Intent intent = new Intent(this.activity, route);
        this.setArgs(intent, args);
        this.activity.startActivity(intent);
        return new Animation(this.activity);
    }

    public Animation pushUntil(Class route) {
        Intent intent = new Intent(this.activity, route);
        this.activity.startActivity(intent);
        this.activity.finishAffinity();
        this.activity.finish();
        return new Animation(this.activity);
    }

    public Animation pushUntil(Class route, Object args) {
        Intent intent = new Intent(this.activity, route);
        this.setArgs(intent, args);
        this.activity.startActivity(intent);
        this.activity.finishAffinity();
        this.activity.finish();
        return new Animation(this.activity);
    }

    public Animation pushReplacement(Class route) {
        Intent intent = new Intent(this.activity, route);
        this.activity.startActivity(intent);
        this.activity.finish();
        return new Animation(this.activity);
    }

    public Animation pushReplacement(Class route, Object args) {
        Intent intent = new Intent(this.activity, route);
        this.setArgs(intent, args);
        this.activity.startActivity(intent);
        this.activity.finish();
        return new Animation(this.activity);
    }

    public Animation pushReplacementUntil(Class route) {
        Intent intent = new Intent(this.activity, route);
        this.activity.startActivity(intent);
        this.activity.finishAffinity();
        this.activity.finish();
        return new Animation(this.activity);
    }

    public Animation pushReplacementUntil(Class route, Object args) {
        Intent intent = new Intent(this.activity, route);
        this.setArgs(intent, args);
        this.activity.startActivity(intent);
        this.activity.finishAffinity();
        this.activity.finish();
        return new Animation(this.activity);
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

    private final OnBackPressedDispatcher onBack(Activity activity) {
        return new OnBackPressedDispatcher(new Runnable() {
            @Override
            public void run() {
                activity.onBackPressed();
            }
        });
    }
}
