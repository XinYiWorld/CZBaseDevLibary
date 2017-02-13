package com.xinyi.czbasedevtool.base.manager.ui_about.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 陈章 on 2016/11/21 0021.
 * func:
 * 按照UI规范设计的自定义风格的对话框
 */

public class PersonalStyleDialogFactory {
//    public static class ViewHolder {
//        public AlertDialog dialog;
//        public ContentView contentView;
//
//        public ViewHolder(AlertDialog dialog, ContentView contentView) {
//            this.dialog = dialog;
//            this.contentView = contentView;
//        }
//
//
//    }
//
//    public static class ContentView {
//        private View root;
//        private Map<Integer, View> views;
//
//        public ContentView(View root) {
//            this.root = root;
//            views = new HashMap<>();
//        }
//
//        public <T extends  View> T findViewById(int resId) {
//            if (!views.containsKey(resId)) {
//                views.put(resId, root.findViewById(resId));
//            }
//            return (T) views.get(resId);
//        }
//    }
//
//    public static ViewHolder createDialog(Context context, int layout_res) {
//        return createDialog(context,layout_res,false);
//    }
//
//    public static ViewHolder createDialog(Context context, int layout_res,boolean touchOutside) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View customView = LayoutInflater.from(context).inflate(layout_res, null);
//        AlertDialog dialog = builder.create();
//        dialog.setView(customView);
//        dialog.show();
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);      //去掉window的默认边框
//        dialog.setCanceledOnTouchOutside(touchOutside);
//        //点击外部不消失
//        ViewHolder viewHolder = new ViewHolder(dialog, new ContentView(customView));
//        return viewHolder;
//    }
//
//
//    public static class BaseDialogBuilder {
//        protected Context mContext;
//        protected String negativeButtonTxt;
//        protected DialogInterface.OnClickListener negativeButtonClickListener;
//
//        protected String positiveButtonTxt;
//        protected DialogInterface.OnClickListener positiveButtonClickListener;
//
//        protected boolean showCloseIcon = false;
//
//        public BaseDialogBuilder(Context mContext) {
//            this.mContext = mContext;
//        }
//
//        public BaseDialogBuilder negativeButton(String txt, DialogInterface.OnClickListener negativeButtonClickListener2) {
//            negativeButtonTxt = txt;
//            negativeButtonClickListener = negativeButtonClickListener2;
//            return this;
//        }
//
//        public BaseDialogBuilder positiveButton(String txt, DialogInterface.OnClickListener positiveButtonClickListener2) {
//            positiveButtonTxt = txt;
//            positiveButtonClickListener = positiveButtonClickListener2;
//            return this;
//        }
//
//        public BaseDialogBuilder showCloseIcon(boolean show) {
//            showCloseIcon = show;
//            return this;
//        }
//
//        public BaseDialogBuilder build() {
//            return this;
//        }
//
//        public AlertDialog create() {
//            return create(true);
//        }
//
//        public AlertDialog create(boolean canOutTouch) {
//            final ViewHolder holder = createDialog(mContext, R.layout.layout_dialog_tip,canOutTouch);
//            initView(holder);
//            return holder.dialog;
//        }
//
//        protected void initView(final ViewHolder holder) {
//            TextView btn_negative = holder.contentView.findViewById(R.id.btn_negative);
//            TextView btn_positive = holder.contentView.findViewById(R.id.btn_positive);
//            View ll_btn_wrapper = holder.contentView.findViewById(R.id.ll_btn_wrapper);
//            View btn_close = holder.contentView.findViewById(R.id.btn_close);
//
//            if (negativeButtonTxt != null) {
//                btn_negative.setVisibility(View.VISIBLE);
//                btn_negative.setText(negativeButtonTxt);
//                btn_negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(negativeButtonClickListener != null){
//                            negativeButtonClickListener.onClick(holder.dialog, DialogInterface.BUTTON_NEGATIVE);
//                        }else{
//                            holder.dialog.dismiss();
//                        }
//                    }
//                });
//            } else {
//                btn_negative.setVisibility(View.GONE);
//            }
//
//            if (positiveButtonTxt != null ) {
//                btn_positive.setVisibility(View.VISIBLE);
//                btn_positive.setText(positiveButtonTxt);
//                btn_positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(positiveButtonClickListener != null){
//                            positiveButtonClickListener.onClick(holder.dialog,DialogInterface.BUTTON_POSITIVE);
//                        }else{
//                            holder.dialog.dismiss();
//                        }
//                    }
//                });
//            } else {
//                btn_positive.setVisibility(View.GONE);
//            }
//
//            ll_btn_wrapper.setVisibility(btn_negative.isShown() || btn_positive.isShown() ? View.VISIBLE :View.GONE);
//            if (showCloseIcon) {
//                btn_close.setVisibility(View.VISIBLE);
//                btn_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        holder.dialog.dismiss();
//                    }
//                });
//            } else {
//                btn_close.setVisibility(View.GONE);
//            }
//        }
//
//    }
//
//
//    //建造者，构建简单的消息提示框。
//    public static class TipDialogBuilder {
//        protected Context mContext;
//        protected String negativeButtonTxt;
//        protected DialogInterface.OnClickListener negativeButtonClickListener;
//
//        protected String positiveButtonTxt;
//        protected DialogInterface.OnClickListener positiveButtonClickListener;
//
//        protected boolean showCloseIcon = false;
//        protected String message,message2;
//
//        public TipDialogBuilder(Context mContext) {
//            this.mContext = mContext;
//        }
//
//        public TipDialogBuilder message(String msg) {
//            this.message = msg;
//            return this;
//        }
//
//        public TipDialogBuilder message2(String msg) {
//            this.message2 = msg;
//            return this;
//        }
//
//        public TipDialogBuilder negativeButton(String txt, DialogInterface.OnClickListener negativeButtonClickListener2) {
//            negativeButtonTxt = txt;
//            negativeButtonClickListener = negativeButtonClickListener2;
//            return this;
//        }
//
//        public TipDialogBuilder positiveButton(String txt, DialogInterface.OnClickListener positiveButtonClickListener2) {
//            positiveButtonTxt = txt;
//            positiveButtonClickListener = positiveButtonClickListener2;
//            return this;
//        }
//
//        public TipDialogBuilder showCloseIcon(boolean show) {
//            showCloseIcon = show;
//            return this;
//        }
//
//        public TipDialogBuilder build() {
//            return this;
//        }
//
//        public AlertDialog create() {
//            return create(true);
//        }
//
//        public AlertDialog create(boolean canOutTouch) {
//            final ViewHolder holder = createDialog(mContext, R.layout.layout_dialog_tip,canOutTouch);
//            initView(holder);
//            return holder.dialog;
//        }
//
//        protected void initView(final ViewHolder holder) {
//
//
//            TextView btn_negative = holder.contentView.findViewById(R.id.btn_negative);
//            TextView btn_positive = holder.contentView.findViewById(R.id.btn_positive);
//            View ll_btn_wrapper = holder.contentView.findViewById(R.id.ll_btn_wrapper);
//            View btn_close = holder.contentView.findViewById(R.id.btn_close);
//
//
//            TextView tvTitle = holder.contentView.findViewById(R.id.tv_title);
//            setShowMessage1(tvTitle);
//
//
//            TextView tv_message2 = holder.contentView.findViewById(R.id.tv_message2);
//            if(message2 == null || message2.isEmpty()){
//                tv_message2.setVisibility(View.GONE);
//            }else{
//                tv_message2.setVisibility(View.VISIBLE);
//                setShowMessage2(tv_message2);
//            }
//
//
//            if (negativeButtonTxt != null) {
//                btn_negative.setVisibility(View.VISIBLE);
//                btn_negative.setText(negativeButtonTxt);
//                btn_negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(negativeButtonClickListener != null){
//                            negativeButtonClickListener.onClick(holder.dialog, DialogInterface.BUTTON_NEGATIVE);
//                        }else{
//                            holder.dialog.dismiss();
//                        }
//                    }
//                });
//            } else {
//                btn_negative.setVisibility(View.GONE);
//            }
//
//            if (positiveButtonTxt != null ) {
//                btn_positive.setVisibility(View.VISIBLE);
//                btn_positive.setText(positiveButtonTxt);
//                btn_positive.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(positiveButtonClickListener != null){
//                            positiveButtonClickListener.onClick(holder.dialog,DialogInterface.BUTTON_POSITIVE);
//                        }else{
//                            holder.dialog.dismiss();
//                        }
//                    }
//                });
//            } else {
//                btn_positive.setVisibility(View.GONE);
//            }
//
//            ll_btn_wrapper.setVisibility(btn_negative.isShown() || btn_positive.isShown() ? View.VISIBLE :View.GONE);
//            if (showCloseIcon) {
//                btn_close.setVisibility(View.VISIBLE);
//                btn_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        holder.dialog.dismiss();
//                    }
//                });
//            } else {
//                btn_close.setVisibility(View.GONE);
//            }
//        }
//
//
//        protected void setShowMessage1(TextView tvTitle){
//            tvTitle.setText(message);
//        }
//
//        protected void setShowMessage2(TextView tv_message2){
//            tv_message2.setText(message2);
//        }
//
//    }
//
//    //提示框的文字，部分文字变色。
//    public static class ColorTipDialogBuilder extends TipDialogBuilder{
//        protected  String messageWithForeColor ;
//        protected  String messageWithForeColor2 ;
//        protected  int lightTxtForeColor ;
//        public    ColorTipDialogBuilder  message(String message,String messageWithForeColor){
//            this.message = message;
//            this.messageWithForeColor = messageWithForeColor;
//            return this;
//        }
//
//        public   ColorTipDialogBuilder  message2(String message2,String messageWithForeColor2){
//            this.message2 = message2;
//            this.messageWithForeColor2 = messageWithForeColor2;
//            return this;
//        }
//
//        public ColorTipDialogBuilder(Context mContext) {
//            super(mContext);
//            lightTxtForeColor = R.color.topBack_normal;
//        }
//
//
//        @Override
//        protected void setShowMessage1(TextView tvTitle) {
//            if(messageWithForeColor == null || messageWithForeColor.isEmpty()){
//                super.setShowMessage1(tvTitle);
//            }else{
//                //加颜色
//                int fstart=message.indexOf(messageWithForeColor);
//                int fend=fstart+messageWithForeColor.length();
//                SpannableStringBuilder style=new SpannableStringBuilder(message);
//                style.setSpan(new ForegroundColorSpan(lightTxtForeColor),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//                tvTitle.setText(style);
//            }
//        }
//
//        @Override
//        protected void setShowMessage2(TextView tv_message2) {
//            if(messageWithForeColor2 == null || messageWithForeColor2.isEmpty()){
//                super.setShowMessage2(tv_message2);
//            }else{
//                //加颜色
//                int fstart=message2.indexOf(messageWithForeColor2);
//                int fend=fstart+messageWithForeColor2.length();
//                SpannableStringBuilder style=new SpannableStringBuilder(message2);
//                style.setSpan(new ForegroundColorSpan(lightTxtForeColor),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//                tv_message2.setText(style);
//            }
//        }
//    }
//
//
//    //建造者，构建简单的文本输入框。
//    public static class EditDialogBuilder extends TipDialogBuilder{
//        String etHint  = "";
//        TxtCallBack txtCallBack;
//        String realTxt = "";
//
//
//        public EditDialogBuilder(Context mContext) {
//            super(mContext);
//        }
//
//        public EditDialogBuilder etHint(String hint) {
//            etHint = hint;
//            return this;
//        }
//
//
//        public EditDialogBuilder txt(String txt) {
//            realTxt = txt;
//            return this;
//        }
//
//
//        public EditDialogBuilder txtCallBack(TxtCallBack txtCallBack) {
//            this.txtCallBack = txtCallBack;
//            return this;
//        }
//
//
//
//        @Override
//        protected void initView(ViewHolder holder) {
//            super.initView(holder);
//            final EditText etview = holder.contentView.findViewById(R.id.et);
//            //文本监听
//            etview.setHint(etHint);
//            etview.setText(realTxt);
//            if(txtCallBack  != null){
//                etview.requestFocus();
//                etview.setVisibility(View.VISIBLE);
//                etview.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        txtCallBack.text(etview.getText().toString());
//                    }
//                });
//            }
//        }
//
//        public AlertDialog create(boolean canOutTouch) {
//            final ViewHolder holder = createDialog(mContext, R.layout.layout_dialog_edit,canOutTouch);
//            initView(holder);
//            return holder.dialog;
//        }
//    }
//
//
//    //密码输入对话框
//    public static class PasswordInputDialogBuilder extends TipDialogBuilder{
//        GridPasswordView.OnPasswordChangedListener onPasswordChangedListener;
//        public View btn_forget_password;
//        public GridPasswordView et_gpv;
//
//        public PasswordInputDialogBuilder(Context mContext) {
//            super(mContext);
//        }
//
//        public PasswordInputDialogBuilder passwordChangedListener(GridPasswordView.OnPasswordChangedListener onPasswordChangedListener){
//            this.onPasswordChangedListener = onPasswordChangedListener;
//            return this;
//        }
//
//        @Override
//        protected void initView(final ViewHolder holder) {
//            super.initView(holder);
//            et_gpv = holder.contentView.findViewById(R.id.et_gpv);
//            if(onPasswordChangedListener != null){
//                et_gpv.setOnPasswordChangedListener(onPasswordChangedListener);
//            }
//
//            btn_forget_password = holder.contentView.findViewById(R.id.btn_forget_password);
//            btn_forget_password.setVisibility(View.GONE);
//            btn_forget_password.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, AuthenticationActivity.class);
//                    intent.putExtra("openFlag",forgetFlag);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);
//                    holder.dialog.dismiss();
//                }
//            });
//        }
//
//
//        public AlertDialog create(boolean canOutTouch) {
//            final ViewHolder holder = createDialog(mContext, R.layout.layout_dialog_password,canOutTouch);
//            initView(holder);
//            return holder.dialog;
//        }
//    }
//
//
//    //展示互动退出房间提示框
//    public static class GenseeLeaveTipDialogBuilder {
//        protected Context mContext;
//        private DialogInterface.OnClickListener buttonClickListener1,buttonClickListener2,buttonClickListener3;
//        private boolean showCloseIcon = false;
//
//
//        public GenseeLeaveTipDialogBuilder(Context mContext) {
//            this.mContext = mContext;
//        }
//
//
//        public GenseeLeaveTipDialogBuilder button1(DialogInterface.OnClickListener buttonClickListener1) {
//            this.buttonClickListener1 = buttonClickListener1;
//            return this;
//        }
//
//        public GenseeLeaveTipDialogBuilder button2(DialogInterface.OnClickListener buttonClickListener2) {
//            this.buttonClickListener2 = buttonClickListener2;
//            return this;
//        }
//
//        public GenseeLeaveTipDialogBuilder button3(DialogInterface.OnClickListener buttonClickListener3) {
//            this.buttonClickListener3 = buttonClickListener3;
//            return this;
//        }
//
//
//        public GenseeLeaveTipDialogBuilder showCloseIcon(boolean show) {
//            showCloseIcon = show;
//            return this;
//        }
//
//        public GenseeLeaveTipDialogBuilder build() {
//            return this;
//        }
//
//        public AlertDialog create() {
//            return create(true);
//        }
//
//        public AlertDialog create(boolean canOutTouch) {
//            final ViewHolder holder = createDialog(mContext, R.layout.layout_dialog_gensee_leave_tip,canOutTouch);
//            initView(holder);
//            return holder.dialog;
//        }
//
//        protected void initView(final ViewHolder holder) {
//            View btn_close = holder.contentView.findViewById(R.id.btn_close);
//            btn_close.setVisibility(showCloseIcon ? View.VISIBLE : View.GONE);
//            btn_close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.dialog.dismiss();
//                }
//            });
//            holder.contentView.findViewById(R.id.tv_leave).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(buttonClickListener1 != null){
//                        buttonClickListener1.onClick(holder.dialog, DialogInterface.BUTTON_NEGATIVE);
//                    }else{
//                        holder.dialog.dismiss();
//                    }
//                }
//            });
//
//            holder.contentView.findViewById(R.id.tv_consult).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(buttonClickListener2 != null){
//                        buttonClickListener2.onClick(holder.dialog, DialogInterface.BUTTON_NEGATIVE);
//                    }else{
//                        holder.dialog.dismiss();
//                    }
//                }
//            });
//
//            holder.contentView.findViewById(R.id.tv_satisfaction_survey).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(buttonClickListener3 != null){
//                        buttonClickListener3.onClick(holder.dialog, DialogInterface.BUTTON_NEGATIVE);
//                    }else{
//                        holder.dialog.dismiss();
//                    }
//                }
//            });
//        }
//    }
//
//
//    public static class ListDialogBuilder extends  BaseDialogBuilder{
//        BaseAdapter adapter;
//        AdapterView.OnItemClickListener onItemClickListener;
//
//
//        public ListDialogBuilder adapter(BaseAdapter adapter){
//            this.adapter = adapter;
//            return this;
//        }
//
//
//        public ListDialogBuilder onItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
//            this.onItemClickListener = onItemClickListener;
//            return this;
//        }
//        public ListDialogBuilder(Context mContext) {
//            super(mContext);
//        }
//
//        @Override
//        protected void initView(ViewHolder holder) {
//            super.initView(holder);
//            ListView listv = holder.contentView.findViewById(R.id.listv);
//            if(adapter != null){
//                listv.setAdapter(adapter);
//            }
//
//            if(onItemClickListener != null){
//                listv.setOnItemClickListener(onItemClickListener);
//            }
//        }
//
//        public AlertDialog create(boolean canOutTouch) {
//            final ViewHolder holder = createDialog(mContext, R.layout.layout_dialog_list,canOutTouch);
//            initView(holder);
//            return holder.dialog;
//        }
//    }
//
//    public interface  TxtCallBack{
//        void text(String txt);
//    }
}
