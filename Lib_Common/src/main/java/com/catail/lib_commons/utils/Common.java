package com.catail.lib_commons.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {
    public static final String PHOTO_SRC = Environment.getExternalStorageDirectory().getPath() + "/Pictures/"; // 图片存储路径

    /**
     * 像素转屏幕无关像素（dp）
     */
    public static int px2dp(int px, Context context) {
        float desntity = context.getResources().getDisplayMetrics().density;
        return (int) (px / desntity + 0.5f);
    }

    /**
     * dp转px
     */
    public static int dp2px(int dp, Context context) {
        float desntity = context.getResources().getDisplayMetrics().density;
        return (int) (dp * desntity + 0.5f);
    }

    /**
     * 吐司
     */
    public static void showToast(final Activity activity, final String str) {
        if (activity != null) {
            activity.runOnUiThread(() -> Toast.makeText(activity, str, Toast.LENGTH_SHORT).show());
        }
    }


    /**
     * 返回当前时间 例： 2016-01-01 12:00:00
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowDateTime() {
        String retTime;
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        retTime = sDateFormat.format(new Date());
        return retTime;
    }

    /**
     * 获取当前日期 例：2016-01-01
     */
    public static String getNowDate() {
        String retDate;
        String nowTime = getNowDateTime();
        retDate = nowTime.substring(0, 10);
        return retDate;
    }

    /**
     * 2016-01-01 格式转化为 06-Apr-2016 格式
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToEnFormat(String date) {
        String ret = "";
        if (date == null || date.length() != 10 || !date.contains("-"))
            return ret;

        date = date.replaceAll("-", "/");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(date, pos);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.ENGLISH);
        ret = df.format(strtodate);
        return ret;
    }
}
