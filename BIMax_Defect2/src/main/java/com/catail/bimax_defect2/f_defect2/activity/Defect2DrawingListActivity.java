package com.catail.bimax_defect2.f_defect2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2BlockAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2LevelAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2PinOptionAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2ZoneAdapter;
import com.catail.bimax_defect2.f_defect2.bean.PinOptionBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2PinListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectBlockListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectLevelListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectZoneListsResultBean;
import com.catail.bimax_defect2.f_defect2.model.Defect2DrawingListsModel;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.activity.CustomFullScanActivity;
import com.catail.lib_commons.adapter.PopMenuListViewAdapter;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.DMDLPPinPoint;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.bean.QueryDMDLPDefectListDetailsResultBean;
import com.catail.lib_commons.interfaces.BESCallBack;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.DialogHelper;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Utils;
import com.catail.lib_commons.view.RectPinView;
import com.example.zhouwei.library.CustomPopWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.voghdev.pdfviewpager.library.subscaleview.ImageSource;
import okhttp3.Call;

public class Defect2DrawingListActivity extends BaseActivity implements View.OnClickListener, BESCallBack {
    private TextView tv_block, tv_leavel, tv_zone;
    private String msg = "", projectId = "", projectName = "";
    private RectPinView pin_view;
    private float pinX = 0.0f;
    private float pinY = 0.0f;
    private final List<DMDLPPinPoint> PointLists = new ArrayList<>();
    private final List<PointF> PinLists = new ArrayList<>();
    private List<QueryDMDLPDefectListDetailsResultBean.ResultBean> mDefectDataList;
    private List<QueryDMDLPDefectListDetailsResultBean.ResultBean> mDefectSelectedDataList;//点击一个区域 显示的defect

    private ImageView iv_empty;

    private ImageView title_bar_right_menu;
    private CustomPopWindow mCustomPopWindow1;//右侧的menu 弹窗
    private List<String> mMenuLists;//menu列表
    private ArrayList<PinOptionBean> mPinOptionDataList;
    private String mCopyCheckId = "";//代表是否复制,如果有值代表复制,
    private boolean pinViewOnLongPress = true;//判断一下是construct 三级还是四级的区域,三级区域不能点击.

    private Defect2DrawingListsModel defect2DrawingListsModel;
    private File mDrawingPicFile;//项目,block,level,unit的图纸pic本地文件
    private String mBlockStr = "", mLevelStr = "", mZoneStr = "";


    private List<QueryDefectBlockListsResultBean.ResultBean.BlockListBean> mBlockDrawingLists;//block 数据
    private Defect2BlockAdapter mBlockAdapter;
    private List<QueryDefectLevelListsResultBean.ResultBean.LevelBean> mLevelDrawingLists;//level 数据
    private Defect2LevelAdapter mLevelAdapter;
    private List<QueryDefectZoneListsResultBean.ResultBean.UnitListBean> mZoneDrawingLists;//zone 数据

    private List<QueryDefect2PinListsResultBean.ResultBean> mAllPinsLists;//zone图纸下全部的点.

    private String currentDrawing = "-1";//区分打开的具体图纸. 0:项目图纸, 1:block图纸, 2:level图纸, 3:Zone图纸
    private String currentDrawingId = "", currentDrawingPicPath = "", currentDrawingPosition = "";//当前图纸信息.
    private String mDefectType = "";

