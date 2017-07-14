package com.xinyi.czbasedevtool.base.utils.download;

/**
 * Created by 陈章 on 2017/7/3 0003.
 * func:状态监听器
 */

public interface IStateObserver {
    /**
     * 通知监听器状态改变的回调
     */
    void notifyDownloadStateChange(DownloadInfo downloadInfo);

    /**
     * 通知监听器下载进度改变的回调
     */
    void notifyDownloadProgressChange(DownloadInfo downloadInfo);
}
