package com.catail.lib_commons.utils;


import android.util.Log;

public class Logger {
    public static boolean ENABLE = true;

    public final static int LEVEL_V = 0;
    public final static int LEVEL_D = 1;
    public final static int LEVEL_I = 2;
    public final static int LEVEL_W = 3;
    public final static int LEVEL_E = 4;

    public static int LEVEL = LEVEL_D;// 设置日志可打印的最高级别

    public static void v(String tag, String msg) {
        if (ENABLE) {
            if (LEVEL <= LEVEL_V) {
                Log.v(tag, msg);
            }
        }
    }

    public static void d(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // com.itheima.mobilesafe92.activity.HomeActivity
        String className = stackTrace[3].getClassName();
        String TAG = className.substring(className.lastIndexOf(".") + 1);
        Logger.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (ENABLE) {
            if (LEVEL <= LEVEL_D) {
                Log.d(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (ENABLE) {
            if (LEVEL <= LEVEL_I) {
                Log.i(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (ENABLE) {
            if (LEVEL <= LEVEL_W) {
                Log.w(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (ENABLE) {
            if (LEVEL <= LEVEL_E) {
                Log.e(tag, msg);
            }
        }
    }

    public static void e(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // com.itheima.mobilesafe92.activity.HomeActivity
        String className = stackTrace[3].getClassName();
        String TAG = className.substring(className.lastIndexOf(".") + 1);
        Logger.e(TAG, msg);
    }
}
