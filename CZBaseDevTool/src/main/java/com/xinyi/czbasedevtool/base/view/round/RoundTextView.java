package com.xinyi.czbasedevtool.base.view.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.utils.image.ImageCommonUtil;

/**
 * Created by 陈章 on 2017/2/13 0013.
 * func:
 * 自定义的可以指定圆角大小、背景颜色、边框颜色和粗细的TextView
 * 避免反复的在drawable里创建xml文件，占用内存。
 */

public class RoundTextView  extends TextView{

    private int bgColor;
    private float cornerRadius;
    private int bgPressColor;
    private float borderWidth;
    private int borderColor;
    private int bgCheckColor;

    public RoundTextView(Context context) {
        this(context,null);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        bgColor = typedArray.getColor(R.styleable.RoundTextView_bgColor, getResources().getColor( android.R.color.transparent));
        bgPressColor = typedArray.getColor(R.styleable.RoundTextView_bgPressColor,-1);
        bgCheckColor = typedArray.getColor(R.styleable.RoundTextView_bgCheckColor,-1);
        cornerRadius = typedArray.getDimension(R.styleable.RoundTextView_cornerRadius, 0);
        borderWidth = typedArray.getDimension(R.styleable.RoundTextView_borderWidth, 0);
        borderColor = typedArray.getColor(R.styleable.RoundTextView_borderColor, getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refresh();
        setClickable(true);     //否则选择器不起作用
    }

    private void refresh(){
        setBackgroundDrawable(ImageCommonUtil.createGradientDrawable(bgColor,new int[]{bgPressColor,bgCheckColor},cornerRadius,borderColor,borderWidth,new int[]{android.R.attr.state_pressed,android.R.attr.state_checked}));
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        refresh();
    }

    public void setBgPressColor(int bgPressColor) {
        this.bgPressColor = bgPressColor;
        refresh();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        refresh();
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        refresh();
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        refresh();
    }
}
