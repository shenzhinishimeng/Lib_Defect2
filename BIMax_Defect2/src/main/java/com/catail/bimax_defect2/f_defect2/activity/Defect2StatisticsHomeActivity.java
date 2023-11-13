package com.catail.bimax_defect2.f_defect2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2ConstructionPowerRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2ConstructionPowerResultBean;
import com.catail.bimax_defect2.f_defect2.fragment.Defect2StatisticsLineChartFragment;
import com.catail.bimax_defect2.f_defect2.fragment.Defect2StatisticsStatusPieChartFragment;
import com.catail.bimax_defect2.f_defect2.fragment.Defect2StatisticsTypePieChartFragment;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.activity.CustomFullScanActivity;
import com.catail.lib_commons.adapter.ChecklistPagerAdapter;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.base.BaseVisibleInitFragment;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.DialogHelper;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MyLog;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Util;
import com.catail.lib_commons.utils.Utils;
import com.catail.lib_commons.view.ScaleCircleNavigator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2StatisticsHomeActivity extends BaseActivity implements View.OnClickListener {
    private String projectId = "", projectName = "", mDefectType = "";
    private ViewPager mViewPager;
    private ArrayList<BaseVisibleInitFragment> recordList;
    private ImageView ivApplyBtn;
    private String mApplyPower = "0", mDraftPower = "0";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_inspection_statistics_home;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.defect2_title);
        tv_title.setVisibility(View.INVISIBLE);

        TextView tv_bottom_title = findViewById(R.id.tv_bottom_title);
        tv_bottom_title.setText(getResources().getString(R.string.defect2_construction));

        ImageView title_bar_left1 = findViewById(R.id.title_bar_left_menu);
        ImageView title_barleft2 = findViewById(R.id.title_barleft2);
//        ImageView title_bar_right3 = findViewById(R.id.title_bar_right3);
        ivApplyBtn = findViewById(R.id.title_bar_right2);
        ImageView title_bar_right1 = findViewById(R.id.title_bar_right1);
        title_bar_left1.setVisibility(View.VISIBLE);
        title_barleft2.setVisibility(View.VISIBLE);
//        title_bar_right3.setVisibility(View.VISIBLE);
//        ivApplyBtn.setVisibility(View.VISIBLE);
        title_bar_right1.setVisibility(View.VISIBLE);
        title_bar_left1.setOnClickListener(this);
        title_barleft2.setOnClickListener(this);
