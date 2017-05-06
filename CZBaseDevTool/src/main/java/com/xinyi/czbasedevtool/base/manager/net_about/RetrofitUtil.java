package com.xinyi.czbasedevtool.base.manager.net_about;


import okhttp3.OkHttpClient;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * 拦截器配置
 */
public class RetrofitUtil {
    //得到OkHttpClient设置给Retrofit
    //可以给OkHttpClient添加各种拦截器
    public static OkHttpClient generateOkHttpClient() {
       return generateOkHttpClient(null);
    }

    public static OkHttpClient generateOkHttpClient(String token) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(RetrofitClient.isHasToken()){
            builder.addInterceptor(new TokenAddInterceptor(token == null ? TokenGenerator.getToken() : token));
            CommonParamsInterceptor commonParamsInterceptor = new CommonParamsInterceptor();
            commonParamsInterceptor.addParams("token",token == null ? TokenGenerator.getToken() : token);
             builder.addInterceptor(commonParamsInterceptor);
        }
        builder.addInterceptor(new HeaderAttachInterceptor());
        builder.addInterceptor(new MyLogInterceptor());
        builder.addInterceptor(new ProgressInterceptor());
//        builder.addInterceptor(new CacheInterceptor());
        return  builder.build();
    }
}
