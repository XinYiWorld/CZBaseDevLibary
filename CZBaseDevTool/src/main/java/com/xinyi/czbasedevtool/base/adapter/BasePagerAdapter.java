package com.xinyi.czbasedevtool.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 *
 * author:Created by ChenZhang on 2016/7/12 0012.
 * function:
 */
public abstract  class BasePagerAdapter<T extends View> extends PagerAdapter {
    protected List<T> mData;

    public BasePagerAdapter(List<T> mData) {
        this.mData = mData;
    }

    public BasePagerAdapter(T[] mData) {
        this.mData = Arrays.asList(mData);
    }

    @Override
    public int getCount() {
        if(mData != null && mData.size() > 0) return mData.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T item = mData.get(position);
        setUpItem(position,item);
        container.addView(item);
        return item;
    }

    /**
     * 初始化item数据
     * @param position
     * @param item
     */
    public abstract  void setUpItem(int position, T item) ;


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public T getItem(int position){
        return mData.get(position);
    }

}
