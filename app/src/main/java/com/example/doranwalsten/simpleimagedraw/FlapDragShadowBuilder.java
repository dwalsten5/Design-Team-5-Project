package com.example.doranwalsten.simpleimagedraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.DragEvent;
import android.view.View;

/**
 * Created by doranwalsten on 1/23/16.
 */
public class FlapDragShadowBuilder extends View.DragShadowBuilder {


    Point touchPointCoord;
    private Bitmap image;

    public FlapDragShadowBuilder(Bitmap view_image) {
        super();
        image = view_image;
        touchPointCoord = new Point();
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        canvas.drawBitmap(image,0,0,null);
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize,
                                       Point shadowTouchPoint) {

        shadowSize.x = image.getWidth();
        shadowSize.y = image.getHeight();
        shadowTouchPoint.set(shadowSize.x, shadowSize.y);
    }
}
