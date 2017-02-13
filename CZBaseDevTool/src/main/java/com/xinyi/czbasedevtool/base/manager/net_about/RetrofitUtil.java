package com.xinyi.czbasedevtool.base.manager.net_about;


import okhttp3.OkHttpClient;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class RetrofitUtil {
    //得到OkHttpClient设置给Retrofit
    //可以给OkHttpClient添加各种拦截器
    public static OkHttpClient generateOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HeaderAttachInterceptor())
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new ProgressInterceptor())
                ;

        if(RetrofitClient.isHasToken()) builder.addInterceptor(new TokenAddInterceptor());
        return  builder.build();
    }
}
