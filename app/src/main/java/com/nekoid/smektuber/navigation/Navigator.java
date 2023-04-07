package com.nekoid.smektuber.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class Navigator {
    public static final String TAG = "Navigator";
    private static Context savedContext;

    public static void push(Context context, Class route, Object arguments) {
        Intent intent = new Intent(context, route);
        setArgs(arguments, intent);
        context.startActivity(intent);
        Log.i(TAG, "start activity : " + context.toString());
    }

    public static void push(Context context, Class route) {
        Intent intent = new Intent(context, route);
        context.startActivity(intent);
        Log.i(TAG, "start activity : " + context.toString());
    }

//    public static void pushReplacement(Context context, Class route) {
//        Intent intent = new Intent(context, route);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//    }
//
//    public static void pushReplacement(Context context, Class route, Object arguments) {
////        Intent intent = new Intent(context, route);
////        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        Intent intent = setIntent(context, route);
////        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        setArgs(arguments, intent);
//        context.startActivity(intent);
//    }
//
//    public static void pop(Context context) {
//        Context ctx = getContext();
//        Intent intent = setIntent(context, savedContext.getClass());
//
//    }

    @Nullable
    public static Object getArgs(Activity activity) {
        Intent intent = activity.getIntent();
        Arguments args = (Arguments) intent.getSerializableExtra("args");
        if (args != null) {
            Log.i(TAG, "get Arguments : " + activity.toString());
            return args.getArgs();
        }
        Log.i(TAG, "argument its not available");
        return new Arguments(null).getArgs();
    }

    private static void setArgs(Object args, Intent intent) {
        Arguments arguments = new Arguments(args);
        intent.putExtra("args", arguments);
        Log.i(TAG, "set Arguments : " + args.toString());
    }

    private static Intent setIntent(Context context, Class route) {
        Intent intent = new Intent(context, route);
        return intent;
    }

    private static void setContext(Context context) {
        savedContext = context;
    }

    private static Context getContext() {
        if (savedContext == null) {
            new Exception("Context not found");
        }
        return savedContext;
    }
}
