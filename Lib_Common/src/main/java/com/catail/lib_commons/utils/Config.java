package com.catail.lib_commons.utils;

import android.content.pm.PackageInfo;
import android.os.Environment;

import com.catail.lib_commons.CommonsApplication;

public class Config {

    public static final String LOGIN_BEAN = "login_bean";// 用户登录成功后基本信息
    public static final String USERINFO = "userInfo";// 用户信息
    public static final String UserInProjectRole = "user_in_project_role";//用户在项目中的权限
    public static final String PROJECT_PERMISSION = "projectAndPermission";//项目保存的参数

    public static final String CHECKLIST_COMMENT_APPLY_USER = "CHECKLIST_COMMENT_APPLY_USER";

    public static final String LANGUAGE_KEY = "sysLanguageName";// 语言选择
    public static final String MULTILANGUAGE_KEY = "MultiLanguage_key";//新的多语言选择

    public static final String PHOTO_SRC = Environment.getExternalStorageDirectory().getPath() + "/Pictures/"; // 图片存储路径
    //2023年1月11日, 与arissa 和俞自由沟通后, 拍照,编辑,相册选择图片后的照片可以存在相册中, 签字,下载后的图片存在APP中, 用户不可见.
    public static final String SDStorageCacheInspectionPic = CommonsApplication.getContext().getExternalCacheDir() + "/Pic";
    public static final String SDStorageCache = CommonsApplication.getContext().getExternalCacheDir() + "";

    public static final String BDLocation_INFO = "bdlocation_info";//百度定位基本参数信息

    public static final String[] img = {"bmp", "jpg", "png", "jpeg", "gif"};

    public static String YunPianApikey = "f579becad5fb0555e182e35fcbc8d98e"; // 云片短信的apikey

    public static String BIMAXModelappKey = "WXV779X1ORqkxbQZZOyuoFW58UyZZOmrX6UT";//bimax 模型的appkey;
    public static String BIMAXModelappSecret = "5850b40146687cc795d992e94dc04d1ba7d76ce40dd67a59a79f9066c375df2f";//bimax 模型的appSecret

    public static String getPackName() {
        try {
            PackageInfo packageInfo = CommonsApplication
                    .getContext()
                    .getPackageManager()
                    .getPackageInfo(CommonsApplication.getContext().getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String ThambStr = "thumb_";//插入的缩略图的前缀
}
