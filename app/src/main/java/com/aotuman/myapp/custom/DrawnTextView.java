package com.aotuman.myapp.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.aotuman.myapp.ImageUtils;


/**
 * 柱状图横坐标上下部文字
 * Created by admin on 2015/12/2.
 */
public class DrawnTextView extends View {

    private String    text      = "文字描述";
    private TextPaint textPaint = new TextPaint();
    private Context context;

    private float barTextWidth = 45f;//柱状图字体宽度换行
    private float textSize     = 8f;//柱状图字体大小
    private StaticLayout mTextLayout;
    private StaticLayout mNumberLayout;
    private float textX;
    private float width;
    private int color = 0xff343434;

    private String  mSales    = "";
    private float   maxSales  = 0;
    private boolean numberFlg = false;
    private String valueformat;

    public DrawnTextView(Context context, String text, float x, float width, String valueformat, boolean flag) {
        this(context, null);
        this.textX = x;
        this.context = context;
        this.width = width;
        this.text = text;
        this.valueformat = valueformat;
        this.numberFlg = flag;
    }

    public DrawnTextView(Context context, String text, float x, float width, String sales, float maxSales, int color, String valueformat, boolean flag) {
        this(context, null);
        this.textX = x;
        this.context = context;
        this.width = width;
        this.text = text;
        this.maxSales = maxSales;
        this.mSales = sales;
        this.numberFlg = flag;
        this.color = color;
        this.valueformat = valueformat;
    }

    public DrawnTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width  = this.width;
        float height = 214f;//30
        setMeasuredDimension(ImageUtils.dip2px(context, width), ImageUtils.dip2px(context, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textPaint.setTextSize(ImageUtils.dip2px(context, textSize));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(color);
        int textwidth = ImageUtils.dip2px(context, barTextWidth);
        if (!mSales.equals("")) {
            if (valueformat.equals("1") || valueformat.equals("d%%")) {
                canvas.translate(ImageUtils.dip2px(context, textX), ImageUtils.dip2px(context, calcBarTopPoint(mSales)));
                mNumberLayout = new StaticLayout(mSales + "%", textPaint, textwidth, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
                mNumberLayout.draw(canvas);
                canvas.translate(-ImageUtils.dip2px(context, textX), -ImageUtils.dip2px(context, calcBarTopPoint(mSales)));
            } else {
                canvas.translate(ImageUtils.dip2px(context, textX), ImageUtils.dip2px(context, calcBarTopPoint(mSales)));
                mNumberLayout = new StaticLayout(mSales, textPaint, textwidth, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
                mNumberLayout.draw(canvas);
                canvas.translate(-ImageUtils.dip2px(context, textX), -ImageUtils.dip2px(context, calcBarTopPoint(mSales)));
            }
        }
        if (numberFlg) {
            textPaint.setColor(0xff343434);
            canvas.translate(ImageUtils.dip2px(context, textX), ImageUtils.dip2px(context, 191));
            mTextLayout = new StaticLayout(text, textPaint, textwidth, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
            mTextLayout.draw(canvas);
            canvas.translate(-ImageUtils.dip2px(context, textX), -ImageUtils.dip2px(context, 191));
        }
    }

    private float calcBarTopPoint(String sale) {
        return maxSales == 0 ? 187 - 18 : 187 - (Float.parseFloat(sale) / maxSales) * (150f) - 12;
    }


}
