package com.nekoid.smektuber.helpers.widget;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

abstract class Building {
    // Activity
    protected Activity activity;

    // Attributes
    protected Button button;
    protected EditText editText;
    protected TextInputLayout textInputLayout;

    // Sizeable
    protected int width;
    protected int height;
    protected int minWidth;
    protected int minHeight;
    protected int maxWidth;
    protected int maxHeight;
    protected float fontSize;

    // Spacing
    protected int paddingLeft;
    protected int paddingRight;
    protected int paddingBottom;
    protected int paddingTop;

    // Coloring
    protected int color;
    protected int textColor;
    protected int backgroundColor;
    protected int hintTextColor;
    protected int outlineColor;

    // Style
    protected int minLines;
    protected int maxLines;
    protected int lines;
    protected int minEms;
    protected int maxEms;
    protected int ems;
    protected Typeface font;
    protected String text;
    protected String hintText;
    protected boolean isEnabled = true;
    protected boolean isFocusable = true;
    protected boolean isFocusableInTouchMode = false;
    protected boolean isClickable = true;
    protected int isVisible;
    //    private int vi

    protected void buildButton() {
        if (this.width != 0) {
            this.button.getLayoutParams().width = this.width;
        }

        if (this.height != 0) {
            this.button.getLayoutParams().height = this.height;
        }

        if (this.minWidth != 0) {
            this.button.setMinWidth(this.minWidth);
        }

        if (this.minHeight != 0) {
            this.button.setMinHeight(this.minHeight);
        }

        if (this.maxWidth != 0) {
            this.button.setMaxWidth(this.maxWidth);
        }

        if (this.maxHeight != 0) {
            this.button.setMaxHeight(this.maxHeight);
        }

        if (this.fontSize != 0) {
            this.button.setTextSize(this.fontSize);
        }

        if (this.paddingLeft != 0) {
            this.button.setPadding(this.paddingLeft, this.button.getPaddingTop(), this.button.getPaddingRight(), this.button.getPaddingBottom());
        }

        if (this.paddingRight != 0) {
            this.button.setPadding(this.button.getPaddingLeft(), this.button.getPaddingTop(), this.paddingRight, this.button.getPaddingBottom());
        }

        if (this.paddingBottom != 0) {
            this.button.setPadding(this.button.getPaddingLeft(), this.button.getPaddingTop(), this.button.getPaddingRight(), this.paddingBottom);
        }

        if (this.paddingTop != 0) {
            this.button.setPadding(this.button.getPaddingLeft(), this.paddingTop, this.button.getPaddingRight(), this.button.getPaddingBottom());
        }

        if (this.color != 0) {
            this.button.setTextColor(this.color);
        }

        if (this.backgroundColor != 0) {
            this.button.setBackgroundColor(this.backgroundColor);
        }

        if (this.minLines != 0) {
            this.button.setMinLines(this.minLines);
        }

        if (this.maxLines != 0) {
            this.button.setMaxLines(this.maxLines);
        }

        if (this.lines != 0) {
            this.button.setLines(this.lines);
        }

        if (this.minEms != 0) {
            this.button.setMinEms(this.minEms);
        }

        if (this.maxEms != 0) {
            this.button.setMaxEms(this.maxEms);
        }

        if (this.ems != 0) {
            this.button.setEms(this.ems);
        }

        if (this.font != null) {
            this.button.setTypeface(this.font);
        }

        if (this.text != null) {
            this.button.setText(this.text);
        }

        if (this.hintText != null) {
            this.button.setHint(this.hintText);
        }

        if (this.isEnabled != this.button.isEnabled()) {
            this.button.setEnabled(this.isEnabled);
        }

        if (this.isFocusable != this.button.isFocusable()) {
            this.button.setFocusable(this.isFocusable);
        }

        if (this.isFocusableInTouchMode != this.button.isFocusableInTouchMode()) {
            this.button.setFocusableInTouchMode(this.isFocusableInTouchMode);
        }

        if (this.isClickable != this.button.isClickable()) {
            this.button.setClickable(this.isClickable);
        }

        if (this.isVisible != this.button.getVisibility()) {
            this.button.setVisibility(this.isVisible);
        }
    }

