package xinyi.com.czdevmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.xinyi.czbasedevtool.base.manager.PermissionManager;
import com.xinyi.czbasedevtool.base.manager.ui_about.FragmentMaster;

/**
 * Created by 陈章 on 2017/2/13 0013.
 * func:
 */

public class DefaultLaunchActivity extends FragmentActivity {
    private static final String TAG = "DefaultLaunchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        FragmentMaster aa = new FragmentMaster(this,getSupportFragmentManager());
        aa.begin();
        aa.showFragment(R.id.content,new Fragment1());
        aa.commit();


        PermissionManager.getInstance(this).requestFloatWindowPermission(100);
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
}
