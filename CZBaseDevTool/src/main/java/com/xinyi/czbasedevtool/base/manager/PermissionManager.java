package com.xinyi.czbasedevtool.base.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;
import com.xinyi.czbasedevtool.base.manager.ui_about.dialog.MaterialDialogFactory;
import com.xinyi.czbasedevtool.base.utils.SystemUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by 陈章 on 2017/3/30 0030.
 * func:
 */
public class PermissionManager {
    private static PermissionManager instance;
    private static Context mContext;


    private static Stack<Dialog> myDialogStack = new Stack<>();       //用栈来保存Dialog，防止在onStart方法里Dialog出现一次又一次。


    private PermissionManager(){}

    public static PermissionManager getInstance(Context mContext2) {
        mContext = mContext2;
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new PermissionManager();
                }
            }
        }
        return instance;
    }

//----------------------------------------------Android6.0权限适配-----------------------------------------------------
    /**
     * 获取指定权限集合中没有申请通过的权限集合
     * ?????6.0以下的机型可能检测到没有通过的权限，但是并不会弹出申请权限的对话框。
     * @param context
     * @param planToRequestPermissions
     * @return
     */
    public  List<String> getNotPassedPermissions(Context context, List<String> planToRequestPermissions){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {        //6.0以下则不作权限申请，即没有通过的权限为null即可。
            return null;
        }

        List<String> result = null;
        for(String plan : planToRequestPermissions){
            if(ContextCompat.checkSelfPermission(context, plan) == PackageManager.PERMISSION_DENIED){
                if(result == null){
                    result = new ArrayList<>();
                }
                result.add(plan);
            }
        }

        System.out.println("missions:" + result);
        return result;
    }

    /**
     * 提示用户同意申请的权限
     * @param context
     */
    public  Dialog showAgreePermissonDialog(Context context, final OnTaskDoneListener doNextListener){
        //关闭之前可能重复出现的Dialog。
        closeAllDialog();

        MaterialDialog materialDialog = MaterialDialogFactory.getInstance(context).createMaterialDialog(context, "权限申请", "接下来的权限提示请点击同意，否则无法往后执行！", "同意", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                myDialogStack.remove(dialog);
                doNextListener.done(null);
            }
        }, null, R.drawable.icon_warning_tip);

        return materialDialog;
    }


    private   void closeAllDialog(){
        if(myDialogStack.size() > 0){
            for(int i = 0; i < myDialogStack.size(); i++){
                Dialog dialog = myDialogStack.get(i);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }
    }

//    ---------------------------------------------001判断悬浮窗权限(影响Toast使用)-------------------------------------------------------------
    /**
     * 判断悬浮窗权限
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public   boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24);  // AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                return true;
            } else {
                return false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private  boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class<?> spClazz = Class.forName(manager.getClass().getName());
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op,
                        Binder.getCallingUid(), context.getPackageName());
                Log.e("399", " property: " + property);
                if (AppOpsManager.MODE_ALLOWED == property) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("399", "Below API 19 cannot invoke!");
        }
        return false;
    }


    /**
     * 请求用户给予悬浮窗的权限
     */
    public void requestFloatWindowPermission(final int requestCode) {
        if (isFloatWindowOpAllowed(mContext)) {//已经开启

        } else {
            MaterialDialogFactory.getInstance(mContext)
                    .createMaterialDialog(mContext, "重要提示:", "检测到您未开启悬浮窗权限,可能影响APP的部分功能使用，建议立即开启！", "立即开启", "取消",
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    SystemUtil.openSetting((Activity) mContext,requestCode);
                                }
                            }
                            , new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            }
                    );
        }
    }
}
