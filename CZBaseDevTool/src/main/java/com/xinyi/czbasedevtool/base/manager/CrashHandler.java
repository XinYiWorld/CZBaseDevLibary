package com.xinyi.czbasedevtool.base.manager;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;
import com.xinyi.czbasedevtool.base.utils.io.FileUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 崩溃异常处理类
 * author:Created by ChenZhang on 2016/7/6 0006.
 * function:
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();

    private static CrashHandler instance; // 单例模式

    private Context context; // 程序Context对象
    private Thread.UncaughtExceptionHandler defalutHandler; // 系统默认的UncaughtException处理类
    private DateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss", Locale.CHINA);

    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }

        return instance;
    }

    /**
     * 异常处理初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        // 获取系统默认的UncaughtException处理器
        defalutHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 自定义错误处理
        boolean res = handleException(ex);
        if (!res && defalutHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defalutHandler.uncaughtException(thread, ex);

        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {

        if (ex == null) {
            return false;
        }

        File saveFile = new File(FileUtil.SAVE_ROOT_DIR + "Crash.html");
        File tempFile = new File(FileUtil.SAVE_ROOT_DIR + "CrashTemp.html");
        double fileSize = FileUtil.getFileOrFolderSize(saveFile.getPath(), FileUtil.SIZETYPE_KB);
        Log.i(TAG, "handleException: fileSize=" + fileSize);
        if(saveFile.exists() && fileSize > 3000){
            saveFile.deleteOnExit();
        }

        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                ex.printStackTrace();
                String err = "[" + ex.getMessage() + "]";
                Toast.makeText(context, "程序出现异常." + err, Toast.LENGTH_LONG)
                        .show();

                Looper.loop();
            }
        });

        // 收集设备参数信息 \日志信息
        String errInfo = ExceptionUtil.collectExceptionInfo(ex);
        Log.e(TAG, "handleException: " + errInfo );
        FileUtil.if_append = false;
        FileUtil.copy(saveFile,tempFile);
        // 保存日志文件
        FileUtil.if_append = false;
        FileUtil.write(errInfo, saveFile);
        FileUtil.if_append = true;
        FileUtil.copy(tempFile, saveFile,true);
        return true;                                                                                                                }
}
