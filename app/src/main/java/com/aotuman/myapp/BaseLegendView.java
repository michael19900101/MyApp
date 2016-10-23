package com.aotuman.myapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by Michael on 2016/10/23.
 */
public class BaseLegendView extends View{
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint picPaint;
    private TextPaint textPaint;
    private String titleStr;
    private Context mContext;

    private int textWidth;
    private int textHeight;
    private static final int CHART_TYPE_BAR = 1;
    private static final int CHART_TYPE_LINE = 2;
    private static final int CHART_TYPE_BASELINE = 3;
    private int chartType = CHART_TYPE_BAR;

    public BaseLegendView(Context context,String titleStr,int chartType) {
        super(context);
        this.titleStr = titleStr;
        this.mContext = context;
        this.chartType = chartType;
        init();
    }

    private void init() {
        picPaint = new Paint();
        picPaint.setAntiAlias(true);
        picPaint.setColor(Color.GREEN);

        textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setAntiAlias(true);
        textPaint.setColor(0xFF343434);
        textPaint.setTextSize(ImageUtils.dip2px(mContext,12));// 设置字体大小
        mBound = new Rect();
        textPaint.getTextBounds(titleStr, 0, titleStr.length(), mBound);
        textWidth = mBound.width();
        textHeight = mBound.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(textWidth + 2*textHeight, textHeight + textHeight/3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 折线图
        if(chartType == CHART_TYPE_LINE){
            picPaint.setStyle(Paint.Style.STROKE);
            picPaint.setStrokeWidth(5f);
            int xaisOffset = 5;
            canvas.drawLine(0.25f*textHeight + xaisOffset,textHeight/2,0.5f*textHeight + xaisOffset,textHeight/2,picPaint);
            float x = 1.0f *textHeight  ;
            float y = textHeight / 2;
            float radius = textHeight / 3;
            canvas.drawCircle(x,y,radius,picPaint);
            canvas.drawLine(1.5f*textHeight - xaisOffset,textHeight/2,1.75f*textHeight - xaisOffset,textHeight/2,picPaint);

            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (textHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top ;
            textPaint.setColor(Color.BLACK);
            canvas.drawText(titleStr, 0  + 2*textHeight, baseline, textPaint);
        }else if(chartType == CHART_TYPE_BASELINE){ // 基准线图
            picPaint.setStyle(Paint.Style.STROKE);
            picPaint.setStrokeWidth(5f);
            int xaisOffset = 5;
            canvas.drawLine(0.25f*textHeight + xaisOffset,textHeight/2,0.75f*textHeight + xaisOffset,textHeight/2,picPaint);
            canvas.drawLine(1.25f*textHeight - xaisOffset,textHeight/2,1.75f*textHeight - xaisOffset,textHeight/2,picPaint);

            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (textHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top ;
            textPaint.setColor(Color.BLACK);
            canvas.drawText(titleStr, 0  + 2*textHeight, baseline, textPaint);
        }else{// 柱状图
            picPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0.5f*textHeight,0,1.5f*textHeight,textHeight,picPaint);

            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (textHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top ;
            textPaint.setColor(Color.BLACK);
            canvas.drawText(titleStr, 0  + 2*textHeight, baseline, textPaint);
        }

    }

}
