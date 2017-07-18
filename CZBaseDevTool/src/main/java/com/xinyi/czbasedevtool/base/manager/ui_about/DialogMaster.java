package com.xinyi.czbasedevtool.base.manager.ui_about;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.interfaces.view_about.ProgressView;
import com.xinyi.czbasedevtool.base.manager.ui_about.dialog.MaterialDialogFactory;
import com.xinyi.czbasedevtool.base.utils.ResourceUtil;

/**
 * 对话框操作封装类
 * author:Created by ZhangChen on 2016/7/27 0027.
 * detail:
 */
public class DialogMaster implements ProgressView {
    private Context mContext;
    private MaterialDialogFactory materialDialogFactory;
    private String customProgressMsg;               //定制进度提示信息，不同场景要求可能不一样。
    private static boolean temporaryDisableDialog;         //某些情景下，不需要显示对话框。
    public DialogMaster(Context mContext) {
        this.mContext = mContext;
        materialDialogFactory = MaterialDialogFactory.getInstance(mContext);
        customProgressMsg = ResourceUtil.getString(mContext, R.string.loading);
        temporaryDisableDialog = false;
    }

    public static void temporaryDisableDialog(){
        temporaryDisableDialog = true;
    }

    @Override
    public void onShowProgressDialog() {
        if(!temporaryDisableDialog){
             materialDialogFactory.createMsgProgressMaterialDialog(customProgressMsg);
        }
    }

    @Override
    public void onHideProgressDialog() {
        temporaryDisableDialog = false;
        materialDialogFactory.hideProgressMaterialDialog();
    }

    @Override
    public void onShowMessageDiaolg(@NonNull String msg) {
        if(!temporaryDisableDialog) {
            materialDialogFactory.createMsgProgressMaterialDialog(msg);
        }
    }

    @Override
    public void customProgressMsg(int msgRes) {
        customProgressMsg = ResourceUtil.getString(msgRes);
    }

    @Override
    public void customProgressMsg(String msg) {
        customProgressMsg = msg;
    }

    @Override
    public void reset() {
        customProgressMsg = ResourceUtil.getString(R.string.loading);
        temporaryDisableDialog = false;
    }

}
