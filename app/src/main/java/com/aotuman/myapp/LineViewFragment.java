package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aotuman.myapp.custom.LineView;

/**
 * Created by Michael on 2016/10/29.
 */
public class LineViewFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //水平滑动horizontalScrollView
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());
        horizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalScrollView.setFadingEdgeLength(0);//设置边框拉伸无阴影
        horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);//设置无拉伸
        horizontalScrollView.setHorizontalScrollBarEnabled(false); //禁用水平滚动条
        horizontalScrollView.setPadding(ImageUtils.dip2px(getActivity(), 12f), 0, ImageUtils.dip2px(getActivity(), 12), 0);

        RelativeLayout mRlayout = new RelativeLayout(getActivity());
        mRlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRlayout.setGravity(Gravity.BOTTOM);

        //折线的容器控件
        RelativeLayout mPloylayout = new RelativeLayout(getActivity());
        mPloylayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mPloylayout.setGravity(Gravity.BOTTOM);

        //添加折线图的容器
        mRlayout.addView(mPloylayout);

        //添加scrollview的内容控件
        horizontalScrollView.addView(mRlayout);

        String[] value = {"50","80","100","150","50","80","100","20"};
        float maxSale = 150f;
        LineView lineView = new LineView(getActivity(), value, maxSale, "1");

        mPloylayout.addView(lineView);
        return horizontalScrollView;

    }
}
