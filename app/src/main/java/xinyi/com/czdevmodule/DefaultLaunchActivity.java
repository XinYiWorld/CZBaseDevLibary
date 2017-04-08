package xinyi.com.czdevmodule;

import android.content.Intent;
import android.util.Log;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;
import com.xinyi.czbasedevtool.base.main.DefaultBaseAppCompatActivity;
import com.xinyi.czbasedevtool.base.manager.PermissionManager;
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
                Log.i(TAG, "onActivityResult:开启悬浮窗成功");
            } else {
                Log.i(TAG, "onActivityResult:开启悬浮窗失败");
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
    public int getTitleStringRes() {
        return R.string.appbar_scrolling_view_behavior;
    }

    @Override
    public <BindingObj> void bindView(BindingObj binding) throws IOException {

    }

    @Override
    public void bindView(ContentViewHolder contentViewHolder) throws IOException {
//        FragmentMaster aa = new FragmentMaster(this,getSupportFragmentManager());
//        aa.begin();
//        aa.showFragment(R.id.content,new Fragment1());
//        aa.commit();
//
//
//        PermissionManager.getInstance(this).requestFloatWindowPermission(100);
    }

    @Override
    public void doOnCreateInit() {

    }
}
