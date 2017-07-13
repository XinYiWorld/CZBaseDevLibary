package com.xinyi.czbasedevtool.base.utils.download;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.xinyi.czbasedevtool.base.manager.ThreadPoolManager;
import com.xinyi.czbasedevtool.base.manager.net_about.DownloadService;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.my_define.CZDbManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * 统一的下载管理类
 *
 * @author Administrator
 */
public class DownloadManager implements IStateObserver {
    private static final String TAG = "DownloadManager";
    //定义下载目录:/mnt/sdcard/包名/cz_download
    public static String DOWNLOAD_DIR;

    //定义6种下载状态常量
    public static final int STATE_NONE = 0;//未下载的状态
    public static final int STATE_DOWNLOADING = 1;//下载中的状态
    public static final int STATE_PAUSE = 2;//暂停的状态
    public static final int STATE_FINISH = 3;//下载完成的状态
    public static final int STATE_ERROR = 4;//下载出错的状态
    public static final int STATE_WAITING = 5;//处于等待中的状态，任务创建，但是run方法没有执行

    //用于存放多个界面的下载监听器对象
    private ArrayList<DownloadObserver> downloadObserverList = new ArrayList<DownloadObserver>();

    //用于存放DownloadTask对象，当点击pause的时候能够找到该DownloadTask，及时从线程池中移除，为等待的任务腾出系统资源
    //真正执行任务的类，是不需要持久化存储的！！！！
    private HashMap<Integer, DownloadTask> downloadTaskMap = new HashMap<Integer, DownloadTask>();

    //如果HashMap是映射整数到Obj,则可适应该类，获得更高的效率
//	private SparseArray<DownloadTask> array = new SparseArray<DownloadManager.DownloadTask>();

    private static String BASE_URL = "";

    //使用单例模式
    private static DownloadManager mInstance = new DownloadManager();
    private final DbManager dbManager;

    private DownloadManager() {
        dbManager = CZDbManager.getDefaultDb();
    }

