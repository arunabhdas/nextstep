package com.arunabhdas.mobile.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class HorizontalProgressBar extends View {
    private Paint mPaint;
    private int mGradientStartColor;
    private int mGradientEndColor;
    private float mCornerRadius;
    private LinearGradient mLinearGradient;
    private RectF mBounds;
    private int mMaxWidth;

    public HorizontalProgressBar(Context context) {
        super(context);
    }
    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalProgressBar(Context context, int startColor, int endColor, float cornerRadius, int maxWidth) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientStartColor = startColor;
        mGradientEndColor = endColor;
        mCornerRadius = cornerRadius;
        mMaxWidth = maxWidth;
        mLinearGradient = new LinearGradient(0, 0, (float) mMaxWidth, 0, mGradientStartColor, mGradientEndColor, Shader.TileMode.MIRROR);
        mBounds = new RectF(0, 0, 50, 50);
        setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mBounds = new RectF(0, 0, w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShader(mLinearGradient);
        canvas.drawRoundRect(mBounds, mCornerRadius, mCornerRadius, mPaint);

    }

    public void startAnimation(float startFraction, float endFraction, long duration) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(Math.round(mMaxWidth*startFraction), Math.round(mMaxWidth*endFraction));
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final int width = (Integer) valueAnimator.getAnimatedValue();
                setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
            }
        });

        valueAnimator.start();
    }
}

