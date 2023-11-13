package com.catail.lib_commons.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	/**
	 * 显示无数据NO DATA
	 */
	public static void toastNoData(Activity activity){
		Toast.makeText(activity, "NO DATA", Toast.LENGTH_SHORT).show();
	}
	/**
	 * 普通的toast 
	 */
	public static void toastStr(Activity activity,String string){
		Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
	}
	/**
	 * context显示无数据NO DATA
	 */
	public static void toastNoDataContext(Context context){
		Toast.makeText(context, "NO DATA", Toast.LENGTH_SHORT).show();
	}

	/**
	 * Context普通的toast
	 */
	public static void toastStrContext(Context context, String string){
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
}
