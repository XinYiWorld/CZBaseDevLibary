package com.xinyi.czbasedevtool.base.view.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.utils.image.ImageCommonUtil;

/**
 * Created by 陈章 on 2017/2/13 0013.
 * func:
 * 自定义的可以指定圆角大小、背景颜色、边框颜色和粗细的LinearLayout
 * 避免反复的在drawable里创建xml文件，占用内存。
 */

public class RoundLinearLayout extends LinearLayout{

    private int bgColor;
    private float cornerRadius;
    private int bgPressColor;
    private float borderWidth;
    private int borderColor;

    public RoundLinearLayout(Context context) {
        this(context,null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLinearLayout);
        bgColor = typedArray.getColor(R.styleable.RoundLinearLayout_bgColor, getResources().getColor( android.R.color.transparent));
        bgPressColor = typedArray.getColor(R.styleable.RoundLinearLayout_bgPressColor,-1);
        cornerRadius = typedArray.getDimension(R.styleable.RoundLinearLayout_cornerRadius, 0);
        borderWidth = typedArray.getDimension(R.styleable.RoundLinearLayout_borderWidth, 0);
        borderColor = typedArray.getColor(R.styleable.RoundLinearLayout_borderColor, getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        setBackgroundResource(android.R.drawable.bottom_bar);       //先设置一个背景，防止getBackground()得到的是null。
//        Drawable originalBackground = getBackground();
//        originalBackground.setColorFilter(bgColor, PorterDuff.Mode.DARKEN);
//        Drawable normalDrawable = ImageCommonUtil.bitmap2Drawable(ImageCommonUtil.getRoundedCornerBitmap(ImageCommonUtil.drawable2bitmap(originalBackground), cornerRadius));
//
//        originalBackground.setColorFilter(bgPressColor, PorterDuff.Mode.DARKEN);
//        Drawable pressDrawable = ImageCommonUtil.bitmap2Drawable(ImageCommonUtil.getRoundedCornerBitmap(ImageCommonUtil.drawable2bitmap(originalBackground), cornerRadius + 20));
        refresh();
        setClickable(true);     //否则选择器不起作用
    }

    private void refresh(){
        setBackgroundDrawable(ImageCommonUtil.createGradientDrawable(bgColor,bgPressColor,cornerRadius,borderColor,borderWidth,android.R.attr.state_pressed));
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
