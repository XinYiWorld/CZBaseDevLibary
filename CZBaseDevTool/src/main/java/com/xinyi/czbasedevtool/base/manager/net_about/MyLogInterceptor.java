package com.xinyi.czbasedevtool.base.manager.net_about;

import com.socks.library.KLog;
import com.xinyi.czbasedevtool.base.utils.TLog;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * 自定义日志过滤器
 */
public class MyLogInterceptor implements Interceptor {
    private static final String TAG = "MyLogInterceptor";
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        TLog.v(TAG, "request:" + request.toString());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        TLog.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        TLog.v(TAG, "response body:");
        KLog.json(content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
