package com.aotuman.myapp;

import android.content.Context;

/**
 * Created by Michael on 2016/10/22.
 */
public class ImageUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
