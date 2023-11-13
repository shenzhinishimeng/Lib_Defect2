package com.catail.lib_commons.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preference {
	private static final String SAVE_SYSPARAM_NAME = "QAQC_CMS_Config";
	public static SharedPreferences sysParamSp;
	public static SharedPreferences.Editor spEdditor;

	public static void createSysparamSp(Context context) {
		sysParamSp = context.getSharedPreferences(SAVE_SYSPARAM_NAME,
//				context.MODE_WORLD_READABLE|context.MODE_WORLD_WRITEABLE);
                Context.MODE_PRIVATE);
		spEdditor = sysParamSp.edit();
	}

	public static boolean saveSysparamsToSp(String key, String value)
			throws Exception {
		try {
			if (sysParamSp != null) {
				spEdditor.putString(key, value);
				spEdditor.commit();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			Log.e("error", "保存参数:" + e.getMessage());
			throw new Exception("参数保存异常");
		}

		return false;

	}

	public static String getSysparamFromSp(String key) throws Exception {
		try {
			if (sysParamSp != null) {
				return sysParamSp.getString(key, "");
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("获取参数异常");
		}
		return null;

	}
	public static void clearSp(Context context, String key) {
		createSysparamSp(context);
		sysParamSp.edit().remove(key).commit();
	}
	
}
