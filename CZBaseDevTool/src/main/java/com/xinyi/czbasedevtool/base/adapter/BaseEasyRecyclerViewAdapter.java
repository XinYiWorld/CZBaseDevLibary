package com.xinyi.czbasedevtool.base.adapter;

import android.content.Context;
import android.util.Log;

import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表Adapter基类
 * Created by Zhang on 2015/12/24.
 */
public abstract  class BaseEasyRecyclerViewAdapter<T> extends EasyRecyclerViewAdapter {
    private static final String TAG = "BaseEasyRecycler";
    protected List<T> mData;
    private  EasyRecyclerViewHolder.OnItemClickListener onItemClickListener ;
    protected Context mContext;

    public BaseEasyRecyclerViewAdapter(List<T> mData) {
        this.mData = mData;
    }

    public BaseEasyRecyclerViewAdapter() {
        this.mData = new ArrayList<>();
    }

    public BaseEasyRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{getLayoutId()};
    }

    @Override
    public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        bindData(mData.get(position), position,viewHolder);
        if(onItemClickListener != null){
            viewHolder.setOnItemClickListener(onItemClickListener,position);
        }
    }


    @Override
    public int getRecycleViewItemType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract int getLayoutId();

    protected abstract void bindData(T data,int position,EasyRecyclerViewHolder viewHolder);

    public void set(List<T> list) {
        Log.i(TAG, "set: " + list.size());
        if (list == null) return;
        this.mData = list;
        notifyDataSetChanged();
    }


    public void clear() {
        if( this.mData != null){
            this.mData.clear();
        }
        notifyDataSetChanged();
    }


    public void remove(Object o) {
        this.mData.remove(o);
        notifyDataSetChanged();
    }

    public List<T> getmData() {
        return mData;
    }

    public void addItem(T obj) {
        if (mData != null) {
            mData.add(obj);
        }
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        this.addAll(list,true);
    }

    public void addAll(List<T> list,boolean autoNotify) {           //防止在子线程里刷新
        if(list == null) return;
        if(this.mData == null){
            this.mData = new ArrayList<T>();
        }
        this.mData.addAll(list);
        if(autoNotify){
            notifyDataSetChanged();
        }
    }


    /**
     *  * set the on item click listener
     * 设置Item的点击事件
     * @param onItemClickListener
     */
    public   void bindItemClickListener(final EasyRecyclerViewHolder.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
