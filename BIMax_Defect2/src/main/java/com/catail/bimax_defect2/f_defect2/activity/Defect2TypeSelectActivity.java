package com.catail.bimax_defect2.f_defect2.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2TypeListAdapter;
import com.catail.bimax_defect2.f_defect2.bean.Defect2ApplyListRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2TypeListResultBean;
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

public class Defect2TypeSelectActivity extends BaseActivity implements View.OnClickListener {
    private List<QueryDefect2TypeListResultBean.ResultBean> mDataList;
    private Defect2TypeListAdapter rvListAdapter;
    private String msg = "", projectId = "", mDisciplineId = "";
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
        tvTitle.setText(R.string.defect2_please_select_type);

        ImageView leftBtn = findViewById(R.id.title_bar_left_menu);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(this);

        RecyclerView mRvList = findViewById(R.id.rv_list);

        final LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(RecyclerView.VERTICAL);
        mRvList.setLayoutManager(lm);
        mDataList = new ArrayList<>();
        rvListAdapter = new Defect2TypeListAdapter(R.layout.adapter_defect2_type_list_item, mDataList);
        mRvList.setAdapter(rvListAdapter);


        rvListAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            Intent intent = new Intent();
            intent.putExtra("trade_id",mDataList.get(position).getTrade_id().toString());
            intent.putExtra("trade_name",mDataList.get(position).getTrade());
            intent.putExtra("sub_con_id",mDataList.get(position).getSub_con());
            setResult(ConstantValue.Defect2TypeSelectCode, intent);
            Defect2TypeSelectActivity.this.finish();
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
        mBlock = getIntent().getStringExtra("block");
        mLevel = getIntent().getStringExtra("level");
        mZone = getIntent().getStringExtra("zone");

        QueryDefectTypeList();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_left_menu) {
            Defect2TypeSelectActivity.this.finish();
        }
    }


    private void QueryDefectTypeList() {
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

            requestBean.setBlock(mBlock);
            requestBean.setLevel(mLevel);
            requestBean.setUnit(mZone);

            String json = GsonUtil.GsonString(requestBean);
            Logger.e("CMSC0254--获取Trade,承包商对应关系--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryTradeLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            dismissProgressDialog();
                            String json = response.body().string();
                            Logger.e("CMSC0254--获取Trade,承包商对应关系--返回值", json);


                            String beReplacedStr = "\"result\":{}";
                            String toReplacedStr = "\"result\":[]";
                            String replace = json.replace(beReplacedStr, toReplacedStr);


                            return GsonUtil.GsonToBean(replace,
                                    QueryDefect2TypeListResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDefect2TypeListResultBean resultBean = (QueryDefect2TypeListResultBean) response;
                            try {
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            mDataList.addAll(resultBean.getResult());
                                            rvListAdapter.notifyDataSetChanged();
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno() == 2", resultBean.getErrno() + "");
                                        Util.showDialogLogin(Defect2TypeSelectActivity.this);
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