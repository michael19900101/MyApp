package com.aotuman.myapp.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.aotuman.myapp.utils.ScreenUtils;

/**
 * Created by aotuman on 2017/2/23.
 */

public class SecondBezierView extends View {

    private float mStartX,mStartY,mEndX,mEndY,mControlX,mControlY;
    private Paint mLinePaint,mBezierPaint;
    private Path mBezierPath;

    public SecondBezierView(Context context){
        super(context);
        mStartX = ScreenUtils.getScreenWidth() / 4;
        mStartY = ScreenUtils.getScreenWidth() * 3 / 4;
        mEndX = mEndY = ScreenUtils.getScreenHeight() / 2;
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(5); //画笔宽度
        mLinePaint.setColor(Color.BLACK);
        mBezierPaint = new Paint();
        mBezierPaint.setColor(Color.RED);
        mBezierPaint.setStrokeWidth(5); //画笔宽度
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mControlX = event.getX();
                mControlY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                ValueAnimator animX = ValueAnimator.ofFloat(mControlX, ScreenUtils.getScreenWidth() / 2);
                animX.setDuration(500);
                animX.setInterpolator(new OvershootInterpolator());
                animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlX = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animX.start();
                ValueAnimator animY = ValueAnimator.ofFloat(mControlY, ScreenUtils.getScreenHeight() / 2);
                animY.setDuration(500);
                animY.setInterpolator(new OvershootInterpolator());
                animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlY = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animY.start();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mStartX, mStartY, 8, mLinePaint);
        canvas.drawText("起点", mStartX, mStartY, mLinePaint);
        canvas.drawCircle(mEndX, mEndY, 8, mLinePaint);
        canvas.drawText("终点", mEndX, mEndY, mLinePaint);
        canvas.drawCircle(mControlX, mControlY, 8, mLinePaint);
        canvas.drawText("控制点", mControlX, mControlY, mLinePaint);
        canvas.drawLine(mStartX, mStartY, mControlX, mControlY, mLinePaint);
        canvas.drawLine(mEndX, mEndY, mControlX, mControlY, mLinePaint);

        mBezierPath.reset();//因为不断重绘，path的路径也要重置，不然页面上会显示很多条线
        mBezierPath.moveTo(mStartX, mStartY);//移至起点
        mBezierPath.quadTo(mControlX, mControlY, mEndX, mEndY);//二阶贝塞尔曲线，传入控制点和终点坐标
        canvas.drawPath(mBezierPath, mBezierPaint);
    }
}
