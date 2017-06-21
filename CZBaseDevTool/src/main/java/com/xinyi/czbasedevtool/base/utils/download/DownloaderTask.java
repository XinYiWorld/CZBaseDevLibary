package com.xinyi.czbasedevtool.base.utils.download;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by 陈章 on 2017/6/21 0021.
 * func://下载信息的封装类
 */
@Table(name = "downloadertask" )
public class DownloaderTask {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;              //任务的id
    @Column(name = "url")
    private String url;             //下载地址
    @Column(name = "savePath")
    private String savePath;        //文件保存路径

    public DownloaderTask(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
