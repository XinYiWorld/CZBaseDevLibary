package xinyi.com.czdevmodule.download;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;
import com.xinyi.czbasedevtool.base.main.DefaultBaseAppCompatActivity;
import com.xinyi.czbasedevtool.base.utils.download.DownloadManager;
import com.xinyi.czbasedevtool.base.utils.download.DownloadTargetInfo;
import com.xinyi.czbasedevtool.base.utils.download.DownloadTaskInfo;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xinyi.com.czdevmodule.R;

/**
 * Created by 陈章 on 2017/6/26 0026.
 * func:多任务下载页面
 */
public class ManyTasksDownloadActivity extends DefaultBaseAppCompatActivity {
    @BindView(R.id.prg1)
    ProgressBar prg1;
    @BindView(R.id.tv_progress1)
    TextView tvProgress1;
    @BindView(R.id.tv_state1)
    TextView tvState1;
    @BindView(R.id.btn_download1)
    Button btnDownload1;

    @BindView(R.id.prg2)
    ProgressBar prg2;
    @BindView(R.id.tv_progress2)
    TextView tvProgress2;
    @BindView(R.id.tv_state2)
    TextView tvState2;
    @BindView(R.id.btn_download2)
    Button btnDownload2;


    @BindView(R.id.prg3)
    ProgressBar prg3;
    @BindView(R.id.tv_progress3)
    TextView tvProgress3;
    @BindView(R.id.tv_state3)
    TextView tvState3;
    @BindView(R.id.btn_download3)
    Button btnDownload3;

    

    private final int ONDOWNLOADSTATECHANGE1 = 100;
    private final int ONDOWNLOADPROGRESSCHANGE1 = 101;

    private final int ONDOWNLOADSTATECHANGE2 = 102;
    private final int ONDOWNLOADPROGRESSCHANGE2 = 103;


    private final int ONDOWNLOADSTATECHANGE3 = 104;
    private final int ONDOWNLOADPROGRESSCHANGE3 = 105;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ONDOWNLOADSTATECHANGE1:
                    tvState1.setText((String) msg.obj);
                    break;
                case ONDOWNLOADPROGRESSCHANGE1:
                    prg1.setProgress((Integer) msg.obj);
                    tvProgress1.setText(msg.obj + "%");
                    break;


                case ONDOWNLOADSTATECHANGE2:
                    tvState2.setText((String) msg.obj);
                    break;
                case ONDOWNLOADPROGRESSCHANGE2:
                    prg2.setProgress((Integer) msg.obj);
                    tvProgress2.setText(msg.obj + "%");
                    break;


