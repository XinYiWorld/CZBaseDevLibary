package com.xinyi.czbasedevtool.base.view;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.manager.ui_about.GlideMaster;

import java.util.ArrayList;
import java.util.List;


/**
 * 独立管理轮播图逻辑的类
 * 它是继承ViewPager，不要和BasePager搞混了，它是没有initView方法返回视图的。new出它，自然就会有视图了。
 * @author LENOVO
 *
 */
public class RollViewPager extends ViewPager {
	private static final String TAG = "RollViewPager";
	
	private List<String> imageDescList ;
	private List<String> imageUrlList ;
	private List<View> dotList;
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private TextView descView;
	private MPagerAdapter mPagerAdapter;
	
	private int downX;
	private int downY;
	
	//记录轮播图当前图片索引位置
	private int currPosition = 0;
	private RunTask runTask;
	
	
	private OnViewClickListener mOnViewClickListener;
	
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//根据索引切换图片 
			RollViewPager.this.setCurrentItem(currPosition);	//注意对象是RollViewPager.this
			
			//为了让轮播一起循环，调用发送消息的方法，形成死循环。
			roll();
		}
	};

	
	

	/**
	 * 当ViewPager从界面移除的时候调用
	 * 取消消息和任务，否则切换要其它的tab再回来，会发生轮播图3项对不上的bug.
	 * 
	 * bug:为什么一加上这个轮播就不会自动进行了？？？
	 */
	@Override
	protected void onDetachedFromWindow() {
		handler.removeCallbacksAndMessages(null);
		super.onDetachedFromWindow();
	};
	
	
	
	/*
	 * 因为是代码动态创建对象，所以采用一个参数的构造方法。
	 */
	public RollViewPager(Context context) {
		this(context, null);
	}
	
	/*
	 * #####子控件在写Touch事件时，一般写在dispatchTouchEvent中。###
	 * 左右滑动
	 * 	  右-左：如果是最后一页，翻转tab模块，其它页面，翻转轮播图片。
	 * 	  左-右：如果是第一页，翻转tab模块，其它页面，翻转轮播图片。
	 * 	
	 * 上下滑动
	 *   向下滑动
	 *   向上滑动
	 * (non-Javadoc)
	 * @see android.support.v4.view.ViewPager#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) event.getX();
			downY = (int) event.getY();
			getParent().requestDisallowInterceptTouchEvent(true);	//按下的时候，不让父控件拦截事件。
			
			
			break;

		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int moveY = (int) event.getY();
			if(Math.abs(moveX - downX) > Math.abs(moveY -downY))
			{
				//左右滑动
				if(moveX - downX < 0)
				{
					//由右向左滑动
					if(currPosition == getAdapter().getCount()-1)
					{
						//如果是最后一页(getAdapter()可以得到ViewPager的适配器对象)
						getParent().requestDisallowInterceptTouchEvent(false);	//父控件tab模块，拦截事件，翻转模块。
					}
					else {
						getParent().requestDisallowInterceptTouchEvent(true);	//父控件tab模块，不拦截事件，翻转轮播图。
					}
				}
				else if(moveX - downX > 0){
					//由左向右滑动
					if(currPosition == 0)
					{
						//如果是第一页
						getParent().requestDisallowInterceptTouchEvent(false);	//父控件tab模块，拦截事件，翻转模块。
					}
					else {
						getParent().requestDisallowInterceptTouchEvent(true);	//父控件tab模块，不拦截事件，翻转轮播图。
					}
				}
			}
			else {
				//上下滑动
				getParent().requestDisallowInterceptTouchEvent(false);	//父控件tab模块，拦截事件，下拉是整个模块。
			}
			
			break;
			
		default:
			break;
		}
		return super.dispatchTouchEvent(event);		//（TODO 为什么 不返回true，返回true轮播图为什么不能滑动？？？）
	}
	
	
	public RollViewPager(Context context, List<View> dotList) {
		super(context);
		this.dotList = dotList;
		//初始化一个轮播任务
		runTask = new RunTask();
		
		//滚动切换监听，同步轮播图文字描述和点(不要写在handler中，handler只是自动切换的时候可以变化，如果手动便不会同步。)
		setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				currPosition = arg0;	//记得要同步一下记录当前页面的变量
				refreshImgDescAndDot();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	//动态设置轮播图的描述文字，并且将描述文字的控件传递过来。
	public void initImageDesc(List<String> imageDescList,TextView descView)
	{
		this.imageDescList = imageDescList;
		this.descView = descView;
		if(imageDescList != null && descView != null)
		{
			descView.setText(imageDescList.get(0));
		}
	}
	
	//动态设置轮播图的图片链接
	public void initImageUrl(final List<String> imageUrlList)
	{
		this.imageUrlList = imageUrlList;
		//创建图片对象(轮播图要展示的,注意这个ImageView是来自布局的，并不是直接new出来的。)
		
		imageViewList.clear();
		for(int i = 0; imageUrlList != null & i < imageUrlList.size(); i++)
		{
			//注意ViewPager不可重复添加同一个view，所以view不能为了说节省new写在外面，否则 会报下面这个异常。
			/*
			 * java.lang.IllegalStateException: The specified child already has a parent.
			 *  You must call removeView() on the child's parent first.
			 */

			View view = View.inflate(getContext(), R.layout.banner_viewpager_item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);

			GlideMaster.display(getContext(), "http://192.168.123.1" + imageUrlList.get(i), image, new RequestListener() {
				@Override
				public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
					e.printStackTrace();
					return false;
				}

				@Override
				public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
					return false;
				}
			});
			/*
			 * 为view添加点击事件
			 * 1.按下与抬起的坐标一致
			 * 2.按下与抬起的时间差小于一个指定值
			 */
			
			view.setOnTouchListener(new OnTouchListener() {
				int downX;
				int downY;
				long downTime;
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downX = (int) event.getX();
						downY = (int) event.getY();
						downTime = System.currentTimeMillis();
						
						//图片点中的时候，就不去做翻转。
						handler.removeCallbacksAndMessages(null);
						
						break;
					case MotionEvent.ACTION_UP:
						int upX = (int) event.getX();
						int upY = (int) event.getY();
						long upTime = System.currentTimeMillis();
						
						if(downX == upX && downY == upY && upTime - downTime < 500)
						{
							if(mOnViewClickListener != null)
							{
								mOnViewClickListener.onClick(currPosition);
							}
						}
						
						//手松开，重新开启按下时关闭的滚动
						roll();
						break;
					
					case MotionEvent.ACTION_CANCEL:	//应对按下、移动的事件，
						//有可能图片不会执行up事件，那么图片轮播便不会继续。
						roll();
						break;
					default:
						break;
					}
					return true;
				}
			});
			imageViewList.add(image);
		}
	}

	/**
	 * 滚动轮播图
	 * 设置适配器
	 */
	public void roll() {

		if(mPagerAdapter == null)
		{
			mPagerAdapter = new MPagerAdapter();
			setAdapter(mPagerAdapter);
		}
		else
		{
			mPagerAdapter.notifyDataSetChanged();
		}
		
		//TODO 利用Handler来实现轮播
		handler.postDelayed(runTask, 3000);		//因为会循环调用这方法，所以任务的内部不用再有睡眠操作了。
	}
	
	//轮播任务类
	class RunTask implements Runnable
	{
		@Override
		public void run() {
			currPosition = (currPosition + 1) % imageViewList.size();	
			
			//TODO handler发送一个空消息(下面2个方法有区别吗)
			handler.obtainMessage().sendToTarget();
//			handler.sendEmptyMessage(0);
		}
	}
	
	//同步轮播图文字描述和点
	private void refreshImgDescAndDot() {
		descView.setText(imageDescList.get(currPosition));
		int realPageSize = dotList.size();
		int virtualPageSize = realPageSize + 2;
		if(currPosition == 0){
			setCurrentItem(virtualPageSize - 2,false);
			setHighLightDot(realPageSize - 1);
		}else if(currPosition == virtualPageSize - 1){
			setCurrentItem(1,false);
			setHighLightDot(0);
		}else{
			setHighLightDot(currPosition - 1);
		}

	}

	private void setHighLightDot(int selectPosition) {
		selectPosition = selectPosition % (dotList.size());
		for (int i = 0; i < dotList.size(); i++) {
			dotList.get(selectPosition).setBackgroundResource(R.mipmap.dot_focus);
			if (selectPosition != i) {
				dotList.get(i).setBackgroundResource(R.mipmap.dot_normal);
			}
		}
	}

	;
	
	//定义轮播图的适配器
	private class MPagerAdapter extends PagerAdapter
	{
		
		@Override
		public int getCount() {
			return imageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			container.addView(imageViewList.get(position));
			return imageViewList.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}
	
	
	//点击事件的回调接口
	public  interface OnViewClickListener
	{
		void onClick(int currentPosition);
	}
	
	public void setOnViewClickListener(OnViewClickListener listener)
	{
		this.mOnViewClickListener = listener;
	}
	
}
