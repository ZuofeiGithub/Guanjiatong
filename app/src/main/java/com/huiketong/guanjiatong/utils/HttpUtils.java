package com.huiketong.guanjiatong.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求工具
 */
public class HttpUtils {
    private static HttpUtils mInstance;
    private static OkHttpClient okHttpClient;
    private Handler handler;
    private Context context;
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private HttpUtils() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * POST 表单请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     */
    public static void postFormRequest(String url, Map<String, String> params, HttpCallback callback) {
        getInstance().inner_postFormRequest(url, params, callback);
    }

    /**
     * 内部实现POST表单请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     */
    private void inner_postFormRequest(String url, Map<String, String> params, HttpCallback callback) {
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        // 对参数进行遍历
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey();
            String value = map.getValue();

            if (value == null) {
                value = "";
            }
            builder.add(key, value);
        }
        requestBody = builder.build();
        //构建请求
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        //发送请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.Toast(context, "网络异常！");
                    }
                });
                deliverDataFaild(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {    //请求成功
                    String result = response.body().string();
                    deliverDataSuccess(result, callback);
                } else {
                    throw new IOException(response.toString());
                }
            }
        });
    }

    /**
     * post Json请求
     *
     * @param url        请求地址
     * @param jsonObject 请求数据
     * @param callback   回调
     */
    public static void postJsonRequest(String url, JsonObject jsonObject, HttpCallback callback) {
        getInstance().inner_postJsonRequest(url, jsonObject, callback);
    }

    /**
     * 内部实现post json请求
     *
     * @param url        请求地址
     * @param jsonObject 请求数据
     * @param callback   回调
     */
    private void inner_postJsonRequest(String url, JsonObject jsonObject, final HttpCallback callback) {
        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.Toast(context, "网络异常！");
                    }
                });
                deliverDataFaild(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//成功
                    String result = response.body().string();
                    deliverDataSuccess(result, callback);
                } else {
                    throw new IOException(response.toString());
                }
            }
        });
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public static void getRequest(String url, Map<String, String> params, HttpCallback callback) {
        getInstance().inner_getRequest(url, params, callback);
    }

    /**
     * 内部实现get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    private void inner_getRequest(String url, Map<String, String> params, final HttpCallback callback) {
        if (params == null) {
            params = new HashMap<>();
        }
        String getUrl = buildUrl(url, params);
        final Request request = new Request.Builder().url(getUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.Toast(context, "网络异常！");
                    }
                });
                deliverDataFaild(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//成功
                    String result = response.body().string();
                    deliverDataSuccess(result, callback);
                } else {
                    throw new IOException(response.toString());
                }
            }
        });
    }

    /**
     * 上传图片表单
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param fileName 文件名
     * @param files    图片文件
     * @param callback 回调函数
     */
    public static void sendImageForm(String url, Map<String, String> params,String fileName, List<File> files, HttpCallback callback) {
        getInstance().inner_sendImageForm(url, params, fileName,files, callback);
    }

    /**
     * 上传图片表单
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param fileName 文件名
     * @param files    图片文件
     * @param callback 回调函数
     */
    private void inner_sendImageForm(String url, Map<String, String> params,String fileName, List<File> files, HttpCallback callback) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null){
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (files != null){
            for (File file : files) {
                multipartBodyBuilder.addFormDataPart(fileName, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();

        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(url);// 添加URL地址
        RequestBuilder.post(requestBody);
        Request request = RequestBuilder.build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.Toast(context, "网络异常！");
                    }
                });
                deliverDataFaild(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//成功
                    String result = response.body().string();
                    deliverDataSuccess(result, callback);
                } else {
                    throw new IOException(response.toString());
                }
            }
        });
    }

    /**
     * 构建url地址
     *
     * @param url    基础地址
     * @param params 请求参数
     * @return 构建好的url
     */
    private String buildUrl(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        } else if (!url.contains("?")) {
            url += "?";
        }
        for (Map.Entry<String, String> map : params.entrySet()) {
            String value = map.getValue();
            if (value == null) {
                value = "";
            }
            url += map.getKey() + "=" + value + "&";
        }
        return url.substring(0, url.length() - 1);
    }

    /**
     * 请求失败调用
     *
     * @param request
     * @param io
     * @param callback
     */
    private void deliverDataFaild(final Request request, final IOException io, final HttpCallback callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.complete();
                    callback.requestFaild(request, io);
                }
            }
        });
    }

    /**
     * 请求成功调用
     *
     * @param result
     * @param callback
     */
    private void deliverDataSuccess(final String result, final HttpCallback callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    try {
                        callback.complete();
                        callback.requestSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void setContext(Context context) {
        getInstance().context = context;
    }
}
