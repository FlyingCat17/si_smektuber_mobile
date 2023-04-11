package com.nekoid.smektuber.helpers.widget;

import android.widget.Button;
import android.widget.EditText;

abstract class Sizeable {
    Button button;
    EditText editText;
    
    public Sizeable(Button button) {
        this.button = button;
    }
    
    public Sizeable(EditText editText) {
        this.editText = editText;
    }

    public Style size(Size width, Size height) {
        return this.setWidth(width).setHeight(height);
    }

    public Style setWidth(Size size) {
        if (this.isButton()) {
            this.button.getLayoutParams().width = size.getDp();
        } else if (this.isEditText()) {
            this.editText.getLayoutParams().width = size.getDp();
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setHeight(Size size) {
        if (this.isButton()) {
            this.button.getLayoutParams().height = size.getDp();
        } else if (this.isEditText()) {
            this.editText.getLayoutParams().height = size.getDp();
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setMinWidth(Size size) {
        if (this.isButton()) {
            this.button.setMinWidth(size.getDp());
        } else if (this.isEditText()) {
            this.editText.setMinWidth(size.getDp());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setMinHeight(Size size) {
        if (this.isButton()) {
            this.button.setMinHeight(size.getDp());
        } else if (this.isEditText()) {
            this.editText.setMinHeight(size.getDp());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setMaxWidth(Size size) {
        if (this.isButton()) {
            this.button.setMaxWidth(size.getDp());
        } else if (this.isEditText()) {
            this.editText.setMaxWidth(size.getDp());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setMaxHeight(Size size) {
        if (this.isButton()) {
            this.button.setMaxHeight(size.getDp());
        } else if (this.isEditText()) {
            this.editText.setMaxHeight(size.getDp());
        } else {
            this.exception();
        }
        return this.instance();
    }

    public Style setFontSize(float size) {
        if (this.isButton()) {
            this.button.setTextSize(size);
        } else if (this.isEditText()) {
            this.editText.setTextSize(size);
        } else {
            this.exception();
        }
        return this.instance();
    }

    protected Style instance() {
        if (this.button != null) {
            return new Style(this.button);
        } else {
            return new Style(this.editText);
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
}
