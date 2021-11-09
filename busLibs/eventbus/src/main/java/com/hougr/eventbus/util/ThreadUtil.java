package com.hougr.eventbus.util;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtil {
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    public static void post2MainThread(Runnable runnable) {
        mainHandler.post(runnable);
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
