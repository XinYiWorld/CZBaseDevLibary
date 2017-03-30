package com.xinyi.czbasedevtool.base.utils;

import android.widget.Toast;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

/**
 * author:Created by ZhangChen on 2016/7/27 0027.
 * detail:
 */
public class SystemToastUtil {
    private static Toast toast;
    static {
        toast = Toast.makeText(SystemStaticInstanceManager.getmApplicationContext(), "", Toast.LENGTH_SHORT);
    }

    //短吐司
    public static void shortT(String msg){
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    //长吐司
    public static void longT(String msg){
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }
}
