package com.aotuman.myapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PeriodView extends LinearLayout {
    private static final int TEXT_SIZE = 16;
    private static final int TITLE_SIZE = 16;

    private TextView title;
    private TextView error;
    private LinearLayout dateTextLayout;
    private FrameLayout contentFrameLayout;
    private LinearLayout startDateLayout;
    private LinearLayout endDateLayout;
    private TextView startDateDes;
    private TextView startDateValue;
    private TextView endDateDes;
    private TextView endDateValue;
    private TextView noDataTextView;

    public PeriodView(Context context) {
        super(context);
        LinearLayout.LayoutParams wholeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int padding = 5;
        this.setPadding(padding, padding, padding, padding);
        this.setLayoutParams(wholeParams);
        this.setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);

        final LinearLayout contentPanel = new LinearLayout(context);
        LinearLayout.LayoutParams titlePanelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        contentPanel.setLayoutParams(titlePanelParams);
        contentPanel.setWeightSum(3);

        title = new TextView(context);
        title.setTextSize(TITLE_SIZE);
        title.setTextColor(Color.BLACK);
        title.setText("时间段控件");
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.weight = 1;
        titleParams.gravity = Gravity.CENTER_VERTICAL;
        contentPanel.addView(title, titleParams);

        dateTextLayout = new LinearLayout(context);
        dateTextLayout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams inputBoxParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        inputBoxParams.weight = 2;
        contentFrameLayout = new FrameLayout(context);
        contentFrameLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        noDataTextView = new TextView(context);
        noDataTextView.setText("请选择日期范围");
        noDataTextView.setGravity(Gravity.CENTER_VERTICAL);
        noDataTextView.setTextSize(TEXT_SIZE);
        contentFrameLayout.addView(noDataTextView);


        // 开始日期布局
        LinearLayout.LayoutParams dateLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        startDateLayout = new LinearLayout(context);
        startDateLayout.setOrientation(HORIZONTAL);
        startDateDes = new TextView(context);
        startDateDes.setText("开始日期:");
        startDateValue = new TextView(context);
        startDateValue.setText("2016-10-01");
        startDateLayout.addView(startDateDes);
        startDateLayout.addView(startDateValue);
        dateTextLayout.addView(startDateLayout,dateLayoutParams);

        // 结束日期布局
        endDateLayout = new LinearLayout(context);
        endDateLayout.setOrientation(HORIZONTAL);
        endDateDes = new TextView(context);
        endDateDes.setText("结束日期:");
        endDateValue = new TextView(context);
        endDateValue.setText("2016-10-30");
        endDateLayout.addView(endDateDes);
        endDateLayout.addView(endDateValue);
        dateTextLayout.addView(endDateLayout,dateLayoutParams);

        dateTextLayout.setVisibility(INVISIBLE);
        contentFrameLayout.addView(dateTextLayout);
        contentPanel.addView(contentFrameLayout, inputBoxParams);
        this.addView(contentPanel);

        error = new TextView(context);
        error.setTextColor(Color.RED);
        error.setTextSize(TEXT_SIZE);
        error.setTypeface(null, Typeface.BOLD);
        error.setVisibility(GONE);
        LinearLayout.LayoutParams errorParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.addView(error, errorParams);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getStartDateValue() {
        return startDateValue;
    }

    public TextView getEndDateValue() {
        return endDateValue;
    }

    public LinearLayout getDateTextLayout() {
        return dateTextLayout;
    }

    public TextView getError() {
        return error;
    }

    public TextView getNoDataTextView() {
        return noDataTextView;
    }

    public FrameLayout getContentFrameLayout() {
        return contentFrameLayout;
    }
}
