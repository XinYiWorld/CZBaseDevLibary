package com.xinyi.czbasedevtool.base.manager.net_about;

import android.os.Looper;

import com.xinyi.czbasedevtool.base.utils.TLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载或上传进度拦截器
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class ProgressInterceptor implements Interceptor {
    private static final String TAG = "ProgressInterceptor";
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response orginalResponse = chain.proceed(request);
        TLog.v(TAG, "request:" + request.toString());
        return orginalResponse.newBuilder()
                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                    @Override
                    public void onProgress(long progress, long total, boolean done) {
                        TLog.i(TAG, Looper.myLooper() + "");
                        TLog.i(TAG, "onProgress: " + "total ---->" + total + "   done ---->" + progress);
                    }
                }))
                .build();
    }
}
