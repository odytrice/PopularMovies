package com.odytrice.popularmovies.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An image view which always remains square with respect to its width.
 */
public final class TileImageView extends ImageView {
    public TileImageView(Context context) {
        super(context);
    }

    public TileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        double aspectRatio = 513D/342;
        setMeasuredDimension(getMeasuredWidth(), (int)(aspectRatio * getMeasuredWidth()));
    }
}