//        title_bar_right3.setOnClickListener(this);
        ivApplyBtn.setOnClickListener(this);
        title_bar_right1.setOnClickListener(this);

        mViewPager = findViewById(R.id.vp_content);
        recordList = new ArrayList<>();

    }


    @Override
    public void initData() {
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            projectId = projectAndPermissionBean.getProject_id();
            projectName = projectAndPermissionBean.getProject_name();
            Logger.e("projectId", projectId + "");

            mDefectType = getIntent().getStringExtra("type");

            QueryDefect2ConstructionPower();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initStatisticsFragment();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initStatisticsFragment() {
        Defect2StatisticsStatusPieChartFragment defect2StatisticsStatusPieChartFragment1
                = new Defect2StatisticsStatusPieChartFragment();
        Bundle args1 = new Bundle();
        args1.putString("projectName", projectName);
        args1.putString("projectId", projectId);
        args1.putString("status", "0");
        args1.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment1.setArguments(args1);

        Defect2StatisticsLineChartFragment defect2StatisticsLineChartFragment2
                = new Defect2StatisticsLineChartFragment();
        Bundle args2 = new Bundle();
        args2.putString("projectName", projectName);
        args2.putString("projectId", projectId);
        args2.putString("status", "1");
        args2.putString("mDefectType", mDefectType);
        defect2StatisticsLineChartFragment2.setArguments(args2);

        Defect2StatisticsLineChartFragment defect2StatisticsLineChartFragment3
                = new Defect2StatisticsLineChartFragment();
        Bundle args3 = new Bundle();
        args3.putString("projectName", projectName);
        args3.putString("projectId", projectId);
        args3.putString("status", "2");
        args3.putString("mDefectType", mDefectType);
        defect2StatisticsLineChartFragment3.setArguments(args3);

        Defect2StatisticsStatusPieChartFragment defect2StatisticsStatusPieChartFragment4
                = new Defect2StatisticsStatusPieChartFragment();
        Bundle args4 = new Bundle();
        args4.putString("projectName", projectName);
        args4.putString("projectId", projectId);
        args4.putString("status", "3");
        args4.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment4.setArguments(args4);

        Defect2StatisticsStatusPieChartFragment defect2StatisticsStatusPieChartFragment5
                = new Defect2StatisticsStatusPieChartFragment();
        Bundle args5 = new Bundle();
        args5.putString("projectName", projectName);
        args5.putString("projectId", projectId);
        args5.putString("status", "4");
        args5.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment5.setArguments(args5);

        Defect2StatisticsStatusPieChartFragment defect2StatisticsStatusPieChartFragment6
                = new Defect2StatisticsStatusPieChartFragment();
        Bundle args6 = new Bundle();
        args6.putString("projectName", projectName);
        args6.putString("projectId", projectId);
        args6.putString("status", "5");
        args6.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment6.setArguments(args6);

        Defect2StatisticsStatusPieChartFragment defect2StatisticsStatusPieChartFragment7
                = new Defect2StatisticsStatusPieChartFragment();
        Bundle args7 = new Bundle();
        args7.putString("projectName", projectName);
        args7.putString("projectId", projectId);
        args7.putString("status", "6");
        args7.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment7.setArguments(args7);

        Defect2StatisticsTypePieChartFragment defect2StatisticsStatusPieChartFragment8
                = new Defect2StatisticsTypePieChartFragment();
        Bundle args8 = new Bundle();
        args8.putString("projectName", projectName);
        args8.putString("projectId", projectId);
        args8.putString("status", "7");
        args8.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment8.setArguments(args8);

        Defect2StatisticsTypePieChartFragment defect2StatisticsStatusPieChartFragment9
                = new Defect2StatisticsTypePieChartFragment();
        Bundle args9 = new Bundle();
        args9.putString("projectName", projectName);
        args9.putString("projectId", projectId);
        args9.putString("status", "8");
        args9.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment9.setArguments(args9);

        Defect2StatisticsTypePieChartFragment defect2StatisticsStatusPieChartFragment10
                = new Defect2StatisticsTypePieChartFragment();
        Bundle args10 = new Bundle();
        args10.putString("projectName", projectName);
        args10.putString("projectId", projectId);
        args10.putString("status", "9");
        args10.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment10.setArguments(args10);

        Defect2StatisticsTypePieChartFragment defect2StatisticsStatusPieChartFragment11
                = new Defect2StatisticsTypePieChartFragment();
        Bundle args11 = new Bundle();
        args11.putString("projectName", projectName);
        args11.putString("projectId", projectId);
        args11.putString("status", "10");
        args11.putString("mDefectType", mDefectType);
        defect2StatisticsStatusPieChartFragment11.setArguments(args11);

        recordList.add(defect2StatisticsStatusPieChartFragment1);
        recordList.add(defect2StatisticsLineChartFragment2);
        recordList.add(defect2StatisticsLineChartFragment3);
        recordList.add(defect2StatisticsStatusPieChartFragment4);
        recordList.add(defect2StatisticsStatusPieChartFragment5);
        recordList.add(defect2StatisticsStatusPieChartFragment6);
        recordList.add(defect2StatisticsStatusPieChartFragment7);
        recordList.add(defect2StatisticsStatusPieChartFragment8);
        recordList.add(defect2StatisticsStatusPieChartFragment9);
        recordList.add(defect2StatisticsStatusPieChartFragment10);
        recordList.add(defect2StatisticsStatusPieChartFragment11);

        ChecklistPagerAdapter checklistPagerAdapter = new ChecklistPagerAdapter(getSupportFragmentManager(), recordList);
        mViewPager.setAdapter(checklistPagerAdapter);
        mViewPager.setOnTouchListener((view, motionEvent) -> false);

        mViewPager.setOffscreenPageLimit(11);

//        int dp12 = getResources().getDimensionPixelOffset(R.dimen.margin_5);
//        int dp25 = getResources().getDimensionPixelOffset(R.dimen.margin_10);
//        mIndicatorView.setIndicatorGap(getResources().getDimensionPixelOffset(R.dimen.margin_5));
//        mIndicatorView.setIndicatorDrawable(R.drawable.banner_indicator_focus, R.drawable.banner_indicator_nornal);
//        mIndicatorView.setIndicatorSize(dp12, dp12, dp25, dp12);
//        mIndicatorView.setupWithViewPager(mViewPager);

        initMagicIndicator1();
    }

    private void initMagicIndicator1() {
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator1);
        ScaleCircleNavigator circleNavigator = new ScaleCircleNavigator(this);
        circleNavigator.setCircleCount(recordList.size());
        circleNavigator.setNormalCircleColor(Color.LTGRAY);
        circleNavigator.setSelectedCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(index -> mViewPager.setCurrentItem(index));
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_menu) {
            Defect2StatisticsHomeActivity.this.finish();
        } else if (v.getId() == R.id.title_barleft2) {
            boolean isPermission = PermissionUtils.isGranted(Manifest.permission.CAMERA);
            if (isPermission) {
                //查询摄像头权限,有的话就执行,没有的话就return;
                intentToCaptureActivity();
            } else {
                getCameraPersimmions();
            }
        } else if (v.getId() == R.id.title_bar_right2) {
//            Intent inspectionIntent = new Intent(mContext, Defect2DrawingListActivity.class);
//            inspectionIntent.putExtra("mApplyPower", mApplyPower);
//            inspectionIntent.putExtra("mDraftPower", mDraftPower);
//            inspectionIntent.putExtra("defect_type", mDefectType);
//            mContext.startActivity(inspectionIntent);
        } else if (v.getId() == R.id.title_bar_right1) {
//            Intent inspectionIntent = new Intent(mContext, Defect2ListActivity.class);
//            inspectionIntent.putExtra("mApplyPower", mApplyPower);
//            inspectionIntent.putExtra("mDraftPower", mDraftPower);
//            inspectionIntent.putExtra("defect_type", mDefectType);
//            mContext.startActivity(inspectionIntent);
        }
    }

    public void setCurrentPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    /**
     * 获取摄像头权限
     */
    private void getCameraPersimmions() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
