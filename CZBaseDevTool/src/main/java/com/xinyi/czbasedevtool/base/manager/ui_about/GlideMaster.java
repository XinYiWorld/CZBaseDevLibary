package com.xinyi.czbasedevtool.base.manager.ui_about;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.xinyi.czbasedevtool.base.R;

/**
 * 图片加载Main类
 * author:Created by ChenZhang on 2016/6/24 0024.
 * function:
 */
public class GlideMaster {
    private final static int placeHolderRes = R.mipmap.ic_launcher;
    private final static int errorHolderRes = R.mipmap.ic_launcher;


    public static void display(Context mContext,String url, ImageView img){
        Glide.with(mContext)
                .load(url)
                .placeholder(placeHolderRes)
                .error(errorHolderRes)
                .into(img);
    }

    public static void display(Context mContext, String url, ImageView img, RequestListener listener){
        Glide.with(mContext)
                .load(url)
                .listener(listener)
                .placeholder(placeHolderRes)
                .error(errorHolderRes)
                .into(img);
    }
}
