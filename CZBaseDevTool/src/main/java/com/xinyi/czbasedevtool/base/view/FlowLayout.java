package com.xinyi.czbasedevtool.base.view;

import java.util.ArrayList;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup{
	private int horizontalSpacing = 12;//水平间距
	private int verticalSpacing = 12;//行与行之间的垂直间距
	
	//用来存放每一行的Line对象
	private ArrayList<Line> lineList = new ArrayList<Line>();
	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}
	/**
	 * 设置水平间距
	 * @param horizontalSpacing
	 * @return
	 */
	public void setHorizontalSpacing(int horizontalSpacing){
		this.horizontalSpacing = horizontalSpacing;
	}
	/**
	 * 设置垂直间距
	 * @return
	 */
	public void setVerticalSpacing(int verticalSpacing){
		this.verticalSpacing = verticalSpacing;
	}
	
	/**
	 * 在onMeasure方法中，进行分行操作,相当于排好了座位表
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);//给当前控件设置默认宽高
		//1.根据widthMeasureSpec获取控件的总宽度
		int width = MeasureSpec.getSize(widthMeasureSpec);
		//2.获取实际比较的宽度，就是减去左右padding的宽度
		int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
		//3.遍历所有的子View，进行具体的分行逻辑
		Line line  = null;
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);//获取当前的子View
			//引起childView的onMeasure方法回调，
			childView.measure(0,0);//如果传入0，则会按照控件本身的宽高参数去测量
			
			//初始化Line对象
			if(line==null){
				line = new Line();//如果不换行，则Line对象始终是同一个Line对象
			}
			
			//4.如果line中没有子View，则直接添加childView,不需要比较宽度
			if(line.getViewList().size()==0){
				line.addChildView(childView);
			}else if (line.getWidth()+horizontalSpacing+childView.getMeasuredWidth()>noPaddingWidth) {
				//5.如果当前line的宽+水平间距+childView的宽 大于noPaddingWidth，则需要换行
				lineList.add(line);//先保存之前的Line对象，
				
				line = new Line();//创建新的Line
				line.addChildView(childView);//将childView放入新的Line中
			}else {
				//6.childView属于当前Line，则直接将childView加入到当前line中
				line.addChildView(childView);
			}
			
			//7.如果当前childView是最后一个，则需要保存最后的line对象，否则for循环结束，则最后的line会丢失
			if(i==(getChildCount()-1)){
				//保存最后的line对象
				lineList.add(line);
			}
		}
		
		//for循环结束了，lineList存放了所有的line对象，line对象又存放每行的子View
		//由于需要在垂直方向摆放所有的line的所有子View，所以需要计算高度
		int height = getPaddingTop()+getPaddingBottom();//先算上上下的padding
		//再计算所有的line的高度
		for (int i = 0; i < lineList.size(); i++) {
			height += lineList.get(i).getHeight();
		}
		//最后再加上所有line之间的垂直间距
		height += (lineList.size()-1)*verticalSpacing;
		
		setMeasuredDimension(width, height);//设置flowLayout的宽高
	}
	
	/**
	 * 让所有子View摆放到自己的位置上
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);//获取每个line对象
			
			//偏移下面的所有line:从第二行开始，总是比上一行的top多一个行高和垂直间距
			if(i>0){
				paddingTop += lineList.get(i-1).getHeight()+verticalSpacing;
			}
			
			ArrayList<View> viewList = line.getViewList();//获取line的viewList
			//获取当前line的留白
			float lineRemainSpacing = getLineRemainSpacing(line);
			//计算每个子View能够平均分配多少
			float perSpacing = lineRemainSpacing/viewList.size();
			
			//遍历viewList，去摆放每一个子View
			for (int j = 0; j < viewList.size(); j++) {
				View childView = viewList.get(j);//获取每一个子View对象
				//将每个子View得到的留白，增加到它原来的宽度上面
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childView.getMeasuredWidth()+perSpacing)
						,MeasureSpec.EXACTLY);
				childView.measure(widthMeasureSpec,0);
				
				if(j==0){
					//摆放的是第一个子View
					childView.layout(paddingLeft, paddingTop,paddingLeft+childView.getMeasuredWidth()
							, paddingTop+childView.getMeasuredHeight());
				}else {
					//摆放后面的子View，可以参考前一个子View
					View preView = viewList.get(j-1);//获取前一个子View对象
					int left = preView.getRight()+horizontalSpacing;//获取当前view的left
					childView.layout(left, preView.getTop(),left+childView.getMeasuredWidth()
							, preView.getBottom());
				}
			}
		}
	}

	/**
	 * 获取指定line的是留白区域
	 */
	private float getLineRemainSpacing(Line line){
		return getMeasuredWidth()-getPaddingLeft()-getPaddingRight()-line.getWidth();
	}

	/**
	 * 封装每一行的数据
	 * @author Administrator
	 *
	 */
	class Line{
		private ArrayList<View> viewList;//用来记录当前行的所有的TextView
		private int width;//表示当前line中所有子View的宽度+水平间距
		private int height;//当前的line高度
		
		public Line(){
			viewList = new ArrayList<View>();//初始化子View的集合
		}
		
		/**
		 * 添加子View的方法
		 * @param childView
		 */
		public void addChildView(View childView) {
			if(!viewList.contains(childView)){
				//每添加一个childVIew，都需要更新width
				if(viewList.size()==0){
					//说明当前还能没有子View，则width应该是childView的宽度
					width = childView.getMeasuredWidth();
				}else {
					//说明当前已经有子View，则再次添加childVIew，需要多加一个水平间距
					width += childView.getMeasuredWidth()+horizontalSpacing;
				}
				
				//更新高度, 最好是子View高度最大的那个高度
//				height = height>childView.getMeasuredHeight()?height:childView.getMeasuredHeight();
				height = Math.max(height, childView.getMeasuredHeight());
				
				viewList.add(childView);//将childView存放到list中
			}
		}

		/**
		 * 获取当前line的宽度
		 * @return
		 */
		public int getWidth(){
			return width;
		}
		/**
		 * 获取当前line的高度
		 * @return
		 */
		public int getHeight(){
			return height;
		}
		/**
		 * 获取当line的子View的集合
		 * @return
		 */
		public ArrayList<View> getViewList(){
			return viewList;
		}
		
	}
	
}