    private CustomPopWindow popWindow;
    private String mApplyPower = "0", mDraftPower = "0";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_defect2_drawing_list;
    }

    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.defect2_construction);
        tv_title.setVisibility(View.INVISIBLE);
        TextView tv_bottom_title = findViewById(R.id.tv_bottom_title);
        tv_bottom_title.setText(R.string.defect2_construction);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        title_bar_right_menu = findViewById(R.id.title_bar_right1);
        title_bar_right_menu.setVisibility(View.INVISIBLE);
        title_bar_right_menu.setImageResource(R.mipmap.more_blue);
        title_bar_right_menu.setOnClickListener(this);

        ImageView title_barleft2 = findViewById(R.id.title_barleft2);
        title_barleft2.setVisibility(View.VISIBLE);
        title_barleft2.setOnClickListener(this);

        tv_block = findViewById(R.id.tv_block);
        tv_leavel = findViewById(R.id.tv_leavel);
        tv_zone = findViewById(R.id.tv_zone);
        tv_block.setOnClickListener(this);
        tv_leavel.setOnClickListener(this);
        tv_zone.setOnClickListener(this);

        //block
        mBlockDrawingLists = new ArrayList<>();
        mBlockAdapter = new Defect2BlockAdapter(mBlockDrawingLists);
        //leavel
        mLevelDrawingLists = new ArrayList<>();
        mLevelAdapter = new Defect2LevelAdapter(mLevelDrawingLists);
        //zone
        mZoneDrawingLists = new ArrayList<>();
        mAllPinsLists = new ArrayList<>();


        iv_empty = findViewById(R.id.iv_empty);
        pin_view = findViewById(R.id.pin_view);
        mDefectDataList = new ArrayList<>();
        mDefectSelectedDataList = new ArrayList<>();

        setLocationArrowColor(-1);
    }


    @Override
    public void initData() {
        try {
            //获取系统版本,然后显示中英文进度条文字
            final int sysVersion = Utils.GetSystemCurrentVersion();
            if (sysVersion == 0) {
                msg = getString(R.string.processing);
            } else {
                msg = getString(R.string.processing);
            }

            mApplyPower = getIntent().getStringExtra("mApplyPower");
            mDraftPower = getIntent().getStringExtra("mDraftPower");


            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            projectId = projectAndPermissionBean.getProject_id();
            projectName = projectAndPermissionBean.getProject_name();

            mDefectType = getIntent().getStringExtra("defect_type");

            defect2DrawingListsModel = new Defect2DrawingListsModel(Defect2DrawingListActivity.this);
            //查询 block 显示项目图片.
            defect2DrawingListsModel.QueryDefectBlockLists(projectId, "1", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //block
        mMenuLists = new ArrayList<>();
        mMenuLists.add(getResources().getString(R.string.home_inspection_form));
        mMenuLists.add(getResources().getString(R.string.home_inspection_drawing));

        mPinOptionDataList = new ArrayList<>();
        mPinOptionDataList.addAll(initPinOption());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_menu) {
            Defect2DrawingListActivity.this.finish();
        } else if (v.getId() == R.id.title_barleft2) {
            boolean isPermission = PermissionUtils.isGranted(Manifest.permission.CAMERA);
            if (isPermission) {//查询摄像头权限,有的话就执行,没有的话就return;
                intentToCaptureActivity();
            } else {
                getCameraPersimmions();
            }
        } else if (v.getId() == R.id.tv_block) {
            showLocationDialog(0);
        } else if (v.getId() == R.id.tv_leavel) {
            if (tv_block.getText().toString().isEmpty()) {
                showToast(getResources()
                        .getString(R.string.checklist_level_no_params));
                return;
            }
            showLocationDialog(1);
        } else if (v.getId() == R.id.tv_zone) {
            if (tv_block.getText().toString().isEmpty()) {
                showToast(getResources()
                        .getString(R.string.checklist_level_no_params));
                return;
            }
            if (tv_leavel.getText().toString().isEmpty()) {
                showToast(getResources()
                        .getString(R.string.checklist_level_no_params1));
                return;
            }
            showZoneDialog();
        } else if (v.getId() == R.id.title_bar_right1) {
            showMenuPopTopWithDarkBg();
        } else if (v.getId() == R.id.ll_pop_content) {
            showAddPopwindow();
        }
    }

    private ArrayList<PinOptionBean> initPinOption() {
        ArrayList<PinOptionBean> mDataList = new ArrayList<>();
        PinOptionBean bean = new PinOptionBean();
        bean.setOption_name(getResources().getString(R.string.option_copy));
        PinOptionBean bean1 = new PinOptionBean();
        bean1.setOption_name(getResources().getString(R.string.option_open));
        mDataList.add(bean);
        mDataList.add(bean1);
        return mDataList;
    }

    /**
     * 类型选择框
     */
    @SuppressLint("WrongConstant")
    private void showOptionDialog() {
        AlertDialog typeDialog = new AlertDialog.Builder(Defect2DrawingListActivity.this).create();
        View view = Defect2DrawingListActivity.this.getLayoutInflater().inflate(R.layout.list_dialog2, null);
        TextView titleText = view.findViewById(R.id.tv_title);
        titleText.setText(mDefectSelectedDataList.get(0).getPin_pos());
        RecyclerView rvlistView = view.findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(Defect2DrawingListActivity.this);
        lm.setOrientation(OrientationHelper.VERTICAL);
        rvlistView.setLayoutManager(lm);
        Defect2PinOptionAdapter rvListAdapter =
                new Defect2PinOptionAdapter(R.layout.adapter_checklist_create_by_item,
                        mPinOptionDataList);
        rvlistView.setAdapter(rvListAdapter);
        rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view12, position) -> {
            if (view12.getId() == R.id.rl_content) {
                //0复制,1打开
                if (position == 0) {
                    mCopyCheckId = mDefectSelectedDataList.get(0).getCheck_id();
                    Logger.e("复制记录的id--mCopyCheckId==" + mCopyCheckId);
                } else {
                    mCopyCheckId = "";
                    //打开详情界面
                    Intent intent = new Intent(Defect2DrawingListActivity.this, Defect2DetailsActivity.class);
                    intent.putExtra("check_id", mDefectSelectedDataList.get(0).getCheck_id());
                    intent.putExtra("defect_type", mDefectType);
                    startActivityForResult(intent, ConstantValue.Defect2SubmitAndSaveCode);
                }
                typeDialog.dismiss();
            }
        });
        typeDialog.setView(view);
        typeDialog.show();
        Utils.setAlertDialogConner(typeDialog);
        Utils.setAlertDialogSize(Defect2DrawingListActivity.this, typeDialog, 0);
    }

    /**
     * 弹窗加载
     */
    private void showLocationDialog(final int locationFlag) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = getLayoutInflater().inflate(R.layout.list_dialog, null);
        TextView titleText = view.findViewById(R.id.title);
        ListView dialogRecyclerView = view.findViewById(R.id.dialog_list);
        Logger.e("spinnerFlag==", locationFlag + "");
        if (locationFlag == 0) {
            // block
            titleText.setText(getResources().getString(R.string.inspection_block_area));
            dialogRecyclerView.setAdapter(mBlockAdapter);
            dialogRecyclerView.setVisibility(View.VISIBLE);
        } else if (locationFlag == 1) {
            //level
            titleText.setText(getResources().getString(R.string.checklist_dialog_level_title));
            dialogRecyclerView.setAdapter(mLevelAdapter);
            dialogRecyclerView.setVisibility(View.VISIBLE);
        }
        dialog.setView(view);
        dialog.show();
        Utils.setAlertDialogConner(dialog);
        Utils.setAlertDialogSize(Defect2DrawingListActivity.this, dialog, 0);
        dialogRecyclerView.setOnItemClickListener((parent, view1, position, id) -> {
            if (locationFlag == 0) {
                String blockStr = mBlockDrawingLists.get(position).getBlock();
                String blockDrawingId = mBlockDrawingLists.get(position).getDrawing_id() + "";
                String blockDrawingName = mBlockDrawingLists.get(position).getDrawing_name();
                String blockDrawingVersion = mBlockDrawingLists.get(position).getBlock_version();
                String blockDrawingPath = mBlockDrawingLists.get(position).getDrawing_path();
                Logger.e("blockStr=" + blockStr);
                Logger.e("blockDrawingId=" + blockDrawingId);
                Logger.e("blockDrawingName=" + blockDrawingName);
                Logger.e("blockDrawingVersion=" + blockDrawingVersion);
                Logger.e("blockDrawingPath=" + blockDrawingPath);
                showDrawingBlockSelect(blockStr, blockDrawingId, blockDrawingName, blockDrawingVersion, blockDrawingPath);

//                mBlockStr = mBlockDrawingLists.get(position).getBlock();
//                tv_block.setText(mBlockDrawingLists.get(position).getBlock());
//                tv_leavel.setText("");
//                tv_zone.setText("");
//                setLocationArrowColor(0);
//                // 需要把level 和zone 选择的图片都置为空
//                mLevelStr = "";
//                mZoneStr = "";
//                mZoneDrawingLists.clear();
//
//                //添加level列表的数据进入
//                if (mLevelDrawingLists.size() > 0) {
//                    mLevelDrawingLists.clear();
//                }
//                //打开对应的block图纸,  查询level 的图纸.
//                //判断一下项目的图纸是否为空
//                if (!TextUtils.isEmpty(mBlockDrawingLists.get(position).getDrawing_list().getDrawing_name())) {
//                    //判断 文件存不存在, 版本号+drawing_id+文件名称.
//                    String blockPicName = mBlockDrawingLists.get(position).getBlock_version()
//                            + "_" + mBlockDrawingLists.get(position).getDrawing_list().getDrawing_id()
//                            + "_" + mBlockDrawingLists.get(position).getDrawing_list().getDrawing_name();
//                    String blockPicUrl = NetApi.IMG_SHOW_URL + mBlockDrawingLists.get(position).getDrawing_list().getDrawing_path();
//
//                    mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
//                            + "/" + blockPicName);
//                    if (mDrawingPicFile.exists()) {
//                        //存在 显示坐标点
//                        Logger.e("BlockPicFile--文件存在");
//                        //查询level 数组图纸.然后显示点.
//                        //showProjectPic(mDrawingPicFile, resultBean.getResult().getBlock_list());
//                        currentDrawing = "1";
//                        openDrawingPic();
//                        defect2DrawingListsModel.QueryDefectLevelLists(projectId,
//                                mBlockStr, Defect2DrawingListActivity.this);
//                    } else {
//                        //不存在下载, 询level 数组图纸.然后显示点
//                        Logger.e("BlockPicFile--文件不存在");
//                        downloadDrawingPic(blockPicName, blockPicUrl, "1");
//                    }
//                } else {
//                    iv_empty.setImageResource(R.mipmap.level_empty);
//                    iv_empty.setVisibility(View.VISIBLE);
//                }
            } else if (locationFlag == 1) {
                String levelStr = mLevelDrawingLists.get(position).getSecondary_region();
                String levelDrawingId = mLevelDrawingLists.get(position).getDrawing_id() + "";
                String levelDrawingName = mLevelDrawingLists.get(position).getDrawing_name();
                String levelDrawingVersion = mLevelDrawingLists.get(position).getVersion();
                String levelDrawingPath = mLevelDrawingLists.get(position).getDrawing_path();
                Logger.e("levelStr=" + levelStr);
                Logger.e("drawingId=" + levelDrawingId);
                Logger.e("Drawing_name=" + levelDrawingName);
                Logger.e("Drawing_version=" + levelDrawingVersion);
                Logger.e("Drawing_path=" + levelDrawingPath);
                showDrawingLevelSelect(levelStr, levelDrawingId, levelDrawingName, levelDrawingVersion, levelDrawingPath);

//                mLevelStr = mLevelDrawingLists.get(position).getSecondary_region();
//                tv_leavel.setText(mLevelDrawingLists.get(position).getSecondary_region());
//                tv_zone.setText("");
//                setLocationArrowColor(1);
//                // 需要zone 选择的图片都置为空
//                mZoneStr = "";
//               
//                //添加zone的列表数据进入
//                if (mZoneDrawingLists.size() > 0) {
//                    mZoneDrawingLists.clear();
//                }
//
//                //打开对应的level图纸,  查询zone 的图纸.
//                //判断一下项目的图纸是否为空
//                if (!TextUtils.isEmpty(mLevelDrawingLists.get(position).getDrawing_list().getDrawing_name())) {
//                    //判断 文件存不存在, 版本号+drawing_id+文件名称.
//                    String levelPicName = mLevelDrawingLists.get(position).getVersion()
//                            + "_" + mLevelDrawingLists.get(position).getDrawing_list().getDrawing_id()
//                            + "_" + mLevelDrawingLists.get(position).getDrawing_list().getDrawing_name();
//                    String levelPicUrl = NetApi.IMG_SHOW_URL + mLevelDrawingLists.get(position).getDrawing_list().getDrawing_path();
//
//                    mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
//                            + "/" + levelPicName);
//                    if (mDrawingPicFile.exists()) {
//                        //存在 显示坐标点
//                        Logger.e("LevelPicFile--文件存在");
//                        //查询zone 数组图纸.然后显示点.
//                        currentDrawing = "2";
//                        openDrawingPic();
//                        defect2DrawingListsModel.QueryDefectZoneLists(projectId,
//                                mBlockStr, mLevelStr, Defect2DrawingListActivity.this);
//                    } else {
//                        //不存在下载, 询zone 数组图纸.然后显示点
//                        Logger.e("LevelPicFile--文件不存在");
//                        downloadDrawingPic(levelPicName, levelPicUrl, "2");
//                    }
//                } else {
//                    iv_empty.setImageResource(R.mipmap.level_empty);
//                    iv_empty.setVisibility(View.VISIBLE);
//                }
            }
            dialog.dismiss();
        });
    }

    /**
     * type 0=block,1=level,2=zone
     */
    private void setLocationArrowColor(int type) {
        if (type == 0) {
            tv_block.setSelected(true);
            tv_leavel.setSelected(false);
            tv_zone.setSelected(false);
        } else if (type == 1) {
            tv_leavel.setSelected(true);
            tv_zone.setSelected(false);
        } else if (type == 2) {
            tv_zone.setSelected(true);
        } else {
            tv_block.setSelected(false);
            tv_leavel.setSelected(false);
            tv_zone.setSelected(false);
        }
    }

    @SuppressLint("WrongConstant")
    private void showZoneDialog() {
        final AlertDialog partDialog = new AlertDialog.Builder(this).create();
        View photoSelectionView = getLayoutInflater().inflate(R.layout.list_dialog1, null);
        RecyclerView PhotoSelectionListView = photoSelectionView.findViewById(R.id.dialog_list);
        TextView tv_title = photoSelectionView.findViewById(R.id.title);
        tv_title.setText(getResources().getString(R.string.materials_zone));

        final LinearLayoutManager lm = new LinearLayoutManager(Defect2DrawingListActivity.this);
        lm.setOrientation(OrientationHelper.VERTICAL);
        PhotoSelectionListView.setLayoutManager(lm);

        Defect2ZoneAdapter rvListAdapter =
                new Defect2ZoneAdapter(R.layout.text_item, mZoneDrawingLists);
        PhotoSelectionListView.setAdapter(rvListAdapter);
        rvListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.rel_bg) {
                String zoneStr = mZoneDrawingLists.get(position).getUnit();
                String zoneDrawingId = mZoneDrawingLists.get(position).getDrawing_id() + "";
                String zoneDrawingName = mZoneDrawingLists.get(position).getDrawing_name();
                String zoneDrawingVersion = mZoneDrawingLists.get(position).getVersion();
                String zoneDrawingPath = mZoneDrawingLists.get(position).getDrawing_path();
                Logger.e("zoneStr=" + zoneStr);
                Logger.e("drawingId=" + zoneDrawingId);
                Logger.e("Drawing_name=" + zoneDrawingName);
                Logger.e("Drawing_version=" + zoneDrawingVersion);
                Logger.e("Drawing_path=" + zoneDrawingPath);
                showDrawingZoneSelect(zoneStr, zoneDrawingId, zoneDrawingName, zoneDrawingVersion, zoneDrawingPath);

                //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
                //下面是跳转到新的页面的逻辑.
                //打开zone页面
//                if (mDefectSelectedDataList.size() > 0) {
//                    mDefectSelectedDataList.clear();
//                }
//
//                mDefectSelectedDataList.add(mDefectDataList.get(position));
//
//                Intent intent = new Intent(Defect2DrawingListActivity.this,
//                        Defect2UnitActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("projectId", projectId);
//                bundle.putString("block", tv_block.getText().toString());
//                bundle.putString("level", tv_leavel.getText().toString());
//                bundle.putString("zone", mDefectSelectedDataList.get(0).getZone());
//                //drawing_id,drawing_name,drawing_path
//                bundle.putSerializable("zone_info", (Serializable) mDefectSelectedDataList);
//                intent.putExtras(bundle);
//                startActivity(intent);

                partDialog.dismiss();
            }
        });
        partDialog.setView(photoSelectionView);
        partDialog.show();
        Utils.setAlertDialogConner(partDialog);
        Utils.setAlertDialogSize(Defect2DrawingListActivity.this, partDialog, 0);
    }

    private void showDrawingBlockSelect(String blockStr, String blockDrawingId, String blockDrawingName,
                                        String blockDrawingVersion, String blockDrawingPath) {
        mBlockStr = blockStr;
        tv_block.setText(mBlockStr);
        tv_leavel.setText("");
        tv_zone.setText("");
        setLocationArrowColor(0);
        // 需要把level 和zone 选择的图片都置为空
        mLevelStr = "";
        mZoneStr = "";
        mZoneDrawingLists.clear();

        //添加level列表的数据进入
        if (mLevelDrawingLists.size() > 0) {
            mLevelDrawingLists.clear();
        }
        //打开对应的block图纸,  查询level 的图纸.
        //判断一下项目的图纸是否为空
        if (!TextUtils.isEmpty(blockDrawingName)) {
            //判断 文件存不存在, 版本号+drawing_id+文件名称.
            String blockPicName = blockDrawingVersion + "_" + blockDrawingId + "_" + blockDrawingName;
            String blockPicUrl = NetApi.IMG_SHOW_URL + blockDrawingPath;

            mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
                    + "/" + blockPicName);
            if (mDrawingPicFile.exists()) {
                //存在 显示坐标点
                Logger.e("BlockPicFile--文件存在");
                //查询level 数组图纸.然后显示点.
                //showProjectPic(mDrawingPicFile, resultBean.getResult().getBlock_list());
                currentDrawing = "1";

                openDrawingPic();
                defect2DrawingListsModel.QueryDefectLevelLists(projectId,
                        mBlockStr, "1", Defect2DrawingListActivity.this);
            } else {
                //不存在下载, 询level 数组图纸.然后显示点
                Logger.e("BlockPicFile--文件不存在");
                downloadDrawingPic(blockPicName, blockPicUrl, "1", blockDrawingId, blockDrawingPath);
            }
        } else {
            iv_empty.setImageResource(R.mipmap.level_empty);
            iv_empty.setVisibility(View.VISIBLE);
        }
    }

    private void showDrawingLevelSelect(String levelStr, String levelDrawingId, String levelDrawingName,
                                        String levelDrawingVersion, String levelDrawingPath) {
        mLevelStr = levelStr;
        tv_leavel.setText(mLevelStr);
        tv_zone.setText("");
        setLocationArrowColor(1);
        // 需要zone 选择的图片都置为空
        mZoneStr = "";

        //添加zone的列表数据进入
        if (mZoneDrawingLists.size() > 0) {
            mZoneDrawingLists.clear();
        }

        //打开对应的level图纸,  查询zone 的图纸.
        //判断一下项目的图纸是否为空
        if (!TextUtils.isEmpty(levelDrawingName)) {
            //判断 文件存不存在, 版本号+drawing_id+文件名称.
            String levelPicName = levelDrawingVersion + "_" + levelDrawingId + "_" + levelDrawingName;
            String levelPicUrl = NetApi.IMG_SHOW_URL + levelDrawingPath;

            mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
                    + "/" + levelPicName);
            if (mDrawingPicFile.exists()) {
                //存在 显示坐标点
                Logger.e("LevelPicFile--文件存在");
                //查询zone 数组图纸.然后显示点.
                currentDrawing = "2";
                openDrawingPic();
                defect2DrawingListsModel.QueryDefectZoneLists(projectId,
                        mBlockStr, mLevelStr, "1", Defect2DrawingListActivity.this);
            } else {
                //不存在下载, 询zone 数组图纸.然后显示点
                Logger.e("LevelPicFile--文件不存在");
                downloadDrawingPic(levelPicName, levelPicUrl, "2", levelDrawingId, levelDrawingPath);
            }
        } else {
            iv_empty.setImageResource(R.mipmap.level_empty);
            iv_empty.setVisibility(View.VISIBLE);
        }
    }

    private void showDrawingZoneSelect(String zoneStr, String zoneDrawingId, String zoneDrawingName,
                                       String zoneDrawingVersion, String zoneDrawingPath) {
        //设置选项信息
        mZoneStr = zoneStr;
        tv_zone.setText(mZoneStr);
        setLocationArrowColor(3);

        //打开对应的level图纸,  查询zone 的图纸.
        //判断一下项目的图纸是否为空
        if (!TextUtils.isEmpty(zoneDrawingName)) {
            //判断 文件存不存在, 版本号+drawing_id+文件名称.
            String levelPicName = zoneDrawingVersion + "_" + zoneDrawingId + "_" + zoneDrawingName;
            String levelPicUrl = NetApi.IMG_SHOW_URL + zoneDrawingPath;

            mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
                    + "/" + levelPicName);
            if (mDrawingPicFile.exists()) {
                //存在 显示坐标点
                Logger.e("LevelPicFile--文件存在");
                // 查询zone 数组图纸.然后显示点.
                currentDrawing = "3";
                currentDrawingId = zoneDrawingId;
                currentDrawingPicPath = zoneDrawingPath;
                openDrawingPic();
                // 请求数据,显示具体的坐标点.
                defect2DrawingListsModel.QueryDefect2PinLists(projectId, mBlockStr, mLevelStr, mZoneStr,
                        currentDrawingId, "1", this);

            } else {
                //不存在下载, 询zone 数组图纸.然后显示点
                Logger.e("LevelPicFile--文件不存在");
                downloadDrawingPic(levelPicName, levelPicUrl, "3", zoneDrawingId, zoneDrawingPath);
            }
        } else {
            iv_empty.setImageResource(R.mipmap.level_empty);
            iv_empty.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 显示PopupWindow 同时背景变暗
     */
    private void showMenuPopTopWithDarkBg() {
        @SuppressLint("InflateParams")
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu_content, null);
        //处理popWindow 显示内容
        handleBlockLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow1 = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(() -> Log.e("TAG", "onDismiss"))
                .create()
                .showAsDropDown(title_bar_right_menu, -125, 0);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     */
    @SuppressLint("WrongConstant")
    private void handleBlockLogic(View contentView) {
        RecyclerView mRvList = contentView.findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(Defect2DrawingListActivity.this);
        lm.setOrientation(OrientationHelper.VERTICAL);
        mRvList.setLayoutManager(lm);
        PopMenuListViewAdapter rvListAdapter =
                new PopMenuListViewAdapter(R.layout.pop_menu_item, mMenuLists);
        mRvList.setAdapter(rvListAdapter);
        rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> {
            if (view.getId() == R.id.rl_content) {
                if (mCustomPopWindow1 != null) {
                    mCustomPopWindow1.dissmiss();
                }
                //TODO defect 不需要下载.
//                if (position == 0) {
//                    Intent inspectionFormIntent = new Intent(Defect2DrawingListActivity.this,
//                            InspectionFormDownloadActivity.class);
//                    startActivity(inspectionFormIntent);
//                } else if (position == 1) {
//                    Intent inspectionDrawingIntent = new Intent(Defect2DrawingListActivity.this,
//                            InspectionDrawingDownloadActivity.class);
//                    startActivity(inspectionDrawingIntent);
//                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initGuesture() {
        final GestureDetector gestureDetector = new GestureDetector(Defect2DrawingListActivity.this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (pin_view.isReady()) {
                            Logger.e("----onSingleTapConfirmed----");
//                            showAllPin();
                            onclickPin(e);
//                            showPop(e);
                        } else {
                            Toast.makeText(Defect2DrawingListActivity.this, "Single tap: Image not ready",
                                    Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        if (pin_view.isReady()) {
                            PointF sCoord = pin_view.viewToSourceCoord(e.getX(), e.getY());
                            Logger.e("----onLongPress----");
                            pinX = sCoord.x;
                            pinY = sCoord.y;
                            //保存设置坐标点的位置.

//                            if (pinViewOnLongPress) {
//                                showToast("可以长按");
//                            } else {
//                                showToast("不可以长按");
//                            }
                            if (mApplyPower.equals("1")) {
                                if (currentDrawing.equals("3")) {
                                    showPop(e);
                                }
                            }

                        } else {
                            Toast.makeText(Defect2DrawingListActivity.this, "Long press: Image not ready", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
//                        if (picStatus.equals("0")) {
//                            if (pin_view.isReady()) {
//                                Logger.e("----onDoubleTap----");
//                                PointF sCoord = pin_view.viewToSourceCoord(e.getX(), e.getY());
//                                Toast.makeText(InspectionListActivity.this, "Double tap: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(InspectionListActivity.this, "Double tap: Image not ready", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        return true;
                    }
                });

        pin_view.setOnTouchListener((view, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));
    }

    private void onclickPin(MotionEvent e) {
        Logger.e("----onclickPin----");
        PointF sCoord = pin_view.viewToSourceCoord(e.getX(), e.getY());
        if (sCoord != null) {
            Logger.e("------------------------------");
            Logger.e("sCoord X()====" + sCoord.x);
            Logger.e("sCoord Y()====" + sCoord.y);
            Logger.e("------------------------------");
            // 这里写 点击图标的事件
            if (mDefectSelectedDataList.size() > 0) {
                mDefectSelectedDataList.clear();
            }

            for (int i = 0; i < PointLists.size(); i++) {
                DMDLPPinPoint pointF = PointLists.get(i);
                //区分一下缩小的倍数
                //0.79 是测试数据ditie的倍数,暂时没找到方法如何根据像素来测试. 根据倍数也差不多.
                if (pin_view.getScale() > 0.7) {
                    if (((pointF.getPin_x() - 50) < ((int) sCoord.x)
                            && ((int) sCoord.x) < (pointF.getPin_x() + 50))
                            && ((pointF.getPin_y() - 50) < ((int) sCoord.y)
                            && ((int) sCoord.y) < (pointF.getPin_y() + 50))) {
                        Logger.e("iiiii===" + i);
                        Logger.e("iiiii===" + mDefectDataList.get(i).getPin_x()
                                + "," + mDefectDataList.get(i).getPin_y());
                        mDefectSelectedDataList.add(mDefectDataList.get(i));
                    }
                } else if (pin_view.getScale() > 0.3
                        && pin_view.getScale() < 0.7) {
                    if (((pointF.getPin_x() - 100) < ((int) sCoord.x)
                            && ((int) sCoord.x) < (pointF.getPin_x() + 100))
                            && ((pointF.getPin_y() - 100) < ((int) sCoord.y)
                            && ((int) sCoord.y) < (pointF.getPin_y() + 100))) {
                        Logger.e("iiiii===" + i);
                        Logger.e("iiiii===" + mDefectDataList.get(i).getPin_x()
                                + "," + mDefectDataList.get(i).getPin_y());
                        mDefectSelectedDataList.add(mDefectDataList.get(i));
                    }
                } else if (pin_view.getScale() < 0.3) {
                    if (((pointF.getPin_x() - 200) < ((int) sCoord.x)
                            && ((int) sCoord.x) < (pointF.getPin_x() + 200))
                            && ((pointF.getPin_y() - 200) < ((int) sCoord.y)
                            && ((int) sCoord.y) < (pointF.getPin_y() + 200))) {
                        Logger.e("iiiii===" + i);
                        Logger.e("iiiii===" + mDefectDataList.get(i).getPin_x()
                                + "," + mDefectDataList.get(i).getPin_y());
                        mDefectSelectedDataList.add(mDefectDataList.get(i));
                    }
                }
            }
            Logger.e("mDefectSelectedDataList==" + mDefectSelectedDataList.toString());

            //↓↓↓↓↓↓↓↓↓↓新的逻辑,找到对应的点,打开下一层级的图纸.↓↓↓↓↓↓↓↓↓↓
            //区分打开的具体图纸. 0:项目图纸, 1:block图纸, 2:level图纸, 3:Zone图纸
            if (mDefectSelectedDataList.size() > 0) {
                String areaNameStr = mDefectSelectedDataList.get(0).getZone();
                String drawingId = mDefectSelectedDataList.get(0).getDrawing_id() + "";
                String drawingName = mDefectSelectedDataList.get(0).getDrawing_name();
                String drawingVersion = mDefectSelectedDataList.get(0).getDrawing_version();
                String drawingPath = mDefectSelectedDataList.get(0).getDrawing_path();
                Logger.e("areaStr=" + areaNameStr);
                Logger.e("drawingId=" + drawingId);
                Logger.e("drawingName=" + drawingName);
                Logger.e("drawingVersion=" + drawingVersion);
                Logger.e("drawingPath=" + drawingPath);
                if (currentDrawing.equals("0")) {
                    showDrawingBlockSelect(areaNameStr, drawingId, drawingName, drawingVersion, drawingPath);
                } else if (currentDrawing.equals("1")) {
                    showDrawingLevelSelect(areaNameStr, drawingId, drawingName, drawingVersion, drawingPath);
                } else if (currentDrawing.equals("2")) {
                    showDrawingZoneSelect(areaNameStr, drawingId, drawingName, drawingVersion, drawingPath);
                } else if (currentDrawing.equals("3")) {
                    //判断一下打开详情界面还是 申请页面进行编辑.
                    if (mDefectSelectedDataList.get(0).getStatus() == -1) {
                        Logger.e("打开申请页面进行编辑.");
                        //打开申请界面
                        //打开草稿箱的数据,mCopyCheckId传空
                        mCopyCheckId = "";
                        Intent intent = new Intent(Defect2DrawingListActivity.this, Defect2ApplyActivity.class);
                        intent.putExtra("check_id", mDefectSelectedDataList.get(0).getCheck_id());
                        intent.putExtra("defect_type", mDefectType);
                        intent.putExtra("copy_check_id", mCopyCheckId);//代表是否复制,如果有值代表复制,
                        startActivityForResult(intent, ConstantValue.Defect2SubmitAndSaveCode);
                    } else {
                        //让用户选择 要复制或者打开记录
                        //如果没有申请权限,直接打开记录,有申请权限和话就弹窗
                        if (mApplyPower.equals("1")) {
                            showOptionDialog();
                        } else {
                            mCopyCheckId = "";
                            //打开详情界面
                            Intent intent = new Intent(Defect2DrawingListActivity.this, Defect2DetailsActivity.class);
                            intent.putExtra("check_id", mDefectSelectedDataList.get(0).getCheck_id());
                            intent.putExtra("defect_type", mDefectType);
                            startActivityForResult(intent, ConstantValue.Defect2SubmitAndSaveCode);
                        }

                    }
                }
            }

            //↓↓↓↓↓↓↓↓↓↓旧的参考逻辑,打开Zone页面↓↓↓↓↓↓↓↓↓↓
//            if (mDefectSelectedDataList.size() > 0) {
//                Intent intent = new Intent(Defect2DrawingListActivity.this, Defect2DrawingListActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("projectId", projectId);
//                bundle.putString("block", tv_block.getText().toString());
//                bundle.putString("level", tv_leavel.getText().toString());
//                bundle.putString("zone", mDefectSelectedDataList.get(0).getZone());
//                //drawing_id,drawing_name,drawing_path
//                bundle.putSerializable("zone_info", (Serializable) mDefectSelectedDataList);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }

            // 不应该是显示删除的图标.而是写判断要去跳转到具体的zone 页面.
//        dmdlpDetailsDefectDetatilsAdapter.notifyDataSetChanged();
//        if (mDefectSelectedDataList.size() > 0) {
//            rv_listView.setVisibility(View.VISIBLE);
//        } else {
//            rv_listView.setVisibility(View.GONE);
//        }
        }
    }

    private void showPop(MotionEvent e) {
        Logger.e("----showPop----");
        View contentView = LayoutInflater.from(Defect2DrawingListActivity.this)
                .inflate(R.layout.ins_zone_add_inspection, null);

//        TextView tv_pin_title = contentView.findViewById(R.id.tv_pin_title);
//        tv_pin_title.setText(R.string.inspection_title);
        //处理popWindow 显示内容
        LinearLayout ll_pop_content = contentView.findViewById(R.id.ll_pop_content);
        ll_pop_content.setOnClickListener(this);

        popWindow = new CustomPopWindow.PopupWindowBuilder(Defect2DrawingListActivity.this)
                .setView(contentView)
                .create();
        int xOff = 0;
        int yOff;
        //下面这么写是最基础的方法, 还有一种简洁的方法判断极值,
        //首先 Y分三种情况  ,以Pop在下方写判断
        //第一种是按下的Y的位置 < pop 的高度
        //第二种是按下的Y的位置 >Y+pop的高度
        //第三种是  pop高度  <按下的Y的位置<Y+pop高度
        if ((Integer.parseInt(Math.round(e.getY()) + "")) < popWindow.getHeight()) {
            yOff = -pin_view.getHeight() + (Integer.parseInt(Math.round(e.getY()) + ""));
            Logger.e("AAA", "AAA");
            //每一种Y都有三种情况,分别判断X的位置 ,以Pop在右侧写判断
            //第一种是 按下的X位置< Pop的宽度
            //第二种是 按下的X位置> X+Pop的位置
            //第三种是 按下的X位置 在中间
            if ((Integer.parseInt(Math.round(e.getX()) + "")) < popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + ""));
//                ll_pop_content.setBackgroundResource(R.mipmap.bottom_right);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            } else if ((Integer.parseInt(Math.round(e.getX()) + "")) > pin_view.getWidth() - popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + "")) - (popWindow.getWidth());
//                ll_pop_content.setBackgroundResource(R.mipmap.bottom_left);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            } else if ((Integer.parseInt(Math.round(e.getX()) + "")) >= popWindow.getWidth()
                    && (Integer.parseInt(Math.round(e.getX()) + "")) <= pin_view.getWidth() - popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + ""));
//                ll_pop_content.setBackgroundResource(R.mipmap.bottom_right);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            }
        } else if ((Integer.parseInt(Math.round(e.getY()) + ""))
                > pin_view.getHeight() - popWindow.getHeight()) {
            Logger.e("BBB", "BBB");
            //每一种Y都有三种情况,分别判断X的位置 ,以Pop在右侧写判断
            //第一种是 按下的X位置< Pop的宽度
            //第二种是 按下的X位置> X+Pop的位置
            //第三种是 按下的X位置 在中间
            if (Build.VERSION.SDK_INT > 23) {
                yOff = (Integer.parseInt(Math.round(e.getY()) + "")) - popWindow.getHeight();
            } else {
                yOff = -((Integer.parseInt(Math.round(e.getY()) + "")) - pin_view.getHeight() + 2 * popWindow.getHeight());
            }
            if ((Integer.parseInt(Math.round(e.getX()) + "")) < popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + ""));
//                ll_pop_content.setBackgroundResource(R.mipmap.top_right);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            } else if ((Integer.parseInt(Math.round(e.getX()) + "")) > pin_view.getWidth() - popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + "")) - (popWindow.getWidth());
//                ll_pop_content.setBackgroundResource(R.mipmap.top_left);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            } else if ((Integer.parseInt(Math.round(e.getX()) + "")) >= popWindow.getWidth()
                    && (Integer.parseInt(Math.round(e.getX()) + "")) <= pin_view.getWidth() - popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + ""));
//                ll_pop_content.setBackgroundResource(R.mipmap.top_right);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            }
        } else {
            Logger.e("CCC", "CCC");
            //我也不知道为什么, 有这样的问题,  点击这个位置就定位不准,这个区域的,我就写个判断吧,打个补丁
            if (Build.VERSION.SDK_INT > 23) {
                Logger.e("大23323232", "大23232323");
                if (88 <= (Integer.parseInt(Math.round(e.getY()) + "")) && (Integer.parseInt(Math.round(e.getY()) + "")) <= 220) {
                    yOff = -pin_view.getHeight() + (Integer.parseInt(Math.round(e.getY()) + "")) - popWindow.getHeight();
                } else {
                    yOff = (Integer.parseInt(Math.round(e.getY()) + "") - popWindow.getHeight());
                }
            } else {
                Logger.e("小23323232", "小23232323");
                yOff = -pin_view.getHeight() + (Integer.parseInt(Math.round(e.getY()) + "")) - popWindow.getHeight();
            }
            //每一种Y都有三种情况,分别判断X的位置 ,以Pop在右侧写判断
            //第一种是 按下的X位置< Pop的宽度
            //第二种是 按下的X位置> X+Pop的位置
            //第三种是 按下的X位置 在中间
            if ((Integer.parseInt(Math.round(e.getX()) + "")) < popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + ""));
