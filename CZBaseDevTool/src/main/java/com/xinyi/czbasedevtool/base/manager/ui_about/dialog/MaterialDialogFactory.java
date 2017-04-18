package com.xinyi.czbasedevtool.base.manager.ui_about.dialog;


import android.content.Context;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.utils.ResourceUtil;
import com.xinyi.czbasedevtool.base.utils.TLog;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * github:          https://github.com/afollestad/material-dialogs
 * 1.对于确定和取消按钮的回调，如果传入null，按钮不会显示。
 * 2.不同形式对话框的Builder不能共用
 * 3.传入的字符如title等如果为"",对应的按钮仍然会占那一行的高度。
 *
 */
public class MaterialDialogFactory{
    private static final String TAG = "MaterialDialogFactory";
    private  MaterialDialog.Builder normalMaterialDialogBuilder = null;
    private  MaterialDialog.Builder listMaterialDialogBuilder = null;
    private  MaterialDialog.Builder choiceMaterialDialogBuilder = null;
    private  MaterialDialog.Builder progressMaterialDialogBuilder = null;

    private static final int DEFAULT_TITLE_COLORRES = R.color.black;


    private static MaterialDialogFactory mInstance = new MaterialDialogFactory();
    private static Context mContext;
    private MaterialDialog progressMaterialDialog;

    private MaterialDialogFactory(){
        TLog.i(TAG, "MaterialDialogFactory inited");
    }

    public static MaterialDialogFactory getInstance() {
        return getInstance(null);
    }
    
    public static MaterialDialogFactory getInstance(Context context) {
        mContext = context;
        return mInstance;
    }


    /**
     * MaterialDialog
     * @param title
     * @param msg
     * @param pText
     * @param pListener
     * @param nListener
     */
    //默认取消按钮是”取消"两个字
    public  MaterialDialog createMaterialDialog(String title,String msg,String pText,MaterialDialog.SingleButtonCallback pListener,MaterialDialog.SingleButtonCallback nListener ,int ... iconRes){
        return createMaterialDialog(title,msg,pText,"取消",pListener,nListener,iconRes);
    }

    //可以定制取消按钮的文字
    public  MaterialDialog createMaterialDialog(String title,String msg,String pText,String nText,MaterialDialog.SingleButtonCallback pListener,MaterialDialog.SingleButtonCallback nListener ,int ... iconRes){
//        if(normalMaterialDialogBuilder == null){
//            normalMaterialDialogBuilder = new MaterialDialog.Builder(context);
//        }else{
//            if(normalMaterialDialogBuilder.getContext() != context){
//                normalMaterialDialogBuilder = new MaterialDialog.Builder(context);
//            }
//        }
        normalMaterialDialogBuilder = new MaterialDialog.Builder(mContext);

        normalMaterialDialogBuilder.backgroundColorRes(android.R.color.white);
        normalMaterialDialogBuilder.cancelable(true);
        if(iconRes != null && iconRes.length > 0){
            normalMaterialDialogBuilder.iconRes(iconRes[0]);
        }
        if(!TextUtils.isEmpty(title)){
            normalMaterialDialogBuilder.title(title);
            normalMaterialDialogBuilder.titleColorRes(android.R.color.black);
        }
        if(!TextUtils.isEmpty(msg)){
            normalMaterialDialogBuilder.content(msg);
        }
        if(!TextUtils.isEmpty(pText)&&pListener!=null){
            normalMaterialDialogBuilder.onPositive(pListener);
            normalMaterialDialogBuilder.positiveText(pText);
        }else if(TextUtils.isEmpty(pText)&&pListener!=null){
            normalMaterialDialogBuilder.onPositive(pListener);
            normalMaterialDialogBuilder.positiveText("确定");
        }
        if(nListener!=null && !TextUtils.isEmpty(nText)){
            normalMaterialDialogBuilder.onNegative(nListener);
            normalMaterialDialogBuilder.negativeText(nText);
        }
        return normalMaterialDialogBuilder.show();
    }


