package com.catail.lib_commons.base;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import com.catail.lib_commons.R;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.Utils;
import com.finddreams.languagelib.MultiLanguageUtil;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private final String TAG = getClass().getSimpleName();
    protected ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setBackgroundDrawable(null);
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        Logger.e("当前Activity名称==", TAG);
        mContext = this;
        mContentResolver = getContentResolver();
        initImmersionBar(R.color.white_background_FFFFFF);//初始化沉浸式状态栏
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 返回当前Activity布局文件的id
     */
    abstract protected int getLayoutResId();

    public abstract void initView();

    public abstract void initData();

    private Dialog loadingDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgressDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = Utils.createLoadingDialog(BaseActivity.this, msg);
        }
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * 隐藏progressDialog
     */
    public void dismissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * 显示吐司消息
     */
    public void showToast(final String msg) {
        runOnUiThread(() -> {
            if (!TextUtils.isEmpty(msg)) {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示吐司消息
     */
    public void showNoDataToast() {
        runOnUiThread(() -> {
            Toast.makeText(BaseActivity.this, getResources().getString(R.string.no_data),
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        // 兼容androidX在部分手机切换语言失败问题
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MultiLanguageUtil.attachBaseContext(newBase);
        Configuration configuration = context.getResources().getConfiguration();
        // 此处的ContextThemeWrapper是androidx.appcompat.view包下的
        // 你也可以使用android.view.ContextThemeWrapper，但是使用该对象最低只兼容到API 17
        // 所以使用 androidx.appcompat.view.ContextThemeWrapper省心
        final ContextThemeWrapper wrappedContext = new ContextThemeWrapper(context,
                androidx.appcompat.R.style.Theme_AppCompat_Empty) {
            @Override
            public void applyOverrideConfiguration(Configuration overrideConfiguration) {
                if (overrideConfiguration != null) {
                    overrideConfiguration.setTo(configuration);
                }
                super.applyOverrideConfiguration(overrideConfiguration);
            }
        };
        super.attachBaseContext(wrappedContext);
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected void initImmersionBar(int color) {
        ImmersionBar mImmersionBar = ImmersionBar.with(this)
//                .statusBarColor(R.color.blue_background)
                .statusBarColor(color)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true); //使用该属性必须指定状态栏的颜色，不然状态栏透明，很难看
        mImmersionBar.init();//初始化
    }

}
