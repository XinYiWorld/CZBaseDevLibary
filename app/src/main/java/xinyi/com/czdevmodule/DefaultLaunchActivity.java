package xinyi.com.czdevmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.xinyi.czbasedevtool.base.manager.ui_about.FragmentMaster;

/**
 * Created by 陈章 on 2017/2/13 0013.
 * func:
 */

public class DefaultLaunchActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        FragmentMaster aa = new FragmentMaster(this,getSupportFragmentManager());
        aa.begin();
        aa.showFragment(R.id.content,new Fragment1());
        aa.commit();
    }
}
