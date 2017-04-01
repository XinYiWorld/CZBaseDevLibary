package com.xinyi.czbasedevtool.base.manager.ui_about.popwindow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.manager.TwoThreadManager;
import com.xinyi.czbasedevtool.base.utils.ViewUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe popupwindows 窗口
 */
public class PopWindowLoader {
    private View mView;
    private PopupWindow popupWindow;
    private Map<Integer,View> viewCache;
    private Activity attachedActivity;
    public PopWindowLoader(View mView) {
        this.mView = mView;
        viewCache  = new HashMap<>();
    }

    public PopWindowLoader(Context context, int viewResId){
        attachedActivity = (Activity) context;
        this.mView = ViewUtil.inflate(context,viewResId);
        View pop_bottom_view = mView.findViewById(R.id.pop_bottom_view);
        if(pop_bottom_view != null){
            pop_bottom_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);      //防止popwindow的getHeight为0或-2
        viewCache  = new HashMap<>();
    }
    /**
     * 初始化方法
     *
     * @param ctx
     * @param width
     * @param height
     * @return
     */
    public PopWindowLoader init(Context ctx, int width, int height) {
        popupWindow = new PopupWindow(ctx);

        popupWindow.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        popupWindow.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        popupWindow.setHeight(height);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);


//        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb4000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        return this;
    }


    public PopWindowLoader init(Context ctx, int width, int height,int bgColorRes) {
        init(ctx, width, height);
        ColorDrawable dw = new ColorDrawable(bgColorRes);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        return this;
    }

    /**
     * 设置动画风格
     * @param animationStyle
     * @return
     */
    public PopWindowLoader setAnimationStyle(@StyleRes int animationStyle) {
        popupWindow.setAnimationStyle(animationStyle);
        return  this;
    }

    /**
     * 在屏幕上下左右显示PopupWindow
     *
     * @param activity
     * @param gravity
     * @param x
     * @param y
     */
    public void showAtLocation(Activity activity, int gravity, int x, int y) {
        showAtLocation(activity.getWindow().getDecorView(), gravity, x, y);
    }

    /**
     * @param view
     * @param gravity
     * @param x
     * @param y
     * @ClassName PopWindowLoader.java
     * @MethodName show
     * @Description 显示在指定位置
     * @author xugang
     */
    public void showAtLocation(View view, int gravity, int x, int y) {
        if (popupWindow == null) {
            throw new NullPointerException("PopupWindow is null,please call the init(Context context) before this.");
        }
        popupWindow.showAtLocation(view, gravity, x, y);
//        executeOnShow();
    }

    /**
     * @param anchor
     * @param xoff
     * @param yoff
     * @ClassName PopWindowLoader.java
     * @MethodName showAsDropDown
     * @Description 在相对位置显示
     * @author xugang
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (popupWindow == null) {
            throw new NullPointerException("PopupWindow is null,please call the init(Context context) before this.");
        }
        popupWindow.showAsDropDown(anchor, xoff, yoff);
//        executeOnShow();
    }

    /**
     * 展示POP窗口
     * @param anchor
     */
    public void showAsDropDown(View anchor) {
        if (popupWindow == null) {
            throw new NullPointerException("PopupWindow is null,please call the init(Context context) before this.");
        }
        popupWindow.showAsDropDown(anchor);
//        executeOnShow();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (popupWindow == null) {
            throw new NullPointerException("PopupWindow is null,please call the init(Context context) before this.");
        }
        popupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
//        executeOnShow();
    }

    /**
     * 取消窗口
     */
    public void dismiss() {
        popupWindow.dismiss();
//        executeOnDismiss();
    }


    /**
     * 取消窗口，取消之前先执行一段代码。
     */
    public void dismiss(Runnable runnable) {
        runnable.run();
        popupWindow.dismiss();
    }
    
    /**
     * 取消窗口
     */
    public void dismiss(long milliTimes) {
        TwoThreadManager.getInstantce().postDelay(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        },milliTimes);
    }


    /**
     *
     */
    public boolean isShowing(){
        return popupWindow.isShowing();
    }
    /**
     * 获取当前popup窗口
     * @return
     */
    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public <T extends View> T findViewById(int viewId){
        if(!viewCache.containsKey(viewId)){
            viewCache.put(viewId,mView.findViewById(viewId));
        }
       return (T) viewCache.get(viewId);
    }


    //各种监听
    public void bindClickListenerForView(int viewId, View.OnClickListener onClickListener){
        if(mView != null){
            if(onClickListener != null && viewId != -1){
                findViewById(viewId).setOnClickListener(onClickListener);
            }
        }
    }

    public void bindClickListenerForView(View clickView, View.OnClickListener onClickListener){
            if(onClickListener != null ){
                clickView.setOnClickListener(onClickListener);
            }
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener){
        popupWindow.setOnDismissListener(onDismissListener);
    }

    private void executeOnShow(){
        WindowManager.LayoutParams lp= attachedActivity.getWindow().getAttributes();
        lp.alpha=0.3f;
        attachedActivity.getWindow().setAttributes(lp);
    }

    private void executeOnDismiss(){
        WindowManager.LayoutParams lp= attachedActivity.getWindow().getAttributes();
        lp.alpha=1.0f;
        attachedActivity.getWindow().setAttributes(lp);
    }

}
