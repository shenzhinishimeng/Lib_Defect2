package com.catail.bimax_defect2.f_defect2.model;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.Defect2ApplyListRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDisciplineListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QuerySubConListsResultBean;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.bean.LoginBean;
import com.catail.lib_commons.interfaces.BESCallBack;
import com.catail.lib_commons.utils.Config;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MyLog;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Preference;
import com.catail.lib_commons.utils.Util;
import com.catail.lib_commons.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

public class Defect2ApplyListModel {

    private BaseActivity activity;
    private String msg;

    public Defect2ApplyListModel(BaseActivity activity) {
        this.activity = activity;
    }

    public void QueryDisciplineLists(String projectId, String defectType, BESCallBack besCallBack) {
        try {
            LoginBean loginBean = (LoginBean)
                    Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2ApplyListRequestBean qaRequestBean = new Defect2ApplyListRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);
            qaRequestBean.setDefect_type(defectType);

            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0253 --查询Discipline列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDisciplineLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0253 --查询Discipline列表--返回值", jsonStr);

//                            String beReplacedStr6 = "\"action\":{}";
//                            String toReplacedStr6 = "\"action\":[]";
//                            String replace6 = json.replace(beReplacedStr6, toReplacedStr6);

                            return GsonUtil.GsonToBean(jsonStr, QueryDisciplineListsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDisciplineListsResultBean resultBean = (QueryDisciplineListsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {

                                            besCallBack.onSuccess(NetApi.QueryDisciplineLists, resultBean);

                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()==2", resultBean.getErrno() + "");
                                        Util.showDialogLogin(activity);
                                    } else if (resultBean.getErrno() == 7) {
                                        //无数据返回
                                        if (lanVersion == 0) {
                                            activity.showToast(resultBean.getErrstr_cn());
                                        } else {
                                            activity.showToast(resultBean.getErrstr());
                                        }
                                    } else {
                                        //其他错误代码
                                        if (lanVersion == 0) {
                                            activity.showToast(resultBean.getErrstr_cn());
                                        } else {
                                            activity.showToast(resultBean.getErrstr());
                                        }
                                    }
                                } else {
                                    activity.showToast(activity.getResources().getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                activity.showToast("UnFinished-Unknown Error");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void QuerySubConLists(String projectId, BESCallBack besCallBack) {
        try {
            LoginBean loginBean = (LoginBean)
                    Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2ApplyListRequestBean qaRequestBean = new Defect2ApplyListRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);

            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0134 --查询分包列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QuerySubConLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0134 --查询分包列表--返回值", jsonStr);

//                            String beReplacedStr6 = "\"action\":{}";
//                            String toReplacedStr6 = "\"action\":[]";
//                            String replace6 = json.replace(beReplacedStr6, toReplacedStr6);

                            return GsonUtil.GsonToBean(jsonStr, QuerySubConListsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QuerySubConListsResultBean resultBean = (QuerySubConListsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {

                                            besCallBack.onSuccess(NetApi.QuerySubConLists, resultBean);

                                        }
                                    } else if (resultBean.getErrno() == 2) {
                                        //其他设备登录
                                        Logger.e("resultBean.getErrno()==2", resultBean.getErrno() + "");
                                        Util.showDialogLogin(activity);
                                    } else if (resultBean.getErrno() == 7) {
                                        //无数据返回
                                        if (lanVersion == 0) {
                                            activity.showToast(resultBean.getErrstr_cn());
                                        } else {
                                            activity.showToast(resultBean.getErrstr());
                                        }
                                    } else {
                                        //其他错误代码
                                        if (lanVersion == 0) {
                                            activity.showToast(resultBean.getErrstr_cn());
                                        } else {
                                            activity.showToast(resultBean.getErrstr());
                                        }
                                    }
                                } else {
                                    activity.showToast(activity.getResources().getString(R.string.no_data));
                                }
                            } catch (Exception e) {
                                activity.showToast("UnFinished-Unknown Error");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
