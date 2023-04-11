package com.nekoid.smektuber.helpers.widget;

import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

abstract class Spacing extends Sizeable {
    public Spacing(Button button) {
        super(button);
    }

    public Spacing(EditText editText) {
        super(editText);
    }

    public Style setPadding(int left, int top, int right, int bottom) {
        if (this.isButton()) {
            this.button.setPadding(left, top, right, bottom);
        } else if (this.isEditText()) {
            this.editText.setPadding(left, top, right, bottom);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingLeft(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(pixel, this.button.getPaddingTop(), this.button.getPaddingRight(), this.button.getPaddingBottom());
        } else if (this.isEditText()) {
            this.editText.setPadding(pixel, this.editText.getPaddingTop(), this.editText.getPaddingRight(), this.editText.getPaddingBottom());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingTop(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(this.button.getPaddingLeft(), pixel, this.button.getPaddingRight(), this.button.getPaddingBottom());
        } else if (this.isEditText()) {
            this.editText.setPadding(this.editText.getPaddingLeft(), pixel, this.editText.getPaddingRight(), this.editText.getPaddingBottom());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingRight(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(this.button.getPaddingLeft(), this.button.getPaddingTop(), pixel, this.button.getPaddingBottom());
        } else if (this.isEditText()) {
            this.editText.setPadding(this.editText.getPaddingLeft(), this.editText.getPaddingTop(), pixel, this.editText.getPaddingBottom());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingBottom(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(this.button.getPaddingLeft(), this.button.getPaddingTop(), this.button.getPaddingRight(), pixel);
        } else if (this.isEditText()) {
            this.editText.setPadding(this.editText.getPaddingLeft(), this.editText.getPaddingTop(), this.editText.getPaddingRight(), pixel);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingHorizontal(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(pixel, this.button.getPaddingTop(), pixel, this.button.getPaddingBottom());
        } else if (this.isEditText()) {
            this.editText.setPadding(pixel, this.editText.getPaddingTop(), pixel, this.editText.getPaddingBottom());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingVertical(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(this.button.getPaddingLeft(), pixel, this.button.getPaddingRight(), pixel);
        } else if (this.isEditText()) {
            this.editText.setPadding(this.editText.getPaddingLeft(), pixel, this.editText.getPaddingRight(), pixel);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setPaddingAll(int pixel) {
        if (this.isButton()) {
            this.button.setPadding(pixel, pixel, pixel, pixel);
        } else if (this.isEditText()) {
            this.editText.setPadding(pixel, pixel, pixel, pixel);
        } else {
            this.exception();
        }
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
