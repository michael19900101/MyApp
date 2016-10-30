package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aotuman.myapp.custom.VerticalLineView;

/**
 * Created by Michael on 2016/10/29.
 */
public class VerticalLineViewFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        VerticalLineView lineView = new VerticalLineView(getActivity());
        return lineView;

    }
}
