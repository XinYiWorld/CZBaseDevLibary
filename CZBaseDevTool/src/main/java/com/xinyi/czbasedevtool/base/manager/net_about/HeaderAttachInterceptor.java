package com.xinyi.czbasedevtool.base.manager.net_about;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class HeaderAttachInterceptor implements Interceptor {
    private static final String TAG = "HeaderAttachInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        return chain.proceed(request);
    }
}