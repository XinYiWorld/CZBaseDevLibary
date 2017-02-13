package com.xinyi.czbasedevtool.base.manager.ui_about;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class AnimationManager {
    //动画实例
    public  static  Animation upRotateAnimation;    //顺时针向上旋转
    public  static  Animation downRotateAnimation;  //顺进针向下旋转

    public  final static  int DEFAULT_DURATION  = 500;    //默认动画时间



    private static AnimationManager instance = new AnimationManager();
    private AnimationManager(){
        initAnimations();
    }

    //初始化动画实例
    private static void initAnimations() {
        upRotateAnimation = new RotateAnimation(
                0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        upRotateAnimation.setDuration(500);
        upRotateAnimation.setFillAfter(true);

        downRotateAnimation = new RotateAnimation(
                -180, -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downRotateAnimation.setDuration(500);
        downRotateAnimation.setFillAfter(true);
    }

    public static AnimationManager getInstance(){return instance;}

    //为View设置动画
    public void animate(View view, Animation animation, Integer ...duration){
        long animationTime;
        if(duration != null && duration.length > 0){
            animationTime = duration[0];
        }else{
            animationTime = animation.getDuration();
        }
        animation.setDuration(animationTime);
        view.startAnimation(animation);
    }
}
