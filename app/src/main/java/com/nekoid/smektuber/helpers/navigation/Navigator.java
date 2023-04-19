package com.nekoid.smektuber.helpers.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class Navigator {
    public static final String TAG = "Navigator";

    public static Of.Animation push(Activity activity, Class route) {
        return new Of(activity).push(route);
    }

    public static Of.Animation push(Activity activity, Class route, Object arguments) {
        return new Of(activity).push(route, arguments);
    }

    public static Of.Animation pushUntil(Activity activity, Class route) {
        return new Of(activity).pushUntil(route);
    }

    public static Of.Animation pushUntil(Activity activity, Class route, Object arguments) {
        return new Of(activity).pushUntil(route, arguments);
    }

    public static Of.Animation pushReplacement(Activity activity, Class route) {
        return new Of(activity).pushReplacement(route);
    }

    public static Of.Animation pushReplacement(Activity activity, Class route, Object arguments) {
        return new Of(activity).pushReplacement(route, arguments);
    }

    public static Of.Animation pushReplacementUntil(Activity activity, Class route) {
        return new Of(activity).pushReplacementUntil(route);
    }

    public static Of.Animation pushReplacementUntil(Activity activity, Class route, Object arguments) {
        return new Of(activity).pushReplacementUntil(route, arguments);
    }

    public static Of.Animation pop(Activity activity) {
        return new Of(activity).pop();
    }

    public static Of.Animation pop(Activity activity, Object arguments) {
        return new Of(activity).pop(arguments);
    }

    public static Of.Animation popUntil(Activity activity) {
        return new Of(activity).popUntil();
    }

    public static Of.Animation popUntil(Activity activity, Object arguments) {
        return new Of(activity).popUntil(arguments);
    }

    public static Of of(Activity activity) {
        return new Of(activity);
    }

    @Nullable
    public static Object getArgs(Activity activity) {
        Intent intent = activity.getIntent();
        Arguments args = (Arguments) intent.getSerializableExtra("_args");

        if (args != null) {
            return args.getArgs();
        }

        return new Arguments(null).getArgs();
    }
}
