package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aotuman.myapp.custom.ChartBaseView;
import com.aotuman.myapp.custom.DashLineView;
import com.aotuman.myapp.custom.DrawnTextView;
import com.aotuman.myapp.custom.LineView;

/**
 * Created by Michael on 2016/10/29.
 */
public class TotalViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ChartBaseView chartBaseView = new ChartBaseView(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        params.leftMargin = ImageUtils.dip2px(getActivity(), 8);
//        params.rightMargin = ImageUtils.dip2px(getActivity(), 8);
        chartBaseView.setLayoutParams(params);

        frameLayout.addView(chartBaseView);

        String[] value = {"50","80","100","40","60","80","20","0"};
        float maxSale = 800;
        LineView lineView = new LineView(getActivity(), value, maxSale, "1");
        lineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        chartBaseView.addLinearChartView(lineView);

        String[] sales = {"800","800","800","800","800","800","800","800"};
        float maxSales = 800f;
        DashLineView dashLineView = new DashLineView(getActivity(),sales,maxSales,"1");
        chartBaseView.addBaseLineChartView(dashLineView);

        String[]    xaixsTitle = {"一月","二月","三月","四月","五月","六月","七月","八月"};
        for (int i = 0, textlength = xaixsTitle.length; i < textlength; i++) {
            float textX = 0;
            if (i == 0) {
                textX = 12 + (25f) / 2.0f;
            } else {
                textX = (i * 25f) + i * 20 + (i + 1) * 12 + 12.5f;
            }
            //添加图表x坐标描述
            DrawnTextView               textView = new DrawnTextView(getActivity(), xaixsTitle[i], textX, textlength * 57, "1", true);
            RelativeLayout.LayoutParams rparams   = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = ImageUtils.dip2px(getActivity(), 5);
            textView.setLayoutParams(rparams);
            chartBaseView.getTlayout().addView(textView);
        }
        return frameLayout;
    }
}
