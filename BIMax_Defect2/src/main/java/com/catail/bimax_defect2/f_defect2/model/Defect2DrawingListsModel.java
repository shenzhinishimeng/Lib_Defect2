package com.catail.bimax_defect2.f_defect2.model;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.Defect2DrawingRequestBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2PinListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectBlockListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectLevelListsResultBean;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectZoneListsResultBean;
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

public class Defect2DrawingListsModel {
    private BaseActivity activity;
    private String msg;

    public Defect2DrawingListsModel(BaseActivity activity) {
        this.activity = activity;
    }

    public void QueryDefectBlockLists(String projectId, String drawing_Flag, BESCallBack besCallBack) {
        try {
            LoginBean loginBean = (LoginBean)
                    Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2DrawingRequestBean qaRequestBean = new Defect2DrawingRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);
            qaRequestBean.setDrawing_flag(drawing_Flag);
            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0131--查询Defect Block列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefectBlockLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0131--查询Defect Block列表--返回值", jsonStr);

//                            String beReplacedStr6 = "\"action\":{}";
//                            String toReplacedStr6 = "\"action\":[]";
//                            String replace6 = json.replace(beReplacedStr6, toReplacedStr6);

                            return GsonUtil.GsonToBean(jsonStr, QueryDefectBlockListsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDefectBlockListsResultBean resultBean = (QueryDefectBlockListsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            besCallBack.onSuccess(NetApi.QueryDefectBlockLists, resultBean);
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


    public void QueryDefectLevelLists(String projectId, String blockStr, String drawing_Flag,
                                      BESCallBack besCallBack) {
        try {
            LoginBean loginBean = (LoginBean)
                    Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2DrawingRequestBean qaRequestBean = new Defect2DrawingRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);
            qaRequestBean.setDrawing_flag(drawing_Flag);
            qaRequestBean.setBlock(blockStr);

            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0132--查询Defect level列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefectLevelLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0132--查询Defect level列表--返回值", jsonStr);

//                            String beReplacedStr6 = "\"action\":{}";
//                            String toReplacedStr6 = "\"action\":[]";
//                            String replace6 = json.replace(beReplacedStr6, toReplacedStr6);

                            return GsonUtil.GsonToBean(jsonStr, QueryDefectLevelListsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDefectLevelListsResultBean resultBean =
                                    (QueryDefectLevelListsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            besCallBack.onSuccess(NetApi.QueryDefectLevelLists, resultBean);
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

    public void QueryDefectZoneLists(String projectId, String blockStr, String levelStr, String drawing_Flag,
                                     BESCallBack besCallBack) {
        try {
            LoginBean loginBean = (LoginBean)
                    Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2DrawingRequestBean qaRequestBean = new Defect2DrawingRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);
            qaRequestBean.setDrawing_flag(drawing_Flag);
            qaRequestBean.setBlock(blockStr);
            qaRequestBean.setLevel(levelStr);


            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0133--查询Defect Zone列表--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefectZoneLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0133--查询Defect Zone列表--返回值", jsonStr);

//                            String beReplacedStr6 = "\"action\":{}";
//                            String toReplacedStr6 = "\"action\":[]";
//                            String replace6 = json.replace(beReplacedStr6, toReplacedStr6);
                            return GsonUtil.GsonToBean(jsonStr, QueryDefectZoneListsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDefectZoneListsResultBean resultBean =
                                    (QueryDefectZoneListsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            besCallBack.onSuccess(NetApi.QueryDefectZoneLists, resultBean);
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


    public void QueryDefect2PinLists(String projectId, String blockStr, String levelStr,
                                     String zoneStr, String drawingId, String drawing_Flag, BESCallBack besCallBack) {
        try {
            LoginBean loginBean = (LoginBean)
                    Utils.stringToObject(Preference.getSysparamFromSp(Config.LOGIN_BEAN));

            Defect2DrawingRequestBean qaRequestBean = new Defect2DrawingRequestBean();
            qaRequestBean.setUid(loginBean.getUid());
            qaRequestBean.setToken(loginBean.getToken());
            qaRequestBean.setProject_id(projectId);
            qaRequestBean.setDrawing_flag(drawing_Flag);
            qaRequestBean.setBlock(blockStr);
            qaRequestBean.setLevel(levelStr);
            qaRequestBean.setZone(zoneStr);
            qaRequestBean.setDrawing_id(drawingId);

            String json = GsonUtil.GsonString(qaRequestBean);
            Logger.e("CMSC0260--查询defect2图纸上的点--上传参数", json);
            OkHttpUtils
                    .postString()
                    .url(NetApi.QueryDefect2PinLists + "")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            String jsonStr = response.body().string();
                            MyLog.loger("CMSC0260--查询defect2图纸上的点--返回值", jsonStr);

//                            String beReplacedStr6 = "\"action\":{}";
//                            String toReplacedStr6 = "\"action\":[]";
//                            String replace6 = json.replace(beReplacedStr6, toReplacedStr6);
                            return GsonUtil.GsonToBean(jsonStr, QueryDefect2PinListsResultBean.class);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Logger.e("Exception", e.toString());
                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            QueryDefect2PinListsResultBean resultBean =
                                    (QueryDefect2PinListsResultBean) response;
                            try {
                                int lanVersion = Utils.GetSystemCurrentVersion();
                                if (resultBean != null) {
                                    if (resultBean.getErrno() == 0) {
                                        if (resultBean.getResult() != null) {
                                            besCallBack.onSuccess(NetApi.QueryDefect2PinLists, resultBean);
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
