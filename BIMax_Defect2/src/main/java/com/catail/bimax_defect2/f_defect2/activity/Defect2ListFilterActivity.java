package com.catail.bimax_defect2.f_defect2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2FilterBlockAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2FilterDisciplineAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2FilterLevelAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2FilterSubAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2FilterZoneAdapter;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectBlockListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectLevelListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectZoneListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDisciplineListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QuerySubConListsResultBean;
import com.catail.bimax_defect2.f_defect2.model.Defect2ApplyListModel;
import com.catail.bimax_defect2.f_defect2.model.Defect2DrawingListsModel;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.interfaces.BESCallBack;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Utils;

import java.util.ArrayList;

public class Defect2ListFilterActivity extends BaseActivity implements View.OnClickListener, BESCallBack {

    private TextView tv_block, tv_level, tv_zone, mTbFlag;
    private TextView tv_discipline_type, tv_sub_con_name;
    private String mDisciplineId = "", mDisciplineName = "", mSubConId = "", mSubConName = "";
    private String projectId = "", isInitiatedByMe = "0";
    private ArrayList<QueryDefectBlockListsResultBean.ResultBean.BlockListBean> mBlockLists;
    private ArrayList<QueryDefectLevelListsResultBean.ResultBean.LevelBean> mLevelLists;
    private ArrayList<QueryDefectZoneListsResultBean.ResultBean.UnitListBean> mZoneLists;

    private Defect2DrawingListsModel defect2DrawingListsModel;
    private String mBlockStr = "", mLevelStr = "", mZoneStr = "";
    private AlertDialog typeDialog;//弹出的dialog

