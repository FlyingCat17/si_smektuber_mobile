package com.nekoid.smektuber.navigation;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Arguments<T> implements Serializable {
    private Args<T> arguments;
    private Object args;
    private T e;

    public Arguments(Object x) {
        this.args = x;
    }

    public Arguments(Class<T> x) {

    }

    @Nullable
    public Object getArgs() {
        return this.args;
    }
}
