package com.nekoid.smektuber.helpers.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private static ExecutorService executorService;

    public static void shutdown() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public static void execute(Service service) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        Threads.executorService = executorService;

        service.run(executorService, handler);
    }
}
