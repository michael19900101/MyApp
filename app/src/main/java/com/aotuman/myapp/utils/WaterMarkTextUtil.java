package com.aotuman.myapp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

/**
 * Created by aotuman on 2017/3/20.
 */

public class WaterMarkTextUtil {
    //设置背景
    public static void setWaterMarkTextBg(View view, String content) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        view.setBackgroundDrawable(drawTextToBitmap(content));
    }


    public static BitmapDrawable drawTextToBitmap(String content) {
        // 1.创建带有文字的bitmap
        Bitmap bitmap = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(40);
        Path path = new Path();
        path.moveTo(30, 150);
        path.lineTo(300, 0);
        canvas.drawTextOnPath(content, path, 0, 30, paint);

        // 2.设置bitmapDrawable的填充方式为重复填充
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapDrawable.setDither(true);
        return bitmapDrawable;
    }
}