package com.xinyi.czbasedevtool.base.manager.net_about;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class TokenAddInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl originalUrl = chain.request().url();
        Request request = chain.request()
                .newBuilder()
                .url(originalUrl + "&token=" + getToken())
                .build();
        return chain.proceed(request);
    }

    private String getToken(){
        return "673384149f156fb1bc9f68d7375305ac";
    }
}