package com.xinyi.czbasedevtool.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * author:Created by ChenZhang on 2016/6/28 0028.
 * function:
 */
public  class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter{
    private List<T> fragments;

    public BaseFragmentPagerAdapter(FragmentManager fm,List<T> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm,T[] fragments) {
        super(fm);
        this.fragments = Arrays.asList(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if(fragments != null && fragments.size() > 0){
            return fragments.size();
        }
        return 0;
    }
}
