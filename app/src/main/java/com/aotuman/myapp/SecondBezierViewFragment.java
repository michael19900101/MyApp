package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aotuman.myapp.bezier.SecondBezierView;

/**
 * Created by aotuman on 2017/2/23.
 */

public class SecondBezierViewFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams wholeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        SecondBezierView secondBezierView = new SecondBezierView(getActivity());
        linearLayout.addView(secondBezierView);
        return linearLayout;
    }
}
