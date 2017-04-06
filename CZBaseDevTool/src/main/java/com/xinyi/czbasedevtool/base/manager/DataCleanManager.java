package com.xinyi.czbasedevtool.base.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;
import com.xinyi.czbasedevtool.base.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈章 on 2017/4/6 0006.
 * func:
 * 缓存清空工具类
 * 缓存组成部分
 * 1）指定目录
 * 2）Webview
 * 3）app系统缓存
 */

public class DataCleanManager {
    private static DataCleanManager mInstance  = new DataCleanManager();
    private static Context mContext;
    private List<File> prepareToCleanDir = new ArrayList<>();
    private OnTaskDoneListener onCleanFinishListener ;
    private  DataCleanManager(){}

    public static DataCleanManager getInstance(Context mContext2){mContext = mContext2;
       return mInstance;
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ToastUtil.shortT(mContext,"缓存清除成功");
                onCleanFinishListener.done(null);
                prepareToCleanDir.clear();
            } else {
                ToastUtil.shortT(mContext,"缓存清除失败");
            }
        }
    };

    //添加一些需要删除的文件夹目录
    public void addPrepareToCleanDir(File dir){
        prepareToCleanDir.add(dir);
    }

    public void clean(){
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        });
    }

    //得到总的缓存大小
    public String getTotalCachedSize(){
        // 计算缓存大小
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = mContext.getFilesDir();// /data/data/package_name/files
        File cacheDir = mContext.getCacheDir();// /data/data/package_name/cache

        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);

        // 2.2版本才有将应用缓存转移到sd卡的功能
        if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
            File externalCacheDir = mContext.getExternalCacheDir();//"<sdcard>/Android/data/<package_name>/cache/"
            fileSize += getDirSize(externalCacheDir);
        }

        //计算指定目录下的缓存
        for (File dir : prepareToCleanDir ){
            fileSize += getDirSize(dir);
        }

        if (fileSize > 0)
            cacheSize = formatFileSize(fileSize);
        return cacheSize;
    }


    //监听清空缓存完成
    public void setOnCleanFinishListener(OnTaskDoneListener onCleanFinishListener) {
        this.onCleanFinishListener = onCleanFinishListener;
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }


    /**
     * 转换文件大小
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 在项目中经常会使用到WebView 控件,当加载html 页面时,会在/data/data/package_name目录下生成database与cache 两个文件夹。请求的url 记录是保存在WebViewCache.db,而url 的内容是保存在WebViewCache 文件夹下
     */
    private void clearAppCache()
    {
        //删除指定目录下的所有文件
        for (File file : prepareToCleanDir){
             clearCacheFolder(file,System.currentTimeMillis());
        }
        //先删除WebViewCache目录下的文件
        mContext.deleteDatabase("webview.db");
        mContext.deleteDatabase("webview.db-shm");
        mContext.deleteDatabase("webview.db-wal");
        mContext.deleteDatabase("webviewCache.db");
        mContext.deleteDatabase("webviewCache.db-shm");
        mContext.deleteDatabase("webviewCache.db-wal");
        //清除数据缓存
        clearCacheFolder(mContext.getFilesDir(),System.currentTimeMillis());
        clearCacheFolder(mContext.getCacheDir(),System.currentTimeMillis());
        //2.2版本才有将应用缓存转移到sd卡的功能
        if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
            clearCacheFolder(mContext.getExternalCacheDir(),System.currentTimeMillis());
        }

    }

    /**
     * 清除缓存目录
     * @param dir 目录
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory() && dir.exists()) {
            try {
                for (File child:dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
}
