package com.xinyi.czbasedevtool.base.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.interfaces.view_about.ChainSetter;

/**
 * Created by 陈章 on 2016/10/27 0027.
 * func:包裹contentview，用于简化和增加功能。
 */

public class ContentViewHolder implements ChainSetter<ContentViewHolder> {
    private SparseArray<View> childViews = new SparseArray<>();
    private View contentView;
    
    public ContentViewHolder(View contentView) {
        this.contentView = contentView;
    }

    public View getContentView(){
        return contentView;
    }

    /**
     * Deprecated. Use {@link #findViewById(int)} instead for a better understanding.
     */
    @Deprecated
    public <T extends View> T getView(int id) {
        return findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {
        View childView = childViews.get(id);
        if (childView == null) {
            childView = contentView.findViewById(id);
            if (childView != null)
                childViews.put(id, childView);
        }
        return (T) childView;
    }

    @Override
    public ContentViewHolder setText(int viewId, CharSequence text) {
        TextView textView = findViewById(viewId);
        textView.setText(text);
        return this;
    }

    @Override
    public ContentViewHolder setTextColor(int viewId, int textColor) {
        TextView view = findViewById(viewId);
        view.setTextColor(textColor);
        return this;
    }

    @Override
    public ContentViewHolder setTextColor(int viewId, ColorStateList colorStateList) {
        TextView view = findViewById(viewId);
        view.setTextColor(colorStateList);
        return this;
    }

    @Override
    public ContentViewHolder setMovementMethod(int viewId, MovementMethod method) {
        TextView textView = findViewById(viewId);
        textView.setMovementMethod(method);
        return this;
    }

    @Override
    public ContentViewHolder setImageResource(int viewId, int imgResId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(imgResId);
        return this;
    }

    @Override
    public ContentViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = findViewById(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public ContentViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = findViewById(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    @Override
    public ContentViewHolder setImageUri(int viewId, Uri imageUri) {
        ImageView view = findViewById(viewId);
        view.setImageURI(imageUri);
        return this;
    }

    @Override
    public ContentViewHolder setScaleType(int viewId, ImageView.ScaleType type) {
        ImageView view = findViewById(viewId);
        view.setScaleType(type);
        return this;
    }

    @Override
    public ContentViewHolder setBackgroundColor(int viewId, int bgColor) {
        View view = findViewById(viewId);
        view.setBackgroundColor(bgColor);
        return this;
    }

    @Override
    public ContentViewHolder setBackgroundResource(int viewId, int bgRes) {
        View view = findViewById(viewId);
        view.setBackgroundResource(bgRes);
        return this;
    }

    @Override
    public ContentViewHolder setColorFilter(int viewId, ColorFilter colorFilter) {
        ImageView view = findViewById(viewId);
        view.setColorFilter(colorFilter);
        return this;
    }

    @Override
    public ContentViewHolder setColorFilter(int viewId, int colorFilter) {
        ImageView view = findViewById(viewId);
        view.setColorFilter(colorFilter);
        return this;
    }

    @Override
    public ContentViewHolder setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        View view = findViewById(viewId);
        ViewCompat.setAlpha(view, value);
        return this;
    }

    @Override
    public ContentViewHolder setVisibility(int viewId, int visibility) {
        View view = findViewById(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @Override
    public ContentViewHolder setMax(int viewId, int max) {
        ProgressBar view = findViewById(viewId);
        view.setMax(max);
        return this;
    }

    @Override
    public ContentViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = findViewById(viewId);
        view.setProgress(progress);
        return this;
    }

    @Override
    public ContentViewHolder setRating(int viewId, float rating) {
        RatingBar view = findViewById(viewId);
        view.setRating(rating);
        return this;
    }

    @Override
    public ContentViewHolder setTag(int viewId, Object tag) {
        View view = findViewById(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public ContentViewHolder setTag(int viewId, int key, Object tag) {
        View view = findViewById(viewId);
        view.setTag(key, tag);
        return this;
    }

    @Override
    public ContentViewHolder setEnabled(int viewId, boolean enabled) {
        View view = findViewById(viewId);
        view.setEnabled(enabled);
        return this;
    }

    @Override
    public ContentViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView<Adapter> view = findViewById(viewId);
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public ContentViewHolder setAdapter(int viewId, RecyclerView.Adapter adapter) {
        RecyclerView view = findViewById(viewId);
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public ContentViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = findViewById(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public ContentViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
        return this;
    }

    @Override
    public ContentViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        findViewById(viewId).setOnLongClickListener(listener);
        return this;
    }

    @Override
    public ContentViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        findViewById(viewId).setOnTouchListener(listener);
        return this;
    }
}
