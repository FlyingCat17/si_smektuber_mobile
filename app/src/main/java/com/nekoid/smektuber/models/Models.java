package com.nekoid.smektuber.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

abstract class Models implements Serializable {
    @NonNull
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}