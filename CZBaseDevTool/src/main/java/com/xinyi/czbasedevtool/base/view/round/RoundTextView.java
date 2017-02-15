package com.xinyi.czbasedevtool.base.view.round;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.utils.ResourceUtil;
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
        cornerRadius = typedArray.getDimension(R.styleable.RoundTextView_cornerRadius, 0);
        borderWidth = typedArray.getDimension(R.styleable.RoundTextView_borderWidth, 0);
        borderColor = typedArray.getColor(R.styleable.RoundTextView_borderColor, getResources().getColor(android.R.color.transparent));
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
        setBackgroundDrawable(ImageCommonUtil.createGradientDrawable(bgColor,bgPressColor,cornerRadius,borderColor,borderWidth,android.R.attr.state_pressed));
        setClickable(true);     //否则选择器不起作用
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setBgPressColor(int bgPressColor) {
        this.bgPressColor = bgPressColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }
}
