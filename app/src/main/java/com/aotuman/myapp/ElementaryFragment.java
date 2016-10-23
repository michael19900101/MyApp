package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by aotuman on 2016/10/10.
 */
public class ElementaryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams wholeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        FlexboxLayout flexboxLayout = new FlexboxLayout(getActivity());
        for(int i = 0; i< 10;i++){
//            TextView textView = new TextView(getActivity());
//            textView.setText("  学生["+i+"]  ");

            BaseLegendView baseLegendView = new BaseLegendView(getActivity(),"学生["+i+"]",i);
            baseLegendView.setPadding(0,5,0,5);
            flexboxLayout.addView(baseLegendView);
        }
//        row（默认值）：主轴为水平方向，起点在左端。
//        row-reverse：主轴为水平方向，起点在右端。
//        column：主轴为垂直方向，起点在上沿。
//        column-reverse：主轴为垂直方向，起点在下沿。
        flexboxLayout.setFlexDirection(FlexboxLayout.FLEX_DIRECTION_ROW_REVERSE);//属性决定主轴的方向（即项目的排列方向）


//        flex-start（默认值）：左对齐
//        flex-end：右对齐
//        center： 居中
//        space-between：两端对齐，项目之间的间隔都相等。
//        space-around：每个项目两侧的间隔相等。所以，项目之间的间隔比项目与边框的间隔大一倍。
        flexboxLayout.setJustifyContent(FlexboxLayout.JUSTIFY_CONTENT_CENTER); //属性定义了项目在主轴上的对齐方式。

//        nowrap ：不换行
//        wrap：按正常方向换行
//        wrap-reverse：按反方向换行
        flexboxLayout.setFlexWrap(FlexboxLayout.FLEX_WRAP_WRAP);// 项目按反方向换行

        linearLayout.addView(flexboxLayout,wholeParams);

        return linearLayout;
    }

}
