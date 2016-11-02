package com.aotuman.myapp.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.aotuman.myapp.ImageUtils;


/**
 * 虚线图View
 * Created by HuangKunTing on 2016/09/06.
 */
public class DashLineView extends View {

    private int   mLineNumber    = 6;//细横线的个数
    private float X_Space        = 57f;//56.8f;//点与点之间的距离
    private float Y_Space        = 27f;//水平线之间的距离
    private int   mDashLineColor = Color.RED;//虚线的颜色
    private int   mTextColor     = Color.RED;//基准线文字描述的颜色
    private int   mDashLineWidth = 2;//设置虚线的宽度，默认值2
    private int   mTextSize      = 8;//文字大小，默认10
    private int   mClickAreas    = 50;//响应点击的区域
    private float minSales       = 0;
    private float maxSales       = 0;
    private float mFirstPointX   = 29.5f;
    private String[]        mSales;
    private Context mContext;
    private Paint mBaseLinePaint;
    private Paint mTextPaint;
    private float           width_temp, height_temp;
    private Activity myAc;//用于获取屏幕的信息
    private String valueformat;
    private int mScreenWidth;
    private String description = "";// 基准线描述(可配置) eg:'KPI = '


    public DashLineView(Context context) {
        super(context);
        mContext = context;
        initData();
    }

    public DashLineView(Context context, String[] sales, float maxSales, String valueformat) {
        super(context);
        this.mContext = context;
        this.mSales = sales;
        this.maxSales = maxSales;
        this.valueformat = valueformat;
        initData();
    }

    public DashLineView(Context context, String[] sales, float maxSales, String description, String valueformat) {
        super(context);
        this.mContext = context;
        this.mSales = sales;
        this.maxSales = maxSales;
        this.valueformat = valueformat;
        this.description = description;
        initData();
    }

    private void initData(){
        WindowManager wm     = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//        WindowManager wm     = myAc.getWindowManager();
        int           height = wm.getDefaultDisplay().getHeight();
        int           width  = wm.getDefaultDisplay().getWidth();
        height_temp = height / 960f;
        width_temp = width / 640f;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;

    }

    private float calcPointX(int xPoint) {
        return mFirstPointX + xPoint * X_Space;//25f是首个柱状图的Y轴中心点
    }

    private float calcPointY(String sale) {
        float sales = Float.parseFloat(sale);
        float Y     = (mLineNumber * Y_Space + 25f);//y轴最大值，10f为折线点的最小值的Y轴坐标,设置为0则最小值点不在粗横线上
        return maxSales == minSales ? Y : Y - ((sales - minSales) / (maxSales - minSales)) * (Y - 50f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width  = 60f + (mSales.length - 1) * X_Space;
        float height = Y_Space * mLineNumber + 52f;//37f
        setMeasuredDimension(ImageUtils.dip2px(mContext, width), ImageUtils.dip2px(mContext, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBaseLinePaint = new Paint();
        mBaseLinePaint.setStyle(Paint.Style.STROKE);//设置画笔style空心
        mBaseLinePaint.setColor(mDashLineColor);
        mBaseLinePaint.setStrokeWidth(ImageUtils.dip2px(mContext, mDashLineWidth));//设置画笔宽度
        mBaseLinePaint.setAntiAlias(true);

        // 基准线文字描述画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(mDashLineColor);
        mTextPaint.setTextSize(ImageUtils.dip2px(mContext, mTextSize));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);

        float lastX = 0;// 最后一个点的横坐标
        float lastY = 0;// 最后一个点的纵坐标
        if(mSales != null && mSales.length > 0){
            lastX =  ImageUtils.dip2px(mContext, calcPointX(mSales.length - 1));
            if(lastX < mScreenWidth){
                lastX = mScreenWidth;
            }
            lastY =  ImageUtils.dip2px(mContext, calcPointY(mSales[mSales.length - 1]));
        }
        Path path = new Path();
        path.moveTo(0, lastY);
        path.lineTo(lastX, lastY);
        PathEffect effect = new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
        mBaseLinePaint.setPathEffect(effect);
        String descStr;
        String numStr = "";
        if(mSales != null && mSales.length > 0){
            numStr = mSales[mSales.length - 1];
        }
        if (valueformat.equals("1") || valueformat.equals("d%%")) {
            descStr = description + numStr + "%";
        }else {
            descStr = description + numStr;
        }
        float textLength = mTextPaint.measureText(descStr);
        canvas.drawPath(path, mBaseLinePaint);
        canvas.drawText(descStr, textLength, lastY - 15.0f * height_temp, mTextPaint);
    }

    /**
     * @param mDashLineColor 设置虚线的颜色
     */
    public void setmBaseLineColor(int mDashLineColor) {
        this.mDashLineColor = mDashLineColor;
        invalidate();
    }
}
