package com.nekoid.smektuber.navigation;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Arguments<T> implements Serializable {
    private Class<T> arguments;
    private Object args;
    private T e;

    public Arguments(Object x) {
        this.args = x;
    }

    public Arguments(Class<T> x) {
        this.args = x;
        this.arguments = x;
    }

    @Nullable
    public Object getArgs() {
        if (this.arguments != null) {
            return this.arguments;
        }
        return this.args;
    }
}
