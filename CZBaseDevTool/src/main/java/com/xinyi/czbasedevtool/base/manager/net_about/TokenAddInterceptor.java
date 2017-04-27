package com.xinyi.czbasedevtool.base.manager.net_about;

import com.xinyi.czbasedevtool.base.utils.TLog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:  用于GET请求加token
 * 由于直接在URL上添加公共参数，在POST请求服务是获取不到数据的。所以此方式不通。
 */
public class TokenAddInterceptor implements Interceptor {
    private static final String TAG = "TokenAddInterceptor";
    private String token;

    public TokenAddInterceptor() {
        token = null;
    }

    public TokenAddInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();
        Request newRequest = originalRequest.newBuilder().build();

        String method = originalRequest.method();
        if (method.equals("GET")) {
             newRequest = originalRequest.newBuilder()
                .url(originalUrl + "&token=" + (token == null ? getToken() : token))
                .build();
        }
        TLog.v(TAG, "intercept: url = " + newRequest.url());
        return chain.proceed(newRequest);
    }

    private String getToken() {
        return TokenGenerator.getToken();
    }
}