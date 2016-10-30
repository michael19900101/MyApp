package com.aotuman.myapp.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.aotuman.myapp.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael on 2016/10/29.
 */
public class LineView extends View {

    private int   mLineNumber    = 6;//细横线的个数
    private float X_Space        = 57f;//56.8f;//点与点之间的距离
    private float Y_Space        = 27f;//水平线之间的距离
    private int   mPolyLineColor = 0xffFEB727;//折线的颜色
    private int   mTextNameColor = 0xff000000;//X坐标名称的颜色
    private int   mPolyLineWidth = 2;//设置折线的宽度，默认值2
    private int   mTextSize      = 8;//文字大小，默认10
    private int   mClickAreas    = 50;//响应点击的区域
    private float minSales       = 0;
    private float maxSales       = 0;
    private float mFirstPointX   = 29.5f;
    private String[]        mSales;
    private Context mContext;
    private Paint mLinePaint;
    private OnClickListener mPointOnClickListener;
    private Paint           mTextNumberPaint;
    private Paint           mTextNamePaint;
    private Paint           mCirclePaint;
    private Paint           mArcPaint;
    private float           width_temp, height_temp;
    private int      mClickPoint;
    private Activity myAc;//用于获取屏幕的信息
    private String   valueformat;
    private List<Map<String, Integer>> mPoints           = new ArrayList<Map<String, Integer>>();
    private final static String                     X_KEY             = "x_point";
    private final static String                     Y_KEY             = "y_point";
    private              int                        animationDuration = 200;//动画时间
    private static final int                        MSG_DATA_CHANGE   = 0x11;
    public LineView(Context context, String[] sales, float maxSales, String valueformat) {
        this(context, null);
        this.mContext = context;
        this.mSales = sales;
        this.maxSales = maxSales;
        this.valueformat = valueformat;
        initData();
    }

    public LineView(Context context, String[] sales, float maxSales, String valueformat, float firstPointX, float X_Space) {
        this(context, null);
        this.mContext = context;
        this.mSales = sales;
        this.maxSales = maxSales;
        this.valueformat = valueformat;
        this.mFirstPointX = firstPointX + 5;
        this.X_Space = X_Space;
        initData();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initData();

    }

