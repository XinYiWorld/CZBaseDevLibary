package com.xinyi.czbasedevtool.base.manager.ui_about;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xinyi.czbasedevtool.base.R;

/**
 * 图片加载Main类
 * author:Created by ChenZhang on 2016/6/24 0024.
 * function:
 * 1)Glide加载圆形图片第一次只显示默认图片:http://blog.csdn.net/qq_26413249/article/details/52217322
 */
public class GlideMaster {
    private  static int placeHolderRes = R.mipmap.ic_launcher;
    private  static int errorHolderRes = R.mipmap.ic_launcher;

    public static void init(int placeHolderRes2,int errorHolderRes2){
        placeHolderRes =  placeHolderRes2;
        errorHolderRes = errorHolderRes2;
    }


    public static void display(Context mContext, String url, final ImageView img){
        Glide.with(mContext)
                .load(url)
                .placeholder(placeHolderRes)
                .error(errorHolderRes)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        img.setImageDrawable(resource);
                    }
                });
    }

    public static void display(Context mContext, String url, final ImageView img, RequestListener listener){
        Glide.with(mContext)
                .load(url)
                .listener(listener)
                .placeholder(placeHolderRes)
                .error(errorHolderRes)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        img.setImageDrawable(resource);
                    }
                });
    }
}
