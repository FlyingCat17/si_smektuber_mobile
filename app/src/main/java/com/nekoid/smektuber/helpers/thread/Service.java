package com.nekoid.smektuber.helpers.thread;

import android.os.Handler;

import java.util.concurrent.Executor;

public interface Service {
    void run(Executor executor, Handler handler);
}