    private void initData() {
        mTextNumberPaint = new Paint();
        mTextNamePaint = new Paint();
        mCirclePaint = new Paint();
        mLinePaint = new Paint();
        mArcPaint = new Paint();
        myAc = (Activity) mContext;
        WindowManager wm     = myAc.getWindowManager();
        int           height = wm.getDefaultDisplay().getHeight();
        int           width  = wm.getDefaultDisplay().getWidth();
        height_temp = height / 960f;
        width_temp = width / 640f;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < mSales.length; x++) {
                    Message message = new Message();
                    message.what = MSG_DATA_CHANGE;
                    message.arg1 = ImageUtils.dip2px(mContext, calcPointX(x));//获取x轴坐标
                    message.arg2 =  ImageUtils.dip2px(mContext,calcPointY(mSales[x]));//获取y轴坐标
                    mHandler.sendMessage(message);
                    SystemClock.sleep(animationDuration);
                }
            }
        }).start();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_DATA_CHANGE:
                    setLinePoint(msg.arg1, msg.arg2);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void setLinePoint(int curX, int curY) {
        Map<String, Integer> temp = new HashMap<String, Integer>();
        temp.put(X_KEY, curX);
        temp.put(Y_KEY, curY);
        mPoints.add(temp);
        invalidate();
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
        float height = Y_Space * mLineNumber + +52f;//37f
        setMeasuredDimension(ImageUtils.dip2px(mContext, width), ImageUtils.dip2px(mContext, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mLinePaint.setColor(mPolyLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(ImageUtils.dip2px(mContext, mPolyLineWidth));
        mLinePaint.setAntiAlias(true);
        //画折线
        for (int x = 0; x < mPoints.size(); x++) {
            if (x > 0) {
                canvas.drawLine(mPoints.get(x - 1).get(X_KEY), mPoints.get(x - 1).get(Y_KEY), mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY), mLinePaint);
            }
        }
        //数字paint设置
        mTextNumberPaint.setColor(mPolyLineColor);
        mTextNumberPaint.setTextSize(ImageUtils.dip2px(mContext, mTextSize));
        mTextNumberPaint.setTextAlign(Paint.Align.CENTER);
        mTextNumberPaint.setAntiAlias(true);
        //文字paint设置
        mTextNamePaint.setColor(mTextNameColor);
        mTextNamePaint.setTextSize(ImageUtils.dip2px(mContext, mTextSize));
        mTextNamePaint.setTextAlign(Paint.Align.CENTER);
        mTextNamePaint.setAntiAlias(true);
        //圆paint设置
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        //圆弧paint设置
        mArcPaint.setColor(mPolyLineColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(ImageUtils.dip2px(mContext, mPolyLineWidth));
        mArcPaint.setAntiAlias(true);
        if (valueformat.equals("1") || valueformat.equals("d%%")) {
            for (int x = 0; x < mPoints.size(); x++) {
                canvas.drawCircle(mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY), ImageUtils.dip2px(mContext, 2.0f), mCirclePaint);//画白圆
                canvas.drawCircle(mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY), ImageUtils.dip2px(mContext, 3.0f), mArcPaint);//画圆弧
                if (x == mPoints.size() - 1) {//最后一个点

                    if (mPoints.size() == 1) {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) + 5 * width_temp, mPoints.get(x).get(Y_KEY) + 25.0f * height_temp, mTextNumberPaint);
                    } else {
                        if (Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x - 1])) {
                            canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) + 5 * width_temp, mPoints.get(x).get(Y_KEY) + 25.0f * height_temp, mTextNumberPaint);
                        } else {
                            canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) + 5 * width_temp, mPoints.get(x).get(Y_KEY) - 15.0f * height_temp, mTextNumberPaint);
                        }
                    }
                } else if (x == 0) {//第一个点

                    if (Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x + 1])) {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) - 10 * width_temp, mPoints.get(x).get(Y_KEY) - 10.0f * height_temp, mTextNumberPaint);
                    } else {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) - 10 * width_temp, mPoints.get(x).get(Y_KEY) - 10.0f * height_temp, mTextNumberPaint);
                    }
                } else {//中间其他点

                    if (Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x + 1]) && Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x - 1])) {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) + 30 * width_temp, mPoints.get(x).get(Y_KEY) + 20.0f * height_temp, mTextNumberPaint);
                    } else if (Float.parseFloat(mSales[x]) > Float.parseFloat(mSales[x + 1]) && Float.parseFloat(mSales[x]) > Float.parseFloat(mSales[x - 1])) {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY) - 15.0f * height_temp, mTextNumberPaint);
                    } else if (Float.parseFloat(mSales[x]) < (Float.parseFloat(mSales[x + 1]) + Float.parseFloat(mSales[x - 1])) / 2) {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) + 30 * width_temp, mPoints.get(x).get(Y_KEY), mTextNumberPaint);
                    } else if (Float.parseFloat(mSales[x]) > (Float.parseFloat(mSales[x + 1]) + Float.parseFloat(mSales[x - 1])) / 2) {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY) - 10 * width_temp, mPoints.get(x).get(Y_KEY) - 20.0f * height_temp, mTextNumberPaint);
                    } else {
                        canvas.drawText(mSales[x] + "%", mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY) - 20.0f * height_temp, mTextNumberPaint);
                    }
                }

            }
        } else {
            for (int x = 0; x < mPoints.size(); x++) {
                canvas.drawCircle(mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY), ImageUtils.dip2px(mContext, 2.0f), mCirclePaint);//画白圆
                canvas.drawCircle(mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY), ImageUtils.dip2px(mContext, 3.0f), mArcPaint);//画圆弧
                if (x == mPoints.size() - 1) {//最后一个点
                    if (mPoints.size() == 1) {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) + 5 * width_temp, mPoints.get(x).get(Y_KEY) + 25.0f * height_temp, mTextNumberPaint);
                    } else {
                        if (Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x - 1])) {
                            canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) + 5 * width_temp, mPoints.get(x).get(Y_KEY) + 25.0f * height_temp, mTextNumberPaint);
                        } else {
                            canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) + 5 * width_temp, mPoints.get(x).get(Y_KEY) - 15.0f * height_temp, mTextNumberPaint);
                        }
                    }
                } else if (x == 0) {//第一个点
                    if (Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x + 1])) {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) - 10 * width_temp, mPoints.get(x).get(Y_KEY) - 10.0f * height_temp, mTextNumberPaint);
                    } else {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) - 10 * width_temp, mPoints.get(x).get(Y_KEY) - 10.0f * height_temp, mTextNumberPaint);
                    }
                } else {//中间其他点
                    if (Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x + 1]) && Float.parseFloat(mSales[x]) < Float.parseFloat(mSales[x - 1])) {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) + 30 * width_temp, mPoints.get(x).get(Y_KEY) + 20.0f * height_temp, mTextNumberPaint);
                    } else if (Float.parseFloat(mSales[x]) > Float.parseFloat(mSales[x + 1]) && Float.parseFloat(mSales[x]) > Float.parseFloat(mSales[x - 1])) {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY) - 15.0f * height_temp, mTextNumberPaint);
                    } else if (Float.parseFloat(mSales[x]) < (Float.parseFloat(mSales[x + 1]) + Float.parseFloat(mSales[x - 1])) / 2) {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) + 30 * width_temp, mPoints.get(x).get(Y_KEY), mTextNumberPaint);
                    } else if (Float.parseFloat(mSales[x]) > (Float.parseFloat(mSales[x + 1]) + Float.parseFloat(mSales[x - 1])) / 2) {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY) - 10 * width_temp, mPoints.get(x).get(Y_KEY) - 20.0f * height_temp, mTextNumberPaint);
                    } else {
                        canvas.drawText(mSales[x], mPoints.get(x).get(X_KEY), mPoints.get(x).get(Y_KEY) - 20.0f * height_temp, mTextNumberPaint);
                    }
                }

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mClickPoint = getTouchSalesIndex(event);
                break;
            case MotionEvent.ACTION_UP:
                mClickPoint = getTouchSalesIndex(event);
                if (mPointOnClickListener != null) {
                    mPointOnClickListener.onClick(this);
                }
                break;
        }
        if (mClickPoint != -1) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    public int getClickPoint() {
        return mClickPoint;
    }

    /*
     * 得到点击的折点的index
	 */
    private int getTouchSalesIndex(MotionEvent event) {
        for (int i = 0; i < mPoints.size(); i++) {
            float x = event.getX();
            float y = event.getY();
            float diffX = x - mPoints.get(i).get(X_KEY);
            float diffY = y - mPoints.get(i).get(Y_KEY);
            double z = Math.hypot(Math.abs(diffX), Math.abs(diffY)); //求直角三角形斜边的长度
            if (z <= mClickAreas) {
                return i;
            }
        }
        return -1;

    }

    /**
     * @param firstPointX 设置折线第一个点的X轴坐标,默认是10f，折线柱状图该值设置为25f
     */
    public void setFirstPointX(float firstPointX) {
        this.mFirstPointX = firstPointX;
        invalidate();
    }

    /**
     * @param mTextNameColor 设置X坐标名称颜色
     */
    public void setTextNameColor(int mTextNameColor) {
        this.mTextNameColor = mTextNameColor;
        invalidate();
    }

    /**
     * @param mPointOnClickListener 监听圆点点击事件
     */
    public void setPointOnClickListener(OnClickListener mPointOnClickListener) {

        this.mPointOnClickListener = mPointOnClickListener;
        invalidate();
    }

    /**
     * @param mLineNumber 设置Y轴横线的条数
     */
    public void setLineNumber(int mLineNumber) {
        this.mLineNumber = mLineNumber;
        invalidate();
    }

    /**
     * @param x_Space 设置折点间的距离,默认60f,折线柱状图设置为45f
     */
    public void setX_Space(float x_Space) {
        X_Space = x_Space;
        invalidate();
    }

    /**
     * @param y_Space 设置横线间的间距
     */
    public void setY_Space(float y_Space) {
        Y_Space = y_Space;
        invalidate();
    }

    /**
     * @param mPolyLineWidth 设置折线的宽度
     */
    public void setPolyLineWidth(int mPolyLineWidth) {
        this.mPolyLineWidth = mPolyLineWidth;
        invalidate();
    }

    /**
     * @param mSales 设置销售额
     */
    public void setSales(String[] mSales) {
        this.mSales = mSales;
        invalidate();
    }

    /**
     * @param mTextSize 设置字体的大小
     */
    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    /**
     * @param mClickAreas 设置响应点击圆点的区域范围
     */
    public void setClickAreas(int mClickAreas) {
        this.mClickAreas = mClickAreas;
        invalidate();
    }

    /**
     * @param mPolyLineColor 设置折线的颜色
     */
    public void setPolyLineColor(int mPolyLineColor) {
        this.mPolyLineColor = mPolyLineColor;
        invalidate();
    }

}

