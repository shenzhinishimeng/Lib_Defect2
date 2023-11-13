package com.catail.bimax_inspection;

import android.content.Intent;

import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.activity.LoginActivity;
import com.catail.lib_commons.interfaces.AccountManager;
import com.catail.lib_commons.interfaces.OnLoginFinishCallback;
import com.catail.lib_commons.utils.Logger;

public class Defect2Application extends CommonsApplication {
    public static String isLite = "0";//0完整版；1Lite版；(放到这吧,使用的时候不每次都从本地取值了.)

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("onCreate");
        //调lib库的时候,在这里传this就可以设置到lib里面
//        LoginActivity activity = new LoginActivity();
//        activity.setMyCall1111(this);
        AccountManager.getInstance().registerCallback(onLoginFinishCallback);
//        accountManager = new AccountManager();
//        accountManager.registerCallback(onLoginFinishCallback);
    }

    private OnLoginFinishCallback onLoginFinishCallback = new OnLoginFinishCallback() {
        @Override
        public void onLoginFinish(LoginActivity loginActivity) {
//        public void onLoginFinish( ) {
            Logger.e("onLoginFinish");
            Intent intent = new Intent(loginActivity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
            loginActivity.finish();
        }
    };

//    @Override
//    public void onLoginFinish(LoginActivity loginActivity) {
//
//    }
}
