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
 * func:
 */

public class SimpleDownloadActivity extends DefaultBaseAppCompatActivity {
    @BindView(R.id.prg)
    ProgressBar prg;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.btn_download)
    Button btnDownload;

    private final int ONDOWNLOADSTATECHANGE = 100;
    private final int ONDOWNLOADPROGRESSCHANGE = 101;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ONDOWNLOADSTATECHANGE:
                    tvState.setText((String) msg.obj);
                    break;
                case ONDOWNLOADPROGRESSCHANGE:
                    prg.setProgress((Integer) msg.obj);
                    tvProgress.setText(msg.obj + "%");
                    break;
            }
        }
    };
    private DownloadManager downloadManager;
    private DownloadTargetInfo downloadTargetInfo;

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
        return R.layout.activit_download_simple;
    }

    @Override
    public <BindingObj> void bindView(BindingObj binding) throws IOException {

    }

    @Override
    public void bindView(ContentViewHolder contentViewHolder) throws IOException {
        super.bindView(contentViewHolder);
        prg.setMax(100);

        downloadTargetInfo = new DownloadTargetInfo("images/newl.flv", "图片1", "男儿无泪.flv", 9224820);
        downloadManager = DownloadManager.getInstance(mContext.getApplicationContext());
        downloadManager.setBASE_URL("http://192.168.56.1/");
        downloadManager.registerDownloadObserver(new DownloadManager.DownloadObserver() {
            @Override
            public void onDownloadStateChange(DownloadTaskInfo downloadInfo) {
                Log.i(TAG, "onDownloadStateChange: " + downloadInfo.getState());
                mHandler.obtainMessage(ONDOWNLOADSTATECHANGE, getDownloadTxt(downloadInfo.getState())).sendToTarget();
            }

            @Override
            public void onDownloadProgressChange(DownloadTaskInfo downloadInfo) {
                long currentLength = downloadInfo.getCurrentLength();
                long size = downloadInfo.getSize();

                Log.i(TAG, "onDownloadProgressChange: " + currentLength);
                mHandler.obtainMessage(ONDOWNLOADPROGRESSCHANGE, (int) (currentLength * 100.0 / size)).sendToTarget();

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

    @OnClick({R.id.btn_download, R.id.btn_del_task, R.id.btn_del_task_and_file, R.id.btn_re_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                if (btnDownload.getText().equals("开始下载")) {
                    btnDownload.setText("暂停");
                    downloadManager.download(downloadTargetInfo);
                } else {
                    btnDownload.setText("开始下载");
                    downloadManager.pause(downloadTargetInfo);
                }
                break;
            case R.id.btn_del_task:
                downloadManager.removeOnlyTask(downloadTargetInfo);
                break;
            case R.id.btn_del_task_and_file:
                downloadManager.removeTaskAndFile(downloadTargetInfo);
                break;
            case R.id.btn_re_download:
                downloadManager.reDownload(downloadTargetInfo);
                break;
        }
    }
}
