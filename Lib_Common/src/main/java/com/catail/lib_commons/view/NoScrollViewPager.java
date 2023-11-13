package com.catail.lib_commons.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 屏蔽viewpager响应事件的类
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //屏蔽viewpager响应事件

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //如果viewpager可以获取事件,不去拦截此事件,
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

}
