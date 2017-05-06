package com.xinyi.czbasedevtool.base.manager.net_about;

import android.content.Context;

import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;
import com.xinyi.czbasedevtool.base.utils.SPUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class RetrofitClient {
    private  static String BASE_URL = "http://192.168.123.1/";
    private  static String token;

    //    private final  static String BASE_URL = "http://app.haopeixun.org/index.php/";

    private static Retrofit retrofitInstance = null;
    private static Retrofit.Builder retrofitBuilder;

    private RetrofitClient (){
        ExceptionUtil.throwRunmtimeExcpetionInPrivateConstructor(RetrofitClient.class);
    }

    public static Retrofit getDefaultRetrofitClient(){
        if(retrofitBuilder == null){
            retrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(RetrofitUtil.generateOkHttpClient())
                    .addConverterFactory(FastJsonConverterFactory.create())     //添加Json转换器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());  //添加 RxJava 适配器
        }
        retrofitInstance = retrofitBuilder.build();
        return retrofitInstance;
    }

    //得到RxJava的请求接口服务
    public static <T> T getService(Class<T> serviceClassz){
        return getDefaultRetrofitClient().create(serviceClassz);
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        onRetrofitSetChanged();
    }

    public static boolean isHasToken() {
        return token != null;
    }

    public static void setHasToken(boolean hasToken){
        if(hasToken){
            token = TokenGenerator.getToken();
        }else{
            token = null;
        }
        onRetrofitSetChanged();
    }

    public static void setToken(String token) {
        RetrofitClient.token = token;
        onRetrofitSetChanged();
    }

    //开启缓存
    public static void openCache(Context context){
        SPUtil.getInstance(context).put(context,"cache_data",true);
    }

    //关闭缓存
    public static void closeCache(Context context){
        SPUtil.getInstance(context).put(context,"cache_data",false);
    }

    //是否有缓存判断
    public static boolean hasCache(Context context){
        return (boolean) SPUtil.getInstance(context).get(context,"cache_data",false);
    }


    public static void onRetrofitSetChanged(){
        if(retrofitBuilder == null ) return;
        retrofitBuilder.baseUrl(BASE_URL);
        retrofitBuilder.client(RetrofitUtil.generateOkHttpClient(token));
    }
}
