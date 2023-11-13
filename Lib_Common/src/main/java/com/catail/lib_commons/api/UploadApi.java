package com.catail.lib_commons.api;

import com.catail.lib_commons.bean.DataSuccessBean;
import com.catail.lib_commons.net.NetResponseError;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.MyLog;
import com.catail.lib_commons.utils.NetApi;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UploadApi {
    private IMAGEResultBack imaBack;

    public void request(List<String> fileStr, IMAGEResultBack imaBack) {
        Logger.e("将要上传的图片为==", fileStr.toString());
        this.imaBack = imaBack;
//		String[] fileName = { "file1", "file2", "file3" };
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
//		for (int i = 0; i < fileStr.size(); i++) {
//			builder.addFormDataPart(fileName[i], "photo.jpg",
//					RequestBody.create(MediaType.parse("image/jpg"), new File(fileStr.get(i))));
        // Log.e("fileStr.get(i)", fileStr.get(i)+"");
//		}

        for (int i = 0; i < fileStr.size(); i++) {
            builder.addFormDataPart("file" + (i + 1), "photo.jpg",
                    RequestBody.create(MediaType.parse("image/jpg"), new File(fileStr.get(i))));
            Logger.e("fileStr.get(" + i + ")", fileStr.get(i) + "");
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(1000 * 10, TimeUnit.MINUTES);
        Request request = new Request.Builder().url(NetApi.UPLOAD_IMG)
                .addHeader("Content-Disposition", "form-data; name='file1'").post(builder.build()).build();


//		 Log.e("request.httpUrl", request.httpUrl()+"");
//		 Log.e("request.headers", request.headers()+"");
//		 Log.e("request.body().toString", request.body().toString()+"");
//		 Log.e("request.toString()", request.toString());
        okHttpClient.newCall(request).enqueue(callback);
    }

    public Object response(JSONObject jsonObject) throws Exception {
        MyLog.loger("图片上传的结果值=", jsonObject + "");
        try {
            List<String> fileLis = new ArrayList<>();
            JSONObject respJsonObject = jsonObject.getJSONObject("resp");
            String code = respJsonObject.getString("code");
            String desc = respJsonObject.getString("desc");
            if (code.equals("200") && desc.equalsIgnoreCase("OK")) {
                JSONObject dataJsonObject = jsonObject.getJSONObject("data");

//				Logger.e("dataJsonObject.toString()",dataJsonObject.toString());


                fileLis.add(dataJsonObject.getString("file1"));

                if (dataJsonObject.has("file2")) {
                    fileLis.add(dataJsonObject.getString("file2"));
                }
                if (dataJsonObject.has("file3")) {
                    fileLis.add(dataJsonObject.getString("file3"));
                }
                if (dataJsonObject.has("file4")) {
                    fileLis.add(dataJsonObject.getString("file4"));
                }
                if (dataJsonObject.has("file5")) {
                    fileLis.add(dataJsonObject.getString("file5"));
                }
                if (dataJsonObject.has("file6")) {
                    fileLis.add(dataJsonObject.getString("file6"));
                }
                if (dataJsonObject.has("file7")) {
                    fileLis.add(dataJsonObject.getString("file7"));
                }
                if (dataJsonObject.has("file8")) {
                    fileLis.add(dataJsonObject.getString("file8"));
                }
                if (dataJsonObject.has("file9")) {
                    fileLis.add(dataJsonObject.getString("file9"));
                }
                return fileLis;

            } else {
                DataSuccessBean dataSuccessBean = new DataSuccessBean();
                dataSuccessBean.setErrorNum(code);
                dataSuccessBean.setErrStr(desc);
                return dataSuccessBean;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private final Callback callback = new Callback() {

        @Override
        public void onResponse(Response arg0) {
            try {
                String body = arg0.body().string();
                Logger.e("onResponse==" + body);
                JSONObject jsonObject = new JSONObject(body);
                if (imaBack != null)
                    imaBack.onSuccess(jsonObject);
            } catch (Exception e) {
                if (imaBack != null)
                    imaBack.OnFail(e.getMessage(), NetResponseError.analyticalAnomaly);
                e.printStackTrace();
            } finally {
                if (imaBack != null)
                    imaBack = null;
            }
        }

        @Override
        public void onFailure(Request arg0, IOException arg1) {
            if (imaBack != null) {
                imaBack.OnFail(arg1.getMessage(), NetResponseError.netError);
                Logger.e("arg1.getMessage()==" + arg1.getMessage().toString());
            }
        }
    };

    public interface IMAGEResultBack {
        void onSuccess(JSONObject jsonObject);

        void OnFail(String error, String failType);
    }

}
