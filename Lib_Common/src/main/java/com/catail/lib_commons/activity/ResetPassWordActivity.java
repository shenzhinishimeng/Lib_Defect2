package com.catail.lib_commons.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.R;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.ConfirmChangePWDBean;
import com.catail.lib_commons.bean.ResetPwdRequestBean;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.MD5Crypto;
import com.catail.lib_commons.utils.NetApi;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class ResetPassWordActivity extends BaseActivity implements OnClickListener {

    private EditText resetpwd_New_PassWord; // 新密码
    private EditText resetpwd_Confirm_Password;// 确认新密码
    private String phoneNumber;// 前页面传来的电话号码


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_resetpassword;
    }

    @Override
    public void initData() {

    }

    public void initView() {
        CommonsApplication.activityList.add(this);

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        // 退出按钮
        ImageView restPass_exit = findViewById(R.id.title_bar_left_menu);
        // 标题
        TextView restPass_title = findViewById(R.id.tv_title);
        resetpwd_New_PassWord =  findViewById(R.id.resetpwd_new_PassWord);
        resetpwd_Confirm_Password =  findViewById(R.id.resetpwd_confirm_password);
        // 提交
        TextView resetpwd_Confirm = findViewById(R.id.resetpwd_confirm);

        resetpwd_Confirm.setOnClickListener(this);
        restPass_exit.setOnClickListener(this);
        restPass_title.setText(getResources().getString(R.string.restPassword_title));

    }

    public void ConfirmChangePassword(String userPhoneNum, String userPwd) {
        String url = NetApi.ResetPassword;
        Log.e("URL", url + "");
        String MD5userPwd = MD5Crypto.md5(userPwd);
        ResetPwdRequestBean resetPwd = new ResetPwdRequestBean();
        resetPwd.setUser_phone(userPhoneNum);
        resetPwd.setNewpwd(MD5userPwd);
        String content = new Gson().toJson(resetPwd);
        Log.e("content", content);
        OkHttpUtils.postString().url(url).mediaType(MediaType.parse("application/json; charset=utf-8")).content(content)
                .build().execute(new RestPwdCallBack());
    }

    public class RestPwdCallBack extends Callback {

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Log.e("onError", arg1.getMessage());

        }

        @Override
        public void onResponse(Object arg0, int arg1) {
            ConfirmChangePWDBean confirmChangePWD = (ConfirmChangePWDBean) arg0;
            String result = confirmChangePWD.getErrno();
            if (!result.isEmpty()) {
                if (result.equals("0")) {
                    Intent intent = new Intent(ResetPassWordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    ResetPassWordActivity.this.finish();
                } else {
                    Toast.makeText(ResetPassWordActivity.this,
                            getResources().getString(R.string.password_modification_failed), Toast.LENGTH_SHORT).show();
                    ResetPassWordActivity.this.finish();
                }
            } else {
                return;
            }
        }

        @Override
        public Object parseNetworkResponse(Response arg0, int arg1) throws Exception {
            // TODO Auto-generated method stub
            String json = arg0.body().string();
            Log.e("json=", json);
            ConfirmChangePWDBean confirmChangePWD = GsonUtil.GsonToBean(json, ConfirmChangePWDBean.class);
            return confirmChangePWD;
        }

    }

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == R.id.resetpwd_confirm) {
            String new_PassWord = resetpwd_New_PassWord.getText().toString().trim();
            String confirm_Password = resetpwd_Confirm_Password.getText().toString().trim();
            if (!new_PassWord.isEmpty()) {
                // 第一次密码不能为空
                if (!confirm_Password.isEmpty()) {
                    // 第二次输入密码不能为空
                    if (new_PassWord.length() < 6 || new_PassWord.length() >= 9) {
                        // 密码必须是6-8位
                        Toast.makeText(ResetPassWordActivity.this,
                                        getResources().getString(R.string.the_password_must_be_bits), Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    if (confirm_Password.length() < 6 || new_PassWord.length() >= 9) {
                        // 密码必须是6-8位
                        Toast.makeText(ResetPassWordActivity.this,
                                        getResources().getString(R.string.the_password_must_be_bits), Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    if (!new_PassWord.equals(confirm_Password)) {
                        // 两次输入的密码不一致
                        Toast.makeText(ResetPassWordActivity.this,
                                        getResources().getString(R.string.the_two_passwords_do_not_agree), Toast.LENGTH_SHORT)
                                .show();
                        return;
                    } else {
                        // TODO 确认修改密码
                        Log.e("New_PassWord", new_PassWord);
                        ConfirmChangePassword(phoneNumber, new_PassWord);
                    }
                } else {
                    Toast.makeText(ResetPassWordActivity.this,
                            getResources().getString(R.string.verify_that_the_password_is_not_empty),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(ResetPassWordActivity.this,
                        getResources().getString(R.string.the_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (id == R.id.title_bar_left_menu) {
            ResetPassWordActivity.this.finish();
        }

    }

}
