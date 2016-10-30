package com.aotuman.myapp.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.aotuman.myapp.ImageUtils;
import com.aotuman.myapp.constants.Constants;

/**
 * Created by Michael on 2016/10/29.
 */
public class VerticalLineView  extends View {
    private float  Xaix            = 40f;//横线X轴起点
    private float  Y_Space         = 27f;//水平横线距离固定
    private float  textSize        = 12f;//柱状图字体大小
    private int    lineNumber      = 6;//横线条数固定
    private float  textPointX      = 20f;//画文字的x轴坐标
    private float  textPointY      = 20f;//画文字的y轴坐标
    private float  thinLineWidth   = 1.0f;//画细线的宽度
    private float  boldLineWidth   = 2.0f;//画粗线的宽度
    private String unitDescription = "";
    private Paint linePaint;
    private TextPaint textPaint;
    private Context context;
    private String[] leftScale = {"1000","2000","3000","4000","5000","6000"}; // 左边的刻度值
    private String[] rightScale = {"0","20%","40%","60%","80%","100%"}; // 右边的刻度值

    public VerticalLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public VerticalLineView(Context context) {
        this(context, null);
        this.context = context;
        init();
    }

    private void init() {
        linePaint = new Paint();
        textPaint = new TextPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width  = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        float height = (lineNumber * Y_Space) + 52f;
        setMeasuredDimension(ImageUtils.dip2px(context, width), ImageUtils.dip2px(context, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setAntiAlias(true);
        textPaint.setColor(0xFF343434);
        textPaint.setTextSize(ImageUtils.dip2px(context, textSize));
        unitDescription = "单位：箱";
        canvas.drawText(unitDescription, ImageUtils.dip2px(context, textPointX - 10f), ImageUtils.dip2px(context, textPointY-10f), textPaint);
        float startX = ImageUtils.dip2px(context, Xaix + 0.5f);//加0.5消除粗竖线与横竖线的重合时的缺口
        float startY = ImageUtils.dip2px(context, 30.0f) ;//50.0f为从上往下第一条横线y坐标,
        float stopX  = ImageUtils.dip2px(context, Xaix + 0.5f);//加0.5消除粗竖线与横竖线的重合时的缺口
        float stopY  = ImageUtils.dip2px(context, (lineNumber * Y_Space) + 10 + 15.5f);//设置竖线的高度，加0.5消除粗竖线与横竖线的重合时的缺口

        //画粗竖线
        linePaint.setColor(0xFFDEDEDE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(ImageUtils.dip2px(context, boldLineWidth));
        canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        //画粗横线
        startX = ImageUtils.dip2px(context, Xaix);
        startY = ImageUtils.dip2px(context, (lineNumber * Y_Space) + 10 + 15f);
        stopX = Constants.screenWidth - ImageUtils.dip2px(context, Xaix);
        stopY = ImageUtils.dip2px(context, (lineNumber * Y_Space)  + 10 + 15f);//最底横线的Y坐标
        canvas.drawLine(startX, startY, stopX, stopY, linePaint);

        for (int x = 0; x < lineNumber; x++) {
            //画细横线
            startX = ImageUtils.dip2px(context, Xaix);
            startY = ImageUtils.dip2px(context, (lineNumber * Y_Space + 10 - x * Y_Space) + 14);
            stopX = Constants.screenWidth - ImageUtils.dip2px(context, Xaix);
            stopY = ImageUtils.dip2px(context, (lineNumber * Y_Space + 10 - x * Y_Space) + 14);
            linePaint.setStrokeWidth(ImageUtils.dip2px(context, thinLineWidth));
            canvas.drawLine(startX, startY, stopX, stopY, linePaint);

            // 画刻度值
            String titleStr = leftScale[x];
            Rect mBound = new Rect();
            textPaint.getTextBounds(titleStr, 0, titleStr.length(), mBound);
            int textWidth = mBound.width();
            int textHeight = mBound.height();
            canvas.drawText(titleStr, startX - textWidth - ImageUtils.dip2px(context,3), startY + textHeight/2, textPaint);

            // 画刻度值
            titleStr = rightScale[x];
            mBound = new Rect();
            textPaint.getTextBounds(titleStr, 0, titleStr.length(), mBound);
            textWidth = mBound.width();
            textHeight = mBound.height();
            canvas.drawText(titleStr, stopX + ImageUtils.dip2px(context,3), stopY + textHeight/2, textPaint);
        }


        startX = Constants.screenWidth - ImageUtils.dip2px(context, Xaix);
        startY = ImageUtils.dip2px(context, 30.0f) ;
        stopX  = Constants.screenWidth - ImageUtils.dip2px(context, Xaix);
        stopY  = ImageUtils.dip2px(context, (lineNumber * Y_Space) + 10 + 15.5f);

        //画粗竖线
        linePaint.setColor(0xFFDEDEDE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(ImageUtils.dip2px(context, boldLineWidth));
        canvas.drawLine(startX, startY, stopX, stopY, linePaint);


    }

    /**
     * @param boldLineWidth 设置横竖粗线的宽度
     */
    public void setBoldLineWidth(float boldLineWidth) {
        this.boldLineWidth = boldLineWidth;
        invalidate();
    }

    /**
     * @param thinLineWidth 设置横细线的宽度
     */
    public void setThinLineWidth(float thinLineWidth) {
        this.thinLineWidth = thinLineWidth;
        invalidate();
    }

    /**
     * @param unitDescription 设置图标的单位
     */
    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription.equals("") ? "" : "单位 : " + unitDescription;
        invalidate();
    }

    /**
     * @param lineNumber 设置横线数量
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        invalidate();
    }

    /**
     * @param textPointX 设置画文字的x轴坐标
     */
    public void setTextPointX(float textPointX) {
        this.textPointX = textPointX;
    }

    /**
     * @param textPointY 设置画文字的y轴坐标
     */
    public void setTextPointY(float textPointY) {
        this.textPointY = textPointY;
        invalidate();
    }

    /**
     * @param textSize 设置画文字的大小
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }

    /**
     * @param y_Space 设置水平横线距离
     */
    public void setY_Space(float y_Space) {
        Y_Space = y_Space;
        invalidate();
    }

    /**
     * @param xaix 设置横线X轴起点
     */
    public void setXaix(float xaix) {
        Xaix = xaix;
        invalidate();
    }

}
