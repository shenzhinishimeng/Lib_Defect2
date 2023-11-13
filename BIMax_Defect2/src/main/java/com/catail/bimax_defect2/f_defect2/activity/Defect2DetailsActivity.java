package com.catail.bimax_defect2.f_defect2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2ActionAdapter;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2DetailsProgressListAdapter;
import com.catail.bimax_defect2.f_defect2.bean.Defect2QueryDetailsRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2QueryDetailsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2SendSubmitRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2SendSubmitResultBean;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.activity.UseCameraActivity;
import com.catail.lib_commons.adapter.AddPhotoAdapter;
import com.catail.lib_commons.api.UploadApi;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.AddPhotoBean;
import com.catail.lib_commons.bean.DMDLPPinPoint;
import com.catail.lib_commons.bean.DataSuccessBean;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.bean.ProjectAndPermissionBean;
import com.catail.lib_commons.bean.QueryDMDLPDefectListDetailsResultBean;
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
import com.catail.lib_commons.view.RectPinView;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hzw.doodle.DoodleActivity;
import es.voghdev.pdfviewpager.library.subscaleview.ImageSource;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2DetailsActivity extends BaseActivity implements View.OnClickListener {
    private List<Defect2QueryDetailsResultBean.ResultBean.DetailBean> mProgressDataList;
    private Defect2DetailsProgressListAdapter mProgressListAdapter;
    private String msg = "", projectId = "", mCheckId = "", mActionId = "", mActionType = "",
            mStartDateDay = "";
    private TextView mTvTypeName, mTvSubName, mTvLocationName, mTvRefNo, mTvDisciplineName,
            mTvCategoryName, mTvDeadlineName, mTvAction, mTvDeadline;
    private EditText mEtRemarks;
    private LinearLayout mllReplyContent;
    private RelativeLayout rl_deadline_content;

    private RectPinView mPinDetailsView;
    private final List<PointF> mPinLists = new ArrayList<>();
    private final List<DMDLPPinPoint> mPointLists = new ArrayList<>();

    private List<AddPhotoBean> mPhotosList;//附件区域图片集合
    private AddPhotoAdapter mAddPhotoAdapter;//附件区域添加图片的适配器

    private List<Defect2QueryDetailsResultBean.ResultBean.ActionBean> mAcitonLists;
    private File mDrawingPicFile;//项目,block,level,unit的图纸pic本地文件
    private final List<QueryDMDLPDefectListDetailsResultBean.ResultBean> mDefectDataList = new ArrayList<>();

    private UploadApi uploadApi;// 图片上传
    private TextView mTvTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_defect2_details;
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        mTvTitle = findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.defect2_title);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        mPinDetailsView = findViewById(R.id.pin_details_view);

        mTvRefNo = findViewById(R.id.tv_ref_no);
        mTvLocationName = findViewById(R.id.tv_location_name);
        mTvDisciplineName = findViewById(R.id.tv_discipline_name);
        mTvTypeName = findViewById(R.id.tv_type_name);
        mTvCategoryName = findViewById(R.id.tv_category_name);
        mTvSubName = findViewById(R.id.tv_sub_name);
        mTvDeadlineName = findViewById(R.id.tv_deadline_name);
        mEtRemarks = findViewById(R.id.et_remarks);
        mllReplyContent = findViewById(R.id.ll_reply_content);
        mTvAction = findViewById(R.id.tv_action);
        TextView tv_send = findViewById(R.id.tv_send);
        mTvAction.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        RecyclerView mRvProgressList = findViewById(R.id.rv_progress_list);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(RecyclerView.VERTICAL);
        mRvProgressList.setLayoutManager(lm);
        mProgressDataList = new ArrayList<>();
        mProgressListAdapter = new Defect2DetailsProgressListAdapter(
                R.layout.adapter_defect2_details_progress_list_item, mProgressDataList);
        mRvProgressList.setAdapter(mProgressListAdapter);

        mProgressListAdapter.notifyDataSetChanged();

        RecyclerView rvPhotoView = findViewById(R.id.rv_photo_view);
        mPhotosList = new ArrayList<>();
        final GridLayoutManager glm = new GridLayoutManager(Defect2DetailsActivity.this, 3);
        rvPhotoView.setLayoutManager(glm);
        mAddPhotoAdapter = new AddPhotoAdapter(mPhotosList, Defect2DetailsActivity.this, 9);
        rvPhotoView.setAdapter(mAddPhotoAdapter);
        mAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            if (id == R.id.iv_photo) {
                Logger.e("图片编辑", "图片编辑");
                AddPhotoBean addPhotoBean = mPhotosList.get(position);
                String imageEditorPic = addPhotoBean.getPic();
                PhotoUtils.ImageEditor(Defect2DetailsActivity.this, imageEditorPic, position);
            } else if (id == R.id.iv_del_photo) {
                Logger.e("删除照片", "删除照片");
                mPhotosList.remove(position);
                mAddPhotoAdapter.notifyDataSetChanged();
            } else if (id == R.id.iv_add_photo) {
                Logger.e("添加照片", "添加照片");
                // 点击事件,弹出对话框选择,本地相册,拍照
                PhotoUtils.showPhotoSelectionDialog(Defect2DetailsActivity.this, 9);
            }
        });
        rvPhotoView.setNestedScrollingEnabled(false);

        mAcitonLists = new ArrayList<>();

        rl_deadline_content = findViewById(R.id.rl_deadline_content);
        mTvDeadline = findViewById(R.id.tv_deadline);
        rl_deadline_content.setOnClickListener(this);
    }


    @SuppressLint("NotifyDataSetChanged")
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

            AddPhotoBean bean = new AddPhotoBean();
            bean.setItemType(1);
            bean.setPhotoTitle("");
            bean.setPic("");
            mPhotosList.add(bean);
            mAddPhotoAdapter.notifyDataSetChanged();

            defect2QueryDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_menu) {
            Defect2DetailsActivity.this.finish();
        } else if (v.getId() == R.id.tv_action) {
            showActionListDialog();
        } else if (v.getId() == R.id.rl_deadline_content) {
            showDateDialog();
        } else if (v.getId() == R.id.tv_send) {
            if (TextUtils.isEmpty(mActionId)) {
                showToast(getResources().getString(R.string.defect2_please_select_action));
                return;
            }

            List<String> photoLists = new ArrayList<>();

            for (int i = 0; i < mPhotosList.size(); i++) {
                if (mPhotosList.get(i).getItemType() == 0) {
                    photoLists.add(mPhotosList.get(i).getPic());
                }
            }
            if (photoLists.size() > 0) {
                if (uploadApi == null) {
                    showProgressDialog(msg);
                    uploadApi = new UploadApi();
                }
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

                                    defect2SendSubmit(photoList1);
                                } else if (object instanceof DataSuccessBean) {
                                    DataSuccessBean dataSuccessBean = (DataSuccessBean) object;
                                    Logger.e("dataSuccessBean.toString()=", dataSuccessBean.toString());
                                    /* 图片上上传失败 */
                                    Common.showToast(Defect2DetailsActivity.this, getString(R.string.picture_fail));
                                }
                            } catch (Exception e) {
                                dismissProgressDialog();
                                Logger.e("Exception", "Exception");
                                e.printStackTrace();
                                Logger.e("error", "异常：" + e.getMessage());
                                /* 图片上上传失败 */
                                Common.showToast(Defect2DetailsActivity.this, getString(R.string.picture_fail));
                            }
                        });
                    }

                    @Override
                    public void OnFail(String error, String failType) {
                        dismissProgressDialog();
                        /* 图片上上传失败 */
                        Common.showToast(Defect2DetailsActivity.this, getString(R.string.picture_fail));
                    }
                });
            } else {
                defect2SendSubmit(null);
            }
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(Defect2DetailsActivity.this);
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
        Utils.setAlertDialogSize(Defect2DetailsActivity.this, Datedialog, 0.96);
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

    private void showActionListDialog() {
        AlertDialog typeDialog =
                new AlertDialog.Builder(Defect2DetailsActivity.this).create();
        View view = Defect2DetailsActivity.this.getLayoutInflater().inflate(R.layout.list_dialog2, null);
        TextView titleText = view.findViewById(R.id.tv_title);
        titleText.setText(R.string.checklist_action);
        RecyclerView rvlistView = view.findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(Defect2DetailsActivity.this);
        lm.setOrientation(RecyclerView.VERTICAL);
        rvlistView.setLayoutManager(lm);
        Defect2ActionAdapter rvListAdapter =
                new Defect2ActionAdapter(R.layout.adapter_checklist_create_by_item, mAcitonLists);
        rvlistView.setAdapter(rvListAdapter);
        rvListAdapter.setOnItemChildClickListener((baseQuickAdapter, view1, i) -> {
            if (view1.getId() == R.id.rl_content) {
                mActionId = mAcitonLists.get(i).getNode_id();
                mActionType = mAcitonLists.get(i).getAction_type();
                mTvAction.setText(mAcitonLists.get(i).getName_en());
                typeDialog.dismiss();
            }
        });

        typeDialog.setView(view);
        typeDialog.show();
        Utils.setAlertDialogConner(typeDialog);
        Utils.setAlertDialogSize(Defect2DetailsActivity.this, typeDialog, 0);
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
            PhotoUtils.ImageLubanCompression(Defect2DetailsActivity.this,
                    picStrLists, mPhotosList, mAddPhotoAdapter);
//            PhotoUtils.TaskPhotoResult(DMDLPApplyActivity.this, PhotoUtils.captureFileUri,
//                    mPhotosList, mAddPhotoAdapter);
        } else if (requestCode == ConstantValue.CopyWeChatImageSelector && data != null) {
            try {
                //获取选择器返回的数据
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                Logger.e("images", images.toString());
                PhotoUtils.ImageLubanCompression(Defect2DetailsActivity.this, images,
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
        }
    }


    private void showPinView(String defect_no, String drawing_id, String drawing_pic_path,
                             String drawing_version, String drawing_name, String drawing_position) {
        if (!TextUtils.isEmpty(drawing_pic_path)) {
            //判断 文件存不存在, 版本号+drawing_id+文件名称.
            String projectPicName = drawing_version + "_" + drawing_id + "_" + drawing_name;
//            String projectPicUrl = NetApi.IMG_SHOW_URL + drawing_pic_path;

            mDrawingPicFile = new File(Config.SDStorageCacheInspectionPic
                    + "/" + projectPicName);
            if (mDrawingPicFile.exists()) {
                //存在 显示坐标点
                Logger.e("projectPicFile--文件存在");
                //打开图纸 ,显示标点.
                openDrawingPic();
                showAllPins(defect_no, drawing_id, drawing_name, drawing_position, drawing_pic_path,
                        drawing_version);
            } else {
                // 不存在下载,
//                Logger.e("projectPicFile--文件不存在");
                downloadDrawingPic(defect_no, drawing_id, drawing_name, drawing_position, drawing_pic_path,
                        drawing_version);
            }
        }
    }

    /**
     * 打开图纸
     */
    private void openDrawingPic() {
//        iv_empty.setVisibility(View.GONE);
        //打开图纸.
        ImageSource uri = ImageSource.uri(mDrawingPicFile.getPath());
        mPinDetailsView.setImage(uri);
    }

    public void showAllPins(String defect_no, String drawing_id, String drawing_name,
                            String drawing_position, String drawing_pic_path, String drawing_version) {
        if (mDefectDataList.size() > 0) {
            mDefectDataList.clear();
        }
        if (mPointLists.size() > 0) {
            mPointLists.clear();
        }
        QueryDMDLPDefectListDetailsResultBean.ResultBean bean = new QueryDMDLPDefectListDetailsResultBean.ResultBean();
        //点的信息.
        String[] posionArr = drawing_position.split(",");

        DMDLPPinPoint dmdlpPinPoint = new DMDLPPinPoint();
        dmdlpPinPoint.setPin_x(Integer.parseInt(posionArr[0]));
        dmdlpPinPoint.setPin_y(Integer.parseInt(posionArr[1]));

        dmdlpPinPoint.setPin_pos(Integer.parseInt(drawing_id));
        dmdlpPinPoint.setDrawing_id(Integer.parseInt(drawing_id));
        dmdlpPinPoint.setDrawing_name(drawing_name);
        dmdlpPinPoint.setUnit("");
        dmdlpPinPoint.setDrawing_path(drawing_pic_path);
        dmdlpPinPoint.setDrawing_position(drawing_position);

        dmdlpPinPoint.setZone(defect_no);
        //设置点
        bean.setStatus(0);
        bean.setPin_x(Integer.parseInt(posionArr[0]));
        bean.setPin_y(Integer.parseInt(posionArr[1]));
        bean.setPin_pos(drawing_id);
        bean.setDrawing_id(Integer.parseInt(drawing_id));
        bean.setDrawing_name(drawing_name);

        bean.setDrawing_path(drawing_pic_path);
        bean.setDrawing_position(drawing_position);
        bean.setZone(defect_no);
        bean.setDrawing_version(drawing_version);

        mDefectDataList.add(bean);
        mPointLists.add(dmdlpPinPoint);

        Logger.e("dmdlpPinPoint====" + dmdlpPinPoint);
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");
        Logger.e("PointLists.size()====" + mPointLists.size() + "");
        Logger.e("mDefectDataList.size()==" + mDefectDataList.size() + "");

        showAllPin();//block
    }

    private void downloadDrawingPic(String defect_no, String drawing_id, String drawing_name,
                                    String drawing_position, String drawing_pic_path, String drawing_version) {
        showProgressDialog(msg);
        String drawingPicName = drawing_version + "_" + drawing_id + "_" + drawing_name;
        OkHttpUtils//
                .get()//
                .url(NetApi.IMG_SHOW_URL + drawing_pic_path)//
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
                            //打开图纸 ,显示标点.
                            openDrawingPic();
                            showAllPins(defect_no, drawing_id, drawing_name, drawing_position, drawing_pic_path,
                                    drawing_version);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void showAllPin() {
        Logger.e("----showAllPin----");
        if (mPinLists.size() > 0) {
            mPinLists.clear();
        }
        for (int i = 0; i < mPointLists.size(); i++) {
            // 这里面写判断, 如何多个颜色
            PointF center = new PointF(Float.parseFloat((mPointLists.get(i).getPin_x() + "")),
                    (int) Float.parseFloat(mPointLists.get(i).getPin_y() + ""));
            mPinLists.add(center);
//            pin_view.setPins(PinLists);
        }
        Logger.e("------------------------------");
        Logger.e("PinLists==" + mPinLists.size());
        Logger.e("------------------------------");

        mPinDetailsView.setAllTypePins(mPinLists, mDefectDataList);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUIDetails(Defect2QueryDetailsResultBean.ResultBean result) {
        try {
            //判断一下如果图纸没有,先下载图纸.
            String drawing_pic_path = result.getDrawing_pic_path();
            String file_name = drawing_pic_path.substring(drawing_pic_path.lastIndexOf("/") + 1);
            String drawing_name = file_name.substring(0, file_name.length() - 4);
            Logger.e("drawing_name=" + drawing_name);

            String defect_no = result.getDefect_no();
            mTvTitle.setText(defect_no);
            String drawing_id = result.getDrawing_id();
            String drawing_version = "1";
            String drawing_position = result.getDrawing_position();

            showPinView(defect_no, drawing_id, drawing_pic_path, drawing_version,
                    drawing_name, drawing_position);
            //显示type,分包公司, 截止时间,
            mTvRefNo.setText(result.getCheck_id());
            String location = result.getBlock() + "-" + result.getSecondary_region() + "-" + result.getLocation();
            mTvLocationName.setText(location);
            mTvDisciplineName.setText(result.getDiscipline());
            mTvTypeName.setText(result.getTrade());
            mTvCategoryName.setText(result.getCategory());
            mTvSubName.setText(result.getSub_con());
            String deadlineTimeEn = DateFormatUtils.CNStr2ENStrNoTime(result.getDeadline_time());
            mTvDeadlineName.setText(deadlineTimeEn);
            //显示列表.
            mProgressListAdapter.setStatus(result.getStatus());

            mProgressDataList.addAll(result.getDetail());
            mProgressListAdapter.notifyDataSetChanged();
            //如果action为空就没有权限,不显示
//            ProjectAndPermissionBean projectAndPermissionBean =
//                    (ProjectAndPermissionBean) Utils.stringToObject(
//                            Preference.getSysparamFromSp(Config.PROJECT_PERMISSION));
//
//            String groupType = projectAndPermissionBean.getGroup_type();
            if (result.getAction().size() > 0) {
                mAcitonLists.addAll(result.getAction());

                mllReplyContent.setVisibility(View.VISIBLE);
                if (result.getDeadline_flag().equals("1")) {
                    rl_deadline_content.setVisibility(View.VISIBLE);
                } else {
                    rl_deadline_content.setVisibility(View.GONE);
                }
            } else {
                mllReplyContent.setVisibility(View.GONE);
            }
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
            requestBean.setCheck_id(mCheckId);

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
                                        Util.showDialogLogin(Defect2DetailsActivity.this);
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


    private void defect2SendSubmit(List<String> picLists) {
        showProgressDialog(msg);
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2SendSubmitRequestBean requestBean = new Defect2SendSubmitRequestBean();

            requestBean.setUid(loginBean.getUid());
            requestBean.setToken(loginBean.getToken());
            requestBean.setProject_id(projectId);
            requestBean.setCheck_id(mCheckId);

            requestBean.setDeadline_time(mStartDateDay);

            requestBean.setNode_id(mActionId);
            requestBean.setAction_type(mActionType);

            requestBean.setRemarks(mEtRemarks.getText().toString().trim());

            //拍照相册选择的检查图片
            StringBuilder picSubmit = new StringBuilder();
            if (picLists != null) {
                for (int i = 0; i < picLists.size(); i++) {
                    picSubmit.append(picLists.get(i));
                    if (i != (picLists.size() - 1)) {
                        picSubmit.append("|");
                    }
                }
            }
            requestBean.setPic(picSubmit.toString());
            String json = GsonUtil.GsonString(requestBean);

            Logger.e("CMSC0262--defect详情提交操作--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.Defect2DetailsSubmit + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            dismissProgressDialog();
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0262--defect详情提交操作--返回值", jsonStr);
                            return GsonUtil.GsonToBean(jsonStr,
                                    Defect2SendSubmitResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @Override
                        public void onResponse(Object response, int i) {
                            Defect2SendSubmitResultBean resultBean = (Defect2SendSubmitResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            setResult(ConstantValue.Defect2SubmitAndSaveCode);
                                            Defect2DetailsActivity.this.finish();
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()", resultBean.getErrno() + "");
                                        //Utils.showDialogLogin(getActivity());
                                        Util.showDialogLogin(Defect2DetailsActivity.this);
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