package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aotuman.myapp.utils.WaterMarkTextUtil;


/**
 * Created by aotuman on 2016/10/10.
 */
public class WaterMaskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.watermasklayout, null);
        WaterMarkTextUtil.setWaterMarkTextBg(view,"无名科技");
        return view;
    }


}