//                        updateAboutPermission();
                        LogUtils.d(permissionsGranted);
                        intentToCaptureActivity();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);


                    }
                })
                .theme(ScreenUtils::setFullScreen)
                .request();
    }

    /**
     * 跳转到二维码扫描界面
     */
    private void intentToCaptureActivity() {
        //判断是从哪个页面进入的
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            String projectId = projectAndPermissionBean.getProject_id();
//            Logger.e("projectId", projectId + "");
            //判断是从哪个页面进入的
            Intent intent = new Intent(Defect2StatisticsHomeActivity.this,
                    CustomFullScanActivity.class);
            intent.putExtra("flag", "task_home");
            intent.putExtra("projectId", projectId);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void QueryDefect2ConstructionPower() {
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            QueryDefect2ConstructionPowerRequestBean requestBean = new QueryDefect2ConstructionPowerRequestBean();

            requestBean.setUid(loginBean.getUid());
            requestBean.setToken(loginBean.getToken());
            requestBean.setProject_id(projectId);

            String json = GsonUtil.GsonString(requestBean);

            Logger.e("CMSC0265--查询Construction权限--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefect2ConstructionPower + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            dismissProgressDialog();
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0265--查询Construction权限--返回值", jsonStr);
                            return GsonUtil.GsonToBean(jsonStr,
                                    QueryDefect2ConstructionPowerResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @Override
                        public void onResponse(Object response, int i) {
                            QueryDefect2ConstructionPowerResultBean resultBean = (QueryDefect2ConstructionPowerResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {

                                            mApplyPower = resultBean.getResult().getApply();
//                                            mApplyPower = "1";
                                            mDraftPower = resultBean.getResult().getDraft_label();

                                            if (mApplyPower.equals("1")) {
                                                ivApplyBtn.setVisibility(View.VISIBLE);
                                            } else {
                                                ivApplyBtn.setVisibility(View.GONE);
                                            }
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()", resultBean.getErrno() + "");
                                        //Utils.showDialogLogin(getActivity());
                                        Util.showDialogLogin(Defect2StatisticsHomeActivity.this);
                                    } else if (resultBean.getErrno() == 7) {
                                        //无数据返回
                                        if (lanVersion == 0) {
                                            showToast(resultBean.getErrstr_cn());
                                        } else {
                                            showToast(resultBean.getErrstr());
                                        }
                                    } else {
                                        //其他错误代码
                                        if (lanVersion == 0) {
                                            showToast(resultBean.getErrstr_cn());
                                        } else {
                                            showToast(resultBean.getErrstr());
                                        }
                                    }
                                } else {
                                    showToast(getResources().getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                showToast("UnFinished-Unknown Error");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