                case ONDOWNLOADSTATECHANGE3:
                    tvState3.setText((String) msg.obj);
                    break;
                case ONDOWNLOADPROGRESSCHANGE3:
                    prg3.setProgress((Integer) msg.obj);
                    tvProgress3.setText(msg.obj + "%");
                    break;



            }
        }
    };
    private DownloadManager downloadManager;
    private DownloadTargetInfo downloadTargetInfo1;
    private DownloadTargetInfo downloadTargetInfo2;
    private DownloadTargetInfo downloadTargetInfo3;

    @Override
    public void annotationBind(@NonNull Activity target, View view) {
        super.annotationBind(target, view);
        ButterKnife.bind(this);
    }

    @Override
    public boolean useDataBinding() {
        return false;
    }

    @Override
    public <T> void onSuccess(int requestCode, BaseHttpResponseBean codeInfoBean, T successBean) {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activit_download_many_tasks;
    }

    @Override
    public <BindingObj> void bindView(BindingObj binding) throws IOException {

    }

    @Override
    public void bindView(ContentViewHolder contentViewHolder) throws IOException {
        super.bindView(contentViewHolder);
        prg1.setMax(100);
        prg2.setMax(100);
        prg3.setMax(100);

        downloadTargetInfo1 = new DownloadTargetInfo("images/newl.flv", "图片1", "男儿无泪.flv", 9224820);
        downloadTargetInfo1.setId(0);

        downloadTargetInfo2 = new DownloadTargetInfo("images/huaxin.flv", "图片1", "画心.flv", 6862755);
        downloadTargetInfo2.setId(1);

        downloadTargetInfo3 = new DownloadTargetInfo("images/qinshi.mp4", "图片1", "琴师.mp4", 19274609);
        downloadTargetInfo3.setId(2);

        downloadManager = DownloadManager.getInstance(mContext.getApplicationContext());
        downloadManager.setBASE_URL("http://192.168.56.1/");
        downloadManager.registerDownloadObserver(new DownloadManager.DownloadObserver() {
            @Override
            public void onDownloadStateChange(DownloadTaskInfo downloadInfo) {
                if(downloadInfo.getId() == downloadTargetInfo1.getId()){
                    Log.i(TAG, "ONDOWNLOADSTATECHANGE1: " + downloadInfo.getState());
                    mHandler.obtainMessage(ONDOWNLOADSTATECHANGE1, getDownloadTxt(downloadInfo.getState())).sendToTarget();
                }else if(downloadInfo.getId() == downloadTargetInfo2.getId()){
                    Log.i(TAG, "ONDOWNLOADSTATECHANGE2: " + downloadInfo.getState());
                    mHandler.obtainMessage(ONDOWNLOADSTATECHANGE2, getDownloadTxt(downloadInfo.getState())).sendToTarget();
                }else if(downloadInfo.getId() == downloadTargetInfo3.getId()){
                    Log.i(TAG, "ONDOWNLOADSTATECHANGE3: " + downloadInfo.getState());
                    mHandler.obtainMessage(ONDOWNLOADSTATECHANGE3, getDownloadTxt(downloadInfo.getState())).sendToTarget();
                }
            }

            @Override
            public void onDownloadProgressChange(DownloadTaskInfo downloadInfo) {
                if(downloadInfo.getId() == downloadTargetInfo1.getId()){
                    long currentLength = downloadInfo.getCurrentLength();
                    long size = downloadInfo.getSize();

                    Log.i(TAG, "ONDOWNLOADPROGRESSCHANGE1: " + currentLength);
                    mHandler.obtainMessage(ONDOWNLOADPROGRESSCHANGE1, (int) (currentLength * 100.0 / size)).sendToTarget();
                }else if(downloadInfo.getId() == downloadTargetInfo2.getId()){
                    long currentLength = downloadInfo.getCurrentLength();
                    long size = downloadInfo.getSize();

                    Log.i(TAG, "ONDOWNLOADPROGRESSCHANGE2 " + currentLength);
                    mHandler.obtainMessage(ONDOWNLOADPROGRESSCHANGE2, (int) (currentLength * 100.0 / size)).sendToTarget();
                }else if(downloadInfo.getId() == downloadTargetInfo3.getId()){
                    long currentLength = downloadInfo.getCurrentLength();
                    long size = downloadInfo.getSize();

                    Log.i(TAG, "ONDOWNLOADPROGRESSCHANGE3: " + currentLength);
                    mHandler.obtainMessage(ONDOWNLOADPROGRESSCHANGE3, (int) (currentLength * 100.0 / size)).sendToTarget();
                }
            }
        });


    }


    @Override
    public void doOnCreateInit() {

    }


    private String getDownloadTxt(int code) {
        switch (code) {
            case 0:
                return "未下载";
            case 1:
                return "下载中";
            case 2:
                return "暂停";
            case 3:
                return "下载完成";
            case 4:
                return "下载出错";
            case 5:
                return "等待中";
            default:
                return "";
        }
    }

    @OnClick({R.id.btn_download1, R.id.btn_del_task1, R.id.btn_del_task_and_file1, R.id.btn_re_download1})
    public void onClick1(View view) {
        switch (view.getId()) {
            case R.id.btn_download1:
                if (btnDownload1.getText().equals("开始下载")) {
                    btnDownload1.setText("暂停");
                    downloadManager.download(downloadTargetInfo1);
                } else {
                    btnDownload1.setText("开始下载");
                    downloadManager.pause(downloadTargetInfo1);
                }
                break;
            case R.id.btn_del_task1:
                downloadManager.removeOnlyTask(downloadTargetInfo1);
                prg1.setProgress(0);
                break;
            case R.id.btn_del_task_and_file1:
                downloadManager.removeTaskAndFile(downloadTargetInfo1);
                prg1.setProgress(0);
                break;
            case R.id.btn_re_download1:
                downloadManager.reDownload(downloadTargetInfo1);
                break;
        }
    }

    @OnClick({R.id.btn_download2, R.id.btn_del_task2, R.id.btn_del_task_and_file2, R.id.btn_re_download2})
    public void onClick2(View view) {
        switch (view.getId()) {
            case R.id.btn_download2:
                if (btnDownload2.getText().equals("开始下载")) {
                    btnDownload2.setText("暂停");
                    downloadManager.download(downloadTargetInfo2);
                } else {
                    btnDownload2.setText("开始下载");
                    downloadManager.pause(downloadTargetInfo2);
                }
                break;
            case R.id.btn_del_task2:
                downloadManager.removeOnlyTask(downloadTargetInfo2);
                prg2.setProgress(0);
                break;
            case R.id.btn_del_task_and_file2:
                downloadManager.removeTaskAndFile(downloadTargetInfo2);
                prg2.setProgress(0);
                break;
            case R.id.btn_re_download2:
                downloadManager.reDownload(downloadTargetInfo2);
                break;
        }
    }


    @OnClick({R.id.btn_download3, R.id.btn_del_task3, R.id.btn_del_task_and_file3, R.id.btn_re_download3})
    public void onClick3(View view) {
        switch (view.getId()) {
            case R.id.btn_download3:
                if (btnDownload3.getText().equals("开始下载")) {
                    btnDownload3.setText("暂停");
                    downloadManager.download(downloadTargetInfo3);
                } else {
                    btnDownload3.setText("开始下载");
                    downloadManager.pause(downloadTargetInfo3);
                }
                break;
            case R.id.btn_del_task3:
                downloadManager.removeOnlyTask(downloadTargetInfo3);
                prg3.setProgress(0);
                break;
            case R.id.btn_del_task_and_file3:
                downloadManager.removeTaskAndFile(downloadTargetInfo3);
                prg3.setProgress(0);
                break;
            case R.id.btn_re_download3:
                downloadManager.reDownload(downloadTargetInfo3);
                break;
        }
    }
}
