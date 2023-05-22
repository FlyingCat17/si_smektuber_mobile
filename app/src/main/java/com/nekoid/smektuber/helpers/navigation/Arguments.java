package com.nekoid.smektuber.helpers.navigation;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Arguments implements Serializable {
    private Object args;

    public Arguments(Object x) {
        this.args = x;
    }

    @Nullable
    public Object getArgs() {
        return this.args;
    }
}
