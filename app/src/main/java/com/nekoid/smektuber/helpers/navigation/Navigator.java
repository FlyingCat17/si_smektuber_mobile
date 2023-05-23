package com.nekoid.smektuber.helpers.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

public class Navigator {
    /**
     * Used to indicate that some piece of data should be attached to some other
     * place.  For example, image data could be attached to a contact.  It is up
     * to the recipient to decide where the data should be attached; the intent
     * does not specify the ultimate destination.
     * <p>Output: nothing.
     */
    public static final String DATA = Intent.ACTION_ATTACH_DATA;

    /**
     * Activity Action: Provide explicit editable access to the given data.
     * <p>Output: nothing.
     */
    public static final String EDIT = Intent.ACTION_EDIT;

    /**
     * Activity Action: Pick an existing item, or insert a new item, and then edit it.
     * The extras can contain type specific data to pass through to the editing/creating
     * activity.
     * <p>Output: The URI of the item that was picked.  This must be a content:
     * URI so that any receiver can access it.
     */
    public static final String INSERT_OR_EDIT = Intent.ACTION_INSERT_OR_EDIT;

    /**
     * Activity Action: Pick an item from the data, returning what was selected.
     * (vnd.android.cursor.dir/*) from which to pick an item.
     * <p>Output: The URI of the item that was picked.
     */
    public static final String PICK = Intent.ACTION_PICK;


    /**
     * Activity Action: Display the data to the user.  This is the most common
     * action performed on data -- it is the generic action you can use on
     * a piece of data to get the most reasonable thing to occur.  For example,
     * when used on a contacts entry it will view the entry; when used on a
     * mailto: URI it will bring up a compose window filled with the information
     * supplied by the URI; when used with a tel: URI it will invoke the
     * dialer.
     * <p>Output: nothing.
     */
    public static final String VIEW = Intent.ACTION_VIEW;

    public static Of.Animation push(Activity activity, Class route) {
        return new Of(activity).push(route);
    }

    public static Of.Animation push(Activity activity, Class route, Object arguments) {
        return new Of(activity).push(route, arguments);
    }

    public static void openApp(Activity activity, String action, Uri uri) {
        new Of(activity).start(action, uri);
    }

    public static void openApp(Activity activity, String action) {
        openApp(activity, action, null);
    }

    /**
     * <p>Default is Action VIEW</p>
     * <p>Open youtube, facebook, whatsapp and other app with uri</p>
     * **/
    public static void openApp(Activity activity, Uri uri) {
        openApp(activity, VIEW, uri);
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
        if (args != null) return args.getArgs();
        return null;
    }
}
