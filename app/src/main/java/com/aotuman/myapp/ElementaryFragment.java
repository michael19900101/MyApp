package com.aotuman.myapp;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aotuman on 2016/10/10.
 */
public class ElementaryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        TextView view = new TextView(getActivity());
//        view.setText("AAAA");

        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams wholeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int padding = 10;
        linearLayout.setPadding(padding, padding, padding, padding);
        linearLayout.setLayoutParams(wholeParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(getActivity());
        title.setText("AAAAA");
        title.setTextSize(16);
        title.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.weight = 1;
        titleParams.gravity = Gravity.CENTER_VERTICAL;
        linearLayout.addView(title, titleParams);

        EditText dateEdit = new EditText(getActivity());
        dateEdit.setKeyListener(null);
        dateEdit.setFocusable(true);
        dateEdit.setTextSize(16);
        LinearLayout.LayoutParams dateEditParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateEditParams.weight = 2;
        dateEditParams.gravity = Gravity.CENTER_VERTICAL;
        linearLayout.addView(dateEdit, dateEditParams);

        return linearLayout;
    }
}
