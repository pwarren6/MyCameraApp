package com.mobileappscompany.training.mycameraapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by admin on 7/18/2017.
 */

public class Rectangle extends View {
    Paint paint = new Paint();
    public Rectangle(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(20, 56, 200, 112);
        canvas.drawRect(rect, paint);
    }
}
