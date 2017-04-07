package com.xinyi.czbasedevtool.base.manager.net_about;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * 由于直接在URL上添加公共参数，在POST请求服务是获取不到数据的。所以此方式不通。
 */
@Deprecated
public class TokenAddInterceptor implements Interceptor {
    private String token;

    public TokenAddInterceptor() {
        token = null;
    }

    public TokenAddInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl originalUrl = chain.request().url();
        Request request = chain.request()
                .newBuilder()
                .url(originalUrl + "&token=" + (token == null ? getToken() : token))
                .build();
        return chain.proceed(request);
    }

    private String getToken(){
        return TokenGenerator.getToken();
    }
}