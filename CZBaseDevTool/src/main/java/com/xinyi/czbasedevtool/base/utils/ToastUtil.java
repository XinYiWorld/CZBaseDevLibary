package com.xinyi.czbasedevtool.base.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyi.czbasedevtool.base.R;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class ToastUtil {

    private static TextView tv_toast;

    private ToastUtil(){
        throw new RuntimeException("ToastUtil can't init");
    }
    private static Toast toastInstance;

    @Deprecated
    public static void snakeShort(View decorView ,Object obj){
        if(decorView != null){
             Snackbar.make(decorView, obj.toString(), Snackbar.LENGTH_SHORT).show();
        }else{
             SystemToastUtil.shortT(obj.toString());
        }
    }

    public static void show(Context context, String msg, int showTime){
        if(toastInstance == null){
            toastInstance= Toast.makeText(context, "", Toast.LENGTH_SHORT);
            toastInstance.setView(LayoutInflater.from(context).inflate(R.layout.layout_my_toast,null,false));
            tv_toast = (TextView) toastInstance.getView().findViewById(R.id.tv_toast);
        }
        tv_toast.setText(msg);
        toastInstance.setDuration(showTime);
        toastInstance.show();
    }
}
