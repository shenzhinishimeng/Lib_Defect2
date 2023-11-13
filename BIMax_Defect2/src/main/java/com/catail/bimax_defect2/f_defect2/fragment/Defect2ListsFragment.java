package com.catail.bimax_defect2.f_defect2.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.activity.Defect2ApplyActivity;
import com.catail.bimax_defect2.f_defect2.activity.Defect2DetailsActivity;
import com.catail.bimax_defect2.f_defect2.adapter.Defect2ListAdapter;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2ListRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2ListResultBean;
import com.catail.lib_commons.base.BaseVisibleInitFragment;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MyLog;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Util;
import com.catail.lib_commons.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2ListsFragment extends BaseVisibleInitFragment implements BaseQuickAdapter.OnItemChildClickListener,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private SwipeRefreshLayout mSwiplayout;
    private List<QueryDefect2ListResultBean.ResultBean> mDataList;
    private Defect2ListAdapter rvListAdapter;
    private String msg = "", projectId = "";
    private String status = "", mDefectType = "";
    private int Defect2Page = 1;
    private boolean isRefresh = false;

    private String mBlockStr = "", mLevelStr = "", mZoneStr = "";
    private String mDisciplineId = "", mSubConId = "", isInitiatedByMe = "0";

    private EditText mEtKeywordsSearch;
    private String mSearchTags = "";
    private ImageView ivDelBtn;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_defect2_list;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initView(View view) {
        TextView tv_bottom_title = view.findViewById(R.id.tv_bottom_title);
        tv_bottom_title.setText(getActivity().getResources().getString(R.string.defect2_construction));

        mEtKeywordsSearch = view.findViewById(R.id.et_keywords_search);

        mSwiplayout = view.findViewById(R.id.swipe_refresh_layout);
        RecyclerView mRvList = view.findViewById(R.id.rv_list);

        final LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(OrientationHelper.VERTICAL);
        mRvList.setLayoutManager(lm);
        mDataList = new ArrayList<>();
        rvListAdapter = new Defect2ListAdapter(R.layout.adapter_inspection_list_item1, mDataList);
        mRvList.setAdapter(rvListAdapter);

//        mRvList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

        rvListAdapter.setOnItemChildClickListener(this);

        rvListAdapter.setOnLoadMoreListener(() -> {
            if (rvListAdapter.getData().size() >= 10) {
                QueryDefect2List();
            } else {
                rvListAdapter.loadMoreEnd(false);
            }
        }, mRvList);
//        mRvList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

        // 设置旋转背景色 ：设为白色
        mSwiplayout.setProgressBackgroundColorSchemeResource(R.color.white_background);
        mSwiplayout.setProgressViewOffset(true, 0, 80);
        mSwiplayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwiplayout.setOnRefreshListener(this);

        ivDelBtn = view.findViewById(R.id.iv_del);
        ivDelBtn.setOnClickListener(this);
        try {
            mEtKeywordsSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().trim().length() > 0) {
                        ivDelBtn.setVisibility(View.VISIBLE);
                    } else {
                        ivDelBtn.setVisibility(View.GONE);
                    }
                }
            });
            mEtKeywordsSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().trim().length() > 0) {
                        ivDelBtn.setVisibility(View.VISIBLE);
                    } else {
                        ivDelBtn.setVisibility(View.GONE);
                    }
                }
            });
            mEtKeywordsSearch.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 键盘搜索事件
                    // 查询数据.
                    mSearchTags = mEtKeywordsSearch.getText().toString();
                    onRefresh();
                    return true;
                }
                return false;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
