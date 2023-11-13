package com.catail.bimax_defect2.f_defect2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2CategoryListAdapter;
import com.catail.bimax_defect2.f_defect2.bean.CreateNewCategoryResultBean;
import com.catail.bimax_defect2.f_defect2.bean.Defect2ApplyListRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2CategoryListRequestBean;
import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Util;
import com.catail.lib_commons.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2CategorySelectActivity extends BaseActivity implements View.OnClickListener {
    private List<QueryDefect2CategoryListRequestBean.ResultBean> mDataList;
    private Defect2CategoryListAdapter rvListAdapter;
    private String msg = "", projectId = "", mDisciplineId = "", mTradeId = "";
    private String mDefectType = "", mBlock = "", mLevel = "", mZone = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_defect2_type_select;
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public void initView() {
        CommonsApplication.activityList.add(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(R.string.defect2_category);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        ImageView addBtn = findViewById(R.id.title_bar_right1);
        addBtn.setVisibility(View.VISIBLE);
        addBtn.setImageResource(R.mipmap.add_btn_blue);
        addBtn.setOnClickListener(this);


        RecyclerView mRvList = findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(RecyclerView.VERTICAL);
        mRvList.setLayoutManager(lm);
        mDataList = new ArrayList<>();
        rvListAdapter = new Defect2CategoryListAdapter(R.layout.adapter_defect2_category_list_item, mDataList);
        mRvList.setAdapter(rvListAdapter);


        rvListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.putExtra("category_id", mDataList.get(position).getCategory_id().toString());
            intent.putExtra("category_name", mDataList.get(position).getCategory());
            setResult(ConstantValue.Defect2CategorySelectCode, intent);
            Defect2CategorySelectActivity.this.finish();
        });

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
        projectId = getIntent().getStringExtra("projectId");
        mDefectType = getIntent().getStringExtra("defectType");
        mDisciplineId = getIntent().getStringExtra("disciplineId");
        mTradeId = getIntent().getStringExtra("tradeId");
        mBlock = getIntent().getStringExtra("block");
        mLevel = getIntent().getStringExtra("level");
        mZone = getIntent().getStringExtra("zone");
        QueryDefect2CategoryList();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_menu) {
            Defect2CategorySelectActivity.this.finish();
        } else if (v.getId() == R.id.title_bar_right1) {
            showNewCategoryDialog();
        }
    }


    public void showNewCategoryDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_storage_marking, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(Defect2CategorySelectActivity.this);
        builder.setView(view);
        TextView tvDialogTitle = view.findViewById(R.id.tv_title);
        tvDialogTitle.setText(getResources().getString(R.string.defect2_category));

        final EditText et_keywords_search = view.findViewById(R.id.et_keywords_search);

        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvDone = view.findViewById(R.id.tv_done);

        final AlertDialog dialog = builder.show();

        Utils.setAlertDialogConner(dialog);
        Utils.setAlertDialogSize(Defect2CategorySelectActivity.this, dialog, 0.88);


        tvCancel.setOnClickListener(v -> dialog.dismiss());
        tvDone.setOnClickListener(v -> {

            String newCategoryStr = et_keywords_search.getText().toString().trim();
            CreateNewCategory(newCategoryStr);
            dialog.dismiss();
        });

    }


    private void QueryDefect2CategoryList() {
        showProgressDialog(msg);
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2ApplyListRequestBean requestBean = new Defect2ApplyListRequestBean();
            requestBean.setUid(loginBean.getUid());
            requestBean.setToken(loginBean.getToken());
            requestBean.setProject_id(projectId);

            requestBean.setDefect_type(mDefectType);
            requestBean.setDiscipline_id(mDisciplineId);
            requestBean.setTrade_id(mTradeId);

            requestBean.setBlock(mBlock);
            requestBean.setLevel(mLevel);
            requestBean.setUnit(mZone);

            String json = GsonUtil.GsonString(requestBean);
            Logger.e("CMSC0255--查询 Category列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryCategoryLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            dismissProgressDialog();
                            String json = response.body().string();
                            Logger.e("CMSC0255--查询 Category列表--返回值", json);

                            String beReplacedStr = "\"result\":{}";
                            String toReplacedStr = "\"result\":[]";
                            String replace = json.replace(beReplacedStr, toReplacedStr);


                            return GsonUtil.GsonToBean(replace,
                                    QueryDefect2CategoryListRequestBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDefect2CategoryListRequestBean resultBean = (QueryDefect2CategoryListRequestBean) response;
                            try {
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            if (mDataList.size() > 0) {
                                                mDataList.clear();
                                            }
                                            mDataList.addAll(resultBean.getResult());
                                            rvListAdapter.notifyDataSetChanged();
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno() == 2", resultBean.getErrno() + "");
                                        Util.showDialogLogin(Defect2CategorySelectActivity.this);
                                    } else if (resultBean.getErrno() == 7) {
                                        //无数据返回
                                        showToast(resultBean.getErrstr());
                                    } else {
                                        showToast(resultBean.getErrstr());
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


    private void CreateNewCategory(String newCategoryStr) {
        showProgressDialog(msg);
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2ApplyListRequestBean requestBean = new Defect2ApplyListRequestBean();
            requestBean.setUid(loginBean.getUid());
            requestBean.setToken(loginBean.getToken());
            requestBean.setProject_id(projectId);

            requestBean.setDefect_type(mDefectType);
            requestBean.setDiscipline_id(mDisciplineId);

            requestBean.setCategory(newCategoryStr);

            String json = GsonUtil.GsonString(requestBean);
            Logger.e("CMSC0256--创建新的Category--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.CreateNewCategory + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            dismissProgressDialog();
                            String jsonStr = response.body().string();
                            Logger.e("CMSC0256--创建新的Category--返回值", jsonStr);

                            return GsonUtil.GsonToBean(jsonStr,
                                    CreateNewCategoryResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            CreateNewCategoryResultBean resultBean = (CreateNewCategoryResultBean) response;
                            try {
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            QueryDefect2CategoryList();
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno() == 2", resultBean.getErrno() + "");
                                        Util.showDialogLogin(Defect2CategorySelectActivity.this);
                                    } else if (resultBean.getErrno() == 7) {
                                        //无数据返回
                                        showToast(resultBean.getErrstr());
                                    } else {
                                        showToast(resultBean.getErrstr());
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
}