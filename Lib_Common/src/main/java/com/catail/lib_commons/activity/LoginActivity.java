package com.catail.lib_commons.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.catail.lib_commons.R;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.bean.LoginRequestBean;
import com.catail.lib_commons.bean.LoginResultBean;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.interfaces.AccountManager;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.DialogHelper;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MD5Crypto;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Utils;
import com.finddreams.languagelib.LanguageType;
import com.finddreams.languagelib.MultiLanguageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;


public class LoginActivity extends BaseActivity implements OnClickListener {
    private List<TextView> languageTextList; // 切换中英文,'忘记密码'
    private List<EditText> inputEditList; // 用户名，密码输入框
    private TextView registerBtn; // 新用户注册按钮
    private TextView registerMsgText; // 注册文本信息
    private TextView tv_time; // 时间
    private Button loginBtn; // 登录按钮
    private LoginBean loginBean;

//    private OnLoginFinishCallback onLoginFinishCallback;

    @Override
    protected int getLayoutResId() {
        loginAgain();
        return R.layout.activity_login2;
    }

    @SuppressLint("SetTextI18n")
    public void initView() {
        languageTextList = new ArrayList<>();
        TextView tvEnglish = findViewById(R.id.tv_english);
        TextView tvChinese = findViewById(R.id.tv_chinese);
        TextView tvLanguage = findViewById(R.id.tv_language);
        tvLanguage.setOnClickListener(this);
        TextView tvForgetPassword = findViewById(R.id.tv_forget_password);
        languageTextList.add(tvEnglish);
        languageTextList.add(tvChinese);
        languageTextList.add(tvForgetPassword);

        inputEditList = new ArrayList<>();
        EditText etName = findViewById(R.id.et_name);
        EditText etPswd = findViewById(R.id.et_pswd);
        inputEditList.add(etName);
        inputEditList.add(etPswd);

        tv_time = findViewById(R.id.tv_time);
        registerBtn = findViewById(R.id.register);
        registerMsgText = findViewById(R.id.register_msg);
        TextView versionCodeText = findViewById(R.id.version_code);        // 版本号
        //系统的版本号
        TextView cmsVersionCode = findViewById(R.id.tv_cms_version_code);
        loginBtn = findViewById(R.id.btn_login);

        try {
            /* 获取版本号 */
            Thread.sleep(20);
            versionCodeText.setText("V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            cmsVersionCode.setText("V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < languageTextList.size(); i++) {
            languageTextList.get(i).setOnClickListener(this);
        }
        /* 登录按钮 */
        loginBtn.setOnClickListener(this);
        try {
            if (Preference.sysParamSp != null) {
                if (Preference.sysParamSp.contains(Config.LOGIN_BEAN)) {
                    String objectVal = Preference.getSysparamFromSp(Config.LOGIN_BEAN);// 获取个人基本信息
                    loginBean = (LoginBean) Utils.stringToObject(objectVal);// 登录信息字符串转对象
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginBean != null) {
            if (loginBean.getUserName() != null) {
                inputEditList.get(0).setText(loginBean.getUserName());// 记忆用户姓名
                inputEditList.get(0).setSelection(inputEditList.get(0).getText().toString().trim().length());// 设置姓名内容光标处于末尾
            }
        }
        inputEditList.get(1).setOnEditorActionListener((v, actionId, event) -> {
            String userName = inputEditList.get(0).getText().toString().trim();
            String password = inputEditList.get(1).getText().toString().trim();
            try {
                if (validateLogin(userName, password))
                    userLogin(userName, password);
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_exception),
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        // 设置下划线
        // registerBtn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        /* 注册事件 */
        registerBtn.setOnClickListener(this);
        // languageTextList.get(2).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        // if (Config.UserPhoneNum.isEmpty() || Config.UserPhoneNum.equals("")
        // || Config.UserPhoneNum == "") {
        //
        // inputEditList.get(0).setText("");
        // } else {
        // inputEditList.get(0).setText(Config.UserPhoneNum);
        // }


        //切换语言文字的显示
        int savedLanguageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (savedLanguageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            tvLanguage.setText(getResources().getString(R.string.setting_language_english));
        } else if (savedLanguageType == LanguageType.LANGUAGE_EN) {
            tvLanguage.setText(getResources().getString(R.string.setting_language_english));
        } else if (savedLanguageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            tvLanguage.setText(getResources().getString(R.string.setting_simplified_chinese));
        } else {
            tvLanguage.setText(getResources().getString(R.string.setting_simplified_chinese));
        }
    }

    private String msg;

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        //获取系统版本,然后显示中英文进度条文字
        int sysVersion = Utils.GetSystemCurrentVersion();
        if (sysVersion == 0) {
            msg = getString(R.string.processing);
        } else {
            msg = getString(R.string.processing);
        }

        try {
            ProjectAndPermissionBean bean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
        } catch (Exception e) {
            try {
                ProjectAndPermissionBean projectAndPermissionBean = new ProjectAndPermissionBean();
                projectAndPermissionBean.setProject_id("");

                String objectVal = Utils.objectToString(projectAndPermissionBean);
                Preference.saveSysparamsToSp(Config.PROJECT_PERMISSION, objectVal);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        String currentCNDate = Utils.getCurrentYYDate();
        tv_time.setText("2015-" + currentCNDate);
    }

//    public void setMyCall1111(OnLoginFinishCallback onLoginFinishCallback) {
//        Logger.e("setMyCall1111");
//        this.onLoginFinishCallback = onLoginFinishCallback;
//        Logger.e((onLoginFinishCallback != null) + "");
//    }

//    // 创建跳转接口对象
//    private SubModuleInterface subModuleInterface;

    /**
     * 免登陆
     */
    private void loginAgain() {
        try {
            LoginBean loginBean = (LoginBean) Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));
            if (loginBean != null && loginBean.isSuccess()) {

//                // 初始化跳转接口对象
//                subModuleInterface = (SubModuleInterface) new CommonsApplication();
//                // 调用跳转接口方法
//                subModuleInterface.navigateToMainModule(LoginActivity.this);

                ////////////////////////////////比如这里返回的服务器代码是-2,这时候需要通知其他activity
//                onLoginFinishCallback.onLoginFinish(LoginActivity.this);

                AccountManager.getInstance().onLoginFinishCallback.onLoginFinish(LoginActivity.this);
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 不同语言下切换页面语言内容
     */

    private void setLanguageUi() {
        /* 用户名提示 */
        inputEditList.get(0).setHint(getResources().getString(R.string.hint_enter_name));
        /* 密码输入提示 */
        inputEditList.get(1).setHint(getResources().getString(R.string.hint_enter_pswd));
        /* 登录按钮 */
        loginBtn.setText(getResources().getString(R.string.btn_sign_in));
        /* 忘记密码 */
        languageTextList.get(2).setText(getResources().getString(R.string.find_password));
        /* 新用户注册按钮 */
        registerBtn.setText(getResources().getString(R.string.register_user));
        registerMsgText.setText(getResources().getString(R.string.register_msg));

    }

    /**
     * 登录验证
     */

    private boolean validateLogin(String userName, String password) {
        if (userName.length() == 0 || userName.equals("")) {
            Toast.makeText(LoginActivity.this, getString(R.string.hint_enter_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() == 0 || password.equals("")) {
            Toast.makeText(LoginActivity.this, getString(R.string.hint_enter_pswd), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private int LanFlag;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_language) {
            //设置语言类型
            startActivity(new Intent(this, SetLanguageActivity.class));
        } else if (id == R.id.tv_english) {//                LanFlag = 1;
//                boolean isPermissionEn = PermissionUtils.isGranted(Manifest.permission.CHANGE_CONFIGURATION);
//                Logger.e("isPermission", isPermissionEn + "");
//                if (isPermissionEn) {//查询摄像头权限,有的话就执行,没有的话就return;
//                    switchEnLanguage();
//                } else {
//                    getLanguagePersimmions();
//                }
//                LanguageSettings.attachBaseContext(LoginActivity.this);
            switchEnLanguage();
        } else if (id == R.id.tv_chinese) {//                LanFlag = 0;
//                boolean isPermissionCN = PermissionUtils.isGranted(Manifest.permission.CHANGE_CONFIGURATION);
//                Logger.e("isPermission", isPermissionCN + "");
//                if (isPermissionCN) {//查询摄像头权限,有的话就执行,没有的话就return;
//                    switchCNLanguage();
//                } else {
//                    getLanguagePersimmions();
//                }
//                LanguageSettings.attachBaseContext(LoginActivity.this);

            switchCNLanguage();
        } else if (id == R.id.btn_login) {

            String userName = inputEditList.get(0).getText().toString().trim();
            String password = inputEditList.get(1).getText().toString().trim();
            try {
                if (validateLogin(userName, password)) {
                    userLogin(userName, password);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_exception),
                        Toast.LENGTH_SHORT).show();
            }
            // 注册事件
        } else if (id == R.id.register) {// 忘记密码

        } else if (id == R.id.tv_forget_password) {
            // 找回密码
            startActivity(new Intent(LoginActivity.this, FindPasswordActivity.class));
        }

    }

    /**
     * 选英文语言
     */
    private void switchEnLanguage() {
        try {
            /* 保存当前语言参数:英文环境 */
//            Preference.saveSysparamsToSp(Config.LANGUAGE_KEY, "US");
            /* 切换英文 */
//            CmsApplication.switchLanguage("US");
//            setLanguageUi();
//
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            Logger.e("设置语言:", "设置语言");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选中文语言
     */
    private void switchCNLanguage() {
        try {
            /* 保存当前语言参数:中文环境 */
//            Preference.saveSysparamsToSp(Config.LANGUAGE_KEY, "CN");
            /* 切换汉语 */
//            CmsApplication.switchLanguage("CN");
//            setLanguageUi();

//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            Logger.e("设置语言:", "设置语言");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取语言权限
     * CHANGE_CONFIGURATION
     */
    private void getLanguagePersimmions() {
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
//                        updateAboutPermission();
                        if (LanFlag == 0) {
                            switchCNLanguage();
                        } else {
                            switchEnLanguage();
                        }
                        LogUtils.d(permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                        LoginActivity.this.finish();

                    }
                })
                .theme(activity -> {
                    ScreenUtils.setFullScreen(activity);
                    Logger.e("onActivityCreate", "onActivityCreate");
                })
                .request();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//		ImmersionBar.with(this).destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    private String userName = "";

    /**
     * 用户登录
     */

    private void userLogin(String uin, String pwd) {
//        for (int i = 0; i < CMSJTCApplication.activityList.size(); i++) {
//            CMSJTCApplication.activityList.get(i).finish();
//        }
        try {
            showProgressDialog(msg);
            userName = uin;
            String pwdEncryptionStr = MD5Crypto.md5(pwd);
            String deviceInfo = "AND"
                    + "|手机品牌:" + android.os.Build.BRAND
                    + "|程序SDK:" + android.os.Build.VERSION.RELEASE
                    + "|手机SDK:" + android.os.Build.VERSION.SDK_INT
                    + "|APP版本:" + "V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

            LoginRequestBean loginRequestBean = new LoginRequestBean();
            loginRequestBean.setPhone(uin);
            loginRequestBean.setPwd(pwdEncryptionStr);
            loginRequestBean.setDevice_info(deviceInfo);
            String json = GsonUtil.GsonString(loginRequestBean);
            Logger.e("CMSC0001--登录--上传值", json);
            Logger.e("登录URL==", NetApi.Login);
            OkHttpUtils
                    .postString().
                    url(NetApi.Login)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            dismissProgressDialog();
                            String json = response.body().string();
                            Logger.e("CMSC0001--登录--返回值", json);
                            return GsonUtil.GsonToBean(json, LoginResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dismissProgressDialog();
                            e.printStackTrace();
//            Logger.e("onError", e.getMessage().toString());
                            try {
                                LoginBean loginData = new LoginBean();
                                loginData.setUserName(userName);
                                loginData.setSuccess(false);

                                String objectVal = Utils.objectToString(loginData);
                                Preference.saveSysparamsToSp(Config.LOGIN_BEAN, objectVal);

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            try {
                                LoginResultBean resultBean = (LoginResultBean) response;
//                Logger.e("CMSC0001--登录--返回值", GsonUtil.GsonString(loginBean));
                                if (resultBean.getErrno() == 0) {
                                    try {
                                        LoginBean loginData = new LoginBean();
                                        loginData.setUserName(userName);
                                        loginData.setSuccess(true);
                                        loginData.setUid(resultBean.getResult().getUid());
                                        loginData.setToken(resultBean.getResult().getToken());

                                        String objectVal = Utils.objectToString(loginData);
                                        boolean saveSysparamsFlag = Preference.saveSysparamsToSp(Config.LOGIN_BEAN, objectVal);
                                        Logger.e("saveSysparamsFlag==" + saveSysparamsFlag);
                                        // 初始化跳转接口对象
//                                        subModuleInterface = (SubModuleInterface) new CommonsApplication();
//                                        // 调用跳转接口方法
//                                        subModuleInterface.navigateToMainModule(LoginActivity.this);


//                                        Logger.e((onLoginFinishCallback !=null)+"");

//                                        onLoginFinishCallback.onLoginFinish(LoginActivity.this);
                                        //网络请求成功后,登录
//                                        AccountManager accountManager = new AccountManager();
//                                        accountManager.onLoginFinishCallback.onLoginFinish(LoginActivity.this);
                                        AccountManager.getInstance().onLoginFinishCallback.onLoginFinish(LoginActivity.this);
//                                        AccountManager.onLoginFinishCallback?.onLoginFinish();

//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(intent);
                                        //LoginActivity.this.finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    int appLan = Utils.GetSystemCurrentVersion();
                                    if (appLan == 0) {
                                        showToast(resultBean.getErrstr_cn());
                                    } else {
                                        showToast(resultBean.getErrstr());
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