//获取系统版本,然后显示中英文进度条文字
        int sysVersion = Utils.GetSystemCurrentVersion();
        if (sysVersion == 0) {
            msg = getString(R.string.processing);
        } else {
            msg = getString(R.string.processing);
        }
        projectId = getArguments().getString("projectId");
        String projectName = getArguments().getString("projectName");
        status = getArguments().getString("status");
        mDefectType = getArguments().getString("defect_type");

        QueryDefect2List();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.main) {
            if (status.equals("-1")) {
                //打开申请界面
                Intent intent = new Intent(getActivity(), Defect2ApplyActivity.class);
                intent.putExtra("check_id", mDataList.get(position).getCheck_id());
                intent.putExtra("defect_type", mDefectType);
                intent.putExtra("copy_check_id=","");//代表是否复制,如果有值代表复制,
                startActivityForResult(intent, ConstantValue.Defect2SubmitAndSaveCode);
            } else {
                //打开详情界面
                Intent intent = new Intent(getActivity(), Defect2DetailsActivity.class);
                intent.putExtra("check_id", mDataList.get(position).getCheck_id());
                startActivityForResult(intent, ConstantValue.Defect2SubmitAndSaveCode);
            }
        }
    }

    @Override
    public void onRefresh() {
        Defect2Page = 1;
        isRefresh = true;
        if (mDataList.size() > 0) {
            mDataList.clear();
        }
        QueryDefect2List();
    }


    private void QueryDefect2List() {
        showProgressDialog(msg);
        try {
            LoginBean loginBean = (LoginBean) Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));
            QueryDefect2ListRequestBean requestBean = new QueryDefect2ListRequestBean();
            requestBean.setUid(loginBean.getUid());
            requestBean.setToken(loginBean.getToken());
            requestBean.setProject_id(projectId);
            requestBean.setDefect_type(mDefectType);

            requestBean.setPage(Defect2Page + "");
            requestBean.setPagesize("10");
            requestBean.setStatus(status);//-1 草稿;0进行中; 6完成;9 onhold; 10 overdue;

            requestBean.setBlock(mBlockStr);
            requestBean.setLevel(mLevelStr);
            requestBean.setUnit(mZoneStr);

            requestBean.setApply_user_id(isInitiatedByMe);//是否查询自己申请的.
            requestBean.setDiscipline_id(mDisciplineId);
            requestBean.setContractor_id(mSubConId);//分包公司名称

            String json = GsonUtil.GsonString(requestBean);

            Logger.e("CMSC0261--查询Defect2列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefect2Lists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json).build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            dismissProgressDialog();
                            String json = response.body().string();
                            MyLog.loger("CMSC0261--查询Defect2列表--返回值", json);

                            String beReplacedStr = "\"result\":{}";
                            String toReplacedStr = "\"result\":[]";
                            String replace = json.replace(beReplacedStr, toReplacedStr);

                            return GsonUtil.GsonToBean(replace, QueryDefect2ListResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dismissProgressDialog();
                            e.printStackTrace();
                            Logger.e("onError", "onError");
                        }

                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Object response, int id) {
                            mSwiplayout.setRefreshing(false);
                            QueryDefect2ListResultBean resultBean = (QueryDefect2ListResultBean) response;
                            try {
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            //判断是上拉加载还是点击的刷新按钮
                                            if (isRefresh) {
                                                mDataList.clear();
                                                mDataList.addAll(resultBean.getResult());
                                                rvListAdapter.notifyDataSetChanged();
                                                isRefresh = false;
                                                Defect2Page += 1;
                                            } else {
                                                Defect2Page += 1;
                                                mDataList.addAll(resultBean.getResult());
                                                rvListAdapter.notifyDataSetChanged();
                                            }
                                            rvListAdapter.loadMoreComplete();
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno() == 2", resultBean.getErrno() + "");
                                        Util.showDialogLogin((AppCompatActivity) getActivity());
                                    } else if (resultBean.getErrno() == 7) {
                                        //无数据返回
//                                showToast(resultBean.getErrstr());
                                        rvListAdapter.notifyDataSetChanged();
                                        rvListAdapter.loadMoreEnd();
                                    } else {
                                        //其他错误代码
//                                showToast(resultBean.getErrstr());
                                        Logger.e("其他错误代码");
                                    }
                                } else {
//                            showToast(getResources().getString(R.string.no_data));
                                    Logger.e("无数据");
                                }
                            } catch (Exception e) {
//                        showToast("UnFinished-Unknown Error");
                                Logger.e("异常");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void reFreshData(String mBlockStr, String mLevelStr, String mZoneStr,
                            String mDisciplineId, String mSubConId, String isInitiatedByMe) {
        this.mBlockStr = mBlockStr;
        this.mLevelStr = mLevelStr;
        this.mZoneStr = mZoneStr;
        this.mDisciplineId = mDisciplineId;
        this.mSubConId = mSubConId;
        this.isInitiatedByMe = isInitiatedByMe;

        Defect2Page = 1;
        isRefresh = true;
        if (mDataList.size() > 0) {
            mDataList.clear();
        }
        QueryDefect2List();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.e("onActivityResult==1111111111");
        Logger.e("requestCode==" + requestCode);
        Logger.e("resultCode==" + resultCode);

        if (requestCode == ConstantValue.InspectionListFresh
                && resultCode == ConstantValue.InspectionListFresh) {
            onRefresh();
        } else if (requestCode == ConstantValue.Defect2SubmitAndSaveCode
                && resultCode == ConstantValue.Defect2SubmitAndSaveCode) {
            onRefresh();
        }
    }


    @Override
    public void onClick(View v) {
        //删除
        if (v.getId() == R.id.iv_del) {
            //删除
            mEtKeywordsSearch.setText("");
            ivDelBtn.setVisibility(View.GONE);
        }
    }
}
