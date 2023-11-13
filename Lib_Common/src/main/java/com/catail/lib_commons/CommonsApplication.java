package com.catail.lib_commons;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;
import android.os.Vibrator;

import com.blankj.utilcode.util.Utils;
import com.catail.lib_commons.utils.Preference;
import com.finddreams.languagelib.MultiLanguageUtil;
import com.hjq.toast.Toaster;
import com.hjq.toast.style.WhiteToastStyle;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class CommonsApplication extends Application {
    public static List<Activity> activityList;

    public static Context mContext;
    //    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
        mContext = getApplicationContext();
        Preference.createSysparamSp(getApplicationContext());

        initOkHttp();//初始化okhttputils

//        ZXingLibrary.initDisplayOpinion(this);//初始化二维码扫描
//        initX5WebView();//x5webview

        Utils.init(this);//初始化各种工具类

//        initBDLocation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

        }
        initMultiLanguage();//多语言切换

        // 初始化吐司工具类
        Toaster.init(this, new WhiteToastStyle());


    }


    public static Context getContext() {
        return mContext;
    }

    private void initOkHttp() {

//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//              .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .sslSocketFactory(createSSLSocketFactory())
//                .sslSocketFactory(sslParams.sSLSocketFactory)
//                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
//                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void initMultiLanguage() {
        MultiLanguageUtil.init(this);
    }
}
