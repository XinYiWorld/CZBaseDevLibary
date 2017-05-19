package com.xinyi.czbasedevtool.base.manager.ui_about;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈章 on 2017/2/13 0013.
 * func:
 * Fragment管理工具类
 */

public class FragmentMaster {
    private FragmentManager mFragmentManager;
    private Context mContext;
    private FragmentTransaction transaction;
    private List<Fragment> addedFragments;
    public FragmentMaster(Context mContext, FragmentManager mFragmentManager) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        addedFragments = new ArrayList<>();
    }

    public void begin(){
        transaction = mFragmentManager.beginTransaction();
    }

    public void hideFragment(Fragment fragment){
        hideFragment(fragment,null);
    }

    public void showFragment(int fillId,Fragment fragment){
        showFragment(fillId, fragment,null);
    }


    public void hideFragment(Fragment fragment, OnTaskDoneListener onTaskDoneListener){
        if(fragment != null){
            transaction.hide((fragment));
        }
        if(onTaskDoneListener != null){
            onTaskDoneListener.done(null);
        }
    }

    public void showFragment(int fillId,Fragment fragment, OnTaskDoneListener onTaskDoneListener){
        if(!fragment.isAdded()){
            transaction.add(fillId, fragment);
            addedFragments.add(fragment);
        }else {
            transaction.show(fragment);
        }
        if(onTaskDoneListener != null){
            onTaskDoneListener.done(null);
        }
    }

    public void showFragment(int fillId,Fragment fragment,String tag, OnTaskDoneListener onTaskDoneListener){
        if(!fragment.isAdded()){
            transaction.add(fillId, fragment,tag);
            addedFragments.add(fragment);
        }else {
            transaction.show(fragment);
        }
        if(onTaskDoneListener != null){
            onTaskDoneListener.done(null);
        }
    }

    public Fragment findFragmentByTag(String tag){
        return mFragmentManager.findFragmentByTag(tag);
    }


    //移除所有的Fragment
    public void removeAllFragment(){
        begin();
        for (Fragment fragment : addedFragments){
            transaction.remove(fragment);
        }
        commit();
    }

    public void commit(){
        transaction.commitAllowingStateLoss();
    }
}