    public DownloadTaskInfo getDownloadInfo(DownloadTargetInfo targetDownloadInfo) {
        try {
            return dbManager.selector(DownloadTaskInfo.class).where("id", "=", targetDownloadInfo.getId()).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取DownloadManager的实例对象
     *
     * @return
     */
    public static DownloadManager getInstance(Context context) {
        DOWNLOAD_DIR = Environment.getExternalStorageDirectory()
                + File.separator + context.getPackageName()
                + File.separator + "cz_download";

        //初始化下载目录
        File file = new File(DOWNLOAD_DIR);
        if (!file.exists()) {
            //不存在，则创建目录
            file.mkdirs();//将路径中的每一层文件夹都创建
        }

        return mInstance;
    }


    /**
     * 下载的方法
     */
    public void download(DownloadTargetInfo targetDownloadInfo) {
        try {
            //1.首先获取当前任务对应的DownloadINfo数据
            DownloadTaskInfo downloadInfo = dbManager.selector(DownloadTaskInfo.class).where("id", "=", targetDownloadInfo.getId()).findFirst();
            if (downloadInfo == null) {
                //说明是第一次下载，需要创建DownloadInfo，并存放到downloadInfoMap中
                downloadInfo = DownloadTaskInfo.create(targetDownloadInfo);
                dbManager.save(downloadInfo);
            }
            //2.根据downloadInfo的state判断是否能够进行下载，在这些状态下才能下载：none,pause,error
            if (downloadInfo.getState() == STATE_NONE || downloadInfo.getState() == STATE_PAUSE
                    || downloadInfo.getState() == STATE_ERROR) {
                //可以进行下载操作
                //创建/获取（用于暂停恢复）下载任务
                DownloadTask downloadTask = downloadTaskMap.get(downloadInfo.getId());
                if (downloadTask == null) {
                    downloadTask = new DownloadTask(downloadInfo);
                    downloadTaskMap.put(downloadInfo.getId(), downloadTask);//将downloadTask维护起来
                }

                //因为downloadTask可能会放入缓存队列等待，所以讲它的state设置waiting
                downloadInfo.setState(STATE_WAITING);//更改下载状态

                notifyDownloadStateChange(downloadInfo);//通知所有监听器状态改变

                //将下载任务交给线程池管理
                ThreadPoolManager.getInstance().execute(downloadTask);
            }
        } catch (DbException e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * 暂停的方法
     */
    public void pause(DownloadTargetInfo targetDownloadInfo) {
        try {
            DownloadTaskInfo downloadInfo = dbManager.selector(DownloadTaskInfo.class).where("id", "=", targetDownloadInfo.getId()).findFirst();
            if (downloadInfo != null && downloadInfo.getState() != STATE_FINISH) {
                //首先将状态设置为pause，然后通知监听器状态更改
                downloadInfo.setState(STATE_PAUSE);
                notifyDownloadStateChange(downloadInfo);

                //再取出对应的downloadTask对象，及时从线程池中移除，为等待的任务及时腾出系统资源
                DownloadTask downloadTask = downloadTaskMap.get(downloadInfo.getId());
                downloadTask.stop();
                ThreadPoolManager.getInstance().remove(downloadTask);
            }
        } catch (DbException e) {
            e.printStackTrace();

        } finally {
        }
    }

    /**
     * 只移除任务的方法
     */
    public void removeOnlyTask(DownloadTargetInfo targetDownloadInfo) {
        try {
            DownloadTaskInfo downloadInfo = dbManager.selector(DownloadTaskInfo.class).where("id", "=", targetDownloadInfo.getId()).findFirst();
            if (downloadInfo != null) {
                //再取出对应的downloadTask对象，及时从线程池中移除，为等待的任务及时腾出系统资源
                DownloadTask downloadTask = dbManager.selector(DownloadTask.class).where("id", "=", downloadInfo.getId()).findFirst();
                ThreadPoolManager.getInstance().remove(downloadTask);
            }
        } catch (DbException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 移除任务和文件的方法
     */
    public void removeTaskAndFile(DownloadTargetInfo targetDownloadInfo) {
        try {
            DownloadTaskInfo downloadInfo = dbManager.selector(DownloadTaskInfo.class).where("id", "=", targetDownloadInfo.getId()).findFirst();
            if (downloadInfo == null) return;
            File file = new File(downloadInfo.getPath());
            dbManager.delete(DownloadTaskInfo.class, WhereBuilder.b("id", "=", targetDownloadInfo.getId()));
            file.deleteOnExit();
            if (downloadInfo != null) {
                //再取出对应的downloadTask对象，及时从线程池中移除，为等待的任务及时腾出系统资源
                DownloadTask downloadTask = dbManager.selector(DownloadTask.class).where("id", "=", downloadInfo.getId()).findFirst();
                ThreadPoolManager.getInstance().remove(downloadTask);
            }
        } catch (DbException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 重新下载的方法
     */
    public void reDownload(DownloadTargetInfo targetDownloadInfo) {
        //1.首先获取当前任务对应的DownloadINfo数据
        removeTaskAndFile(targetDownloadInfo);
        download(targetDownloadInfo);
    }


    /**
     * 通知监听器状态改变的回调
     */
    @Override
    public void notifyDownloadStateChange(DownloadTaskInfo downloadInfo) {
        for (DownloadObserver observer : downloadObserverList) {
            observer.onDownloadStateChange(downloadInfo);
        }
    }

    /**
     * 通知监听器下载进度改变的回调
     */
    @Override
    public void notifyDownloadProgressChange(DownloadTaskInfo downloadInfo) {
        for (DownloadObserver observer : downloadObserverList) {
            observer.onDownloadProgressChange(downloadInfo);
        }
    }


    /**
     * 注册一个下载监听器对象
     *
     * @param downloadObserver
     */
    public void registerDownloadObserver(DownloadObserver downloadObserver) {
        if (downloadObserver != null && !downloadObserverList.contains(downloadObserver)) {
            downloadObserverList.add(downloadObserver);
        }
    }

    /**
     * 移除一个下载监听器对象
     */
    public void unregisterDownloadObserver(DownloadObserver downloadObserver) {
        if (downloadObserver != null && downloadObserverList.contains(downloadObserver)) {
            downloadObserverList.remove(downloadObserver);
        }
    }

    /**
     * 下载过程的监听器
     *
     * @author Administrator
     */
    public interface DownloadObserver {
        /**
         * 当下载状态改变的回调
         */
        void onDownloadStateChange(DownloadTaskInfo downloadInfo);

        /**
         * 当下载进度改变的回调
         */
        void onDownloadProgressChange(DownloadTaskInfo downloadInfo);
    }

    public static String getBASE_URL() {
        return BASE_URL;
    }

    public static void setBASE_URL(String BASE_URL2) {
        BASE_URL = BASE_URL2;
    }

    class DownloadTask implements Runnable {
        private DownloadTaskInfo downloadInfo;
        private Subscription subscribe;


        public DownloadTask(DownloadTaskInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        /**
         * 结束网络执行,因为线程池remove一个task之后，网络请求并不会自动暂停。
         */
        public void stop() {
            if (subscribe != null && !subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
            }
        }

        @Override
        public void run() {
            //3.一旦run方法执行，说明进入下载中的状态，所以要更改状态
            downloadInfo.setState(DownloadManager.STATE_DOWNLOADING);//设置为下载中的状态
            notifyDownloadStateChange(downloadInfo);//通知监听器状态更改

            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(downloadInfo.getBaseUrl());
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());  //添加 RxJava 适配器
            Retrofit retrofit = builder.build();
            DownloadService downloadService = retrofit.create(DownloadService.class);
            Observable<ResponseBody> observable;
            //4.开始真正的下载操作,分2种情况：a.从头下载    b.断点下载
            final File file = new File(downloadInfo.getPath());
            if (!file.exists() || file.length() != downloadInfo.getCurrentLength()) {
                //没有下载或者文件保存出错,都是需要重新下载的情况
                file.delete();//删除无效文件
                downloadInfo.setCurrentLength(0);//清空已经下载的长度
                observable = downloadService.download(downloadInfo.getDownloadUrl());
            } else {
                //属于断点下载的情况,
                observable = downloadService.download("bytes=" + downloadInfo.getCurrentLength() + "-", downloadInfo.getDownloadUrl());
            }

            subscribe = observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: ");
                            e.printStackTrace();
                            //说明请求文件数据失败,属于下载失败的情况
                            file.delete();//删除失败文件
                            downloadInfo.setCurrentLength(0);//情况已经下载的长度
                            downloadInfo.setState(DownloadManager.STATE_ERROR);//设置状态为下载失败
                            notifyDownloadStateChange(downloadInfo);//通知监听器状态更改
                            try {
                                dbManager.saveOrUpdate(downloadInfo);
                            } catch (DbException e1) {
                                e1.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            //4.可以根据服务器返回的数据进行IO操作
                            InputStream is = responseBody.byteStream();
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file, true);
                                byte[] buffer = new byte[1024 * 8];//8k的缓冲区
                                int len = -1;
                                while ((len = is.read(buffer)) != -1 && downloadInfo.getState() == DownloadManager.STATE_DOWNLOADING) {
                                    fos.write(buffer, 0, len);
                                    //需要实时更新currentLength
                                    downloadInfo.setCurrentLength(downloadInfo.getCurrentLength() + len);
                                    notifyDownloadProgressChange(downloadInfo);//通知下载进度更新
                                }

                                //5.走到这里有几种情况:a.finish  b.pause c.失败的情况,加这个情况可以增强代码的健壮性
                                if (file.length() == downloadInfo.getSize() && downloadInfo.getState() == DownloadManager.STATE_DOWNLOADING) {
                                    //属于下载完成的情况
                                    downloadInfo.setState(DownloadManager.STATE_FINISH);//将状态设置为下载完成
                                    notifyDownloadStateChange(downloadInfo);
                                    //当run方法结束了，需要将downloadTask从downloadTaskMap中移除，因为没有必要维护了
                                    downloadTaskMap.remove(downloadInfo.getId());
                                } else if (downloadInfo.getState() == DownloadManager.STATE_PAUSE) {
                                    //通知监听器状态更改
                                    notifyDownloadStateChange(downloadInfo);
                                } else if (file.length() != downloadInfo.getCurrentLength()) {
                                    //说明保存文件出错
                                    file.delete();//删除失败文件
                                    downloadInfo.setCurrentLength(0);//情况已经下载的长度

                                    downloadInfo.setState(DownloadManager.STATE_ERROR);//设置状态为下载失败
                                    notifyDownloadStateChange(downloadInfo);//通知监听器状态更改
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
//                                if(e instanceof InterruptedIOException){    //占暂停会走此
                                    downloadInfo.setState(DownloadManager.STATE_PAUSE);//设置状态为下载失败
                                    notifyDownloadStateChange(downloadInfo);//通知监听器状态更改
//                                }else{
//                                    //如果出现异常，属于下载失败的情况
//                                    file.delete();//删除失败文件
//                                    downloadInfo.setCurrentLength(0);//情况已经下载的长度
//                                    downloadInfo.setState(DownloadManager.STATE_ERROR);//设置状态为下载失败
//                                    notifyDownloadStateChange(downloadInfo);//通知监听器状态更改
//                                }
                            } finally {
                                //关闭流和链接
                                try {
                                    if (fos != null) {
                                        fos.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        dbManager.saveOrUpdate(downloadInfo);
                                    } catch (DbException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                        }
                    });


        }

    }
}
