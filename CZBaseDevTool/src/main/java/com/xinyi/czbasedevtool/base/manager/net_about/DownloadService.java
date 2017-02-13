package com.xinyi.czbasedevtool.base.manager.net_about;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Retrofit下载接口类
 * author:Created by ZhangChen on 2016/7/22 0022.
 * detail:
 */
public interface DownloadService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);

}
