package com.xinyi.czbasedevtool.base.manager.net_about;

import android.content.Context;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;
import com.xinyi.czbasedevtool.base.utils.CZ_TextUtil;
import com.xinyi.czbasedevtool.base.utils.NetUtil;
import com.xinyi.czbasedevtool.base.utils.TLog;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 陈章 on 2017/4/7 0007.
 * func:
 * 缓存拦截器
 */
public class CacheInterceptor implements Interceptor {
    private Context mContext;

    public CacheInterceptor() {
        this.mContext = SystemStaticInstanceManager.getmApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String header_cache_control_time = request.header("Cache_Control_Time");  //秒作单位
        TLog.i("header_cache_control:" + header_cache_control_time);

        if (!NetUtil.isConnected(mContext)) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response responseLatest;
        if (NetUtil.isConnected(mContext)) {
            int maxAge = 0; //有网失效一分钟 60
            if(RetrofitClient.hasCache(mContext) && !CZ_TextUtil.is_null_or_empty(header_cache_control_time)){
                maxAge = Integer.parseInt(header_cache_control_time);
            }
            TLog.i("maxAge = " + maxAge);
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 0; //有网失效一分钟 60
            if(RetrofitClient.hasCache(mContext) && !CZ_TextUtil.is_null_or_empty(header_cache_control_time)){
                maxStale = Integer.parseInt(header_cache_control_time);
            }
            TLog.i("maxStale = " + maxStale);
            responseLatest= response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return  responseLatest;
    }
}
