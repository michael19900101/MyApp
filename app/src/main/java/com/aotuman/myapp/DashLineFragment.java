package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aotuman.myapp.custom.DashLineView;

/**
 * Created by aotuman on 2016/10/31.
 */
public class DashLineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] sales = {"800","800","800","800","800","800","800","800"};
        float maxSales = 800f;
        DashLineView dashLineView = new DashLineView(getActivity(),sales,maxSales,"1");
        return dashLineView;
    }
}
