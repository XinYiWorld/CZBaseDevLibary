package com.xinyi.czbasedevtool.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.manager.ui_about.AnimationManager;

/**
 * Author: 陈章
 * Email:  1349308479@qq.com|1349308479xinyi@gmail.com
 * Created Time: 2016/2/26-18:06
 * Description:可折叠控件
 * arrow_id属性的id建议在ids.xml中定义
 */
public class BaseArrowExpandableLayout extends RelativeLayout
{
    private Boolean isAnimationRunning = false;
    private Boolean isOpened = false;
    private Integer duration;
    private FrameLayout contentLayout;
    private FrameLayout headerLayout;
    private Animation animation;

    private OnHeaderClickListener mHeaderClickListener = null;  //头部点击监听

    private boolean allowExpandle = true;
    private View arrow;

    public boolean isAllowExpandle() {
        return allowExpandle;
    }

    public void setAllowExpandle(boolean allowExpandle) {
        this.allowExpandle = allowExpandle;
    }

    //是否允许展开

    public BaseArrowExpandableLayout(Context context)
    {
        super(context);
    }

    public BaseArrowExpandableLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseArrowExpandableLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs)
    {
        final View rootView = View.inflate(context, R.layout.view_expandable, this);
        headerLayout = (FrameLayout) rootView.findViewById(R.id.view_expandable_headerlayout);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
        final int headerID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_headerLayout, -1);
        final int contentID = typedArray.getResourceId(R.styleable.ExpandableLayout_el_contentLayout, -1);
        final int arrowID = typedArray.getResourceId(R.styleable.ExpandableLayout_arrow_id,-1);
        contentLayout = (FrameLayout) rootView.findViewById(R.id.view_expandable_contentLayout);

        if (headerID == -1 || contentID == -1)
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");

        if(arrowID == -1)
            throw new IllegalArgumentException("arrow id cannot be null!");

        if (isInEditMode())
            return;

        duration = typedArray.getInt(R.styleable.ExpandableLayout_el_duration, getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
        final View headerView = View.inflate(context, headerID, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        headerLayout.addView(headerView);
        final View contentView = View.inflate(context, contentID, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(contentView);
        contentLayout.setVisibility(GONE);
        arrow = headerView.findViewById(arrowID);
        headerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //暴露头部点击监听
                if (mHeaderClickListener != null) {
                    mHeaderClickListener.onHeaderClick(BaseArrowExpandableLayout.this);
                }
                if (!isAnimationRunning) {
                    if (contentLayout.getVisibility() == VISIBLE) {
                        collapse(contentLayout);
                    } else {
                        if (!isAllowExpandle()) {
                            return;                 //如果不允许展开就展开也不执行动画
                        }
                        expand(contentLayout);
                    }
                    isAnimationRunning = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isAnimationRunning = false;
                        }
                    }, duration);
                }
            }
        });
        typedArray.recycle();
    }

    //展开操作
    private void expand(final View v)
    {
        AnimationManager.getInstance().animate(arrow, AnimationManager.upRotateAnimation, duration);
        //暴露展开的组件，以在外部加载数据。
        if(mHeaderClickListener != null){
            mHeaderClickListener.onExpand(BaseArrowExpandableLayout.this);
        }

        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);

        animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                if (interpolatedTime == 1)
                    isOpened = true;
                v.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }


            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    //折叠操作
    private void collapse(final View v)
    {
        AnimationManager.getInstance().animate(arrow, AnimationManager.downRotateAnimation, duration);
        final int initialHeight = v.getMeasuredHeight();
        animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1)
                {
                    v.setVisibility(View.GONE);
                    isOpened = false;
                }
                else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    public Boolean isOpened()
    {
        return isOpened;
    }

    public void show()
    {
        if (!isAnimationRunning)
        {
            expand(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    isAnimationRunning = false;
                }
            }, duration);
        }
    }

    public FrameLayout getHeaderLayout()
    {
        return headerLayout;
    }

    public FrameLayout getContentLayout()
    {
        return contentLayout;
    }

    public void hide()
    {
        if (!isAnimationRunning)
        {
            collapse(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    isAnimationRunning = false;
                }
            }, duration);
        }
    }

    @Override
    public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
        animation.setAnimationListener(animationListener);
    }

    //定义一个监听器，监听头部点击。
    public interface OnHeaderClickListener{
        void onHeaderClick(BaseArrowExpandableLayout expandableLayout);
        void onExpand(BaseArrowExpandableLayout expandableLayout);       //展开的时候加载数据，所以需要监听。
    }

    public void setOnHeaderClickListener(OnHeaderClickListener mHeaderClickListener){
        this.mHeaderClickListener = mHeaderClickListener;
    }

    /**
     * 暴露折叠的方法给外部
     */
    public void collapse(){
        //没有展开就不折叠，避免再执行动画。
        if(!isOpened()){
            return;
        }
        collapse(contentLayout);
    }

}
