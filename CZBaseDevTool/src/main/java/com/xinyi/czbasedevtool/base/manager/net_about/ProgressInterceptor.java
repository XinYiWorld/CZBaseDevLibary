package com.xinyi.czbasedevtool.base.manager.net_about;

import android.os.Looper;
import android.util.Log;

import com.socks.library.KLog;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
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
        Log.v(TAG, "request:" + request.toString());
        return orginalResponse.newBuilder()
                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                    @Override
                    public void onProgress(long progress, long total, boolean done) {
                        Log.i(TAG, Looper.myLooper() + "");
                        Log.i(TAG, "onProgress: " + "total ---->" + total + "   done ---->" + progress);
                    }
                }))
                .build();
    }
}
