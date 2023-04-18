package com.nekoid.smektuber.helpers.widget;

import android.app.Activity;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;

abstract class Coloring extends Spacing {
    public Coloring(Activity activity, EditText editText) {
        super(activity, editText);
    }

    public Coloring(Activity activity, Button button) {
        super(activity, button);
    }

    public Coloring(Activity activity, TextInputLayout textInputLayout) {
        super(activity, textInputLayout);
    }

    public Style textColor(int color) {
        this.textColor = new Color(this.activity, color).getColor();
        return this.instance();
    }

    public Style textColor(String color) {
        this.textColor = android.graphics.Color.parseColor(color);
        return this.instance();
    }

    public Style backgroundColor(int color) {
        this.backgroundColor = new Color(this.activity, color).getColor();
        return this.instance();
    }

    public Style backgroundColor(String color) {
        this.backgroundColor = android.graphics.Color.parseColor(color);
        return this.instance();
    }

    public Style hintTextColor(int color) {
        this.hintTextColor = new Color(this.activity, color).getColor();
        return this.instance();
    }

    public Style hintTextColor(String color) {
        this.hintTextColor = android.graphics.Color.parseColor(color);
        return this.instance();
    }

    public Style outlineColor(int color) {
        this.outlineColor = new Color(this.activity, color).getColor();
        return this.instance();
    }

    public Style outlineColor(String color) {
        this.outlineColor = android.graphics.Color.parseColor(color);
        return this.instance();
    }
}
