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

public class ThirdBezierView extends View {

    private float mStartX,mStartY,mEndX,mEndY,
            mControlX1,mControlY1,mControlX2,mControlY2;
    private Paint mLinePaint,mBezierPaint;
    private Path mBezierPath;
    private boolean mIsSecondPoint = false;

    public ThirdBezierView(Context context){
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
        switch (event.getAction() & MotionEvent.ACTION_MASK) {//多点触控
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mIsSecondPoint = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mControlX1 = event.getX(0);//获取控制点1的横纵坐标
                mControlY1 = event.getY(0);
                if (mIsSecondPoint) {
                    mControlX2 = event.getX(1);//获取控制点2的横纵坐标
                    mControlY2 = event.getY(1);
                }
                invalidate();
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
        canvas.drawCircle(mControlX1, mControlY1, 8, mLinePaint);
        canvas.drawText("控制点1", mControlX1, mControlY1, mLinePaint);
        canvas.drawCircle(mControlX2, mControlY2, 8, mLinePaint);
        canvas.drawText("控制点2", mControlX2, mControlY2, mLinePaint);
        canvas.drawLine(mStartX, mStartY, mControlX1, mControlY1, mLinePaint);
        canvas.drawLine(mControlX1, mControlY1, mControlX2, mControlY2, mLinePaint);
        canvas.drawLine(mEndX, mEndY, mControlX2, mControlY2, mLinePaint);

        mBezierPath.reset();
        mBezierPath.moveTo(mStartX, mStartY);
        mBezierPath.cubicTo(mControlX1, mControlY1, mControlX2, mControlY2, mEndX, mEndY);
        canvas.drawPath(mBezierPath, mBezierPaint);
    }
}
