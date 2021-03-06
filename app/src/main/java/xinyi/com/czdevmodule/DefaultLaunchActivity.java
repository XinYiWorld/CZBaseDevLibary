package xinyi.com.czdevmodule;

import android.content.Intent;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;
import com.xinyi.czbasedevtool.base.main.DefaultBaseAppCompatActivity;
import com.xinyi.czbasedevtool.base.manager.PermissionManager;
import com.xinyi.czbasedevtool.base.manager.ui_about.FragmentMaster;
import com.xinyi.czbasedevtool.base.utils.TLog;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;

import java.io.IOException;

/**
 * Created by 陈章 on 2017/2/13 0013.
 * func:
 */

public class DefaultLaunchActivity extends DefaultBaseAppCompatActivity {
    private static final String TAG = "DefaultLaunchActivity";

    @Override
    public boolean useDataBinding() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (PermissionManager.getInstance(this).isFloatWindowOpAllowed(this)) {//已经开启
                TLog.i(TAG, "onActivityResult:开启悬浮窗成功");
            } else {
                TLog.i(TAG, "onActivityResult:开启悬浮窗失败");
            }
        }
    }

    @Override
    public <T> void onSuccess(int requestCode, BaseHttpResponseBean codeInfoBean, T successBean) {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_demo;
    }

    @Override
    public String getLeftTextString() {
        return "dfafasdf";
    }

    @Override
    public String getRightTextString() {
        return "gfdsgdsg";
    }


    @Override
    public <BindingObj> void bindView(BindingObj binding) throws IOException {

    }

    @Override
    public void bindView(ContentViewHolder contentViewHolder) throws IOException {
        FragmentMaster aa = new FragmentMaster(this,getSupportFragmentManager());
        aa.begin();
        aa.showFragment(R.id.content,new Fragment1());
        aa.commit();


        PermissionManager.getInstance(this).requestFloatWindowPermission(100);
    }

    @Override
    public void doOnCreateInit() {

    }
}
