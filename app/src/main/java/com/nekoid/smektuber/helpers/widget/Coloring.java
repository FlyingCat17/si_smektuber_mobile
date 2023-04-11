package com.nekoid.smektuber.helpers.widget;

import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

abstract class Coloring extends Spacing {
    public Coloring(EditText editText) {
        super(editText);
    }

    public Coloring(Button button) {
        super(button);
    }

    public Style setTextColor(Color color) {
        if (this.isButton()) {
            this.button.setTextColor(color.getColor());
        } else if (this.isEditText()) {
            this.editText.setTextColor(color.getColor());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setTextColor(String color) {
        if (this.isButton()) {
            this.button.setTextColor(android.graphics.Color.parseColor(color));
        } else if (this.isEditText()) {
            this.editText.setTextColor(android.graphics.Color.parseColor(color));
        } else {
            this.exception();
        }
        return this.instance();
    }

   public Style setTextColor(int color) {
        if (this.isButton()) {
            this.button.setTextColor(color);
        } else if (this.isEditText()) {
            this.editText.setTextColor(color);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setBackgroundColor(Color color) {
        if (this.isButton()) {
            this.button.setBackgroundColor(color.getColor());
        } else if (this.isEditText()) {
            this.editText.setBackgroundColor(color.getColor());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setBackgroundColor(String color) {
        if (this.isButton()) {
            this.button.setBackgroundColor(android.graphics.Color.parseColor(color));
        } else if (this.isEditText()) {
            this.editText.setBackgroundColor(android.graphics.Color.parseColor(color));
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setBackgroundColor(int color) {
        if (this.isButton()) {
            this.button.setBackgroundColor(color);
        } else if (this.isEditText()) {
            this.editText.setBackgroundColor(color);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setHintTextColor(Color color) {
        if (this.isButton()) {
            this.button.setHintTextColor(color.getColor());
        } else if (this.isEditText()) {
            this.editText.setHintTextColor(color.getColor());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setHintTextColor(String color) {
        if (this.isButton()) {
            this.button.setHintTextColor(android.graphics.Color.parseColor(color));
        } else if (this.isEditText()) {
            this.editText.setHintTextColor(android.graphics.Color.parseColor(color));
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setHintTextColor(int color) {
        if (this.isButton()) {
            this.button.setHintTextColor(color);
        } else if (this.isEditText()) {
            this.editText.setHintTextColor(color);
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setOutlineColor(Color color) {
        if (this.isButton()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.button.setOutlineSpotShadowColor(color.getColor());
            }
        } else if (this.isEditText()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.editText.setOutlineSpotShadowColor(color.getColor());
            }
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setOutlineColor(String color) {
        if (this.isButton()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.button.setOutlineSpotShadowColor(android.graphics.Color.parseColor(color));
            }
        } else if (this.isEditText()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.editText.setOutlineSpotShadowColor(android.graphics.Color.parseColor(color));
            }
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setOutlineColor(int color) {
        if (this.isButton()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.button.setOutlineSpotShadowColor(color);
            }
        } else if (this.isEditText()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                this.editText.setOutlineSpotShadowColor(color);
            }
        } else {
            this.exception();
        }
        return this.instance();
    }
}