    protected void buildEditText() {
        if (this.width != 0) {
            this.editText.getLayoutParams().width = this.width;
        }

        if (this.height != 0) {
            this.editText.getLayoutParams().height = this.height;
        }

        if (this.minWidth != 0) {
            this.editText.setMinWidth(this.minWidth);
        }

        if (this.minHeight != 0) {
            this.editText.setMinHeight(this.minHeight);
        }

        if (this.maxWidth != 0) {
            this.editText.setMaxWidth(this.maxWidth);
        }

        if (this.maxHeight != 0) {
            this.editText.setMaxHeight(this.maxHeight);
        }

        if (this.fontSize != 0) {
            this.editText.setTextSize(this.fontSize);
        }

        if (this.paddingLeft != 0) {
            this.editText.setPadding(this.paddingLeft, this.editText.getPaddingTop(), this.editText.getPaddingRight(), this.editText.getPaddingBottom());
        }

        if (this.paddingRight != 0) {
            this.editText.setPadding(this.editText.getPaddingLeft(), this.editText.getPaddingTop(), this.paddingRight, this.editText.getPaddingBottom());
        }

        if (this.paddingBottom != 0) {
            this.editText.setPadding(this.editText.getPaddingLeft(), this.editText.getPaddingTop(), this.editText.getPaddingRight(), this.paddingBottom);
        }

        if (this.paddingTop != 0) {
            this.editText.setPadding(this.editText.getPaddingLeft(), this.paddingTop, this.editText.getPaddingRight(), this.editText.getPaddingBottom());
        }

        if (this.color != 0) {
            this.editText.setTextColor(this.color);
        }

        if (this.backgroundColor != 0) {
            this.editText.setBackgroundColor(this.backgroundColor);
        }

        if (this.minLines != 0) {
            this.editText.setMinLines(this.minLines);
        }

        if (this.maxLines != 0) {
            this.editText.setMaxLines(this.maxLines);
        }

        if (this.lines != 0) {
            this.editText.setLines(this.lines);
        }

        if (this.minEms != 0) {
            this.editText.setMinEms(this.minEms);
        }

        if (this.maxEms != 0) {
            this.editText.setMaxEms(this.maxEms);
        }

        if (this.ems != 0) {
            this.editText.setEms(this.ems);
        }

        if (this.font != null) {
            this.editText.setTypeface(this.font);
        }

        if (this.text != null) {
            this.editText.setText(this.text);
        }

        if (this.hintText != null) {
            this.editText.setHint(this.hintText);
        }

        if (this.isEnabled != this.editText.isEnabled()) {
            this.editText.setEnabled(this.isEnabled);
        }

        if (this.isFocusable != this.editText.isFocusable()) {
            this.editText.setFocusable(this.isFocusable);
        }

        if (this.isFocusableInTouchMode != this.editText.isFocusableInTouchMode()) {
            this.editText.setFocusableInTouchMode(this.isFocusableInTouchMode);
        }

        if (this.isClickable != this.editText.isClickable()) {
            this.editText.setClickable(this.isClickable);
        }

        if (this.isVisible != this.editText.getVisibility()) {
            this.editText.setVisibility(this.isVisible);
        }
    }

    protected void buildTextInputLayout() {
        if (this.width != 0) {
            this.textInputLayout.getLayoutParams().width = this.width;
        }

        if (this.height != 0) {
            this.textInputLayout.getLayoutParams().height = this.height;
        }

        if (this.minWidth != 0) {
            this.textInputLayout.setMinWidth(this.minWidth);
        }

        if (this.maxWidth != 0) {
            this.textInputLayout.setMaxWidth(this.maxWidth);
        }

        if (this.paddingLeft != 0) {
            this.textInputLayout.setPadding(this.paddingLeft, this.textInputLayout.getPaddingTop(), this.textInputLayout.getPaddingRight(), this.textInputLayout.getPaddingBottom());
        }

        if (this.paddingRight != 0) {
            this.textInputLayout.setPadding(this.textInputLayout.getPaddingLeft(), this.textInputLayout.getPaddingTop(), this.paddingRight, this.textInputLayout.getPaddingBottom());
        }

        if (this.paddingBottom != 0) {
            this.textInputLayout.setPadding(this.textInputLayout.getPaddingLeft(), this.textInputLayout.getPaddingTop(), this.textInputLayout.getPaddingRight(), this.paddingBottom);
        }

        if (this.paddingTop != 0) {
            this.textInputLayout.setPadding(this.textInputLayout.getPaddingLeft(), this.paddingTop, this.textInputLayout.getPaddingRight(), this.textInputLayout.getPaddingBottom());
        }

        if (this.backgroundColor != 0) {
            this.textInputLayout.setBackgroundColor(this.backgroundColor);
        }

        if (this.minEms != 0) {
            this.textInputLayout.setMinEms(this.minEms);
        }

        if (this.maxEms != 0) {
            this.textInputLayout.setMaxEms(this.maxEms);
        }

        if (this.font != null) {
            this.textInputLayout.setTypeface(this.font);
        }

        if (this.hintText != null) {
            this.textInputLayout.setHint(this.hintText);
        }

        if (this.isEnabled != this.textInputLayout.isEnabled()) {
            this.textInputLayout.setEnabled(this.isEnabled);
        }

        if (this.isFocusable != this.textInputLayout.isFocusable()) {
            this.textInputLayout.setFocusable(this.isFocusable);
        }

        if (this.isFocusableInTouchMode != this.textInputLayout.isFocusableInTouchMode()) {
            this.textInputLayout.setFocusableInTouchMode(this.isFocusableInTouchMode);
        }

        if (this.isClickable != this.textInputLayout.isClickable()) {
            this.textInputLayout.setClickable(this.isClickable);
        }

        if (this.isVisible != this.textInputLayout.getVisibility()) {
            this.textInputLayout.setVisibility(this.isVisible);
        }
    }
}