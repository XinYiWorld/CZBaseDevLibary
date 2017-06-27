package com.xinyi.czbasedevtool.base.manager.net_about;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Retrofit下载接口类
 * author:Created by ZhangChen on 2016/7/22 0022.
 * detail:
 */
public interface DownloadService {
    /**
     * 整个下载
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);

    /**
     * 断点续传
     * @param start
     * @param url
     * @return
     */
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);
}
