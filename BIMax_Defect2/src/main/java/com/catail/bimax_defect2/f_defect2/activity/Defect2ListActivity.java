package com.catail.bimax_defect2.f_defect2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.fragment.Defect2ListsFragment;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.activity.CustomFullScanActivity;
import com.catail.lib_commons.adapter.InspectionPagerAdapter;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.base.BaseVisibleInitFragment;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.DialogHelper;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Utils;
import com.catail.lib_commons.view.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class Defect2ListActivity extends BaseActivity implements View.OnClickListener {
    private NoScrollViewPager mVpContent;
    private ArrayList<BaseVisibleInitFragment> recordList;
    private String projectId = "", projectName = "", mDefectType = "";
    //    private ImageView ivDelBtn;
    private Defect2ListsFragment checklistOngoingFragment;
    private Defect2ListsFragment checklistSolvedFragment;
    private Defect2ListsFragment checklistOnholdFragment;
    private Defect2ListsFragment checklistOverdueFragment;
    private Defect2ListsFragment checklistDraftFragment;

    private String mBlockStr = "", mLevelStr = "", mZoneStr = "";
    private String mDisciplineId = "", mDisciplineName = "", mSubConId = "", mSubConName = "";
    private String isInitiatedByMe = "0";
    private SlidingTabLayout mSlidingTabLayout;
    private String mApplyPower = "0", mDraftPower = "0";
    private ImageView applyBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_inspection_list1;
    }

    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.defect2_title);
        tv_title.setVisibility(View.INVISIBLE);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        ImageView addBtn = findViewById(R.id.title_bar_right1);
        addBtn.setVisibility(View.VISIBLE);
        addBtn.setImageResource(R.mipmap.filter_blue);
        addBtn.setOnClickListener(this);

        applyBtn = findViewById(R.id.title_bar_right2);
        applyBtn.setVisibility(View.INVISIBLE);
        applyBtn.setOnClickListener(this);

        ImageView title_barleft2 = findViewById(R.id.title_barleft2);
        title_barleft2.setVisibility(View.VISIBLE);
        title_barleft2.setOnClickListener(this);

        ImageView title_barleft3 = findViewById(R.id.title_barleft3);
        title_barleft3.setOnClickListener(this);

        mSlidingTabLayout = findViewById(R.id.tablayout);
        mVpContent = findViewById(R.id.vp_content);
        recordList = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            projectId = projectAndPermissionBean.getProject_id();
            projectName = projectAndPermissionBean.getProject_name();

            mDefectType = getIntent().getStringExtra("defect_type");
            mApplyPower = getIntent().getStringExtra("mApplyPower");
            mDraftPower = getIntent().getStringExtra("mDraftPower");

            if (mApplyPower.equals("1")) {
                applyBtn.setVisibility(View.VISIBLE);
            } else {
                applyBtn.setVisibility(View.INVISIBLE);
            }

            Logger.e("projectId", projectId + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> mTitleList = new ArrayList<>();
        mTitleList.add(getResources().getString(R.string.defect2_ongoing));
        mTitleList.add(getResources().getString(R.string.defect2_solved));
        mTitleList.add(getResources().getString(R.string.defect2_overdue));
        mTitleList.add(getResources().getString(R.string.defect2_onhold));
        if (mDraftPower.equals("1")) {
            mTitleList.add(getResources().getString(R.string.defect2_draft));
        }


        checklistOngoingFragment = new Defect2ListsFragment();
        Bundle args1 = new Bundle();
        args1.putString("projectId", projectId);
        args1.putString("projectName", projectName);
        args1.putString("defect_type", mDefectType);
        args1.putString("status", "0");
        checklistOngoingFragment.setArguments(args1);

        checklistSolvedFragment = new Defect2ListsFragment();
        Bundle args2 = new Bundle();
        args2.putString("projectId", projectId);
        args2.putString("projectName", projectName);
        args2.putString("defect_type", mDefectType);
        args2.putString("status", "6");
        checklistSolvedFragment.setArguments(args2);

        checklistOverdueFragment = new Defect2ListsFragment();
        Bundle args5 = new Bundle();
        args5.putString("projectId", projectId);
        args5.putString("projectName", projectName);
        args5.putString("defect_type", mDefectType);
        args5.putString("status", "10");
        checklistOverdueFragment.setArguments(args5);

        checklistOnholdFragment = new Defect2ListsFragment();
        Bundle args4 = new Bundle();
        args4.putString("projectId", projectId);
        args4.putString("projectName", projectName);
        args4.putString("defect_type", mDefectType);
        args4.putString("status", "9");
        checklistOnholdFragment.setArguments(args4);

        checklistDraftFragment = new Defect2ListsFragment();
        Bundle args3 = new Bundle();
        args3.putString("projectId", projectId);
        args3.putString("projectName", projectName);
        args3.putString("defect_type", mDefectType);
        args3.putString("status", "-1");
        checklistDraftFragment.setArguments(args3);

        recordList.add(checklistOngoingFragment);
        recordList.add(checklistSolvedFragment);
        recordList.add(checklistOverdueFragment);
        recordList.add(checklistOnholdFragment);
        //如果是分包,不显示draft
        if (mDraftPower.equals("1")) {
            recordList.add(checklistDraftFragment);
        }

        InspectionPagerAdapter checklistPagerAdapter =
                new InspectionPagerAdapter(getSupportFragmentManager(), recordList, mTitleList);
        mVpContent.setAdapter(checklistPagerAdapter);
        mSlidingTabLayout.setViewPager(mVpContent);
        mVpContent.setOnTouchListener((view, motionEvent) -> false);
        mVpContent.setOffscreenPageLimit(5);
        mVpContent.setCurrentItem(0);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_left_menu) {
            Defect2ListActivity.this.finish();
        } else if (id == R.id.title_bar_right1) {
            Intent filterIntent =
                    new Intent(Defect2ListActivity.this, Defect2ListFilterActivity.class);
            filterIntent.putExtra("defect_type", mDefectType);
            filterIntent.putExtra("block", mBlockStr);
            filterIntent.putExtra("level", mLevelStr);
            filterIntent.putExtra("zone", mZoneStr);

            filterIntent.putExtra("mDisciplineId", mDisciplineId);
            filterIntent.putExtra("mDisciplineName", mDisciplineName);
            filterIntent.putExtra("mSubConId", mSubConId);
            filterIntent.putExtra("mSubConName", mSubConName);
            filterIntent.putExtra("isInitiatedByMe", isInitiatedByMe);

            startActivityForResult(filterIntent, ConstantValue.InspectionFilterCode);
        } else if (id == R.id.title_bar_right2) {
            Intent inspectionIntent = new Intent(mContext, Defect2DrawingListActivity.class);
            inspectionIntent.putExtra("mApplyPower", mApplyPower);
            inspectionIntent.putExtra("mDraftPower", mDraftPower);
            inspectionIntent.putExtra("defect_type", mDefectType);
            mContext.startActivity(inspectionIntent);
        } else if (id == R.id.title_barleft2) {
            boolean isPermission = PermissionUtils.isGranted(Manifest.permission.CAMERA);
            if (isPermission) {//查询摄像头权限,有的话就执行,没有的话就return;
                intentToCaptureActivity();
            } else {
                getCameraPersimmions();
            }
        } else if (id == R.id.title_barleft3) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("onActivityResult==");
        Logger.e("requestCode==" + requestCode);
        Logger.e("resultCode==" + resultCode);

        if (requestCode == ConstantValue.InspectionFilterCode
                && resultCode == ConstantValue.InspectionFilterCode) {
            if (data != null) {

                mBlockStr = data.getStringExtra("block");
                mLevelStr = data.getStringExtra("level");
                mZoneStr = data.getStringExtra("zone");

                mDisciplineId = data.getStringExtra("mDisciplineId");
                mDisciplineName = data.getStringExtra("mDisciplineName");
                mSubConId = data.getStringExtra("mSubConId");
                mSubConName = data.getStringExtra("mSubConName");
                isInitiatedByMe = data.getStringExtra("isInitiatedByMe");

                freshFragment();
            } else if (requestCode == ConstantValue.InspectionListFresh
                    && resultCode == ConstantValue.InspectionListFresh) {
                freshFragment();
            }
        }

    }

    private void freshFragment() {
        int currentItem = mVpContent.getCurrentItem();
        if (currentItem == 0) {
            if (checklistOngoingFragment != null) {
                checklistOngoingFragment.reFreshData(mBlockStr, mLevelStr, mZoneStr,
                        mDisciplineId, mSubConId, isInitiatedByMe);
            }
        } else if (currentItem == 1) {
            if (checklistSolvedFragment != null) {
                checklistSolvedFragment.reFreshData(mBlockStr, mLevelStr, mZoneStr,
                        mDisciplineId, mSubConId, isInitiatedByMe);
            }
        } else if (currentItem == 2) {
            if (checklistOnholdFragment != null) {
                checklistOnholdFragment.reFreshData(mBlockStr, mLevelStr, mZoneStr,
                        mDisciplineId, mSubConId, isInitiatedByMe);
            }
        } else if (currentItem == 3) {
            if (checklistOverdueFragment != null) {
                checklistOverdueFragment.reFreshData(mBlockStr, mLevelStr, mZoneStr,
                        mDisciplineId, mSubConId, isInitiatedByMe);
            }
        } else if (currentItem == 4) {
            if (checklistDraftFragment != null) {
                checklistDraftFragment.reFreshData(mBlockStr, mLevelStr, mZoneStr,
                        mDisciplineId, mSubConId, isInitiatedByMe);
            }
        }
    }

    //获取摄像头权限
    private void getCameraPersimmions() {
        //                        Logger.e("onActivityCreate", "onActivityCreate");
        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
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
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            String projectId = projectAndPermissionBean.getProject_id();
            //判断是从哪个页面进入的
            Intent intent = new Intent(Defect2ListActivity.this,
                    CustomFullScanActivity.class);
            intent.putExtra("flag", "task_home");
            intent.putExtra("projectId", projectId);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}