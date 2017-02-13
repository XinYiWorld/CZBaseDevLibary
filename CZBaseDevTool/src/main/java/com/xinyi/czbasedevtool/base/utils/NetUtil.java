package com.xinyi.czbasedevtool.base.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

/**
 * 跟网络相关的工具类
 * 
 * @author zhy
 * 
 */
public class NetUtil
{
	private NetUtil()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context)
	{

		ConnectivityManager connectivity = SystemStaticInstanceManager.getmConnectivityManager();

		if (null != connectivity)
		{

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected())
			{
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi(Context context)
	{
		ConnectivityManager cm = SystemStaticInstanceManager.getmConnectivityManager();

		if (cm == null) return false;
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		if(activeNetworkInfo == null) return false;
		return activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 打开网络设置界面
	 */
	public static void openNetSetting( Context context)
	{
		if(android.os.Build.VERSION.SDK_INT > 13 ){
			//3.2以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
			 context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
		}
		else {
			context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		}
	}

}
