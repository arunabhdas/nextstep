package com.arunabhdas.mobile.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.arunabhdas.mobile.broccoli.R;
import com.arunabhdas.mobile.views.HorizontalProgressBar;


/**
 * TODO: document your custom view class.
 */
public class HorizontalProgressBarView extends LinearLayout {
    private int mGradientStartColor = Color.BLUE;
    private int mGradientEndColor = Color.GREEN;
    private int mBackgroundColor = Color.GRAY;
    private boolean mShouldStartAnimation = false;
    private float mCornerRadius = 25.0f;
    private float mPreviousAnimationEndFraction = 0;
    private float mAnimationEndFraction = 1;

    private HorizontalProgressBar vProgressBar;
    private Context mContext;
    private Paint mPaint;
    private RectF mBounds;
    private Path mClipPath;

    public HorizontalProgressBarView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public HorizontalProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public HorizontalProgressBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setWillNotDraw(false);

        // init path
        mClipPath = new Path();

        // init paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HorizontalProgressBar, 0, 0);

        try {
            mGradientStartColor = a.getColor(R.styleable.HorizontalProgressBar_gradientStartColor, mGradientStartColor);
            mGradientEndColor = a.getColor(R.styleable.HorizontalProgressBar_gradientEndColor, mGradientEndColor);
            mBackgroundColor = a.getColor(R.styleable.HorizontalProgressBar_backgroundColor, mBackgroundColor);
            mPaint.setColor(mBackgroundColor);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBounds = new RectF(0, 0, w, h);
        mCornerRadius = h / 2.0f;
        vProgressBar = new HorizontalProgressBar(mContext, mGradientStartColor, mGradientEndColor, mCornerRadius, w);
        this.addView(vProgressBar);
        if (mShouldStartAnimation) {
            this.runAnimation();
            mShouldStartAnimation = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mBounds, mCornerRadius, mCornerRadius, mPaint);
        mClipPath.addRoundRect(mBounds, mCornerRadius, mCornerRadius, Path.Direction.CW);
        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }

    public void setGradientStartColor(int gradientStartColor) {
        this.mGradientStartColor = gradientStartColor;
    }

    public void setGradientEndColor(int gradientEndColor) {
        this.mGradientEndColor = gradientEndColor;
    }

    public void setAnimationEndFraction(float endFraction) {
        if (mAnimationEndFraction < 1 && mAnimationEndFraction > 0) {
            mPreviousAnimationEndFraction = mAnimationEndFraction;
        }
        mAnimationEndFraction = endFraction;
    }

    public void startAnimation() {
        if (vProgressBar != null) {
            runAnimation();
        } else {
            mShouldStartAnimation = true;
        }
    }

    private void runAnimation() {
        float startFraction = 0;
        long duration = Math.round(1000 * mAnimationEndFraction);
        if (mPreviousAnimationEndFraction > 0 && mPreviousAnimationEndFraction < 1) {
            startFraction = mPreviousAnimationEndFraction;
            duration = Math.round(Math.abs(mPreviousAnimationEndFraction - mAnimationEndFraction) * 1000);
        }
        vProgressBar.startAnimation(startFraction, mAnimationEndFraction, duration);
    }
}
