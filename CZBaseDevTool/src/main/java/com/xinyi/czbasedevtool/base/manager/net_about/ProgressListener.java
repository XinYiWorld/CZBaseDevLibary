package com.xinyi.czbasedevtool.base.manager.net_about;

/**
 * 监听OkHttp的下载及上传进度
 * author:Created by ZhangChen on 2016/7/27 0027.
 * detail:
 */
public interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
