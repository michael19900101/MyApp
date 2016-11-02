package com.aotuman.myapp.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aotuman.myapp.ImageUtils;

/**
 * Created by Michael on 2016/10/29.
 */
public class ChartBaseView extends LinearLayout {

    private Context mContext;
    private VerticalLineView mVerticalLineView;
    private RelativeLayout mRlayout;
    private RelativeLayout mPloylayout;
    private RelativeLayout  mTlayout;
    private RelativeLayout  mBaseLineLayout;

    public ChartBaseView(Context context) {
        this(context, null);
    }

    public ChartBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        addView(relativeLayout);

        //横竖线view
        RelativeLayout lineLayout = new RelativeLayout(mContext);
        lineLayout.setPadding(0, 0, 0, 0);
        mVerticalLineView = new VerticalLineView(mContext);
        mVerticalLineView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lineLayout.addView(mVerticalLineView);

        //水平滑动horizontalScrollView
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mContext);
        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlParams.leftMargin = ImageUtils.dip2px(mContext, 30f);
        rlParams.rightMargin = ImageUtils.dip2px(mContext, 30f);
        horizontalScrollView.setLayoutParams(rlParams);
        horizontalScrollView.setFadingEdgeLength(0);//设置边框拉伸无阴影
        horizontalScrollView.setOverScrollMode(OVER_SCROLL_NEVER);//设置无拉伸
        horizontalScrollView.setHorizontalScrollBarEnabled(false); //禁用水平滚动条
        horizontalScrollView.setPadding(ImageUtils.dip2px(mContext, 12f), 0, ImageUtils.dip2px(mContext, 12), 0);

        //scrollview的容器控件
        mRlayout = new RelativeLayout(mContext);
        mRlayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRlayout.setGravity(Gravity.BOTTOM);


        //折线的容器控件
        mPloylayout = new RelativeLayout(mContext);
        mPloylayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mPloylayout.setGravity(Gravity.BOTTOM);

        // 基准线的容器
        mBaseLineLayout = new RelativeLayout(mContext);
        mBaseLineLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBaseLineLayout.setGravity(Gravity.BOTTOM);

        //文字的容器控件
        mTlayout = new RelativeLayout(mContext);
        mTlayout.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        //添加坐标view
        relativeLayout.addView(lineLayout);
        //添加折线图的容器
        mRlayout.addView(mPloylayout);
        //添加文字容器控件
        mRlayout.addView(mTlayout);
        //添加基准线的容器
        mRlayout.addView(mBaseLineLayout);
        //添加scrollview的内容控件
        horizontalScrollView.addView(mRlayout);
        //添加scrollview控件
        relativeLayout.addView(horizontalScrollView);
    }

    /**
     *
     * @param v 添加图表到scrollview的容器中
     */
    public void addLinearChartView(View v){
        mPloylayout.addView(v);
    }

    public void addBaseLineChartView(View v){
        mBaseLineLayout.addView(v);
    }

    public RelativeLayout getTlayout() {
        return mTlayout;
    }
}
