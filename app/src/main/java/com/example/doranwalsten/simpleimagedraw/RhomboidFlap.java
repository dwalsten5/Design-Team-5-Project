package com.example.doranwalsten.simpleimagedraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by doranwalsten on 1/23/16.
 * This class creates a rhombioid flap shape to display in the app
 */
public class RhomboidFlap extends View {

    //Default Constructor
    private double alpha; //Inclination angle
    private double beta; //Spacing angle
    private int height;
    private int width;
    private Point center;

    public RhomboidFlap(Context context,int x, int y) {
        super(context);
        //Set default alpha, beta, height, and width
        this.alpha = 0;
        this.beta = Math.PI/3;
        this.width = 80;
        this.height = (int) Math.floor(this.width*Math.sqrt(3));
        this.center = new Point(x,y);
    }

    public void setParameters(double a, double b, int h, int w) {
        this.alpha = a;
        this.beta = b;
        this.width = w;
        this.height = h;
    }

    public void setCenter(int x, int y) {
        this.center.set(x,y);
    }

    public Point getCenter() {
        return this.center;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Get the points
        ArrayList<Point> ref = calculatePoints();
        //Set the style
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(android.graphics.Color.BLACK);

        //Design the outline
        Path path = new Path();
        path.moveTo(ref.get(0).x, ref.get(0).y);
        path.lineTo(ref.get(1).x, ref.get(1).y);
        path.lineTo(ref.get(2).x,ref.get(2).y);
        path.moveTo(ref.get(0).x, ref.get(0).y);
        path.lineTo(ref.get(3).x, ref.get(3).y);
        path.lineTo(ref.get(4).x,ref.get(4).y);
        path.lineTo(ref.get(5).x,ref.get(5).y);
        path.lineTo(ref.get(0).x,ref.get(0).y);
        path.close();

        canvas.drawPath(path,paint);
    }

    private ArrayList<Point> calculatePoints() {
        //List of Points
        ArrayList<Point> reference = new ArrayList<Point>();
        //Important values
        double l = Math.sqrt(Math.pow(this.height / 2, 2) + Math.pow(this.width / 2, 2));
        double gamma = (this.alpha + this.beta) - Math.PI/2;

        //Find the points
        Point pt1 = new Point();
        pt1.set(center.x, center.y);
        reference.add(pt1);

        int x = 0;
        int y = 0;
        Point pt2 = new Point();
        x = (int) (pt1.x + l * Math.cos(this.alpha));
        y = (int) (pt1.y - l * Math.sin(this.alpha));
        pt2.set(x, y);
        reference.add(pt2);

        Point pt3 = new Point();
        x = (int) (pt2.x + l * Math.sin(gamma));
        y = (int) (pt2.y - l * Math.cos(gamma));
        pt3.set(x, y);
        reference.add(pt3);

        //Now, the body of the Rhombus
        Point pt4 = new Point();
        x = pt1.x - this.width / 2;
        y = pt1.y + this.height / 2;
        pt4.set(x, y);
        reference.add(pt4);

        Point pt5 = new Point();
        x = pt1.x - this.width;
        y = pt1.y;
        pt5.set(x, y);
        reference.add(pt5);

        Point pt6 = new Point();
        x = pt1.x - this.width / 2;
        y = pt1.y - this.height / 2;
        pt6.set(x, y);
        reference.add(pt6);

        return reference;
    }


    public ImageView returnImage() {
        Bitmap viewCapture = null;
        this.setDrawingCacheEnabled(true);
        viewCapture = Bitmap.createBitmap(this.getDrawingCache());
        ImageView viewSeen = new ImageView(this.getContext());
        viewSeen.setImageBitmap(viewCapture);
        return viewSeen;
    }


}
