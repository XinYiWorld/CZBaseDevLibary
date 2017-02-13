package com.xinyi.czbasedevtool.base.manager.ui_about.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;


/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * 系统自带的对话框
 */
public class SystemDialogFactory {
    private static AlertDialog.Builder alertDialogBuilder;
    private static ProgressDialog progDialog;

    private SystemDialogFactory(){
        throw new RuntimeException("SystemDialogFactory can't init");
    }
    /***
     * 获取一个dialog
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        if(alertDialogBuilder == null ){
            alertDialogBuilder = new AlertDialog.Builder(context);
        }else{
            if(alertDialogBuilder.getContext() != context){
                alertDialogBuilder = new AlertDialog.Builder(context);
            }
        }

        return alertDialogBuilder;
    }

    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        return builder;
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }


    public static AlertDialog.Builder getViewDialog(Context context, String title, View view , DialogInterface.OnClickListener onOkClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("确定", onOkClickListener);
        return builder;
    }


    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }



    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onItemClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onItemClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onItemClickListener) {
        return getSelectDialog(context, "", arrays, onItemClickListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onItemClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onItemClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays, int selectIndex, DialogInterface.OnClickListener onItemClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onItemClickListener);
    }

    public static ProgressDialog getProgressDialog(Context context,String ... msg){
        if (progDialog == null) {  //每次都要创建新的对象，否则conext引用过期导致崩溃。
            progDialog = new ProgressDialog(context);
        }else{
            if(progDialog.getContext() != context){
                progDialog = new ProgressDialog(context);
            }
        }

        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        if(msg != null && msg.length > 0){
            progDialog.setMessage(msg[0]);
        }else{
            progDialog.setMessage("正在加载...");
        }
        return progDialog;
    }

    public static ProgressDialog getProgressDialog(){
        ExceptionUtil.throwNullPointerException(progDialog);
        return progDialog;
    }
}
