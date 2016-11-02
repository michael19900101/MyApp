package com.aotuman.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by aotuman on 2016/10/10.
 */
public class TextFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final PeriodView periodView = new PeriodView(getActivity());
        periodView.getContentFrameLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(periodView.getDateTextLayout().getVisibility()!=View.VISIBLE){
                    periodView.getDateTextLayout().setVisibility(View.VISIBLE);
                    periodView.getNoDataTextView().setVisibility(View.INVISIBLE);
                }else {
                    periodView.getDateTextLayout().setVisibility(View.INVISIBLE);
                    periodView.getNoDataTextView().setVisibility(View.VISIBLE);
                }
            }
        });

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.periodlayout, null);
        return view;
    }

}
