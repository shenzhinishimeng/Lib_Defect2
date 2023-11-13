package com.catail.lib_commons.net;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class OkHttpManager {
	private static OkHttpManager okHttpManager;
	private final OkHttpClient okHttpClient;
	private RequestCallBack requestCallBack;
	private final MediaType MEDIATYPE_JSON = MediaType
			.parse("application/json; charset=utf-8");

	public OkHttpManager() {
		super();
		// TODO Auto-generated constructor stub
		okHttpClient = new OkHttpClient();
		setConnection(1000*10, TimeUnit.SECONDS);
		setReadTime(1000*10, TimeUnit.SECONDS);
		setWriteTime(1000*10, TimeUnit.SECONDS);

	}

	public static OkHttpManager getInstance() {
		if (okHttpManager == null) {
			synchronized (OkHttpManager.class) {
				if (okHttpManager == null) {
					okHttpManager = new OkHttpManager();
				}
			}
		}
		return okHttpManager;
	}

	/**
	 * 设置连接超时
	 * 
	 */

	private boolean setConnection(long timeout, TimeUnit timeUnit) {
		if (okHttpClient != null) {
			okHttpClient.setConnectTimeout(timeout, timeUnit);
			return true;
		}
		return false;
	}

	/**
	 * 读取超时
	 * 
	 */
	private boolean setReadTime(long timeout, TimeUnit timeUnit) {
		if (okHttpClient != null) {
			okHttpClient.setReadTimeout(timeout, timeUnit);
			return true;
		}
		return false;

	}

	/**
	 * 写入超时
	 * 
	 */
	private boolean setWriteTime(long timeout, TimeUnit timeUnit) {
		if (okHttpClient != null) {
			okHttpClient.setWriteTimeout(timeout, timeUnit);
			return true;
		}
		return false;

	}

	/**
	 * 同步get请求
	 * 
	 */

	private Response getSyn(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Call call = okHttpClient.newCall(request);
		Response resultResponse = call.execute();
		return resultResponse;
	}

	/**
	 * 异步的get请求
	 * 
	 */

	private void getAsyn(String url, RequestCallBack responseCallback)
			throws IOException {
		this.requestCallBack = responseCallback;
		Request request = new Request.Builder().url(url).build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(resultCallback);

	}

	/**
	 * 同步post请求(表单)
	 * 
	 */

	private Response postSynForm(String url, HashMap<String, String> param)
			throws Exception {
		try {
			if (param.size() == 0)
				return null;
		} catch (Exception e) {
			throw new Exception("参数不足");
		}
		Call call = okHttpClient.newCall(buildPostFormRequest(url, param));
		return call.execute();

	}

	/**
	 * post异步请求(表单)
	 * 
	 */

	private void postAsynForm(String url, HashMap<String, String> param,
			RequestCallBack responseCallback) throws Exception {
		this.requestCallBack = responseCallback;
		// okHttpClient = new OkHttpClient();
		try {
			if (param.size() == 0)
				return;
		} catch (Exception e) {
			throw new Exception("参数不足");
		}
		// OkHttpClient okHttpClient = this.okHttpClient.clone();
		okHttpClient.newCall(buildPostFormRequest(url, param)).enqueue(
				resultCallback);

	}

	/**
	 * post同步（JSON）
	 * 
	 */
	private Response postSynJson(String url, String json) throws Exception {
		OkHttpClient okHttpClient = this.okHttpClient.clone();

		RequestBody requestBody = RequestBody.create(MEDIATYPE_JSON, json);
		Request request = new Request.Builder().url(url).post(requestBody)
				.build();
		return okHttpClient.newCall(request).execute();

	}

	/**
	 * post异步（JSON）
	 * 
	 */
	private void postAsynJson(String url, JSONObject json,
			RequestCallBack responseCallback) throws Exception {
		this.requestCallBack = responseCallback;
		// okHttpClient = new OkHttpClient();
		RequestBody requestBody = RequestBody.create(MEDIATYPE_JSON,
				json.toString());
		Request request = new Request.Builder().url(url).post(requestBody)
				.build();

		// OkHttpClient okHttpClient = this.okHttpClient.clone();
		okHttpClient.newCall(request).enqueue(resultCallback);

	}

	// 默认采用utf-8编码

	/**
	 * 构建表单
	 * 
	 */

	private Request buildPostFormRequest(String url,
			HashMap<String, String> param) {
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		Set<String> keySets = param.keySet();
		Iterator ieIterator = keySets.iterator();
		Collection<String> valueCollection = param.values();
		Iterator valuesIt = valueCollection.iterator();
		while (ieIterator.hasNext() && valuesIt.hasNext()) {
			formEncodingBuilder.add(ieIterator.next().toString(), valuesIt
					.next().toString());
		}
		RequestBody body = formEncodingBuilder.build();
		return new Request.Builder().url(url).post(body).build();

	}

	/**
	 * 响应
	 */

	private final Callback resultCallback = new Callback() {

		@Override
		public void onResponse(Response arg0) throws IOException {
			if (requestCallBack != null) {
				requestCallBack.onSuccess(arg0);
				// okHttpClient = null;
			}

		}

		@Override
		public void onFailure(Request arg0, IOException arg1) {
			if (requestCallBack != null) {
				requestCallBack.onFail(arg1.getMessage(), arg0);
				// okHttpClient = null;
			}

		}
	};

	/**
	 * get同步请求
	 * 
	 */

	public Response getSynRequest(String url) throws Exception {
		Response response = null;
		try {
			response = getSyn(url);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("请求异常");
		}
		return response;
	}

	/**
	 * get异步请求
	 * 
	 */
	public boolean getAsynRequest(String url, RequestCallBack responseCallback)
			throws Exception {
		boolean flag = false;
		try {
			getAsyn(url, responseCallback);
			flag = true;

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("未知异常");
		}
		return flag;
	}

	/**
	 * post同步请求(表单)
	 * 
	 */

	public Response postSynFormRequest(String url, HashMap<String, String> param)
			throws Exception {
		Response response = null;
		try {
			response = postSynForm(url, param);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("参数不足");
		}
		return response;
	}

	/**
	 * post异步请求（表单）
	 * 
	 */
	public boolean postAsynFormRequest(String url,
			HashMap<String, String> param, RequestCallBack responseCallback)
			throws Exception {
		boolean flag = false;
		try {
			postAsynForm(url, param, responseCallback);
			flag = true;

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("参数不足");
		}
		return flag;
	}

	/**
	 * post同步请求(json)
	 * 
	 */

	public Response postSynRequest(String url, String json) throws Exception {
		Response response = null;
		try {
			response = postSynJson(url, json);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("未知异常");
		}
		return response;
	}

	/**
	 * post异步请求（json）
	 * 
	 */
	public boolean postAsynJsonRequest(String url, JSONObject json,
			RequestCallBack responseCallback) throws Exception {
		boolean flag = false;
		try {
			postAsynJson(url, json, responseCallback);
			flag = true;

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("未知异常");
		}
		return flag;
	}

	public void postTogetByte(String url, RequestCallBack responseCallback) {
		this.requestCallBack = responseCallback;
		Request request = new Request.Builder().url(url).build();
		okHttpClient.newCall(request).enqueue(resultCallback);
	}

	/**
	 * 回调接口
	 * 
	 *
	 */
	public interface RequestCallBack {
		void onSuccess(Response arg0) throws IOException;

		void onFail(String error, Request request);
	}

}
