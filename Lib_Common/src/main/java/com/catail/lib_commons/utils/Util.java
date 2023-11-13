package com.catail.lib_commons.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.R;
import com.catail.lib_commons.activity.LoginActivity;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;

public class Util {
    /**
     * 重新登录
     */
    public static void showDialogLogin(final AppCompatActivity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.alertdialog_login, null);
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (activity != null && activity instanceof Activity && !(activity).isFinishing()) {
            alertDialog.show();
        }
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(R.layout.alertdialog_login);
        TextView quitTextView = window.findViewById(R.id.quit_text);

//        try {
//            UserInfoBean userInfoBean = (UserInfoBean) Utils
//                    .stringToObject(Preference.getSysparamFromSp(Config.USERINFO));
//            JPushInterface.deleteAlias(activity, Integer.parseInt(userInfoBean.getUserId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        quitTextView.setOnClickListener(view1 -> {
            if (alertDialog.isShowing())
                alertDialog.dismiss();

            try {
                LoginBean loginBean = (LoginBean) Utils.stringToObject(Preference
                        .getSysparamFromSp(Config.LOGIN_BEAN));
                loginBean.setSuccess(false);
                String objectVal = Utils.objectToString(loginBean);
                Preference.saveSysparamsToSp(Config.LOGIN_BEAN, objectVal);
                Preference.clearSp(activity, Config.PROJECT_PERMISSION);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < CommonsApplication.activityList.size(); i++) {
                CommonsApplication.activityList.get(i).finish();
            }
            /* 回退到登录界面 */
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        });
        TextView loginAgainText = window.findViewById(R.id.login_again_text);
        loginAgainText.setOnClickListener(view12 -> {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
            try {
                LoginBean loginBean = (LoginBean) Utils.stringToObject(Preference
                        .getSysparamFromSp(Config.LOGIN_BEAN));
                loginBean.setSuccess(false);

                String objectVal = Utils.objectToString(loginBean);
                Preference.saveSysparamsToSp(Config.LOGIN_BEAN, objectVal);
                Preference.clearSp(activity, Config.PROJECT_PERMISSION);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < CommonsApplication.activityList.size(); i++) {
                CommonsApplication.activityList.get(i).finish();
            }
            /* 回退到登录界面 */
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        });
        Utils.setAlertDialogConner(alertDialog);
        Utils.setAlertDialogSize(activity, alertDialog, 0);
    }

    public static String SpecialProjectshowTime(String date) {
        String returndate = "";
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            String qa_task_showtime = projectAndPermissionBean.getQa_task_showtime();
//            Logger.e("qa_task_showtime==", qa_task_showtime + "---");
            if (qa_task_showtime.equals("1")) {
                returndate = DateFormatUtils.CNStr2ENStr(date);
                return returndate;
            } else {
                returndate = DateFormatUtils.CNStr2ENStrNoTime(date);
                return returndate;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("时间解析异常", "时间解析异常,为空或者解析错误");
            return returndate;
        }
    }

}
