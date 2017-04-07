package com.xinyi.czbasedevtool.base.manager.net_about;

import android.content.Context;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;
import com.xinyi.czbasedevtool.base.utils.NetUtil;

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
        if (!NetUtil.isConnected(mContext)) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        Response responseLatest;
        if (NetUtil.isConnected(mContext)) {
            int maxAge = 0; //有网失效一分钟 60
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 0; // 没网失效6小时 6* 60 *60
            responseLatest= response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return  responseLatest;
    }
}
