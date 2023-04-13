package com.nekoid.smektuber.helpers.widget;

import android.widget.Button;
import android.widget.EditText;

public class Style extends Coloring {
    public Style(Button button) {
        super(button);
    }

    public Style(EditText editText) {
        super(editText);
    }

    public Style setMaxLines(int lines) {
        if (this.isButton()) {
            this.button.setMaxLines(lines);
        } else if (this.isEditText()) {
            this.editText.setMaxLines(lines);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setMinLines(int lines) {
        if (this.isButton()) {
            this.button.setMinLines(lines);
        } else if (this.isEditText()) {
            this.editText.setMinLines(lines);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setLines(int lines) {
        if (this.isButton()) {
            this.button.setLines(lines);
        } else if (this.isEditText()) {
            this.editText.setLines(lines);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setMaxEms(int ems) {
        if (this.isButton()) {
            this.button.setMaxEms(ems);
        } else if (this.isEditText()) {
            this.editText.setMaxEms(ems);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setMinEms(int ems) {
        if (this.isButton()) {
            this.button.setMinEms(ems);
        } else if (this.isEditText()) {
            this.editText.setMinEms(ems);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setEms(int ems) {
        if (this.isButton()) {
            this.button.setEms(ems);
        } else if (this.isEditText()) {
            this.editText.setEms(ems);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setFont(Font font) {
        if (this.isButton()) {
             this.button.setTypeface(font.getFont());
        } else if (this.isEditText()) {
            this.editText.setTypeface(font.getFont());
        } else {
            this.exception();
        }
        return this;
   }

    public Style setText(String text) {
        if (this.isButton()) {
            this.button.setText(text);
        } else if (this.isEditText()) {
            this.editText.setText(text);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setHintText(String text) {
        if (this.isButton()) {
            this.button.setHint(text);
        } else if (this.isEditText()) {
            this.editText.setHint(text);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setEnabled(boolean enabled) {
        if (this.isButton()) {
            this.button.setEnabled(enabled);
        } else if (this.isEditText()) {
            this.editText.setEnabled(enabled);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setFocusable(boolean focusable) {
        if (this.isButton()) {
            this.button.setFocusable(focusable);
        } else if (this.isEditText()) {
            this.editText.setFocusable(focusable);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setFocusableInTouchMode(boolean focusable) {
        if (this.isButton()) {
            this.button.setFocusableInTouchMode(focusable);
        } else if (this.isEditText()) {
            this.editText.setFocusableInTouchMode(focusable);
        } else {
            this.exception();
        }
        return this;
    }

    public Style setClickable(boolean clickable) {
        if (this.isButton()) {
            this.button.setClickable(clickable);
        } else if (this.isEditText()) {
            this.editText.setClickable(clickable);
        } else {
            this.exception();
        }
        return this;
    }

    /**
     * Set visibility of widget
     * @param visibility View.VISIBLE, View.INVISIBLE, View.GONE
     * @return Style
     */
    public Style setVisibility(int visibility) {
        if (this.isButton()) {
            this.button.setVisibility(visibility);
        } else if (this.isEditText()) {
            this.editText.setVisibility(visibility);
        } else {
            this.exception();
        }
        return this;
    }
}
