package com.catail.lib_commons.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class UIUtils {

    /**
     * dp转px
     */
    public static int dip2px(float dpValue, Context mContext) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 取屏幕宽度
     *
     */
    public static int getScreenWidth(Context mContext) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 取屏幕高度
     *
     */
    public static int getScreenHeight(Context mContext) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 取状态栏高度
     *
     */
    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 取导航栏高度
     *
     */
    public static int getNavigationBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