    private ArrayList<QueryDisciplineListsResultBean.ResultBean> mDisciplineLists;
    private ArrayList<QuerySubConListsResultBean.ResultBean> mSubConLists;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_defect2_list_filter;
    }

    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(R.string.defect2_title);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        tv_block = findViewById(R.id.tv_block);
        tv_level = findViewById(R.id.tv_level);
        tv_zone = findViewById(R.id.tv_zone);
        tv_discipline_type = findViewById(R.id.tv_discipline_type);
        tv_sub_con_name = findViewById(R.id.tv_sub_con_name);
        TextView tv_reset = findViewById(R.id.tv_reset);
        TextView tv_confirm = findViewById(R.id.tv_confirm);

        tv_block.setOnClickListener(this);
        tv_level.setOnClickListener(this);
        tv_zone.setOnClickListener(this);
        tv_discipline_type.setOnClickListener(this);
        tv_sub_con_name.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        mTbFlag = findViewById(R.id.tb_flag);
        mTbFlag.setOnClickListener(this);

        mBlockLists = new ArrayList<>();
        mLevelLists = new ArrayList<>();
        mZoneLists = new ArrayList<>();
        mDisciplineLists = new ArrayList<>();
        mSubConLists = new ArrayList<>();

    }

    @Override
    public void initData() {
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            projectId = projectAndPermissionBean.getProject_id();
            Logger.e("projectId", projectId + "");

            //获取值, 主要处理block level zone
            Intent data = getIntent();
            String mDefectType = data.getStringExtra("defect_type");
            mBlockStr = data.getStringExtra("block");
            mLevelStr = data.getStringExtra("level");
            mZoneStr = data.getStringExtra("zone");

            if (!TextUtils.isEmpty(mBlockStr)) {
                tv_block.setText(mBlockStr);
            }
            if (!TextUtils.isEmpty(mLevelStr)) {
                tv_level.setText(mLevelStr);
            }
            if (!TextUtils.isEmpty(mZoneStr)) {
                tv_zone.setText(mZoneStr);
            }

            //获取Discipline ,获取分包公司, 获取只查询自己的.
            mDisciplineId = data.getStringExtra("mDisciplineId");
            mDisciplineName = data.getStringExtra("mDisciplineName");
            mSubConId = data.getStringExtra("mSubConId");
            mSubConName = data.getStringExtra("mSubConName");
            isInitiatedByMe = data.getStringExtra("isInitiatedByMe");

            if (!TextUtils.isEmpty(mDisciplineId)) {
                tv_discipline_type.setText(mDisciplineName);
            }

            if (!TextUtils.isEmpty(mSubConId)) {
                tv_sub_con_name.setText(mSubConName);
            }


            mTbFlag.setSelected(!isInitiatedByMe.equals("0"));

            //查询block
            defect2DrawingListsModel = new Defect2DrawingListsModel(Defect2ListFilterActivity.this);
            //查询 block 显示项目图片.
            defect2DrawingListsModel.QueryDefectBlockLists(projectId, "0", this);
            //查询level
            if (!TextUtils.isEmpty(mBlockStr)) {
                defect2DrawingListsModel.QueryDefectLevelLists(projectId,
                        mBlockStr, "0", Defect2ListFilterActivity.this);
            }
            //查询zone
            if (!TextUtils.isEmpty(mLevelStr)) {
                defect2DrawingListsModel.QueryDefectZoneLists(projectId,
                        mBlockStr, mLevelStr, "0", Defect2ListFilterActivity.this);
            }
            //查询discipline
            Defect2ApplyListModel defect2ApplyListModel = new Defect2ApplyListModel(Defect2ListFilterActivity.this);
            defect2ApplyListModel.QueryDisciplineLists(projectId, mDefectType, this);
            //查询分包公司
            defect2ApplyListModel.QuerySubConLists(projectId, this);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_left_menu) {
            Defect2ListFilterActivity.this.finish();
        } else if (id == R.id.tv_block) {
            showDialog("0");
        } else if (id == R.id.tv_level) {
            showDialog("1");
        } else if (id == R.id.tv_zone) {
            showDialog("2");
        } else if (id == R.id.tv_discipline_type) {
            showDialog("4");
        } else if (id == R.id.tv_sub_con_name) {
            showDialog("5");
        } else if (id == R.id.tb_flag) {// 如果已经选中状态则取消选中 否则使选中状态
            if (mTbFlag.isSelected()) {
                mTbFlag.setSelected(false);
                isInitiatedByMe = "0";
            } else {
                mTbFlag.setSelected(true);
                isInitiatedByMe = "1";
            }
        } else if (id == R.id.tv_reset) {
            mBlockStr = "";
            mLevelStr = "";
            mZoneStr = "";
            mLevelLists.clear();
            mZoneLists.clear();
            tv_block.setText(getResources().getString(R.string.materials_block));
            tv_level.setText(getResources().getString(R.string.materials_level));
            tv_zone.setText(getResources().getString(R.string.materials_zone));

            mDisciplineId = "";
            mDisciplineName = "";
            tv_discipline_type.setText(getResources().getString(R.string.defect2_please_select_discipline_type));
            mSubConId = "";
            mSubConName = "";
            tv_discipline_type.setText(getResources().getString(R.string.defect2_please_select_sub));

            isInitiatedByMe = "0";
            mTbFlag.setSelected(false);
        } else if (id == R.id.tv_confirm) {
            Intent intent = new Intent();
            intent.putExtra("block", mBlockStr);
            intent.putExtra("level", mLevelStr);
            intent.putExtra("zone", mZoneStr);

            intent.putExtra("mDisciplineId", mDisciplineId);
            intent.putExtra("mDisciplineName", mDisciplineName);

            intent.putExtra("mSubConId", mSubConId);
            intent.putExtra("mSubConName", mSubConName);
            intent.putExtra("isInitiatedByMe", isInitiatedByMe);

            setResult(ConstantValue.InspectionFilterCode, intent);
            Defect2ListFilterActivity.this.finish();
        }
    }


    @SuppressLint("WrongConstant")
    private void showDialog(String typeFlag) {
        typeDialog = new AlertDialog.Builder(Defect2ListFilterActivity.this).create();
        View view = getLayoutInflater().inflate(R.layout.list_dialog2, null);
        TextView titleText = view.findViewById(R.id.tv_title);
        titleText.setText(R.string.filter_by);

        RecyclerView rvlistView = view.findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(Defect2ListFilterActivity.this);
        lm.setOrientation(OrientationHelper.VERTICAL);
        rvlistView.setLayoutManager(lm);
        switch (typeFlag) {
            case "0": {
                titleText.setText(getResources().getString(R.string.inspection_block_area));
                Defect2FilterBlockAdapter rvListAdapter =
                        new Defect2FilterBlockAdapter(R.layout.adapter_checklist_create_by_item, mBlockLists);
                rvlistView.setAdapter(rvListAdapter);
                rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
                    if (view1.getId() == R.id.rl_content) {
                        mBlockStr = mBlockLists.get(i).getBlock();
                        tv_block.setText(mBlockLists.get(i).getBlock());

                        defect2DrawingListsModel.QueryDefectLevelLists(projectId,
                                mBlockStr, "0", Defect2ListFilterActivity.this);
                        typeDialog.dismiss();
                    }
                });
                break;
            }
            case "1": {
                titleText.setText(getResources().getString(R.string.checklist_dialog_level_title));

                Defect2FilterLevelAdapter rvListAdapter =
                        new Defect2FilterLevelAdapter(R.layout.adapter_checklist_create_by_item, mLevelLists);
                rvlistView.setAdapter(rvListAdapter);
                rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
                    if (view1.getId() == R.id.rl_content) {
                        mLevelStr = mLevelLists.get(i).getSecondary_region();
                        tv_level.setText(mLevelLists.get(i).getSecondary_region());

                        defect2DrawingListsModel.QueryDefectZoneLists(projectId,
                                mBlockStr, mLevelStr, "0", Defect2ListFilterActivity.this);
                        typeDialog.dismiss();
                    }
                });
                break;
            }
            case "2": {
                titleText.setText(getResources().getString(R.string.materials_zone));
                Defect2FilterZoneAdapter rvListAdapter =
                        new Defect2FilterZoneAdapter(R.layout.adapter_checklist_create_by_item, mZoneLists);
                rvlistView.setAdapter(rvListAdapter);
                rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
                    if (view1.getId() == R.id.rl_content) {
                        mZoneStr = mZoneLists.get(i).getUnit();
                        tv_zone.setText(mZoneLists.get(i).getUnit());
                        typeDialog.dismiss();
                    }
                });
                break;
            }
            case "4": {
                titleText.setText(getResources().getString(R.string.defect2_please_select_discipline_type));
                Defect2FilterDisciplineAdapter rvListAdapter =
                        new Defect2FilterDisciplineAdapter(R.layout.adapter_checklist_create_by_item, mDisciplineLists);
                rvlistView.setAdapter(rvListAdapter);
                rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
                    if (view1.getId() == R.id.rl_content) {
                        mDisciplineId = mDisciplineLists.get(i).getDiscipline_id() + "";
                        mDisciplineName = mDisciplineLists.get(i).getDiscipline();
                        tv_discipline_type.setText(mDisciplineLists.get(i).getDiscipline());
                        typeDialog.dismiss();
                    }
                });
                break;
            }
            case "5": {
                titleText.setText(getResources().getString(R.string.inspection_subcon_title));
                Defect2FilterSubAdapter rvListAdapter =
                        new Defect2FilterSubAdapter(R.layout.adapter_checklist_create_by_item, mSubConLists);
                rvlistView.setAdapter(rvListAdapter);
                rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
                    if (view1.getId() == R.id.rl_content) {
                        mSubConId = mSubConLists.get(i).getContractor_id() + "";
                        mSubConName = mSubConLists.get(i).getContractor_name();
                        tv_sub_con_name.setText(mSubConLists.get(i).getContractor_name());
                        typeDialog.dismiss();
                    }
                });
                break;
            }
        }

        typeDialog.setView(view);

        typeDialog.show();
        Utils.setAlertDialogConner(typeDialog);
        Utils.setAlertDialogSize(Defect2ListFilterActivity.this, typeDialog, 0);
    }


    @Override
    public void onSuccess(String name, Object objectBean) {
        if (name.equals(NetApi.QueryDefectBlockLists)) {
            QueryDefectBlockListsResultBean resultBean =
                    (QueryDefectBlockListsResultBean) objectBean;
            mBlockLists.addAll(resultBean.getResult().getBlock_list());

        } else if (name.equals(NetApi.QueryDefectLevelLists)) {
            //添加level到选择列表, 显示level点
            QueryDefectLevelListsResultBean resultBean =
                    (QueryDefectLevelListsResultBean) objectBean;
            resultBean.getResult().getLevel();
            mLevelLists.addAll(resultBean.getResult().getLevel());
        } else if (name.equals(NetApi.QueryDefectZoneLists)) {
            //添加zone到选择列表, 显示zone坐标点
            QueryDefectZoneListsResultBean resultBean =
                    (QueryDefectZoneListsResultBean) objectBean;
            resultBean.getResult().getLevel();
            mZoneLists.addAll(resultBean.getResult().getUnit_list());
        } else if (name.equals(NetApi.QueryDisciplineLists)) {
            QueryDisciplineListsResultBean resultBean =
                    (QueryDisciplineListsResultBean) objectBean;
            mDisciplineLists.addAll(resultBean.getResult());
        } else if (name.equals(NetApi.QuerySubConLists)) {
            QuerySubConListsResultBean resultBean =
                    (QuerySubConListsResultBean) objectBean;
            mSubConLists.addAll(resultBean.getResult());
        }
    }

    @Override
    public void onFaild(String name, String faildStr) {

    }
}