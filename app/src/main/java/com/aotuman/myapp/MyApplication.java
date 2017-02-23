package com.aotuman.myapp;

import android.app.Application;

import com.aotuman.myapp.utils.Utils;

/**
 * Created by aotuman on 2017/2/23.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
