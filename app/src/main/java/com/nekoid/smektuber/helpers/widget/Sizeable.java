package com.nekoid.smektuber.helpers.widget;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

abstract class Sizeable extends Building {
    public Sizeable(Activity activity, Button button) {
        this.activity = activity;
        this.button = button;
    }
    
    public Sizeable(Activity activity, EditText editText) {
        this.activity = activity;
        this.editText = editText;
    }
    public Sizeable(Activity activity, TextInputLayout textInputLayout) {
        this.activity = activity;
        this.textInputLayout = textInputLayout;
    }

    public Style size(int width, int height) {
        return this.setWidth(width).setHeight(height);
    }

    public Style setWidth(int size) {
        this.width = new Size(this.activity, size).dp;
        return this.instance();
    }

    public Style setHeight(int size) {
        this.height = new Size(this.activity, size).dp;
        return this.instance();
    }

    public Style setMinWidth(int size) {
        this.minWidth = new Size(this.activity, size).dp;
        return this.instance();
    }

    public Style setMinHeight(int size) {
        this.minHeight = new Size(this.activity, size).dp;
        return this.instance();
    }

    public Style setMaxWidth(int size) {
        this.maxWidth = new Size(this.activity, size).dp;
        return this.instance();
    }

    public Style setMaxHeight(int size) {
        this.maxHeight = new Size(this.activity, size).dp;
        return this.instance();
    }

    public Style fontSize(float size) {
        this.fontSize = size;
        return this.instance();
    }

    protected Style instance() {
        if (this.button != null) {
            return new Style(this.activity, this.button);
        } else {
            return new Style(this.activity, this.editText);
        }
    }

    protected void exception(String message) {
        throw new RuntimeException(message);
    }

    protected void exception() {
        throw new RuntimeException("Widget is null or not found");
    }

    protected boolean isButton() {
        if (this.button != null) {
            return true;
        }
        return false;
    }

    protected boolean isEditText() {
        if (this.editText != null) {
            return true;
        }
        return false;
    }

    public void build() {
        if (this.button != null) {
            this.buildButton();
        } else if (this.editText != null) {
            this.buildEditText();
        } else if (this.textInputLayout != null) {
            this.buildTextInputLayout();
        } else {
            this.exception();
        }
    }


}
