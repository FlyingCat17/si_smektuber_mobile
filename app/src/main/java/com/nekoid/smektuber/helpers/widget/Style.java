package com.nekoid.smektuber.helpers.widget;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class Style extends Coloring {
    public Style(Activity activity, Button button) {
        super(activity, button);
    }

    public Style(Activity activity, EditText editText) {
        super(activity,editText);
    }

    public Style(Activity activity, TextInputLayout textInputLayout) {
        super(activity, textInputLayout);
    }

    public Style maxLines(int lines) {
        this.maxLines = lines;
        return this;
    }

    public Style minLines(int lines) {
        this.minLines = lines;
        return this;
    }

    public Style lines(int lines) {
        this.lines = lines;
        return this;
    }

    public Style maxEms(int ems) {
        this.maxEms = ems;
        return this;
    }

    public Style minEms(int ems) {
        this.minEms = ems;
        return this;
    }

    public Style ems(int ems) {
        this.ems = ems;
        return this;
    }

    public Style font(String font) {
        this.font = new Font(this.activity, font).getFont();
        return this;
   }

    public Style text(String text) {
        this.text = text;
        return this;
    }

    public Style hintText(String text) {
        this.hintText = text;
        return this;
    }

    public Style isEnabled(boolean enabled) {
        this.isEnabled = enabled;
        return this;
    }

    public Style isFocusable(boolean focusable) {
        this.isFocusable = focusable;
        return this;
    }

    public Style isFocusableInTouchMode(boolean focusable) {
        this.isFocusableInTouchMode = focusable;
        return this;
    }

    public Style isClickable(boolean clickable) {
        this.isClickable = clickable;
        return this;
    }

    /**
     * Set visibility of widget
     * @param visibility View.VISIBLE, View.INVISIBLE, View.GONE
     * @return Style
     */
    public Style isVisible(int visibility) {
        this.isVisible = visibility;
        return this;
    }
}