//                ll_pop_content.setBackgroundResource(R.mipmap.top_right);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            } else if ((Integer.parseInt(Math.round(e.getX()) + "")) > pin_view.getWidth() - popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + "")) - (popWindow.getWidth());
//                ll_pop_content.setBackgroundResource(R.mipmap.top_left);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            } else if ((Integer.parseInt(Math.round(e.getX()) + "")) >= popWindow.getWidth()
                    && (Integer.parseInt(Math.round(e.getX()) + "")) <= pin_view.getWidth() - popWindow.getWidth()) {
                xOff = (Integer.parseInt(Math.round(e.getX()) + ""));
//                ll_pop_content.setBackgroundResource(R.mipmap.top_right);
                ll_pop_content.setBackgroundResource(R.mipmap.add_inspection);
            }
        }

        Logger.e("------------------------------");
        Logger.e("Pop的宽度====" + popWindow.getWidth() + "");
        Logger.e("Pop的高度====" + popWindow.getHeight() + "");
        Logger.e("按下的X位置====" + (Integer.parseInt(Math.round(e.getX()) + "")));
        Logger.e("按下的要显示的X位置====" + xOff);
        Logger.e("按下的Y位置====" + (Integer.parseInt(Math.round(e.getY()) + "")));
        Logger.e("按下的要显示的y位置====" + yOff);
        Logger.e("------------------------------");

        Logger.e("------------------------------");
        Logger.e("pinX====" + pinX);
        Logger.e("pinY====" + pinY);
        Logger.e("------------------------------");

        int x = (int) pinX;
        int y = (int) pinY;

        currentDrawingPosition = x + "," + y;

        popWindow.showAsDropDown(pin_view, xOff, yOff, Gravity.NO_GRAVITY);
    }


    private void showAllPin() {
        Logger.e("----showAllPin----");
        if (PinLists.size() > 0) {
            PinLists.clear();
        }
        for (int i = 0; i < PointLists.size(); i++) {
            // 这里面写判断, 如何多个颜色
            PointF center = new PointF(Float.parseFloat((PointLists.get(i).getPin_x() + "")),
                    (int) Float.parseFloat(PointLists.get(i).getPin_y() + ""));
            PinLists.add(center);
            pin_view.setAllTypePins(PinLists, mDefectDataList);
//            pin_view.setPins(PinLists);
        }
    }

    private void showAddPopwindow() {
        if (PinLists.size() > 0) {
            PinLists.clear();
        }

        QueryDMDLPDefectListDetailsResultBean.ResultBean bean
                = new QueryDMDLPDefectListDetailsResultBean.ResultBean();
        bean.setStatus(0);
        // 图钉的索引应该由 本地数据库中id 来决定. 现在写个临时的.
        bean.setPin_pos("0");
        mDefectDataList.add(bean);

        DMDLPPinPoint dmdlpPinPoint = new DMDLPPinPoint();
        dmdlpPinPoint.setPin_x(pinX);
        dmdlpPinPoint.setPin_y(pinY);
        PointLists.add(dmdlpPinPoint);
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");

        showAllPin();

        // 在本地数据库中创建出此条记录.
        try {

//            Intent intent = new Intent(InspectionZoneActivity.this,
//                    InspectionDetailsActivity.class);
            //跳转到类型界面.
            Intent intent = new Intent(Defect2DrawingListActivity.this,
                    Defect2ApplyActivity.class);
            intent.putExtra("check_id", "");//第一次创建check_id为空
            intent.putExtra("defect_type", mDefectType);
            intent.putExtra("block", mBlockStr);
            intent.putExtra("level", mLevelStr);
            intent.putExtra("zone", mZoneStr);

            intent.putExtra("drawing_id", currentDrawingId);
            intent.putExtra("drawing_path", currentDrawingPicPath);
            intent.putExtra("drawing_position", currentDrawingPosition);

            intent.putExtra("copy_check_id", mCopyCheckId);//代表是否复制,如果有值代表复制,
            startActivityForResult(intent, ConstantValue.Defect2SubmitAndSaveCode);
            mCopyCheckId = "";//复制完成一次后,取消.
        } catch (Exception e) {
            e.printStackTrace();
        }

        popWindow.dissmiss();
    }


    public void showAllPins(List<QueryDefect2PinListsResultBean.ResultBean> insRecordLists) {
        if (PointLists.size() > 0) {
            PointLists.clear();
        }
        if (mDefectDataList.size() > 0) {
            mDefectDataList.clear();
        }
//        Logger.e("insRecordLists==" + insRecordLists.toString());
        for (int i = 0; i < insRecordLists.size(); i++) {
            QueryDMDLPDefectListDetailsResultBean.ResultBean bean = new QueryDMDLPDefectListDetailsResultBean.ResultBean();
            //点的信息.
            String drawing_position = insRecordLists.get(i).getDrawing_position();
            String[] split = drawing_position.split(",");

            int pinx = Math.round(Float.parseFloat(split[0]));
            int piny = Math.round(Float.parseFloat(split[1]));
            DMDLPPinPoint dmdlpPinPoint = new DMDLPPinPoint();
            dmdlpPinPoint.setPin_x(pinx);
            dmdlpPinPoint.setPin_y(piny);
            dmdlpPinPoint.setPin_pos(i + 1);
            dmdlpPinPoint.setDrawing_id(Integer.parseInt(currentDrawingId));
            dmdlpPinPoint.setDrawing_name(currentDrawingId);
            dmdlpPinPoint.setDrawing_path(currentDrawingPicPath);
            dmdlpPinPoint.setDrawing_position(insRecordLists.get(i).getDrawing_position());

            //设置点
            bean.setStatus(Integer.parseInt(insRecordLists.get(i).getStatus()));
            bean.setPin_x(pinx);
            bean.setPin_y(piny);
            if (!TextUtils.isEmpty(insRecordLists.get(i).getCheck_id())) {
                bean.setPin_pos(insRecordLists.get(i).getDefect_no());
            } else {
                bean.setPin_pos((i + 1) + "");
            }
            bean.setDrawing_id(Integer.parseInt(currentDrawingId));
            bean.setDrawing_name(currentDrawingId);
            bean.setDrawing_path(currentDrawingPicPath);
            bean.setDrawing_position(insRecordLists.get(i).getDrawing_position());
            bean.setCheck_id(insRecordLists.get(i).getCheck_id());
            bean.setRecord_type("0");//record_type;0本地数据 1网络数据
            bean.setFeature_pin_type("defect");
            mDefectDataList.add(bean);

            PointLists.add(dmdlpPinPoint);

//            Logger.e("dmdlpPinPoint====" + dmdlpPinPoint.toString());
//            Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");
        }


        Logger.e("PointLists.size()====" + PointLists.size() + "");
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");

        showAllPin();
    }

    public void showBlockPins(List<QueryDefectBlockListsResultBean.ResultBean.BlockListBean> blockLists) {
        if (mDefectDataList.size() > 0) {
            mDefectDataList.clear();
        }
        if (PointLists.size() > 0) {
            PointLists.clear();
        }
        for (int i = 0; i < blockLists.size(); i++) {
            QueryDMDLPDefectListDetailsResultBean.ResultBean bean = new QueryDMDLPDefectListDetailsResultBean.ResultBean();
            //点的信息.
            String drawing_position1 = blockLists.get(i).getDrawing_position();
            String[] posionArr = drawing_position1.split(",");

            DMDLPPinPoint dmdlpPinPoint = new DMDLPPinPoint();
            dmdlpPinPoint.setPin_x(Integer.parseInt(posionArr[0]));
            dmdlpPinPoint.setPin_y(Integer.parseInt(posionArr[1]));

            dmdlpPinPoint.setPin_pos(blockLists.get(i).getDrawing_id());
            dmdlpPinPoint.setDrawing_id(blockLists.get(i).getDrawing_id());
            dmdlpPinPoint.setDrawing_name(blockLists.get(i).getDrawing_name());
            dmdlpPinPoint.setUnit("");
            dmdlpPinPoint.setDrawing_path(blockLists.get(i).getDrawing_path());
            dmdlpPinPoint.setDrawing_position(blockLists.get(i).getDrawing_position());

            dmdlpPinPoint.setZone(blockLists.get(i).getBlock());
            //设置点
            bean.setStatus(0);
            bean.setPin_x(Integer.parseInt(posionArr[0]));
            bean.setPin_y(Integer.parseInt(posionArr[1]));
            bean.setPin_pos(blockLists.get(i).getDrawing_id() + "");
            bean.setDrawing_id(blockLists.get(i).getDrawing_id());
            bean.setDrawing_name(blockLists.get(i).getDrawing_name());

            bean.setDrawing_path(blockLists.get(i).getDrawing_path());
            bean.setDrawing_position(blockLists.get(i).getDrawing_position());
            bean.setZone(blockLists.get(i).getBlock());
            bean.setDrawing_version(blockLists.get(i).getBlock_version());
            bean.setFeature_pin_type("defect");
            mDefectDataList.add(bean);
            PointLists.add(dmdlpPinPoint);

            Logger.e("dmdlpPinPoint====" + dmdlpPinPoint);
            Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");
        }
        Logger.e("PointLists.size()====" + PointLists.size() + "");
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");

        showAllPin();//block
    }

    public void showLevelPins(List<QueryDefectLevelListsResultBean.ResultBean.LevelBean> levelLists) {
        if (mDefectDataList.size() > 0) {
            mDefectDataList.clear();
        }
        if (PointLists.size() > 0) {
            PointLists.clear();
        }

        Logger.e("showLevelPins");
        Logger.e("pinLits==" + levelLists.size());
        for (int i = 0; i < levelLists.size(); i++) {
            QueryDMDLPDefectListDetailsResultBean.ResultBean bean = new QueryDMDLPDefectListDetailsResultBean.ResultBean();
            //点的信息.
            String drawing_position1 = levelLists.get(i).getDrawing_position();
            String[] posionArr = drawing_position1.split(",");

            DMDLPPinPoint dmdlpPinPoint = new DMDLPPinPoint();
            dmdlpPinPoint.setPin_x(Integer.parseInt(posionArr[0]));
            dmdlpPinPoint.setPin_y(Integer.parseInt(posionArr[1]));

            dmdlpPinPoint.setPin_pos(Integer.parseInt(levelLists.get(i).getDrawing_id()));
            dmdlpPinPoint.setDrawing_id(Integer.parseInt(levelLists.get(i).getDrawing_id()));
            dmdlpPinPoint.setDrawing_name(levelLists.get(i).getDrawing_name());
            dmdlpPinPoint.setUnit("");
            dmdlpPinPoint.setDrawing_path(levelLists.get(i).getDrawing_path());
            dmdlpPinPoint.setDrawing_position(levelLists.get(i).getDrawing_position());

            dmdlpPinPoint.setZone(levelLists.get(i).getSecondary_region());
            //设置点
            bean.setStatus(0);
            bean.setPin_x(Integer.parseInt(posionArr[0]));
            bean.setPin_y(Integer.parseInt(posionArr[1]));
            bean.setPin_pos(levelLists.get(i).getDrawing_id() + "");
            bean.setDrawing_id(Integer.parseInt(levelLists.get(i).getDrawing_id()));
            bean.setDrawing_name(levelLists.get(i).getDrawing_name());

            bean.setDrawing_path(levelLists.get(i).getDrawing_path());
            bean.setDrawing_position(levelLists.get(i).getDrawing_position());
            bean.setZone(levelLists.get(i).getSecondary_region());
            bean.setDrawing_version(levelLists.get(i).getVersion());
            bean.setFeature_pin_type("defect");
            mDefectDataList.add(bean);
            PointLists.add(dmdlpPinPoint);

            Logger.e("dmdlpPinPoint====" + dmdlpPinPoint);
            Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");
        }

        Logger.e("PointLists.size()====" + PointLists.size() + "");
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");

        showAllPin();//level
    }

    public void showZonePins(List<QueryDefectZoneListsResultBean.ResultBean.UnitListBean> zoneLists) {
        if (mDefectDataList.size() > 0) {
            mDefectDataList.clear();
        }
        if (PointLists.size() > 0) {
            PointLists.clear();
        }

        Logger.e("showZonePins");
        Logger.e("pinLits==" + zoneLists.size());
        for (int i = 0; i < zoneLists.size(); i++) {
            QueryDMDLPDefectListDetailsResultBean.ResultBean bean = new QueryDMDLPDefectListDetailsResultBean.ResultBean();
            //点的信息.
            String drawing_position1 = zoneLists.get(i).getDrawing_position();
            String[] posionArr = drawing_position1.split(",");

            DMDLPPinPoint dmdlpPinPoint = new DMDLPPinPoint();
            dmdlpPinPoint.setPin_x(Integer.parseInt(posionArr[0]));
            dmdlpPinPoint.setPin_y(Integer.parseInt(posionArr[1]));

            dmdlpPinPoint.setPin_pos(Integer.parseInt(zoneLists.get(i).getDrawing_id()));
            dmdlpPinPoint.setDrawing_id(Integer.parseInt(zoneLists.get(i).getDrawing_id()));
            dmdlpPinPoint.setDrawing_name(zoneLists.get(i).getDrawing_name());
            dmdlpPinPoint.setUnit("");
            dmdlpPinPoint.setDrawing_path(zoneLists.get(i).getDrawing_path());
            dmdlpPinPoint.setDrawing_position(zoneLists.get(i).getDrawing_position());

            dmdlpPinPoint.setZone(zoneLists.get(i).getUnit());
            //设置点
            bean.setStatus(0);
            bean.setPin_x(Integer.parseInt(posionArr[0]));
            bean.setPin_y(Integer.parseInt(posionArr[1]));
            bean.setPin_pos(zoneLists.get(i).getDrawing_id() + "");
            bean.setDrawing_id(Integer.parseInt(zoneLists.get(i).getDrawing_id()));
            bean.setDrawing_name(zoneLists.get(i).getDrawing_name());

            bean.setDrawing_path(zoneLists.get(i).getDrawing_path());
            bean.setDrawing_position(zoneLists.get(i).getDrawing_position());
            bean.setZone(zoneLists.get(i).getUnit());
            bean.setDrawing_version(zoneLists.get(i).getVersion());
            bean.setFeature_pin_type("defect");
            mDefectDataList.add(bean);
            PointLists.add(dmdlpPinPoint);

            Logger.e("dmdlpPinPoint====" + dmdlpPinPoint);
            Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");
        }

        Logger.e("PointLists.size()====" + PointLists.size() + "");
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");

        showAllPin();//zone
    }


    /**
     * 获取摄像头权限
     */
    private void getCameraPersimmions() {
        //                        Logger.e("onActivityCreate", "onActivityCreate");
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
        // 二维码扫描 要不要
        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            String projectId = projectAndPermissionBean.getProject_id();
            //判断是从哪个页面进入的
            Intent intent = new Intent(Defect2DrawingListActivity.this,
                    CustomFullScanActivity.class);
            intent.putExtra("flag", "task_home");
            intent.putExtra("projectId", projectId);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 打开图纸
     */
    private void openDrawingPic() {

        iv_empty.setVisibility(View.GONE);
        //打开图纸.
        ImageSource uri = ImageSource.uri(mDrawingPicFile.getPath());
        pin_view.setImage(uri);
        initGuesture();
    }

    /**
     * drawing_flag=0 项目图纸,drawing_flag=1 block图纸,drawing_flag=2 level图纸,drawing_flag=3 Zone图纸
     */
    private void showDrawingPin(String drawing_flag) {
        //取出来展示默认的 level上标的点.
        switch (drawing_flag) {
            case "0":
                showBlockPins(mBlockDrawingLists);
                break;
            case "1":
                showLevelPins(mLevelDrawingLists);
                break;
            case "2":
                showZonePins(mZoneDrawingLists);
                break;
            case "3":
                // 显示具体的用户申请的点数据
                showAllPins(mAllPinsLists);
                break;
        }
    }

    /*
    只负责下载图片和打开图片,显示点
       pic_flag=0 项目图纸,  pic_flag=1 block图纸,  pic_flag=2 level图纸,  pic_flag=3 Unit图纸,
     */
    private void downloadDrawingPic(String drawingPicName, String drawingPicUrl, String pic_flag,
                                    String drawing_id, String drawing_path) {
        showProgressDialog(msg);
        OkHttpUtils//
                .get()//
                .url(drawingPicUrl)//
                .build()//
//                .execute(new FileCallBack(Config.PHOTO_SRC, pic_name) {
                .execute(new FileCallBack(Config.SDStorageCacheInspectionPic, drawingPicName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dismissProgressDialog();
                        Logger.e("onError==", e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        dismissProgressDialog();
                        Logger.e("下载完成后的地址==" + file.getAbsoluteFile());
                        try {
                            Logger.e("下载完成");
                            //打开图纸, 并且显示点.
                            // pic_flag=0 项目图纸,  pic_flag=1 block图纸,
                            // pic_flag=2 level图纸,  pic_flag=3 Unit图纸,
                            switch (pic_flag) {
                                case "0":
                                    //打开图纸 ,显示标点.
                                    currentDrawing = "0";
                                    openDrawingPic();
                                    showDrawingPin(currentDrawing);//项目图纸显示block
                                    break;
                                case "1":
                                    // 查询level 数组图纸.
                                    currentDrawing = "1";
                                    openDrawingPic();
                                    defect2DrawingListsModel.QueryDefectLevelLists(projectId,
                                            mBlockStr, "1", Defect2DrawingListActivity.this);
                                    break;
                                case "2":
                                    // 查询Zone 数组图纸.
                                    currentDrawing = "2";
                                    openDrawingPic();
                                    defect2DrawingListsModel.QueryDefectZoneLists(projectId,
                                            mBlockStr, mLevelStr, "1", Defect2DrawingListActivity.this);
                                    break;
                                case "3":
                                    // 打开图纸, 并且请求接口,显示具体的数据.
                                    currentDrawing = "3";
                                    currentDrawingId = drawing_id;
                                    currentDrawingPicPath = drawing_path;
                                    openDrawingPic();
                                    //查询数据
                                    defect2DrawingListsModel.QueryDefect2PinLists(projectId, mBlockStr, mLevelStr, mZoneStr,
                                            currentDrawingId, "1", Defect2DrawingListActivity.this);
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onSuccess(String name, Object objectBean) {
        if (name.equals(NetApi.QueryDefectBlockLists)) {
            QueryDefectBlockListsResultBean resultBean =
                    (QueryDefectBlockListsResultBean) objectBean;
            mBlockDrawingLists.addAll(resultBean.getResult().getBlock_list());
            //判断一下项目的图纸是否为空
            if (!TextUtils.isEmpty(resultBean.getResult().getProgram_drawing().getDrawing_name())) {
                //判断 文件存不存在, 版本号+drawing_id+文件名称.
                String projectPicName = resultBean.getResult().getProgram_drawing().getProgram_version()
                        + "_" + resultBean.getResult().getProgram_drawing().getDrawing_id()
                        + "_" + resultBean.getResult().getProgram_drawing().getDrawing_name();
                String projectPicUrl = NetApi.IMG_SHOW_URL + resultBean.getResult().getProgram_drawing().getDrawing_path();

                mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
                        + "/" + projectPicName);
                if (mDrawingPicFile.exists()) {
                    //存在 显示坐标点
                    Logger.e("projectPicFile--文件存在");
                    //打开图纸 ,显示标点.
                    currentDrawing = "0";
                    openDrawingPic();
                    showDrawingPin(currentDrawing);//项目图纸显示block
                } else {
                    //不存在下载,
                    Logger.e("projectPicFile--文件不存在");
                    downloadDrawingPic(projectPicName, projectPicUrl, "0",
                            resultBean.getResult().getProgram_drawing().getDrawing_id().toString(),
                            resultBean.getResult().getProgram_drawing().getDrawing_path());
                }
            }
        } else if (name.equals(NetApi.QueryDefectLevelLists)) {
            //添加level到选择列表, 显示level点
            QueryDefectLevelListsResultBean resultBean =
                    (QueryDefectLevelListsResultBean) objectBean;
            resultBean.getResult().getLevel();
            mLevelDrawingLists.addAll(resultBean.getResult().getLevel());
            //打开图纸 ,显示标点.
//            currentDrawing="1";
//            openDrawingPic();
            showDrawingPin(currentDrawing);//block图纸显示level点
        } else if (name.equals(NetApi.QueryDefectZoneLists)) {
            //添加zone到选择列表, 显示zone坐标点
            QueryDefectZoneListsResultBean resultBean =
                    (QueryDefectZoneListsResultBean) objectBean;
            resultBean.getResult().getLevel();
            mZoneDrawingLists.addAll(resultBean.getResult().getUnit_list());
            //打开图纸 ,显示标点.
//            currentDrawing="2";
//            openDrawingPic();
            showDrawingPin(currentDrawing);//level图纸显示zone点
        } else if (name.equals(NetApi.QueryDefect2PinLists)) {
            QueryDefect2PinListsResultBean resultBean =
                    (QueryDefect2PinListsResultBean) objectBean;
            mAllPinsLists.addAll(resultBean.getResult());
            // 显示点.
            showDrawingPin(currentDrawing);//zone图纸显示用户申请的点
        }

    }

    @Override
    public void onFaild(String name, String faildStr) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.Defect2SubmitAndSaveCode
                && resultCode == ConstantValue.Defect2SubmitAndSaveCode) {
            // 刷新页面的点的数据.
            defect2DrawingListsModel.QueryDefect2PinLists(projectId, mBlockStr, mLevelStr, mZoneStr,
                    currentDrawingId, "1", this);
        }
    }
}