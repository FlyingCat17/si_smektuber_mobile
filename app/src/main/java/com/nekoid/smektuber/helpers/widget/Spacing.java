package com.nekoid.smektuber.helpers.widget;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

abstract class Spacing extends Sizeable {
    public Spacing(Activity activity, Button button) {
        super(activity, button);
    }

    public Spacing(Activity activity, EditText editText) {
        super(activity, editText);
    }

    public Spacing(Activity activity, TextInputLayout textInputLayout) {
        super(activity, textInputLayout);
    }

    public Style padding(int left, int top, int right, int bottom) {
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        return this.instance();
    }

    public Style paddingLeft(int pixel) {
        this.paddingLeft = pixel;
        return this.instance();
    }

    public Style paddingTop(int pixel) {
        this.paddingTop = pixel;
        return this.instance();
    }

    public Style paddingRight(int pixel) {
        this.paddingRight = pixel;
        return this.instance();
    }

    public Style paddingBottom(int pixel) {
        this.paddingBottom = pixel;
        return this.instance();
    }

    public Style paddingHorizontal(int pixel) {
        this.paddingLeft = pixel;
        this.paddingRight = pixel;
        return this.instance();
    }

    public Style paddingVertical(int pixel) {
        this.paddingTop = pixel;
        this.paddingBottom = pixel;
        return this.instance();
    }

    public Style paddingAll(int pixel) {
        this.paddingLeft = pixel;
        this.paddingTop = pixel;
        this.paddingRight = pixel;
        this.paddingBottom = pixel;
        return this.instance();
    }

    public Style setGravity(int gravity) {
        if (this.isButton()) {
            this.button.setGravity(gravity);
        } else if (this.isEditText()) {
            this.editText.setGravity(gravity);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityLeft() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.LEFT);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.LEFT);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityCenter() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.CENTER);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.CENTER);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityRight() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.RIGHT);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.RIGHT);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityTop() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.TOP);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.TOP);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityBottom() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.BOTTOM);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.BOTTOM);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityCenterVertical() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.CENTER_VERTICAL);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.CENTER_VERTICAL);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityCenterHorizontal() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.CENTER_HORIZONTAL);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setGravityCenterVerticalHorizontal() {
        if (this.isButton()) {
            this.button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        } else if (this.isEditText()) {
            this.editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        } else {
            this.exception();
        }
        return this.instance();
    }
}