    /**
     * 列表对话框
     * @param title
     * @param itemRes
     * @param pText
     * @param listCallback
     * @param pListener
     */
    public  MaterialDialog createListMaterialDialog(String title,int itemRes,String pText,MaterialDialog.ListCallback listCallback,MaterialDialog.SingleButtonCallback pListener){
//        if(listMaterialDialogBuilder == null){
//            listMaterialDialogBuilder = new MaterialDialog.Builder(mContext);
//        }else{
//            if(listMaterialDialogBuilder.getContext() != mContext){
//                listMaterialDialogBuilder = new MaterialDialog.Builder(mContext);
//            }
//        }

        listMaterialDialogBuilder = new MaterialDialog.Builder(mContext);

        if(!TextUtils.isEmpty(title)){
            listMaterialDialogBuilder.title(title);
            listMaterialDialogBuilder.titleColorRes(DEFAULT_TITLE_COLORRES);
        }


        if(!TextUtils.isEmpty(pText)&&pListener!=null){
            listMaterialDialogBuilder.onPositive(pListener);
            listMaterialDialogBuilder.positiveText(pText);
        }else if(TextUtils.isEmpty(pText)&&pListener!=null){
            listMaterialDialogBuilder.onPositive(pListener);
            listMaterialDialogBuilder.positiveText("确定");
        }

        listMaterialDialogBuilder.items(itemRes);
        if(listCallback != null){
            listMaterialDialogBuilder.itemsCallback(listCallback);
        }

        return listMaterialDialogBuilder.show();
    }


    /**
     * 单选对话框
     * @param title
     * @param itemRes
     * @param pText
     * @param listCallback
     * @param selectIndex
     * @param pListener
     */
    public  MaterialDialog createChoiceListMaterialDialog(String title,int itemRes,String pText,MaterialDialog.ListCallbackSingleChoice listCallback,int selectIndex,MaterialDialog.SingleButtonCallback pListener){
//        if(choiceMaterialDialogBuilder == null){
//            choiceMaterialDialogBuilder = new MaterialDialog.Builder(mContext);
//        }else{
//            if(choiceMaterialDialogBuilder.getContext() != mContext){
//                choiceMaterialDialogBuilder = new MaterialDialog.Builder(mContext);
//            }
//        }

        choiceMaterialDialogBuilder = new MaterialDialog.Builder(mContext);

        if(!TextUtils.isEmpty(title)){
            choiceMaterialDialogBuilder.title(title);
            choiceMaterialDialogBuilder.titleColorRes(DEFAULT_TITLE_COLORRES);
        }


        if(!TextUtils.isEmpty(pText)&&pListener!=null){
            choiceMaterialDialogBuilder.onPositive(pListener);
            choiceMaterialDialogBuilder.positiveText(pText);
        }else if(TextUtils.isEmpty(pText)&&pListener!=null){
            choiceMaterialDialogBuilder.onPositive(pListener);
            choiceMaterialDialogBuilder.positiveText("确定");
        }

        choiceMaterialDialogBuilder.items(itemRes);
        if(listCallback != null){
            choiceMaterialDialogBuilder.itemsCallbackSingleChoice(selectIndex, listCallback);
        }

        return choiceMaterialDialogBuilder.show();
    }

    /**
     * 进度对话框  带标题和提示信息
     * @param title
     * @param content
     */
    public  MaterialDialog createProgressMaterialDialog(String title,String content){
//        if(progressMaterialDialogBuilder== null){
//            progressMaterialDialogBuilder = new MaterialDialog.Builder(mContext);
//        }else{
//            if(progressMaterialDialogBuilder.getContext() != mContext){
//                progressMaterialDialogBuilder = new MaterialDialog.Builder(mContext);
//            }
//        }
        progressMaterialDialogBuilder = new MaterialDialog.Builder(mContext);


        if(progressMaterialDialog != null){
            progressMaterialDialog.setTitle(title);
            progressMaterialDialog.setContent(content);

            if(!progressMaterialDialog.isShowing()){
                progressMaterialDialog.show();
            }
            return progressMaterialDialog;
        }

        progressMaterialDialog = progressMaterialDialogBuilder.title(title)
                .content(content)
                .progress(true, 0)
                .show();
        return progressMaterialDialog;
    }


    /**
     * 进度对话框  仅仅只显示进度条
     */
    public  MaterialDialog createSimpleProgressMaterialDialog(){
        return  createProgressMaterialDialog(null,null);
    }

    /**
     * 进度对话框  仅仅只显示进度条+提示信息，不带标题
     */
    public  MaterialDialog createMsgProgressMaterialDialog(String msg){
        return createProgressMaterialDialog(null,msg);
    }

    /**
     * 进度对话框  仅仅只显示进度条+提示信息，不带标题
     */
    public  MaterialDialog createMsgProgressMaterialDialog(int msgRes){
        return createProgressMaterialDialog(null, ResourceUtil.getString(msgRes));
    }


    /**
     * 关闭进度对话框
     */
    public  void hideProgressMaterialDialog(){
       if(progressMaterialDialog != null && progressMaterialDialog.isShowing()){
           progressMaterialDialog.dismiss();        //注意是dimiss而不是hide，hide仅仅是Visibility的改变，不会改变isShowing的变化.
       }
    }

}
