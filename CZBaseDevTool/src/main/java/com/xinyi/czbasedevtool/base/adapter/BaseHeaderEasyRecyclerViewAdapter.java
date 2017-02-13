package com.xinyi.czbasedevtool.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表Adapter基类,带头部。
 * Created by Zhang on 2015/12/24.
 */
public abstract  class BaseHeaderEasyRecyclerViewAdapter<T,K> extends EasyRecyclerViewAdapter {
    private static final String TAG = "BaseHeaderEasyAdapter";
    protected List<T> mData;
    protected Context mContext;

    private static final int HEADER_ITEM_TYPE = 0;
    private static final int NORMAL_ITEM_TYPE = 1;
    private K headData;



    public BaseHeaderEasyRecyclerViewAdapter(List<T> mData) {
        this.mData = mData;
    }

    public BaseHeaderEasyRecyclerViewAdapter() {
        this.mData = new ArrayList<>();
    }

    public BaseHeaderEasyRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int[] getItemLayouts() {
        if(enabledHeader()){
            return new int[]{getHeaderLayoutId(),getNormalLayoutId()};
        }else{
            return new int[]{getNormalLayoutId()};
        }
    }

    @Override
    public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        int itemViewType = getRecycleViewItemType(position);

        if(itemViewType == NORMAL_ITEM_TYPE){
            int realPosition = getRealPosition(viewHolder);
            bindNormalData(mData.get(realPosition), realPosition, viewHolder);
        }else if(itemViewType == HEADER_ITEM_TYPE){
            bindHeadData(headData,viewHolder);
        }
    }


    @Override
    public int getRecycleViewItemType(int position) {
        if(position == 0){
            if(enabledHeader()){
                return HEADER_ITEM_TYPE;
            }else{
                return NORMAL_ITEM_TYPE;
            }
        }else{
            return NORMAL_ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? (enabledHeader() ? 1 : 0) : (enabledHeader() ? (mData.size() + 1) : mData.size());
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        if(enabledHeader()){
            return  holder.getLayoutPosition() - 1;
        }else{
            return holder.getLayoutPosition();
        }
    }


    protected   int getHeaderLayoutId(){return -1;}

    protected abstract  int getNormalLayoutId();


    protected abstract void bindNormalData(T data,int position,EasyRecyclerViewHolder viewHolder);

    protected abstract void bindHeadData(K data,EasyRecyclerViewHolder viewHolder);

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
        this.addAll(list, true);
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



    public void setHeadData(K headData){
        this.headData = headData;
        notifyDataSetChanged();
    }

    public   boolean  enabledHeader(){
        return true;
    }

}
