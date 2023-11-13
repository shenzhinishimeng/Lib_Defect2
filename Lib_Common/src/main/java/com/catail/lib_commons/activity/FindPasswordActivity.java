package com.catail.lib_commons.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.R;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.utils.Config;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class FindPasswordActivity extends BaseActivity implements OnClickListener {
    private EditText phoneEdit;// 手机号
    private EditText codeEdit;// 验证码
    private TextView sendCode;// 发送验证码
    private Button nextBtn;// 下一步
    private String verficationCode;// 随机数记录
    private int timeI = 60;
    private String SMSText;
    private String phoneNumYanZheng;
    private Response response;
    private final OkHttpClient client = new OkHttpClient();

    private final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == -9) {
                Log.e("iiii", timeI + "");
                sendCode.setText(getResources().getString(R.string.send_again) + "(" + timeI + ")");
                sendCode.setClickable(false);
            } else if (msg.what == -8) {
                sendCode.setText(getResources().getString(R.string.get_code));
                sendCode.setClickable(true);
                // i = 30;
            }
        }

    };


    public void initView() {
        CommonsApplication.activityList.add(this);

        // 找回密码title
        TextView titleText = findViewById(R.id.tv_title);
        sendCode = findViewById(R.id.get_code_text);

        // 回退按钮
        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        nextBtn = findViewById(R.id.next_btn);

        phoneEdit = findViewById(R.id.phone_edit);
        codeEdit = findViewById(R.id.code_edit);

        // “密码找回”
        titleText.setText(getResources().getString(R.string.retrieve_password));
        leftBtn.setVisibility(View.VISIBLE);
//        phoneEdit.addTextChangedListener(textWatcher);
        sendCode.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
//        sendCode.setClickable(false);
//        nextBtn.setClickable(false);
    }

    /**
     * 手机号正则匹配
     *
     * @param phoneNume
     * @return
     */
    private boolean matchPhoneNumber(String phoneNume) {
        String mactchCode = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

        return phoneNume.matches(mactchCode);
    }

    private void btnSetting_1() {
        sendCode.setTextColor(getResources().getColor(R.color.blue_textcolor_39bcca));
        nextBtn.setBackgroundResource(R.drawable.login_btn);
        nextBtn.setTextColor(getResources().getColor(R.color.white_background));
        sendCode.setClickable(true);
        nextBtn.setClickable(true);

    }

    private void btnSetting_2() {
        sendCode.setClickable(false);
        nextBtn.setClickable(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonsApplication.activityList.remove(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_find_password;
    }

    @Override
    public void initData() {

    }

    public void onFinish(View view) {
        finish();
    }

    /**
     * 生成四位随机数
     */
    public static String verificationCodeGeneration(int num) {
        String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        Random rnum = new Random(new Date().getTime());

        for (int i = 0; i < digits.length; i++) {
            int index = Math.abs(rnum.nextInt()) % 10;
            String tmpDigit = digits[index];
            digits[index] = digits[i];
            digits[i] = tmpDigit;
        }

        String returnStr = digits[0];
        for (int i = 1; i < num; i++) {
            returnStr = digits[i] + returnStr;
        }
        return returnStr;
    }

    /**
     * 设置重发时间为60s
     */

    private void setTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i > 0; i--) {
                    timeI = i;
                    handler.sendEmptyMessage(-9);
                    if (i <= 0)
                        break;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
    }

    public long getCurrentTime() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis;
    }

    private long FirstClickTime;

    @Override
    public void onClick(View v) {
        int id = v.getId();// 发送验证码
        if (id == R.id.get_code_text) {
            if (phoneEdit.getText().toString().isEmpty()) {
                showToast(getResources().getString(R.string.please_enter_your_mobile_phone_number));
                return;
            }

            FirstClickTime = getCurrentTime();

            verficationCode = verificationCodeGeneration(4);
            Log.e("random=", verficationCode);
            phoneNumYanZheng = phoneEdit.getText().toString();
//                CheckPhoneIsExist(phoneNumYanZheng);
            SMSText = "【CMS】" + "Your verification code is" + " " + verficationCode + ".";
            String phone = "+65" + phoneNumYanZheng;
            Log.e("SMCText", SMSText);
            Log.e("phone", phone);
            // +65 91687854
            SendSMSToIdentifying(phone);
            setTime();
            // setTime();
            // 下一步
        } else if (id == R.id.next_btn) {
            if (phoneEdit.getText().toString().isEmpty() && codeEdit.getText().toString().isEmpty()) {
                showToast(getResources().getString(R.string.please_enter_your_mobile_phone_number));
                return;
            }
            if (codeEdit.getText().toString().isEmpty()) {
                showToast(getResources().getString(R.string.please_enter_the_verification_code));
                return;
            }

            long SeconedClickTime = getCurrentTime();
            long resultTime = SeconedClickTime - FirstClickTime;
            if (resultTime <= 60 * 1000 && resultTime >= 0) {
                if (!phoneEdit.getText().toString().trim().isEmpty()) {
                    // 手机号不能为空
                    if (!codeEdit.getText().toString().trim().isEmpty()) {
                        // 验证码不能为空
                        if (phoneNumYanZheng.equals(phoneEdit.getText().toString().trim())) {
                            // 发送验证码的手机和当前手机是否一致
                            if (verficationCode.equals(codeEdit.getText().toString().trim())) {
                                // 验证码是否一致
                                Intent intent = new Intent(FindPasswordActivity.this, ResetPassWordActivity.class);
                                intent.putExtra("phoneNumber", phoneEdit.getText().toString().trim());
                                startActivity(intent);
                                FindPasswordActivity.this.finish();
                            } else {
                                Toast.makeText(FindPasswordActivity.this,
                                                getResources().getString(R.string.verification_code_error), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } else {
                            Toast.makeText(FindPasswordActivity.this,
                                    getResources().getString(
                                            R.string.verify_that_the_phone_is_inconsistent_with_the_input_phone_number),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FindPasswordActivity.this,
                                        getResources().getString(R.string.the_captcha_can_not_be_empty), Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(FindPasswordActivity.this,
                                    getResources().getString(R.string.the_phone_number_can_not_be_empty), Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                Toast.makeText(FindPasswordActivity.this, getResources().getString(R.string.validation_timeout),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 使用JDK发送单条短信,智能匹配短信模板
     *
     * @param apikey
     *            成功注册后登录云片官网,进入后台可查看
     * @param text
     *            需要使用已审核通过的模板或者默认模板
     * @param mobile
     *            接收的手机号,仅支持单号码发送
     */
    // public void testSendSms(String apikey, String mobile, String text) {
    //
    // YunpianRestClient client = new YunpianRestClient(apikey);//
    // //用apikey生成client,可作为全局静态变量
    //
    // SmsOperator smsOperator = client.getSmsOperator();// 获取所需操作类
    // ResultDO<SendSingleSmsInfo> result = smsOperator.singleSend(mobile,
    // text);// 发送短信,ResultDO<?>.isSuccess()判断是否成功
    // Log.e("error", "短信：" + result);
    // }

    /**
     * 云片短信发送验证码
     */
    private void SendSMSToIdentifying(final String phoneNum) {
        new Thread() {
            public void run() {
                RequestBody formBody = new FormEncodingBuilder().add("apikey", Config.YunPianApikey)
                        .add("text", SMSText).add("mobile", phoneNum).build();
                // Request request = new
                // Request.Builder().url("https://sms.yunpian.com/v2/sms/single_send.json")
                Request request = new Request.Builder().url("https://us.yunpian.com/v2/sms/single_send.json")
                        .post(formBody).build();
                try {
                    response = client.newCall(request).execute();
                    if (!response.isSuccessful()){
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }.start();

    }

//    private void CheckPhoneIsExist(String userPhoneNum) {
//        String url = Config.SERVER_URLTEST + ConstantValue.CheckPhoneIsExist;
//        UserPhone userPhone = new UserPhone();
//        userPhone.setUser_phone(userPhoneNum);
//        String content = new Gson().toJson(userPhone);
//        OkHttpUtils.postString().url(url).mediaType(MediaType.parse("application/json; charset=utf-8")).content(content)
//                .build().execute(new MyStringCallback());
//    }

//    public class MyStringCallback extends Callback {
//
//        @Override
//        public void onError(Call call, Exception e, int id) {
//            Log.e("onError=", e.getMessage());
//
//        }
//
//        @Override
//        public void onResponse(Object response, int id) {
//            CheckPhoneIsExist checkPhoneIsExist = (CheckPhoneIsExist) response;
//            if (!checkPhoneIsExist.getIsexist().isEmpty() || !checkPhoneIsExist.getIsexist().equals("")
//                    || checkPhoneIsExist.getIsexist() != "") {
//                // 获取的结果不为空
//                if (checkPhoneIsExist.getIsexist().equals("1")) {
//                    SMSText = "【CMS】" + "Your verification code is" + " " + verficationCode + ".";
//                    String phone = "+65" + phoneNumYanZheng;
//                    Log.e("SMCText", SMSText);
//                    Log.e("phone", phone);
//                    // +65 91687854
//                    SendSMSToIdentifying(phone);
//                    setTime();
//                } else {
//                    Toast.makeText(FindPasswordActivity.this, getResources().getString(R.string.check_phone_is_exist),
//                            Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                return;
//            }
//        }
//
//        @Override
//        public Object parseNetworkResponse(okhttp3.Response arg0, int arg1) throws Exception {
//            String json = arg0.body().string();
//            if (!json.isEmpty() || !json.equals("") || json != "") {
//                // 获取的结果不为空
//                CheckPhoneIsExist checkPhoneIsExist = GsonUtil.GsonToBean(json, CheckPhoneIsExist.class);
//                return checkPhoneIsExist;
//            } else {
//                return null;
//            }
//
//        }
//
//    }
}
