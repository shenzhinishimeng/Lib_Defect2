package com.catail.bimax_defect2.f_defect2.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catail.bimax_defect2.R
import com.catail.bimax_defect2.f_defect2.adapter.Defect2StatisticsContractorAdapter
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2StatisticsLineChartListsResult
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2StatisticsListsRequestBean
import com.catail.lib_commons.base.BaseVisibleInitFragment
import com.catail.lib_commons.bean.LoginBean
import com.catail.lib_commons.utils.AAChartUtils
import com.catail.lib_commons.utils.Config
import com.catail.lib_commons.utils.GsonUtil
import com.catail.lib_commons.utils.Logger
import com.catail.lib_commons.utils.MyLog
import com.catail.lib_commons.utils.NetApi
import com.catail.lib_commons.utils.Preference
import com.catail.lib_commons.utils.Util
import com.catail.lib_commons.utils.Utils
import com.catail.qaqc.f_defect2.bean.PieChartListsBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.Callback
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.Response

class Defect2StatisticsLineChartFragment : BaseVisibleInitFragment(),
    BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {
    private var projectId: String? = null
    private var msg: String? = null
    private var status: String? = ""
    private var mDefectType: String? = ""

    private var tv_total_count: TextView? = null
    private var tv_statistic_title: TextView? = null

    //公司统计信息
    private var iv_contractors_arrow: ImageView? = null
    private var contractorsChartModel = AAChartModel()
    private var contractors_chartView: AAChartView? = null
    private var mContractorsList: ArrayList<PieChartListsBean>? = null

    //公司列表
    private var mContractorsDataList: ArrayList<QueryDefect2StatisticsLineChartListsResult.ResultBean>? =
        null
    private var mAllContractorsDataList: ArrayList<QueryDefect2StatisticsLineChartListsResult.ResultBean>? =
        null
    private var mTempContractorsDataList: ArrayList<QueryDefect2StatisticsLineChartListsResult.ResultBean>? =
        null
    private var rvContractorsListAdapter: Defect2StatisticsContractorAdapter? = null

    private var contractorId = ""
    private var contractorName = ""

    override fun getContentViewLayoutId(): Int {
        return R.layout.fragment_defect2_statistics_linechart
    }

    @SuppressLint("WrongConstant")
    override fun initView(fragment_view: View) {
        tv_total_count = fragment_view.findViewById(R.id.tv_total_count)
        tv_statistic_title = fragment_view.findViewById(R.id.tv_statistic_title)

        //公司统计图
        contractors_chartView = fragment_view.findViewById(R.id.contractors_chartView)
        contractorsChartModel =
            AAChartUtils.initColumChartModel(requireActivity())//只用一次
        contractorsChartModel.xAxisVisible(false)

        AAChartUtils.setColumChartColor(contractorsChartModel)
        AAChartUtils.setColumChartStyle(contractorsChartModel)
        mContractorsList = ArrayList()

        iv_contractors_arrow = fragment_view.findViewById(R.id.iv_contractors_arrow)
        iv_contractors_arrow?.setOnClickListener(this)

        //公司列表
        val rv_contractors_list = fragment_view.findViewById<RecyclerView>(R.id.rv_contractors_list)
        val localLinearLayoutManager = LinearLayoutManager(activity)
        localLinearLayoutManager.orientation = RecyclerView.VERTICAL
        rv_contractors_list.layoutManager = localLinearLayoutManager

        mContractorsDataList = ArrayList()
        mTempContractorsDataList = ArrayList()
        mAllContractorsDataList = ArrayList()

        rvContractorsListAdapter = Defect2StatisticsContractorAdapter(
            R.layout.adapter_defect2_statistics_contractors_item,
            mContractorsDataList
        )
        rv_contractors_list.adapter = rvContractorsListAdapter
        rvContractorsListAdapter!!.onItemChildClickListener = this
    }

    override fun initData() {
        val sysVersion = Utils.GetSystemCurrentVersion()
        msg = if (sysVersion == 0) {
            getString(R.string.processing)
        } else {
            getString(R.string.processing)
        }

        projectId = arguments?.getString("projectId").toString()
        val projectName = arguments?.getString("projectName").toString()
        status = arguments?.getString("status").toString()
        mDefectType = arguments?.getString("mDefectType").toString()

        initPieChartTitle()

        QueryDefect2StatisticsLists()
    }

    private fun initPieChartTitle() {
        if (status == "1") {
            tv_statistic_title!!.text = resources.getString(R.string.defect2_statistics_title_1)
        } else if (status == "2") {
            tv_statistic_title!!.text = resources.getString(R.string.defect2_statistics_title_2)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_contractors_arrow -> {
                if (mContractorsDataList!!.size <= 5) {
                    mContractorsDataList!!.clear()
                    mContractorsDataList!!.addAll(mAllContractorsDataList!!)
                    iv_contractors_arrow!!.isSelected = true
                } else {
                    mContractorsDataList!!.clear()
                    mContractorsDataList!!.addAll(mTempContractorsDataList!!)
                    iv_contractors_arrow!!.isSelected = false
                }
                rvContractorsListAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
        if (view.id == R.id.rl_content) {
//            InspectionStatisticsHomeActivity statisticsHomeActivity
//                    = (InspectionStatisticsHomeActivity) getActivity();
//            if (position == 0) {
//                statisticsHomeActivity.setCurrentPage(4);
//            } else if (position == 1) {
//                statisticsHomeActivity.setCurrentPage(3);
//            } else if (position == 2) {
//                statisticsHomeActivity.setCurrentPage(5);
//            } else if (position == 3) {
//                statisticsHomeActivity.setCurrentPage(6);
//            }
        }
    }

    fun setContractorsData(result: List<QueryDefect2StatisticsLineChartListsResult.ResultBean>) {
        for (i in result.indices) {
            val tempBean = PieChartListsBean().apply {
                piechart_name = result[i].name
                piechart_count = result[i].cnt
            }
            mContractorsList?.add(tempBean)
        }
        setContractorsChartData(mContractorsList!!)
    }

    private fun setContractorsChartData(mContractorsList: ArrayList<PieChartListsBean>) {
        val chartNameDatas = arrayOfNulls<String>(6)
        val chartValueDatas = arrayOfNulls<Any>(6)

        val otherStr = resources.getString(R.string.defect2_others)
        var otherCount = 0
        for (i in mContractorsList.indices) {
            if (i < 5) {
                chartNameDatas[i] = mContractorsList[i].piechart_name.toString()
                chartValueDatas[i] = mContractorsList[i].piechart_count!!.toInt()
            } else {
                //方法一:如果>6,需要判断是不是最后一个, 最后一个设置名称和数量
                //方法二:如果>6,每次都设置第六个数据相加后面的数值
                //有多种方法,我选方法一
                //方法一:
                if (i == mContractorsList.size - 1) {
                    otherCount += mContractorsList[i].piechart_count!!.toInt()
                    chartNameDatas[5] = otherStr
                    chartValueDatas[5] = otherCount
                } else {
                    otherCount += mContractorsList[i].piechart_count!!.toInt()
                }
                //方法二:
//                if(i==6){
//                    chartNameDatas[5] = otherStr
//                    chartValueDatas[5] = otherCount
//                }else{
//                    var otherCount1 =mContractorsList.get(5).piechart_count!!.toInt()
//                    otherCount1+= mContractorsList.get(i).piechart_count!!.toInt()
//                    chartValueDatas[5] = otherCount1
//                }
            }
        }
        //设置名称
        AAChartUtils.setColumChartName(contractorsChartModel, chartNameDatas)
        //设置值
        AAChartUtils.setColumChartValue(contractorsChartModel, chartValueDatas)
        //刷新统计图
        contractors_chartView?.aa_drawChartWithChartModel(contractorsChartModel)//第一次初始化
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setContractorLists(result: List<QueryDefect2StatisticsLineChartListsResult.ResultBean>) {
        for (i in result.indices) {
            mAllContractorsDataList!!.add(result[i])
            if (i < 5) {
                mContractorsDataList!!.add(result[i])
                mTempContractorsDataList!!.add(result[i])
            }
        }
        Logger.e("mAllContractorsDataList==" + mAllContractorsDataList!!.size.toString())
        if (mAllContractorsDataList!!.size > 5) {
            iv_contractors_arrow!!.visibility = View.VISIBLE
        }
        rvContractorsListAdapter!!.notifyDataSetChanged()

        //默认选择第一个 去查询职位的信息.
        if (mAllContractorsDataList!!.size > 0) {
            contractorId = mAllContractorsDataList!![0].name.toString()
            contractorName = mAllContractorsDataList!![0].name!!
        }
    }



    private fun QueryDefect2StatisticsLists() {
        try {
            val loginBean = Utils
                .stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN)) as LoginBean
            val qaRequestBean = QueryDefect2StatisticsListsRequestBean()
            qaRequestBean.uid = loginBean.uid
            qaRequestBean.token = loginBean.token
            qaRequestBean.project_id = projectId
            qaRequestBean.bes_project_id = ""
            qaRequestBean.flag = status
            qaRequestBean.defect_type = mDefectType
            val json = GsonUtil.GsonString(qaRequestBean)
            Logger.e("CMSC0264--Dashboard统计--上传参数", json)
            OkHttpUtils
                .postString()
                .url(NetApi.QueryDefect2StatisticsLists + "")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(object : Callback<Any?>() {
                    @Throws(Exception::class)
                    override fun parseNetworkResponse(response: Response, id: Int): Any? {
                        val jsonStr = response.body()!!.string()
                        MyLog.loger("CMSC0264--status--$status--返回值", jsonStr)
                        val beReplacedStr = "\"result\":{}"
                        val toReplacedStr = "\"result\":[]"
                        val replace = jsonStr.replace(beReplacedStr, toReplacedStr)
                        return GsonUtil.GsonToBean(
                            replace,
                            QueryDefect2StatisticsLineChartListsResult::class.java
                        )
                    }

                    override fun onError(call: Call, e: Exception, id: Int) {
                        Logger.e("CMSC0376-Exception", e.toString())
                    }

                    override fun onResponse(response: Any?, id: Int) {
                        try {
                            val resultBean =
                                response as QueryDefect2StatisticsLineChartListsResult?
                            if (resultBean != null) {
                                if (resultBean.errno == 0) {
                                    if (resultBean.result != null) {
                                        // 显示公司数据
                                        setContractorsData(resultBean.result!!)

                                        setContractorLists(resultBean.result!!)
                                    }
                                } else if (resultBean.errno == 2) {
                                    //其他设备登录
                                    Logger.e(
                                        "resultBean.getErrno()==2",
                                        resultBean.errno.toString() + ""
                                    )
                                    Util.showDialogLogin(activity as AppCompatActivity?)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            //                                showToast("UnFinished-Unknown Error");
                        }
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}