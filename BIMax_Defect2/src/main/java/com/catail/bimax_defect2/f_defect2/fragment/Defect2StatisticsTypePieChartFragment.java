package com.catail.bimax_defect2.f_defect2.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.adapter.InspectionStatisticsTypeHomeAdapter;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2StatisticsListsRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2StatisticsTypePieChartListsResult;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.base.BaseVisibleInitFragment;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MyLog;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Util;
import com.catail.lib_commons.utils.Utils;
import com.catail.lib_commons.view.PieChartView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2StatisticsTypePieChartFragment extends BaseVisibleInitFragment {
    private List<QueryDefect2StatisticsTypePieChartListsResult.ResultBean> mDataList;
    private InspectionStatisticsTypeHomeAdapter rvListAdapter;
    private String projectId = "", status = "", mDefectType = "";
    private PieChartView pieChartView;
    //    private PieChart mInsChart;
    private TextView tv_total, tv_statistic_title;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_inspection_statistics_home;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initView(View view) {
        tv_total = view.findViewById(R.id.tv_total);
        tv_statistic_title = view.findViewById(R.id.tv_statistic_title);

        RecyclerView mRvList = view.findViewById(R.id.rv_list);
        final LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(OrientationHelper.VERTICAL);
        mRvList.setLayoutManager(lm);
        mDataList = new ArrayList<>();
        rvListAdapter = new InspectionStatisticsTypeHomeAdapter(
                R.layout.adapter_inspection_statistics_home_item, mDataList);
        mRvList.setAdapter(rvListAdapter);

        //统计图.
        PieChart mInsChart = view.findViewById(R.id.ins_chart);
        Legend legend = mInsChart.getLegend();
        legend.setEnabled(false);

        mInsChart.setDrawEntryLabels(false);
        pieChartView = new PieChartView(getActivity(), mInsChart);

        mInsChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Logger.e(" e==" + e.toString());
                Logger.e(" h==" + h.toString());
//                int value = (int) h.getY();
//                Logger.e("value=="+value);
//                Logger.e("totalCnt=="+totalCnt);
//                mInsChart.setCenterText((value/100*totalCnt) + "");
                String xPositionStr = h.getX() + "";
                String xPosition = xPositionStr.substring(0, xPositionStr.lastIndexOf("."));
                Logger.e("xPosition==" + xPosition);
                mInsChart.setCenterText(mDataList.get(Integer.parseInt(xPosition)).getCnt() + "");
                mInsChart.setCenterTextSize(20);
                mInsChart.invalidate();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    public void initData() {
//        int sysVersion = Utils.GetSystemCurrentVersion();

        projectId = getArguments().getString("projectId");
//        String projectName = getArguments().getString("projectName");
        status = getArguments().getString("status");
        mDefectType = getArguments().getString("mDefectType");

        initPieChartTitle();

        QueryDefect2StatisticsLists();
    }

    private void initPieChartTitle() {
        if (status.equals("7")) {
            tv_statistic_title.setText(getResources().getString(R.string.defect2_statistics_title_7));
        } else if (status.equals("8")) {
            tv_statistic_title.setText(getResources().getString(R.string.defect2_statistics_title_8));
        } else if (status.equals("9")) {
            tv_statistic_title.setText(getResources().getString(R.string.defect2_statistics_title_9));
        } else if (status.equals("10")) {
            tv_statistic_title.setText(getResources().getString(R.string.defect2_statistics_title_10));
        }
    }


    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void showStatusPieChartUI(List<QueryDefect2StatisticsTypePieChartListsResult.ResultBean> result) {
        mDataList.addAll(result);
        rvListAdapter.notifyDataSetChanged();

        if (mDataList.size() > 0) {

            ArrayList<String> typeNameLists = new ArrayList<>();
            ArrayList<Integer> stringList = new ArrayList<>();

            float totalCnt = 0;
            for (int i = 0; i < mDataList.size(); i++) {
                totalCnt += mDataList.get(i).getCnt();
            }

            for (int i = 0; i < mDataList.size(); i++) {
                typeNameLists.add(mDataList.get(i).getCategory());
                double calcCnt = Utils.Keep1Decimal(mDataList.get(i).getCnt() / totalCnt * 10000);
                Logger.e("calcCnt==" + calcCnt);
//            stringList.add(mDataList.get(i).getCnt());
                String[] split = String.valueOf(calcCnt).split("\\.");
                Logger.e("split=" + split[0]);
                stringList.add(Integer.parseInt(split[0]));

            }
            Logger.e("stringList==" + stringList.size());
            Logger.e("typeNameLists==" + typeNameLists.size());

            String[] totalArr = (totalCnt + "").split("\\.");

            //设置total
            tv_total.setText(getResources().getString(R.string.members_total) + " " + totalArr[0]);

            //设置 统计图
            if (stringList.size() < 5) {
                pieChartView.setData((BaseActivity) getActivity(), stringList.size(),
                        100f, stringList, typeNameLists, mDataList.get(0).getCnt() + "", "Inspection_status");
            } else {
                pieChartView.setData((BaseActivity) getActivity(), stringList.size(),
                        100f, stringList, typeNameLists, mDataList.get(0).getCnt() + "", "Inspection_status");
            }
        }

    }

    private void QueryDefect2StatisticsLists() {
        try {
            LoginBean loginBean = (LoginBean) Utils
                    .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            QueryDefect2StatisticsListsRequestBean qaRequestBean
                    = new QueryDefect2StatisticsListsRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);
            qaRequestBean.setBes_project_id("");
            qaRequestBean.setFlag(status);
            qaRequestBean.setDefect_type(mDefectType);

            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0264--Dashboard统计--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefect2StatisticsLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0264--status----返回值", jsonStr);
                            String beReplacedStr = "\"result\":{}";
                            String toReplacedStr = "\"result\":[]";
                            String replace = jsonStr.replace(beReplacedStr, toReplacedStr);
                            return GsonUtil.GsonToBean(replace,
                                    QueryDefect2StatisticsTypePieChartListsResult.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("CMSC0376-Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            try {
                                QueryDefect2StatisticsTypePieChartListsResult resultBean
                                        = (QueryDefect2StatisticsTypePieChartListsResult) response;
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            showStatusPieChartUI(resultBean.getResult());
                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()==2", resultBean.getErrno() + "");
                                        Util.showDialogLogin((AppCompatActivity) getActivity());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
//                                showToast("UnFinished-Unknown Error");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
