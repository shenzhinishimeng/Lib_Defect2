package com.catail.lib_commons.utils;

import android.util.Log;


public class MyLog {

    public static void loger(String tag, String message) {
        if (message.length() > 3000) {
            for (int i = 0; i < message.length(); i += 3000) {
                if (i + 3000 < message.length()) {
                    Log.e(tag + i, message.substring(i, i + 3000));
                } else {
                    Log.e(tag + i, message.substring(i));
                }
            }
        } else {
            Log.e(tag, message);
        }

    }
}
