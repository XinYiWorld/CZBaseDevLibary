package com.xinyi.czbasedevtool.base.interfaces.view_about;

import android.content.Intent;
import android.os.Bundle;

/**
 * 各种跳转导航
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public interface I_Navigater {
    /**
     * startActivity
     *
     * @param clazz
     */
    void readyGo(Class<?> clazz) ;

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    void readyGo(Class<?> clazz, Bundle bundle);

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    void readyGoThenKill(Class<?> clazz) ;

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    void readyGoThenKill(Class<?> clazz, Bundle bundle);

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    void readyGoForResult(Class<?> clazz, int requestCode) ;

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) ;
}
