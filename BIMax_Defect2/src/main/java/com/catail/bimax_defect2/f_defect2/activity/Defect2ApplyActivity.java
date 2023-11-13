package com.catail.bimax_defect2.f_defect2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2DisciplineListsAdapter;
import com.catail.bimax_defect2.f_defect2.bean.Defect2QueryDetailsRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2QueryDetailsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2SubmitRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2SubmitResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDisciplineListsResultBean;
import com.catail.bimax_defect2.f_defect2.model.Defect2ApplyListModel;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.activity.UseCameraActivity;
import com.catail.lib_commons.adapter.AddPhotoAdapter;
import com.catail.lib_commons.api.UploadApi;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.AddPhotoBean;
import com.catail.lib_commons.bean.DataSuccessBean;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.bean.UserInfoBean;
import com.catail.lib_commons.interfaces.BESCallBack;
import com.catail.lib_commons.utils.Common;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.DateFormatUtils;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MyLog;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.PhotoUtils;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Util;
import com.catail.lib_commons.utils.Utils;
import com.catail.lib_commons.utils.calendarselection.MonthDateView;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hzw.doodle.DoodleActivity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2ApplyActivity extends BaseActivity implements View.OnClickListener, BESCallBack {
    private TextView mTvDiscipline, mTvType, mTvDefectCategory, mTvDeadline, mTbFlag;
    private EditText mEtRemarks;
    private List<AddPhotoBean> mPhotosList;//附件区域图片集合
    private AddPhotoAdapter mAddPhotoAdapter;//附件区域添加图片的适配器
    private String msg = "", projectId = "", mCheckId = "", mCopyCheckId = "",
            mStartDateDay = "", urgentFlag = "0";
    private String mDefectType = "", mDisciplineId = "", mTradeId = "", mSubConId = "", mCategoryId = "";
    private String mBlock = "", mLevel = "", mZone = "";
    private String mDrawingId = "", mDrawingPath = "", mDrawingPosition = "";
    private ArrayList<QueryDisciplineListsResultBean.ResultBean> mDisciplineLists;
    private UploadApi uploadApi;// 图片上传

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_defect2_apply;
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(R.string.defect2_title);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);


        mTvDiscipline = findViewById(R.id.tv_discipline);
        mTvType = findViewById(R.id.tv_type);
        mTvDefectCategory = findViewById(R.id.tv_defect_category);
        mEtRemarks = findViewById(R.id.et_remarks);

        mTvDiscipline.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvDefectCategory.setOnClickListener(this);

        RelativeLayout rl_deadline_content = findViewById(R.id.rl_deadline_content);
        mTvDeadline = findViewById(R.id.tv_deadline);
        rl_deadline_content.setOnClickListener(this);

        mTbFlag = findViewById(R.id.tb_flag);
        mTbFlag.setOnClickListener(this);
        mTbFlag.setSelected(false);

        TextView tv_submit_btn = findViewById(R.id.tv_submit_btn);
        TextView tv_save = findViewById(R.id.tv_save);
        tv_submit_btn.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        mDisciplineLists = new ArrayList<>();

        RecyclerView rvPhotoView = findViewById(R.id.rv_photo_view);
        mPhotosList = new ArrayList<>();

        final GridLayoutManager glm = new GridLayoutManager(Defect2ApplyActivity.this, 3);
        rvPhotoView.setLayoutManager(glm);
        mAddPhotoAdapter = new AddPhotoAdapter(mPhotosList, Defect2ApplyActivity.this, 9);
        rvPhotoView.setAdapter(mAddPhotoAdapter);
        mAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            if (id == R.id.iv_photo) {
                Logger.e("图片编辑", "图片编辑");
                AddPhotoBean addPhotoBean = mPhotosList.get(position);
                String imageEditorPic = addPhotoBean.getPic();
                PhotoUtils.ImageEditor(Defect2ApplyActivity.this, imageEditorPic, position);
            } else if (id == R.id.iv_del_photo) {
                Logger.e("删除照片", "删除照片");
                mPhotosList.remove(position);
                mAddPhotoAdapter.notifyDataSetChanged();
            } else if (id == R.id.iv_add_photo) {
                Logger.e("添加照片", "添加照片");
                // 点击事件,弹出对话框选择,本地相册,拍照
                PhotoUtils.showPhotoSelectionDialog(Defect2ApplyActivity.this, 9);
            }
        });
        rvPhotoView.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        //获取系统版本,然后显示中英文进度条文字
        final int sysVersion = Utils.GetSystemCurrentVersion();
        if (sysVersion == 0) {
            msg = getString(R.string.processing);
        } else {
            msg = getString(R.string.processing);
        }

        try {
            ProjectAndPermissionBean projectAndPermissionBean = (ProjectAndPermissionBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
            projectId = projectAndPermissionBean.getProject_id();

            mCheckId = getIntent().getStringExtra("check_id");
            mDefectType = getIntent().getStringExtra("defect_type");
            mCopyCheckId = getIntent().getStringExtra("copy_check_id");
            Logger.e("mCopyCheckId==" + mCopyCheckId);
            //如果mCheckId 为空,则是申请,不为空则是编辑
            if (TextUtils.isEmpty(mCheckId)) {
                //如果mCopyCheckId为空,申请
                if (TextUtils.isEmpty(mCopyCheckId)) {
                    mBlock = getIntent().getStringExtra("block");
                    mLevel = getIntent().getStringExtra("level");
                    mZone = getIntent().getStringExtra("zone");

                    mDrawingId = getIntent().getStringExtra("drawing_id");
                    mDrawingPath = getIntent().getStringExtra("drawing_path");
                    mDrawingPosition = getIntent().getStringExtra("drawing_position");
                    AddPhotoBean bean = new AddPhotoBean();
                    bean.setItemType(1);
                    bean.setPhotoTitle("");
                    bean.setPic("");
                    mPhotosList.add(bean);
                    mAddPhotoAdapter.notifyDataSetChanged();
                } else {
                    mDrawingPosition = getIntent().getStringExtra("drawing_position");
                    //如果不为空,则是复制功能
                    //查询数据,下载图片.
                    defect2QueryDetails();
                }
            } else {
                //查询数据,下载图片.
                defect2QueryDetails();
            }
            Defect2ApplyListModel defect2ApplyListModel =
                    new Defect2ApplyListModel(Defect2ApplyActivity.this);
            defect2ApplyListModel.QueryDisciplineLists(projectId, mDefectType, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_menu) {
            Defect2ApplyActivity.this.finish();
        } else if (v.getId() == R.id.tv_discipline) {
            showDisciplineDialog();
        } else if (v.getId() == R.id.tv_type) {
            if (!TextUtils.isEmpty(mDisciplineId)) {
                Intent intent = new Intent(this, Defect2TypeSelectActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("defectType", mDefectType);
                intent.putExtra("disciplineId", mDisciplineId);
                intent.putExtra("block", mBlock);
                intent.putExtra("level", mLevel);
                intent.putExtra("zone", mZone);
                startActivityForResult(intent, ConstantValue.Defect2TypeSelectCode);
            } else {
                showToast(getResources().getString(R.string.defect2_please_select_discipline_type));
            }
        } else if (v.getId() == R.id.tv_defect_category) {
            if (!TextUtils.isEmpty(mTradeId)) {
                Intent intent = new Intent(this, Defect2CategorySelectActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("defectType", mDefectType);
                intent.putExtra("disciplineId", mDisciplineId);
                intent.putExtra("tradeId", mTradeId);
                intent.putExtra("block", mBlock);
                intent.putExtra("level", mLevel);
                intent.putExtra("zone", mZone);
                startActivityForResult(intent, ConstantValue.Defect2CategorySelectCode);
            } else {
                showToast(getResources().getString(R.string.defect2_please_select_type));
            }
        } else if (v.getId() == R.id.rl_deadline_content) {
            showDateDialog();
        } else if (v.getId() == R.id.tb_flag) {
            // 如果已经选中状态则取消选中 否则使选中状态
            if (mTbFlag.isSelected()) {
                mTbFlag.setSelected(false);
                urgentFlag = "0";
                // btnSubmit.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                mTbFlag.setSelected(true);
                urgentFlag = "1";
                // btnSubmit.setBackgroundColor(getResources().getColor(R.color.color_btn_blue));
            }
        } else if (v.getId() == R.id.tv_save) {
            submitAndSave("0");
        } else if (v.getId() == R.id.tv_submit_btn) {
            submitAndSave("1");
        }
    }


    /**
     * 类型选择框
     */
    @SuppressLint("WrongConstant")
    private void showDisciplineDialog() {
        AlertDialog typeDialog = new AlertDialog.Builder(Defect2ApplyActivity.this).create();
        View view = Defect2ApplyActivity.this.getLayoutInflater().inflate(R.layout.list_dialog2, null);
        TextView titleText = view.findViewById(R.id.tv_title);
        titleText.setText(R.string.defect2_please_select_discipline_type);
        RecyclerView rvlistView = view.findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(Defect2ApplyActivity.this);
        lm.setOrientation(OrientationHelper.VERTICAL);
        rvlistView.setLayoutManager(lm);
        Defect2DisciplineListsAdapter rvListAdapter = new Defect2DisciplineListsAdapter(
                R.layout.adapter_checklist_create_by_item, mDisciplineLists);
        rvlistView.setAdapter(rvListAdapter);
        rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
            if (view1.getId() == R.id.rl_content) {
                mDisciplineId = mDisciplineLists.get(i).getDiscipline_id().toString();
                mTvDiscipline.setText(mDisciplineLists.get(i).getDiscipline());
                typeDialog.dismiss();
            }
        });

        typeDialog.setView(view);
        typeDialog.show();
        Utils.setAlertDialogConner(typeDialog);
        Utils.setAlertDialogSize(Defect2ApplyActivity.this, typeDialog, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //    storage/emulated/0/CMS/1527832345846.jpg//拍照后
        //    storage/emulated/0/DCIM/Camera/IMG_20180601_135318.jpg//从相册选择

        Logger.e("------------------------------");
        Logger.e("requestCode====" + requestCode);
        Logger.e("resultCode====" + resultCode);
        Logger.e("RESULT_OK====" + RESULT_OK);
        Logger.e("------------------------------");

        if (requestCode == ConstantValue.takePhotoCode && resultCode == ConstantValue.takePhotoCode) {
            String path = data.getStringExtra(UseCameraActivity.IMAGE_PATH);
            Logger.e("拍照的回调==", path);
//                tv_test_question1.setText(path);
            Bitmap bitmap = Utils.DealTakePhoto(mContentResolver, path);
            PhotoUtils.DealTakePhoto(bitmap, path);
            Logger.e("path==" + path);

            ArrayList<String> picStrLists = new ArrayList<>();
            picStrLists.add(path);
            PhotoUtils.ImageLubanCompression(Defect2ApplyActivity.this,
                    picStrLists, mPhotosList, mAddPhotoAdapter);
//            PhotoUtils.TaskPhotoResult(DMDLPApplyActivity.this, PhotoUtils.captureFileUri,
//                    mPhotosList, mAddPhotoAdapter);
        } else if (requestCode == ConstantValue.CopyWeChatImageSelector && data != null) {
            try {
                //获取选择器返回的数据
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                Logger.e("images", images.toString());
                PhotoUtils.ImageLubanCompression(Defect2ApplyActivity.this, images,
                        mPhotosList, mAddPhotoAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == ConstantValue.ImageEditorCode) {
            // 图片区域 相册选择后照片的编辑
            if (resultCode == DoodleActivity.RESULT_OK) {
                String path = data.getStringExtra(DoodleActivity.KEY_IMAGE_PATH);
                int position = data.getIntExtra("position", -1);
                PhotoUtils.ImageShow(path, mPhotosList, mAddPhotoAdapter, position);
            } else if (resultCode == DoodleActivity.RESULT_ERROR) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ConstantValue.Defect2TypeSelectCode && resultCode == ConstantValue.Defect2TypeSelectCode) {
            mTradeId = data.getStringExtra("trade_id");
            mSubConId = data.getStringExtra("sub_con_id");
            mTvType.setText(data.getStringExtra("trade_name"));
        } else if (requestCode == ConstantValue.Defect2CategorySelectCode && resultCode == ConstantValue.Defect2CategorySelectCode) {
            mCategoryId = data.getStringExtra("category_id");
            mTvDefectCategory.setText(data.getStringExtra("category_name"));

        }
    }


    private ImageView iv_left;
    private ImageView iv_right;
    private MonthDateView monthDateView;
    private String selectionDay = "";
    private AlertDialog Datedialog;

    /**
     * 日历选择
     */
    private void showDateDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.date_select, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Defect2ApplyActivity.this);
        builder.setView(view);

        //日历区域
        iv_left = view.findViewById(R.id.iv_left);
        iv_right = view.findViewById(R.id.iv_right);
        monthDateView = view.findViewById(R.id.monthDateView);
        TextView tv_date = view.findViewById(R.id.date_text);
        TextView tv_week = view.findViewById(R.id.week_text);
        monthDateView.setTextView(tv_date, tv_week);

        initDefaultDate();

//		monthDateView.setDaysHasThingList(list);
        monthDateView.setDateClick(() -> {
            Log.e("日期", monthDateView.getmSelYear() + "-" + (monthDateView.getmSelMonth() + 1) + "-" + monthDateView.getmSelDay());
            String mDay;
            int day = monthDateView.getmSelDay();
            if (day < 10) {
                mDay = "0" + day;
            } else {
                mDay = day + "";
            }
            String mMon;
            int mon = monthDateView.getmSelMonth() + 1;
            if (mon < 10) {
                mMon = "0" + mon;
            } else {
                mMon = mon + "";
            }
            selectionDay = monthDateView.getmSelYear() + "-" + mMon + "-" + mDay;
            //Logger.e("selectionDay", selectionDay);

            mStartDateDay = selectionDay;
            String formatDate = DateFormatUtils.CNStr2ENStrNoTime(mStartDateDay);
            mTvDeadline.setText(formatDate);

            Datedialog.dismiss();
        });
//		monthDateView.setmCurrentColor(color.new_calendar_month_textcolor);
        setOnlistener();

        Datedialog = builder.show();
        Utils.setAlertDialogConner(Datedialog);
        Utils.setAlertDialogSize(Defect2ApplyActivity.this, Datedialog, 0.96);
    }

    private void setOnlistener() {
        iv_left.setOnClickListener(v -> monthDateView.onLeftClick());

        iv_right.setOnClickListener(v -> monthDateView.onRightClick());

    }

    private void initDefaultDate() {
        //区分一下开始 时间和结束时间
        if (!mStartDateDay.isEmpty()) {
            Logger.e("startDateDay!=空", "startDateDay!=空");
            String[] split = mStartDateDay.split("-");
            int year = Integer.parseInt(split[0]);
            int month;
            if (split[1].startsWith("0")) {
                month = Integer.parseInt(split[1]) - 1;
            } else {
                month = Integer.parseInt(split[1]) - 1;
            }

            int day;
            if (split[2].startsWith("0")) {
                day = Integer.parseInt(split[2]);
            } else {
                day = Integer.parseInt(split[2]);
            }
            MonthDateView.setCurrentDay(year, month, day);
        } else {
            Logger.e("startDateDay==空", "startDateDay==空");
        }

    }


    /**
     * submitAndSaveType  0 保存 1提交.
     */
    private void submitAndSave(String submitAndSaveType) {
        List<String> photoLists = new ArrayList<>();

        for (int i = 0; i < mPhotosList.size(); i++) {
            if (mPhotosList.get(i).getItemType() == 0) {
                photoLists.add(mPhotosList.get(i).getPic());
            }
        }
        if (photoLists.size() > 0) {
            if (uploadApi == null) {
                uploadApi = new UploadApi();
            }
            showProgressDialog(msg);
            uploadApi.request(photoLists, new UploadApi.IMAGEResultBack() {
                @Override
                public void onSuccess(final JSONObject jsonObject) {
                    runOnUiThread(() -> {
                        dismissProgressDialog();

                        try {
                            Object object = uploadApi.response(jsonObject);
                            if (object instanceof List<?>) {
                                // success
                                List<String> photoList1 = new ArrayList<>();
                                photoList1.addAll((List<String>) object);

                                defect2SaveAndSubmitDatas(submitAndSaveType, photoList1);
                            } else if (object instanceof DataSuccessBean) {
                                DataSuccessBean dataSuccessBean = (DataSuccessBean) object;
                                Logger.e("dataSuccessBean.toString()=", dataSuccessBean.toString());
                                /* 图片上上传失败 */
                                Common.showToast(Defect2ApplyActivity.this, getString(R.string.picture_fail));
                            }
                        } catch (Exception e) {
                            dismissProgressDialog();
                            Logger.e("Exception", "Exception");
                            e.printStackTrace();
                            Logger.e("error", "异常：" + e.getMessage());
                            /* 图片上上传失败 */
                            Common.showToast(Defect2ApplyActivity.this, getString(R.string.picture_fail));
                        }
                    });
                }

                @Override
                public void OnFail(String error, String failType) {
                    dismissProgressDialog();
                    /* 图片上上传失败 */
                    Common.showToast(Defect2ApplyActivity.this, getString(R.string.picture_fail));
                }
            });
        } else {
            defect2SaveAndSubmitDatas(submitAndSaveType, null);
        }
    }

    /**
     * 查询工作区域
     */
    private void defect2SaveAndSubmitDatas(String submitAndSaveType, List<String> pic) {
        showProgressDialog(msg);
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));
            UserInfoBean userInfoBean = (UserInfoBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.USERINFO));

            Defect2SubmitRequestBean defect2SubmitRequestBean = new Defect2SubmitRequestBean();
            defect2SubmitRequestBean.setUid(loginBean.getUid());
            defect2SubmitRequestBean.setToken(loginBean.getToken());
            defect2SubmitRequestBean.setProject_id(projectId);
            defect2SubmitRequestBean.setCheck_id(mCheckId);
            defect2SubmitRequestBean.setContractor_id(userInfoBean.getContractor_id());

            defect2SubmitRequestBean.setDefect_type(mDefectType);
            defect2SubmitRequestBean.setDiscipline_id(mDisciplineId);
            defect2SubmitRequestBean.setTrade_id(mTradeId);
            defect2SubmitRequestBean.setCategory_id(mCategoryId);
            defect2SubmitRequestBean.setSub_con(mSubConId);

            defect2SubmitRequestBean.setBlock(mBlock);
            defect2SubmitRequestBean.setSecondary_region(mLevel);
            defect2SubmitRequestBean.setLocation(mZone);

            defect2SubmitRequestBean.setDeadline_time(mStartDateDay);
            defect2SubmitRequestBean.setUrgent_flag(urgentFlag);
            defect2SubmitRequestBean.setRemarks(mEtRemarks.getText().toString().trim());

            defect2SubmitRequestBean.setDrawing_id(mDrawingId);
            defect2SubmitRequestBean.setDrawing_pic_path(mDrawingPath);
            defect2SubmitRequestBean.setDrawing_position(mDrawingPosition);

            //拍照相册选择的检查图片
            StringBuilder picSubmit = new StringBuilder();
            if (pic != null) {
                for (int i = 0; i < pic.size(); i++) {
                    picSubmit.append(pic.get(i));
                    if (i != (pic.size() - 1)) {
                        picSubmit.append("|");
                    }
                }
            }
            defect2SubmitRequestBean.setPic(picSubmit.toString());//设置照片

            String json = GsonUtil.GsonString(defect2SubmitRequestBean);
            String submtAndSaveUrl;
            if (submitAndSaveType.equals("0")) {
                submtAndSaveUrl = NetApi.SaveDefect2Draft + "";
                Logger.e("CMSC0257--Defect2保存草稿--上传参数", json);
            } else {
                submtAndSaveUrl = NetApi.SubmitDefect2 + "";
                Logger.e("CMSC0258--Defect2提交--上传参数", json);
            }

            OkHttpUtils
                    .postString()
                    .url(submtAndSaveUrl)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            dismissProgressDialog();
                            String json = response.body().string();
                            if (submitAndSaveType.equals("0")) {
                                MyLog.loger("CMSC0257--Defect2保存草稿--返回值", json);
                            } else {
                                MyLog.loger("CMSC0258--Defect2提交--返回值", json);
                            }
                            return GsonUtil.GsonToBean(json,
                                    Defect2SubmitResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            Defect2SubmitResultBean resultBean = (Defect2SubmitResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            //判断是上拉加载还是点击的刷新按钮
                                            setResult(ConstantValue.Defect2SubmitAndSaveCode);
                                            Defect2ApplyActivity.this.finish();
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()==2", resultBean.getErrno() + "");
                                        Util.showDialogLogin(Defect2ApplyActivity.this);
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
                                showToast("UnFinished-Unknown Error");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void defect2QueryDetails() {
        showProgressDialog(msg);
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2QueryDetailsRequestBean requestBean = new Defect2QueryDetailsRequestBean();

            requestBean.setUid(loginBean.getUid());
            requestBean.setToken(loginBean.getToken());
            //如果是复制,使用复制的id,否则使用check_id
            if (!TextUtils.isEmpty(mCopyCheckId)) {
                requestBean.setCheck_id(mCopyCheckId);
            } else {
                requestBean.setCheck_id(mCheckId);
            }

            String json = GsonUtil.GsonString(requestBean);

            Logger.e("CMSC0259--查询检查单详情--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefect2Details + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            dismissProgressDialog();
                            String json = response.body().string();
                            MyLog.loger("CMSC0259--查询检查单详情--返回值", json);
                            String beReplacedStr = "\"action\":{}";
                            String toReplacedStr = "\"action\":[]";
                            String replace = json.replace(beReplacedStr, toReplacedStr);

                            String beReplacedStr1 = "\"detail\":{}";
                            String toReplacedStr1 = "\"detail\":[]";
                            String replace1 = replace.replace(beReplacedStr1, toReplacedStr1);
                            return GsonUtil.GsonToBean(replace1,
                                    Defect2QueryDetailsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @Override
                        public void onResponse(Object response, int i) {
                            Defect2QueryDetailsResultBean resultBean = (Defect2QueryDetailsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            setUIDetails(resultBean.getResult());
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()", resultBean.getErrno() + "");
                                        //Utils.showDialogLogin(getActivity());
                                        Util.showDialogLogin(Defect2ApplyActivity.this);
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

    private void setUIDetails(Defect2QueryDetailsResultBean.ResultBean result) {
//        private String msg = "", projectId = "", mCheckId = "", mStartDateDay = "", urgentFlag = "0";
//        private String mDefectType = "", mDisciplineId = "", mTradeId = "", mSubConId = "", mCategoryId = "";
//        private String mBlock = "", mLevel = "", mZone = "";
//        private String mDrawingId = "", mDrawingPath = "", mDrawingPosition = "";
//        private ArrayList<QueryDisciplineListsResultBean.ResultBean> mDisciplineLists;

        mBlock = result.getBlock();
        mLevel = result.getSecondary_region();
        mZone = result.getLocation();

        mDrawingId = result.getDrawing_id();
        mDrawingPath = result.getDrawing_pic_path();

        //如果是复制功能.不替换
        if (TextUtils.isEmpty(mCopyCheckId)) {
            mDrawingPosition = result.getDrawing_position();
        }

        // 显示discipline type
        mDisciplineId = result.getDiscipline_id();
        mTvDiscipline.setText(result.getDiscipline());

        //显示 type
        mTradeId = result.getTrade_id();
        mTvType.setText(result.getTrade());
        mSubConId = result.getSub_con();

        //显示 category
        mCategoryId = result.getCategory_id();
        mTvDefectCategory.setText(result.getCategory());

        //显示remark
        mEtRemarks.setText(result.getRemarks());
        //显示deadline
        mStartDateDay = result.getDeadline_time();
        String deadlineEn = DateFormatUtils.CNStr2ENStrNoTime(mStartDateDay);
        mTvDeadline.setText(deadlineEn);
        //显示urgent
        urgentFlag = result.getUrgent_flag();
        mTbFlag.setSelected(!urgentFlag.equals("0"));

        //显示图片
        if (mPhotosList.size() > 0) {
            mPhotosList.clear();
        }
        if (result.getPic().length() > 4) {
            DealPic(result.getPic());
        } else {
            AddPhotoBean bean = new AddPhotoBean();
            bean.setItemType(1);
            bean.setPhotoTitle("");
            bean.setPic("");
            mPhotosList.add(bean);
            mAddPhotoAdapter.notifyDataSetChanged();
        }
    }

    private String[] picArr;//图片的数量

    private void DealPic(String picStr) {
        //处理图片
        Logger.e("pic====", picStr);
        picArr = picStr.split("\\|");
        Logger.e("picArr====", picArr.length + "");
        for (String pic : picArr) {
            Logger.e("图片的地址+i==", NetApi.IMG_SHOW_URL + pic);
            DownLoadPic(NetApi.IMG_SHOW_URL + pic);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void DownLoadPic(String url) {
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Config.PHOTO_SRC, System.currentTimeMillis() + ".jpg") {
                    public void inProgress(float progress) {
                        Logger.e("inProgress==", progress + "");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.e("onError==", e.getMessage());
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(File file, int id) {
                        Logger.e("onResponse==", file.getAbsolutePath());
                        AddPhotoBean bean = new AddPhotoBean();
                        bean.setItemType(0);
                        bean.setPhotoTitle("");
                        bean.setPic(file.getAbsolutePath());
                        mPhotosList.add(bean);


                        //最大只支持9张图片
                        if (mPhotosList.size() == picArr.length) {
                            if (mPhotosList.size() < 6) {
                                AddPhotoBean bean1 = new AddPhotoBean();
                                bean1.setItemType(1);
                                bean1.setPhotoTitle("");
                                bean1.setPic("");
                                mPhotosList.add(bean1);
                                mAddPhotoAdapter.notifyDataSetChanged();
                            } else {
                                mAddPhotoAdapter.notifyDataSetChanged();
                            }
                            Logger.e("mPdfPhotosList.size()====", mPhotosList.size() + "");
                        }
                    }
                });
    }

    @Override
    public void onSuccess(String name, Object objectBean) {
        if (name.equals(NetApi.QueryDisciplineLists)) {
            QueryDisciplineListsResultBean resultBean =
                    (QueryDisciplineListsResultBean) objectBean;
            mDisciplineLists.addAll(resultBean.getResult());
        }
    }

    @Override
    public void onFaild(String name, String faildStr) {

    }
}