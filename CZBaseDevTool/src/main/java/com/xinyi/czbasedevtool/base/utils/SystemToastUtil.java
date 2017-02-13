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

    public static void shortT(String msg){
        toast.setText(msg);
        toast.show();
    }
}
